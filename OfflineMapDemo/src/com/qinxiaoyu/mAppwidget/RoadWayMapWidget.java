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
 * @date    2016年4月1日 上午9:04:27
 * @describe  井下交通地图地图控件  
 */
/**
 * @author    qinxiaoyu@163.com
 * @date      2016年4月7日 上午8:38:04 
 */
public class RoadWayMapWidget extends MapWidget{

	  
	/**小车图片*/
	protected static Bitmap thisCarIcon;
	/**小车的移动路径，目前是单路径*/
	protected List<Point> movePath;
	protected Scroller mapMoveScroller;
	private LinearInterpolator linearInterpolator;
	
	/**
	 * 调试信息方法
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月7日 上午10:28:33 
	 * @param str
	 */
	private void debug(String str){Log.d("RoadWayMapWidget",str);}
	
	
	
	/**
	 * 
	 * @param context
	 * 			-context
	 * @param rootMapFolder
	 * 			-地图资源文件名
	 * @param initialZoomLevel
	 * 			-初始化缩放等级
	 * @date  2016年4月1日上午9:11:39
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
	 * 初始化居中小车
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月6日 上午9:38:27 
	 * @param context
	 */
	public void initThisCar(Context context){
		thisCarIcon = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.car)).getBitmap();
	}

	
	
	

	/**
	 * 使地图旋转某个角度，匀速旋转的方式
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月7日 上午8:38:08 
	 * @param start_rotation
	 * 			- 开始旋转的角度
	 * @param end_rotation
	 * 			- 选择的角度
	 * @param duration 
	 * 			- 旋转过程的时间
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
	 * 匀速移动控件
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月7日 下午2:34:51 
	 * @param x_start
	 * 			-x坐标起始位置
	 * @param y_start
	 * 			-y坐标起始位置
	 * @param x_offset
	 * 			-x坐标位置偏移量
	 * @param y_offset
	 * 			-y坐标位置便宜量
	 * @param LinearDuration
	 * 			-移动时间
	 */
	public void moveViewByLinear(final float x_start,float y_start,final float x_offset,float y_offset,int LinearDuration ){
		TranslateAnimation animation = new TranslateAnimation(x_start, x_offset,y_start, y_offset);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(LinearDuration);//设置动画持续时间 
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
	 * 移动地图，从一个点到另一个点，匀速移动方式
	 * @author    qinxiaoyu@163.com
	 * @date      2016年4月1日 上午9:38:13
	 * @param x_start
	 * 				-起始点x坐标
	 * @param y_start
	 * 				-起始点y坐标
	 * @param x_end
	 * 				-x坐标偏移量
	 * @param y_end
	 * 				-y轴偏移量
	 * @param scrollerDuration
	 * 				-移动的时间
	 */
	public void moveTo(int x_start,int y_start,int x_end,int y_end,int scrollerDuration){  	
//		scroller.setFriction(1);
//		// 更新结束后，使用动画控制偏移过程
		scroller.startScroll(x_start, y_start, x_end, y_end,scrollerDuration);  
//		// 重绘控件
		
//		mapMoveScroller.startScroll(x_start, y_start, x_end, y_end,scrollerDuration);
		invalidate();   
	} 
	
	
	/**
	 * 重写onDraw方法
	 *
	 * @param canvas
	 *
	 * @date 2016年4月1日上午9:38:50
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

			//去掉地图上显示的logo
//			if (logo != null) {
//				canvas.drawBitmap(logo,
//						getWidth() + getScrollX() - logo.getWidth() - getWidth()/2,
//						getHeight() + getScrollY() - logo.getHeight() - getHeight()/2,
//						null);
//			}
			//显示地图上移动的小车
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
