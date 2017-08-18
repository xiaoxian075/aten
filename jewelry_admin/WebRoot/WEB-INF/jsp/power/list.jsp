<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>权限管理</title>
</head>
<body>
	<form action="/admin/power/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="添加权限"
				onclick="addInfo('/admin/power/add');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>权限名称:</td>
					<td><input type="text" name="power_name_s"
						value="${power_name_s}" /></td>
					<td>权限地址:</td>
					<td><input type="text" name="url_search_s"
						value="${url_search_s}" /></td>
					<td>角色类型:</td>
					<td><select name="plat_role_s" type="select">
							<option value="">请选择</option>
							<option value="0"
								<c:if test="${plat_role_s==0}"> selected</c:if>>运营商</option>
					</select>
	  	  		</td>
	  	  		<td>是否控制:</td>
				<td><select name="is_control_power_s" type="select">
						<option value="">请选择</option>
						<option value="1" <c:if test="${is_control_power_s==1}"> selected</c:if>>是</option>
						<option value="0" <c:if test="${is_control_power_s==0}"> selected</c:if>>否</option>
					</select>
	  	  		</td>
	  	  		<td>
	  	  			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="搜索" onclick="searchInfo('/admin/power/list');"/>
	  	  			<input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/power/list');"/>
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
	  				<th width="5%">ID</th>

					<th width="10%">权限名称</th>
	  				
	  				<th width="15%">权限地址</th>
		  			
		  			<th width="10%">角色类型</th>
		  			
	  				<th width="15%">所属菜单</th>
	  				
	  				<th width="10%">是否控制</th>
	  				
	  				<th width="10%">操作</th>
	  			</tr>
	  			<c:if test="${!empty powerList}">
	  				<c:forEach items="${powerList}" var="item" varStatus="status">
	  					<tr>
	  						<td>${status.count}</td>
							
							<td>${item.power_name}</td>
		  					
		  					<td class="list_td_left">${item.url}</td>
	  						
	  						<td>
	  							<c:if test="${item.plat_role==0}"> 运营商 </c:if>
	  						</td>
	  						
							<td class="list_td_left">${item.menu_name_str}</td>
							
		  					<td>
								<c:if test="${item.is_control_power==0}"><span class="span_red">否</span></c:if>
								<c:if test="${item.is_control_power==1}"><span class="span_green">是</span></c:if>
							</td>
		  					
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/power/edit','${item.power_id}');"/>
		  						<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/power/delete','${item.power_id}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty powerList}"><td colspan="8">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

