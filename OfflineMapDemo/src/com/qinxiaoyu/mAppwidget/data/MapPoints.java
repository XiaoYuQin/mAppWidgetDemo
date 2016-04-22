package com.qinxiaoyu.mAppwidget.data;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;



/**
 * 将json文件的数据转换为 特定格式的 电子标签和地图位置。
 * @author    秦晓宇
 * @date      2016年4月13日 上午10:53:07 
 */
public class MapPoints {

	private Map<String,MapPoint> mapPoints;
	
	@SuppressWarnings("unused")
	private void debug(String str)
	{
		Log.i("MapPoints",str);
	}
	
	/**
	 * 打开保存在内存中的标签数据文件，获得所有标签数据集。
	 * @param path
	 *
	 * time 2016年4月13日上午10:54:55
	 */
	public MapPoints()
	{	
		mapPoints = new HashMap<String,MapPoint>();	
	}
	

	public MapPoint getPointByID(String id)
	{
		MapPoint mapPoint = null;
		mapPoint = mapPoints.get(id);
		return mapPoint;
	}
	public void setPoint(String id,MapPoint point)
	{
		mapPoints.put(id, point);
	}
	
}
