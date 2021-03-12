package com.yjg.appui.base;

import java.util.List;

import org.apache.log4j.Logger;

import com.yjg.appui.testcases.CaseBaseTest;
import com.yjg.appui.util.DosCmd;

public class AndroidSpecific {
	//public static String udid;
	/**
	 * 获取设备默认输入法
	 * @return
	 */
	public static Logger logger = Logger.getLogger(AndroidSpecific.class);
	
	public static  String getDefaultInput(String udid){
		DosCmd dc=new DosCmd();
		String input="";
		try {
			List<String> res=dc.execCmdConsole("adb -s "+udid+" shell \"settings get secure default_input_method\"");
			logger.info("获取设备默认输入法:"+"adb -s "+udid+" shell \"settings get secure default_input_method\"");
			input=res.get(0);//数组集合的第一个
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}
	/**
	 * 唤醒屏幕
	 * @throws Exception
	 */
	public static void wakeUp(String udid) throws Exception{
		DosCmd dc=new DosCmd();
		try {
			dc.execCmdConsole("adb -s "+udid+" shell am start -n io.appium.unlock/io.appium.unlock.Unlock");//io.appium.unlock/io.appium.unlock.Unlock 这个包名
			System.out.println("adb -s "+udid+" shell am start -n io.appium.unlock/io.appium.unlock.Unlock");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		AndroidSpecific as=new AndroidSpecific();
//		wakeUp("8681-M02-0x77a1e9df");
		System.out.println("设备默认输入法:"+as.getDefaultInput("172.18.10.42:5555"));
	}
}
