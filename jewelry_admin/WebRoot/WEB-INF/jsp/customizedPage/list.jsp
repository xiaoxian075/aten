<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>专题页面管理</title>
  </head>
  <body>
  <form action="/admin/customizedPage/list" method="post" >
	  <div class="list_oper_div">
	  		<input class="btn ol_btn" type="button" value="新增专题页面" onclick="addInfo('/admin/customizedPage/add');"/>
	  </div>
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
		  	  		<td>页面标题:</td>
		  	  		<td><input type="text" name="page_title_vague_s" value="${page_title_vague_s}"/></td>
		  	  		<td>页面类型:</td>
		  	  		<td>
		  	  			<select name="page_type_vague_s" type="select" >
							<option value="">请选择</option>
		                    <option value="10" <c:if test="${page_type_vague_s==10}"> selected</c:if>>app原生页面</option>
		                    <option value="11" <c:if test="${page_type_vague_s==11}"> selected</c:if>>h5代码页面</option>
						</select>
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/customizedPage/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/customizedPage/list');"/>
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
						<th width="10%">页面编号</th>
		  				<th width="10%">页面标题</th>
		  				<th width="10%">页面类型</th>
						<th width="10%">创建时间</th>
						<th width="10%">最后时间</th>
		  				<th width="10%">操作</th>
	  			</tr>
	  			<c:if test="${!empty customizedPageList}">
	  				<c:forEach items="${customizedPageList}" var="item" varStatus="status">
	  					<tr>
							<td>${item.pageUnique}</td>
		  					<td>${item.pageTitle}</td>
							
		  					<td>
								<c:if test="${item.pageType==10}">app原生页面</c:if>
	                            <c:if test="${item.pageType==11}">h5代码页面</c:if>
							</td>
							<td>${item.createTime}</td>
							<td>${item.lastTime}</td>
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/customizedPage/edit','${item.pageUnique}');"/>
								<input class="btn ol_colorbtn ol_bluebtn" type="button" value="专题页面" onclick="editInfo('/admin/customizedPage/viewEdit','${item.pageUnique}');"/>
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/customizedPage/delete','${item.pageUnique}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty customizedPageList}"><td colspan="8">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

