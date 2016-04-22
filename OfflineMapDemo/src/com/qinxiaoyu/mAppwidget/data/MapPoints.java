package com.qinxiaoyu.mAppwidget.data;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;



/**
 * ��json�ļ�������ת��Ϊ �ض���ʽ�� ���ӱ�ǩ�͵�ͼλ�á�
 * @author    ������
 * @date      2016��4��13�� ����10:53:07 
 */
public class MapPoints {

	private Map<String,MapPoint> mapPoints;
	
	@SuppressWarnings("unused")
	private void debug(String str)
	{
		Log.i("MapPoints",str);
	}
	
	/**
	 * �򿪱������ڴ��еı�ǩ�����ļ���������б�ǩ���ݼ���
	 * @param path
	 *
	 * time 2016��4��13������10:54:55
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
