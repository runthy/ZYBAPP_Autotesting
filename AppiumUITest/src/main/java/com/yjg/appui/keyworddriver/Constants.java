package com.yjg.appui.keyworddriver;
/**
 * @author neo shi
 * @description 常量类封装: 定义测试数据相关常量设定
 * @date 2019/01/19
 * */
public class Constants {

	//读取的测试用例的excel表的位置
	public static final String Path_ExcelFile = "configs/YJG_AutoTestcase.xlsx";
	//第一列用 0 表示，测试用例序号列
	public static final int Col_TestCaseID = 0;	//对应的列数
	//第二列和第四列分别是测试步骤序号和测试步骤描述，这里暂不做使用
	//第四列用 3 表示，关键字列
	public static final int Col_KeyWordAction = 3 ;
	//第五列用4表示，操作元素的定位表达式列
	public static final int Col_LocatorExpression = 4 ;
	//第六列用 5 表示，操作值列
	public static final int Col_ActionValue = 5 ;
	//第七列用 6 表示，测试结果列
	public static final int Col_TestStepTestResult = 6 ;
	//测试集合 sheet 中的列号常量设定
	public static final int Col_RunFlag =2;
	//测试集合 sheet 中的测试结果列号常量设定，测试用例执行结果列
	public static final int Col_TestSuiteTestResult = 3 ;
	//测试用例 sheet 名称的常量设定
	public static final String Sheet_TestSteps = "YJG_Testcase";
	//测试用例集 sheet 的常量设定
    public  static  final String Sheet_TestSuite = "测试用例集合";
    /**
     * 用例的执行是向调用【测试用例集合】，再去调用各自的测试用例[YJG_Testcase]，
     * 然后判断关键字，执行对应的方法和操作值，最后进行判断和回写测试结果
     * 
     * */

}
