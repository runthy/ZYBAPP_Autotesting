package com.yjg.appui.keyworddriver;

import com.yjg.appui.testcases.ByExceltest_001;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.page.BasePage;
import com.yjg.appui.util.GetByLocator;

/**
 * @author neo shi
 * @date 2019/01/18
 * @description 这里的方法命和关键字是对应的excel表中操作元素定位表达式对应的是配置文件的 此方法的名称对应 excel
 *              文件中“关键字”列中的 input 关键字
 */
public class KeyWordsAction {
	public static Logger logger = Logger.getLogger(KeyWordsAction.class);
	// 声明静态 AndroidDriverBase 对象，用于在此类中相关 driver 的操作
	public static AndroidDriverBase driver;

	/**
	 * @param inputValue
	 *            参数 inputValue文件中“操作值”列中的字符作为输入框的输入内容
	 * @param locator
	 *            参数 locator 表示输入框的定位表达式，然后自动读取配置文件进行获取元素信息
	 * @description 封装输入的方法
	 * */
	public void input(String byElement, String key) {
		try {
			driver.findElement(GetByLocator.getLocator(byElement))
					.sendKeys(key);
		} catch (Exception e) {
			ByExceltest_001.testResult = false;
//			e.printStackTrace();
			logger.error("输入 [" + key + "] 到 元素[" + byElement + "]失败");
			Assert.fail("输入 [" + key + "] 到 元素[" + byElement + "]失败");
		}
		logger.info("输入：[" + key + "] 到 [" + byElement + "]");
	}

	/*
	 * 此方法名称对应 excel 文件中“关键字”列中的 click 关键字， 实现点击操作，参数locator 代表被点击元素的定位表达式， 参数
	 * String为无实际值传入的参数，仅为了通过反射机制统一地使用两个 函数参数来调用此函数。
	 */

	public void click(String byElement, String string) {

		try {
			clickTheClickable(GetByLocator.getLocator(byElement),
					System.currentTimeMillis(), 2500);
		} catch (StaleElementReferenceException e) {
			logger.error("The element you clicked:[" + byElement
					+ "] is no longer exist!");
			Assert.fail("The element you clicked:[" + byElement
					+ "] is no longer exist!");
			ByExceltest_001.testResult = false;
		} catch (Exception e) {
			logger.error("Failed to click element [" + byElement + "]");
			Assert.fail("Failed to click element [" + byElement + "]", e);
			ByExceltest_001.testResult = false;
		}
		logger.info("点击元素 [" + byElement + "]");
	}

	/** 不能点击时候重试点击操作 */
	private void clickTheClickable(By byElement, long startTime, int timeOut)
			throws Exception {
		try {
			driver.findElement(byElement).click();
		} catch (Exception e) {
			if (System.currentTimeMillis() - startTime > timeOut) {
				logger.warn(byElement + " is unclickable");
				throw new Exception(e);
			} else {
				Thread.sleep(500);
				logger.warn(byElement + " is unclickable, try again");
				clickTheClickable(byElement, startTime, timeOut);
			}
		}
	}

	/**
	 * 通过坐标点击元素，对应关键字clickByCoordinate
	 * 
	 * @param x
	 * @param y
	 *            locator 第一个这是占位，没有意义 这里的输入形式是500=300来进行分割
	 */
	public static void clickByCoordinate(String locator, String coordinate) {
		int x = 0;
		int y = 0;
		if (coordinate.contains("=")) {
			x = Integer.valueOf(coordinate.split("=")[0]);
			y = Integer.valueOf(coordinate.split("=")[1]);
			try {
				driver.tap(1, Integer.valueOf(x), Integer.valueOf(y), 100);
			} catch (Exception e) {
				ByExceltest_001.testResult = false;
//				e.printStackTrace();
			}
		} else {
			System.out.println("坐标无效");
		}

	}

	/**
	 * 此方法的名称对应 excel 文件中“关键字”列中的 WaitFor_Element 关键字， 用于显示等待页面元素出现在页面中。函数读取
	 * excel 文件中“操作值”列中的表达 式作为函数参数，objectMap 对象的getLocator 方法会根据函数参数值在配置 文件中查找
	 * key 值对应的定位表达式。参数 locatorExpression 表示等待出现 页面元素的定位表达式，参数
	 * String为无实际值传入的参数，仅为了通过反射机 *制统一地使用两个函数参数来调用此函数。
	 **/
	public static void WaitFor_Element(String locator, String timeout) {
		try {
			// 调用封装的 isElementExist 函数显示等待页面元素是否出现
			driver.isElementExist(GetByLocator.getLocator(locator),
					Integer.valueOf(timeout));
		} catch (Exception e) {
			ByExceltest_001.testResult = false;
//			e.printStackTrace();
		}
	}

	/*
	 * 此方法的名称对应 excel 文件中“关键字”列中的 sleep 关键字用于等待操作， 暂停几秒，函数参数是以毫秒为单位的等待时间。参数
	 * sleepTime 表示暂停 的毫秒数，参数 String为无实际值传入的参数，仅为了通过反射机制统一地使用 两个函数参数来调用此函数。
	 */
	public static void sleep(String string, String sleepTime) {
		try {
			driver.wait(Integer.valueOf(sleepTime));
		} catch (Exception e) {
			ByExceltest_001.testResult = false;
//			e.printStackTrace();
		}
	}

	/* 此方法的名称对应 excel 文件中“关键字”列中的 Assert_String 关键字，参数 assertString
	 * 为要断言的字符串内容，参数 string 为无实际值传入的参数，仅为了通过反射机制统一地使用两
	 * 个函数参数来调用此函数。
	 */
	
	public static void  Assert_String(String string,String assertString)  {
		try{ 
			 Assert.assertTrue(driver.getPageSource().contains(assertString));
		} catch (AssertionError e) {
			ByExceltest_001.testResult = false;
			Assert.fail("Failed to Assert_String  [" + "没有正常跳转结果页面" + "]");

		}
	}

	/**
	 * 捕获断言的异常
	 * */
	public static boolean myAssertTrue(boolean actual) {
		boolean flag = false;
		try {
			Assert.assertTrue(actual);
		} catch (AssertionError e) {
			flag = false;
			System.out.println("》》》》》》"+flag);
			return flag;
		}
		return flag;
	}

	/**
	 * 滑动屏幕
	 * 
	 * @param string
	 * @param direction
	 *            只是屏幕的滑动，输入时间即可
	 */
	public void swipeScreen(String string, String direction) {
		try {
			driver.swipe(direction, 500);
		} catch (Exception e) {
			// TODO: handle exception
			ByExceltest_001.testResult = false;
			e.printStackTrace();
		}
	}

	/**
	 * 在某元素上滑动
	 * 
	 * @param locator
	 * @param direction
	 */
	public static void swipeOnElement(String locator, String direction) {
		try {
			driver.swipeOnElement(GetByLocator.getLocator(locator), direction,
					500);
		} catch (Exception e) {
			// TODO: handle exception
			ByExceltest_001.testResult = false;
			e.printStackTrace();
		}
	}

	/**
	 * 长按某元素
	 * 
	 * @param locator
	 * @param direction
	 */
	public static void longPress(String locator, String direction) {
		try {
			driver.longPress(GetByLocator.getLocator(locator));
		} catch (Exception e) {
			// TODO: handle exception
			ByExceltest_001.testResult = false;
			e.printStackTrace();
		}
	}

	/**
	 * 坐标点长按
	 * 
	 * @param x
	 * @param y
	 *            还是用等号=进行分割
	 */
	public static void longPressByCoordinate(String string, String coordinate) {
		int x = 0;
		int y = 0;
		if (coordinate.contains("=")) {
			x = Integer.valueOf(coordinate.split("=")[0]);
			y = Integer.valueOf(coordinate.split("=")[1]);
			try {
				driver.longPress(x, y);
			} catch (Exception e) {
				ByExceltest_001.testResult = false;
				e.printStackTrace();
			}
		} else {
			System.out.println("坐标无效");
		}
	}

	/**
	 * 手势密码锁 locator 手势密码的大框 pwd 输入的密码 1,2,3,4
	 * **/
	public static void wakeByGestures(String locator, String pwd) {
		BasePage bp = new BasePage(driver);
		AndroidElement element = driver.findElement(GetByLocator
				.getLocator(locator));
		String[] indexsStr = pwd.split(",");// string数组
		int[] indexs = new int[indexsStr.length];// 转化成数字数字
		for (int i = 0; i < indexsStr.length; i++) {
			indexs[i] = Integer.valueOf(indexsStr[i]);
		}
		bp.wakeByGestures(element, indexs);// 执行手势密码的方法
	}

	/**
	 * 退出driver
	 */
	public static void quit() {
		driver.quit();
	}

}
