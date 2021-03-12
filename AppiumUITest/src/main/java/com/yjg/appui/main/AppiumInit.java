package com.yjg.appui.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yjg.appui.server.Port;
import com.yjg.appui.server.Servers;
import com.yjg.appui.util.DosCmd;
import com.yjg.appui.util.XmlUtil;

/**
 * @author neo.shi
 * @asbstraction 启动appium的服务
 *
 */
public class AppiumInit {
	public static Logger logger = Logger.getLogger(AppiumInit.class);
	
	/**
	 * 启动appium服务  根据当前存在的设备启动对应的服务 可以启动多服务
	 */

	public static void initAll(){
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		DosCmd dc=new DosCmd();
		if(dc.killServer()){
			try {
				logger.info("开始启动服务");
				servers.startServers();//启动所有appium服务端
				logger.info("服务启动完毕");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//这个是针对不同业务进行的
				List<String> classes=new ArrayList<String>();
				classes.add("com.yjg.appui.testcases.Logintest_00");
//				classes.add("com.yjg.appui.testcases.ByExceltest_001");
				XmlUtil.createTestngSingleXml(classes);
				//这个是针对你 要执行的测试用例的
//				XmlUtil.createTestngXml("com.yjg.appui.testcases.WeilikankanTest_002");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			logger.info("清除appium服务失败");
		}
	}
	
	/**
	 * 只是启动当前连接的设备的 第一个设备的服务
	 */
	public static void initOne(String className){
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		DosCmd dc=new DosCmd();
		if(dc.killServer()){
			try {
				logger.info("开始启动服务");
				servers.startOneServers();//启动第一个appium服务端
				logger.info("服务启动完毕");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				XmlUtil.createOneTestngXml(className);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			logger.info("清除appium服务失败");
		}
	}
	

	/**
	 * 只是启动指定设备名称的服务
	 */
	public static void initByUdidName(String udid,String className){
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		DosCmd dc=new DosCmd();
		if(dc.killServer()){
			try {
				logger.info("开始启动服务");
				servers.startByNameServers(udid);//启动第一个appium服务端
				logger.info("服务启动完毕");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				XmlUtil.createUidTestngXml(udid, className);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			logger.info("清除appium服务失败");
		}
	}
	
}
