package com.yjg.appui.testcases;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.page.WeilikankanPage;
import com.yjg.appui.testng.MyAssertion;

public class WeilikankanTest_001 extends CaseBaseTest {
	public static Logger logger = Logger.getLogger(WeilikankanTest_001.class);
	public WeilikankanPage qttp;
	public MyAssertion as;
	public AndroidDriverBase driver;
	String udid="emulator-5554";
	String port="4723";
	@BeforeClass
//	@Parameters({ "udid", "port" })
	public void beforeClass() {
		try {
			logger.info("读到的udid是："+udid+"读到的port是："+port);
			//初始化赋值
			qttp=new WeilikankanPage(driver);//初始化登录功能封装的方法
			driver=driverInit(udid, port);
			driver.implicitlyWait(10);//设置隐形等待
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("执行测试test");
	}
	/**
	 * 读取自己定义的数据的方法的方法
	 * */

	//测试用例
	@Test
	public void login() throws Exception{
		System.out.printf("=============WeilikankanTest_001===========Thead Id :%s%n",Thread.currentThread().getId());
		qttp=new WeilikankanPage(driver);//初始化登录功能封装的方法
		//qttp.explorer();
		qttp.browserNews();
		
	

	}
	//两个用例的衔接？
	@AfterMethod
	public void afterMethod(){
//		driver.resetApp();//重置一次，数据情况，回到最初始的状态
	}
	@AfterClass
	public void quit() throws InterruptedException{
		driver.resetInput();
		driver.quit();
	}
	
}
