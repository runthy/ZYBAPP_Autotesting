package com.yjg.appui.keyworddriver;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.yjg.appui.testcases.ByExceltest_001;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


//本类主要实现后缀为 xlsx 的 excel  文件操作  
//读取excel关键字表的方法
/**
 * 1 先判断是否执行 y or n，然后如果要执行，2、查找执行的序号，根据序号去领一张表进行解析操作那几行 
 * 3 拿关键字、操作元素的定位表达式，和操作值这三行 
 * */
public class ExcelUtil {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;

	// 设定要操作的 Excel 的文件路径
	// 在读写excel的时候，需要先设定要操作的 excel 文件路径
	public static void setExcelFile(String Path){
			 
		FileInputStream ExcelFile;
		try {
			// 实例化 excel 文件的 FileInputStream 对象
			ExcelFile = new FileInputStream(Path);
			System.out.println(Path);
			// 实例化 excel 文件的 XSSFWorkbook 对象
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			//读取文件，获取XSSFWorkbook的对象ExcelWBook，获取整个文档的sheet
		} catch (Exception e) {
			ByExceltest_001.testResult = false;
		    System.out.println("Excel 路径设定失败xxxxxx");
//			e.printStackTrace();
		}

	}
	// 设定要操作的 Excel 的文件路径和 Excel 文件中的 sheet 名称
	// 在读写excel的时候，设定要操作的 excel 文件路径和要操作的 sheet 名称
	public static void setExcelFile(String Path, String SheetName)//拿某个工作表对应的sheet的内容
			 {
		FileInputStream ExcelFile;
		try {
			// 实例化 excel 文件的 FileInputStream 对象
			ExcelFile = new FileInputStream(Path);
			// 实例化 excel 文件的 XSSFWorkbook 对象
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			/* 实例化 XSSFSheet 对象，指定 excel 文件中的 sheet 名称，后续用于 sheet 中行、       
              列和单元格的操作  */
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e) {
			ByExceltest_001.testResult = false;
		    System.out.println("Excel 路径设定失败xxxxx");
//			e.printStackTrace();
		}

	}

	// 读取 excel 文件指定单元格的函数，此函数只支持后缀为 xlsx 的 excel 文件
	public static String getCellData(String SheetName,int RowNum, int ColNum) {//获取第几行第几列
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try {
			// 通过函数参数指定单元格的行号和列号，获取指定的单元格对象
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
		// 如果单元格的内容为字符串类型，则使用 getStringCellValue 方法获取单元格的内容
		// 如果单元格的内容为数字类型，则使用 getNumericCellValue() 方法获取单元格的内容
			String CellData = Cell.getCellType() == XSSFCell.CELL_TYPE_STRING ? Cell
					.getStringCellValue() + ""
					: String.valueOf(Math.round(Cell.getNumericCellValue()));
            //函数返回指定单元格的字符串内容
			return CellData;

		} catch (Exception e) {
			ByExceltest_001.testResult = false;
//			e.printStackTrace();
			//读取遇到异常，则返回空字符串
			return "";
		}
	}
	
	//获取 Excel 文件最后一行的行号
	public static int getLastRowNum() {//最后一行的行号
        //函数返回 sheet 中最后一行的行号
		return ExcelWSheet.getLastRowNum();
	}
	//获取指定 sheet 中的数据总行数
	public static int getRowCount(String SheetName){//指定sheet的总行数
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int number=ExcelWSheet.getLastRowNum();
		return number;
	}

	//在excel 的指定sheet 中，获取第一次包含指定测试用例序号文字的行号
	//例如获取登录01对应的操作行，传入三个参数，哪个工作表，哪个测试用例名字，第几列来获取
	public static int getFirstRowContainsTestCaseID(String sheetName,String testCaseName, int colNum) {
		int i;	
		try{
		
		ExcelWSheet = ExcelWBook.getSheet(sheetName);//拿到sheet表
			int rowCount = ExcelUtil.getRowCount(sheetName);//获取当前工作表的行数
			for (i=0 ; i<rowCount; i++){//遍历
		//使用循环的方法遍历测试用例序号列的所有行，判断是否包含某个测试用例序号关键字
				//System.out.println(getCellData(sheetName,i,colNum));
				if  (ExcelUtil.getCellData(sheetName,i,colNum).equalsIgnoreCase(testCaseName)){//判断每行的数据
			//如果包含，则退出 for 循环，并返回包含测试用例序号关键字的行号
					break;
				}
			}
			return i;//返回的行号 用例第一次出现的第一行
		}catch (Exception e){
			ByExceltest_001.testResult = false;
			return 0;
		}
			
	}
	 //获取指定 sheet 中，某个测试用例最后的行号
	//testCaseStartRowNumber 开始查看行数
	public static int getTestCaseLastStepRow(String SheetName, String testCaseID, int testCaseStartRowNumber){
		try{
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		/* 从包含指定测试用例序号的第一行开始逐行遍历，直到某一行不出现指定测试用例序号，
		      此时的遍历次数就是次测试用例步骤的个数  */
		for(int i=testCaseStartRowNumber;i<=ExcelUtil.getRowCount(SheetName)-1;i++){
			//System.out.println(ExcelUtil.getCellData(SheetName,i, Constants.Col_TestCaseID));
			if(!testCaseID.equals(ExcelUtil.getCellData(SheetName,i, Constants.Col_TestCaseID))){
				int number = i;
				return number;
			}
		}
		int number=ExcelWSheet.getLastRowNum()+1;//最后一行再加1，才是当前所有的行号
		return number;
		}catch(Exception e){
			ByExceltest_001.testResult = false;
			return 0;
		}
	}
	// 在 excel 文件的执行单元格中写入数据，此函数只支持后缀为  xlsx 的 excel 文件写入
		public static void setCellData(String SheetName,int RowNum, int ColNum, String Result) {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			try {
				// 获取 excel文件中的行对象
				Row = ExcelWSheet.getRow(RowNum);
				// 如果单元格为空，则返回 Null
				Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);

				if (Cell == null) {
			// 当单元格对象是 null 的时候，则创建单元格
			// 如果单元格为空，无法直接调用单元格对象的 setCellValue 方法设定单元格的值
					Cell = Row.createCell(ColNum);
					// 创建单元格后可以调用单元格对象的 setCellValue 方法设定单元格的值
					Cell.setCellValue(Result);

				} else {
			// 单元格中有内容，则可以直接调用单元格对象的 setCellValue 方法设定单元格的值
					Cell.setCellValue(Result);

				}
				// 实例化写入 excel 文件的文件输出流对象
				FileOutputStream fileOut = new FileOutputStream(
						Constants.Path_ExcelFile);
				// 将内容写入 excel 文件中
				ExcelWBook.write(fileOut);
				// 调用flush 方法强制刷新写入文件
				fileOut.flush();
				// 关闭文件输出流对象
				fileOut.close();

			} catch (Exception e) {
				ByExceltest_001.testResult = false;
				e.printStackTrace();
			}
		}
		public static void main(String[] args) {
			setExcelFile("configs\\知乎测试用例.xlsx");
			
			//System.out.println(getCellData("知乎",0,1));
			System.out.println(getTestCaseLastStepRow("知乎","登录01",2));//登录01其实是8，结束行的行好是8
			System.out.println(getTestCaseLastStepRow("知乎","退出01",8));//登录01其实是8，结束行的行好是8

		}
}
