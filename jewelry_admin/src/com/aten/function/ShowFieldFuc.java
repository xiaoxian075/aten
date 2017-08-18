package com.aten.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.aten.model.bean.ShowFieldBean;
import com.aten.model.bean.ShowFieldModelBean;
import com.communal.util.ProjectUtil;
import com.communal.util.StringUtil;
import com.communal.util.XmlUtil;
public class ShowFieldFuc {
	
	private static HashMap<String, Object> showFieldMap = null;

	public static void main(String[] args) {

		loadShowField();

	}

	
	/**
	 * @author linjunqin
	 * @Description 获取查询需要显示的字段,别名列表
	 * @param
	 * @date 2017-2-20 下午2:29:01
	 */
	public static List<ShowFieldBean> getFiledList(String model){
		//if(showFieldMap==null){
			showFieldMap = loadShowField();
		//}
		List<ShowFieldBean> sfbList=new ArrayList<ShowFieldBean>();
		if(showFieldMap.containsKey(model)){
			ShowFieldModelBean sfmb = (ShowFieldModelBean)showFieldMap.get(model);
			sfbList = sfmb.getShowFieldList();
		}
		return sfbList;
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 根据model从HashMap中读取显示字段的字符串
	 * @param  model
	 * @date 2016-12-21 下午4:45:08
	 */
	public static String getShowFields(String model){
		//if(showFieldMap==null){
			showFieldMap = loadShowField();
		//}
		String showFieldStr ="";
		if(showFieldMap.containsKey(model)){
			ShowFieldModelBean sfmb = (ShowFieldModelBean)showFieldMap.get(model);
			showFieldStr = sfmb.getShowFields();
		}
		return showFieldStr;
	}
	
	/**
	 * @author linjunqin
	 * @Description 读取显示字段的文件存入HashMap中
	 * @date 2016-12-21 下午4:26:17
	 */
	public static HashMap<String, Object> loadShowField() {
		//获取根目录
		String rootPath = ProjectUtil.getDeployClassPath();
		//获取验证文件的路径
		String sfFilePath = rootPath + "/show-field.xml";
		//开始读取数据存入Map中  key:model  value:显示字段的字符串
		return getShowFieldXml(sfFilePath);
	}

	/**
	 * @author linjunqin
	 * @Description 解析Xml文件返回list列表
	 * @param
	 * @date 2016-12-22 上午8:40:46
	 */
	public static HashMap<String, Object> getShowFieldXml(String fileName){
		Document xmldoc = XmlUtil.readXml(fileName);
		Element root = xmldoc.getDocumentElement();
		//获取根节点
		//System.out.println(root.getNodeName());//打印根节点
		//获取二级节点的长度
		int secendRootLength = root.getChildNodes().getLength();
		//model与fieldnames键值对
		HashMap<String,Object> mMap = new HashMap<String, Object>();
		//获取二级节点的数据
		for(int i=0;i<secendRootLength;i++){
			List<ShowFieldBean> sfbList = new ArrayList<ShowFieldBean>();
			Node secendNode = root.getChildNodes().item(i);
			if(secendNode instanceof Element){
				 ShowFieldModelBean sfmb = new ShowFieldModelBean();
				 //获取二级节点名称	
			     Element model_eElement = (Element) secendNode;
			     //模型名称
			     String modle_name=model_eElement.getAttribute("id");
			     //定义字段拼接String
			     String filed_names="";
			     //获取三级节点的长度
			     int threeRootLength = model_eElement.getChildNodes().getLength();
			     //获取三级节点的数据
			     for(int j=0;j<threeRootLength;j++){
			    	 Node threeNode = model_eElement.getChildNodes().item(j);
					 if(threeNode instanceof Element){
						 ShowFieldBean sfb = new ShowFieldBean();
						 //获取字段名
						 Element field_element = (Element) threeNode;
					     String field_name=field_element.getAttribute("name");
					     String show_field_name=field_name;
					     //去除点号
					     if(field_name.indexOf(".")>0){
					    	 show_field_name = field_name.substring(field_name.indexOf(".")+1, field_name.length());
						 }
					     String alias_name=field_element.getAttribute("alias");
					     //System.out.println(alias_name+"=====");
					     sfb.setField_name(show_field_name);
					     if(!StringUtil.isEmpty(alias_name)){
					    	 sfb.setAlias_name(alias_name);
					    	 filed_names+=field_name+" as \""+alias_name+"\","; 
					     }else{
					    	 sfb.setAlias_name(show_field_name);
					    	 filed_names+=field_name+" as \""+show_field_name+"\",";
					     }
					     //获取类型,用于显示原列名还是别名显示
					     String type=field_element.getAttribute("type");
					     if(StringUtil.isEmpty(type)){
					    	 type="1";
					     }
					     sfb.setType(type);
					     sfbList.add(sfb);
					 }
			     }
			     //处理字段字符串去掉最后的逗号
			     if(filed_names.endsWith(",")){
			    	 filed_names = filed_names.substring(0, filed_names.lastIndexOf(","));
			     }
			     //将模型数据存入hashMap中
			     sfmb.setShowFields(filed_names);
			     sfmb.setShowFieldList(sfbList);
			     System.out.println(modle_name+"===="+sfmb.getShowFields());
			     //System.out.println(modle_name+"===="+sfmb.getShowFieldList());
			     mMap.put(modle_name, sfmb);
			}
		}
		return mMap;
	}
	
}
