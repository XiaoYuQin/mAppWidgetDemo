package com.example.offlinemapdemo;

import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.events.MapTouchedEvent;
import com.ls.widgets.map.interfaces.OnMapTouchListener;
import com.ls.widgets.map.model.MapLayer;
import com.ls.widgets.map.model.MapObject;
import com.ls.widgets.map.utils.PivotFactory;
import com.ls.widgets.map.utils.PivotFactory.PivotPosition;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	
	private void debug(String str){
		Log.d("MainActivity",str);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		
		
		final MapWidget map = new MapWidget(this, "map23",15);
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);		
		map.getConfig().setZoomBtnsVisible(false);
		layout.addView(map);
		
		
		/**********************************地图自动移动**********************************************/
		map.moveTo(100, 200,4000);
	

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
        final MapObject obj = new MapObject(OBJ_ID, icon, new Point(x, y), PivotFactory.createPivotPoint(icon, PivotPosition.PIVOT_CENTER), true, false);
        layer.addMapObject(obj);
        
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
        
        
        
	}



}
