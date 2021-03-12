package com.yjg.othertest;

import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.yjg.appui.base.AndroidDriverBase;
/**
 *  
 * 测试 调用DriverInit类，启动某个app，调用DriverInit类的driverInit方法
 * */
public class StartAppiumServerTest extends DriverInit{
	AndroidDriverBase driver;
	@Parameters({"udid","port"})
	@BeforeClass
	public void beforeClass(String udid,String port) throws Exception{
		try {
			driver=driverInit(udid, port);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("study 1 driver is "+driver);
	}
	@AfterClass
	public void afterClass(){
		System.out.println("study 1 quit");
		driver.quit();
	}
	@Test(priority=2)
	public void Test() throws InterruptedException{
		System.out.println("driver 1 is "+driver);
	}
}
