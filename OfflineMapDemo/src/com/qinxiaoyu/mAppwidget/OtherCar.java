package com.qinxiaoyu.mAppwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

/**
 * ����������ָ���˱����Լ�����ĳ���
 * @author    ������
 * @date      2016��5��5�� ����10:01:19 
 */
public class OtherCar extends AnimationMapObject{

	
	/**����Ȧ����ˢ���߳�*/
	WarringCircle warringCircle;
	
	private boolean isShowWarringCircle = false;
	
	public OtherCar(Context context, int id, Drawable drawable, Point initPoint) {
		super(context, id, drawable, initPoint);
		// TODO Auto-generated constructor stub
		warringCircle = new WarringCircle();
		warringCircle.start();
	}
	/**
	 * ��д�˸���Ļ滭�����������������4����ʾȦ����
	 *
	 * @param canvas
	 *
	 * @date      2016��5��5�� ����10:02:24 
	 *
	 * @see com.qinxiaoyu.mAppwidget.AnimationMapObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas)
	{		
		super.draw(canvas);
		if(this.isShowWarringCircle == true)
		{
			Paint p = new Paint();
			p.setAntiAlias(true);
			p.setColor(Color.RED);
			p.setStyle(Paint.Style.STROKE);
			p.setStrokeWidth(2);
			canvas.drawCircle(move_xstep, move_ystep, warringCircle.getRange4(), p);// ��Բ
			canvas.drawCircle(move_xstep, move_ystep, warringCircle.getRange3(), p);// ��Բ
			canvas.drawCircle(move_xstep, move_ystep, warringCircle.getRange2(), p);// ��Բ
			canvas.drawCircle(move_xstep, move_ystep, warringCircle.getRange1(), p);// ��Բ  		
		}
	}
			
	/**
	 * ˢ�¾�ʾȦ�̣߳�Ŀǰ1����ʾȦ����4��ԲȦ
	 * @author    ������
	 * @date      2016��5��5�� ����10:03:27 
	 */
	private class WarringCircle extends Thread
	{
		private boolean runFlag = true;
		private final int duration = 10;
		private int range;
		private int range1;
		private int range2;
		private int range3;
		private int range4;
		public int getRange1() {
			return range1;
		}
		public int getRange2() {
			return range2;
		}
		public int getRange3() {
			return range3;
		}
		public int getRange4() {
			return range4;
		}
		public void run()
		{
			while(runFlag)
			{																	
				try 
				{
					Thread.sleep(duration);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				range += 1;
				range1 = range;
				range2 = range-35*1;
				range3 = range-35*2;
				range4 = range-35*3;
				
				
				if(range1 > 200) 
				{
					range1 = 0;	
				}	
				if(range2 > 200) 
				{
					range2 = 0;	
				}	
				if(range3 > 200) 
				{
					range3 = 0;	
				}	
				if(range4 > 200) 
				{
					range4 = 0;	
					range = 0;
				}	
			}
		}
		
	}

	public boolean isWarringCircleVisable() {
		return isShowWarringCircle;
	}
	public void setWarringCircleVisable(boolean isShowWarringCircle) {
		this.isShowWarringCircle = isShowWarringCircle;
	}
	
}
