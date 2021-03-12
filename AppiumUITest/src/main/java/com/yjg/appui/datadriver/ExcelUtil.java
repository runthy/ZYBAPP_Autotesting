package com.yjg.appui.datadriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

//本类主要实现后缀为xlsx的 excel文件操作
public class ExcelUtil {

	private  XSSFSheet ExcelWSheet;
	private  XSSFWorkbook ExcelWBook;
	private  XSSFCell Cell;
	private  XSSFRow Row;
	private  String filePath;

	// 设定要操作的 Excel 的文件路径和 Excel 文件中的 sheet 名称
	// 在读写excel的时候，均需要先调用此方法，设定要操作的 excel 文件路径和要操作的 sheet 名称
	public ExcelUtil(String Path, String SheetName)
			throws Exception {
		FileInputStream ExcelFile;
		
		try {
			// 实例化 excel 文件的 FileInputStream 对象
			ExcelFile = new FileInputStream(Path);
			// 实例化 excel 文件的 XSSFWorkbook 对象
			ExcelWBook = new XSSFWorkbook(ExcelFile);
// 实例化 XSSFSheet 对象，指定 excel 文件中的 sheet 名称，后续用于 sheet 中行、列和单元格的操作
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e) {
			throw (e);
		}
		filePath=Path;

	}

	// 读取 excel 文件指定单元格的函数，此函数只支持后缀为xlsx的 excel 文件
	public  String getCellData(int RowNum, int ColNum) throws Exception {

		try {
			// 通过函数参数指定单元格的行号和列号，获取指定的单元格对象
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			// 如果单元格的内容为字符串类型，则使用 getStringCellValue 方法获取单元格的内容
			// 如果单元格的内容为数字类型，则使用 getNumericCellValue() 方法获取单元格的内容
			String CellData="";
//			String CellData=""; = (String) (Cell.getCellType() == XSSFCell.CELL_TYPE_STRING ? Cell
//					.getStringCellValue() + ""
//					: Cell.getNumericCellValue());
			if(Cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
				CellData=Cell.getStringCellValue();
			}else if(Cell.getCellType() ==XSSFCell.CELL_TYPE_NUMERIC){
				DecimalFormat df = new DecimalFormat("0");
				CellData = df.format(Cell.getNumericCellValue());
			}

			return CellData;

		} catch (Exception e) {
			e.printStackTrace();
			return  "";
		}

	}

	// 在 excel 文件的执行单元格中写入数据，此函数只支持后缀为xlsx的 excel 文件写入
	public  void setCellData(int RowNum, int ColNum, String Result)
			throws Exception {

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
				System.out.println("执行完成");

			}
			// 实例化写入 excel 文件的文件输出流对象
			FileOutputStream fileOut = new FileOutputStream(
					filePath);
			// 将内容写入 excel 文件中
			ExcelWBook.write(fileOut);
			// 调用flush 方法强制刷新写入文件
			fileOut.flush();
			// 关闭文件输出流对象
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
			
		}
	}

	// 从 excel 文件获取测试数据的静态方法
	public static Object[][] getTestData(String excelFilePath,
			String sheetName) throws IOException {

		// 根据参数传入的数据文件路径和文件名称，组合出 excel 数据文件的绝对路径
		// 声明一个 file 文件对象
		File file = new File(excelFilePath);

		// 创建 FileInputStream 对象用于读取 excel 文件
		FileInputStream inputStream = new FileInputStream(file);

		// 声明 Workbook 对象
		Workbook Workbook = null;

		// 获取文件名参数的后缀名，判断是xlsx文件还是xls文件
		String fileExtensionName = excelFilePath.substring(excelFilePath.indexOf("."));

		// 判断文件类型如果是xlsx，则使用 XSSFWorkbook 对象进行实例化
		// 判断文件类型如果是xls，则使用 SSFWorkbook 对象进行实例化
		if (fileExtensionName.equals(".xlsx")) {

			Workbook = new XSSFWorkbook(inputStream);
			

		} else if (fileExtensionName.equals(".xls")) {

			Workbook = new HSSFWorkbook(inputStream);

		}

		// 通过 sheetName 参数,生成 sheet 对象
		Sheet Sheet = Workbook.getSheet(sheetName);

		// 获取 excel 数据文件中 sheet1中数据的行数，getLastRowNum 方法获取数据的最后行号
		// getFirstRowNum 方法获取数据的第一行行号，相减之后算出数据的行数
		// 注意：excel 文件的行号和列号都是从 0 开始
		int rowCount = Sheet.getLastRowNum() - Sheet.getFirstRowNum();

		// 创建名为 records 的 list 对象来存储从 excel数据文件读取的数据
		List<Object[]> records = new ArrayList<Object[]>();
		// 使用 2 个 for 循环遍历 excel 数据文件的所有数据（除了第一行，第一行是数据列名称）
		// 所以 i 从1开始，而不是从 0
		for (int i = 1; i<rowCount + 1; i++) {
			// 使用 getRow 方法获取行对象
			Row row = Sheet.getRow(i);

		/* 声明一个数组，来存储 excel 数据文件每行中的测试用例和数据，数组的大小用
		 * getLastCellNum-2 来进行动态声明，实现测试数据个数和数组大小相一致因为 excel 数据文件中      
         * 的测试数据行的最后一个单元格为测试执行结果，倒数第二个单元格为此测试数据行
			 * 是否运行的状态位，所最后两列的单元格数据并不需要传入到测试方法中，所以使用 
              * getLastCellNum-2 的方式去掉每行中的最后两个单元格数据，计算出需要存储的测试数据个数,并
              * 作为测试数据数组的初始化大小
              */
			String fields[] = new String[row.getLastCellNum() - 1];
			//System.out.println(row.getLastCellNum() - 1);
		/* if 用于判断数据行是否要参与测试的执行，excel 文件的倒数第二列为数据行的状态位，标记为			 * "y"表示此数据行要被测试脚本执行，标记为非"y"的数据行均被认为不会参与测试脚本的执行，会
         * 被跳过
         */
			if (row.getCell(row.getLastCellNum()-1).getStringCellValue().equals("y")) {

				for (int j = 0; j<row.getLastCellNum()-1; j++) {
		//判断excel 的单元格字段是数字还是字符，字符串格式调用 getStringCellValue 方法获取
		// 数字格式调用 getNumericCellValue 方法获取
					//fields[j-1] = (String) (row.getCell(j).getCellType()==XSSFCell.CELL_TYPE_STRING?row.getCell(j).getStringCellValue() :""+row.getCell(j).getNumericCellValue());
					if(row.getCell(j).getCellType()==XSSFCell.CELL_TYPE_STRING){
						
						fields[j]=row.getCell(j).getStringCellValue();
					}else if(row.getCell(j).getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						DecimalFormat df = new DecimalFormat("0");
						fields[j] = df.format(row.getCell(j).getNumericCellValue());
					}else{
						System.out.println("格式错误");
					}
				}
				// fields 的数据对象存储到 records的 list 中
				records.add(fields);
			}
			
		}

		// 定义函数返回值，即 Object[][]
		// 将存储测试数据的 list 转换为一个 Object 的二维数组
		Object[][] results = new Object[records.size()][];
		// 设置二维数组每行的值，每行是个object对象
		for (int i = 0; i<records.size(); i++) {
			results[i] = records.get(i);
		}
      	//关闭 excel 文件
		return  results;
	}
	public  int getLastColumnNum(){
		//返回数据文件的最后一列的列号，如果有12列，则结果返回 11 
		return  ExcelWSheet.getRow(0).getLastCellNum()-1;
	}
	public static void main(String[] args) throws Exception {
//		ExcelUtil eu=new ExcelUtil("C:\\Users\\lixionggang\\Desktop/test.xlsx", "Sheet1");
//		
//		System.out.println(eu.getCellData(1, 2));
//		System.out.println(eu.getCellData(1, 3));
//		eu.setCellData(1, 6, "执行失败");
		//eu.setCellData(1, eu.getLastColumnNum(),"yyy");
		Object[][] ob=getTestData("//Users//neo.shi//MyWorkPace//02ScriptWorkpace//AppiumWorkPace//JavaForAppiumTest//AppiumUITest//configs//test.xlsx", "Sheet1");
		for(int i=0;i<ob.length;i++){
			Object[] obl=ob[i];
			System.out.println("========");
			for(int j=0;j<obl.length;j++){
				System.out.println(obl[j]);
			}
		}
	}
}

