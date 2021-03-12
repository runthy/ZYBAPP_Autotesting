package com.yjg.appui.testcases;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.keyworddriver.ExcelUtil1;
import com.yjg.appui.page.LoginPage;
import com.yjg.appui.page.kybPage;
import com.yjg.appui.testng.MyAssertion;
import com.yjg.appui.testng.TestngListener;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import java.io.IOException;

@Listeners({TestngListener.class})//添加监听类
public class kytest_002 extends CaseBaseTest {
	/***
	 * @author Neo.Shi
	 * @date 2019/01/16
	 * 加入了excel读取数据的数据驱动
	 * 问题：对excel表中的y和n需要注意判断条件  如果是y才执行，如果n不执行
	 * 这里采用重新登录的方法进行测试，因为存在登录过的情况，如果登录过，注销重新登录
	 * */
	public static Logger logger = Logger.getLogger(kytest_002.class);
	public MyAssertion as;
	public AndroidDriverBase driver;
	public kybPage op;

	@BeforeClass
	@Parameters({ "udid", "port" })
	public void beforeClass(String udid, String port) {
		try {
			logger.info("读到的udid是："+udid+"读到的port是："+port);
			//初始化赋值
			op=new kybPage(driver);//初始化登录功能封装的方法
			as=new MyAssertion(driver);
			driver=driverInit(udid, port);
			driver.implicitlyWait(5);//设置隐形等待
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("設備完畢啟動。。。");
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
			logger.info("测试用例文件讀取完畢");
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return data;
	}
	/**
	 * 	 * 里面的格式是格式是这样的{{"1","登录成功","13533013009","asd123456","登录","y"}}
	 * */
	@Test(dataProvider="getLoginData")
	public void login(String caseNumber,String caseName,String username,String password,String str) throws Exception{
		logger.info("執行测试");
		op=new kybPage(driver);
		op.login5(username,password,caseNumber);
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
