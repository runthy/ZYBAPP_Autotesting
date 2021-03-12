package com.yjg.appui.server;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.yjg.appui.util.DosCmd;
import com.yjg.appui.util.XmlUtil;

public class Servers {
	
	public static Logger logger = Logger.getLogger(Servers.class);
	private List<Integer> appiumPortList; // appium和设备通信的端口
	private List<Integer> bootstrapPortList;//脚本和appium通信的端口
	private List<String> deviceList;//存放设备的所有的udid
	private String deviceName;//设备名称
	private Port port; //端口号存放
	private DosCmd dos; //dos命令
	private String path=System.getProperty("user.dir");//当前路径存到path中
	
	public Servers(Port port,DosCmd dos){//构造方法 Port类和DosCmd类进行初始化
		this.port=port;
		this.dos=dos;
	}
	/**
	 * @description 根据设备数量生成可用端口列表
	 * @param start 端口起始号
	 * @return 返回值是一个List<Integer>
	 * @throws Exception
	 */
	private List<Integer> getPortList(int start) throws Exception{
		List<String> deviceList=getDevices();
		List<Integer> portList=port.GeneratPortList(start, deviceList.size());
		return portList;	
	}
	/**
	 * 获取当前可用设备
	 * @return
	 * @throws Exception
	 */
	public  List<String> getDevices() throws Exception {
		List<String> devList = dos.execCmdConsole("adb devices");//定义一个集合变量devList ，存放adb devices执行之后的结果
		List<String> deviceRes = new ArrayList<String>();//定义deviceRes来存放 udid的结果集合变量
		if (devList.size() > 2) {//大于2，最少是三个，因为只有一个设备的时候，集合的大小就是3，为了判断当前是否有设备 
			//有设备，进行遍历循环，判断一下
			for(int i = 1; i < devList.size() - 1; i++) {
				String deviceInfo[] = devList.get(i).split("\t");
				if (deviceInfo[1].trim().equals("device")) {//trim()方法是用来做什么的？字符串前后的空格会去掉，但是中间的空格去不掉的
					deviceRes.add(deviceInfo[0].trim());//将udid添加到集合中，最终将集合返回
				}
			}
		} else {
			logger.info("当前没有设备或设备连接状态不正确");
//			System.out.println("当前没有设备或设备连接状态不正确");
		}
		return deviceRes;
	}
	/**
	 * 生成服务端启动命令字符串存入List
	 * @return
	 * @throws Exception
	 * 根据设备的数量生成对应的端口号，然后拼接appium的命令，返回出去
	 */
	public List<String> GeneratServerCommand() throws Exception{
		appiumPortList=getPortList(4490);//端口号组1
		bootstrapPortList=getPortList(2233);//端口号组2
		deviceList=getDevices();//获取到当前设备
		List<String> commandList=new ArrayList<String>();//定义一个集合commandList存放所有的命令
		for(int i=0;i<deviceList.size();i++){//遍历
			//下面是拼接
			String command="appium -p "+appiumPortList.get(i)+" -bp "+bootstrapPortList.get(i)
					+" -U "+deviceList.get(i)+">"+path+"/logs/"+deviceList.get(i).split(":")[0]+"_"+i+".log";
			logger.info("command");
//			System.out.println(command);
			commandList.add(command);
		}
		XmlUtil.createDeviceXml(deviceList,appiumPortList);
		return commandList;//commandList最后好拼接好的命令返回
	}
	
	
	
	public List<String> GeneratByNameServerCommand(String udid) throws Exception{
		appiumPortList=getPortList(4490);//端口号组1   从4490开始  appium通信
		bootstrapPortList=getPortList(2233);//端口号组2  从2233开始  和脚本通信
		deviceName=udid;//获取到当前设备  输入adb devices
		deviceList=getDevices();
		List<String> commandList=new ArrayList<String>();//定义一个集合commandList存放所有的命令
		for(int i=0;i<deviceList.size();i++){//遍历

			if(deviceList.contains(deviceName)){//判断是否存在udid
				System.out.println("udid_"+i+":"+deviceList.get(i));
				//下面是拼接
				String command="appium -p "+appiumPortList.get(i)+" -bp "+bootstrapPortList.get(i)
						+" -U "+deviceName+">"+path+"/result/logs/appiumStartLogs/"+deviceName.split(":")[0]+"_"+i+"_appiumPort"+".log"; //文件名名称不能存在：字符，做特别处理
				logger.info("command");
//				System.out.println("启动appium的命令："+command);
				commandList.add(command);  // command变量存的是 ：appium -p 4491 -bp 2233 -U 127.0.0.1:5557
			}else{
				System.out.println("udid不存在");
			}

		}
		XmlUtil.createDeviceXml(deviceList,appiumPortList);//创建xml文件
		return commandList;//commandList最后好拼接好的命令返回
	}
	/**
	 * 启动所有appium服务端
	 * @return
	 * @throws Exception
	 * 调用GeneratServerCommand拼接appium的方法，得到的命令数据，调用execCmd 执行cmd命令的方法,执行后返回true
	 * 就是执行所有服务端命令的意思
	 */
	public boolean startServers() throws Exception{
		List<String> startCommand=GeneratServerCommand();//startCommand所有生成服务命令  
		//GeneratServerCommand方法  根据设备的数量生成对应的端口号，然后拼接appium的命令，返回出去   存在startCommand变量中
		boolean flag=false;
		if(startCommand.size()>0){//如果startCommand有数据，进行遍历
			for(String s:startCommand){
				dos.execCmd(s);//就是逐个执行GeneratServerCommand方法拼接到的命令，然后在dos中进行执行，execCmd方法只返回boolean值的方法，没有直接获取命令运行后的结果
			}
			flag=true;
		}else{
			flag=false;	
		}
		return flag;
	}
	/**
	 * 只是启动第一个设备
	 * */
	public boolean startOneServers() throws Exception{
		List<String> startCommand=GeneratServerCommand();//startCommand所有生成服务命令  
		//GeneratServerCommand方法  根据设备的数量生成对应的端口号，然后拼接appium的命令，返回出去   存在startCommand变量中
		boolean flag=false;
		if(startCommand.size()>0){//如果startCommand有数据，进行遍历
				dos.execCmd(startCommand.get(0));//  直接拿第一个 获取到到的命令
			flag=true;
		}else{
			flag=false;	
		}
		return flag;
	}
	
	/**
	 * 只是启动指定的设备
	 * */
	public boolean startByNameServers(String udid) throws Exception{
		List<String> startCommand=GeneratByNameServerCommand(udid);//startCommand所有生成服务命令  
		//GeneratServerCommand方法  根据设备的数量生成对应的端口号，然后拼接appium的命令，返回出去   存在startCommand变量中
		boolean flag=false;
		if(startCommand.size()>0){//如果startCommand有数据，进行遍历
				dos.execCmd(startCommand.get(0));//就是逐个执行GeneratServerCommand方法拼接到的命令，然后在dos中进行执行，execCmd方法只返回boolean值的方法，没有直接获取命令运行后的结果
			flag=true;
		}else{
			flag=false;	
		}
		return flag;
	}
	
	public static void main(String[] args) throws Exception {
		
		DosCmd dc=new DosCmd();
		/***1 测试返回的udid****/
		List<String> deviceList=dc.execCmdConsole("adb devices");
		for(int i=1;i<deviceList.size()-1;i++){//遍历  第一行没什么用，排除掉第一行，不用枚举，size()-1第三行是空行，要排除下
			String[] deviceInfo=deviceList.get(i).split("\t");//deviceList.get(i) 拿到   的是 127.0.0.1:5557	device 就是这个，split分割，用\t来分割
//			System.out.println("没有分割的的："+deviceList.get(i));
			if(deviceInfo[1].equals("device")){//分割完，判断下状态，如果是devices 打印下分割后的设备名称
				//equals字符串的是否对等用equals 不是双等号
				System.out.println(deviceInfo[0]);//打印出来，独立封装返回设备的udid 
			}
		}
		/***1 测试返回的udid****/
		
		/***2 测试根据设备的数量生成对应的端口号，然后拼接appium的命令，返回出去****/
//		Servers server=new Servers(new Port(new DosCmd()), new DosCmd());
//		//Servers的参数 是两个类的对象
//		List<String> serverCommands=server.GeneratServerCommand();//生成命令行
//		//
//		for(String s:serverCommands){
//			System.out.println(s);//打印命令行 打印情况如下
//			/**
//			 *  appium -p 4490 -bp 2233 -U 127.0.0.1:5555>E:\WorkPace\ScriptWorkpace\AppiumWorkPace\CrazyAppium/logs/127.0.0.1.log
//				appium -p 4491 -bp 2234 -U 127.0.0.1:5557>E:\WorkPace\ScriptWorkpace\AppiumWorkPace\CrazyAppium/logs/127.0.0.1.log
//				appium -p 4490 -bp 2233 -U 127.0.0.1:5555>E:\WorkPace\ScriptWorkpace\AppiumWorkPace\CrazyAppium/logs/127.0.0.1.log
//				appium -p 4491 -bp 2234 -U 127.0.0.1:5557>E:\WorkPace\ScriptWorkpace\AppiumWorkPace\CrazyAppium/logs/127.0.0.1.log
//			 * */
//		}
		/***2 测试根据设备的数量生成对应的端口号，然后拼接appium的命令，返回出去****/
		
	
		/***3 根据上面获得的命令 启动服务****/
//		Servers server=new Servers(new Port(new DosCmd()), new DosCmd());
//		if(dc.killServer()){//实际在使用的启动服务的前先把所有的appium服务清除掉
//			server.startServers();
//		}
		/***3 根据上面获得的命令 ****/
	}

}
