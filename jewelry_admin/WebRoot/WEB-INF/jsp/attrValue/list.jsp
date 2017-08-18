<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>属性值管理</title>
  </head>
  <body>
  <form action="/admin/attrvalue/list" method="post" >
	  <div class="list_oper_div">
	  		<input class="btn ol_btn" type="button" value="添加属性值" onclick="addInfo('/admin/attrvalue/add/${attr_id}');"/>
	  		<input class="btn ol_btn" type="button" value="排序" onclick="sortInfo('/admin/attrvalue/sort/${attr_id}');"/>
	  		<input type="button" class="btn return" onclick="returnGo('/admin/attr/list')" value="返回属性列表"/>

	  </div>
  	  <div class="searchDiv">
  	  </div>	
	  <div class="list_div">
	  		<table  id="list_table"  border="0" cellspacing="0" cellpadding="0">
	  			<tr>
	  				   <th width="3%">
		  					<input class="all" type="checkbox" />
		  				</th>
	  			
	  					<th width="10%">属性值名称</th>
	  					
	  					<th width="5%">排序</th>

						<th width="10%">操作</th>
	  			</tr>
	  			<c:if test="${!empty attrValueList}">
	  				<c:forEach items="${attrValueList}" var="item" varStatus="status">
	  					<tr>
	  						<td>
		  						<input class="ids" type="checkbox" value="${item.attr_value_id}"/>
		  					</td>

							<td>${item.attr_value}</td>
							<td>
								<input class="sort_id" type="hidden" value="${item.attr_value_id}"/>
								<input class="sort_val" type="text" value="${item.sort_no}" maxlength="6" />
							</td>
							<%----%>
		  					<%--<td>--%>
		  						<%--<c:if test="${!empty item.attr_value_ico}">--%>
		  							<%--<img class="img60 img" src="${ossImgServerUrl}${item.attr_value_ico}"/>--%>
		  						<%--</c:if>--%>
		  						<%--<c:if test="${empty item.attr_value_ico}">--%>
		  							<%--<img class="img60 img" src="${ossImgServerUrl}${noPicture}"/>--%>
		  						<%--</c:if>--%>
		  					<%--</td>--%>
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/attrvalue/edit','${item.attr_value_id}');"/>
		  						<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="commonInfo('/admin/attrvalue/delete',${item.attr_value_id},'确定删除${item.attr_value}吗？');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty attrValueList}"><td colspan="4">暂无数据</td></c:if>
	  		</table>
	  </div>
	   <div class="batchDiv">
	  		<span class="batch_span"><input class="all" type="checkbox" />全选</span>
	  		<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/attrvalue/batchDelete','确定批量删除属性值？');"/>
	  </div>
	  <div class="page_contain">
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	  <%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
  </form>		
  </body>
 </html> 

