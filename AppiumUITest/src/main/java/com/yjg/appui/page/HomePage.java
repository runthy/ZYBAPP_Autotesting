package com.yjg.appui.page;

import io.appium.java_client.android.AndroidElement;

import java.util.List;

import org.openqa.selenium.By;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.util.GetByLocator;
import com.yjg.appui.util.RandomUtil;

public class HomePage extends BasePage{
	private AndroidElement ingroe;
    public HomePage(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
    //点击忽略的操作
    public void clickIngroe(){
    	super.click(GetByLocator.getLocator("ingroe"));
    }
    //网络异常提示框检查

}
