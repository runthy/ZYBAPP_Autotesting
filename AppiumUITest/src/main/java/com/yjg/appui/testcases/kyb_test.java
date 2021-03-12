package com.yjg.appui.testcases;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.page.WeilikankanPage;
import com.yjg.appui.page.kybPage;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

/**
 * 考研班测试用例
 */
public class kyb_test extends CaseBaseTest {
	public static Logger logger = Logger.getLogger(kyb_test.class);
	public kybPage op;
	public AndroidDriverBase driver;
	@BeforeClass
	@Parameters({ "udid", "port" })
	public void beforeClass(String udid, String port) {
		try {
			logger.info("读到的udid是："+udid+"读到的port是："+port);
			//初始化赋值
			op=new kybPage(driver);//初始化登录功能封装的方法
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
	public void orderPay() throws Exception{
		op=new kybPage(driver);
		op.login();
//		op.browserVideo();
		//op.browserNews();
	}

	@AfterMethod
	public void afterMethod(){
//		driver.resetApp();//重置一次，数据情况，回到最初始的状态
	}
	@AfterClass
	public void quit(){
		driver.quit();
	}


}
