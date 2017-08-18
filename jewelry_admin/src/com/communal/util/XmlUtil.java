package com.communal.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import com.communal.util.XmlUtil;

public class XmlUtil {
	
	private static final Logger logger = Logger.getLogger(XmlUtil.class);
	
	/**
	 * @author linjunqin
	 * @Description  读取xml文件
	 * @param
	 * @date 2016-12-22 上午8:28:34
	 */
	public static Document readXml(String filePathName){
		String tempPath = filePathName;
		System.out.println("XML文件的读取路径:"+tempPath);
		InputStream is = null;
		try {
			is = new FileInputStream(tempPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("读取的xml文件未找到");
			e.printStackTrace();
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			Document xmldoc = builder.parse(is);
			return xmldoc;
		} catch (SAXException e) {
			logger.error("解析xml文件异常");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO错误");
			e.printStackTrace();
		}
		return null;
	}
}
