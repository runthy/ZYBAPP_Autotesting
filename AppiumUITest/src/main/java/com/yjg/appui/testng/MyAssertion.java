package com.yjg.appui.testng;

import org.testng.Assert;
import org.testng.Reporter;

import com.yjg.appui.base.AndroidDriverBase;
import com.yjg.appui.base.CrazyPath;
import com.yjg.appui.page.BasePage;


public class MyAssertion extends BasePage {
	//由于截图的原因，暂时继承BasePage类
	public MyAssertion(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

//	  public  AndroidDriverBase driver;
//	  public MyAssertion(AndroidDriverBase driver){
//		  this.driver=driver;
//	  }
	  public  void assertEquals(Object actual, Object expected,String fileName) throws Exception{
	        try{
	            Assert.assertEquals(actual, expected);
	            
	        }catch(AssertionError e){
//        		driver.takeScreen(CrazyPath.path, "\\images\\"+Thread.currentThread().getId()+fileName);
	        	fail(fileName);
	        }
	  }
	  public  void assertEquals(Object actual, Object expected, String fileName,String message){
	        try{
	            Assert.assertEquals(actual, expected, message);
	        }catch(AssertionError e){
	        	fail(fileName,message);
	        }
	  }
	  public  void verifyEquals(Object actual, Object expected,String fileName){
	        try{
	            Assert.assertEquals(actual, expected);
	        }catch(AssertionError e){
	        	try {
	        		driver.takeScreen(CrazyPath.path, "result/screenshot/"+Thread.currentThread().getId()+fileName);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        }
	  }
	  public  void verifyEquals(Object actual, Object expected,String fileName,String message){
	        try{
	            Assert.assertEquals(actual, expected, message);
	        }catch(AssertionError e){
	           	try {
	        		driver.takeScreen(CrazyPath.path, "/images/"+Thread.currentThread().getId()+fileName);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        }
	  }
	  public void fail(String fileName){
		  try {
      			//System.out.println(CrazyPath.path+"\\images\\"+Thread.currentThread().getId()+fileName);
      			Reporter.log("<a href=http://localhost:8080/jenkins/job/test/crazyappium/images/" + Thread.currentThread().getId()+fileName + " target=_blank>Failed Screen Shot</a>", true);  
      			Reporter.log("<img src=http://localhost:8080/jenkins/job/test/crazyappium/images/"+Thread.currentThread().getId()+fileName +" style=width:30px;height:30px img/>", true);
				driver.takeScreen(CrazyPath.path+"//test-output//html//images//",Thread.currentThread().getId()+fileName);
		  } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
		  Assert.fail();
	  }
	  public void fail(String fileName,String message){
		  try {
      			//System.out.println(CrazyPath.path+"\\images\\"+Thread.currentThread().getId()+fileName);
      			Reporter.log("<a href=http://localhost:8080/jenkins/job/test/crazyappium/images/" + Thread.currentThread().getId()+fileName + " target=_blank>Failed Screen Shot</a>", true);  
      			Reporter.log("<img src=http://localhost:8080/jenkins/job/test/crazyappium/images/"+Thread.currentThread().getId()+fileName +" style=width:30px;height:30px img/>", true);
				driver.takeScreen(CrazyPath.path+"//test-output//html//images//",Thread.currentThread().getId()+fileName);
		  } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
		  Assert.fail(message);
	  }
}
