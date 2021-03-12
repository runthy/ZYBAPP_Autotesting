package com.yjg.appui.util;

import org.apache.log4j.Logger;

public class WinOrMac {
    /**
     * @abstracttion 根据不同系统自动返回不同类型的双斜杆符号
     * @return
     */
    public static String osName=System.getProperty("os.name");//获取当前系统的名字
    public static Logger logger = Logger.getLogger(WinOrMac.class);

    public   String doubleBackslant (String path){
        if(osName.toLowerCase().contains("mac")){//判断当前的系统，用相关的命令
            String macxiegan="//";   //appium的服务地址
            logger.info("【"+"当前操作系统为：" + osName + "】"+path +" 使用的文件连接符号是：//" );
            return macxiegan;
        }else if (osName.toLowerCase().contains("win")){
            String winxiegan="\\\\";   //appium的服务地址
            logger.info("【"+"当前操作系统为：" + osName + "】"+path +" 使用的文件连接符号是：\\\\" );
            return winxiegan;
        }else {
            return null;
        }
    }
    public static void main(String[] args) {
        WinOrMac wm=new WinOrMac();
//		System.out.println(path); //E:\WorkPace\ScriptWorkpace\AppiumWorkPace\AppiumTest\CrazyAppium
        String globalPath="configs"+wm.doubleBackslant("test")+"global.properties";

        System.out.print(globalPath);
    }
}
