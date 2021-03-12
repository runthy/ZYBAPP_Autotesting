package com.yjg.appui.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yjg.appui.main.AppiumInit;
import com.yjg.appui.server.Port;

/**
 * 此类完成dos或shell命令的执行封装
 * 注意，设备和电脑需要有连接
 * 主要运用在Server类 和Port类
 */
public class DosCmd {
	public static Logger logger = Logger.getLogger(DosCmd.class);
	static String osName=System.getProperty("os.name");//获取当前系统的名字
	/**
	 * execute dos command
	 * @param cmdString,String cmdString
	 * @return boolean.succeed is true,Failure is false
	 * 只返回boolean值的方法，没有直接获取命令运行后的结果
	 */
	public static boolean execCmd(String cmdString){
		Runtime p = Runtime.getRuntime();
		try {
			if(osName.toLowerCase().contains("mac")){//toLowerCase转换成小写，然后判断系统是mac系统还是xindows系统
				//mac本身是linux系统，命令和window下面有些不一样
				String[] command={"/bin/sh","-c",cmdString};//mac下面的命令
				Process process=p.exec(command);
				System.out.println("[当前操作系统为：" + osName + " 执行 mac shell 命令：] " + cmdString);

			}else if(osName.toLowerCase().contains("win")){
				Process process=p.exec("cmd /c "+cmdString);//win下的命令
				System.out.println("[当前操作系统为：" + osName + " 执行 win cmd 命令：] " + cmdString);

			}
			Thread.sleep(10000);
			logger.info("dos命令执行完成");
			logger.debug("execute  command "+cmdString+" Succeed");//写log
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error("execute  command "+cmdString+" Failure", e);
			return false;
		}
	}
	
	/**
	 * get result of command, after execute dos command 
	 * @param cmdString,String cmdString
	 * @return List<String>
	 *	返回一个字符串的集合，存的是查下得到是数据
	 *  这个方法改成针对adb 的方法，写死了mac系统的adb的绝对路径
	 */
	public List<String> execCmdConsole2(String cmdString) throws InterruptedException {
		List<String> dosRes = new ArrayList<String>();//定义一个存放字符串集合的结果
		try {
			Process process=null;
			if(osName.toLowerCase().contains("mac")){//判断当前的系统，用相关的命令
				String cammand="/Users/neo.shi/Soft/WorkSoft/adt-bundle-mac-x86_64-20131030/sdk/platform-tools/"+cmdString;
				process = Runtime.getRuntime().exec(cammand);//注意要加上这个cmd /c
				System.out.println("[当前操作系统为：" + osName + "adb 已执行命令：] " + cammand);
				logger.debug("[当前操作系统为：" + osName + "执行 mac shell 命令：] " + cammand);
			}
			else if(osName.toLowerCase().contains("win")){
				process = Runtime.getRuntime().exec("cmd /c "+cmdString);//注意要加上这个cmd /c
				logger.info("[当前操作系统为：" + osName + " 执行 win cmd 命令：] " + cmdString);
			}
			InputStream in = process.getInputStream();//输入流  process对象获取输入流，存在in中
			BufferedReader inr = new BufferedReader(new InputStreamReader(in));//然后读取流对象,吧in流对象传进来
			String line = null;//
			while ((line = inr.readLine()) != null) {//获取inr的数据，readLine表示一行一行的读
				dosRes.add(line);//如果读到的内容不是null，我将得到的流写进去集合中

			}				
//			System.out.println("get result of command after execute dos command "+cmdString+" Succeed ");
			logger.debug("execute  command "+cmdString+" Succeed");//写log

			process.waitFor();//等待
			process.destroy();//销毁进程
		} catch (IOException e) {
			logger.error("get result of command after execute dos command "+cmdString+" Failure", e);
		}
		return dosRes;
	}
	/**
	 * get result of command, after execute dos command 
	 * @param cmdString,String cmdString
	 * @return List<String>
	 *	返回一个字符串的集合，存的是查下得到是数据
	 */
	public List<String> execCmdConsole(String cmdString) throws InterruptedException {
		List<String> dosRes = new ArrayList<String>();//定义一个存放字符串集合的结果
		try {
			Process process=null;
//			logger.debug(cmdString); 用这个打印不出list类型
			if(osName.toLowerCase().contains("mac")){//判断当前的系统，用相关的命令
				String cammand="/Users/neo.shi/Soft/WorkSoft/adt-bundle-mac-x86_64-20131030/sdk/platform-tools/"+cmdString;
				process = Runtime.getRuntime().exec(cammand);//注意要加上这个cmd /c
				System.out.println("[当前操作系统为：" + osName + "执行的mac  shell 命令:] " + cammand);
				logger.info("[当前操作系统为：" + osName + "执行 mac  shell 命令：] " + cammand);
			}
			else if(osName.toLowerCase().contains("win")){
				process = Runtime.getRuntime().exec(cmdString);//注意要加上这个cmd /c
				logger.info("[当前操作系统为：" + osName + "执行的win cmd 命令：] " + cmdString);

			
			}
			InputStream in = process.getInputStream();//输入流  process对象获取输入流，存在in中
			BufferedReader inr = new BufferedReader(new InputStreamReader(in));//然后读取流对象,吧in流对象传进来
			String line = null;//
			while ((line = inr.readLine()) != null) {//获取inr的数据，readLine表示一行一行的读
				dosRes.add(line);//如果读到的内容不是null，我将得到的流写进去集合中
			}
			process.waitFor();//等待
			process.destroy();//销毁进程
			logger.debug("get result of command after execute dos command "+cmdString+" Succeed ");
		} catch (IOException e) {
			logger.error("get result of command after execute dos command "+cmdString+" Failure", e);
		}
		return dosRes;
	}
	/**
	 * kill server by pid of server
	 * @param pid
	 * @return boolean
	 */
	public static boolean killServer(){
		String command="taskkill -F -PID node.exe";//默认的是window下的
		if(osName.toLowerCase().contains("mac")){//如果判断是mac系统
			command="killall node";
		}else if(osName.toLowerCase().contains("win")){
			command="taskkill -F -PID node.exe";
		}else{
			command="taskkill -F -PID node.exe";
		}
		if(execCmd(command)){
			logger.debug("kill server node  Succeed");
			return true;
		}else{
			logger.error("kill server node Failure");
			return false;
		}
	}

	
	
	/**appium简单命令的例子
	public static void main(String[] args) throws IOException, InterruptedException {	
		//01 appium启动服务/
		//dos命令下执行启动appium服务器
//		Runtime.getRuntime().exec("cmd /c appium");//Runtime表示当前运行环境，getRuntime获取到当前运行环境的dos执行，exce表示执行方法
//		Thread.sleep(5000);
		//appium启动服务/
		
		//02 appium杀死服务/
		Runtime.getRuntime().exec("taskkill -f -pid node.exe");//Runtime表示当前运行环境，getRuntime获取到当前运行环境的dos执行，exce表示执行方法
		//appium杀死服务/
	}
/***/
	
	//接下来是封装以上的例子,dos命令有两种，一种是输入命令后直接获得数据然后结束，一种是输入命令后持续获得数据
	public static void main(String[] args) throws Exception {
		DosCmd dc=new DosCmd();//创建当前的类，实例化的过程
//		System.out.println(dc.osName);
//		dc.execCmd("adb devices");//只返回boolean值的方法，没有直接获取命令运行后的结果的方法
//		List<String> devicesList=dc.execCmdConsole("adb devices");//execCmdConsole 这个方法返回的结果是string集合
//		System.out.println("集合的大小"+devicesList.size());//找到某个命令然后打印出来
//		//如果不这样打印出来是不会打印出来的，需要逐个打印出来,遍历这个集合
//		for(String s:devicesList){//String s:devicesList 这个是什么意思？枚举s 类型要和devicesList的集合类型保存一致
//			System.out.println(s);
//		}
		
		killServer();
		
	}
}
