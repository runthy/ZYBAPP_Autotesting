package com.yjg.appui.base;

import com.yjg.appui.util.ProUtil;

public class CrazyPath {
	/**
	 * author:neo.shi 
	 * 配置文件的路径
	 * */
	public static String globalPath="configs//global.properties";   //appium的服务地址
	public static String capsPath="configs//caps.properties";  //相关的Android caps参数
	public static String elementPath="configs//element.properties";  //操作的元素的值
	public static String path=System.getProperty("user.dir");//获取当前目录的路径
	
	
	public static void main(String[] args) {
		System.out.println(path); //E:\WorkPace\ScriptWorkpace\AppiumWorkPace\AppiumTest\CrazyAppium

	}
}
