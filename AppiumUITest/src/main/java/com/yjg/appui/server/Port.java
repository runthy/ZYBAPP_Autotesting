package com.yjg.appui.server;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.yjg.appui.util.DosCmd;

/**
 * @author neoshi qq276085439
 * 此类封装appium server所有需要的端口分配的诸多方法
 * 自动生成可用的端口号(未占用的端口)，一组服务需要一对端口号(一个是-p，一个是-bp)
	指定一个起始的端口号，判断端口是否被占用，未被占用则添加到可用list里，并且自增1，一直循环到可用端口数量和设备数量一致
 */
public class Port {
	public static Logger logger = Logger.getLogger(Port.class);
	private DosCmd execDos;
	public Port(DosCmd execDos){
		this.execDos=execDos;
	}
	/**
	 * 验证端口是否被占用
	 * @param portNum
	 * @return boolean
	 */
	public  Boolean isPortUsed(int portNum) { // netstat -ano|findstr 4491
		List<String> portRes = new ArrayList<String>();//portRes 存放netstat命令后的数据
		String osName=System.getProperty("os.name");//获取当前系统的系统名称
//		System.out.println("当前系统："+osName);
		boolean flag=true;//默认是占用的
		try {
			String command="";
			if(osName.toLowerCase().contains("mac")){//如果是mac系统就用mac的命令
				command="netstat -an|grep " + portNum;
			}else if(osName.toLowerCase().contains("win")){
				command="netstat -ano|findstr " + portNum;
			}
			portRes = execDos.execCmdConsole(command);//execCmdConsole方法返回的是list类型的字符串类型
			System.out.println("[端口是否占用]："+portRes.size());
			if (portRes.size() > 0) {//如果结果大于0，说明已经被占用了，flag不变
				logger.debug(String.valueOf(portNum)+" port has been occupied");
			}else{
				logger.debug(String.valueOf(portNum)+" port unoccupied");
				flag=false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(String.valueOf(portNum)+"get port occupied status failure"+e);
		}
		return flag;
	}
	/**
	 * 基于当前连接的设备数量生成可用端口  开始指定一个起始的端口号，判断端口号是否被占用，如果没被占用，就添加到集合中，并且自增1，一直循环到可用端口数量和设备数量一致
	 * @param portStart,Starting ports
	 * @param deviceTotal,Total number of devices
	 * @return List<Integer>
	 */
	public  List<Integer> GeneratPortList(int portStart, int deviceTotal) {
		//portStart指定一个开始端口号，把设备deviceTotal的数量传入进来
		List<Integer> portList = new ArrayList<Integer>();//portList存端口好数量
		while (portList.size() != deviceTotal) {//portList端口号的数量如果不等于设备的数量循环执行
			if (portStart >= 0 && portStart <= 65535) {//判断传入的参数是否大于0和小于等于63335
				if (!isPortUsed(portStart)) {//如果没有被占用，把端口号portStart添加到list集合中portList
					portList.add(portStart);
				}
				portStart++;//端口自增加1
			}
		}
		System.out.println("可用端口"+portList);
		return portList;
	}
	public static void main(String[] args) throws Exception {
		Port p=new Port(new DosCmd());
//		System.out.println(p.isPortUsed(4723));//大于端口是否被占用的方法
		/***一下是根据设备数量生产两组端口号的例子*/
		Servers server=new Servers(p, new DosCmd());//定义一个server类
		List<String> udidList=server.getDevices();//存放udidList udid的值
		int count=udidList.size();//当前设备的数量
		List<Integer> portList=p.GeneratPortList(4491, count);//从4491开始，获取当前环境存在多少个设备connt，自动生成可用端口号
		for(Integer i:portList){
			System.out.println("可用端口号："+i);
		}
		//以上是知识一组端口号码，实际上是需要两组端口号
//		List<Integer> portList2=p.GeneratPortList(2251, count);
//		for(Integer i:portList2){
//			System.out.println(i);
//		}
		/***一下是根据设备数量生产两组端口号的例子*/
		
		/***以下是拼接appium的启动命令*/
		
//		for(int i=0;i<udidList.size();i++){//根据udid的数量来判断  ortList.get(i) 脚本和服务端连接的端口 
//			//portList2.get(i) 服务端和设备连接的端口  ，udidList.get(i) 是设备的名字
//			String appium_commad="appium -p "+portList.get(i)+" -bp "+portList2.get(i)+" -U "
//					+ udidList.get(i)+" > "+"logs/"+udidList.get(i).split(":")[0]+"_"+i+".log";
//			//udidList.get(i).split(":")[0]+".log" 用设备名称来命名log名称
//			//plit(":")[0] :分割取第一个
//			System.out.println("日志地址"+appium_commad);
//			//问题log名称的生产中，如果是 127.0.0.1:5555.log的命名会出现问题，应该去掉设备的5555 或者557
//		}
		//最终要将这些 appium -p 4491 -bp 2251 -U 127.0.0.1:5555 > logs/127.0.0.1.log 返回  请看server类的startServers方法
		
		
//		DosCmd cmd=new DosCmd();
//		int i=10;
//		while(cmd.execCmdConsole("netstat -ano|findstr 4723").size()==0&&i>0){
//			Runtime.getRuntime().exec("cmd /c appium");
//			Thread.sleep(10000);
//			System.out.println(i--);
//		}
//		Runtime.getRuntime().exec("cmd /c appium >D://log.txt");
//		Thread.sleep(10000);
		//System.out.println(p.isPortUsed(4493));
	}
}
