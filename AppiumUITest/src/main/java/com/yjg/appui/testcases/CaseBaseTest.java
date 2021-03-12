package com.yjg.appui.testcases;

import com.yjg.appui.base.AndroidSpecific;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeSuite;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.util.FilePath;
import com.yjg.appui.util.LogConfiguration;
import com.yjg.appui.util.ProUtil;

public class CaseBaseTest {
	/**
	 * @author 作者 neo.shi E-mail:
	 * @version 创建时间：2019年2月2日 下午6:10:19
	 * 类说明
	 */
	public static Logger logger = Logger.getLogger(CaseBaseTest.class);
	/**
	 * 这是一个基类：实现设备的启动，需要传入两个 参数，udid和端口号port
	 * */
//	@BeforeSuite
//	public void beforeSuite(){
////		AppiumInit.init();//启动appium的服务
//	}
	public AndroidDriverBase driverInit(String udid, String port)
			throws Exception {
		LogConfiguration.initLog(this.getClass().getSimpleName());//初始化日志
		ProUtil p = new ProUtil(FilePath.globalPath());//读取配置文件"configs\\global.properties";
		String server=p.getPro("server");//server=http://127.0.0.1
		String capsPath= FilePath.capsPath();//"configs\\caps.properties";
		AndroidSpecific as=new AndroidSpecific();
		String input=as.getDefaultInput(udid);//获取设备默认输入法,存到input中
		logger.info("连接"+udid+"端口"+port);
		logger.info("开始创建server连接");
		return new AndroidDriverBase(server, port, capsPath, udid, input);
		
	}
}
