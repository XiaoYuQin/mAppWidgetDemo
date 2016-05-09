package com.qinxiaoyu.setting.mapPoint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MapLine {
	private float start_x;
	private float start_y;
	private float end_x;
	private float end_y;
	private int id;
	
	public MapLine(int id,float start_x,float start_y,float end_x,float end_y){
		this.id = id;
		this.start_x = start_x;
		this.start_y = start_y;
		this.end_x = end_x;
		this.end_y = end_y;
		
	}
	public void draw(Canvas canvas)
	{				
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(Color.RED);
//		p.setStyle(Paint.Style.STROKE);
		p.setStrokeWidth(2);
		//canvas.drawCircle(x, y, r, p);// ¥Û‘≤
		canvas.drawLine(start_x, start_y, end_x, end_y, p);
	}
}
