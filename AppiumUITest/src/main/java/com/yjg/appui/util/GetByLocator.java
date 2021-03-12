package com.yjg.appui.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class GetByLocator {
	static Logger logger = Logger.getLogger(GetByLocator.class.getName());
	public static By getLocator(String ElementNameInpropFile)  {
		ProUtil properties=new ProUtil("configs//element.properties");
		//根据变量 ElementNameInpropFile，从属性配置文件中读取对应的配置对象
		//locator=name>登录或注册
        String locator = properties.getPro(ElementNameInpropFile);
		// 将配置对象中的定位类型存到 locatorType 变量，将定位表达式的值存入到 locatorValue 变量
        String locatorType = locator.split(">")[0];//name
        String locatorValue = locator.split(">")[1];//登录或注册
		// 输出  locatorType 变量值和locatorValue 变量值，验证是否赋值正确
//        logger.info("获取的定位类型：" + locatorType + "\t 获取的定位表达式" + locatorValue);
		// 根据 locatorType 的变量值内容判断，返回何种定位方式的 By 对象
		if(locatorType.toLowerCase().equals("id"))
			return By.id(locatorValue);//By.id("xxx:id/xxx")
		else if(locatorType.toLowerCase().equals("name"))
			return By.name(locatorValue);//登录或注册
		else if((locatorType.toLowerCase().equals("classname"))||(locatorType.toLowerCase().equals("class")))
			return By.className(locatorValue);
		else if((locatorType.toLowerCase().equals("tagname"))||(locatorType.toLowerCase().equals("tag")))
			return By.className(locatorValue);
		else if((locatorType.toLowerCase().equals("linktext"))||(locatorType.toLowerCase().equals("link")))
			return By.linkText(locatorValue);
		else if(locatorType.toLowerCase().equals("partiallinktext"))
			return By.partialLinkText(locatorValue);
		else if((locatorType.toLowerCase().equals("cssselector"))||(locatorType.toLowerCase().equals("css")))
			return By.cssSelector(locatorValue);
		else if(locatorType.toLowerCase().equals("xpath"))
			
			return By.xpath(locatorValue);
		
		else
			try {
				throw new Exception("输入的 locator type 未在程序中被定义：" + locatorType );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
    }
}
