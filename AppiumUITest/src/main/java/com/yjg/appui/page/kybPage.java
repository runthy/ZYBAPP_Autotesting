package com.yjg.appui.page;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.keyworddriver.ExcelUtil1;
import com.yjg.appui.util.GetByLocator;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.List;

public class kybPage extends BasePage {
	public static String path = System.getProperty("user.dir");
	/***
	 * 全局声明： 1、driver对象是公共类AndroidDriverBase类的对象 2、this对象是父类BasePage类的对象
	 * */
	public static Logger logger = Logger.getLogger(kybPage.class);

	public kybPage(AndroidDriverBase driver) {// 需要默认添加一个构造方法
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @description 等待登录页面元素加载
	 *            selenium api封装引用对象
	 * @param timeOut
	 *            等待元素超时的时间
	 * */
//	public void waitLoginPageLoad(int timeOut) {
//		this.pause(1000);
//		// 而登录的用户名、密码输入框以及登录按钮都在body frame下的navbar frame下,
//		logger.info("开始检查登录页面元素");
//		// 账号输入框
//		this.waitForElementToLoad(timeOut,
//				By.id("com.gatewang.yjg:id/loginAccountEt"));
//		// 密码输入框
//		this.waitForElementToLoad(timeOut, By.id("com.gatewang.yjg:id/pwdEt"));
//		// 点击登录
//		this.waitForElementToLoad(timeOut,
//				By.id("com.gatewang.yjg:id/quick_login_btn"));
//		logger.info("检查登录页面元素完毕");
//	}


	/**
	 * @throws InterruptedException
	 * 点击手机号码登陆   老用户登录
	 */
	public void login() throws Exception {
		//判断当前的activity是什么 再执行一下操作
		driver.sleep(10000);
		logger.info("begin....");
		driver.findElement(By.id("com.tal.kaoyan:id/login_email_edittext")).sendKeys("shizerun");
		driver.findElement(By.id("com.tal.kaoyan:id/login_password_edittext")).sendKeys("asd123456");
		driver.findElement(By.id("com.tal.kaoyan:id/login_login_btn")).click();
//		List<AndroidElement> loginBtn = driver
//				.findElementsByClassName("android.widget.ImageView");// 获取ImageView的所有元素
//		System.out.println("====ss==="+loginBtn.size());
//
//		AndroidElement loginbtn = loginBtn.get(2);// 获取列表中第一个元素
//		loginbtn.click();// 选择商品
//
//		//输入手机号码
//		//加入判断：如果为空就输入，如果不为空判断是否和输入的值一致，如果是进行下一步，如果不是输入输入值
//		List<AndroidElement> phoneNum = driver
//				.findElementsByClassName("android.widget.EditText");// 获取ImageView的所有元素
//		AndroidElement phone = phoneNum.get(0);// 获取列表中第一个元素
//		phone.sendKeys("13533013009");
//
//		//点击登陆
//		List<AndroidElement> loginB = driver
//				.findElementsByClassName("android.view.View");// 获取ImageView的所有元素
//		AndroidElement btn = loginB.get(0);// 获取列表中第一个元素
//		btn.click();
//
//		//输入验证码
//		List<AndroidElement> yanzhengma = driver
//				.findElementsByClassName("android.view.View");// 获取ImageView的所有元素
//		AndroidElement yzm = yanzhengma.get(2);// 获取列表中第一个元素
//		yzm.sendKeys("1111");
//		if(loginCheck()==true){
//			System.out.print("登录成功");
//		}else{
//			System.out.print("登录失败");
//
//		}
		//检查首页页面元素：
		//点击登陆
		//0 是我的个人头像  检查是否对应登陆的头像如果是，成功，如果不是失败
//		driver.sleep(1000);
//		AndroidElement c2 = check.get(1);// 获取列表中第一个元素
////		c2.getText();
//		c2.click();
//		System.out.println("c2="+c2.getText());
	}

	/**
	 * 广场-发布动态功能  square
	 * dynamiclist 动态列表 dynamic动态
	 * @throws InterruptedException
	 */
	//
	public void sendTextMessage() throws InterruptedException {
		//点击动态广场
		List<AndroidElement> dynamiclist = driver
				.findElementsByClassName("android.widget.TextView");// 获取ImageView的所有元素
		System.out.println("此类控件存在个数"+dynamiclist.size());
		driver.sleep(1000);
		//点击动态广场
		AndroidElement dynamic = dynamiclist.get(13);// 获取列表中第一个元素
		System.out.println("文本="+dynamic.getText());
		dynamic.click();
		driver.sleep(1000);
		//点击编辑按钮
		List<AndroidElement> loginBtn = driver.findElementsByAndroidUIAutomator("new UiSelector().className("+"android.view.View"+").index(2)");
		System.out.println("====此类控件存在个数==="+loginBtn.size());
		AndroidElement lbtn= loginBtn.get(4);// 获取列表中第4个元素
		lbtn.click();
		//输入文本信息
		this.sendkeys("className","android.widget.EditText","test");
		//点击发布
		List<AndroidElement> send = driver
				.findElementsByClassName("android.widget.TextView");// 获取ImageView的所有元素
		System.out.println("此类控件存在个数"+send.size());
		driver.sleep(1000);
		AndroidElement s = send.get(1);// 获取列表中第一个元素
		System.out.println("发送按钮文本="+s.getText());
		s.click();
		//检查有没发布成功
		List<AndroidElement> square = driver
				.findElementsByClassName("android.widget.TextView");// 获取ImageView的所有元素
		AndroidElement s1 = square.get(11);// 获取列表中第一个元素

		if(s1.getText().equals("test")==true){
			System.out.println("发布成功："+s1.getText());

		}else{
			System.out.println("发布失败："+s1.getText());

		}

		//android.widget.TextView-11-test

		//检查这个 发布的id是发送第一个？检查内容 是否存在在list中 ；android.widget.TextView-8-穿越的外星人

		//发布的图片的动态  com.shenqi.app.client:id/iv_picture

	}
	public void sendPicMessage() throws InterruptedException {
		//点击动态广场
		List<AndroidElement> dynamiclist = driver
				.findElementsByClassName("android.widget.TextView");// 获取ImageView的所有元素
		System.out.println("此类控件存在个数"+dynamiclist.size());
		driver.sleep(1000);
		//点击动态广场
		AndroidElement dynamic = dynamiclist.get(13);// 获取列表中第一个元素
		System.out.println("文本="+dynamic.getText());
		dynamic.click();
		driver.sleep(1000);
		//点击编辑按钮
		List<AndroidElement> loginBtn = driver.findElementsByAndroidUIAutomator("new UiSelector().className("+"android.view.View"+").index(2)");
		System.out.println("====此类控件存在个数==="+loginBtn.size());
		AndroidElement lbtn= loginBtn.get(4);// 获取列表中第4个元素
		lbtn.click();
		//点击发送图片

		List<AndroidElement> sendpic = driver
				.findElementsByClassName("android.widget.TextView");;
		System.out.println("====此类控件存在个数==="+sendpic.size());
		AndroidElement sp= loginBtn.get(8);// 获取列表中第4个元素
		sp.click();
//		//输入图片信息
		List<AndroidElement> picList = driver
				.findElementsByClassName("com.shenqi.app.client:id/check");
		System.out.println("====此类控件存在个数==="+picList.size());
		AndroidElement pic= picList.get(2);// 获取列表中第4个元素
		pic.click();
//
//		//点完成com.shenqi.app.client:id/picture_tv_ok
		this.click(this.reAndroidBy("id","com.shenqi.app.client:id/picture_tv_ok"));
//
////		this.sendkeys("className","android.widget.EditText","test");
//		//点击发布
		List<AndroidElement> send = driver
				.findElementsByClassName("android.widget.TextView");// 获取ImageView的所有元素
		System.out.println("此类控件存在个数"+send.size());
		driver.sleep(1000);
		AndroidElement s = send.get(1);// 获取列表中第一个元素
		System.out.println("发送按钮文本="+s.getText());
		s.click();
		//检查有没发布成功
		List<AndroidElement> square = driver
				.findElementsByClassName("android.widget.TextView");// 获取ImageView的所有元素
		AndroidElement s1 = square.get(11);// 获取列表中第一个元素

		if(s1.getText().equals("test")==true){
			System.out.println("发布成功："+s1.getText());

		}else{
			System.out.println("发布失败："+s1.getText());

		}

		//android.widget.TextView-11-test

		//检查这个 发布的id是发送第一个？检查内容 是否存在在list中 ；android.widget.TextView-8-穿越的外星人

		//发布的图片的动态  com.shenqi.app.client:id/iv_picture

	}

		//判断整个页面存在的元素是什么，然后打印出来
	public   void PageCheck(String className) throws InterruptedException {
		driver.sleep(1000);

		List<AndroidElement> check = driver
				.findElementsByClassName(className);// 获取ImageView的所有元素
		System.out.println("此类控件存在个数"+check.size());

		driver.sleep(1000);
		for(int i=0;i<check.size();i++){
			AndroidElement c1 = check.get(i);// 获取列表中第一个元素
			System.out.println(className+"-"+i+"-"+c1.getText());
		}



	}

		/**
         * 选择账号密码登陆  老用户登陆
         *
         * @throws Exception
         *
         * */
	
	public void passwordLogin() throws Exception {
		//选择账号密码登陆
		driver.sleep(10000);
		//选择登陆/注册栏
		List<AndroidElement> zhBtn = driver
				.findElementsByClassName("android.view.View");// 获取ImageView的所有元素
		AndroidElement loginbtn = zhBtn.get(4);// 获取列表中第5个元素
		loginbtn.click();
		//选择账号密码登录按钮
		List<AndroidElement> zhmmBtn = driver
				.findElementsByClassName("android.widget.ImageView");// 获取ImageView的所有元素
		System.out.println("此类控件存在个数"+zhmmBtn.size());
		AndroidElement zhmmBtnt = zhmmBtn.get(0);// 获取列表中第1个元素
		zhmmBtnt.click();
		//输入账号
		List<AndroidElement> userInput = driver.findElementsByAndroidUIAutomator("new UiSelector().className("+"android.widget.EditText"+").index(0)");
		System.out.println("此类控件存在个数"+userInput.size());
		AndroidElement user = userInput.get(0);// 获取列表中第1个元素
		user.sendKeys("13533013009");//
		//输入密码
		AndroidElement pwd = userInput.get(1);// 获取列表中第2个元素
		pwd.sendKeys("1221212");//
		//点击登陆
		List<AndroidElement> loginBtn = driver.findElementsByAndroidUIAutomator("new UiSelector().className("+"android.view.View"+").index(0)");
		System.out.println("====此类控件存在个数==="+loginBtn.size());
		AndroidElement lbtn= loginBtn.get(4);// 获取列表中第4个元素
		lbtn.click();
		if(loginCheck()==true){
			System.out.print("登录成功");
		}else{
			System.out.print("登录失败");

		}



	}


	public void login5(String username, String password,String caseNumber) throws Exception {// HomePage登录后调整到首页
		logger.info("開始執行登錄測試用例。。。");

		if (driver.isExitCurActivity(".ui.activity.HomeTabActivity") == true) {
			logger.info("当前页面是首页");
			//点击我的tab功能
			this.click(GetByLocator.getLocator("loginOrReg"));
			//点击头像按钮
			this.click(GetByLocator.getLocator("loginTouxiang"));
			// 输入账号和密码，点击登录
			LonginInput(username, password,caseNumber);
		}if (driver.isExitCurActivity(".ui.activity.ucenter.LoginActivity") == true)
		{
			logger.info("当前页面是登录页面");
			// 输入账号和密码，点击登录

			LonginInput(username, password,caseNumber);

		}else{

			logger.info("当前页面不支持登录");

		}
	}

	/**
	 * 登录后页面检查
	 * 
	 * @throws Exception
	 * */
	// mCurrentFocus=Window{138c5893 u0
	// com.gatewang.yjg/com.gatewang.yjg.module.common.YJGMainActivity}

	public boolean loginCheck() throws Exception {
		boolean flag = false;// false 登录失败 true 登录成功
		//com.tal.kaoyan/com.tal.kaoyan.ui.activity.HomeTabActivity}
			if (driver.isExitCurActivity("ui.activity.HomeTabActivity") == true) {// 检查是否调zhuang到首页
				logger.info("当前activity是:" + driver.getCurActivity());
				logger.info("登录成功");
				flag = true;//
				return flag;// 登录成功
			}else{
				// TODO: handle exception
				logger.info("没有登录成功");
				// toastCheck();
			return flag;// 登录成功
			}
	}

	/**
	 * 登录框输入账号密码，并且点击登录
	 * 
	 * @throws Exception
	 * */
	public void LonginInput(String username, String password,String caseNumber) throws Exception {

		//登录页面的activity com.tal.kaoyan/com.tal.kaoyan.ui.activity.ucenter.LoginActivity

		if (driver.isExitCurActivity(".ui.activity.ucenter.LoginActivity") == true) {
			logger.info("还没有任何账号登录，输入信息，登录中。。。");
			// 检查登录框元素是否存在
//			waitLoginPageLoad(10);
			// 输入账号
			this.sendkeys("id", "com.tal.kaoyan:id/login_email_edittext", username);// 尝试新的输入方法
			// 输入密码
			this.sendkeys("id", "com.tal.kaoyan:id/login_password_edittext", password);

			// 点击登录
			this.click(this.reAndroidBy("id",
					"com.tal.kaoyan:id/login_login_btn"));
//			driver.sleep(1000);
			if (loginCheck() == true) {// 登录成功
				logger.info("首页状态是：" + loginCheck());
				ExcelUtil1 excel=new ExcelUtil1("configs/test.xlsx", "Sheet1");
				excel.setCellData(Integer.valueOf(caseNumber), 6, "测试成功");
				logger.info("["+caseNumber+"]"+"测试用例，测试成功");
			}
			else {
				this.takescreen("登录失败");
				Assert.fail("Failed to login  [" + "没有登录成功" + "]");//失败了就直接调用监听类，后面方法都不执行
				logger.info("["+caseNumber+"]"+"测试用例，测试失败");
				ExcelUtil1 excel=new ExcelUtil1("configs/test.xlsx", "Sheet1");
				excel.setCellData(Integer.valueOf(caseNumber), 6, "测试失败");
//				Assert.fail("Failed to login  [" + "没有登录成功" + "]");//失败了就直接调用监听类，后面方法都不执行
			}

		} else {
			logger.info("账号已经登录");
		}
	}

	/**
	 * 重新登录
	 * 
	 * @throws Exception
	 * */
	public void reToLogin(String username, String password,String caseNumber) throws Exception {
		System.out.printf("========================Thead Id :%s%n", Thread
				.currentThread().getId());
		// 注销登录
		logout();
		// 输入账号和密码，点击登录
		LonginInput(username, password,caseNumber);
		// 再次登录
//		login(username, password,caseNumber);
	}

	/**
	 * 登出操作
	 * */
	public void logout() throws InterruptedException {
		// 第一个页面是从首页开始
		// 点击我的
		System.out.printf("========logout================Thead Id :%s%n",
				Thread.currentThread().getId());

		this.click(GetByLocator.getLocator("loginOrReg"));
		// 判断是否弹出登录提示框
		if (driver.isExitCurActivity(".activity.PwdForLoginActivity") == false) {
			logger.info("已经存在登录中账号");
			// 鼠标向上滑动
			driver.swipe("up", 500);
			// //判断是否存在注销登录按钮
			// isDisplayed=isElementExist判断元素是否存在，是否显示
			if (this.isElementExist(this.reAndroidElement("name", "注销登录")) == true) {
				// 点击注销按钮
				this.click(By.name("注销登录"));
				// this.click(GetByLocator.getLocator("loginOut"));
				// 点击确定按钮退出
				this.click(By.name("确定"));
				logger.info("退出成功");
				// System.out.println("退出成功");
			} else {
				logger.info("未知异常");
			}
		} else {
			logger.info("没有登录");
		}
	}

	/**
	 * 首次登录页面操作 封装滑动页面
	 * 
	 * @throws InterruptedException
	 * */
	public boolean guidePageSwipe() throws InterruptedException {
		/**
		 * 引导页的activity mCurrentFocus=Window{125e822f u0
		 * com.gatewang.yjg/com.gatewang.yjg.ui.activity.GuideActivity}
		 * */
		// driver.sleep(4000);
		boolean flag = true;// 定义一开始是有的
		if (driver.isExitCurActivity(".activity.GuideActivity") == true) {
			driver.swipe("left", 500);
			this.click(By.className("android.widget.ImageView"));
			return flag;
		} else {
			logger.info("不存在引导页");
			flag = false;
			return flag;
		}

	}

	/** ========需要用到手机验证码============ */
	// 快捷登录
	// 注册账号
	// 忘记密码
	/** ========需要用到验证码============ */

	/***
	 * @author Neo.shi
	 * @Abstract 异常检测 在BasePage中有
	 * 
	 * */

}
