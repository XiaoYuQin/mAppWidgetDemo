package com.example.offlinemapdemo;

import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.events.MapTouchedEvent;
import com.ls.widgets.map.interfaces.OnMapTouchListener;
import com.ls.widgets.map.model.MapLayer;
import com.ls.widgets.map.model.MapObject;
import com.ls.widgets.map.utils.PivotFactory;
import com.ls.widgets.map.utils.PivotFactory.PivotPosition;
import com.qinxiaoyu.mAppwidget.AnimationMapObject;
import com.qinxiaoyu.mAppwidget.Car;
import com.qinxiaoyu.mAppwidget.RoadWayMapWidget;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	
	
	
	Handler handler;
	int[] d;
	int[] save;
	
	private void debug(String str){
		Log.d("MainActivity",str);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		
		
		d = new int[2];
		d[0] = 100;
		d[1] = 100;
		save = new int[2];
		final RoadWayMapWidget map = new RoadWayMapWidget(this, "map",15);
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);		
		map.getConfig().setZoomBtnsVisible(false);
		layout.addView(map);
		
//		thisCarIcon = 
//		BitmapDrawable draw = new BitmapDrawable(((BitmapDrawable)getResources().getDrawable(R.drawable.car_arror)).getBitmap();); 
//		ImageView imageView = new ImageView(this); 
//		imageView.setImageDrawable(draw);
		
//		ImageView imageView = new ImageView(this);
//		imageView.setImageDrawable(getResources().getDrawable(R.drawable.car));
		
		
//		map.rotationTo(0f,30f,3000);
//		map.moveTo(0,0,500,0,5000);
		/**********************************地图自动移动**********************************************/
//		map.moveTo(100, 200,4000);
		handler = new Handler()
		{
			public void handleMessage(Message msg) 
			{   
				switch (msg.what) 
				{   
					case 1:
//						Log.i("MainActivity","save x = "+save[0]);
						debug("save x = "+save[0]+"y = "+save[1]+"   x = "+d[0]+" y ="+d[1]);
						map.moveTo(save[0], save[1], 100, 0, 2000);
						
						
						
						save[0] = d[0];
						save[1] = d[1];
					break;
				}   
				super.handleMessage(msg);   
			}   
		};


		

		/**********************************添加一个物体到地图上**********************************************/
        final long LAYER_ID = 5;
        MapLayer layer = map.createLayer(LAYER_ID);    
		 // getting icon from assets
        Drawable icon = getResources().getDrawable(R.drawable.map_icon_attractions);
        
        // define coordinates of icon on map
        int x = 200;
        int y = 300;
        
        // set ID for the object
        final long OBJ_ID = 25;
        
        // adding object to layer
        final AnimationMapObject obj = new AnimationMapObject(OBJ_ID, icon, new Point(x, y), PivotFactory.createPivotPoint(icon, PivotPosition.PIVOT_CENTER), true, false);
        layer.addMapObject(obj);
        obj.setRotation(360, 0);
//        obj.moveTo(1000, 1000);
        
        /**********************************添加一个物体自动移动上**********************************************/
        map.setOnMapTouchListener(new OnMapTouchListener() {
        	
        	@SuppressWarnings("deprecation")
        	@Override
        	public void onTouch(MapWidget arg0, MapTouchedEvent arg1) {
        		// TODO Auto-generated method stub
//					AlertDialog.Builder builder = new AlertDialog.Builder(arg0.getContext());
//					builder.setMessage("OnTouch, X: " + arg1.getScreenX() + " Y: " + arg1.getScreenY() 
//							+ " MAPX: " + arg1.getMapX() + " MAPY: " + arg1.getMapY() +
//							" Touched Count: " + arg1.getTouchedObjectIds().size());
//					builder.create().show();
//					obj.moveTo(arg1.getMapX(), arg1.getMapY());
        		map.moveTo(arg1.getMapX(), arg1.getMapY(),1000);
        		int[] position = new int[2];  
        		map.getLocationOnScreen(position);
        		debug("moveTo now"+"  x = "+position[0]+"  y = "+position[1]);
        	}
        });
        
        /**********************************地图按path移动**********************************************/
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
        
        
    	Car myCar = new Car(getApplicationContext(),1,getResources().getDrawable(R.drawable.car_arror),map.getHandler());
    	map.addCar(myCar);
    	
//    	debug("11111");
//    	if(map.getCarById(1)!=null)
//    	{
////    		map.getCarById(1).turnTo(0f, 360f, 5000);
//    		;
//    	}
    	
//		ImageView imageView = new ImageView(this);
//		imageView.setImageDrawable(getResources().getDrawable(R.drawable.car));
    	
	}



}
