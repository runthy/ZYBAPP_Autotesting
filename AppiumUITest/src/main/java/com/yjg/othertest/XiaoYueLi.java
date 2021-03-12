package com.yjg.othertest;

import io.appium.java_client.android.AndroidElement;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.base.AndroidSpecific;
import com.yjg.appui.base.CrazyPath;
import com.yjg.appui.util.ProUtil;

public class XiaoYueLi {
	public AndroidDriverBase driver;
	//需要先装好app
	//本地化测试的需求，设置功能的功能设置
	@BeforeClass
	public void startApp() throws Exception{
		String udid="HC46RWW09192"; //设备的id
		String input=AndroidSpecific.getDefaultInput(udid); //获取input输入法
		ProUtil p=new ProUtil(CrazyPath.globalPath);//caps的路径 ,获取下面的sever
		String server=p.getPro("server");
		String port="4723";//端口号
		String capsPath=CrazyPath.capsPath;//caps的路径 
		driver=new AndroidDriverBase(server, port, capsPath, udid, input);//调用启动的
		driver.implicitlyWait(10);//设置隐形等待10s
	}
	/**
	 *  查看启动的包命和activity名称
	 * package: name='com.popularapp.periodcalendar' versionCode='188' versionName='1.648.188'
	launchable-activity: name='com.popularapp.periodcalendar.AdActivity'  label='' icon=''
	 */
	//修改语言设置
	//id定位也是当前屏幕才能定位到，这里languge不在当前屏幕，	需要找一个支撑点，luanguage在整体的设置中，是排行12的，这是规律之一，这是固定的一个位置
	//滑动屏幕，获取当前当前屏幕的选项text，如果拿到大于12就说明language出现在当前面屏幕了，然后拿到[11]就能定位到language的位置了
	//问题会不会出现重复获取的情况？会出现，怎能避免？
	
	@Test
	public void changeLanguage(){
		driver.findElement(By.id("com.popularapp.periodcalendar:id/bt_go_setting")).click();//点击设置
		List<AndroidElement> commonElements=new ArrayList<AndroidElement>();//公共的list,把当前屏幕加到公共list里面
		List<String> elementsText=new ArrayList<String>();//存放字符串，很难确定，滑动的页面不会丢失按钮；元素的排重？那些元素都有文字
		while(true){//滑动循环，让他一直循环
			List<AndroidElement> currentElements=driver.findElements(By.id("com.popularapp.periodcalendar:id/item"));//拿到当前屏幕的
			//拿当前屏幕的的元素
			for(AndroidElement ae:currentElements){   //把找到的元素放到currentElements中去，但是要进行排重				
				//AndroidElement ae 这个是干啥的？
				//枚举的知识点
				String text=ae.getText();//干啥的？ae是干啥的
				if(!elementsText.contains(text)){//如果不包含就添加进去
					System.out.println("text"+text);
					elementsText.add(text);
					commonElements.add(ae);//如果不包含我就放进去currentElements 
				}else{
					List<AndroidElement> cList=driver.findElements(By.id("com.popularapp.periodcalendar:id/item_text"));
					if(cList.size()>0){
						elementsText.add(text);
						commonElements.add(ae);//commonElements存进去  commonElements 和elementsText 最终是大小是一样的
					}
				}
			}
			System.out.println(commonElements.size());
			if(commonElements.size()>12){
				commonElements.get(11).click();
				break;//点击了之后就结束了
			}
			driver.swipe("up", 4000);//滑动的时间太快，把一些控件给错过了，时间越大滑动越慢，两屏幕之间可以错过但是不错过，这是原则
		}
	}
	@AfterClass
	public void quit(){
		driver.quit();
	}

}
