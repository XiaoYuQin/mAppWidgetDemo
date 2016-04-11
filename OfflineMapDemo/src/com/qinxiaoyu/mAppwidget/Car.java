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
import android.os.Handler;
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
public class Car{

	
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
	protected static Bitmap thisCarIcon;
	protected Handler handler;
	/**小车旋转的角度*/
	private int rotationAngle;
	
	private AnimationThread animationThread;

	/**
	 * 初始化一辆车，绘制将车辆的图片载入到程序中
	 * @param context
	 * @param drawable
	 * 			- 车辆的图片资源
	 * @date 2016年4月7日下午5:09:50
	 */
	public Car(Context context,int id,Drawable drawable,Handler handler) 
	{	
		
		this.id = id;
		this.handler = handler;
		debug("getDrawable = "+ drawable);
		thisCarIcon = ((BitmapDrawable) drawable).getBitmap();
		debug("onRotation()");
		animationThread = new AnimationThread();
		animationThread.start();		
	}
	
	
	/**
	 * 调试方法
	 * @author    秦晓宇
	 * @date      2016年4月7日 上午10:28:33 
	 * @param str
	 */
	private void debug(String str){Log.d("Car",str);}
	
	/**
	 * 绘制小车
	 * @author    秦晓宇
	 * @date      2016年4月7日 下午5:13:54 
	 * @param canvas
	 * @param point
	 */
	public void draw(Canvas canvas,Point point){
		
		//onRotation(canvas);
		
		onMove(canvas,500,500);
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
	
	private class AnimationThread extends Thread
	{
		public boolean runFlag = true;
		
		public void run()
		{
			while(runFlag)
			{
//				debug("sendEmptyMessage");
				rotationAngle += 5;
				handler.sendEmptyMessage(0x1);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	} 
	/**
	 * 小车旋转某个角度，被UI现场循环调用，达到动画效果，rotationAngle的变化为小车内部自己计算
	 * @author    秦晓宇
	 * @date      2016年4月9日 上午11:07:00 
	 */
	private void  onRotation(Canvas canvas)
	{
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//设置抗锯齿,防止过多的失真
		paint.setAntiAlias(true);
		matrix.setRotate(rotationAngle, thisCarIcon.getWidth() / 2, thisCarIcon.getHeight() / 2);
		canvas.drawBitmap(thisCarIcon, matrix, paint);
	}
	
	/**
	 * 小车移动某个角度，被UI线程循环调用，达到动态移动的效果
	 * @author    秦晓宇
	 * @date      2016年4月11日 上午9:18:18 
	 * @param canvas
	 * @param x_offset
	 * 			-移动的x轴偏移量
	 * @param y_offset
	 * 			-移动的y轴偏移量
	 */
	private void onMove(Canvas canvas,float x_offset,float y_offset)
	{
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//设置抗锯齿,防止过多的失真
		paint.setAntiAlias(true);
		
		matrix.setTranslate(x_offset,y_offset);
		//matrix.postTranslate(getWidth() + getScrollX() - thisCarIcon.getWidth() - getWidth()/2, getHeight() + getScrollY() - thisCarIcon.getHeight() - getHeight()/2);
//		matrix.setRotate(rotationAngle, thisCarIcon.getWidth() / 2, thisCarIcon.getHeight() / 2);
		canvas.drawBitmap(thisCarIcon, matrix, paint);
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
	 * 设置handler，该handler为UI线程Handler，主要用来刷新UI线程中小车的位置，达到动态显示的效果
	 * @author    秦晓宇
	 * @date      2016年4月9日 上午9:26:56 
	 * @param handler
	 */
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
}
