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
 * ��json�ļ�������ת��Ϊ �ض���ʽ�� ���ӱ�ǩ�͵�ͼλ�á�
 * @author    ������
 * @date      2016��4��13�� ����10:53:07 
 */
public class MapPoints {

	private LinkedHashMap<String,MapPoint> mapPoints;
		
	
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
		mapPoints = new LinkedHashMap<String,MapPoint>();	
	}
	
	public void debugAll()
	{
		ArrayList<Object> rfIDStrings = new ArrayList<Object>();
		rfIDStrings = LibLinkedHashMap.getkey(mapPoints);
		for(int i=0;i<rfIDStrings.size();i++)
		{
			debug("�� "+i+" = "+(String)rfIDStrings.get(i));
		}
	}
	/**
	 * ���ݱ�ǩ�ı�Ż�ñ�ǩ�ľ������
	 * @author    ������
	 * @date      2016��4��28�� ����11:03:01 
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
	 * ��ȡ��ͼ�ı�ǩ�������
	 * @author    ������
	 * @date      2016��4��28�� ����11:22:00 
	 * @return
	 */
	public int getPointsNumber()
	{
		return mapPoints.size();
	}
	
}
