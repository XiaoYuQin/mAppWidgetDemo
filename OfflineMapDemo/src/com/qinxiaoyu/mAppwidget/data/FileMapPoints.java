package com.qinxiaoyu.mAppwidget.data;

import android.graphics.Point;

import com.qinxiaoyu.lib.android.SdCard;
import com.qinxiaoyu.lib.util.file.File;
import com.qinxiaoyu.lib.util.format.string.json.Json;
import com.qinxiaoyu.mAppwidget.configuration.ConfigFilePath;

/**
 * ���ã�
 * 1. ��д�����ļ�����õ�ͼ·����
 * 2. �����ǩ���룬��ѯ�ڵ�ͼ�ϵ����λ��
 * @author    ������
 * @date      2016��4��18�� ����11:28:46 
 */
public class FileMapPoints{

//	enum STATUS{
//		CONFIG_SUCCESS,
//		CONFIG_FAIL
//	};
	
	private static MapPoints mapPoints ;
//	private boolean status;
	
	/**�ڿ��������е�һ����������Ҫ�������ɵ�ͼ�����*/
	private int developRFID;
	
	public FileMapPoints()
	{
		mapPoints = new MapPoints();
		//��ʼ����ʱ���ȶ�ȡ�����ļ����ݣ�Ȼ����������ļ�
	}
	public boolean init()
	{
		//1.��ȡ�����ļ���ת��Ϊ��ͼ��
		//ת���ɹ��򷵻�true
		return converJsonToMapPoints();
	}
	
	/**
	 * ����ͼ��ǩ����ת��Ϊ��ǩ����json�ַ���
	 * @author    ������
	 * @date      2016��4��13�� ����11:06:22 
	 * @return
	 * 			- ��ǩ����ת���ɵ��ַ���
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
	 * ����ͼ�ı�ǩ���ļ�ת��Ϊ��ͼ�ı�ǩ���ݡ�
	 * @author    ������
	 * @date      2016��4��13�� ����11:05:16 
	 * @param json
	 * 			- ��ǩ�����ļ��ַ���
	 * @return ת��������ɹ�����true��ʧ�ܷ���false 
	 */
	public boolean converJsonToMapPoints()
	{		
		if(SdCard.checkSDcardStatus() == 0)  
		{			
			String jonsString = File.read(ConfigFilePath.getMapPointConfigFilePath());
			mapPoints = (MapPoints) Json.toObject(mapPoints, jonsString);
			return true;
		}
		return false;
	}
	
	
	public void setPoint(Point point)
	{
		MapPoint mapPoint = new MapPoint(""+developRFID, point);
		mapPoints.setPoint(""+developRFID, mapPoint);
	}
	
	
	/************************** getters & setters **************************/
	public MapPoints getMapPoints() {
		return mapPoints;
	}
	
	public MapPoint getPointByRDID(String rfid)
	{
		return mapPoints.getPointByID(rfid);
	}

}