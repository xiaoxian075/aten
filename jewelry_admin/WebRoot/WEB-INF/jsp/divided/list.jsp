<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>结算分成列表</title>
  </head>
  <body>
  <form action="/admin/divided/list" method="post" >
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
		  	  		<td>会员账户:</td>
		  	  		<td><input type="text" name="login_name_vague_s" value="${login_name_vague_s}"/></td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/divided/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/divided/list');"/>
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
					<th width="10%">会员</th>
					<th width="10%">累计收益(元)</th>
					<th width="10%">会员等级</th>
		  			<th width="5%">操作</th>
	  			</tr>
	  			<c:if test="${!empty dividedList}">
	  				<c:forEach items="${dividedList}" var="item" varStatus="status">
	  					<tr>
							<td>${item.login_name}</td>
							<td>${item.divided_amount}</td>
							<td>${item.account_level}</td>
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看详情" onclick="goInfo('/admin/divided/detail','${item.login_name}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty dividedList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

