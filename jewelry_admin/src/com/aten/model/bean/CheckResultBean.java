package com.aten.model.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckResultBean {

		// 参数map容器
		private HashMap<String, String> paraMap;
		// 错误信息集合
		private List<String> errorList;
		// 返回成功的对象
		private Object returnData;
		// 验证是否成功的状态
		private boolean checkState;// true 验证成功
		// request对象
		private HttpServletRequest request;
		
		public HashMap<String, String> getParaMap() {
			return paraMap;
		}

		public void setParaMap(HashMap<String, String> paraMap) {
			this.paraMap = paraMap;
		}
		
		/**
		 * @author linjunqin
		 * @Description 向Map容器中传参
		 * @param
		 * @date 2017-2-14 上午11:03:41
		 */
		public void setParaMapAddPara(String key,String value) {
			if(this.paraMap==null) paraMap = new HashMap<String,String>();
			paraMap.put(key, value);
		}
		
		public List<String> getErrorList() {
			return errorList;
		}

		public void setErrorList(List<String> errorList) {
			this.errorList = errorList;
		}
		
		/**
		 * @author linjunqin
		 * @Description 向错误提示信息中加入错误信息
		 * @param
		 * @date 2017-2-10 上午11:42:57
		 */
		public void setErrorListaddMsg(String msg){
			if(errorList==null)
				errorList = new ArrayList<String>();
			errorList.add(msg);
		}

		public boolean isCheckState() {
			return checkState;
		}

		public void setCheckState(boolean checkState) {
			this.checkState = checkState;
		}

		public Object getReturnData() {
			return returnData;
		}

		public void setReturnData(Object returnData) {
			this.returnData = returnData;
		}

		public HttpServletRequest getRequest() {
			return request;
		}

		public void setRequest(HttpServletRequest request) {
			this.request = request;
		}

}
