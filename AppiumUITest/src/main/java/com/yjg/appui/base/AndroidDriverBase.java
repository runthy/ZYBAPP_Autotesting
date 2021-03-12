package com.yjg.appui.base;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.yjg.appui.util.DosCmd;

import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * @author Neo.shi
 * @description 封装了几乎所有的基础方法，封装自己的driver类，包含重写及自主封装的方法
 * 由于继承了AndroidDriver类
 * super指代是AndroidDriver这个类的对象
 * this指代的是当前自定义类的对象
 */

public class AndroidDriverBase extends AndroidDriver{
	public static Logger logger = Logger.getLogger(AndroidDriverBase.class);
	public  String input;
	public  String udid;
	/**  
	 * 继承AndroidDriver 必须重写的构造方法 
	 * @param server  appium服务地址
	 * @param port appium使用的端口
	 * @param capsPath 读取caps参数的配置文件的路径
	 * @param udid  设备的udid名称
	 * @param input  设备的输入法
	 * @throws Exception
	 */
	// cappath是从AndroidDriverBase类传递过来
	public AndroidDriverBase(String server,String port,String capsPath,String udid,String input) throws Exception{
		super(new URL(server+":"+port+"/wd/hub"),new CrazyCapabilities().initCaps(capsPath, udid));
		this.input=input;
		this.udid=udid;
	}
	
	
	/**  
	 * 继承AndroidDriver 必须重写的构造方法   ，其中有的app起始的activity和真正启动后的activity不一致；
	 * 需要wait这个方法，否则无法创建成功.特别是有引导页的页面   appWaitActivity加入此参数
	 * @param server  appium服务地址
	 * @param port appium使用的端口
	 * @param capsPath 读取caps参数的配置文件的路径
	 * @param udid  设备的udid名称
	 * @param input  设备的输入法
	 * @throws Exception
	 */
	public AndroidDriverBase(String server,String port,String capsPath,String udid,String input,int waitFlag) throws Exception{
		super(new URL(server+":"+port+"/wd/hub"),new CrazyCapabilities().initCapsWait(capsPath, udid));
		this.input=input;
		this.udid=udid;
	}


	/*****************************************************判断元素状态****************************************************/
	/**
	 * @author Neo.shi
	 * @description 判断某个页面是否包含存在指定的activity
	 * @param CurActivity
	 * @return
	 * @throws InterruptedException
	 */
	public boolean isExitCurActivity(String CurActivity) throws InterruptedException {
		//等待一段时间进行检查
		sleep(4000);
		boolean flag = false;
		if (super.currentActivity().contains(CurActivity) == true) {
			logger.info("当前CurActivity是："+super.currentActivity());
			flag = true;
			return flag;
		} else {
			logger.error("期望的CurActivity是 [" + CurActivity + "] 但是找到了 [" + super.currentActivity() + "]");
			//如果抛异常会停止执行 这里不需要停止执行
//			Assert.fail("期望的CurActivity是 [" + CurActivity + "] 但是找到了 [" + super.currentActivity() + "]");
			return flag;
		}
	}
//	 判断是否存在activity
	public boolean isExitCurActivity1(String CurActivity) {
		try {
			//判断当前的activity是否包含输入的activity
			super.currentActivity().contains(CurActivity);
			return true;
		} catch (Exception e) { //如果会报错，我们捕获这个异常，返回false
			// TODO: handle exception
			//e.printStackTrace();
			return false;
		}
	}

	/**
	 * @description 判断文本是不是和需求要求的文本一致
	 * @param actual
	 * @param expected
	 */
	public void isTextCorrect(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			logger.error("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]");
			Assert.fail("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]");

		}
		logger.info("找到了期望的文字: [" + expected + "]");
	}

	/**
	 * 判断文本是不是和需求要求的文本一致
	 * 如果是，抛出true，如果不是给出false
	 * @param actual
	 * @param expected
	 * @return
	 */
	public boolean isTextCorrect1(String actual, String expected) {
		boolean flag=false;//先设定没有找到
		try {
			Assert.assertEquals(actual, expected);
			flag=true;
		} catch (AssertionError e) {
			logger.info("正常 : [没有找到异常提示为：" + expected + "]");
			return flag;//正常返回false
		}
	
		logger.error("出现错误提示了 : [" + expected + "]");
//		Assert.fail("期望的文字是 [" + expected + "] 找到了 [" + actual + "]");
		return flag;
	}
	
	
	/** 检查元素是否可用
	 * @param element
	 * @return
	 */
	public boolean isEnabled(AndroidElement element) {
		boolean isEnable = false;
		if (element.isEnabled()) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is enabled");
			isEnable = true;
		} else if (element.isDisplayed() == false) {
			logger.warn("The element: [" + getLocatorByElement(element, ">") + "] is not enable");
			isEnable = false;
		}
		return isEnable;
	}
	
	/** 根据元素来获取此元素的定位值
	 * @param element
	 * @param expectText
	 * @return
	 */
	public String getLocatorByElement(AndroidElement element, String expectText) {
		String text = element.toString();
		String expect = null;
		try {
			expect = text.substring(text.indexOf(expectText) + 1, text.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("failed to find the string [" + expectText + "]");
		}
		return expect;
	}
	
	
	/**
	 * 包装点击操作
	 * @param byElement
	 */
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
	 * 不能点击时候重试点击操作 
	 * @param byElement
	 * @param startTime
	 * @param timeOut
	 * @throws Exception
	 */
	private void clickTheClickable(By byElement, long startTime, int timeOut) throws Exception {
		try {
			this.findElement(byElement).click();
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
	 * @description 判断元素是否存在  查找一个元素的时候，appium默认的是，找不到会报错，终止我们的代码，我们如何继续执行？知识判断是否存在，存在走某个分支
	 * 用try来捕获下找不到元素错误 
	 * @param by对象一般是 By.id("xxxx") By.xpath("xxx")  NoSuchAndroidElementException
	 * @return 存在则返回true，不存在则返回false
	 */
	public boolean isElementExist(By by){
		try {
			super.findElement(by).isDisplayed();
			return true;
		} catch (Exception e) { //如果会报错，我们捕获这个异常，返回false
			// TODO: handle exception
			//e.printStackTrace();
			return false;
		}
	}
	/**
	 * 在指定超时时间内元素是否存在，如存在则立即返回结果，如不存在则在超时后返回结果
	 * @param by 定位对象
	 * @param timeout 超时时间
	 * @return 指定时间内任意时间该元素出现则停止等待返回true，指定时间内没出现则返回false
	 */
	//两个方法的方法命相同，但是参数不同，术语我们 叫重载
	public boolean isElementExist(By by,int timeout){
		try {
			new WebDriverWait(this,timeout).until(ExpectedConditions.presenceOfElementLocated(by));
			//显示等待，是针对某个元素进行的超时设置  
			//this当前的对象，这里传的就是代表driver
			logger.info("存在该元素可用");//加上日志
			return true;
		} catch (Exception e) {//找到就返回true，如果找不到就异常，给try捕获，返回false
			// TODO: handle exception
			//e.printStackTrace();
			logger.debug("element not found");//加上日志
			return false;
		}
	}
	/**
	 * 查找元素存在则返回该元素对象，不存在则返回null  找到返回的是element对象，这里没加日志？
	 * @param by
	 * @return
	 */
	@Override
	public AndroidElement findElement(By by){
		try {
			AndroidElement element=(AndroidElement) super.findElement(by);
			return element;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	/**
	 * 在指定超时时间内查找元素是否存在，如存在则立即返回AndroidElement对象，如不存在则在超时后返回null
	 * @param by
	 * @param timeout 单位是秒
	 * @return
	 */
	//上面返回boolen类型的，是判断元素是否存在，返回的boolean的类型
	//这种类型用在对元素进行操作的时候，定位到他还有点击，还有输入，操作AndroidEelent的element对象
	public AndroidElement findElement(final By by,int timeout){
		try {
			AndroidElement element=new WebDriverWait(this, timeout).until(new ExpectedCondition<AndroidElement>() {
				@Override
				public AndroidElement apply(WebDriver driver) {
					// TODO Auto-generated method stub
					return (AndroidElement) driver.findElement(by);
				}
			});
			return element;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
//	通过查找一系列的element然后存在list变量中
	public List findElements(By by){
		List<AndroidElement> elementList=super.findElements(by);
		return elementList;
	}
	
	
	/***********************************滑动操作*********************************/
	
	/**
	 * @description 滑动屏幕方法，通过参数实现各方向滑动
	 * @param direction 方向参数，值为"up"、"down"、"right"、"left"
	 * @param duration 滑动时间，单位毫秒  持续时间
	 */
	public void swipe(String direction,int duration){
		try {
			SwipeScreen swipeSreen=new SwipeScreen(this);//整个屏幕滑动，实例化，然后直接调用方法
			swipeSreen.swipe(direction, duration);
			logger.info("手势往"+direction+"滑动成功");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
//		logger.info("手势往"+direction+"滑动成功");
	}
	
	/**
	 * @description  This Method for swipe up
	 * @param during 滑动是时长
	 * @param num  滑动的次数
	 */
	public void swipeToUp(int during,int num) {
		try {
			int width = this.manage().window().getSize().width;// 获取当前屏幕的宽度
			int height = this.manage().window().getSize().height;// 获取当前屏幕的高度
			for (int i = 0; i < num; i++) {
				this.swipe(width / 2, height * 3 / 4, width / 2, height / 3,
						during);
				logger.info("手势往up"+i+"滑动成功");
			}
		} 
		catch(Exception e){
			System.out.println(e);

		}
	}


	/**
	 * This Method for swipe down
	 * @param during
	 * @param num
	 */
	public void swipeToDown( int during,int num) {
		try {
			int width = this.manage().window().getSize().width;// 获取当前屏幕的宽度
			int height = this.manage().window().getSize().height;// 获取当前屏幕的高度
			for (int i = 0; i < num; i++) {
				this.swipe(width / 2, height / 4, width / 2, height * 3 / 4,
						during);
				logger.info("手势往up"+i+"滑动成功");

			}
		} catch(Exception e){
			System.out.println(e);
		}
	}

	/**
	 * This Method for swipe Left
	 * @param during
	 * @param num
	 */
	public void swipeToLeft( int during,
			int num) {
		int width = this.manage().window().getSize().width;// 获取当前屏幕的宽度
		int height = this.manage().window().getSize().height;// 获取当前屏幕的高度
		for (int i = 0; i < num; i++) {
			this.swipe(width * 3 / 4, height / 2, width / 4, height / 2,
					during);
		}
	}

	/**
	 * This Method for swipe right
	 * @param during
	 * @param num
	 */
	public void swipeToRight( int during,
			int num) {
		int width = this.manage().window().getSize().width;// 获取当前屏幕的宽度
		int height = this.manage().window().getSize().height;// 获取当前屏幕的高度
		for (int i = 0; i < num; i++) {
			this.swipe(width / 4, height / 2, width * 3 / 4, height / 2,
					during);
		}
	}

	
	/**
	 * 在元素上滑动
	 * @param element 元素对象
	 * @param derction 方向参数，值为"up"、"down"、"right"、"left"
	 * @param duration
	 */
	//场景？//滑动是以元素为基准的，所以必须传递一个元素
	//SwipeElementDirection 是appium自带的
	//为什么要带上偏移量？是边界的问题，如果不到哎偏移量，会从最边边滑起，保证在元素的内部移动，
	public void swipeOnElement(AndroidElement element,String derction,int duration){
		String derc=derction.toLowerCase();
		switch (derc) {
		case "up":
			element.swipe(SwipeElementDirection.UP, 10, 10, duration);
			break;
		case "down":
			element.swipe(SwipeElementDirection.DOWN, 10, 10, duration);
			break;
		case "left":
			element.swipe(SwipeElementDirection.LEFT, 10, 10, duration);
			break;
		case "right":
			element.swipe(SwipeElementDirection.RIGHT, 10, 10, duration);
			break;
		default:
			System.out.println("方向参数错误");
			break;
		}
	}
	//上面是定位好的element对象，这里传入的是by对象
	public void swipeOnElement(By by,String derction,int duration){
		AndroidElement	element=this.findElement(by);//先定位到元素，再调用上面的函数
		//为什么要重载这两种？原因是参数不同
		//swipeOnElement返回 element 元素对象 不仅仅是滑动操作，可能它需要点击等操作
		//swipeOnElement元素只做滑动操作的时候调用
		this.swipeOnElement(element, derction, duration);
	}
	/**
	 * 向某方向滑动直到某元素出现
	 * @param by 查找对象
	 * @param direction 方向
	 * @param duration 每次滑动时间，单位毫秒
	 * @param findCount 查找次数
	 */
	//使用的场景 1、是否滑动到底部或者最右边最左边等操作; 2 、当前的数据不在当前屏幕
	//次数干啥用？如果元素不存在，while一直找，会死循环，添加查找次数，来限制
	public boolean swipeUntilElementAppear(By by,String direction,int duration,int findCount){
		//this.implicitlyWait(3);
		boolean flag=false;
		while(!flag&&findCount>0){//如果true 此处大于0就查找
			try {
				super.findElement(by);
				flag=true;
			} catch (Exception e) {
				// TODO: handle exception
				this.swipe(direction,duration);
				findCount--;//滑动一次找一次，找不到就返回flag，不再进行查找
			}
		}
		return flag;
	}	

	/**滑动直到某个元素
	 * 我通过滑动，查找到这个元素，并且还有进行操作，点击等操作，获取属性等
	 * @param by
	 * @param direction
	 * @param duration
	 * @param findCount
	 * @return
	 */
	public AndroidElement swipeUntilElement(By by,String direction,int duration,int findCount){
		//this.implicitlyWait(3);
		AndroidElement element=null;
		boolean flag=false;
		while(!flag&&findCount>0){
			try {
				element=(AndroidElement) super.findElement(by);
				flag=true;
			} catch (Exception e) {
				// TODO: handle exception
				this.swipe(direction,duration);
				findCount--;
			}
		}
		return element;
	}
	
	/***********************************滑动操作*********************************/
	
	/***********************************其他操作*********************************/
	/**等待休眠时间 
	 * @param timeOut
	 * @throws InterruptedException
	 */
	public void sleep(int timeOut) throws InterruptedException {
		Thread.sleep(timeOut);
	}
	

	/** 
	 * @description 获取当前activity
	 * @return
	 */
	public  String getCurActivity() {
		return super.currentActivity();
	}
	
	/**
	 * @description 获取app应用占用的屏幕宽度
	 * @return 返回宽度
	 */
	public int appScreenWidth(){
		int width=super.manage().window().getSize().getWidth();
		//super指AndroidDriver的对象
		System.out.println("获取app应用占用的屏幕宽度为->"+width);
		return width;
	}
	/**
	 * @description 获取app应用占用的屏幕高度
	 * @return 返回高度
	 */
	public int appScreenHeight(){
		int height=super.manage().window().getSize().getHeight();
		System.out.println("获取app应用占用的屏幕宽度为->"+height);
		return height;
	}
	
	/**
	 * 与服务端断开连接,断开前重置输入法
	 */
	public void quit(){
		try {
			this.resetInput();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//断开连接之前，重置输入法
		super.quit();
	}
	/**
	 * 隐式等待
	 * @param timeout
	 */
	//简化的封装，写短了而已  单位是秒
	public void implicitlyWait(int timeout){
		super.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}
	/**
	 * 获取手机分辨率
	 * @return
	 */
	//appium封装了app占用手机屏幕的大小，没有获取手机本手获取屏幕大小的方法
	
	public int[] getMobileSize(){
		//adb shell wm size  ，将获取到的Physical size: 540x960的值：540x960解析出来
		//也就是提取字符串的数字
		//先用冒号：进行分割，然后后面用x来分割，就得到对应的数据了
		DosCmd dos=new DosCmd();
		int[] sizeInt=new int[2];//数组存放540和960
		try {
			List<String> resList=dos.execCmdConsole("adb -s "+udid+" shell wm size");//使用adb 获取某个设备的手机分辨率
			//
			String[] size=new String[2];
			if(resList.size()>0){
					size=resList.get(0).split(": ")[1].split("x");
					//有结果，拿第一行，然后用：空格来风格，索引1 540x960,然后通过x来分割
					//size[0]=540,size[1]是960
			}
			sizeInt[0]=Integer.valueOf(size[0]);
			sizeInt[1]=Integer.valueOf(size[1]);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sizeInt;
	}
	/**
	 * 重置输入法
	 * @param input   
	 * @throws InterruptedException 
	 */
	//借助执行dos命令来做的
	public void resetInput() throws InterruptedException{
		DosCmd dc=new DosCmd();
		List<String> res=dc.execCmdConsole("adb -s "+udid+" shell \"settings get secure default_input_method\"");
//		logger.info("获取设备默认输入法:"+"adb -s "+udid+" shell \"settings get secure default_input_method\"");
		input=res.get(0);//数组集合的第一个
		try {
			dc.execCmdConsole("adb shell \"ime set "+input+"\"");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 切换输入法
	 * @param input
	 */
	//input初始化之前就要拿到 带参数的可以切换输入法，搜索中输入法中有完成或者搜索的的情况，这样的情况
	//就需要切换成对应的输入法来完成
	public void resetInput(String input){
		DosCmd dc=new DosCmd();
		try {
			dc.execCmdConsole("adb shell \"ime set "+input+"\"");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/***********************************其他操作*********************************/

	
	/**
	 * 单击某元素，使用tap方式
	 * @param element
	 * @param duration
	 */
	public void tapSingle(AndroidElement element){
		super.tap(1, element,100);//调用父类的tap方法，100毫秒
	}
	/**
	 * touchaction的方式点击元素 touchaction封装了很多手势操作，包括长按，某个元素，拖拽某个元素等等
	 * @param element
	 */

	public void tap(AndroidElement element){
		try {
			TouchAction ta=new TouchAction(this);
			ta.tap(element).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 点击某个坐标点
	 * @param x
	 * @param y
	 */
	public void clickByCoordinate(int x,int y){
		try {
			TouchAction ta=new TouchAction(this);
			ta.tap(x,y).release().perform();
			System.out.println("点击坐标"+x+"  "+y+"成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("点击坐标"+x+"  "+y+"报错");
		}
	}
	/**
	 * 元素长按
	 * @param by
	 */
	//传入的是by对象  没有其他操作
	public void longPress(By by){
		try {
			AndroidElement element=(AndroidElement) super.findElement(by);
			TouchAction ta=new TouchAction(this);
			ta.longPress(element).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 元素长按
	 * @param element
	 */
	//element和上面的by有什么不同？这个方法是可能需要其他操作，上面的不进行操作
	public void longPress(AndroidElement element){
		try {
			//AndroidElement element=(AndroidElement) super.findElement(by);
			TouchAction ta=new TouchAction(this);
			ta.longPress(element).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 在某一个坐标点上长按
	 * @param x
	 * @param y
	 */
	//坐标点
	public void longPress(int x,int y){
		try {
			TouchAction ta=new TouchAction(this);
			ta.longPress(x,y).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public String getPageSouce(){
		return super.getPageSource();
	}

	
	/**
	 * 判断两个字符串集合list是否元素对应相等
	 * @param strSrc
	 * @param strDes
	 * @return
	 */
	//
	public Boolean listStrEquals(List<String> strSrc,List<String> strDes){
		Boolean flag=false;
		if((!strSrc.isEmpty()&&strSrc!=null)&&(!strDes.isEmpty()&&strDes!=null)){
			if(strSrc.size()==strDes.size()){
				for(int i=0;i<strDes.size();i++){
					if(strSrc.get(i).equals(strDes.get(i))){
						flag=true;
						continue;
					}else{
						flag=false;
						//System.out.println(strSrc.get(i)+" "+strDes.get(i));
						break;
					}
				}
			}else{
				System.out.println("两个list大小不相等");
			}
		}else{
			System.out.println("list为空或者为null");
		}
		return flag;
	}
	/**
	 * 从指定androidelement集合中根据索引选取某一个
	 * @param srcList
	 * @param index
	 * @return
	 */
	//用在什么地方？传入两个集合，判断都不会空，和大小是否一样，如果不一样，逐个进行比对
	//1、可以用在没有底部标识的滑动到底部；
	//滑动之前，我获取这界面的所有的title文本，然后滑动之后，再获取所有文章开头的titile再存入另个字符串集合里面

	public AndroidElement selectElementFromList(List<AndroidElement> srcList,int index){
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			if(index>=0&&index<srcList.size()){
				element=srcList.get(index);
			}else{
				System.out.println("集合大小为"+srcList.size()+"但是索引参数传值为"+index);
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	
	/**
	 * 从指定androidelement集合中根据索引选取某一个   找到多个元素要返回其中的某一个
	 * @param srcList
	 * @param index
	 * @return
	 */
	public AndroidElement selectElementFromList(By by,int index){
		List<AndroidElement> srcList=super.findElements(by);
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			if(index>=0&&index<srcList.size()){
				element=srcList.get(index);
			}else{
				System.out.println("集合大小为"+srcList.size()+"但是索引参数传值为"+index);
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	/**
	 * 从AndroidElement集合中选出text值与预期相符的唯一元素
	 * @param srcList
	 * @param strFind
	 * @return
	 */

	public AndroidElement selectElementFromList(List<AndroidElement> srcList,String strFind){
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			for(AndroidElement ae:srcList){
				if(ae.getAttribute("text").equals(strFind)){
					element=ae;
					break;
				}
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	/**
	 * 某方向滑动直到边界，如底部，顶部(在没有边界标识的时候使用)
	 * @param direction
	 * @param by
	 * @return
	 * 实现滑动前后的字符串集合list的对比
	 */

	public void swipeUntilBoundary(String direction,By by){
		boolean flag=false;
		while(!flag){
			List<String> strSrc=new ArrayList<String>();
			List<String> strDes=new ArrayList<String>();
			//滑动前定位元素并将元素的text添加到集合strSrc里  这个方法只能定位到元素，所以需要把text拿出来
			List<AndroidElement> elementOld=super.findElements(by);//前
			for(AndroidElement ae:elementOld){
				strSrc.add(ae.getText());
			}
			this.swipe(direction, 500);//滑动后再获取一次元素，将text存list
			this.wait(1000);
			//滑动后定位元素并将元素的text添加到集合strSrc里
			List<AndroidElement> elementNew=super.findElements(by);//后
			for(AndroidElement ae:elementNew){
				strDes.add(ae.getText());
			}
			flag=this.listStrEquals(strSrc,strDes);
			//如果滑动的两次的值一模一样，就说明到到达边界了
		}
	}
	
	/*******************************键盘事件********************************/
	/**
	 * 设备返回键操作
	 */
	public void pressBack(){
		this.wait(500);//返回之前等待了500ms，太快的话，容易失败
		super.pressKeyCode(AndroidKeyCode.BACK);
	}
	/**
	 * 多次返回  深层次的界面，需要连续多次点击返回
	 * @param number
	 */
	public void pressBack(int number){
		if(number>0){
			for(int i=0;i<number;i++){
				this.pressBack();
				System.out.println("执行第"+String.valueOf(i+1)+"次返回");
			}
		}else{
			System.out.println("参数有误");
		}
	}
	/**
	 * 设备home键操作
	 */
	public void pressHome(){
		this.wait(500);
		super.pressKeyCode(AndroidKeyCode.HOME);
	}
	/**
	 * 设备回车键操作
	 */
	public void pressEnter(){
		this.wait(500);
		super.pressKeyCode(AndroidKeyCode.ENTER);
	}
	/**
	 * 手机键盘删除操作
	 */
	public void pressBackspace(){
		this.wait(200);
		super.pressKeyCode(AndroidKeyCode.BACKSPACE);
		//敲字的时候，点击键盘上的backsapce的方式
	}
	/**
	 * 多次手机键盘删除操作
	 */
	public void pressBackspace(int numbers){
		if(numbers>0){
			for(int i=0;i<numbers;i++){
				this.pressBackspace();
				System.out.println("执行第"+String.valueOf(i+1)+"次删除");
			}
		}else{
			System.out.println("参数有误");
		}
	}
	
	/**
	 * 唤醒屏幕
	 */
	public void wakeUp() {
		try {
			if(super.isLocked()){//是不是锁频，如果是锁频就唤醒
				AndroidSpecific.wakeUp(udid);
			}else{
				System.out.println("未锁屏不用唤醒");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//不管你当前设备是否锁频，appium都会自动解锁，如果测试过程中，锁屏幕，是不会 unlock的app
	//解锁当前设备的作用，adb去调用unlockapp
	/**
	 * 等待，死等
	 * @param milliSecond
	 */
	//之前都是sleep的，单位还是毫秒
	public void wait(int milliSecond){
		try {
			Thread.sleep(milliSecond);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 网络检查（之后根据网络状态的值进行对应操作）
	public void networkCheck(AndroidDriver driver) {
		int networkStatue = driver.getNetworkConnection().value;
		switch (networkStatue) {
		case 2:
			System.out.print("当前网络状态为：Data Only\n");
			break;
		case 6:
			System.out.print("当前网络状态为：Data and Wifi mixed\n");
			break;
		}
	}
	
	/*******************************键盘事件********************************/
	
	/*******************************截图全屏********************************/
	//保存的路径和文件名
	public void takeScreen(String path,String fileName) throws Exception{
		File srcFile=super.getScreenshotAs(OutputType.FILE);//OutputType.FILE文件类型
		System.out.println(path+fileName);//文件路径加上文件名词
		FileUtils.copyFile(srcFile,new File(path+fileName));
	}
	
	/**
	 * @param tr  当前执行类的对象
	 */
	private void saveScreenShot(ITestResult tr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String mDateTime = formatter.format(new Date());
		String fileName = mDateTime + "_" + tr.getName();
		String filePath = "";
		try {
			// 这里可以调用不同框架的截图功能
			File screenshot = super.getScreenshotAs(OutputType.FILE);
			filePath = "result/screenshot/"+ fileName + ".jpg";
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
	
	
	//针对元素进行截图
	//AndroidElement element对象，然后获得对象和大小
	public void takeScreenForElement(AndroidElement element,String path,String fileName) throws Exception{
		 // 获得element的位置和大小
		 Point location = element.getLocation();
		 //当前元素的大小
		 Dimension size = element.getSize();
		 byte[] imageByte=super.getScreenshotAs(OutputType.BYTES);//截到的全屏
		 // 创建全屏截图
		 BufferedImage originalImage =ImageIO.read(new ByteArrayInputStream(imageByte));
		 //read读取一下，存到内存或缓存中 
		 // 截取element所在位置的子图。
		 BufferedImage croppedImage = originalImage.getSubimage(
		  location.getX(),//起点坐标
		  location.getY(),
		  size.getWidth(),
		  size.getHeight());
		 try {
			  ImageIO.write(croppedImage, "png", new File(path+fileName+".png"));//再写到文件中
				//jpg的有时候，颜色会对不上
			  //ImageIO.write(im, formatName, output)
		 }catch (IOException e) {
				// TODO Auto-generated catch block
			  e.printStackTrace();
		 }
	}
	
	/*******************************截图全屏********************************/


	

}
