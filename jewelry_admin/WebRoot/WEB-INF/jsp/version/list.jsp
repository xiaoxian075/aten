<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>版本更新管理</title>
  </head>
  <body>
  <form action="/admin/version/list" method="post" >
	  <div class="list_oper_div">
	  		<input class="btn ol_btn" type="button" value="新增版本" onclick="addInfo('/admin/version/add');"/>
	  </div>
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
		  	  		<td>版本号:</td>
		  	  		<td><input type="text" name="update_version_vague_s" value="${update_version_vague_s}"/></td>
		  	  		<td>是否更新:</td>
		  	  		<td>
		  	  			<select name="is_update_s" type="select" >
							<option value="">请选择</option>
							<option value="1" <c:if test="${is_update_s==1}"> selected</c:if>>是</option>
							<option value="0" <c:if test="${is_update_s==0}"> selected</c:if>>否</option>
						</select>
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/version/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/version/list');"/>
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
		  			<th width="10%">更新系统</th>
		  			
		  			<th width="10%">版本号</th>
		  			
		  			<th width="10%">更新路径</th>
		  			
		  			<th width="10%">数字版本号</th>
		  			
		  			<th width="10%">是否更新</th>
		  			
		  			<th width="10%">更新时间</th>
		  			
		  			<th width="10%">是否强制更新</th>
		  				
		  			<th width="10%">操作</th>
	  			</tr>
	  			<c:if test="${!empty versionList}">
	  				<c:forEach items="${versionList}" var="item" varStatus="status">
	  					<tr>
		  					<td>
								<c:if test="${item.sys_type==1}">
									<span class="span_green">安卓</span>
								</c:if> 
								<c:if test="${item.sys_type==0}">
									<span class="span_green">IOS</span>
								</c:if>
							</td>
		  					
		  					<td>${item.update_version}</td>
		  					
		  					<td>${item.update_apk_url}</td>
		  					
		  					<td>${item.digital_version}</td>
		  					
		  					<td>
								<c:if test="${item.is_update==1}">
									<span class="span_green">是</span>
								</c:if> 
								<c:if test="${item.is_update==0}">
									<span class="span_red">否</span>
								</c:if>
							</td>
		  					
		  					<td>${item.update_time}</td>
		  					
		  					<td>
								<c:if test="${item.force_update==1}">
									<span class="span_green">是</span>
								</c:if> 
								<c:if test="${item.force_update==0}">
									<span class="span_red">否</span>
								</c:if>
							</td>
		  						
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/version/edit','${item.version_id}');"/>
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/version/delete','${item.version_id}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty versionList}"><td colspan="8">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

