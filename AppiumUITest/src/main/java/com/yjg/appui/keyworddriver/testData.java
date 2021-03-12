package com.yjg.appui.keyworddriver;


import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class testData  {
	
	
	/**
	 * 读取excel表的方法
	 * 格式是这样的{{"1","登录成功","13533013009","asd123456","登录","y"}}
	 * */
	@DataProvider
	public Object[][] getLoginData(){
		Object[][] data=null;
		try {
			data=ExcelUtil1.getTestData("configs/test.xlsx", "Sheet1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	@Test(dataProvider="getLoginData")
	public void login(String caseNumber,String caseName,String username,String password,String str) throws Exception{
//		System.out.println("userName"+username);
		
	}

}
