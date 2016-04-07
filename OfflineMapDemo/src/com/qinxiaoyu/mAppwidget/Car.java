package com.qinxiaoyu.mAppwidget;

import android.graphics.drawable.Drawable;

import com.ls.widgets.map.model.MapObject;




/**
 * @author    qinxiaoyu@163.com
 * @date      2016年4月1日 下午5:38:55
 * 
 *  特性：
 *  1.车有轱辘
 *  2.车有车灯
 *  3.车有方向
 *  4.车有速度
 *  5.车有类型（可能有不同的图片）
 *  
 */
public class Car extends MapObject{

	
	/**
	 * 前大灯是否开启
	 */
	protected boolean isHeadlampsOpen;
	
	
	/**转向枚举
	 * @author    qinxiaoyu@163.com
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
	
	
	/**
	 * 初始化一辆车
	 * @param id
	 * @param drawable
	 * @param x
	 * @param y
	 *
	 * time 2016年4月1日下午5:37:59
	 */
	public Car(Object id, Drawable drawable, int x, int y) {
		super(id, drawable, x, y);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	/******************** getting & setting **************************/
	/**
	 * 获得大灯是否开启的状体
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月5日 上午10:05:28 
	 * @return
	 */
	public boolean	getIsHeadlampsOpen(){return this.isHeadlampsOpen;}
	/**
	 * 设置大灯开启状态
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月5日 上午10:05:48 
	 * @param isHeadlampsOpen
	 */
	public void setIsHeadlampsOpen(boolean isHeadlampsOpen){this.isHeadlampsOpen = isHeadlampsOpen;}
	/**
	 * 获得转向状态
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月5日 上午10:06:06 
	 * @return
	 */
	public MOTOR_TURNING getTurning(){return this.turning;}
	/**
	 * 设置转向状态
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月5日 上午10:06:21 
	 * @param turning
	 */
	public void setTurning(MOTOR_TURNING turning){this.turning = turning;}
	/**
	 * 获得车辆速度
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月5日 上午10:06:31 
	 * @return
	 */
	public float getSpeed(){return this.speed;}
	/**
	 * 设置车辆速度
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月5日 上午10:06:47 
	 * @param speed
	 */
	public void setSpeed(float speed){this.speed = speed;}
	/**
	 * 获得车辆类型
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月5日 上午10:07:02 
	 * @return
	 */
	public String getType(){return this.type;}
	/**
	 * 设置车辆类型
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月5日 上午10:07:10 
	 * @param type
	 */
	public void setType(String type){this.type = type;}
	
}
