package com.yjg.appui.testcases;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.page.LoginPage;
import com.yjg.appui.testng.MyAssertion;
import com.yjg.appui.testng.TestngListener;
@Listeners({TestngListener.class})//添加监听类
public class Logintest_001 extends CaseBaseTest {
	/**
	 * 引入了自定义的参数
	 * 自动断言，但是没有加入期望断言
	 * 能执行成功，但是问题只能参考截图，应该加入异常的输出
	 * 增加了				Assert.fail("Failed to login  [" + "没有登录成功" + "]");//失败了就直接调用监听类，后面方法都不执行
	//函数能自动结合监听类监控执行结果
	 * */
	public static Logger logger = Logger.getLogger(Logintest_001.class);
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
		logger.info("执行测试test");
	}

	/**1读取自定义数据*/
	/**
	 * 读取自己定义的数据的方法的方法
	 * */
	@DataProvider
	public Object[][] getLoginData1(){
		Object[][] data={
//				{"13533013009","asd123456"},
				{"shizerun","asd123456"},
				{"sfsdf!@#$%^&*","23434"},
				{"我是中文","239473"}
				};
		return data;
	}

	//测试用例
	@Test(dataProvider="getLoginData")
	public void login(String username,String password,String caseNumber) throws Exception{
//		System.out.printf("=============001begining===========Thead Id :%s%n",Thread.currentThread().getId());
		login=new LoginPage(driver);
		login.login5(username,password,caseNumber);//是否存在引导页
//		login.reToLogin(username, password,caseNumber);//没有数据的时候使用
		//在LoginTest003 添加了输入caseNumber
	}
	/**1读取自定义数据*/

	//两个用例的衔接？
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
