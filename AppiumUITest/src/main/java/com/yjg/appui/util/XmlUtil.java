package com.yjg.appui.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.yjg.appui.server.Port;
import com.yjg.appui.server.Servers;

/**
 * @author Administrator
 *
 */
public class XmlUtil {
	public static WinOrMac wm =new WinOrMac();

	/**
	 * 读取device.xml配置文件
	 * 
	 * @param filePath
	 * @return
	 * @throws DocumentException
	 */
	public static List<String> readXML(String filePath) throws DocumentException {

		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(filePath));
		// 获取根元素
		Element root = document.getRootElement();
		// System.out.println(root.getName());

		// 获取特定名称的子元素
		@SuppressWarnings("unchecked")
		List<Element> deviceList = root.elements("deviceId");
		// 迭代输出
		List<String> deviceData = new ArrayList<String>();
		for (Element e : deviceList) {
			for (Iterator iter = e.elementIterator(); iter.hasNext();) {
				Element e1 = (Element) iter.next();
				deviceData.add(e1.getText());
			}
		}
		return deviceData;
	}

	/**
	 * 创建device.xml文件
	 * 
	 * @param deviceList
	 * @param appiumPortList
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void createDeviceXml(List<String> deviceList, List<Integer> appiumPortList)
			throws IOException, InterruptedException {
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("Device");
		document.setRootElement(root);
		root.addAttribute("name", "appiumstartlist");
		if (deviceList.size() > 0) {
			for (int j = 0; j < deviceList.size(); j++) {
				Element deviceId = root.addElement("deviceId");
				deviceId.addAttribute("id", String.valueOf(j));
				Element deviceName = deviceId.addElement("deviceName");
				Element appiumPort = deviceId.addElement("appiumPort");
				deviceName.setText(deviceList.get(j));
				appiumPort.setText(String.valueOf(appiumPortList.get(j)));
			}
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2 = new XMLWriter(new FileOutputStream("configs"+wm.doubleBackslant("device.xml")+"device.xml"), format);
		xmlWrite2.write(document);
	}

	/**
	 * 创建testng.xml配置文件
	 * 
	 * @param
	 * @param classname
	 * @throws Exception
	 */
	public static void createTestngXml(String classname) throws Exception {
		Servers servers = new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList = servers.getDevices();
		System.out.println("设备数量" + deviceList.size());
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		Element listeners = root.addElement("listeners");
		Element listener1 = listeners.addElement("listener");
		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
		Element listener2 = listeners.addElement("listener");
		listener2.addAttribute("class-name", "com.yjg.appui.util.ExtentTestNGIReporterListener");
		List<String> s = readXML("configs"+wm.doubleBackslant("device.xml")+"device.xml");
		// {192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for (int j = 0; j < deviceList.size(); j++) {
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			Element paramUuid = test.addElement("parameter");
			paramUuid.addAttribute("name", "udid");
			paramUuid.addAttribute("value", s.get(2 * j));
			Element paramPort = test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value", s.get(2 * j + 1));
			Element classes = test.addElement("classes");
			Element classNode = classes.addElement("class");
			classNode.addAttribute("name", classname);
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"), format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param classesList
	 * @throws Exception
	 *             根据设备数量生成对应的testng xml文件
	 */
	public static void createTestngXml(List<String> classesList) throws Exception {
		Servers servers = new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList = servers.getDevices();
		System.out.println("设备数量" + deviceList.size());
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		Element listeners = root.addElement("listeners");
		Element listener1 = listeners.addElement("listener");
		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
		Element listener2 = listeners.addElement("listener");
		listener2.addAttribute("class-name", "com.yjg.appui.util.ExtentTestNGIReporterListener");
		List<String> s = readXML("configs"+wm.doubleBackslant("device.xml")+"device.xml");

		// {192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for (int j = 0; j < deviceList.size(); j++) {
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			Element paramUuid = test.addElement("parameter");
			paramUuid.addAttribute("name", "udid");
			paramUuid.addAttribute("value", s.get(2 * j));
			Element paramPort = test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value", s.get(2 * j + 1));
			Element classes = test.addElement("classes");
			for (String className : classesList) {
				Element classNode = classes.addElement("class");
				classNode.addAttribute("name", className);
			}
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"), format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createTestngSingleXml(List<String> classesList) throws Exception {
		Servers servers = new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList = servers.getDevices();
		System.out.println("设备数量" + deviceList.size());
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		Element listeners = root.addElement("listeners");
		Element listener1 = listeners.addElement("listener");
		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
		Element listener2 = listeners.addElement("listener");
		listener2.addAttribute("class-name", "com.yjg.appui.util.ExtentTestNGIReporterListener");
		List<String> s = readXML("configs"+wm.doubleBackslant("device.xml")+"device.xml");
		// {192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for (int j = 0; j < deviceList.size(); j++) {
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			Element paramUuid = test.addElement("parameter");
			paramUuid.addAttribute("name", "udid");
			paramUuid.addAttribute("value", s.get(2 * j));
			Element paramPort = test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value", s.get(2 * j + 1));
			Element classes = test.addElement("classes");
			Element classNode = classes.addElement("class");
			classNode.addAttribute("name", classesList.get(j));
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"), format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param classname
	 * @throws Exception
	 *             只生成一个对应的 testng xml文件
	 */
	public static void createUidTestngXml(String udid, String classname) throws Exception {
		// TODO Auto-generated method stub
		Servers servers = new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList = servers.getDevices();
		System.out.println("存在设备数量：【" + deviceList.size() + "】");
		for (int i = 0; i < deviceList.size(); i++) {// 遍历
			if (deviceList.get(i).equals(udid) == true) {// 判断是否存在udid
				System.out.println("==y===" + i);
				Document document = DocumentHelper.createDocument();
				Element root = DocumentHelper.createElement("suite");
				document.setRootElement(root);
				root.addAttribute("name", "Suite");
				root.addAttribute("parallel", "tests");
				root.addAttribute("thread-count", String.valueOf(1));
				System.out.println(i + 1);
				Element listeners = root.addElement("listeners");
				Element listener1 = listeners.addElement("listener");
				listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
				Element listener2 = listeners.addElement("listener");
				listener2.addAttribute("class-name", "com.yjg.appui.util.ExtentTestNGIReporterListener");
				List<String> s = readXML("configs"+wm.doubleBackslant("device.xml")+"device.xml");
				// {192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
				Element test = root.addElement("test");
				test.addAttribute("name", udid);
				Element paramUuid = test.addElement("parameter");
				paramUuid.addAttribute("name", "udid");
				paramUuid.addAttribute("value", udid);
				Element paramPort = test.addElement("parameter");
				paramPort.addAttribute("name", "port");
				paramPort.addAttribute("value", s.get(i));
				Element classes = test.addElement("classes");
				Element classNode = classes.addElement("class");
				classNode.addAttribute("name", classname);
				OutputFormat format = new OutputFormat("    ", true);
				XMLWriter xmlWrite2;
				try {
					xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"), format);
					xmlWrite2.write(document);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			} else {
				System.out.println("不包含此"+udid);

			}

		}

	}
	/***
	 * 
	 * 只拿第一个
	 * */
	public static void createOneTestngXml(String classname) throws Exception {
		// TODO Auto-generated method stub
		Servers servers = new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList = servers.getDevices();
		System.out.println("存在设备数量：【" + deviceList.size() + "】");
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(1));
		Element listeners = root.addElement("listeners");
		Element listener1 = listeners.addElement("listener");
		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
		Element listener2 = listeners.addElement("listener");
		listener2.addAttribute("class-name", "com.yjg.appui.util.ExtentTestNGIReporterListener");
		List<String> s = readXML("configs"+wm.doubleBackslant("device.xml")+"device.xml");
		// {192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for (int j = 0; j < 1; j++) {
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			Element paramUuid = test.addElement("parameter");
			paramUuid.addAttribute("name", "udid");
			paramUuid.addAttribute("value", s.get(2 * j));
			Element paramPort = test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value", s.get(2 * j + 1));
			Element classes = test.addElement("classes");
			Element classNode = classes.addElement("class");
			classNode.addAttribute("name", classname);
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"), format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		// createTestngXml("cn.crazy.appium.network.study.Study");

		createDeviceXml(new Servers(new Port(new DosCmd()), new DosCmd()).getDevices(),
				new Port(new DosCmd()).GeneratPortList(4490, 2));
		List<String> classesList = new ArrayList<String>();
		classesList.add("cn.crazy.appium.network.study.Study");
		classesList.add("cn.crazy.appium.network.study.Study1");
		createTestngSingleXml(classesList);
		// List<String> d=readXML("configs\\device.xml");
		// for(String s:d){
		// System.out.println(s);
		// }
		// SAXReader reader=new SAXReader();
		// Document document=reader.read(new File("configs\\student.xml"));
		// Element root=document.getRootElement();
		// System.out.println(root.attributeValue("name"));
		// List<Element> list=root.elements("student");
		// for(Element e:list){
		// System.out.println(e.getText());
		// }
		// createTestngXml("cn.crazy.appium.testcases.ZhihuLoginTest");
		// Document docment=DocumentHelper.createDocument();
		// Element root=DocumentHelper.createElement("tearcher");
		// docment.setRootElement(root);
		// root.addAttribute("name", "沙陌");
		// String name[]={"国军","唯一","风云九州","静海"};
		// for(int i=0;i<name.length;i++){
		// Element student=root.addElement("student");
		// student.addAttribute("id", String.valueOf(i));
		// student.setText(name[i]);
		// }
		// OutputFormat format = new OutputFormat(" ", true);
		// XMLWriter xmlWrite2 = new XMLWriter(
		// new FileOutputStream("configs\\student.xml"),format);
		// xmlWrite2.write(docment);
	}
}
