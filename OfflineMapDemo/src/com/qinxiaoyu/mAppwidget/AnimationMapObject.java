package com.qinxiaoyu.mAppwidget;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.ls.widgets.map.model.MapObject;

public class AnimationMapObject extends MapObject{

	protected Bitmap bitmap;
	protected float rotationAngle;
	protected RotationAnimationThread rotationanimationThread;
	protected MoveAnimationThread moveAnimationThread;
	

	enum ROTATION_TYPE{
		ROTATION_ANTICLOCKWISE,	//��ʱ����ת
		ROTATION_CLOCKWISE,		//˳ʱ����ת
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

	Point pointTmp;

	
	public AnimationMapObject(Object id, Drawable drawable, int x, int y,int pivotX, int pivotY, boolean isTouchable, boolean isScalable) 
	{
		super(id, drawable, x, y, pivotX, pivotY, isTouchable, isScalable);
		// TODO Auto-generated constructor stub
		bitmap = ((BitmapDrawable) drawable).getBitmap();
		targetPosition = new Point();
		pointTmp = new Point();
		rotationanimationThread = new RotationAnimationThread();
		rotationanimationThread.start();
		moveAnimationThread = new MoveAnimationThread();
		moveAnimationThread.start();
	}

	public AnimationMapObject(long oBJ_ID, Drawable icon, Point point,
			Point createPivotPoint, boolean b, boolean c) {
		super(oBJ_ID, icon, point, createPivotPoint, b, c);
		// TODO Auto-generated constructor stub
		bitmap = ((BitmapDrawable) icon).getBitmap();
		targetPosition = new Point();
		pointTmp = new Point();
		rotationanimationThread = new RotationAnimationThread();
		rotationanimationThread.start();
		moveAnimationThread = new MoveAnimationThread();
		moveAnimationThread.start();
	}

	private void debug(String str){Log.d("AnimationMapObject",str);}
	
//	@Override
//	public void draw(Canvas canvas) {
//		// TODO Auto-generated method stub
//		super.draw(canvas);
//		if(isDraw == true)
//		{
//			isDraw = false;
//			onRotation(canvas);
//		}
//		else
//		{
//			isDraw = true;
//			onMove(canvas);
//		}
//		
//		
//		
//	}
	private void  onRotation(Canvas canvas)
	{
		
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//���ÿ����,��ֹ�����ʧ��
		matrix.setRotate(rotationAngle, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
		canvas.drawBitmap(bitmap, matrix, paint);
		
		//debug();
	}
	private void onMove(Canvas canvas)
	{
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//���ÿ����,��ֹ�����ʧ��
		paint.setAntiAlias(true);		
		matrix.setTranslate(move_xstep,move_xstep);
		canvas.drawBitmap(bitmap, matrix, paint);
	}
	/**
	 * ����Ч����תĳ������
	 * @author    ������
	 * @date      2016��4��11�� ����11:22:01 
	 * @param angle
	 * 			- ��ת�Ƕȣ���ֵ˳ʱ����ת����ֵ��ʱ����ת
	 * @param duration
	 * 			- ��תʱ��
	 */
	public void setRotation(float angle,int duration)
	{
		if(angle == 0) return;
		
		/*
		 * �趨Ҫ��ת�ĽǶȺͳ���ʱ�䣬��ǰ�ǶȺ�angle
		 * */
		
		//���ÿ�ʼ��ת״̬��־λ
		isRotation = true;
		//�ж��������Ƿ�ת
		if(angle > 0)
			rotationType = ROTATION_TYPE.ROTATION_CLOCKWISE;
		else
			rotationType = ROTATION_TYPE.ROTATION_ANTICLOCKWISE;			
		//������ת����ĽǶ�
		rotationEndAngle = rotationAngle+angle;
		//����ÿһ�ȵ���תʱ��
		int rotationScale = (int) (duration/angle);
		debug("setRotation ����ˢ��ʱ�� = "+rotationScale);
		rotationanimationThread.setDuration(rotationScale);
	}
	
	/**
	 * �����ƶ�����
	 * @author    ������
	 * @date      2016��4��11�� ����2:57:39 
	 * @param point
	 * 			- �ƶ���Ŀ���ַ
	 * @param duration
	 * 			- �ƶ���ʱ��
	 */
	public void setMove(Point point,int duration)
	{
		if(point == null) return;
		this.targetPosition = point;
		isMove = true;
		
		targetPosition = point;
		
		//��ǰλ��
		pointTmp = getPosition();
		//����xy���ƫ����
		xoffset = point.x-pointTmp.x;
		yoffset = point.y-pointTmp.y;
		
		move_xoffset = xoffset/duration*100;
		move_yoffset = yoffset/duration*100;				
		
		move_xstep = pointTmp.x+move_xoffset;
		move_ystep = pointTmp.y+move_yoffset;
		
		//moveAnimationThread.setDuration(duration);	
		debug("pointTmp.x = "+pointTmp.x);
		debug("pointTmp.y = "+pointTmp.y);
		debug("xoffset = "+xoffset);
		debug("yoffset = "+yoffset);
		debug("move_xoffset = "+move_xoffset);
		debug("move_yoffset = "+move_yoffset);
	}
	
	
	/**
	 * ÿ����ת1�ȣ�ʱ����duration��ȷ��������ת60�ȣ�duration=60����ʱ1msһ��
	 * ����ת60�ȣ�duration=600ms��ʱ10ms�ػ�һ��
	 * 
	 * һ���ػ�ʱ��=duration/angle
	 * 
	 * **/
	private class RotationAnimationThread extends Thread
	{
		private boolean runFlag = true;
		private int duration;
		
		public int getDuration() {
			return duration;
		}
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
				debug("��ת");
				if(rotationAngle >= rotationEndAngle)
				{
					isRotation = false;
					return;
				}
				rotationAngle += 1;		
			}
								
			if(rotationType == ROTATION_TYPE.ROTATION_ANTICLOCKWISE)
			{
				debug("��ת");
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
		private int duration = 100;
		public int getDuration() {
			return duration;
		}
		public void setDuration(int duration) {
			this.duration = duration;
		}
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
			//���ƶ������Ѿ����ˣ���ʾ�Ѿ��ƶ���λ
			if((Math.abs(addxOffset)>=Math.abs(xoffset))&&(Math.abs(addYoffset)>=Math.abs(yoffset)))
			{
				isMove = false;
				addxOffset = 0;
				addYoffset = 0;
				return;
			}			
			move_ystep += move_yoffset;
			move_xstep += move_xoffset;
			debug("drawMove move_yoffset = "+move_yoffset);
			addxOffset+=move_xoffset;
			addYoffset+=move_yoffset;
		}
		
	}
	
	

	public boolean isRotation() {
		return isRotation;
	}
	
	
	
	
	
	


}
