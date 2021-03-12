package com.yjg.appui.util;

import java.util.Random;

public class RandomUtil {
	/**
	 * 随机生成指定长度的字符串
	 * @param chars
	 * @param lengthOfString
	 * @return
	 * 实际在执行自动化的过程中，都希望它都是变化的，随机的变化的，可以写在配置文件
	 * 如果写在配置文件但是每次执行都得改动，利用修改的时候随机生成，用户的年龄等随机生成
	 * 在每次执行自动化脚本都是变化的，不用人为的修改某些值
	 */
	//随机生成指定长度的字符串，有个池子 chars数组就是池子，随机生成的时候就是往这里拿数据的
	public static String getRndStrAndNumByLen(int lengthOfString) {
		int i, count = 0;
		final String chars ="1,2,3,4,5,6,7,8,9,0,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		String[] charArr = chars.split(",");//用逗号进行分割，变成一个数组
		//可能包含字母，数字，纯字母，纯数字，或者混合等字符串
		StringBuffer randomStr = new StringBuffer("");//randomStr变量
		Random rnd = new Random();//随机对象的new
		int strLen = charArr.length;//池子的元素的长度

		while (count < lengthOfString) {//去循环遍历
			i = rnd.nextInt(strLen);//strLen是10个数字加上26个字母，如果等于36，i值就在0-36之间，strLen按照池子的长度来生成
			//System.out.println(i);
			randomStr.append(charArr[i]);//将池子中组成的的某个元素charArr[i]，赋值给randomStr
			count++;
		}
		return randomStr.toString().toLowerCase();//返回出来，全部转化成小写
	}
	//只是生成字母
	public static String getRndStrByLen(int lengthOfString) {
		int i, count = 0;
		final String chars ="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		String[] charArr = chars.split(",");//用逗号进行分割，变成一个数组
		//可能包含字母，数字，纯字母，纯数字，或者混合等字符串
		StringBuffer randomStr = new StringBuffer("");//randomStr变量
		Random rnd = new Random();//随机对象的new
		int strLen = charArr.length;//池子的元素的长度

		while (count < lengthOfString) {//去循环遍历
			i = rnd.nextInt(strLen);//strLen是10个数字加上26个字母，如果等于36，i值就在0-36之间，strLen按照池子的长度来生成
			//System.out.println(i);
			randomStr.append(charArr[i]);//将池子中组成的的某个元素charArr[i]，赋值给randomStr
			count++;
		}
		return randomStr.toString().toLowerCase();//返回出来，全部转化成小写
	}
	
	/***
	 * 生成指定范围内的中文
	 * */
	public static String getRndStrZhByLen(int lengthOfString) {
		int i, count = 0;
		final String chars ="我,也,不,知,道,你,想,干,神,爱,好,的,地,方,发,的,的,是,问,有,二,与";
		String[] charArr = chars.split(",");

		StringBuffer randomStr = new StringBuffer("");
		Random rnd = new Random();
		int strLen = charArr.length;

		while (count < lengthOfString) {
			i = rnd.nextInt(strLen);//strLen如果等于30，i值就在0-30之间
			//System.out.println(i);
			randomStr.append(charArr[i]);
			count++;
		}
		return randomStr.toString().toLowerCase();
	}
	/**
	 * 随机生成指定长度的数字，以字符串形式返回
	 * @param lengthOfNumber
	 * @return
	 */
	public static String getRndNumByLen(int lengthOfNumber) {
		int i, count = 0;
		//098
		StringBuffer randomStr = new StringBuffer("");
		Random rnd = new Random();

		while (count < lengthOfNumber) {
			i = Math.abs(rnd.nextInt(9));//abs是绝对值的作用
			if (i == 0 && count == 0) {//注意：（不能是0开头的），意思是不生成开始为0的数字，比如098,01 
			} else {
				randomStr.append(String.valueOf(i));
				count++;
			}
		}
		return randomStr.toString();
	}

	/**
	 * 生成指定范围内的数字，不包含参数本身；例如生成10以内的数字，不包含10
	 * @param extent
	 * @return
	 */
	public static int getExtentRandomNumber(int extent) {//和r.nextInt(10);方法一样效果
		int number = (int) (Math.random() * extent);//Math.random() 这个取的值都是0到1之间的值
		return number;
	}
	/**
	 * 生成指定范围内的浮点数   ，例如5到10之间的小数点
	 * @param min
	 * @param max
	 * @return
	 */
	private static float randomFloat(int min,int max){
        Random random = new Random();
//        int s = random.nextInt(max)%(max-min+1) + min;
        float x=min;//x=10
        while(x<=min){
        	double db = random.nextDouble() * max * 10;// random.nextDouble() 也是0到1之间的值
        	x = ((int) db) / 10f;//除以10f是把double转化成float
        }
        return x;
	}
	/**
	 * 生成指定范围内的整数    例如是5到10之间的整数，是包含5和10的
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomInt(int min,int max){ 
        Random random = new Random();
        //例如输入是5,10   ，max的值是0到9，例如是9，（max-min+1）相当于10-5+1是6，9%6=9对6取余是3，最终3+min就是3+5最终等于8
        //
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}
	public static void main(String[] args) {
		
//		/****1 Random类的随机类**/
//		Random rd=new Random();
//		System.out.println(rd.nextInt());//打印 -1686750533的值，随机打印
//		System.out.println(rd.nextInt(3));//随机打印0到(3-1)的范围；0-2的值，也就是只有0 1 2三个值可选择
//		/****1 Random类的随机类**/
		
		/****2 指定长度的字符串***/
		System.out.println(getRndStrByLen(5));//最总生成的是指定生产的长度为5的字符串 例如：64jnb，g7bky等等
		//不能百分比生成纯字母和数字的字符串
		/****2 指定长度的字符串***/
		
		/****3 指定长度的中文（纯中文的）***/
		System.out.println(getRndStrZhByLen(5));//最总生成的是指定生产的长度为5的中文 例如：发道不知好，你二道的是等等
		/****3 指定长度的中文***/
		
		/****4 指定长度的数字和字母(纯数字的)***/
		System.out.println(getRndNumByLen(5));//最总生成的是指定生产的长度为5的数字 例如：50464，50434是等等
		/****4 指定长度的数字和字母***/
		
		/****5 生成数字和字母的字符串***/
		System.out.println(getRndStrByLen(3)+getRndNumByLen(3));
		/****5 生成数字和字母的字符串***/
		
	}

}
