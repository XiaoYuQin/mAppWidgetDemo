package com.qinxiaoyu.mAppwidget.data;

import android.graphics.Point;

public class MapPoint {

	private String id;
	private Point point;
	
	public MapPoint(String id,Point ps)
	{
		this.point = ps;
		this.id = id;		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}

}
