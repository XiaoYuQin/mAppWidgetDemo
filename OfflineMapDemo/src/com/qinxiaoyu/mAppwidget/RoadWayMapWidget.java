package com.qinxiaoyu.mAppwidget;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Scroller;

import com.example.offlinemapdemo.R;
import com.ls.widgets.map.MapWidget;



/**
 * @author    qinxiaoyu@163.com
 * @date    2016��4��1�� ����9:04:27
 * @describe  ���½�ͨ��ͼ��ͼ�ؼ�  
 */
/**
 * @author    qinxiaoyu@163.com
 * @date      2016��4��7�� ����8:38:04 
 */
public class RoadWayMapWidget extends MapWidget{

	  
	/**С��ͼƬ*/
	protected static Bitmap thisCarIcon;
	/**С�����ƶ�·����Ŀǰ�ǵ�·��*/
	protected List<Point> movePath;
	protected Scroller mapMoveScroller;
	private LinearInterpolator linearInterpolator;
	
	/**
	 * ������Ϣ����
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��7�� ����10:28:33 
	 * @param str
	 */
	private void debug(String str){Log.d("RoadWayMapWidget",str);}
	
	
	
	/**
	 * 
	 * @param context
	 * 			-context
	 * @param rootMapFolder
	 * 			-��ͼ��Դ�ļ���
	 * @param initialZoomLevel
	 * 			-��ʼ�����ŵȼ�
	 * @date  2016��4��1������9:11:39
	 */
	public RoadWayMapWidget(Context context, String rootMapFolder,int initialZoomLevel) {
		super(context, rootMapFolder, initialZoomLevel);	
		// TODO Auto-generated constructor stub
		
		initThisCar(context);
		movePath = new ArrayList<Point>();
		linearInterpolator = new LinearInterpolator();
		mapMoveScroller = new Scroller(context, linearInterpolator);
	}
		
	
	/**
	 * ��ʼ������С��
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��6�� ����9:38:27 
	 * @param context
	 */
	public void initThisCar(Context context){
		thisCarIcon = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.car)).getBitmap();
	}

	
	
	

	/**
	 * ʹ��ͼ��תĳ���Ƕȣ�������ת�ķ�ʽ
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��7�� ����8:38:08 
	 * @param start_rotation
	 * 			- ��ʼ��ת�ĽǶ�
	 * @param end_rotation
	 * 			- ѡ��ĽǶ�
	 * @param duration 
	 * 			- ��ת���̵�ʱ��
	 */
	public void rotationTo(float start_rotation,float end_rotation,long duration)
	{
		
		LinearInterpolator ll = new LinearInterpolator();
		ObjectAnimator animator = ObjectAnimator.ofFloat(this, "rotation",start_rotation, end_rotation);
		animator.setInterpolator(ll);
		animator.setDuration(5000);
		animator.start();
	} 
	
	
	/**
	 * �����ƶ��ؼ�
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��7�� ����2:34:51 
	 * @param x_start
	 * 			-x������ʼλ��
	 * @param y_start
	 * 			-y������ʼλ��
	 * @param x_offset
	 * 			-x����λ��ƫ����
	 * @param y_offset
	 * 			-y����λ�ñ�����
	 * @param LinearDuration
	 * 			-�ƶ�ʱ��
	 */
	public void moveViewByLinear(final float x_start,float y_start,final float x_offset,float y_offset,int LinearDuration ){
		TranslateAnimation animation = new TranslateAnimation(x_start, x_offset,y_start, y_offset);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(LinearDuration);//���ö�������ʱ�� 
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
						         
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
						         
			@Override
			public void onAnimationEnd(Animation animation) {
				int left = getLeft()+(int)(x_offset-x_start);
				int top = getTop();
				int width = getWidth();
				int height = getHeight();
				clearAnimation();
				layout(left, top, left+width, top+height);
			}
		});
		this.startAnimation(animation);

	}
	
	
	
	
	
	/**
	 * �ƶ���ͼ����һ���㵽��һ���㣬�����ƶ���ʽ
	 * @author    qinxiaoyu@163.com
	 * @date      2016��4��1�� ����9:38:13
	 * @param x_start
	 * 				-��ʼ��x����
	 * @param y_start
	 * 				-��ʼ��y����
	 * @param x_end
	 * 				-x����ƫ����
	 * @param y_end
	 * 				-y��ƫ����
	 * @param scrollerDuration
	 * 				-�ƶ���ʱ��
	 */
	public void moveTo(int x_start,int y_start,int x_end,int y_end,int scrollerDuration){  	
//		scroller.setFriction(1);
//		// ���½�����ʹ�ö�������ƫ�ƹ���
		scroller.startScroll(x_start, y_start, x_end, y_end,scrollerDuration);  
//		// �ػ�ؼ�
		
//		mapMoveScroller.startScroll(x_start, y_start, x_end, y_end,scrollerDuration);
		invalidate();   
	} 
	
	
	/**
	 * ��дonDraw����
	 *
	 * @param canvas
	 *
	 * @date 2016��4��1������9:38:50
	 *
	 * @see com.ls.widgets.map.MapWidget#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		this.getDrawingRect(drawingRect);

		if (config != null) {
			if (prevGrid != null) {
				prevGrid.draw(canvas, paint, drawingRect);
			}

			if (grid != null) {
				grid.draw(canvas, paint, drawingRect);
			}

			drawLayers(canvas, drawingRect);

			//ȥ����ͼ����ʾ��logo
//			if (logo != null) {
//				canvas.drawBitmap(logo,
//						getWidth() + getScrollX() - logo.getWidth() - getWidth()/2,
//						getHeight() + getScrollY() - logo.getHeight() - getHeight()/2,
//						null);
//			}
			//��ʾ��ͼ���ƶ���С��
			if(thisCarIcon != null){
				canvas.drawBitmap(thisCarIcon,
						getWidth() + getScrollX() - thisCarIcon.getWidth() - getWidth()/2,
						getHeight() + getScrollY() - thisCarIcon.getHeight() - getHeight()/2,
						null);
			}
			
			
		} else {
			debug("scrollTo(0, 0);");
			scrollTo(0, 0);
			drawMissingDataErrorMessage(canvas);
		}
	}

	
	/******************** getting & setting **************************/

	public static Bitmap getThisCarIcon() {
		return thisCarIcon;
	}


	public static void setThisCarIcon(Bitmap thisCarIcon) {
		RoadWayMapWidget.thisCarIcon = thisCarIcon;
	}


	public List<Point> getMovePath() {
		return movePath;
	}


	public void setMovePath(List<Point> movePath) {
		this.movePath = movePath;
	}
	
	
}
