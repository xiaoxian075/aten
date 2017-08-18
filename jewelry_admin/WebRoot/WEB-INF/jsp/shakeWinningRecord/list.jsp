<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>摇一摇优惠卷中奖记录</title>
  </head>
  <body>
  <form action="/admin/shakewinningrecord/list" method="post" >
	  <div class="list_oper_div">
		  <input class="btn return" type="button" value="返回摇一摇活动列表"
				 onclick="goInfo('/admin/shake/list');" />
	  </div>
  	  <div class="searchDiv">
  	  		<table class="searchTable">
				<input type="hidden" name="parameter_id" value="${parameter_id}">
  	  			<tr>
					<td>中奖人账户:</td>
					<td><input type="text" name="login_name_vague_s" value="${login_name_vague_s}"/></td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/shakewinningrecord/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/shakewinningrecord/list');"/>
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
					    <th width="10%">所属活动</th>

		  				<th width="10%">奖项设置标识</th>
		  			
		  				<th width="10%">中奖人帐户</th>
		  			
		  				<th width="10%">中奖时间</th>
		  			
		  				<%--<th width="10%">是否已领奖</th>--%>
		  			<%----%>
		  				<%--<th width="10%">领奖时间</th>--%>

	  			</tr>
	  			<c:if test="${!empty shakeWinningRecordList}">
	  				<c:forEach items="${shakeWinningRecordList}" var="item" varStatus="status">
	  					<tr>
							<td>${item.shake_name}</td>
		  					<td>${item.awards_name}</td>
		  					
		  					<td>${item.login_name}</td>

		  					
		  					<td>${item.draw_time}</td>

		  					<%--<td>--%>
								<%--<c:if test="${item.is_draw==1}"><span style="color:green">已领奖</span></c:if>--%>
								<%--<c:if test="${item.is_draw==0}"><span style="color:red">未领奖</span></c:if>--%>
							<%--</td>--%>

		  					<%--<td>${item.accept_time}</td>--%>


	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty shakeWinningRecordList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

