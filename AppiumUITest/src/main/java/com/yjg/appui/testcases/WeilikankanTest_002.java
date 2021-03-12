package com.yjg.appui.testcases;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.page.WeilikankanPage;
public class WeilikankanTest_002 extends CaseBaseTest {
	public static Logger logger = Logger.getLogger(WeilikankanTest_002.class);
	public WeilikankanPage op;
	public AndroidDriverBase driver;
	@BeforeClass
	@Parameters({ "udid", "port" })
	public void beforeClass(String udid, String port) {
		try {
			logger.info("读到的udid是："+udid+"读到的port是："+port);
			//初始化赋值
			op=new WeilikankanPage(driver);//初始化登录功能封装的方法
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
		System.out.printf("=============WeilikankanTest_001===========Thead Id :%s%n",Thread.currentThread().getId());
		op=new WeilikankanPage(driver);
		op.browserVideo();
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
