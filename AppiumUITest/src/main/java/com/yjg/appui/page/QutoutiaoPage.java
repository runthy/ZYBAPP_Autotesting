package com.yjg.appui.page;

import io.appium.java_client.android.AndroidElement;

import java.util.List;

import org.apache.log4j.Logger;

import com.yjg.appui.base.AndroidDriverBase;

public class QutoutiaoPage extends BasePage {
	public static String path = System.getProperty("user.dir");
	/***
	 * 全局声明： 1、driver对象是公共类AndroidDriverBase类的对象 2、this对象是父类BasePage类的对象
	 * */
	public static Logger logger = Logger.getLogger(LoginPage.class);

	public QutoutiaoPage(AndroidDriverBase driver) {// 需要默认添加一个构造方法
		super(driver);
		// TODO Auto-generated constructor stub
	}

	// windows是aas mac上是aax
	// 将结合中的数据赋值给AndroidElement的对象news
	// 重复阅读是可以的赚积分的，但是还是要去取下重复比较好，以免被封杀
	// 点击的新闻要区分有金币图标才点击，如果没有图标不查看
	// 加入伪装，也读取政治新闻，也随机停留一段时间
	// 点击之后的页面检查，有提示框，有下载框，有广告页面
	// 启动的过程存在广告，要加载3s左右，然后默认到达刷新页面
	//
	public void browserNews() throws InterruptedException {

		for (int i = 0; i < 1000000; i++) {
			driver.sleep(1000);
			if (selectNews("com.jifen.qukan:id/aax","com.jifen.qukan:id/aas") == true
					&& isRedPacket("id", "com.jifen.qukan:id/awd", "down") == true) {
				driver.sleep(4000);
				logger.info("存在红包图标");
				// 循环阅读
				cycleSwipe(5);
				logger.info("新闻查看完毕");
				driver.pressBack();
				// 点击刷新
				driver.swipeToUp(500, 2);
			} else {
				logger.warn("不存在红包图标");
				driver.pressBack();
				driver.swipeToUp(500, 2);
			}
		}
	}

	// 往哪个方向查看某个元素是否存在 默认查看五次
	public boolean isRedPacket(String byType, String idValue, String derection) {
		boolean flag = false;// 设定一开始不存在红包
		if (driver.swipeUntilElementAppear(this.reAndroidBy(byType, idValue),
				derection, 500, 1) == true) {
			flag = true;
			return flag;
		} else {
			return flag;
		}
	}

	// 循环上下滑动
	public void cycleSwipe(int count) throws InterruptedException {
		for (int i = 0; i < count; i++) {
			driver.sleep(1000);
			driver.swipeToUp(500, 3);// 三下手指往上滑动
			driver.sleep(1000);
			driver.swipeToDown(500, 2);// 两下手指往上滑动
			logger.info("第[" + i + 1 + "]次");
		}
	}

	public boolean selectNews(String idVulue,String idVulue1) {
		boolean flag = false;
		List<AndroidElement> newsLis = driver.findElementsById(idVulue);// 获取ImageView的所有
		List<AndroidElement> newsLis1 = driver.findElementsById(idVulue1);															// //
																		// //windows是
																		// //
																		// mac上是aax
		if (newsLis.size() != 0) {
			AndroidElement news = newsLis.get(newsLis.size() - 1);// 获取列表中第一个元素
			logger.info("新闻数量【" + newsLis.size() + "】");
			// news.click();//有时候会出现点击不到的情况
			this.click(news);
			// if(this.click(news)){//AndroidElement类型
			// AndroidElement news2 = newsLis.get(1);// 获取列表中第一个元素
			// this.click(news2);
			// }
			flag = true;
			// logger.info("选中的新闻是：【"+news.getText()+"】");
			return flag;
		} else if (newsLis1.size() != 0) {
			
			
			
				AndroidElement news1 = newsLis1.get(newsLis1.size() - 1);// 获取列表中第一个元素
				logger.info("新闻数量【" + newsLis.size() + "】");
				// news.click();//有时候会出现点击不到的情况
				this.click(news1);
				// if(this.click(news)){//AndroidElement类型
				// AndroidElement news2 = newsLis.get(1);// 获取列表中第一个元素
				// this.click(news2);
				// }
				flag = true;

			}
		return flag;
			
		

	}

	public List<AndroidElement> getNewslist(String string) {
		List<AndroidElement> newsLis = driver.findElementsById(string);// 获取ImageView的所有元素
		return newsLis;

	}

	// 某个页面的新闻text string
	public void refesh(String string) throws InterruptedException {
		// 获取new的list
		List<AndroidElement> newsLis = getNewslist(string);
		AndroidElement news = newsLis.get(newsLis.size() - 1);
		// List<AndroidElement> newsLis = driver
		// .findElementsById("com.jifen.qukan:id/aax");// 获取ImageView的所有元素
		// 将结合中的数据赋值给AndroidElement的对象news
		// 重复阅读是可以的赚积分的，但是还是要去取下重复比较好，以免被封杀
		// 点击的新闻要区分有金币图标才点击，如果没有图标不查看
		//
		// AndroidElement news = newsLis.get(newsLis.size() - 1);// 获取列表中第一个元素
		if (newsLis.size() != 0) {
			news.click();
			if (this.isElementExist(
					this.reAndroidBy("id", "com.jifen.qukan:id/awg"), 10) == true) {
				for (int i = 0; i < 100; i++) {
					for (int i1 = 0; i1 < 20; i1++) {
						driver.sleep(200);
						driver.swipe("up", 800);
						driver.sleep(200);
						driver.swipe("down", 800);
					}
					// 点击返回
					this.click(this.reAndroidBy("id", "com.jifen.qukan:id/gb"));
					this.click(this.reAndroidBy("name", "刷新"));
					news.click();
					for (int i2 = 0; i2 < 20; i2++) {
						driver.sleep(200);
						driver.swipe("up", 800);
						driver.sleep(200);
						driver.swipe("down", 800);
					}
				}
			} else {
				logger.warn("不存在红包图标");
				this.click(this.reAndroidBy("id", "com.jifen.qukan:id/gb"));

			}

		} else {
			logger.warn("不存在新闻");
		}
	}
}
