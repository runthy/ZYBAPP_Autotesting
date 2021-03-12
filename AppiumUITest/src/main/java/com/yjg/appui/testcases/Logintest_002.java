package com.yjg.appui.testcases;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.keyworddriver.ExcelUtil1;
import com.yjg.appui.page.LoginPage;
import com.yjg.appui.testng.MyAssertion;
import com.yjg.appui.testng.TestngListener;
@Listeners({TestngListener.class})//添加监听类
public class Logintest_002 extends CaseBaseTest {
	/***
	 * @author Neo.Shi
	 * @date 2019/01/16
	 * 加入了excel读取数据的数据驱动
	 * 问题：对excel表中的y和n需要注意判断条件  如果是y才执行，如果n不执行
	 * 这里采用重新登录的方法进行测试，因为存在登录过的情况，如果登录过，注销重新登录
	 * */
	public static Logger logger = Logger.getLogger(Logintest_002.class);
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
			driver.implicitlyWait(5);//设置隐形等待
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("执行测试。。。");
	}
	/**
	 * 读取excel表的方法
	 * 里面的格式是格式是这样的{{"1","登录成功","13533013009","asd123456","登录","y"}}
	 * */
	@DataProvider
	public Object[][] getLoginData(){
		Object[][] data=null;
		try {
			data=ExcelUtil1.getTestData("configs/test.xlsx", "Sheet1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return data;
	}
	
	@Test(dataProvider="getLoginData")
	public void login(String caseNumber,String caseName,String username,String password,String str) throws Exception{
//		System.out.printf("=============001begining===========Thead Id :%s%n",Thread.currentThread().getId());
		login=new LoginPage(driver);
		login.guidePageSwipe();//是否存在引导页
		login.reToLogin(username, password,caseNumber);//没有数据的时候使用

	}
	//两个用例的衔接,重置app
	@AfterMethod
	public void afterMethod(){
//		driver.resetApp();//重置一次，数据情况，回到最初始的状态
	}
	@AfterClass
	public void quit() throws InterruptedException{
//		driver.resetInput();
		driver.quit();
	}
	
}
