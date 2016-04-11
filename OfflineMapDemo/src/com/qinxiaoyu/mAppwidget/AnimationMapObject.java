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
		ROTATION_ANTICLOCKWISE,	//��ʱ����ת
		ROTATION_CLOCKWISE,		//˳ʱ����ת
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
		//���ÿ����,��ֹ�����ʧ��
		paint.setAntiAlias(true);
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
		
		matrix.setTranslate(1,1);
		//matrix.postTranslate(getWidth() + getScrollX() - thisCarIcon.getWidth() - getWidth()/2, getHeight() + getScrollY() - thisCarIcon.getHeight() - getHeight()/2);
		//matrix.setRotate(rotationAngle, thisCarIcon.getWidth() / 2, thisCarIcon.getHeight() / 2);
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
	public void setRotation(int angle,int duration)
	{
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
		//������תʱ��
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
		this.targetPosition = point;
		isMove = true;
		//����X���Y�ᵥλʱ���ƶ��ľ���
		Point pointTmp = getPosition();
		
		
	}
	
	
	/**
	 * ÿ����ת1�ȣ�ʱ����duration��ȷ��������ת60�ȣ�duration=60����ʱ1msһ��
	 * ����ת60�ȣ�duration=600ms��ʱ10ms�ػ�һ��
	 * 
	 * һ���ػ�ʱ��=duration/angle
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
				//ÿ��50�����滻ˢ���ƶ�����תЧ������
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
		private void drawMove()
		{
			//�жϵ�ǰ���λ�ã���ǰλ�õ��������Ŀ��λ�õ�X����ʱ���ƶ���offsetΪ��
//			getPosition();
		}
		
	}

	public boolean isRotation() {
		return isRotation;
	}
	
	
	
	
	
	


}
