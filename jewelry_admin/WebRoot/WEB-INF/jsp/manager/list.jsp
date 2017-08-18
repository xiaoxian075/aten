<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>用户管理</title>
</head>
<body>
	<form action="/admin/manager/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增用户"
				onclick="addInfo('/admin/manager/add');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>用户名:</td>
					<td><input type="text" name="mana_name_vange_s"
						value="${mana_name_vange_s}" /></td>
					<td>姓名:</td>
					<td><input type="text" name="real_name_s"
						value="${real_name_s}" /></td>
					<td>手机号:</td>
					<td><input type="text" name="phone_s" value="${phone_s}" /></td>
					<td>所属角色:</td>
					<td><select class="" id="role_code_s" name="role_code_s"
						isrequired="yes" type="select">
							<option value="">请选择</option>
							<c:if test="${!empty roleList}">
								<c:forEach items="${roleList}" var="item" varStatus="status">
									<option value="${item.role_code}"
										<c:if test="${item.role_code==role_code_s}">selected</c:if>>${item.role_name}</option>
								</c:forEach>
							</c:if>
						</select>
		  	  		</td>
		  	  		<td>状态:</td>
		  	  		<td>
		  	  			<select name="state_s" type="select" >
							<option value="">请选择</option>
							<option value="1" <c:if test="${state_s==1}"> selected</c:if>>启用</option>
							<option value="0" <c:if test="${state_s==0}"> selected</c:if>>禁用</option>
						</select>
		  	  		</td>
		  	  		<td>
		  	  			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/manager/list');"/>
		  	  			<input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/manager/list');"/>
		  	  		</td>
	  	  		</tr>
	  	  	</table>
  	  </div>	
  	  <div class="show_line">
  	  		<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
  	  </div> 
	  <div class="list_div">
	  		<table id="list_table"  border="0" cellspacing="0" cellpadding="0">
	  			<tr>
	  					<th width="3%">
		  					<input class="all" type="checkbox" />
		  				</th>
		  				<th width="10%">用户名</th>
		  				<th width="10%">姓名</th>
		  				<th width="10%">所属角色</th>
		  				<th width="10%">手机号</th>
		  				<th width="10%">状态</th>
		  				<th width="16%">操作</th>
	  			</tr>
	  			<c:if test="${!empty managerList}">
	  				<c:forEach items="${managerList}" var="item"  varStatus="status">
	  					<tr>
	  						<td>
		  						<input class="ids" type="checkbox" value="${item.mana_id}"/>
		  					</td>
		  					<td>${item.mana_name}</td>
		  					<td>${item.real_name}</td>
		  					<td>${item.role_name}</td>
		  					<td>${item.phone}</td>
		  					<td>
		  						<c:if test="${item.state==1}"><span class="span_green">已启用</span></c:if>
								<c:if test="${item.state==0}"><span class="span_red">已禁用</span></c:if>
		  					</td>
		  					<td class="list_td_left">  						
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="editInfo('/admin/manager/view','${item.mana_id}');"/>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/manager/edit','${item.mana_id}');"/>
		  						<!-- 状态操作 -->
		  						<c:if test="${item.state==1 && item.mana_type==1  && item.role_code!='syscode'}">
		  							<input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用" onclick="commonInfo('/admin/manager/limitState','${item.mana_id}','确定禁用该用户？');"/>
		  						</c:if>
								<c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用" onclick="commonInfo('/admin/manager/enableState','${item.mana_id}','确定启用该用户？');"/>
								</c:if>
								<input class="btn ol_colorbtn ol_bluebtn" type="button" value="初始化密码" onclick="commonInfo('/admin/manager/initpwd','${item.mana_id}','确定初始化当前账号密码？');"/>
		  						<c:if test="${item.mana_type==1 && item.role_code!='syscode'}">
		  							<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="commonInfo('/admin/manager/delete','${item.mana_id}','确定删除用户${item.mana_name}吗');"/>
		  						</c:if>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty managerList}"><td colspan="7">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="batchDiv">
	  		<span class="batch_span"><input class="all" type="checkbox" />全选</span>
	  		<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/manager/batchDelete','确定删除用户？');"/>
			<input class="btn ol_colorbtn ol_bredbtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/manager/batchLimitState','确定禁用用户？');"/>
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/manager/batchEnableState','确定启用用户？');"/>
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="初始化密码" onclick="commonBatchInfo('/admin/manager/batchinitpwd','确定初始化密码？');"/>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

