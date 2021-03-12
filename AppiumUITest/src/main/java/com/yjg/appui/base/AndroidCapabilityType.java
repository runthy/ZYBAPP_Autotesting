package com.yjg.appui.base;

import io.appium.java_client.remote.MobileCapabilityType;

public interface AndroidCapabilityType extends MobileCapabilityType {
	/**
	 * 自己写的接口类，作用方便调用MobileCapabilityType类的变量，不用手写字符串
	 * 额外能知道此接口中还有更多的参数可以使用，保留此类的使用
	 * */
	String NO_SIGN="noSign";
	String UNICODE_KEY_BOARD="unicodeKeyboard";
	String RESET_KEY_BOARD="resetKeyboard";
	String AUTO_LAUNCH="autoLaunch";
}
