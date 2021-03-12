package com.yjg.othertest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;


public class StartApp {
	/**
	 * @author Administrator
	 * @data 20190122
	 * @description 这是简单的启动类，简单测试使用
	 * @param apkPath
	 * */
	
	public static AndroidDriver<AndroidElement> initDriver(String apkPath) throws Exception{
		//定义读取apk的路径方法
		File app=new  File(apkPath);
		DesiredCapabilities caps=new DesiredCapabilities();
		//DEVICE_NAME
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "shadouxing");
		//app参数是指定要安装的测试apk的路径
		caps.setCapability(MobileCapabilityType.APP,app.getAbsoluteFile());
		/**
		 * 以下两项参数是结合使用，第一个unicodeKeyBoard是使用appium自带的输入法，可以隐藏键盘并支持中文输入
		 * resetKeyBoard是执行完测试后将设备的输入法重置回原有的输入法
		 * */
		caps.setCapability("unicodeKeyboard", true);
		caps.setCapability("resetKeyboard", true);
		//不对app进行重签名，因为有的app在重签名之后无法使用
		caps.setCapability("noSign", true);
		//设置session的超时时间
		caps.setCapability("newCommandTimeout", 600);
		//指定执行引擎，默认是Appium，4.2以下设置需指定为Selendroid
		//caps.setCapability("automationName","Selendroid"); 
		//执行手机web测试时，浏览器的名称，在做app测试时这项参数可以不写
		//caps.setCapability("browserName","Chrome");
		//caps.setCapability("udid", "192.168.56.101:5555");
		
		AndroidDriver<AndroidElement> driver
		     =new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
		return driver;
	}
	public static AndroidDriver<AndroidElement> initDriver(String udid,String appPackage,String appActivity) throws Exception{
		DesiredCapabilities caps=new DesiredCapabilities();
		//设备名称，是安卓端必须项，但是值可以选择性写
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "shadouxing");
		//要启动的应用包名
		caps.setCapability("appPackage", appPackage);
		//要启动的应用的起始activity
		caps.setCapability("appActivity", appActivity);
		/**
		 * 以下两项参数是结合使用，第一个unicodeKeyBoard是使用appium自带的输入法，可以隐藏键盘并支持中文输入
		 * resetKeyBoard是执行完测试后将设备的输入法重置回原有的输入法
		 * */
		caps.setCapability("unicodeKeyboard", true);
		caps.setCapability("resetKeyboard", true);
		//不对app进行重签名，因为有的app在重签名之后无法使用
		caps.setCapability("noSign", true);
		//设置session的超时时间
		caps.setCapability("newCommandTimeout", 600);
		//指定执行引擎，默认是Appium，4.2以下设置需指定为Selendroid
		//caps.setCapability("automationName","Selendroid"); 
		//执行手机web测试时，浏览器的名称，在做app测试时这项参数可以不写
		//caps.setCapability("browserName","Chrome");
		caps.setCapability("udid", udid);
		caps.setCapability("deviceReadyTimeout",60);
		AndroidDriver<AndroidElement> driver
	     		=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
		Thread.sleep(5000);
		return driver;
	}
	public static AndroidDriver<AndroidElement> initDriverBySelendroid(String apkPath) throws Exception{
		File app=new  File(apkPath);
		DesiredCapabilities caps=new DesiredCapabilities();
		//deivcename:shadouxing,app:C:\\Users\\lixionggang\\Desktop\\zhihu.apk
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "shadouxing");
		//app参数是指定要安装的测试apk的路径
		caps.setCapability(MobileCapabilityType.APP,app.getAbsoluteFile());
		//以下两项参数是结合使用，第一个unicodeKeyBoard是使用appium自带的输入法，可以隐藏键盘并支持中文输入
		//resetKeyBoard是执行完测试后将设备的输入法重置回原有的输入法
		caps.setCapability("unicodeKeyboard", true);
		caps.setCapability("resetKeyboard", true);
		//不对app进行重签名，因为有的app在重签名之后无法使用
		caps.setCapability("noSign", true);
		//设置session的超时时间
		caps.setCapability("newCommandTimeout", 600);
		//指定执行引擎，默认是Appium，4.2以下设置需指定为Selendroid
		caps.setCapability("automationName","Selendroid"); 
		caps.setCapability("udid", "127.0.0.1:5555");
		
		AndroidDriver<AndroidElement> driver
		     =new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
		return driver;
		//driver.quit();
	}
	public static AndroidDriver<AndroidElement> initDriverByBrowser() throws Exception{
		//File app=new  File(apkPath);
		DesiredCapabilities caps=new DesiredCapabilities();
		//deivcename:shadouxing,app:C:\\Users\\lixionggang\\Desktop\\zhihu.apk
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "shadouxing");
		//app参数是指定要安装的测试apk的路径
		//caps.setCapability(MobileCapabilityType.APP,app.getAbsoluteFile());
		//以下两项参数是结合使用，第一个unicodeKeyBoard是使用appium自带的输入法，可以隐藏键盘并支持中文输入
		//resetKeyBoard是执行完测试后将设备的输入法重置回原有的输入法
		caps.setCapability("unicodeKeyboard", true);
		caps.setCapability("resetKeyboard", true);
		//不对app进行重签名，因为有的app在重签名之后无法使用
		caps.setCapability("noSign", true);
		//设置session的超时时间
		caps.setCapability("newCommandTimeout", 600);
		//执行手机web测试时，浏览器的名称，在做app测试时这项参数可以不写
		caps.setCapability("browserName","Chrome");
		caps.setCapability("udid", "620e9a26");
		AndroidDriver<AndroidElement> driver
		     =new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
		return driver;
		//driver.quit();
	}


	public static void main(String[] args) throws Exception {
		// 只是启动服务
//		initDriver("com.gatewang.yjg","com.gatewang.yjg.ui.activity.LauncherActivity");
		//启动服务，并传递可操作的AndroidDriver对象
		AndroidDriver<AndroidElement> driver=StartApp.initDriver("127.0.0.1:5555","com.jf.lkrj","com.jf.lkrj.SplashActivity");
		Thread.sleep(3000);
		driver.findElement(By.name("我的")).click();
//		driver.quit();

	}

}
