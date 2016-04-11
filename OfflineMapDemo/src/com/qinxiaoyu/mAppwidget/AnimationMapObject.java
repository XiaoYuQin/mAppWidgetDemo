package com.qinxiaoyu.mAppwidget;

import android.R.bool;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ls.widgets.map.model.MapObject;

public class AnimationMapObject extends MapObject{

	protected Bitmap bitmap;
	protected int rotationAngle;
	protected AnimationThread animationThread;
	

	enum ROTATION_TYPE{
		ROTATION_ANTICLOCKWISE,	//逆时针旋转
		ROTATION_CLOCKWISE,		//顺时针旋转
	};
	protected ROTATION_TYPE rotationType;
	protected boolean isRotation;
	protected int rotationEndAngle;
	
	protected boolean isMove;
	protected Point targetPosition;
	protected int move_xoffset;
	protected int move_yoffset;
	
	public AnimationMapObject(Object id, Drawable drawable, int x, int y,int pivotX, int pivotY, boolean isTouchable, boolean isScalable) 
	{
		super(id, drawable, x, y, pivotX, pivotY, isTouchable, isScalable);
		// TODO Auto-generated constructor stub
		bitmap = ((BitmapDrawable) drawable).getBitmap();
		animationThread = new AnimationThread();
		animationThread.start();
	}

	public AnimationMapObject(long oBJ_ID, Drawable icon, Point point,
			Point createPivotPoint, boolean b, boolean c) {
		super(oBJ_ID, icon, point, createPivotPoint, b, c);
		// TODO Auto-generated constructor stub
		bitmap = ((BitmapDrawable) icon).getBitmap();
		animationThread = new AnimationThread();
		animationThread.start();
	}

	private void debug(String str){Log.d("AnimationMapObject",str);}
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
//		super.draw(canvas);
//		if(isRotation == true)
		onRotation(canvas);
		
	}
	private void  onRotation(Canvas canvas)
	{
		
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//设置抗锯齿,防止过多的失真
		paint.setAntiAlias(true);
		matrix.setRotate(rotationAngle, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
		canvas.drawBitmap(bitmap, matrix, paint);
		
		//debug();
	}
	private void onMove(Canvas canvas)
	{
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//设置抗锯齿,防止过多的失真
		paint.setAntiAlias(true);
		
		matrix.setTranslate(1,1);
		//matrix.postTranslate(getWidth() + getScrollX() - thisCarIcon.getWidth() - getWidth()/2, getHeight() + getScrollY() - thisCarIcon.getHeight() - getHeight()/2);
		//matrix.setRotate(rotationAngle, thisCarIcon.getWidth() / 2, thisCarIcon.getHeight() / 2);
		canvas.drawBitmap(bitmap, matrix, paint);
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
	public void setRotation(int angle,int duration)
	{
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
		//计算旋转时间
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
		this.targetPosition = point;
		isMove = true;
		//计算X轴和Y轴单位时间移动的距离
		Point pointTmp = getPosition();
		
		
	}
	
	
	/**
	 * 每次旋转1度，时间由duration来确定。若旋转60度，duration=60，则定时1ms一次
	 * 若旋转60度，duration=600ms则定时10ms重绘一次
	 * 
	 * 一次重绘时间=duration/angle
	 * 
	 * **/
	private class AnimationThread extends Thread
	{
		private boolean runFlag = true;
		private boolean frushFlag = true;


		public void run()
		{
			while(runFlag)
			{											
				//每隔50毫秒替换刷新移动和旋转效果参数
				if(frushFlag == true)
				{					
					drawRotation();
					frushFlag = false;
				}
				else
				{
					drawMove();
					frushFlag = true;
				}
								
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
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
		private void drawMove()
		{
			//判断当前点的位置，当前位置的坐标大于目标位置的X坐标时，移动的offset为正
//			getPosition();
		}
		
	}

	public boolean isRotation() {
		return isRotation;
	}
	
	
	
	
	
	


}
