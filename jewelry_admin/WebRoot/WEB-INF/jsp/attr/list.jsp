<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>属性列表</title>
</head>
<body>
	<form action="/admin/attr/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增属性"
				onclick="addInfo('/admin/attr/add');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>属性名称:</td>
					<td><input type="text" name="attr_name_vague_s"
						value="${attr_name_vague_s}" /></td>
					<td>状态:</td>
					<td><select name="state_s" type="select">
							<option value="">请选择</option>
							<option value="1"
								<c:if test="${state_s==1}"> selected</c:if>>启用</option>
							<option value="0" <c:if test="${state_s==0}"> selected</c:if>>禁用</option>
						</select>
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/attr/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/attr/list');"/>
		  	  		</td>
	  	  		</tr>
	  	  	</table>
  	  </div>	
  	  <div class="show_line">
  	  		<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
  	  </div> 
	  <div class="list_div">
	  		<table  id="list_table"  border="0" cellspacing="0" cellpadding="0">
	  			<tr>
					<th width="3%">
						<input class="all" type="checkbox" />
					</th>
	  				
		  				<th width="10%">属性名称</th>
		  			
		  				<th width="10%">状态</th>

		  				
		  			     <th width="10%">操作</th>
	  			</tr>
	  			<c:if test="${!empty attrList}">
	  				<c:forEach items="${attrList}" var="item" varStatus="status">
	  					<tr>
							<td>
								<input class="ids" type="checkbox" value="${item.attr_id}"/>
							</td>

		  					
		  					<td>${item.attr_name}</td>
		  					
		  					<td>
								<c:if test="${item.state==1}"><span class="span_green">已启用</span></c:if>
								<c:if test="${item.state==0}"><span class="span_red">已禁用</span></c:if>
							</td>
		  					
		  						
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/attr/edit','${item.attr_id}');"/>
								<c:if test="${item.state==1}">
									<input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用" onclick="commonInfo('/admin/attr/limitState','${item.attr_id}','确定禁用${item.attr_name},并同步禁用其属性值？');"/>
								</c:if>
								<c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用" onclick="commonInfo('/admin/attr/enableState','${item.attr_id}','确定启用${item.attr_name},并同步启用其属性值？');"/>
								</c:if>
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/attr/delete','${item.attr_id}');"/>
								<input class="btn ol_colorbtn ol_bluebtn" type="button" value="属性值管理" onclick="goInfo('/admin/attrvalue/list/','${item.attr_id}')"/>
							</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty attrList}"><td colspan="4">暂无数据</td></c:if>
				<input type="hidden" id="parameter_id" name="parameter_id" value="${parameter_id}">
	  		</table>
	  </div>
	  <div class="batchDiv">
	  		<span class="batch_span"><input class="all" type="checkbox" />全选</span>
	  		<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/attr/delete','确定删除选中的属性？');"/>
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/attr/enableState','确定启用选中的属性？');"/>
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/attr/limitState','确定禁用选中的属性？');"/>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

