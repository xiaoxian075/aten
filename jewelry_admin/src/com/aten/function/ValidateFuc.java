package com.aten.function;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.aten.client.RedisClient;
import com.aten.model.bean.ValidateBean;
import com.communal.util.JsonUtil;
import com.communal.util.PropertiesUtil;
import com.communal.util.ValidateUtil;
import com.communal.util.XmlUtil;

/**
 * @author linjunqin
 * @Description 更新验证工具缓存
 * @param
 * @date 2017-2-10 下午2:01:56
 */
public class ValidateFuc {

	private static final Logger logger = Logger.getLogger(ValidateFuc.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getClassPath());
		//getFieldXml("E:\\myworkspace\\infuncar-app\\src\\validate.xml");
	}

	/**
	 * @author linjunqin
	 * @Description 解析Xml文件返回list列表
	 * @param
	 * @date 2016-12-22 上午8:40:46
	 */
	public static HashMap<String, String> getValidateFieldXml(String fileName){
		Document xmldoc = XmlUtil.readXml(fileName);
		Element root = xmldoc.getDocumentElement();
		//获取根节点
		//System.out.println(root.getNodeName());//打印根节点
		//获取二级节点的长度
		int secendRootLength = root.getChildNodes().getLength();
		//model与fieldnames键值对
		HashMap<String,String> mMap = new HashMap<String, String>();
		//获取二级节点的数据
		for(int i=0;i<secendRootLength;i++){
			Node secendNode = root.getChildNodes().item(i);
			if(secendNode instanceof Element){
				 //获取二级节点名称	
			     Element model_eElement = (Element) secendNode;
			     //模型名称
			     String modle_name=model_eElement.getAttribute("id");
			     //定义字段拼接String
			     String filed_names="";
			     //获取三级节点的长度
			     int threeRootLength = model_eElement.getChildNodes().getLength();
			     //获取三级节点的数据
			     List<ValidateBean>  vbList = new ArrayList<ValidateBean>();
			     for(int j=0;j<threeRootLength;j++){
			    	 Node threeNode = model_eElement.getChildNodes().item(j);
					 if(threeNode instanceof Element){
						 ValidateBean vb = new ValidateBean();
						 //获取字段名
						 Element field_element = (Element) threeNode;
						 //字段名称
					     String name=field_element.getAttribute("name");
					     vb.setName(name);
					     //是否必填
					     String required=field_element.getAttribute("required");
					     vb.setRequired(required);
					     //字段类型
					     String type=field_element.getAttribute("type");
					     vb.setType(type);
					     //字段最大长度
					     String maxlen=field_element.getAttribute("maxlen");
					     vb.setMaxlen(maxlen);
					     //字段最小长度
					     String minlen=field_element.getAttribute("minlen");
					     vb.setMinlen(minlen);
					     //提醒信息
					     String tip=field_element.getAttribute("tip");
					     vb.setTip(tip);
					     //加入list中
					     vbList.add(vb);
					 }
			     }
			     //将模型存入List对象再转成json字符串
			     System.out.println(modle_name+"===="+JsonUtil.list2json(vbList));
			     mMap.put(modle_name, filed_names);
			     //存入redis缓存中
			     RedisClient.addListJson(modle_name,vbList);
			}
		}
		return mMap;
	}
	
	
}
