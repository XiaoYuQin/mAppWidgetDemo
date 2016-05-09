package com.qinxiaoyu.setting.mapPoint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MapPointIndex{

	private float x;
	private float y;
	private float r;
	private int id;
	
	public MapPointIndex(int id,float x,float y,float r){
		this.id = id;
		this.x = x;
		this.y = y;
		this.r = r;
	}
	public void draw(Canvas canvas)
	{				
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(Color.BLUE);
//		p.setStyle(Paint.Style.STROKE);
		p.setStrokeWidth(2);
		canvas.drawCircle(x, y, r, p);// ¥Û‘≤
		p.setColor(Color.BLACK);
		p.setStrokeWidth(4);
		canvas.drawText(""+id, x-r, y+2*r, p);
		canvas.drawText(""+(int)x+": "+(int)y, x-r, y+3*r, p);
	}
	
}
