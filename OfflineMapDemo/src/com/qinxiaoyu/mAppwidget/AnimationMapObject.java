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
public class AnimationMapObject{

	
	private static final boolean isDebug = true;
	
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

	/**ͼ������*/
	protected float scale = 1f;
	


//	Matrix matrix;
	Point pointTmp;
	/**
	 * ��ʼ��һ���������ƽ�������ͼƬ���뵽������
	 * @param context
	 * @param drawable
	 * 			- ������ͼƬ��Դ
	 * @date 2016��4��7������5:09:50
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
	 * ���Է���
	 * @author    ������
	 * @date      2016��4��7�� ����10:28:33 
	 * @param str
	 */
	private void debug(String str)
	{
		if(isDebug == true)
			Log.d("AnimationMapObject",str);
	}
	
	/**
	 * ����С��
	 * @author    ������
	 * @date      2016��4��7�� ����5:13:54 
	 * @param canvas
	 * @param point
	 */
	public void draw(Canvas canvas){
		Matrix matrix = new Matrix();
		Paint paint = new Paint();

		//���ÿ����,��ֹ�����ʧ��
		matrix.postScale(scale, scale);
//		if(isRotation == true);
		matrix.postRotate(rotationAngle, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
//		if(isMove == true)
		matrix.postTranslate(move_xstep-bitmap.getWidth() / 2,move_ystep-bitmap.getHeight() / 2);		
		
		paint.setAntiAlias(true);  

		canvas.drawBitmap(bitmap, matrix, paint);
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
		int rotationScale = (int) (duration/Math.abs(angle));
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
		if(point.x == move_xstep && point.y == move_ystep) return;
		this.targetPosition = point;
		isMove = true;
		
		targetPosition = point;
		
		//��ǰλ��
		//pointTmp = bitmap.get//getPosition();
		
		pointTmp.x = (int) move_xstep;
		pointTmp.y = (int) move_ystep;
		//����xy���ƫ����
		xoffset = point.x-pointTmp.x;
		yoffset = point.y-pointTmp.y;
		
		move_xoffset = xoffset/duration*20;
		move_yoffset = yoffset/duration*20;				
		
		move_xstep = pointTmp.x+move_xoffset;
		move_ystep = pointTmp.y+move_yoffset;
		
		//moveAnimationThread.setDuration(duration);	
		debug("��ǰ��x���� = "+pointTmp.x);
		debug("��ǰ��y���� = "+pointTmp.y);
		debug("���尴x���ƶ����ܾ��� = " + xoffset);
		debug("���尴y���ƶ����ܾ��� = " + yoffset);
		debug("����ÿ���ػ�x��λ�� = " + move_xoffset);
		debug("����ÿ���ػ�y��λ�� = " + move_yoffset);
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
	 * ���õ�ǰͼƬ�ڵ�ͼ��λ��
	 * @author    ������
	 * @date      2016��4��13�� ����9:52:18 
	 * @param point
	 * 			- ͼƬ����λ��
	 */
	public void setPosition(Point point)
	{
		move_xstep = point.x;
		move_ystep = point.y;
	}
	
	/**
	 * ��õ�ǰͼƬ����ڵ�ͼ��λ��
	 * @author    ������
	 * @date      2016��4��13�� ����9:55:06 
	 * @return		
	 * 			- ��ǰͼƬ������λ��
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
