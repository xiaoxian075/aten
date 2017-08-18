<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title></title>
</head>
<body>
	<form action="/admin/accountInterApprove/list" method="post">
		<div class="list_oper_div">
			<input type="button" class="btn return" onclick="returnGo('/admin/integral/list')" value="返回列表" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>审批编号:</td>
					<td><input type="text" name="approve_num_vague_s"
						value="${approve_num_vague_s}" /></td>
					<td>会员帐号:</td>
					<td><input type="text" name="login_name_vague_s"
						value="${login_name_vague_s}" /></td>
					<td>状态:</td>
					<td><select name="audit_state_s" type="select">
							<option value="">请选择</option>
							<option value="0"
								<c:if test="${audit_state_s==0}"> selected</c:if>>待审批</option>
							<option value="1" <c:if test="${audit_state_s==1}"> selected</c:if>>审批通过</option>
							<option value="2" <c:if test="${audit_state_s==2}"> selected</c:if>>审批失败</option>
						</select>
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="搜索" onclick="goInfo('/admin/accountInterApprove/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/accountInterApprove/list');"/>
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
	  				<th width="10%">审批编号</th>
		  			
	  				<th width="10%">会员帐号</th>
		  			
	  				<th width="10%">增减类型</th>
	  			
	  				<th width="10%">积分值</th>
		  			
		  			<th width="10%">状态</th>
		  			
		  			<th width="10%">操作</th>
	  			</tr>
	  			<c:if test="${!empty accountInterApproveList}">
	  				<c:forEach items="${accountInterApproveList}" var="item" varStatus="status">
	  					<tr>
		  					<td>${item.approve_num}</td>
		  					
		  					<td>${item.login_name}</td>
		  					
		  					<td>
								<c:if test="${item.io_type==2}">增加</c:if>
								<c:if test="${item.io_type==1}">减少</c:if>							
							</td>
		  					
		  					<td>
								${item.inter_value}
							</td>
		  					
		  					<td>
		  						<c:if test="${item.audit_state==2}"><span class="span_red">审批失败</span></c:if>
	                            <c:if test="${item.audit_state==1}"><span class="span_green">审批通过</span></c:if>
								<c:if test="${item.audit_state==0}"><span>待审批</span></c:if>
	                         </td>
		  					
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="editInfo('/admin/accountInterApprove/view','${item.ia_id}');"/>
								<c:if test="${item.audit_state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button" value="审批" onclick="editInfo('/admin/accountInterApprove/approve','${item.ia_id}');"/>
								</c:if>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty accountInterApproveList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

