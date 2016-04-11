package com.qinxiaoyu.mAppwidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Scroller;

import com.example.offlinemapdemo.R;
import com.ls.widgets.map.MapWidget;



/**
 * @author    ������
 * @date    2016��4��1�� ����9:04:27
 * @describe  ���½�ͨ��ͼ��ͼ�ؼ�  
 */
public class RoadWayMapWidget extends MapWidget{

	  
	/**С��ͼƬ*/
	protected static Bitmap thisCarIcon;
	/**С�����ƶ�·����Ŀǰ�ǵ�·��*/
	protected List<Point> movePath;
	protected Scroller mapMoveScroller;
	private LinearInterpolator linearInterpolator;
	private ArrayList<Car> cars;
	
	Matrix matrix;
	Handler handler;
	
	int xxx;
		
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
		
	
		
//		thisCarIcon = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.car_arror)).getBitmap();
		thisCarIcon = BitmapFactory.decodeResource(getResources(),  R.drawable.car).copy(Bitmap.Config.ARGB_8888, true); 
		
	
		movePath = new ArrayList<Point>();
		linearInterpolator = new LinearInterpolator();
		mapMoveScroller = new Scroller(context, linearInterpolator);
		
		cars = new ArrayList<Car>();
		
//		Canvas canvas = new Canvas(thisCarIcon);
		matrix = new Matrix();
		//��ͼƬ������Ϊ��ת���ģ���ת180��
		
		Paint paint = new Paint();
		//���ÿ����,��ֹ�����ʧ��
		paint.setAntiAlias(true);
//		canvas.drawBitmap(thisCarIcon, matrix, paint);
		
		handler = new Handler()
		{
			public void handleMessage(Message msg) 
			{   
				if(msg.what == 0x1)
				{
//					debug("TimerTask run");
//					xxx+=2;
//					if(xxx>=360) xxx=0;
//					matrix.postTranslate(getWidth() + getScrollX() - thisCarIcon.getWidth() - getWidth()/2, getHeight() + getScrollY() - thisCarIcon.getHeight() - getHeight()/2);
					invalidate();
				}
			}
		};
		
//		new Timer().schedule(new TimerTask(){
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
////				debug("TimerTask run");
//				handler.sendEmptyMessage(0x1);
//
//			}
//		},0,10);
		
	}		

	
	




	/**
	 * ���Է���
	 * @author    ������
	 * @date      2016��4��7�� ����10:28:33 
	 * @param str
	 */
	private void debug(String str){Log.d("RoadWayMapWidget",str);}
	
//	public void onRotate()
//	{
//		iv = (ImageView)findViewById(R.id.image);
//		RotateAnimation animation = new RotateAnimation(0, 360);
//		animation.setDuration(100000);//�趨תһȦ��ʱ��
//		animation.setRepeatCount(Animation.INFINITE);//�趨����ѭ��
//		animation.setRepeatMode(Animation.RESTART);
//		iv.startAnimation(animation);		
//	}
	
	/**
	 * ��ͼ��תĳ���Ƕȣ�������ת�ķ�ʽ
	 * @author    ������
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
		animator.setDuration(duration);
		animator.start();
	} 
		
	/**
	 * �����ƶ��ؼ�
	 * @author    ������
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
	 * @author    ������
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
		// ���½�����ʹ�ö�������ƫ�ƹ���
		scroller.startScroll(x_start, y_start, x_end, y_end,scrollerDuration);  
		// �ػ�ؼ�
		invalidate();   
	} 
		
	/**
	 * ����С��
	 * @author    ������
	 * @date      2016��4��7�� ����5:35:51 
	 * @param canvas
	 * @param point
	 * 			- ��ͼ�Ļ�������С��С�������ڸò��������ػ��Լ�
	 */
	private void drawCars(Canvas canvas,Point point)
	{
		int size = cars.size();
		for(int i=0;i<size;i++)
		{
//			debug(""+i);
			cars.get(i).draw(canvas, point);
		}
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

			//��ʾ��ͼ���ƶ���С��
			if(thisCarIcon != null)
			{
//				canvas.drawBitmap(thisCarIcon,
//					getWidth() + getScrollX() - thisCarIcon.getWidth() - getWidth()/2,
//					getHeight() + getScrollY() - thisCarIcon.getHeight() - getHeight()/2,
//					paint);			
				
//				matrix.setRotate(xxx, thisCarIcon.getWidth() / 2, thisCarIcon.getHeight() / 2);
//				matrix.setTranslate(xxx, 0);
//				canvas.drawBitmap(thisCarIcon, matrix, paint);
			}
			
			
			
			//�������е�С��
			Point point = new Point();
			point.set(getWidth(), getHeight());
			drawCars(canvas,point);
			
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
	 * ���ͼ���һ��С��
	 * @author    ������
	 * @date      2016��4��7�� ����5:28:00 
	 * @param car
	 */
	public void addCar(Car car)
	{
		cars.add(car);
	}
	
	public Car getCarById(int id) 
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
