package com.qinxiaoyu.mAppwidget.configuration;

import com.qinxiaoyu.lib.android.SdCard;

public class ConfigFilePath {

	
	/**
	 * ��ͼ�������ļ�·��
	 * */
	static private String MapPointConfigFilePath = SdCard.getSdcardPath()+"/offlineMap/config/MapPointsConfig.json";

	/******************** getting & setting **************************/
	static public String getMapPointConfigFilePath() {return MapPointConfigFilePath;}
	static public void setMapPointConfigFilePath(String mapPointConfigFilePath) {MapPointConfigFilePath = mapPointConfigFilePath;}
	
	
}
