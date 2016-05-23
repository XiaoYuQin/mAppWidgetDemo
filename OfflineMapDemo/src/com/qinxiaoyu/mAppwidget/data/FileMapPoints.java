package com.qinxiaoyu.mAppwidget.data;

import android.graphics.Point;
import android.util.Log;

import com.qinxiaoyu.lib.android.SdCard;
import com.qinxiaoyu.lib.util.file.File;
import com.qinxiaoyu.lib.util.format.string.json.Json;
import com.qinxiaoyu.mAppwidget.configuration.ConfigFilePath;

/**
 * 作用：
 * 1. 读写配置文件，获得地图路径点
 * 2. 输入标签号码，查询在地图上的相对位置
 * @author    秦晓宇
 * @date      2016年4月18日 上午11:28:46 
 */
public class FileMapPoints{

//	enum STATUS{
//		CONFIG_SUCCESS,
//		CONFIG_FAIL
//	};
	
	private static MapPoints mapPoints ;
//	private boolean status;
	
	/**在开发过程中的一个变量，主要用于生成地图坐标点*/
	private int developRFID;
	
	
	private void debug(String str){Log.d("FileMapPoints",str);}
	
	public FileMapPoints()
	{
		mapPoints = new MapPoints();
		//初始化的时候先读取配置文件内容，然后解析配置文件
	}
	public boolean init()
	{
		//1.读取配置文件并转换为地图点
		//转换成功则返回true
		return converJsonToMapPoints();
	}
	
	/**
	 * 将地图标签数据转换为标签配置json字符串
	 * @author    秦晓宇
	 * @date      2016年4月13日 上午11:06:22 
	 * @return
	 * 			- 标签数据转换成的字符串
	 */
	public boolean converMapPointsToJson()
	{
		String json = Json.toJsonByPretty(mapPoints);
		if(SdCard.checkSDcardStatus() == 0)  
		{			
			File.write(ConfigFilePath.getMapPointConfigFilePath(),json,false);
			return true;
		}
		return false;
	}

	/**
	 * 将地图的标签点文件转换为地图的标签数据。
	 * @author    秦晓宇
	 * @date      2016年4月13日 上午11:05:16 
	 * @param json
	 * 			- 标签配置文件字符串
	 * @return 转换结果，成功返回true，失败返回false 
	 */
	public boolean converJsonToMapPoints()
	{		
		if(SdCard.checkSDcardStatus() == 0)  
		{			
			String jonsString = File.read(ConfigFilePath.getMapPointConfigFilePath());
			mapPoints =  (MapPoints) Json.toObject(mapPoints, jonsString);
			mapPoints.debugAll();
			debug("标签点配置文件转换标签数据成功");
			debug("读取标签数量："+mapPoints.getPointsNumber());
			return true;
		}
		debug("标签点配置文件转换标签数据失败");
		return false;
	}
	
	
	public void setPoint(Point point)
	{
		MapPoint mapPoint = new MapPoint(point.x, point.y);
		mapPoints.setPoint(""+developRFID, mapPoint);
	}
	
	
	/************************** getters & setters **************************/
	/**
	 * 获得所有的地图点
	 * @author    秦晓宇
	 * @date      2016年4月28日 上午11:02:09 
	 * @return	  -地图上标定的所有点
	 */
	public MapPoints getMapPoints() {
		return mapPoints;
	}
	
	public MapPoint getPointByRDID(String rfid)
	{
		return mapPoints.getPointByID(rfid);
	}

}
