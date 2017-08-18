<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>广告位管理</title>
</head>
<body>
	<form action="/admin/adv/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="添加广告位"
				onclick="addInfo('/admin/adv/add');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>广告位编码:</td>
					<td><input type="text" name="adv_code_vague_s"
						value="${adv_code_vague_s}" /></td>
					<td>所属终端:</td>
					<td><select name="the_terminal_s" type="select">
							<option value="">请选择</option>
							<option value="2"
								<c:if test="${the_terminal_s==2}"> selected</c:if>>WEB</option>
							<option value="1" <c:if test="${the_terminal_s==1}"> selected</c:if>>APP</option>
						<lect>
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="搜索" onclick="searchInfo('/admin/adv/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/adv/list');"/>
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
	  				
		  				<th width="10%">广告位编码</th>
		  			
		  				<th width="10%">广告位名称</th>
		  				
		  				<th width="10%">所属终端</th>

				    	<th width="10%">是否启用</th>
		  			
		  				<th width="10%">是否已添加广告</th>

						<th width="15%">广告位介绍</th>
		  				
		  			<th width="10%">操作</th>
	  			</tr>
	  			<c:if test="${!empty advList}">
	  				<c:forEach items="${advList}" var="item" varStatus="status">
	  					<tr>
	  						<td>
		  						<input class="ids" type="checkbox" value="${item.adv_id}"/>
		  					</td>
							
		  					<td>${item.adv_code}</td>
		  					
		  					<td>${item.adv_name}</td>
		  					
		  					<td>
		  						<c:if test="${item.the_terminal==2}">WEB</c:if>
								<c:if test="${item.the_terminal==1}">APP</c:if>
		  					</td>
		  					
							<td>
								<c:if test="${item.state==1}"><span class="span_green">是</span></c:if>
								<c:if test="${item.state==0}"><span class="span_red">否</span></c:if>
							</td>
		  					<td>
		  						<c:if test="${item.is_add_ads==1}"><span class="span_green">是</span></c:if>
								<c:if test="${item.is_add_ads!=1}"><span class="span_red">否</span></c:if>
		  					</td>
							
							<td class="list_td_left">
		  						${item.adv_introduce}
		  					</td>		  						

		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看广告" onclick="goInfo('/admin/ad/list','adv_id','${item.adv_id}');"/>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/adv/edit','${item.adv_id}');"/>
		  					</td>

	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty advList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <input type="hidden" id="adv_id" name="adv_id" value="${adv_id}"/>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

