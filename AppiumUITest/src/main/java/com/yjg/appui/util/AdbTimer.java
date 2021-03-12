package com.yjg.appui.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
//定时器类，可以在脚本运行时进行相关定时任务（之后会对获取数据进行处理）
public class AdbTimer {
	// 需要执行的定时任务
	public TimerTask timerTask() {
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date executeDate = new Date(this.scheduledExecutionTime());
					String executeTime = format.format(executeDate);
					System.out.print("本次任务执行时间是：" + executeTime + "\n");
					Runtime rt = Runtime.getRuntime();
					Process pr = rt
							.exec("adb shell \" top -n 1 -d 1|grep \" cn.weli.story\"");
//					命令查询属性
//					进程属性： 
//					PID	           进程在系统中的ID
//					CPU%    当前瞬时所以使用CPU占用率 
//					S   	进程的状态，其中S表示休眠，R表示正在运行，Z表示僵死状态，N表示该进程优先值是负数。 
//					#THR    程序当前所用的线程数 
//					VSS 	Virtual Set Size 虚拟耗用内存（包含共享库占用的内存） 
//					RSS 	Resident Set Size 实际使用物理内存（包含共享库占用的内存） 
//					PCY 	前台(fg)和後台(bg)進程
//					UID 	运行当前进程的用户id
//					Name    應用程序名稱
					BufferedReader input = new BufferedReader(
							new InputStreamReader(pr.getInputStream()));
					String line = null;
					if ((line = input.readLine()) != null) {
						System.out.print(line + "\n");
						String[] results = line.trim().split("\\s+");
						System.out.print("已切割后長度：" + results.length + "\n");
						if (results.length == 10) {
							//进程在系统中的ID
							String pid = results[0];
							//当前瞬时所以使用CPU占用率 
							String cpuper =results[2];
							//进程的状态
							String state = results[3];
							//程序当前所用的线程数 
							String threadCount = results[4];
							//虚拟耗用内存
							String vss = results[5];
							//实际使用物理内存
							String rss = results[6];
							//前台或後台進程
							String pcy = results[7];
							//对应应用包名
							String packageName = results[9];
							System.out.print("PID:" + pid + "\nCPU%:" + cpuper + "\nThreadState:" + state
									+ "\nThreadCount:" + threadCount + "\nvss:" + vss + "\nrss:" + rss
									+ "\nPackageName:" + packageName + "\n");
						}else {
							System.out.print("未捕獲到數據\n");
						}
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.print(e.toString());
					e.printStackTrace();
				}
			}
		};
		return timerTask;
	}

	// 设置定时器，并运行定时任务
	public void topTimer(long delay, long period) {
		Timer timer = new Timer();
		timer.schedule(timerTask(), delay, period);
	}
	

}
