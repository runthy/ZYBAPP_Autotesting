package com.yjg.appui.page;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.server.Servers;
import com.yjg.appui.util.ProUtil;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

public class BasePage {
	public static Logger logger = Logger.getLogger(BasePage.class);
	public String curActivity;//每个界面都有activity，
	public String pageSource;//每个界面都是一样page界面元素的整体
	public static AndroidDriverBase driver;//AndroidDriverBase 定义这个
	public static String path=System.getProperty("user.dir");
	public BasePage(AndroidDriverBase driver){//定义 一个构造方法，对于driver进行初始化
		this.driver=driver;//逻辑要懂？
	}

	//?
	public String getPageSource(){
		return driver.getPageSouce();
	}
	
	/***
	 * @author Neo.shi
	 * @throws InterruptedException 
	 * @Abstract 异常检测
	 * 
	 * */

//	public boolean toastCheck() throws InterruptedException {
//		driver.sleep(4000);
//		boolean flag = false;// 定义没有提示框
////		if (this.isElementExist(this.reAndroidBy("id",
////				"com.gatewang.yjg:id/loading"),20) == true) {
//		if (this.isElementExist(this.reAndroidElement("id","com.gatewang.yjg:id/loading")) == true) {
//			//com.gatewang.yjg:id/content_text 网络异常的text
//			logger.info("提示框内容: ["
//					+ this.getText(this.reAndroidBy("id",
//							"com.gatewang.yjg:id/content_text")) + "]");
//			//点击确定，元素是否存在如果存在就点击，不存在就不点击
//			this.click(this.reAndroidBy("name", "确定"));
//			flag = true;
//			return flag;
//		} else if (this.isElementExist(this.reAndroidBy("id",
//				"com.gatewang.yjg:id/CardView_2"),10) == true) {
//			logger.info("提示框内容: ["
//					+ this.getText(this.reAndroidBy("id",
//							"com.gatewang.yjg:id/msg")) + "]");
//			//点击确定，元素是否存在如果存在就点击，不存在就不点击
//			this.click(this.reAndroidBy("name", "确定"));
//			flag = true;
//			return flag;
//		} else {
//			logger.info("没有捕获异常信息，请看log信息.");
//			return flag;
//		}
//	}
	/***
	 * 异常框获取
	 * */
//	public boolean toastCheck() throws InterruptedException {
//		driver.sleep(4000);
//		boolean flag = false;// 定义没有提示框
//		try {
////			if (this.isElementExist(this.reAndroidBy("id",
////				"com.gatewang.yjg:id/loading"),20) == true) {
//			if (this.isElementExist(this.reAndroidElement("id","com.gatewang.yjg:id/loading")) == true) {
//				//com.gatewang.yjg:id/content_text 网络异常的text
//				logger.info("提示框内容: ["
//						+ this.getText(this.reAndroidBy("id",
//								"com.gatewang.yjg:id/content_text")) + "]");
//				//点击确定，元素是否存在如果存在就点击，不存在就不点击
//				this.click(this.reAndroidBy("name", "确定"));
//				flag = true;
//				return flag;
//			} else if (this.isElementExist(this.reAndroidBy("id",
//					"com.gatewang.yjg:id/CardView_2"),10) == true) {
//				logger.info("提示框内容: ["
//						+ this.getText(this.reAndroidBy("id",
//								"com.gatewang.yjg:id/msg")) + "]");
//				//点击确定，元素是否存在如果存在就点击，不存在就不点击
//				this.click(this.reAndroidBy("name", "确定"));
//				flag = true;
//				return flag;
//			}else{
//				logger.info("没有捕获异常信息，请看log信息.");
//			}
//		}catch (Exception e) {
//			// TODO: handle exception
//			logger.info("没有找到提示框元素");
//		}
//		return flag;
//
//	}
	
	
	// 截图封装 简化版本
	public static void takescreen(String fileName) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String mDateTime = formatter.format(new Date());

		driver.takeScreen(path, "/images/"
				+ mDateTime+ fileName + ".png");
	}
	/**
	 * @abstract 截图封装 完成版本
	 * @param  tr 当前运行的类对象
	 * */
	public  void saveScreenShot(ITestResult tr) {
		//获取当前系统的年月日时分秒
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String mDateTime = formatter.format(new Date());
		//拼接文件命名
		String fileName = mDateTime + "_"+tr.getName();
		String filePath = "";
		try {
			// 这里可以调用不同框架的截图功能
			File screenshot = driver.getScreenshotAs(OutputType.FILE);
			//文件的存放路径
			filePath = "result/screenshot/"+ fileName + ".jpg";
			//实例化file类操作文件
			File destFile = new File(filePath);
			FileUtils.copyFile(screenshot, destFile);
			
			logger.info("["+fileName + "] 截图成功，保存在：" + "[ " + filePath + " ]");

		} catch (Exception e) {
			filePath = "["+fileName+"]" + " ,截图失败，原因：" + e.getMessage();
			logger.error(filePath);
		}

		if (!"".equals(filePath)) {
			Reporter.setCurrentTestResult(tr);
			Reporter.log(filePath);
			// 把截图写入到Html报告中方便查看
			Reporter.log("<img src=\"../../" + filePath + "\"/>");

		}
	}
	
	//--------------------------- 判断元素状态 ----------------------------------------




	
//	/** 检查元素是否显示 */
	public boolean isElementExist(AndroidElement element) {
		boolean isDisplay = false;
		try{
			if (element.isDisplayed()) {
				logger.info("The element: [" + getLocatorByElement(element, ">") + "] is displayed");
				isDisplay = true;
			}
		} catch (Exception e) {
			logger.warn("The element: [" + getLocatorByElement(element, ">") + "] is not displayed");
			isDisplay = false;
			// TODO: handle exception
		}
	
		return isDisplay;
	}
	


	
	//两个方法的方法命相同，但是参数不同，术语我们 叫重载
	public boolean isElementExist(By by,int timeout){
		try {
			new WebDriverWait((WebDriver) this,timeout).until(ExpectedConditions.presenceOfElementLocated(by));
			//显示等待，是针对某个元素进行的超时设置  
			//this当前的对象，这里传的就是代表driver
			logger.info("element was found");//加上日志
			return true;
		} catch (Exception e) {//找到就返回true，如果找不到就异常，给try捕获，返回false
			// TODO: handle exception
			//e.printStackTrace();
			logger.debug("element not found");//加上日志
			return false;
		}
	}
	
	/**  定位类型locatorType
	 * 根据输入的By 类型返回对应的By.ByType(value);
	 * */
	public   By reAndroidBy(String ByType ,String value){
		if(ByType.toLowerCase().equals("id"))
			return By.id(value);//By.id("xxx:id/xxx")
		else if(ByType.toLowerCase().equals("name"))
			return By.name(value);//登录或注册
		else if((ByType.toLowerCase().equals("classname"))||(ByType.toLowerCase().equals("class")))
			return By.className(value);
		else if((ByType.toLowerCase().equals("tagname"))||(ByType.toLowerCase().equals("tag")))
			return By.className(value);
		else if((ByType.toLowerCase().equals("linktext"))||(ByType.toLowerCase().equals("link")))
			return By.linkText(value);
		else if(ByType.toLowerCase().equals("partiallinktext"))
			return By.partialLinkText(value);
		else if((ByType.toLowerCase().equals("cssselector"))||(ByType.toLowerCase().equals("css")))
			return By.cssSelector(value);
		else if(ByType.toLowerCase().equals("xpath"))
			
			return By.xpath(value);
		
		else
			try {
				throw new Exception("输入的 locator type 未在程序中被定义：" + ByType );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	/***
	 * 依赖上面的 reAndroidBy类返回的By类型值
	 * 根据输入的 ByType定位类型进行然后对应的AndroidElement类型
	 * */
	public AndroidElement  reAndroidElement(String ByType ,String value){
		if(ByType.toLowerCase().equals("id"))
			return  driver.findElement(reAndroidBy(ByType, value));
		else if(ByType.toLowerCase().equals("name"))
			return driver.findElement(reAndroidBy(ByType, value));
		else if((ByType.toLowerCase().equals("classname"))||(ByType.toLowerCase().equals("class")))
			return driver.findElement(reAndroidBy(ByType, value));
		else if((ByType.toLowerCase().equals("tagname"))||(ByType.toLowerCase().equals("tag")))
			return driver.findElement(reAndroidBy(ByType, value));
		else if((ByType.toLowerCase().equals("linktext"))||(ByType.toLowerCase().equals("link")))
			return driver.findElement(reAndroidBy(ByType, value));
		else if(ByType.toLowerCase().equals("partiallinktext"))
			return driver.findElement(reAndroidBy(ByType, value));
		else if((ByType.toLowerCase().equals("cssselector"))||(ByType.toLowerCase().equals("css")))
			return driver.findElement(reAndroidBy(ByType, value));
		else if(ByType.toLowerCase().equals("xpath"))
			return driver.findElement(reAndroidBy(ByType, value));
		
		else
			try {
				throw new Exception("输入的 locator type 未在程序中被定义：" + ByType );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
		
	}
	/** 根据元素来获取此元素的定位值 */
	public String getLocatorByElement(AndroidElement element, String expectText) {
		String text = element.toString();
		String expect = null;
		try {
			expect = text.substring(text.indexOf(expectText) + 1, text.length() - 1);
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("failed to find the string [" + expectText + "]");
		}
		return expect;
	}
	
	
	/** 检查元素是否被勾选 */
	public boolean isSelected(AndroidElement element) {
		boolean flag = false;
		if (element.isSelected() == true) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is selected");
			flag = true;
		} else if (element.isSelected() == false) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is not selected");
			flag = false;
		}
		return flag;
	}
	


	/**
	 * 判断实际文本时候包含期望文本
	 * 
	 * @param actual
	 *            实际文本
	 * @param expect
	 *            期望文本
	 */
	public void isContains(String actual, String expect) {
		try {
			Assert.assertTrue(actual.contains(expect));
		} catch (AssertionError e) {
			logger.error("The [" + actual + "] is not contains [" + expect + "]");
			Assert.fail("The [" + actual + "] is not contains [" + expect + "]");
		}
		logger.info("The [" + actual + "] is contains [" + expect + "]");
	}
	
	/** 检查checkbox是不是勾选 */
	public boolean isCheckboxSelected(By elementLocator) {
		if (driver.findElement(elementLocator).isSelected() == true) {
			logger.info("CheckBox: " + getLocatorByElement(driver.findElement(elementLocator), ">") + " ----------被勾选");
			return true;
		} else
			logger.info("CheckBox: " + getLocatorByElement(driver.findElement(elementLocator), ">") + " ---------------没有被勾选");
		return false;

	}
	/**
	 * 暂停当前用例的执行，暂停的时间为：sleepTime
	 * */
	public void pause(int sleepTime) {
		if (sleepTime <= 0) {
			return;
		}
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {	
			e.printStackTrace();
		}
	}

	/**
	 * 在给定的时间内去查找元素，如果没找到则超时，抛出异常
	 * */
	public void waitForElementToLoad(int timeOut, final By By) {
		logger.info("开始查找元素[" + By + "]");
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver driver) {
					WebElement element = driver.findElement(By);
					return element.isDisplayed();
				}
			});
		} catch (TimeoutException e) {
			logger.error("超时!! " + timeOut + " 秒之后还没找到元素 [" + By + "]");
			Assert.fail("超时!! " + timeOut + " 秒之后还没找到元素 [" + By + "]");

		}
		logger.info("找到了元素 [" + By + "]");
	}

	/**
	 * pageLoadTimeout。页面加载时的超时时间。因为webdriver会等页面加载完毕在进行后面的操作，
	 * 所以如果页面在这个超时时间内没有加载完成，那么webdriver就会抛出异常
	 */
	public void waitForPageLoading(int pageLoadTime) {
		driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
	}
	
	
	/***
	 * element!=null 重点是做了非空的处理
	 * */
	//输入
//	public void sendkeys(AndroidElement element,String value){
//		if(element!=null){//如果元素不是空才进行输入
//			element.sendKeys(value);
//		}else{
//			System.out.println("元素没有定位到，是null");
//		}
//	}
	
	public void sendkeys(String ByType,String value,String contents){
		
		try {
			if(this.reAndroidElement(ByType, value)!=null){//如果元素不是空才进行输入
				this.reAndroidElement(ByType, value).sendKeys(contents);
				logger.info("输入：[" + contents + "] 到 [" + value + "]");
				}
		} catch (Exception e) {
//			e.printStackTrace(); //不抛异常
			logger.error("输入 [" + contents + "] 到 元素[" + ByType + "]失败");
			Assert.fail("输入 [" + contents + "] 到 元素[" + ByType + "]失败");

		}

	}
	

	
	/**
	 * 获得元素的文本
	 * */
	public String getText(By elementLocator) {
		return driver.findElement(elementLocator).getText().trim();
	}
	//AndroidElement 类型
	public String getText(AndroidElement androidElement) {
		return androidElement.getText().trim();
	}
	/**
	 * 获得当前select选择的值
	 * */
	public List<WebElement> getCurrentSelectValue(By by){
		List<WebElement> options = null;
		Select s = new Select(driver.findElement(by));
			options =  s.getAllSelectedOptions();
			return options;
	}
	/**
	 * 包装点击操作  By类型的的点击
	 * */
	public void click(By byElement) {

		try {
			clickTheClickable(byElement, System.currentTimeMillis(), 2500);
		} catch (StaleElementReferenceException e) {
			logger.error("The element you clicked:[" + byElement + "] is no longer exist!");
//			Assert.fail("The element you clicked:[" + byElement + "] is no longer exist!");
		} catch (Exception e) {
			logger.error("Failed to click element [" + byElement + "]");
//			Assert.fail("Failed to click element [" + byElement + "]",e);
		}
		logger.info("点击元素 [" + byElement + "]");
	}
	/**
	 *  AndroidElement类型的点击
	 * */
	public  boolean click(AndroidElement element) {
		boolean flag=false;
		try {
			clickTheElementClickable(element, System.currentTimeMillis(),2000);
			flag=true;
			logger.info("点击元素 [" + element + "]");
			return flag;
		} catch (StaleElementReferenceException e) {
			logger.error("The element you clicked1:[" + element + "] is no longer exist!");
//			Assert.fail("The element you clicked:[" + element + "] is no longer exist!");
			return flag;
		} catch (Exception e) {
			logger.error("Failed to click element1 [" + element + "]");
//			Assert.fail("Failed to click element [" + element + "]",e);
			return flag;
		}
		
	}
	
	
	/** 不能点击时候重试点击操作 
	 * by 类型
	 * */
	private void clickTheElementClickable(AndroidElement element, long startTime, int timeOut) throws Exception {
		try {
			element.click();
		} catch (Exception e) {
			if (System.currentTimeMillis() - startTime > timeOut) {
				logger.warn(element+ " is unclickable");
				throw new Exception(e);
			} else {
				Thread.sleep(500);
				logger.warn(element + " is unclickable, try again");
				clickTheElementClickable(element, startTime, timeOut);
			}
		}
	}
	
	private void clickTheClickable(By byElement, long startTime, int timeOut) throws Exception {
		try {
			driver.findElement(byElement).click();
		} catch (Exception e) {
			if (System.currentTimeMillis() - startTime > timeOut) {
				logger.warn(byElement+ " is unclickable");
				throw new Exception(e);
			} else {
				Thread.sleep(500);
				logger.warn(byElement + " is unclickable, try again");
				clickTheClickable(byElement, startTime, timeOut);
			}
		}
	}
	
	
	/**
	 * 包装清除操作
	 * */
	public void clear(By byElement) {
		try {
			driver.findElement(byElement).clear();
		} catch (Exception e) {
			logger.error("清除元素 [" + byElement + "] 上的内容失败!");
		}
		logger.info("清除元素 [" + byElement  + "]上的内容");
	}

	/**
	 * 向输入框输入内容
	 * */
	public void sendkeys(By byElement, String key) {
		try {
			driver.findElement(byElement).sendKeys(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("输入 [" + key + "] 到 元素[" + byElement + "]失败");
			Assert.fail("输入 [" + key + "] 到 元素[" + byElement + "]失败");
		}
		logger.info("输入：[" + key + "] 到 [" + byElement + "]");
	}
	
	
	//清除
	public void clear(AndroidElement element){
		if(element!=null){
			element.clear();
		}else{
			System.out.println("元素没有定位到，是null");
		}
	}
	//逐个清除，对于密码输入框是无效的
	public void clearOneByOne(AndroidElement element){
		if(element!=null){
			element.click();//当前光标定位到
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_MOVE_END);
			String text=element.getText();
			for(int i=0;i<text.length();i++){//当前的内容有多长，就删除多少次
				driver.pressBackspace();
			}
		}else{
			System.out.println("元素没有定位到，是null");
		}
	}
	
	// 输出跳转后Activity名称
	public void jumpCurrentActivity(String tag) {
		System.out.print("正确跳转进入" + tag + "，当前Activity为："
				+ driver.currentActivity() + "\n");
	}

	// 输出当前Activity名称
	public void getCurrentActivity(String tag) {
		System.out.print("已进入" + tag + "，当前Activity为："
				+ driver.currentActivity() + "\n");
	}
	//输入内容直到正确
	//一个输入框有时候能输入对，有时候不你能输入对，处理，在处理完成之后，我们拿到输入之后的内容，以我们原本输入的内容做判断，如果一样不输入，如果不一样，继续输入
	//直到我们输入的内容输入正确
	public void sendkeysUntilCorrect(AndroidElement element,String str){
		if(element!=null){
			boolean flag=true;
			element.sendKeys(str);//输入一次
			while(flag){//循环判断
				if(str.equals(element.getText())){//str输入的内容和当前输入框中的内容进行对比，如果一样 flag变成false
					flag=false;
				}else{
					element.sendKeys(str);//如果不一样再输入一次
				}
			}
		}else{
			System.out.println("元素为null");
		}
	}
	//只针对数字
	//逐个输入数字，模拟的是键盘输入，例如输入是：15610112934
		public void sendKeysOneByOne(String text){
			char[] chr=text.toCharArray();//例如输入是：15610112934， 将字符串转化成字符的数组，相当于把字符串拆开成这样{1,5,6,1,x,x,x,x}
			for(int i=0;i<chr.length;i++){//遍历数组
				int c=Integer.valueOf(String.valueOf(chr[i]));//String.valueOf(chr[i])将某个项转化成字符串，再转化成数字
				int number=0;//默认是0
				switch (c) {//c已经转化成数字
				case 0://如果c等于0，让number等于键盘上的0
					//driver.pressKeyCode(AndroidKeyCode.KEYCODE_0);
					number=AndroidKeyCode.KEYCODE_0;
					break;
				case 1:
					number=AndroidKeyCode.KEYCODE_1;
					break;
				case 2:
					number=AndroidKeyCode.KEYCODE_2;
					break;
				case 3:
					number=AndroidKeyCode.KEYCODE_3;
					break;
				case 4:
					number=AndroidKeyCode.KEYCODE_4;
					break;
				case 5:
					number=AndroidKeyCode.KEYCODE_5;
					break;
				case 6:
					number=AndroidKeyCode.KEYCODE_6;
					break;
				case 7:
					number=AndroidKeyCode.KEYCODE_7;
					break;
				case 8:
					number=AndroidKeyCode.KEYCODE_8;
					break;
				case 9:
					number=AndroidKeyCode.KEYCODE_9;
					break;
				default:
					System.out.println("数值不对");//如果输入不在0到9之间，提示输入数值不对
					break;
				}
				driver.pressKeyCode(number);//点击
			}
		}
	
	//坐标元素点击，针对一些能定位到整体元素但具体元素无法定位并且具有规律性的元素点击，获取每个子元素的中心点坐标（只能定位整体元素，具体的定位不到）
	//实例，支付宝的特定键盘，安全键盘。是获取不到小格子具体的位置，但是能拿到整个黑色的大框是可以的
	//思路：获取整体元素，将整体元素分为多行多列元素，取每一个子元素的中心坐标进行点击
		//element元素是个大黑框，告诉方法，大框几行几列，让方法解析小块的坐标
	public List<int[]> getElementByCoordinates(AndroidElement element,int rows,int columns){
		int[] coordinate=new int[2];//存放x，y坐标
		List<int[]> elementResolve=new ArrayList<int[]>();//大框中所有小框的重点坐标
		if(element!=null){//如果是null说明没定位大框
			int startx=element.getLocation().getX();//起始点坐标x
			int starty=element.getLocation().getY();//起始点坐标y
			int offsetx=element.getSize().getWidth();//大黑框该元素的宽
			int offsety=element.getSize().getHeight();//大黑框该元素的高
			for(int i=0;i<rows;i++){//双层for循环，计算每个小框的坐标//外层是行
				for(int j=0;j<columns;j++){//内层是列
					coordinate[0]=startx+(offsetx/2*columns)*(2*j+1);//offsetx是宽，2*j+1是基数，
					coordinate[1]=starty+(offsety/(2*rows)*(2*i+1));//offsety是高  高是根据行号变化的
					elementResolve.add(coordinate);
				}
			}
		}
		return elementResolve;
	}
	//根据整体元素拆分后的规律子元素索引点击元素
	//接下看下怎么点击，
	public void clickElementByCoordinate(AndroidElement element,int rows,int columns,int index){
		if(element!=null){
			List<int[]> elementResolve=getElementByCoordinates(element,rows,columns);
			if(!elementResolve.isEmpty()&&elementResolve!=null){
				driver.clickByCoordinate(elementResolve.get(index)[0],elementResolve.get(index)[1]);
				//clickByCoordinate点击坐标的方法
			}else{
				System.out.println("坐标集合为空");
			}
		}else{
			System.out.println("元素为null");
		}
	}
	
	//输入安全键盘的密码
	//password是密码：12806作为一个数组传进来，然后要解析她
	public void sendkeysPwd(List<int[]> pwd,int[] password){
		//对应的是getElementByCoordinates方法的返回结果，将大框拆分之后的结果
		if(password.length>0){
		//再逐个点击
			for(int i=0;i<password.length;i++){//如果是1 索引是0,2索引是1,0对应的索引是10
				if(password[i]==0){
					//0对应的索引是10
					driver.clickByCoordinate(pwd.get(10)[0], pwd.get(10)[1]);
				}else{
					//1到9的对应的索引
					driver.clickByCoordinate(pwd.get(password[i-1])[0], pwd.get(password[i-1])[1]);
				}
			}
		}
	}
	
	//password是密码：12806作为一个数组传进来，然后要解析她
	//重写这个方法 和上面的一样
	public void sendkeysPwd(AndroidElement element,int rows,int colums,int[] password){
		if(element!=null){
			List<int[]> pwd=getElementByCoordinates(element,rows,colums);
			//对应的是getElementByCoordinates方法的返回结果，将大框拆分之后的结果
			if(password.length>0){
			//再逐个点击
				for(int i=0;i<password.length;i++){//如果是1 索引是0,2索引是1,0对应的索引是0
					if(password[i]==0){
						driver.clickByCoordinate(pwd.get(10)[0], pwd.get(10)[1]);
					}else{
						driver.clickByCoordinate(pwd.get(password[i-1])[0], pwd.get(password[i-1])[1]);
					}
				}
			}
		}
	
	}
	//手势密码锁
	//九宫格手势解锁,参数indexs是密码数字组成的数组，参数indexs={1,2,5,6,9,8}
	public void wakeByGestures(AndroidElement element,int[] indexs){
		if(element!=null){
			List<int[]> elementResolve=getElementByCoordinates(element,3,3);//3*3的九宫格
			TouchAction ta=null;//手势用这个TouchAction
			if(indexs.length>0){//大于0是有密码
				//第一次按着移动
				ta=new TouchAction(driver).press(elementResolve.get(indexs[0]-1)[0], elementResolve.get(indexs[0]-1)[1]).waitAction(500);
			}
			for(int i=1;i<indexs.length;i++){
				//移动到下面的点 为啥i等于1呢，？第一个点已经用了，第二个点索引是1
				ta.moveTo(elementResolve.get(indexs[i])[0]-elementResolve.get(indexs[i-1])[0], elementResolve.get(indexs[i])[1]-elementResolve.get(indexs[i-1])[1]).waitAction(500);//每个都等待500ms
			}
			ta.release().perform();
		}
	}

}
