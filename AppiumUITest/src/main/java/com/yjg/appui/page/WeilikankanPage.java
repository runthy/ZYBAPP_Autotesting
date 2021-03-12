package com.yjg.appui.page;

import io.appium.java_client.android.AndroidElement;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.yjg.appui.base.AndroidDriverBase;

public class WeilikankanPage extends BasePage {
	public static String path = System.getProperty("user.dir");
	/***
	 * 全局声明： 1、driver对象是公共类AndroidDriverBase类的对象 2、this对象是父类BasePage类的对象
	 * */
	public static Logger logger = Logger.getLogger(LoginPage.class);

	public WeilikankanPage(AndroidDriverBase driver) {// 需要默认添加一个构造方法
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
		driver.sleep(5000);
		clickok2("cn.weli.story:id/image_close");
		clickok("cn.weli.story:id/bt_ok");
		for (int i = 0; i < 100; i++) {

			if (selectNews("cn.weli.story:id/tv_title") == true
					&& isRedPacket("id", "cn.weli.story:id/cd_view", "down") == true) {
				driver.sleep(4000);
				logger.info("存在红包图标");
				driver.swipeToUp(300, 1);// 三下手指往上滑动
				if(isRedPacket("id", "cn.weli.story:id/tv_height_more", "down")){
					this.click(this.reAndroidBy("id", "cn.weli.story:id/tv_height_more"));
				}else{
					logger.warn("没有更多阅读按钮");
				}
				// 循环阅读
				cycleSwipe(5);
				logger.info("新闻查看完毕");
				driver.sleep(5000);
				clickok2("cn.weli.story:id/image_close");
				//  mCurrentFocus=Window{35b10c93 u0 cn.weli.story/cn.etouch.ecalendar.MainActivity}
				// 点击刷新
				driver.swipeToUp(300, 2);
				
			} else {
				logger.warn("不存在红包图标");
//								this.click(reAndroidBy("id", "cn.weli.story:id/button1"));
				this.click(reAndroidBy("id", "cn.weli.story:id/button1"));
				driver.swipeToUp(300, 2);
			}
			System.out.println("第"+i+"次");
		}
	}
	public boolean  clickok(String idVulue){
		//cn.weli.story:id/bt_ok
				boolean flag = false;
				List<AndroidElement> newsLis = driver.findElementsById(idVulue);// 获取ImageView的所有

				if (newsLis.size() != 0) {
					AndroidElement news = newsLis.get(0);// 获取列表中第一个元素
					// news.click();//有时候会出现点击不到的情况
					logger.info("存在我知道了提示框 ");
					this.click(news);
	
					flag = true;
					return flag;
				} else {
					logger.warn("没有我知道了提示框：" + newsLis.size());
					return flag;
				}

			}
	
	
	public boolean  clickok1(String idValue1,String idValue2){
		//cn.weli.story:id/bt_ok
				boolean flag = false;
				List<AndroidElement> newsLis = driver.findElementsById(idValue1);// 获取ImageView的所有

				if (newsLis.size() != 0) {
					AndroidElement news = newsLis.get(0);// 获取列表中第一个元素
					// news.click();//有时候会出现点击不到的情况
					logger.info("存在提示框");
					this.click(news);

					flag = true;
					return flag;
				} else if(this.isElementExist(this.reAndroidBy("id", idValue2), 200)){
					//cn.weli.story:id/image_home
					System.out.println("存在提示框");
					this.click(this.reAndroidBy("id", "cn.weli.story:id/image_close"));
					logger.warn("没有提示框：" + newsLis.size());
					return flag;
				}else{
					logger.info("不存在提示框");
					return flag;
				}
			

			}
	
	public boolean  clickok2(String idValue1) throws InterruptedException{
		//cn.weli.story:id/bt_ok
		driver.sleep(3000);
				boolean flag = false;
//				List<AndroidElement> newsLis = driver.findElementsById(idValue1);// 获取ImageView的所有
				if(this.isElementExist(this.reAndroidBy("id", idValue1), 200)){
					//cn.weli.story:id/image_home
					System.out.println("存在image_home提示框");
					this.click(this.reAndroidBy("id", "cn.weli.story:id/image_close"));
					
					return flag;
				}else{
					logger.info("不存在image_home提示框");
					return flag;
				}
			

			}
	
	
	/** 查看视频
	 * @throws InterruptedException
	 */
	public void browserVideo() throws InterruptedException {
		//点击video
		this.click(this.reAndroidBy("id", "cn.weli.story:id/iv_tab_2"));
		for (int i = 0; i < 50; i++) {
			clickok("cn.weli.story:id/bt_ok");
			if (selectNews("cn.weli.story:id/video_idle") == true
					&& isRedPacket("id", "cn.weli.story:id/cd_view", "down") == true) {
				driver.sleep(4000);
				logger.info("存在红包图标");
				driver.swipeToUp(300, 1);// 三下手指往上滑动
				if(isRedPacket("id", "cn.weli.story:id/tv_height_more", "down")){
					this.click(this.reAndroidBy("id", "cn.weli.story:id/tv_height_more"));

				}else{
					logger.warn("没有更多阅读按钮");
				}
				// 循环阅读
				cycleSwipe(5);
				logger.info("新闻查看完毕");
				clickok("cn.weli.story:id/bt_ok");
				driver.pressBack();

//								this.click(reAndroidBy("id", "cn.weli.story:id/button1"));

				this.click(this.reAndroidBy("id", "cn.weli.story:id/iv_tab_2"));
				// 点击刷新
				driver.swipeToUp(300, 2);
			} else {
				logger.warn("不存在红包图标");
//								this.click(reAndroidBy("id", "cn.weli.story:id/button1"));
				driver.pressBack();

				this.click(this.reAndroidBy("id", "cn.weli.story:id/iv_tab_2"));
				driver.swipeToUp(300, 2);
			}
		}
	}
	
	/** 
	 * @decription 搜索新闻 
	 * @throws InterruptedException
	 */
	public void explorer() throws InterruptedException {
		//点击我的
		this.click(this.reAndroidBy("id", "cn.weli.story:id/rl_bottom_4"));
		//滑动
		driver.swipeToUp(500, 2);// 三下手指往上滑动
		//用list方法定位搜索新闻按钮
		List<AndroidElement> titleText = driver.findElementsById("cn.weli.story:id/rl_title");//获取title
		this.click(titleText.get(7));//点击第8个 搜索新闻
		//点击立即搜索
		this.click(this.reAndroidBy("id", "cn.weli.story:id/tv_action"));

		for(int i=1;i<=10;i++){

			List<AndroidElement> TextView = driver.findElementsByClassName("android.widget.TextView");//获取title
			System.out.println(TextView.size());	
			this.click(TextView.get(10));//随便点击一个
			if(i==10){
				//输入关键字
			this.sendkeys("id", "cn.weli.story:id/edt_tool_search", "鹿晗"+i);
					//点击搜索
			this.click(this.reAndroidBy("id", "cn.weli.story:id/view_search"));

		}
		//android.widget.TextView
			cycle();
		}
	}
	public static void cycle() throws InterruptedException{
			driver.sleep(5000);
			driver.clickByCoordinate(600, 800);
			driver.sleep(5000);
			driver.swipeToUp(500, 10);// 三下手指往上滑动
			driver.clickByCoordinate(600, 800);
			driver.sleep(5000);
			driver.swipeToUp(500, 10);// 三下手指往上滑动
			driver.swipeToDown(500, 10);// 三下手指往上滑动
			driver.pressBack();
	}
	
	// 往哪个方向查看某个元素是否存在 默认查看五次
	public boolean isRedPacket(String byType, String idValue, String derection) {
		boolean flag = false;// 设定一开始不存在红包
		if (driver.swipeUntilElementAppear(this.reAndroidBy(byType, idValue),
				derection, 300, 1) == true) {
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

	public boolean selectNews(String idVulue) {
		boolean flag = false;
		List<AndroidElement> newsLis = driver.findElementsById(idVulue);// 获取ImageView的所有
		if (newsLis.size() != 0) {
			AndroidElement news = newsLis.get(newsLis.size() - 1);// 获取列表中第一个元素
			logger.info("新闻数量【" + newsLis.size() + "】");
			// news.click();//有时候会出现点击不到的情况
			this.click(news);
			flag = true;
			return flag;
		} else {
			logger.warn("好像找不到新闻了,新闻数量为：" + newsLis.size());
			return flag;
		}

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
