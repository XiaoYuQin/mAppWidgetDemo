package com.qinxiaoyu.mAppwidget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.animation.LinearInterpolator;




/**
 * @author    秦晓宇
 * @date      2016年4月1日 下午5:38:55
 * 
 * @特性：
 *  1.车有轱辘
 *  2.车有车灯
 *  3.车有方向
 *  4.车有速度
 *  5.车有类型（可能有不同的图片）
 *  
 */
public class AnimationMapObject{

	
	private static final boolean isDebug = true;
	
	/**前大灯是否开启*/
	protected boolean isHeadlampsOpen;
	
	
	/**转向枚举
	 * @author    秦晓宇
	 * @date      2016年4月5日 上午9:47:46 
	 */
	public enum MOTOR_TURNING{
		NONE,
		TURN_RIGHT,
		TURN_LEFT
	};
	
	protected MOTOR_TURNING turning;
	protected float speed;
	protected String type;
	protected int id;
	protected Bitmap bitmap;

	protected float rotationAngle;
	protected RotationAnimationThread rotationanimationThread;
	protected MoveAnimationThread moveAnimationThread;

	

	enum ROTATION_TYPE{
		ROTATION_ANTICLOCKWISE,	//逆时针旋转
		ROTATION_CLOCKWISE,		//顺时针旋转
	};
	protected ROTATION_TYPE rotationType;
	protected boolean isRotation;
	protected boolean isDraw;
	protected float rotationEndAngle;
	
	
	protected boolean isMove;
	protected Point targetPosition;
	protected float move_xoffset;
	protected float move_yoffset;
	protected float move_ystep;
	protected float move_xstep;
	protected float xoffset;
	protected float yoffset;
	private float addxOffset;
	private float addYoffset;

	/**图形缩放*/
	protected float scale = 1f;
	


//	Matrix matrix;
	Point pointTmp;
	/**
	 * 初始化一辆车，绘制将车辆的图片载入到程序中
	 * @param context
	 * @param drawable
	 * 			- 车辆的图片资源
	 * @date 2016年4月7日下午5:09:50
	 */
	public AnimationMapObject(Context context,int id,Drawable drawable,Point initPoint) 
	{	
//		matrix = new Matrix();	
		this.id = id;		
		debug("getDrawable = "+ drawable);
		bitmap = ((BitmapDrawable) drawable).getBitmap();
		targetPosition = new Point();
		pointTmp = new Point();
//		animationTimer = new Timer();
		rotationanimationThread = new RotationAnimationThread();
		rotationanimationThread.start();
		moveAnimationThread = new MoveAnimationThread();
		moveAnimationThread.start();
		move_xstep = initPoint.x;
		move_ystep = initPoint.y;
	
	}
	
	
	/**
	 * 调试方法
	 * @author    秦晓宇
	 * @date      2016年4月7日 上午10:28:33 
	 * @param str
	 */
	private void debug(String str)
	{
		if(isDebug == true)
			Log.d("AnimationMapObject",str);
	}
	
	/**
	 * 绘制小车
	 * @author    秦晓宇
	 * @date      2016年4月7日 下午5:13:54 
	 * @param canvas
	 * @param point
	 */
	public void draw(Canvas canvas){
		Matrix matrix = new Matrix();
		Paint paint = new Paint();

		//设置抗锯齿,防止过多的失真
		matrix.postScale(scale, scale);
//		if(isRotation == true);
		matrix.postRotate(rotationAngle, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
//		if(isMove == true)
		matrix.postTranslate(move_xstep-bitmap.getWidth() / 2,move_ystep-bitmap.getHeight() / 2);		
		
		paint.setAntiAlias(true);  

		canvas.drawBitmap(bitmap, matrix, paint);
	}
	
	
	/**
	 * 使车辆在一定时间内旋转一定的角度
	 * @author    秦晓宇
	 * @date      2016年4月7日 下午5:17:22 
	 * @param start_rotation
	 * 			- 开始角度
	 * @param end_rotation
	 * 			- 结束角度
	 * @param duration
	 * 			- 旋转时间 毫秒为单位
	 */
	public void turnTo(float start_rotation,float end_rotation,long duration){		
		LinearInterpolator ll = new LinearInterpolator();
		ObjectAnimator animator = ObjectAnimator.ofFloat(this, "rotation",start_rotation, end_rotation);
		animator.setInterpolator(ll);
		animator.setDuration(duration);
		animator.start();
	} 
	
	public void trueLeft()
	{
		
	}

	
	/**
	 * 动画效果旋转某个物体
	 * @author    秦晓宇
	 * @date      2016年4月11日 上午11:22:01 
	 * @param angle
	 * 			- 旋转角度，正值顺时针旋转，负值逆时针旋转
	 * @param duration
	 * 			- 旋转时间
	 */
	public void setRotation(float angle,int duration)
	{
		if(angle == 0) return;
		
		/*
		 * 设定要旋转的角度和持续时间，当前角度和angle
		 * */
		
		//设置开始旋转状态标志位
		isRotation = true;
		//判断正传还是反转
		if(angle > 0)
			rotationType = ROTATION_TYPE.ROTATION_CLOCKWISE;
		else
			rotationType = ROTATION_TYPE.ROTATION_ANTICLOCKWISE;			
		//计算旋转到达的角度
		rotationEndAngle = rotationAngle+angle;
		//计算每一度的旋转时间
		int rotationScale = (int) (duration/Math.abs(angle));
		debug("setRotation 单次刷新时间 = "+rotationScale);
		rotationanimationThread.setDuration(rotationScale);
	}
	
	/**
	 * 设置移动动画
	 * @author    秦晓宇
	 * @date      2016年4月11日 下午2:57:39 
	 * @param point
	 * 			- 移动的目标地址
	 * @param duration
	 * 			- 移动的时间
	 */
	public void setMove(Point point,int duration)
	{
		if(point == null) return;
		if(point.x == move_xstep && point.y == move_ystep) return;
		this.targetPosition = point;
		isMove = true;
		
		targetPosition = point;
		
		//当前位置
		//pointTmp = bitmap.get//getPosition();
		
		pointTmp.x = (int) move_xstep;
		pointTmp.y = (int) move_ystep;
		//计算xy轴的偏移量
		xoffset = point.x-pointTmp.x;
		yoffset = point.y-pointTmp.y;
		
		move_xoffset = xoffset/duration*20;
		move_yoffset = yoffset/duration*20;				
		
		move_xstep = pointTmp.x+move_xoffset;
		move_ystep = pointTmp.y+move_yoffset;
		
		//moveAnimationThread.setDuration(duration);	
		debug("当前点x坐标 = "+pointTmp.x);
		debug("当前点y坐标 = "+pointTmp.y);
		debug("物体按x轴移动的总距离 = " + xoffset);
		debug("物体按y轴移动的总距离 = " + yoffset);
		debug("物体每次重绘x轴位置 = " + move_xoffset);
		debug("物体每次重绘y轴位置 = " + move_yoffset);
	}
	
	
	
	
	/**
	 * 每次旋转1度，时间由duration来确定。若旋转60度，duration=60，则定时1ms一次
	 * 若旋转60度，duration=600ms则定时10ms重绘一次
	 * 
	 * 一次重绘时间=duration/angle
	 * 
	 * **/
	private class RotationAnimationThread extends Thread
	{
		private boolean runFlag = true;
		private int duration;
		
		public void setDuration(int duration) {
			this.duration = duration;
		}
		public void run()
		{
			while(runFlag)
			{													
				drawRotation();
								
				try 
				{
					Thread.sleep(duration);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		private void drawRotation()
		{				
			if(isRotation == false) return;
			debug("rotationAngle = "+rotationAngle+"   rotationEndAngle = "+rotationEndAngle);			
			if(rotationType == ROTATION_TYPE.ROTATION_CLOCKWISE)
			{
				debug("正转");
				if(rotationAngle >= rotationEndAngle)
				{
					isRotation = false;
					return;
				}
				rotationAngle += 1;		
			}
								
			if(rotationType == ROTATION_TYPE.ROTATION_ANTICLOCKWISE)
			{
				debug("反转");
				if(rotationAngle <= rotationEndAngle)
				{
					isRotation = false;
					return;
				}
				rotationAngle -= 1;
			}	
		}
		
	}
	
	private class MoveAnimationThread extends Thread
	{
		private boolean runFlag = true;
		private final int duration = 20;
		public void run()
		{
			while(runFlag)
			{													
				drawMove();		
				try 
				{
					Thread.sleep(duration);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		private void drawMove()
		{							
			if(isMove == false) return;
			//若移动距离已经够了，标示已经移动到位
			if((Math.abs(addxOffset)>=Math.abs(xoffset))&&(Math.abs(addYoffset)>=Math.abs(yoffset)))
			{
				isMove = false;
				addxOffset = 0;
				addYoffset = 0;
				return;
			}			
			move_ystep += move_yoffset;
			move_xstep += move_xoffset;
			//debug("drawMove move_yoffset = "+move_yoffset);
			addxOffset+=move_xoffset;
			addYoffset+=move_yoffset;
		}
		
	}
	
	

	public boolean isRotation() {
		return isRotation;
	}
	

	
	
	
	/******************** getting & setting **************************/
	/**
	 * 获得大灯是否开启的状体
	 * @author    秦晓宇
	 * @date      2016年4月5日 上午10:05:28 
	 * @return
	 */
	public boolean	getIsHeadlampsOpen(){return this.isHeadlampsOpen;}
	/**
	 * 设置大灯开启状态
	 * @author    秦晓宇
	 * @date      2016年4月5日 上午10:05:48 
	 * @param isHeadlampsOpen
	 */
	public void setIsHeadlampsOpen(boolean isHeadlampsOpen){this.isHeadlampsOpen = isHeadlampsOpen;}
	/**
	 * 获得转向状态
	 * @author    秦晓宇
	 * @date      2016年4月5日 上午10:06:06 
	 * @return
	 */
	public MOTOR_TURNING getTurning(){return this.turning;}
	/**
	 * 设置转向状态
	 * @author    秦晓宇
	 * @date      2016年4月5日 上午10:06:21 
	 * @param turning
	 */
	public void setTurning(MOTOR_TURNING turning){this.turning = turning;}
	/**
	 * 获得车辆速度
	 * @author    秦晓宇
	 * @date      2016年4月5日 上午10:06:31 
	 * @return
	 */
	public float getSpeed(){return this.speed;}
	/**
	 * 设置车辆速度
	 * @author    秦晓宇
	 * @date      2016年4月5日 上午10:06:47 
	 * @param speed
	 */
	public void setSpeed(float speed){this.speed = speed;}
	/**
	 * 获得车辆类型
	 * @author    秦晓宇
	 * @date      2016年4月5日 上午10:07:02 
	 * @return
	 */
	public String getType(){return this.type;}
	/**
	 * 设置车辆类型
	 * @author    秦晓宇
	 * @date      2016年4月5日 上午10:07:10 
	 * @param type
	 */
	public void setType(String type){this.type = type;}
	/**
	 * 获得车辆id
	 * @author    秦晓宇
	 * @date      2016年4月8日 上午8:18:18 
	 * @return	  车辆id
	 */
	public int getId() {return id;}
	/**
	 * 设置车辆id
	 * @author    秦晓宇
	 * @date      2016年4月8日 上午8:18:43 
	 * @param id
	 */
	public void setId(int id) {this.id = id;}

	/**
	 * 设置当前图片在地图的位置
	 * @author    秦晓宇
	 * @date      2016年4月13日 上午9:52:18 
	 * @param point
	 * 			- 图片所处位置
	 */
	public void setPosition(Point point)
	{
		move_xstep = point.x;
		move_ystep = point.y;
	}
	
	/**
	 * 获得当前图片相对于地图的位置
	 * @author    秦晓宇
	 * @date      2016年4月13日 上午9:55:06 
	 * @return		
	 * 			- 当前图片所处的位置
	 */
	public Point getPosition()
	{
		Point point = new Point();
		point.x = (int) move_xstep;
		point.y = (int) move_ystep;
		return point;		
	}
	public float getScale() {
		return scale;
	}


	public void setScale(float scale) {
		this.scale = scale;
	}
	
}
