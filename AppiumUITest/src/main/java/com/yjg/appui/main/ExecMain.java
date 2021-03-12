package com.yjg.appui.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.yjg.appui.page.LoginPage;
import com.yjg.appui.server.Port;
import com.yjg.appui.server.Servers;
import com.yjg.appui.util.DosCmd;
import com.yjg.appui.util.FileUtil;
import com.yjg.appui.util.ProUtil;
import com.yjg.appui.util.WinOrMac;
import com.yjg.appui.util.XmlUtil;

public class ExecMain {
	private static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";  
	private static String path=System.getProperty("user.dir");
	public static Logger logger = Logger.getLogger(ExecMain.class);
	public static WinOrMac wm =new WinOrMac();

	/** 主程序的入口
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.setProperty(ESCAPE_PROPERTY, "false"); 
//		AppiumInit.initAll();//启动所有的设备
		AppiumInit.initOne("com.yjg.appui.testcases.kytest_002");//启动第一台的设备
//		AppiumInit.initByUdidName("8681-M02-0x77a1e9df", "com.yjg.appui.testcases.OrderPayTest_001");;//启动指定的的设备
        List<String> suites = new ArrayList<String>();
        suites.add(System.getProperty("user.dir")+wm.doubleBackslant("testng.xml")+"testng.xml"); //加载需要运行的testng文件
        TestNG tng = new TestNG();
        tng.setTestSuites(suites);
        tng.run();

	}
}
