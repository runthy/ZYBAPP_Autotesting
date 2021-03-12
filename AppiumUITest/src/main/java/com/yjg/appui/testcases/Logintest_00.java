package com.yjg.appui.testcases;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.page.LoginPage;
import com.yjg.appui.testng.MyAssertion;
import com.yjg.appui.testng.TestngListener;
@Listeners({TestngListener.class})//添加监听类
/**
 * 没有加入数据驱动的测试方法
 * */
public class Logintest_00 extends CaseBaseTest {
	public static Logger logger = Logger.getLogger(Logintest_00.class);
	public LoginPage login;
	public MyAssertion as;
	public AndroidDriverBase driver;
	@BeforeClass
	@Parameters({ "udid", "port" })
	public void beforeClass(String udid, String port) {
		try {
			logger.info("读到的udid是："+udid+"读到的port是："+port);
			//初始化赋值
			login=new LoginPage(driver);//初始化登录功能封装的方法
			as=new MyAssertion(driver);
			driver=driverInit(udid, port);
			driver.implicitlyWait(10);//设置隐形等待
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("执行测试test");
	}
	//测试用例
	@Test
	public void login() throws Exception{
		System.out.printf("=============002begining===========Thead Id :%s%n",Thread.currentThread().getId());
		login=new LoginPage(driver);
//		login.PageCheck("android.widget.TextView");
//		login.sendPicMessage();
//		login.PageCheck("android.widget.TextView");
		login.benJiLogin();
//		login.PageCheck("android.widget.TextView");//android.widget.TextView

//		login.passwordLogin();
//		login.reToLogin("GW00187451", "q123456a");
		//在LoginTest003 添加了输入caseNumber
	}
	//两个用例的衔接？
	@AfterMethod
	public void afterMethod(){
//		driver.resetApp();//重置一次，数据情况，回到最初始的状态
	}
	@AfterClass
	public void quit() throws InterruptedException{
//		driver.quit();
	}
	
}
