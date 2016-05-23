package com.example.offlinemapdemo;

import java.util.Timer;
import java.util.TimerTask;

import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.events.MapTouchedEvent;
import com.ls.widgets.map.interfaces.OnMapTouchListener;
import com.ls.widgets.map.model.MapLayer;
import com.ls.widgets.map.model.MapObject;
import com.ls.widgets.map.utils.PivotFactory;
import com.ls.widgets.map.utils.PivotFactory.PivotPosition;
import com.qinxiaoyu.mAppwidget.AnimationMapObject;
import com.qinxiaoyu.mAppwidget.OtherCar;
import com.qinxiaoyu.mAppwidget.RoadWayMapWidget;
import com.qinxiaoyu.mAppwidget.data.FileMapPoints;
import com.qinxiaoyu.mAppwidget.data.MapPoint;
import com.qinxiaoyu.mAppwidget.data.MapPoints;
import com.qinxiaoyu.setting.mapPoint.MapPointIndex;
import com.qinxiaoyu.lib.android.SdCard;
import com.qinxiaoyu.lib.transmit.net.udp.UdpClient;
import com.qinxiaoyu.lib.util.file.File;













import com.qinxiaoyu.lib.util.format.string.json.Json;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	
	
	Handler handler;
	FileMapPoints fileMapPoints;
	int[] d;
	int[] save;
	int xasdfasdf = 0;
	
	int mapPointInedxNum = 0;
	
	
	int mapPointRunIndex = 0;
	
	private void debug(String str){
		Log.d("MainActivity",str);
	}
	  
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
		setContentView(R.layout.activity_main); 
		

		
		
//		LinearLayout objectLayout = (LinearLayout) findViewById(R.id.objectLayout);		
//		final Button btn1 = new Button(this);
//		btn1.setText("1");
//		btn1.setText("Button1");
//		   ViewGroup.LayoutParams  lp = btn1.getLayoutParams();
//	        lp.width =100;
//	        lp.height =100; 
//	        btn1.setLayoutParams(lp);
//		layout.addView(btn1);
		
//		 LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 100);	 
//			// 设置包裹内容或者填充父窗体大小
//			 LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
//					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);	
			//设置padding值
			// btn1.setPadding(10, 10, 10, 10);
			//设置margin值
			// lp.setMargins(20, 20, 0, 20);
//			 layout.addView(btn1,lp);
		
 
		final RoadWayMapWidget map = new RoadWayMapWidget(this, "map",15);
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);		
		map.getConfig().setZoomBtnsVisible(false);
		layout.addView(map);
		
		
		TextView tv = new TextView(this);
		tv.setText("Hello World");
		layout.addView ( tv );
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
//		ImageView imageView = new ImageView(this);  
//		imageView.setImageResource(R.drawable.ic_launcher);  
//		layout.addView(imageView);
//		layout.bringChildToFront(imageView);
//		.bringToFront();
		
//		thisCarIcon = 
//		BitmapDrawable draw = new BitmapDrawable(((BitmapDrawable)getResources().getDrawable(R.drawable.car_arror)).getBitmap();); 
//		ImageView imageView = new ImageView(this); 
//		imageView.setImageDrawable(draw);
		
//		ImageView imageView = new ImageView(this);
//		imageView.setImageDrawable(getResources().getDrawable(R.drawable.car));
		
		
//		map.rotationTo(0f,30f,3000);
//		map.moveTo(0,0,500,0,5000);
//		map.moveTo(0,0,500,0,5000);



		

		/**********************************添加一个物体到地图上**********************************************/
//        final long LAYER_ID = 5;
//        MapLayer layer = map.createLayer(LAYER_ID);    
//		 // getting icon from assets
//        Drawable icon = getResources().getDrawable(R.drawable.car_arror);
//        
//        // define coordinates of icon on map
//
//        // set ID for the object
//        final long OBJ_ID = 25;
//        
//        // adding object to layer
//        final MapObject obj = new MapObject(OBJ_ID, icon, new Point(300, 300), PivotFactory.createPivotPoint(icon, PivotPosition.PIVOT_CENTER), true, false);
//        layer.addMapObject(obj);
        
        
        
//        obj.setRotation(360, 3000);
//        obj.setMove(new Point(700, 500), 10000);
        
        
        /**********************************添加一个物体自动移动上**********************************************/
//		 final long LAYER_ID = 5;
//		 final MapLayer layer = map.createLayer(LAYER_ID);    
		
		
        map.setOnMapTouchListener(new OnMapTouchListener() {
        	@Override
        	public void onTouch(MapWidget arg0, MapTouchedEvent arg1) {
        		// TODO Auto-generated method stub
//					AlertDialog.Builder builder = new AlertDialog.Builder(arg0.getContext());
//					builder.setMessage("OnTouch, X: " + arg1.getScreenX() + " Y: " + arg1.getScreenY() 
//							+ " MAPX: " + arg1.getMapX() + " MAPY: " + arg1.getMapY() +
//							" Touched Count: " + arg1.getTouchedObjectIds().size());
//					builder.create().show();
//					obj.moveTo(arg1.getMapX(), arg1.getMapY());
//        		map.moveTo(arg1.getMapX(), arg1.getMapY(),1000);
//        		int[] position = new int[2];  
//        		map.getLocationOnScreen(position);
//        		debug("moveTo now"+"  x = "+position[0]+"  y = "+position[1]);
//        		Drawable icon = getResources().getDrawable(R.drawable.map_index);        		
        		debug("getMap x = "+arg1.getMapX()+" y = "+arg1.getMapY());
//        		MapObject mapObject = new MapObject(mapPointInedxNum,icon , arg1.getMapX(), arg1.getMapY(),true);        		
//        		layer.addMapObject(mapObject);
        		final int x = arg1.getMapX();
        		final int y = arg1.getMapY();
        		MapPointIndex mapPointIndex = new MapPointIndex(mapPointInedxNum,arg1.getMapX(),arg1.getMapY(), 5);
        		map.add(mapPointIndex);        		
        		mapPointInedxNum ++;
//        		new Thread(new ).start();
        		new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						UdpClient.send("192.168.0.102", 8000, x+","+y+"\r\n");
					}
				}).start();
        		
        	}
        });

		

		
//        /**********************************地图按path移动**********************************************/
//        new Thread(new Runnable(){
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(true)
//				{
//					Message msg = new Message();
//					msg.what = 1;
//					Bundle bundle = new Bundle();
//					d[0] += 100;
//
//					bundle.putIntArray("point", d);
//					msg.setData(bundle);
//					handler.sendMessage(msg);
//					
//					try {
//						Thread.sleep(2000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//        	        	
//        }).start();
        
          
//    	final OtherCar myCar = new OtherCar(getApplicationContext(),1,getResources().getDrawable(R.drawable.car_arror),new Point(100,100));
//    	AnimationMapObject myCar1 = new AnimationMapObject(getApplicationContext(),1,getResources().getDrawable(R.drawable.car_arror),new Point(450,100));
//    	AnimationMapObject myCar2 = new AnimationMapObject(getApplicationContext(),1,getResources().getDrawable(R.drawable.car_arror),new Point(200,200));
//    	AnimationMapObject myCar3 = new AnimationMapObject(getApplicationContext(),1,getResources().getDrawable(R.drawable.car_arror),new Point(700,700));
//    	myCar.setWarringCircleVisable(true);
//    	map.addCar(myCar);
//    	map.addCar(myCar1);
//    	map.addCar(myCar2);
//    	map.addCar(myCar3);
        
        /*其它车辆移动*/
//        OtherCar myCar = new OtherCar(getApplicationContext(),1,getResources().getDrawable(R.drawable.car_arror),new Point(0,0)); 
//        OtherCar myCar1 = new OtherCar(getApplicationContext(),1,getResources().getDrawable(R.drawable.car_arror),new Point(100,0));
//        myCar.setWarringCircleVisable(true);
//        map.addCar(myCar);
//        map.addCar(myCar1);
        
        
        
//    	WarringCircle w1 = new WarringCircle();
//    	map.add(w1);

//    	new Timer().schedule(new TimerTask(){
//
//    	    @Override
//    	    public void run() {
//    	        // TODO Auto-generated method stub
//    	    	debug("run timer ");
//    	    	debug("地图点数量为："+fileMapPoints.getMapPoints().getPointsNumber());
//    	    	if(xasdfasdf>fileMapPoints.getMapPoints().getPointsNumber())
//    	    	{
//    	    		debug("xasdfasdf = "+xasdfasdf+" > "+fileMapPoints.getMapPoints().getPointsNumber());
//    	    	}
//    	    	else
//    	    	{
//    	    		Point movePoint = new Point();
//    	    		debug("point = "+fileMapPoints.getMapPoints().getPointByID("0000"+xasdfasdf).getPoint());
//    	    		if(fileMapPoints.getMapPoints().getPointByID("0000"+xasdfasdf).getPoint()!=null)
//    	    		{
//    	    			debug("movePoint = "+movePoint);
//    	    			
//    	    		}
//    	    		else 
//    	    		{
//						debug("movePoint = null"); 
//					}
//    	    		if(movePoint != null)
//    	    		{    	    			
//    	    			myCar.setMove(movePoint, 1000);
//    	    		}
//    	    		xasdfasdf +=1;
//    	    	}    	    	
//    	    }
//    	},0,1000);
    	
		
		/**********************************模拟生成json地图点文件**********************************************/
//		MapPoints mapPoints = new MapPoints();  
//		mapPoints.setPoint("0001", new MapPoint(1,1));
//		mapPoints.setPoint("0002", new MapPoint(2,2));
//		mapPoints.setPoint("0003", new MapPoint(3,3));
//		mapPoints.setPoint("0004", new MapPoint(4,4));
//		   
//		debug("conver to json"); 
//		String jsonString = Json.toJsonByPretty(mapPoints);
//		debug(jsonString);
//
//		if(SdCard.checkSDcardStatus() == 0)  
//		{
//			String sdPath = SdCard.getSdcardPath();
//			String[] files = new String[10];
//			files[0] = sdPath+"/offlineMap";
//			files[1] = sdPath+"/offlineMap/config";			
//			
//			File.createFiles(files);
//			File.write(sdPath+"/offlineMap/config/MapPointsConfig.json",jsonString,false);
//		}
		/**********************************地图自动移动**********************************************/
//		map.moveTo(1000, 100,15000);
		handler = new Handler()
		{
			public void handleMessage(Message msg) 
			{   
				switch (msg.what) 
				{   
					case 1:
						//debug("save x = "+save[0]+"y = "+save[1]+"   x = "+d[0]+" y ="+d[1]);
						Bundle bundle = new Bundle();
						bundle = msg.getData();
						int x = bundle.getInt("x");
						int y = bundle.getInt("y");
						debug("x="+x+"y="+y);
	        	    	map.moveTo(x, y, 1000);    
					break;
				}   
				super.hand！leMessage(msg);   
			}   
		};
        
        
		/****************************读取json地图点文件获取车辆在地图上的点*************************************/
		fileMapPoints = new FileMapPoints();		
		if(fileMapPoints.init())
		{
			debug("fileMapPoints 初始化成功");
			debug("标签点数量："+fileMapPoints.getMapPoints().getPointsNumber());
		}
		/*创建一个地图物体*/
//		Drawable icon = getResources().getDrawable(R.drawable.map_index);        
//		final AnimationMapObject mapObject = new AnimationMapObject(getApplicationContext(),123,icon, new Point(0,0));     
//		
//		map.addCar(mapObject);
		
        /****************************以地图坐标点为依据，移动车辆*************************************/       
    	new Timer().schedule(new TimerTask(){

    	    @Override
    	    public void run() {
    	        // TODO Auto-generated method stub
//    	    	debug("TimerTask run");
    	    	if(mapPointRunIndex<fileMapPoints.getMapPoints().getPointsNumber())
    	    	{
 
        	    	int x = fileMapPoints.getMapPoints().getPointByID("B"+mapPointRunIndex).getX();
        	    	int y = fileMapPoints.getMapPoints().getPointByID("B"+mapPointRunIndex).getY();        	    	
        	    	mapPointRunIndex++;     
       	    		debug("地图标志物移动到"+"B"+mapPointRunIndex+"("+x+","+y+")");
       	    		Message msg = new Message();
					msg.what = 1;
					Bundle bundle = new Bundle();
					bundle.putInt("x", x);
					bundle.putInt("y", y);
					msg.setData(bundle);
					handler.sendMessage(msg);
					
    	    	}
    	    }
    	},0,1000);

        
//    	myCar.setMove(new Point(1500, 500), 10000);
//    	myCar.setScale(0.5f);
//    	myCar.setRotation(-360, 3000);
//    	myCar1.setRotation(360, 3000);
//    	myCar2.setRotation(-360, 3000);
//    	myCar3.setRotation(360, 3000);
    	 
    	
    	
    	
    	
    	
//    	debug("11111");
//    	if(map.getCarById(1)!=null)
//    	{
////    		map.getCarById(1).turnTo(0f, 360f, 5000);
//    		;
//    	}
    	
//		ImageView imageView = new ImageView(this);
//		imageView.setImageDrawable(getResources().getDrawable(R.drawable.car));
    	
	}

//public class ViewGroup01 extends ViewGroup{
//
//	public ViewGroup01(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//}

}
