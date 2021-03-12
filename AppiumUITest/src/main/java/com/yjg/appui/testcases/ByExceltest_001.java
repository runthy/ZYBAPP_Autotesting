package com.yjg.appui.testcases;

import com.yjg.appui.keyworddriver.Constants;
import com.yjg.appui.keyworddriver.ExcelUtil;
import com.yjg.appui.keyworddriver.KeyWordsAction;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import org.testng.annotations.*;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.base.AndroidSpecific;
import com.yjg.appui.base.CrazyPath;
import com.yjg.appui.page.OderPayPage;
import com.yjg.appui.testcases.CaseBaseTest;
import com.yjg.appui.testng.TestngListener;
import com.yjg.appui.util.ProUtil;

@Listeners({TestngListener.class})//添加监听类
public class ByExceltest_001 extends CaseBaseTest {
    public static Method method[];//数组， 存KeyWordsAction类中，所有的测试方法。存在此数组中
	public static String keyword;//关键字后面的三列
	public static String locatorExpression;//操作的配置文件字段
	public static String value;//操作的值
	public static KeyWordsAction keyWordsaction;
	public static int testStep;
	public static int testLastStep;//最后一步
	public static String testCaseID;
	public static String testCaseRunFlag;//是否执行的标识
	public static boolean testResult;
	public AndroidDriverBase driver;

	/**
	 * @throws Exception
	 */
	@Test
	public void testTestSuite() throws Exception {
		//翻译excel表的数据
		// 声明一个关键动作类的实例
		keyWordsaction = new KeyWordsAction();
		// 使用 java 的反射机制获取 KeyWordsaction 类的所有方法对象
		method = keyWordsaction.getClass().getMethods();
		// 定义 excel 关键文件的路径
		String excelFilePath = Constants.Path_ExcelFile;
		// 设定读取 excel 
		ExcelUtil.setExcelFile(excelFilePath);
		// 读取“测试用例集合” sheet 中的测试用例总数
		int testCasesCount = ExcelUtil.getRowCount(Constants.Sheet_TestSuite);//测试用例所有的行
		// 使用 for 循环，执行所有标记为“y”的测试用例
		for (int testCaseNo = 1; testCaseNo <= testCasesCount; testCaseNo++) {//int testCaseNo = 1d第一行的表头，
			// 读取“测试用例集合” sheet 中每行的测试用例序号
			testCaseID = ExcelUtil.getCellData(Constants.Sheet_TestSuite,
			testCaseNo, Constants.Col_TestCaseID);//第几行，第几列
			System.out.println(testCaseID);
			// 读取“测试用例集合” sheet 中每行的是否运行列中的值
			testCaseRunFlag = ExcelUtil.getCellData(Constants.Sheet_TestSuite,
			testCaseNo, Constants.Col_RunFlag);//是否等于y或者n
			// 如果是否运行列中的值为“y”,则执行测试用例中的所有步骤
			if (testCaseRunFlag.equalsIgnoreCase("y")) {

				// 设定测试用例的当前结果为true，即表明测试执行成功
				testResult = true;
				// 在“知乎”sheet 中，获取当前要执行测试用例的第一个步骤所在行行号
				testStep = ExcelUtil.getFirstRowContainsTestCaseID(//用例要执行的起始行的行号
						Constants.Sheet_TestSteps, testCaseID,
						Constants.Col_TestCaseID);
				//在“知乎”sheet 中，获取当前要执行测试用例的最后一个步骤所在行行号
				testLastStep = ExcelUtil.getTestCaseLastStepRow(//测试行结束的行号
						Constants.Sheet_TestSteps, testCaseID, testStep);
				// 遍历测试用例中的所有测试步骤
				for (; testStep < testLastStep; testStep++) {//遍历以上拿到的行，完成登录的操作
					// 从“知乎” sheet 中读取关键字和操作值，并调用 execute_Actions 方法执行
					keyword = ExcelUtil.getCellData(Constants.Sheet_TestSteps,//关键字
					testStep, Constants.Col_KeyWordAction);
					// 在日志文件中打印关键字信息
					System.out.println("从excel 文件读取到的关键字是：" + keyword);
					//locatorExpression 定位表达式
					locatorExpression=ExcelUtil.getCellData(Constants.Sheet_TestSteps,testStep,Constants.Col_LocatorExpression);
					//value 要操作执行的值
					value = ExcelUtil.getCellData(Constants.Sheet_TestSteps,
					testStep, Constants.Col_ActionValue);
					// 在日志文件中打印操作值信息
					System.out.println("从 excel 文件中读取的操作值是：" + value);
					execute_Actions();//调用执行方法，执行每一步，再判断testResult
			
					if (testResult == false) {//整个用例执行如何
						/*
						 * 如果测试用例的任何一个测试步骤执行失败，则测试用例集合 sheet 
                             * 中的当前执行测试用例的执行结果设定为“测试执行失败”
						 */
						ExcelUtil.setCellData("测试用例集合", testCaseNo,
						Constants.Col_TestSuiteTestResult, "测试执行失败");

						/*
						 * 当前测试用例出现执行失败的步骤，则整个测试用例设定为失败状态，
						 *  break语句跳出当前的 for 循环，继续执行测试集合中的下一个测试
                             * 用例
						 */
						break;
					}

					if (testResult == true) {
						/*
						 * 如果测试用例的所有步骤执行成功，则会在测试用例集合 sheet 中的当						 * 前执行测试用例
						 * 的执行结果设定为“测试执行成功”
						 */
						ExcelUtil.setCellData(Constants.Sheet_TestSuite, testCaseNo,
						Constants.Col_TestSuiteTestResult, "测试执行成功");
						
					}
					
					
				
				}

			}
		}
	}

	/**
	 * 
	 */
	private static void execute_Actions() {
		try {

			for (int i = 0; i < method.length; i++) {//遍历keyWorkAction中所有的测试方法
				/*
				 * 使用反射的方式，找到关键字对应的测试方法，并使用 value （操作值） 作为测          
                  	 * 试方法的函数值进行调用
			  	 */

				if (method[i].getName().equals(keyword)) {//找和关键字对应的方法，执行某个方法
					method[i].invoke(keyWordsaction, locatorExpression,value);//执行这个方法
					if (testResult == true) {
						/* 当前测试步骤执行成功，在 “知乎”sheet 中，会将当前执行的测
                             *  试步骤结果设定为“测试步骤执行成功”
					      */
						ExcelUtil.setCellData(Constants.Sheet_TestSteps,
								testStep, Constants.Col_TestStepTestResult,"测试步骤执行成功");//回写excel的步骤
						break;
					} else {
						/* 当前测试步骤执行失败，在 “知乎”sheet 中，会将当前执行的测
                           *  试步骤结果设定为“测试步骤执行失败”
						 */
						ExcelUtil.setCellData(Constants.Sheet_TestSteps,
								testStep, Constants.Col_TestStepTestResult,"测试步骤 执行失败");

						break;
					}
					
				}
			}
		} catch (Exception e) {
			// 调用测试方法过程中，若出现异常，则将测试设定为失败状态，停止测试用例执行
			Assert.fail("执行出现异常，测试用例执行失败！");
		}
	}

//	@BeforeClass
//	public void BeforeClass() throws Exception {//
//		ProUtil p = new ProUtil(CrazyPath.globalPath);
//		String server=p.getPro("server");
//		String capsPath=CrazyPath.capsPath;
//		AndroidSpecific as=new AndroidSpecific();
//		String input=as.getDefaultInput("172.18.10.42:5555");
//		//System.out.println("连接"+udid+"端口"+port);
//		keyWordsaction.driver= new AndroidDriverBase(server, "4723", capsPath, "172.18.10.42:5555", input);
//		keyWordsaction.driver.implicitlyWait(10);
//	}
	
	
	/**
	 * @param udid
	 * @param port
	 * @throws Exception
	 */
	@BeforeClass
	@Parameters({ "udid", "port" })
	public void BeforeClass(String udid,String port) throws Exception {
		System.out.println("连接"+udid+"端口"+port);
		CaseBaseTest cb=new CaseBaseTest();
		keyWordsaction.driver=cb.driverInit(udid, port);
		keyWordsaction.driver.implicitlyWait(10);
		
	}
	
	
//	@BeforeClass
//	@Parameters({ "udid", "port" })
//	public void beforeClass(String udid, String port) {
//		try {
////			logger.info("读到的udid是："+udid+"读到的port是："+port);
//			//初始化赋值
////			OderPayPage1 op=new OderPayPage1(driver);
////			op=new OderPayPage1(driver);//初始化登录功能封装的方法
//			CaseBaseTest cb=new CaseBaseTest();
//
//			driver=cb.driverInit(udid, port);
//			driver.implicitlyWait(10);//设置隐形等待
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	@AfterClass
	public void afterClass(){
		keyWordsaction.quit();
	}
}
