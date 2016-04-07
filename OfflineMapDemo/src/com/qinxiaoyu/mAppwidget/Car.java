package com.qinxiaoyu.mAppwidget;

import android.graphics.drawable.Drawable;

import com.ls.widgets.map.model.MapObject;




/**
 * @author    qinxiaoyu@163.com
 * @date      2016��4��1�� ����5:38:55
 * 
 *  ���ԣ�
 *  1.�������
 *  2.���г���
 *  3.���з���
 *  4.�����ٶ�
 *  5.�������ͣ������в�ͬ��ͼƬ��
 *  
 */
public class Car extends MapObject{

	
	/**
	 * ǰ����Ƿ���
	 */
	protected boolean isHeadlampsOpen;
	
	
	/**ת��ö��
	 * @author    qinxiaoyu@163.com
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
	
	
	/**
	 * ��ʼ��һ����
	 * @param id
	 * @param drawable
	 * @param x
	 * @param y
	 *
	 * time 2016��4��1������5:37:59
	 */
	public Car(Object id, Drawable drawable, int x, int y) {
		super(id, drawable, x, y);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	/******************** getting & setting **************************/
	/**
	 * ��ô���Ƿ�����״��
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��5�� ����10:05:28 
	 * @return
	 */
	public boolean	getIsHeadlampsOpen(){return this.isHeadlampsOpen;}
	/**
	 * ���ô�ƿ���״̬
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��5�� ����10:05:48 
	 * @param isHeadlampsOpen
	 */
	public void setIsHeadlampsOpen(boolean isHeadlampsOpen){this.isHeadlampsOpen = isHeadlampsOpen;}
	/**
	 * ���ת��״̬
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��5�� ����10:06:06 
	 * @return
	 */
	public MOTOR_TURNING getTurning(){return this.turning;}
	/**
	 * ����ת��״̬
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��5�� ����10:06:21 
	 * @param turning
	 */
	public void setTurning(MOTOR_TURNING turning){this.turning = turning;}
	/**
	 * ��ó����ٶ�
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��5�� ����10:06:31 
	 * @return
	 */
	public float getSpeed(){return this.speed;}
	/**
	 * ���ó����ٶ�
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��5�� ����10:06:47 
	 * @param speed
	 */
	public void setSpeed(float speed){this.speed = speed;}
	/**
	 * ��ó�������
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��5�� ����10:07:02 
	 * @return
	 */
	public String getType(){return this.type;}
	/**
	 * ���ó�������
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��5�� ����10:07:10 
	 * @param type
	 */
	public void setType(String type){this.type = type;}
	
}
