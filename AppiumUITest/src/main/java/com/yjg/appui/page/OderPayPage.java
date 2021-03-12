package com.yjg.appui.page;

import io.appium.java_client.android.AndroidElement;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.keyworddriver.ExcelUtil1;
import com.yjg.appui.util.GetByLocator;

public class OderPayPage extends BasePage {
	public static String path = System.getProperty("user.dir");
	/***
	 * 全局声明： 1、driver对象是公共类AndroidDriverBase类的对象 2、this对象是父类BasePage类的对象
	 * */
	public static Logger logger = Logger.getLogger(LoginPage.class);

	public OderPayPage(AndroidDriverBase driver) {// 需要默认添加一个构造方法
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @description 等待登录页面元素加载
	 * @param seleniumUtil
	 *            selenium api封装引用对象
	 * @param timeOut
	 *            等待元素超时的时间
	 * */
	public void waitOrderPageLoad(int timeOut) {
		// 而登录的用户名、密码输入框以及登录按钮都在body frame下的navbar frame下,
		logger.info("开始检查登录页面元素");
		// 首页的元素
		this.waitForElementToLoad(timeOut, By.name("首页"));

		// 查看消息按钮
		this.waitForElementToLoad(timeOut, By.name("消息"));
		// 查看订单按钮
		this.waitForElementToLoad(timeOut, By.name("订单"));
		// 查看订单按钮
		this.waitForElementToLoad(timeOut, By.name("我的"));
		logger.info("检查登录页面元素完毕");
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

	// 通过选择一个门店（普通，中天，或者快捷支付门店）自动进行付款；
	public void orderPay(String username, String loginpwd, String numpwd,
			int num) throws Exception {
		// 如果没有登录就珍惜登录，如果已经登录就直接下单
		// 点击首页
		this.click(this.reAndroidBy("name", "首页"));
		waitOrderPageLoad(10);
		// 鼠标向上滑动
		driver.swipe("up", 500);
		// 选择第一家门店
//		 selectStoreName(num);
		if (selectStoreId(num) == true) {
			 // 点击添加商品
			List<AndroidElement> goodsLis = driver
					.findElementsById("com.gatewang.yjg:id/add");// 获取ImageView的所有元素
			// // goodsLis商品数量判断
			logger.info("商品数量为:" + goodsLis.size());
			if (goodsLis.size() != 0) {
				// // 选择第一个商品
				AndroidElement goods = goodsLis.get(goodsLis.size() - 1);// 获取列表中第一个元素
				logger.info("选择的商品"+ goods.getText());
				goods.click();// 选择商品
				// // 点击去结算按钮
				this.click(this.reAndroidBy("name", "去结算"));
				// 判断是否存在登录提示框
				boolean flag = loginToast(username, loginpwd);
				if (flag == false) {// 没登录过，登录后再次点击去结算按钮
					this.click(this.reAndroidBy("name", "去结算"));
				}
			}

			/*
			 * 购买中天礼包的商品流程，但是可以独立封装填写资料的页面 mCurrentFocus=Window{16b15f4e u0
			 * com.gatewang
			 * .yjg/com.gatewang.yjg.ui.activity.PartnerSubmitActivity}
			 */
			if (driver.currentActivity().contains(".activity.PartnerSubmitActivity") == true) {
				logger.info("中天门店支付方式。。。。");
				// 中天门店
				ZTStores();
			} else if (driver.isElementExist(
					this.reAndroidBy("id", "com.gatewang.yjg:id/tvQuickPay"), 10) == true) {
				// 快捷支付门店
				QuickPay(username, loginpwd, numpwd);
			} else {
				logger.info("普通支付方式。。。。");
				// 普通门店
				ordinaryStores(loginpwd, numpwd);
			}
		}
		else {
			logger.info("商品为空,请重新选择");
			Assert.fail("失败原因：[商品为空,请重新选择]");

			// 没有存在商品
		}


	}

	// 选择门店
	public boolean selectStoreId(int num) throws Exception {
		// 需要判断如果刷新不出来该元素，需要等待
		// 点击一家门店
		boolean flag = false;
		List<AndroidElement> storeLis = driver
				.findElementsById("com.gatewang.yjg:id/home_list_name");// 获取ImageView的所有元素
		if (!storeLis.isEmpty()) {
			AndroidElement store = storeLis.get(num - 1);// 获取列表中第一个元素
			logger.info("选择的门店：" + store.getText());
			store.click();
			flag = true;
			return flag;
		} else {
			logger.info("门店没有加载出来。。请检查网络设置。。");
			takescreen("没有加载门店");
			Assert.fail("失败原因：[门店没有加载出来。。请检查网络设置。。]");

			return flag;
		}

	}

	// 根据门店名称选择
	public static void selectStoreName(String name) {
		// 点击一家门店
		driver.findElement(By.name(name)).click();
	}

	/**
	 * 输入账号名和密码
	 * @throws Exception
	 * */
	public boolean loginToast(String name, String loginpwd) throws Exception {
		boolean flag = false;
		// 判断是否存在登录框 //如果存在调用登录模块 mCurrentFocus=Window{1a033f0d u0
		// com.gatewang.yjg/com.gatewang.yjg.ui.activity.PwdForLoginActivity}
		if (driver.currentActivity().contains(".activity.PwdForLoginActivity") == true) {
			// 输入账号
			this.sendkeys("id", "com.gatewang.yjg:id/loginAccountEt", name);
			// 输入密码
			this.sendkeys("id", "com.gatewang.yjg:id/pwdEt", loginpwd);
			// 点击登录
			this.click(this.reAndroidBy("id",
					"com.gatewang.yjg:id/quick_login_btn"));
			if (driver.currentActivity().contains(".activity.PwdForLoginActivity") == false) {
				logger.info("登录成功");
			}else{
				//检查原因
				takescreen("支付过程中需要登录，但是登录失败");
				logger.info("登录失败");
				Assert.fail("失败原因：[登录失败]");

				toastCheck();
				//可以选择点击返回，也可以选择在此界面截图
			}

			return flag;// false 标记为一开始是未登录的
		} else {
			logger.warn("该账号已经登录成功");
			flag = true;
			return flag;
		}
	}

	// 普通中天礼包门店
	public void ZTStores() throws Exception {
		// 输入姓名
		List<AndroidElement> lis = driver
				.findElementsByClassName("android.widget.EditText");// 获取ImageView的所有元素
		AndroidElement targetEle1 = lis.get(1);// 获取列表中第一个元素
		targetEle1.sendKeys("test1");
		// 输入身份证
		AndroidElement targetEle2 = lis.get(3);// 获取列表中第一个元素
		targetEle2.sendKeys("test2");
		// 点击邮箱
		AndroidElement targetEle3 = lis.get(4);// 获取列表中第一个元素
		targetEle3.sendKeys("test3");
		// 点击去结算
		this.click(this.reAndroidBy("name", "确认支付信息并支付"));
		logger.info("填写信息完成");
		/**
		 * 支付页面 mCurrentFocus=Window{2e3ad03f u0
		 * com.gatewang.yjg/com.gatewang.yjg.ui.activity.PayActivity}
		 * */
		driver.sleep(1000);
		if (driver.currentActivity().contains(".activity.PayActivity") == true) {
			// 点击全账户支付
			this.click(this.reAndroidBy("name", "全账户支付"));
			// 点击确定
			this.click(this.reAndroidBy("name", "确定"));
		}
		// 执行支付
		inputPayNum("asd123456", "123456");


	}

	/**
	 * 点击支付，如果没有设置过密码，调整至密码设置页面，设置过密码直接执行登录 输入支付密码的过程
	 *
	 * @throws Exception
	 * */
	public void inputPayNum(String loginpwd, String numpwd) throws Exception {
		/*
		 * mCurrentFocus=Window{2b7459f4 u0
		 * com.gatewang.yjg/com.gatewang.yjg.ui.activity.PayPwdActivity}
		 */
		if (driver.currentActivity().contains("activity.PayPwdActivity") == true) {
			System.out.println("没有设置过密码");
			// 输入登录密码
			this.sendkeys(this.reAndroidBy("id",
					"com.gatewang.yjg:id/zgpaypwd_fl_et_login_pwd"), loginpwd);
			// 输入支付密码
			this.sendkeys(this.reAndroidBy("name",
					"com.gatewang.yjg:id/zgpaypwd_fl_et_pay_pwd"), numpwd);

			// 再次输入支付密码
			this.sendkeys(this.reAndroidBy("id",
					"com.gatewang.yjg:id/zgpaypwd_fl_et_pay_pwd_comfirm"),
					numpwd);

			// 点击确定
			this.click(this.reAndroidBy("name", "确定"));
			// 点击确定
			this.click(this.reAndroidBy("name", "确定"));
			logger.info("正在输入密码。。");
			// 输入支付密码
			this.sendkeys(this.reAndroidBy("id",
					"com.gatewang.yjg:id/passedt_layout"), numpwd);
			System.out.println("密码输入完成支付调整中");
		} else {
			logger.info("正在输入密码。。。");
			// 输入支付密码
			this.sendkeys(this.reAndroidBy("id",
					"com.gatewang.yjg:id/passedt_layout"), numpwd);
			logger.info("密码输入完成。。");
			// 是否存在密码异常的情况
			payNumCheck();
			//支付后检测下是否成功
			payCheck();
		}
	}

	// 支付有个响应过程
	// Thread.sleep(3000);
	/*
	 * mCurrentFocus=Window{254afe00 u0
	 * com.gatewang.yjg/com.gatewang.yjg.ui.activity.PayResultActivity}
	 */
	public void payCheck() throws Exception {
		driver.sleep(3000);
		//PayResultActivity 成功支付后的结果页面
		if (driver.currentActivity().contains(".activity.PayResultActivity") == true) { // 查看是否存在支付成功字眼
			logger.info("已调整到支付成功结果页面");
			if (driver.findElement(By.id("com.gatewang.yjg:id/success"))//成功支付字段
					.isDisplayed() == true) {
				logger.info("支付成功");
				// 点击查看订单
				this.click(this.reAndroidBy("name", "查看订单"));
				// 如果存在交易完成界面
				if (driver.findElement(
						By.id("com.gatewang.yjg:id/ll_order_state"))
						.isDisplayed() == true) {
					System.out.println(driver.findElement(
							By.id("com.gatewang.yjg:id/ll_order_state"))
							.getText());
					// 往下拉动一些页面
					driver.swipe("up", 500);
					System.out.println("订单id为:"
							+ driver.findElement(
									By.id("com.gatewang.yjg:id/tv_order_id"))
									.getText());
					// 点击返回。回到订单页面
					this.click(this.reAndroidBy("id",
							"com.gatewang.yjg:id/left_ll"));
				} else if (driver.findElement(
						By.id("com.gatewang.yjg:id/tv_order_state"))
						.isDisplayed() == true) {
					// autoUtil.driver.findElement(By.id("com.gatewang.yjg:id/tv_order_state")).getText()
					System.out.println("订单id为:"
							+ driver.findElement(
									By.id("com.gatewang.yjg:id/tv_order_id"))
									.getText());
					// 点击返回。回到订单页面
					this.click(this.reAndroidBy("id",
							"com.gatewang.yjg:id/left_ll"));
				} else {
					System.out.println("未知错误");
				}
			} else if (driver.findElement(By.name("支付失败")).isDisplayed() == true) {
				System.out.println("支付失败");
				// 截图操作
				takescreen("支付失败");
			} else {
				System.out.println("支付异常");
				// 截图操作
				takescreen("支付异常");
			}
		} else {
			logger.info("没有正常跳转到支付结果页面");
			takescreen("没有正常跳转到支付结果页面");
			CardView();
			Assert.fail("Failed to payCheck  [" + "没有正常跳转到支付结果页面" + "]");

		}
	}

	// 支付密码输入后异常返回异常信息
	public void payNumCheck() throws Exception {
		driver.sleep(3000);
		if(this.getText(this.reAndroidBy("id", "com.gatewang.yjg:id/msg")).equals("正在加载…")){
			//如果找到是正在加载继续等待
				driver.sleep(3000);
		}else if (this.isElementExist(this.reAndroidBy("id", "com.gatewang.yjg:id/CardView_2"), 30)) {
			takescreen("密码支付异常截图");
			/****************************************/
			// 异常提示框
			CardView();
			Assert.fail("Failed to payNumCheck  [" +CardView()+ "]");
			/****************************************/
		}else{
			// 检查输入密码后是否跳转
			logger.info("支付响应过快，支付过程中暂没有检测到异常，请查看支付后跳转检测");
		}
	}

	public boolean inputNumCheck() throws Exception {
		boolean flag=false;
		try{
			//检查是否存在提示框
			if (this.isElementExist(this.reAndroidBy("id", "com.gatewang.yjg:id/CardView_2"), 10)) {
				takescreen("密码支付异常截图["+this.getText(this.reAndroidBy("id", "com.gatewang.yjg:id/CardView_2"))+"]");
				// 异常提示框检查
				CardView();
				flag=true;
				return flag;
				}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("inputNumCheck"+e);
		}
		return flag;


	}

	// 支付异常提示框封装
	public boolean  CardView() throws InterruptedException {
		// 原因是：检测到你的账号在短时间内多次进行错误请求，已被临时加入异常名单！23小时13分钟后移除！
		// 支付密码不正确
		// 余额不足
		// 添加快捷支付订单失败，不允许跨地区消费！
		boolean flag=false;
		if (driver.findElement(By.id("com.gatewang.yjg:id/CardView_2"))
				.isDisplayed() == true) {
			System.out.println("原因是："
					+ driver.findElement(By.id("com.gatewang.yjg:id/msg"))
							.getText());

			flag=true;
			return flag;
		} else {
			System.out.println("未知原因");
			return flag;//没有提示框
		}

	}

	// 普通类型门店
	public void ordinaryStores(String loginpwd, String numpwd) throws Exception {
		/*
		 * 普通支付方式，确认订单页面 mCurrentFocus=Window{32c1e64 u0 com.gatewang.yjg
		 * /com.gatewang.yjg.module.home.ShopHomeActivity}
		 */
		Thread.sleep(1000);
		if (driver.currentActivity().contains(".home.ShopHomeActivity") == true) {
			logger.info("确认订单页面");
			// 点击送出时间
			this.click(this.reAndroidBy("id",
					"com.gatewang.yjg:id/rl_send_or_takeup"));
			// 选择时间 android:id/text1
			List<AndroidElement> TimeLis = driver
					.findElementsByClassName("android.widget.TextView");// 获取ImageView的所有元素
			AndroidElement time = TimeLis.get(4);// 获取列表中第一个元素
			if(TimeLis.size()!=0){
				time.click();
				logger.info("选择的送货时间为：" + time.getText());
				// 点击确认支付
				this.click(this.reAndroidBy("name", "确认支付"));
				// autoUtil.clickByName("确认支付");
				Thread.sleep(2000);
				// 普通支付
//				ordinaryPay(loginpwd, numpwd);
				payCheck();
			}else{
				logger.info("选择的送货时间为空：" + time.getText());
//				Assert.fail("选择的送货时间为空");
			}


		} else {
			logger.info("没有跳转至确认订单页面");
			// 截图
			takescreen("没有跳转至确认订单页面");
		}
	}

	// 普通支付方式
	public void ordinaryPay(String loginpwd, String numpwd) throws Exception {
		// double paynum = 0;
		/*
		 * 支付页面 mCurrentFocus=Window{33b922b6 u0 com.gatewang.yjg/com
		 * .gatewang.yjg.ui.activity.PayActivity}
		 */
		if (driver.currentActivity().contains("activity.PayActivity") == true) {
			// 选择消费账号或者赠送账号
			driver.sleep(1000);
			this.click(this.reAndroidBy("id", "com.gatewang.yjg:id/checkbox2"));
			// 需要支付的金额
			float payamount = Float.valueOf(this.getText(this.reAndroidBy("id",
					"com.gatewang.yjg:id/payamount")));
			System.out.println("需要支付的金额" + payamount);
			// if (accounttype=="赠送账号") {
			// // 现有的积分数额
			// String str_jifen = autoUtil
			// .getIdText("com.gatewang.yjg:id/jifen");
			// int jlast = str_jifen.length();
			// double jifen = new DecimalFormat().parse(
			// str_jifen.substring(3, jlast)).doubleValue();
			// // paynum = jifen;
			// System.out.println("转化后积分为" + paynum);
			// } // 消费账号支付
			// else {
			// 现有的现金数额
			String str_xinjin = this.getText(this.reAndroidBy("id",
					"com.gatewang.yjg:id/xianjin"));
			// .getIdText("com.gatewang.yjg:id/xianjin");
			int xlast = str_xinjin.length();
			// 现金数额为 ? 3,207.26 从？ 开始截取
			// System.out
			// .println("去掉？的现金数额为====:" + str_xinjin.substring(3, last));
			// float xianjin1=Float.valueOf(str_xinjin.substring(2, last));
			// 只能将此类型转车成double类型，因为可能存在千分位符号
			double xianjin = new DecimalFormat().parse(
					str_xinjin.substring(3, xlast)).doubleValue();
			System.out.println("转化后现金为" + xianjin);
			if (xianjin >= payamount) {
				// 点击确定按钮
				this.click(this.reAndroidBy("name", "确定"));
				inputPayNum(loginpwd, numpwd);//输入支付密码
				payCheck();
				// 密码错误的用例
			} else {
				System.out.println("支付金额不够，请重置");
				takescreen("支付失败");
				Assert.fail("Failed to ordinaryPay");

				// 截图
			}
		}

	}

	// 快捷支付方式
	public void QuickPay(String username, String loginpwd, String numpwd)
			throws Exception {
		logger.info("这是快捷支付方式支付。。。");
		this.click(this.reAndroidBy("id", "com.gatewang.yjg:id/tvQuickPay"));
		// 判断是否登录过
		boolean flag1 = loginToast(username, loginpwd);
		// false 标记为一开始是未登录的
		if (flag1 == false) {
			// 再次选择门店
			selectStoreId(0);
			this.click(this.reAndroidBy("id", "com.gatewang.yjg:id/tvQuickPay"));
		}
		/**
		 * mCurrentFocus=Window{1292149a u0
		 * com.gatewang.yjg/com.gatewang.yjg.ui.activityZgPrepayActivity}
		 * */
		// -----判断 快捷支付按钮是否存在
		if (driver.currentActivity().contains("activity.ZgPrepayActivity")) {
			driver.sleep(2000);
			// 输入消费金额
			this.sendkeys("name", "询问商家后输入", "10");
			// 点击去支付
			this.click(this.reAndroidBy("name", "去支付"));
			driver.sleep(2000);
			// 普通支付
//			ordinaryPay(loginpwd, numpwd);

		} else {
			System.out.println("没有进入支付页面");
		}// -----判断 快捷支付按钮是否存在--------

		/**
		 * mCurrentFocus=Window{46e07e9 u0
		 * com.gatewang.yjg/com.gatewang.yjg.module.home.ShopHomeActivity}
		 * */
	}


	/***
	 * 异常框获取
	 * */
	public boolean toastCheck() throws InterruptedException {
		driver.sleep(4000);
		boolean flag = false;// 定义没有提示框
		try {
			if (this.isElementExist(this.reAndroidBy("id",
				"com.gatewang.yjg:id/loading"),20) == true) {
//			if (this.isElementExist(this.reAndroidElement("id","com.gatewang.yjg:id/loading")) == true) {
				//com.gatewang.yjg:id/content_text 网络异常的text
				logger.info("提示框内容: ["
						+ this.getText(this.reAndroidBy("id",
								"com.gatewang.yjg:id/content_text")) + "]");
				//点击确定，元素是否存在如果存在就点击，不存在就不点击
				this.click(this.reAndroidBy("name", "确定"));
				flag = true;
				return flag;
			} else if (this.isElementExist(this.reAndroidBy("id",
					"com.gatewang.yjg:id/CardView_2"),10) == true) {
				logger.info("提示框内容: ["
						+ this.getText(this.reAndroidBy("id",
								"com.gatewang.yjg:id/msg")) + "]");
				//点击确定，元素是否存在如果存在就点击，不存在就不点击
				this.click(this.reAndroidBy("name", "确定"));
				flag = true;
				return flag;
			}else{
				logger.info("没有捕获异常信息，请看log信息.");
			}
		}catch (Exception e) {
			// TODO: handle exception
			logger.info("没有找到提示框元素");
		}
		return flag;

	}

}
