package com.qinxiaoyu.mAppwidget.data;

import android.graphics.Point;

public class MapPoint {

	
	private int x;
	private int y;
	
	public MapPoint(int x,int y)
	{
		this.x = x;
		this.y = y;		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
