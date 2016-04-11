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
 * @author    ������
 * @date      2016��4��1�� ����5:38:55
 * 
 * @���ԣ�
 *  1.�������
 *  2.���г���
 *  3.���з���
 *  4.�����ٶ�
 *  5.�������ͣ������в�ͬ��ͼƬ��
 *  
 */
public class Car{

	
	/**ǰ����Ƿ���*/
	protected boolean isHeadlampsOpen;
	
	
	/**ת��ö��
	 * @author    ������
	 * @date      2016��4��5�� ����9:47:46 
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
	/**С����ת�ĽǶ�*/
	private int rotationAngle;
	
	private AnimationThread animationThread;

	/**
	 * ��ʼ��һ���������ƽ�������ͼƬ���뵽������
	 * @param context
	 * @param drawable
	 * 			- ������ͼƬ��Դ
	 * @date 2016��4��7������5:09:50
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
	 * ���Է���
	 * @author    ������
	 * @date      2016��4��7�� ����10:28:33 
	 * @param str
	 */
	private void debug(String str){Log.d("Car",str);}
	
	/**
	 * ����С��
	 * @author    ������
	 * @date      2016��4��7�� ����5:13:54 
	 * @param canvas
	 * @param point
	 */
	public void draw(Canvas canvas,Point point){
		
		//onRotation(canvas);
		
		onMove(canvas,500,500);
	}
	
	/**
	 * ʹ������һ��ʱ������תһ���ĽǶ�
	 * @author    ������
	 * @date      2016��4��7�� ����5:17:22 
	 * @param start_rotation
	 * 			- ��ʼ�Ƕ�
	 * @param end_rotation
	 * 			- �����Ƕ�
	 * @param duration
	 * 			- ��תʱ�� ����Ϊ��λ
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
	 * С����תĳ���Ƕȣ���UI�ֳ�ѭ�����ã��ﵽ����Ч����rotationAngle�ı仯ΪС���ڲ��Լ�����
	 * @author    ������
	 * @date      2016��4��9�� ����11:07:00 
	 */
	private void  onRotation(Canvas canvas)
	{
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//���ÿ����,��ֹ�����ʧ��
		paint.setAntiAlias(true);
		matrix.setRotate(rotationAngle, thisCarIcon.getWidth() / 2, thisCarIcon.getHeight() / 2);
		canvas.drawBitmap(thisCarIcon, matrix, paint);
	}
	
	/**
	 * С���ƶ�ĳ���Ƕȣ���UI�߳�ѭ�����ã��ﵽ��̬�ƶ���Ч��
	 * @author    ������
	 * @date      2016��4��11�� ����9:18:18 
	 * @param canvas
	 * @param x_offset
	 * 			-�ƶ���x��ƫ����
	 * @param y_offset
	 * 			-�ƶ���y��ƫ����
	 */
	private void onMove(Canvas canvas,float x_offset,float y_offset)
	{
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//���ÿ����,��ֹ�����ʧ��
		paint.setAntiAlias(true);
		
		matrix.setTranslate(x_offset,y_offset);
		//matrix.postTranslate(getWidth() + getScrollX() - thisCarIcon.getWidth() - getWidth()/2, getHeight() + getScrollY() - thisCarIcon.getHeight() - getHeight()/2);
//		matrix.setRotate(rotationAngle, thisCarIcon.getWidth() / 2, thisCarIcon.getHeight() / 2);
		canvas.drawBitmap(thisCarIcon, matrix, paint);
	}
	
	/******************** getting & setting **************************/
	/**
	 * ��ô���Ƿ�����״��
	 * @author    ������
	 * @date      2016��4��5�� ����10:05:28 
	 * @return
	 */
	public boolean	getIsHeadlampsOpen(){return this.isHeadlampsOpen;}
	/**
	 * ���ô�ƿ���״̬
	 * @author    ������
	 * @date      2016��4��5�� ����10:05:48 
	 * @param isHeadlampsOpen
	 */
	public void setIsHeadlampsOpen(boolean isHeadlampsOpen){this.isHeadlampsOpen = isHeadlampsOpen;}
	/**
	 * ���ת��״̬
	 * @author    ������
	 * @date      2016��4��5�� ����10:06:06 
	 * @return
	 */
	public MOTOR_TURNING getTurning(){return this.turning;}
	/**
	 * ����ת��״̬
	 * @author    ������
	 * @date      2016��4��5�� ����10:06:21 
	 * @param turning
	 */
	public void setTurning(MOTOR_TURNING turning){this.turning = turning;}
	/**
	 * ��ó����ٶ�
	 * @author    ������
	 * @date      2016��4��5�� ����10:06:31 
	 * @return
	 */
	public float getSpeed(){return this.speed;}
	/**
	 * ���ó����ٶ�
	 * @author    ������
	 * @date      2016��4��5�� ����10:06:47 
	 * @param speed
	 */
	public void setSpeed(float speed){this.speed = speed;}
	/**
	 * ��ó�������
	 * @author    ������
	 * @date      2016��4��5�� ����10:07:02 
	 * @return
	 */
	public String getType(){return this.type;}
	/**
	 * ���ó�������
	 * @author    ������
	 * @date      2016��4��5�� ����10:07:10 
	 * @param type
	 */
	public void setType(String type){this.type = type;}
	/**
	 * ��ó���id
	 * @author    ������
	 * @date      2016��4��8�� ����8:18:18 
	 * @return	  ����id
	 */
	public int getId() {return id;}
	/**
	 * ���ó���id
	 * @author    ������
	 * @date      2016��4��8�� ����8:18:43 
	 * @param id
	 */
	public void setId(int id) {this.id = id;}


	/**
	 * ����handler����handlerΪUI�߳�Handler����Ҫ����ˢ��UI�߳���С����λ�ã��ﵽ��̬��ʾ��Ч��
	 * @author    ������
	 * @date      2016��4��9�� ����9:26:56 
	 * @param handler
	 */
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
}
