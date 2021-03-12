package com.yjg.appui.util;



public class FilePath {
	/**
	 * author:neo.shi 
	 * 配置文件的路径 其中 mac 系统下是// 斜杆的， win系统是 \\
	 * */
//	public static String globalPath="configs//global.properties";
//	public static String capsPath="configs/caps.properties";
//	public static String elementPath="configs/element.properties";
	public static String path=System.getProperty("user.dir");//获取当前目录的路径
	public static WinOrMac wm =new WinOrMac();

	/**
	 * @return
	 */
	public static String globalPath(){
		String globalPath="configs"+wm.doubleBackslant("global.properties")+"global.properties";   //appium的服务地址
//		System.out.println(globalPath);
		return globalPath;
	}

	/**
	 * @return
	 */
	public static String capsPath(){
		String capsPath="configs"+wm.doubleBackslant("caps.properties")+"caps.properties";  //相关的Android caps参数
//		System.out.println(capsPath);

		return capsPath;
	}

	/**
	 * @return
	 */
	public static String elementPath(){
		
		String elementPath="configs"+wm.doubleBackslant("element.properties")+"element.properties";
//		System.out.println(elementPath);
		return elementPath;
	}

	/**
	 * @abstracttion 根据不同系统自动返回不同类型的双斜杆符号
	 * @return
	 */
//	public  static String doubleBackslant (){
//		if(osName.toLowerCase().contains("mac")){//判断当前的系统，用相关的命令
//			String macxiegan="//";   //appium的服务地址
//			System.out.println("【"+"当前操作系统为：" + osName + "】"+" 使用的文件连接符号是：//" );
//			return macxiegan;
//		}else if (osName.toLowerCase().contains("win")){
//			String winxiegan="\\";   //appium的服务地址
//			System.out.println("【"+"当前操作系统为：" + osName + "】"+" 使用的文件连接符号是：\\" );
//			return winxiegan;
//		}else {
//			return null;
//		}
//	}
	public static void main(String[] args) {
//		System.out.println(path); //E:\WorkPace\ScriptWorkpace\AppiumWorkPace\AppiumTest\CrazyAppium
//		String globalPath="configs"+wm.doubleBackslant("global.properties")+"global.properties";
		System.out.print(globalPath()+capsPath()+elementPath());
	}
}
