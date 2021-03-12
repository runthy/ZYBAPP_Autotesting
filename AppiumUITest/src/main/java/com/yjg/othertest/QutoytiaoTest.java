package com.yjg.othertest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;

public class QutoytiaoTest {
	/**
	 * @author Administrator
	 * @data 20190122
	 * @description 这是简单的测试类，简单测试使用
	 * 
	 * */
	public static void main(String[] args) throws Exception {
		AndroidDriver<AndroidElement> driver=StartApp.initDriver("127.0.0.1:5557","com.jifen.qukan","com.jifen.qkbase.main.MainActivity");
//		AndroidDriver<AndroidElement> driver=StartApp.initDriver("");//某个需要安装的路径
		Thread.sleep(3000);
//		driver.findElement(By.name("我的")).click();
//		driver.quit();
	}
}
