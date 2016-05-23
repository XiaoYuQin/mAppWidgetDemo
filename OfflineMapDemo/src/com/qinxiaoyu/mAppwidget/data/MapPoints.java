package com.qinxiaoyu.mAppwidget.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qinxiaoyu.lib.util.format.list.LibArrayList;
import com.qinxiaoyu.lib.util.format.map.LibLinkedHashMap;

import android.util.Log;



/**
 * 将json文件的数据转换为 特定格式的 电子标签和地图位置。
 * @author    秦晓宇
 * @date      2016年4月13日 上午10:53:07 
 */
public class MapPoints {

	private LinkedHashMap<String,MapPoint> mapPoints;
		
	
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
		mapPoints = new LinkedHashMap<String,MapPoint>();	
	}
	
	public void debugAll()
	{
		ArrayList<Object> rfIDStrings = new ArrayList<Object>();
		rfIDStrings = LibLinkedHashMap.getkey(mapPoints);
		for(int i=0;i<rfIDStrings.size();i++)
		{
			debug("卡 "+i+" = "+(String)rfIDStrings.get(i));
		}
	}
	/**
	 * 根据标签的编号获得标签的具体参数
	 * @author    秦晓宇
	 * @date      2016年4月28日 上午11:03:01 
	 * @param id
	 * @return	
	 */
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
	/**
	 * 获取地图的标签点的数量
	 * @author    秦晓宇
	 * @date      2016年4月28日 上午11:22:00 
	 * @return
	 */
	public int getPointsNumber()
	{
		return mapPoints.size();
	}
	
}
