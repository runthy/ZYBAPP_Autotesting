package com.yjg.othertest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.base.AndroidSpecific;
import com.yjg.appui.base.CrazyPath;
import com.yjg.appui.util.ProUtil;

public class DriverInit {
	private AndroidDriverBase driver=null;
	//AndroidDriverBase 尝试调用AndroidDriverBase 的构造方法 ，最简单的构造
	public void driverStart() throws Exception{
		String udid="127.0.0.1:5557";
		String server="http://127.0.0.1";
		String port="4723";
		String input="xxxx";
		String capsPath=CrazyPath.capsPath;//文件的路径
		
		AndroidDriverBase dr=new AndroidDriverBase(server, port, capsPath, udid, input);
	}
	
	public  AndroidDriverBase driverInit(String udid,String port) throws Exception{
		/**
		 * public static String globalPath="configs\\global.properties";
		public static String capsPath="configs\\caps.properties";
		public static String elementPath="configs\\element.properties";
		public static String path=System.getProperty("user.dir");
		 * */
		ProUtil p = new ProUtil(CrazyPath.globalPath); //读取globalPathd路径的文件
		String server=p.getPro("server");//globalPath路径的文件中，读取server的值
		System.out.println(server);
		String capsPath=CrazyPath.capsPath; //读取"configs\\caps.properties";读取caps.properties文件的路劲
		AndroidSpecific as=new AndroidSpecific();
		String input=as.getDefaultInput(udid);//用adb命令获取当前设备的输入法：io.appium.android.ime/.UnicodeIME
		System.out.println("连接"+udid+"端口"+port+",input:"+input);
		driver=new AndroidDriverBase(server, port, capsPath, udid, input);
		//AndroidDriver dr=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4490/wd/hub"), new DesiredCapabilities());
		//隐式等待10秒中
		driver.implicitlyWait(10);
		//driver.resetApp();
		
		return driver;
	}
	public  AndroidDriverBase driverInit(String udid,String port,int waitFlag) throws Exception{
		ProUtil p = new ProUtil(CrazyPath.globalPath);
		String server=p.getPro("server");
		System.out.println(server);
		String capsPath=CrazyPath.capsPath;
		AndroidSpecific as=new AndroidSpecific();
		String input=as.getDefaultInput(udid);
		System.out.println("连接"+udid+"端口"+port+",input:"+input);
		driver=new AndroidDriverBase(server, port, capsPath, udid, input,waitFlag);
		//AndroidDriver dr=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4490/wd/hub"), new DesiredCapabilities());
		//隐式等待10秒中
		driver.implicitlyWait(10);
		//driver.resetApp();
		
		return driver;
	}
//    private static class SingletonHolder{
//    	
//        private static AndroidDriverBase  instance = DriverInit(udid,port);
//    }
	protected
	 AndroidDriverBase getDriver(String udid,String port) throws Exception{
//		String udid="192.168.56.101:5555";
//		String port="4990";
		synchronized(DriverInit.class){
            if(driver == null){
            driver = driverInit(udid,port);
            }
		return driver;

		}
	}
}
