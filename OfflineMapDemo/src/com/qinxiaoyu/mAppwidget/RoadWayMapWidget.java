package com.qinxiaoyu.mAppwidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Scroller;

import com.example.offlinemapdemo.R;
import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.model.MapObject;
import com.qinxiaoyu.setting.mapPoint.MapLine;
import com.qinxiaoyu.setting.mapPoint.MapPointIndex;



/**
 * @author    秦晓宇
 * @date    2016年4月1日 上午9:04:27
 * @describe  井下交通地图地图控件  
 */
public class RoadWayMapWidget extends MapWidget{

	  
	/**小车图片*/
	protected static Bitmap thisCarIcon;
	/**小车的移动路径，目前是单路径*/
	protected List<Point> movePath;
	protected Scroller mapMoveScroller;
	private LinearInterpolator linearInterpolator;
	private ArrayList<AnimationMapObject> cars;
	private ArrayList<MapPointIndex> mapPointIndexs;
	private ArrayList<MapLine> mapLines;
	private ArrayList<MapObject> mapObjects; 
	
	Handler handler;
	
		
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
		
	
		
//		thisCarIcon = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.car_arror)).getBitmap();
		thisCarIcon = BitmapFactory.decodeResource(getResources(),  R.drawable.car).copy(Bitmap.Config.ARGB_8888, true); 
		
	
		movePath = new ArrayList<Point>();
		linearInterpolator = new LinearInterpolator();
		mapMoveScroller = new Scroller(context, linearInterpolator);
		
		cars = new ArrayList<AnimationMapObject>();
		mapPointIndexs = new ArrayList<MapPointIndex>();
		mapObjects = new ArrayList<MapObject>(); 
		
		handler = new Handler()
		{
			public void handleMessage(Message msg) 
			{   
				if(msg.what == 0x1)
				{
					invalidate();
				}
			}
		};
		
		new Timer().schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
//				debug("TimerTask run");
				handler.sendEmptyMessage(0x1);

			}
		},0,50);
		
	}		

	
	




	/**
	 * 调试方法
	 * @author    秦晓宇
	 * @date      2016年4月7日 上午10:28:33 
	 * @param str
	 */
	private void debug(String str){Log.d("RoadWayMapWidget",str);}
	
//	public void onRotate()
//	{
//		iv = (ImageView)findViewById(R.id.image);
//		RotateAnimation animation = new RotateAnimation(0, 360);
//		animation.setDuration(100000);//设定转一圈的时间
//		animation.setRepeatCount(Animation.INFINITE);//设定无限循环
//		animation.setRepeatMode(Animation.RESTART);
//		iv.startAnimation(animation);		
//	}
	
	/**
	 * 地图旋转某个角度，匀速旋转的方式
	 * @author    秦晓宇
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
		animator.setDuration(duration);
		animator.start();
	} 
		
	/**
	 * 匀速移动控件
	 * @author    秦晓宇
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
	 * @author    秦晓宇
	 * @date      2016年4月1日 上午9:38:13
	 * @param x_start
	 * 				-起始点x坐标
	 * @param y_start
	 * 				-起始点y坐标
	 * @param x_end
	 * 				-结束点x坐标
	 * @param y_end
	 * 				-结束点y坐标
	 * @param scrollerDuration
	 * 				-移动的时间
	 */
	public void moveTo(int x_start,int y_start,int x_end,int y_end,int scrollerDuration){  	
		int xoffset = x_end-x_start;
		int yoffset = y_end-y_start;
				
		// 更新结束后，使用动画控制偏移过程
		scroller.startScroll(x_start, y_start, xoffset, yoffset,scrollerDuration);  
		// 重绘控件
		invalidate();   
	} 
	
	
	@Override
	public void moveTo(int x,int y,int travelTimeMs){  
		super.moveTo(x, y, travelTimeMs);
	}
		
	/**
	 * 绘制小车
	 * @author    秦晓宇
	 * @date      2016年4月7日 下午5:35:51 
	 * @param canvas
	 */
	private void drawCars(Canvas canvas)
	{
		int size = cars.size();
		for(int i=0;i<size;i++)
		{
			cars.get(i).draw(canvas);
		}
	}
	private void drawMapPointIndexs(Canvas canvas)
	{
		for(int i=0;i<mapPointIndexs.size();i++)
		{
			mapPointIndexs.get(i).draw(canvas);
		}
	}
	private void drawMapObjects(Canvas canvas)
	{
		for(int i=0;i<mapObjects.size();i++)
		{
			mapObjects.get(i).drawByUser(canvas);
		}
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
	protected void onDraw(Canvas canvas) 
	{
		this.getDrawingRect(drawingRect);

		if (config != null) 
		{
			if (prevGrid != null) 
			{
				prevGrid.draw(canvas, paint, drawingRect);
			}

			if (grid != null) 
			{
				grid.draw(canvas, paint, drawingRect);
			}

			drawLayers(canvas, drawingRect);

			//显示地图上移动的小车
			if(thisCarIcon != null)
			{
				canvas.drawBitmap(thisCarIcon,
					getWidth() + getScrollX() - thisCarIcon.getWidth() - getWidth()/2,
					getHeight() + getScrollY() - thisCarIcon.getHeight() - getHeight()/2,
					paint);			
				
//				matrix.setRotate(xxx, thisCarIcon.getWidth() / 2, thisCarIcon.getHeight() / 2);
//				matrix.setTranslate(xxx, 0);
//				canvas.drawBitmap(thisCarIcon, matrix, paint);
			}
			drawCars(canvas);
			drawMapPointIndexs(canvas);
			drawMapObjects(canvas);
			
		} 
		else 
		{
			debug("scrollTo(0, 0);");
			scrollTo(0, 0);
			drawMissingDataErrorMessage(canvas);
		}
	}
	
	
	/******************** getting & setting **************************/
	public List<Point> getMovePath() {
		return movePath;
	}
	public void setMovePath(List<Point> movePath) {
		this.movePath = movePath;
	}
	/**
	 * 向地图添加一辆小车
	 * @author    秦晓宇
	 * @date      2016年4月7日 下午5:28:00 
	 * @param car
	 */
	public void addCar(AnimationMapObject car)
	{
		cars.add(car);
	}
	public void add(MapPointIndex mapPointIndex){
		mapPointIndexs.add(mapPointIndex);
	}
	public void add(MapLine line)
	{
		mapLines.add(line);
	}
	public void add(MapObject mapObject)
	{
		mapObjects.add(mapObject);
	}
	
	
	public AnimationMapObject getCarById(int id) 
	{
		for(int i=0;i<cars.size();i++)
		{			
			debug("i = "+i);
			int tmpid = cars.get(i).getId();
			if(tmpid == id)
			{
				debug("getCarById("+id+") return "+ i);
				cars.get(i).turnTo(0f, 360f, 5000);
				return cars.get(i);
			}
		}
		debug("getCarById("+id+") return null");
		return null;
	}

	public Handler getHandler() {
		return handler;
	}
	

	
	
}
