package com.yjg.appui.base;
import java.io.File;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.yjg.appui.util.ProUtil;

public class CrazyCapabilities {
	/**
	 * 设置 Android设备的参数
	 * @param capsPath  cap参数的xml配置文件的路径
	 * @param udid
	 * @return
	 */
	//capspath 是从AndroidDriverBase类中传递过来
	public  DesiredCapabilities initCaps(String capsPath,String udid){
		/**
		 *  配置文件中的参数如下：
		 * apkpath=C:\\Users\\lixionggang\\Desktop\\zhihu.apk
			deviceName=devicename
			automationName=Appium
			newCommandTimeout=600
			appWaitActivity=.ui.activity.MainActivity
			appPackage=com.popularapp.periodcalendar
			appActivity=com.popularapp.periodcalendar.MainActivity
			noSign=True
			unicodeKeyboard=True
			resetKeyboard=True
			deviceReadyTimeout=30
		 * */
		ProUtil p=new ProUtil(capsPath);
		File apkPath=new File(p.getPro("apkpath"));
		DesiredCapabilities caps=new DesiredCapabilities();
		try {
			//这里不进行每次都安装，每次都安装会出现，重置的情况
//			caps.setCapability(AndroidCapabilityType.APP, apkPath.getAbsolutePath());
			//设备名称
			caps.setCapability(AndroidCapabilityType.DEVICE_NAME, p.getPro(AndroidCapabilityType.DEVICE_NAME));
			//   AndroidCapabilityType.DEVICE_NAME 相当于 String DEVICE_NAME = "deviceName";
			//p.getPro(AndroidCapabilityType.DEVICE_NAME)相当于是 获取paps.properties配置文件中deviceName=devicename的值
			//appPackage=com.popularapp.periodcalendar 包名
			caps.setCapability(AndroidCapabilityType.APP_PACKAGE, p.getPro(AndroidCapabilityType.APP_PACKAGE));
			//appActivity=com.popularapp.periodcalendar.MainActivity  activity名
			caps.setCapability(AndroidCapabilityType.APP_ACTIVITY, p.getPro(AndroidCapabilityType.APP_ACTIVITY));
			//String RESET_KEY_BOARD="resetKeyboard";  resetKeyboard=True 
			caps.setCapability(AndroidCapabilityType.NO_SIGN, p.getPro(AndroidCapabilityType.NO_SIGN));
			caps.setCapability(AndroidCapabilityType.RESET_KEY_BOARD, p.getPro(AndroidCapabilityType.RESET_KEY_BOARD));
			//String UNICODE_KEY_BOARD="unicodeKeyboard"; unicodeKeyboard=True
			caps.setCapability(AndroidCapabilityType.UNICODE_KEY_BOARD, p.getPro(AndroidCapabilityType.UNICODE_KEY_BOARD));
			//  String UDID = "udid"; appium的  udid用的变量的方式
			caps.setCapability(AndroidCapabilityType.UDID,udid);
			//String NEW_COMMAND_TIMEOUT = "newCommandTimeout";    deviceReadyTimeout=30
			caps.setCapability(AndroidCapabilityType.NEW_COMMAND_TIMEOUT, p.getPro(AndroidCapabilityType.NEW_COMMAND_TIMEOUT));
			caps.setCapability("noReset","True");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return caps;
	}
	public  DesiredCapabilities initCapsWait(String capsPath,String udid){
		ProUtil p=new ProUtil(capsPath);
		File apkPath=new File(p.getPro("apkpath"));
		//C:\Users\Administrator\Desktop\YJG.apk 如果没有app就自动安装，如果有app就启动activity方法
		DesiredCapabilities caps=new DesiredCapabilities();
		try {
			caps.setCapability("app", apkPath.getAbsolutePath());
			caps.setCapability("deviceName", p.getPro("deviceName"));
			caps.setCapability("appPackage", p.getPro("appPackage"));
			caps.setCapability("appActivity", p.getPro("appActivity"));
			caps.setCapability("appWaitActivity", p.getPro("appWaitActivity"));
			caps.setCapability("noSign", p.getPro("noSign"));
			caps.setCapability("unicodeKeyboard", p.getPro("unicodeKeyboard"));
			caps.setCapability("resetKeyboard", p.getPro("resetKeyboard"));
			caps.setCapability("udid",udid);
			caps.setCapability("noReset","True");

			caps.setCapability("newCommandTimeout", p.getPro("newCommandTimeout"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return caps;
	}
}
