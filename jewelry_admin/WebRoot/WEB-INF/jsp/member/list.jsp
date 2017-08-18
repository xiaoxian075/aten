<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>会员列表</title>
  </head>
  <body>
  <form action="/admin/member/list" method="post" >
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
		  	  		<td>用户名:</td>
		  	  		<td><input type="text" name="login_name_vague_s" value="${login_name_vague_s}"/></td>
		  	  		<td>手机号:</td>
		  	  		<td><input type="text" name="mobile_vague_s" value="${mobile_vague_s}"/></td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/member/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/member/list');"/>
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
	  				
		  				<th width="10%">用户名</th>
		  			
		  				<th width="10%">昵称</th>
		  			
		  				<th width="10%">性别</th>
		  			
		  				<th width="10%">手机号</th>
		  			
		  				<th width="10%">会员等级</th>
		  			
		  				<th width="8%">余额（元）</th>
		  			
		  				<th width="8%">积分</th>
		  			
		  				<th width="8%">收益（元）</th>
		  			
		  				<th width="8%">累计收益（元）</th>
		  			
		  				<th width="10%">最后登录时间</th>
		  			
		  			<th width="10%">操作</th>
	  			</tr>
	  			<c:if test="${!empty memberList}">
	  				<c:forEach items="${memberList}" var="item" varStatus="status">
	  					<tr>
		  					
		  					<td>${item.login_name}</td>
		  					
		  					<td>${item.nick_name}</td>
		  					
		  					<td>${item.sex}</td>
		  					
		  					<td>${item.mobile}</td>
		  					
		  					<td>${item.lev}</td>
		  					
		  					<td>${item.balance}</td>
		  					
		  					<td>${item.integral}</td>
		  					
		  					<td>${item.earnings}</td>
		  					
		  					<td>${item.total_earnings}</td>
		  					
		  					<td>${item.last_time}</td>
		  					
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看资金流" onclick="goInfo('/admin/member/listBill','${item.id}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty memberList}"><td colspan="11">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

