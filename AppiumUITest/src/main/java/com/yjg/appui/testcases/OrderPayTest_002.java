package com.yjg.appui.testcases;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.page.OderPayPage;
import com.yjg.appui.testng.TestngListener;
/**
 * @author Neo shi
 * @date 2019/01/18
 * @description 实现了用户自动登录，自动选择商家，自动随机选择商品下单的过程
 *  需要加入，数据驱动，通过用例来驱动代码
 * */
@Listeners({TestngListener.class})//添加监听类
public class OrderPayTest_002 extends CaseBaseTest {
	public static Logger logger = Logger.getLogger(OrderPayTest_002.class);
	public OderPayPage op;
	public AndroidDriverBase driver;
	@BeforeClass
	@Parameters({ "udid", "port" })
	public void beforeClass(String udid, String port) {
		try {
			logger.info("读到的udid是："+udid+"读到的port是："+port);
			//初始化赋值
			op=new OderPayPage(driver);//初始化登录功能封装的方法
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
//		System.out.printf("=============OrderPayTest_001===========Thead Id :%s%n",Thread.currentThread().getId());
		op=new OderPayPage(driver);
		op.guidePageSwipe();//是否存在引导页
		op.orderPay("13533013009","asd123456","123456",2);
	}

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
