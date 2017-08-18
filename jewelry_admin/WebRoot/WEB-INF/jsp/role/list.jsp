<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>角色管理</title>
</head>
<body>
	<form action="/admin/role/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增角色"
				onclick="addInfo('/admin/role/add');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>角色编码:</td>
					<td><input type="text" name="role_code_vague_s"
						value="${role_code_vague_s}" /></td>
					<td>状态:</td>
					<td><select name="state_s" type="select">
							<option value="">请选择</option>
							<option value="1"
								<c:if test="${state_s==1}"> selected</c:if>>启用</option>
							<option value="0" <c:if test="${state_s==0}"> selected</c:if>>禁用</option>
						</select>
		  	  		</td>
		  	  		<td>
		  	  			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/role/list');"/>
		  	  			<input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/role/list');"/>
		  	  		</td>
	  	  		</tr>
	  	  	</table>	
  	  		
  	  </div>	
  	  <div class="show_line">
  	  		<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
  	  </div> 
	  <div class="list_div">
	  		<table  id="list_table" border="0" cellspacing="0" cellpadding="0">
	  			<tr>
	  					<th width="3%">
		  					<input class="all" type="checkbox" />
		  				</th>
	  				
	  					<th width="10%">角色编码</th>
	  					
		  				<th width="10%">角色名称</th>
		  				
		  				<th width="10%">平台角色类型</th>
		  				
		  				<th width="10%">状态</th>
		  			
		  				<th width="15%">操作</th>
	  			</tr>
	  			<c:if test="${!empty roleList}">
	  				<c:forEach items="${roleList}" var="item" varStatus="status">
	  					<tr>
	  						<td>
		  						<input class="ids" type="checkbox" value="${item.role_code}"/>
		  					</td>
							
							<td>${item.role_code}</td>
							
		  					<td>${item.role_name}</td>
		  					
		  					<td>
		  						<c:if test="${item.plat_role==0}"><span class="span_green">角色</span></c:if>
								<c:if test="${item.plat_role==1}"><span class="span_red">合伙人</span></c:if>
		  					</td>
		  					
		  					<td>
		  						<c:if test="${item.state==1}"><span class="span_green">已启用</span></c:if>
								<c:if test="${item.state==0}"><span class="span_red">已禁用</span></c:if>
		  					</td>
		  					
		  					<td class="list_td_left">
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="goInfo('/admin/role/edit','${item.role_code}');"/>
		  						<c:if test="${item.is_sys==0}">
		  							<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/role/delete','${item.role_code}');"/>
		  							<!-- 状态操作 -->
			  						<c:if test="${item.state==1}">
			  							<input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用" onclick="commonInfo('/admin/role/limitState','${item.role_code}','确定禁用该角色？');"/>
			  						</c:if>
									<c:if test="${item.state==0}">
										<input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用" onclick="commonInfo('/admin/role/enableState','${item.role_code}','确定启用该角色？');"/>
									</c:if>
		  							
		  						</c:if>
		  						<!--<input class="btn ol_colorbtn ol_bluebtn" type="button" value="权限分配" onclick="goInfo('/admin/role/edit','${item.role_code}');"/>-->
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty roleList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	 <div class="batchDiv">
	  		<span class="batch_span"><input class="all" type="checkbox" />全选</span>
	  		<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/role/batchDelete','确定删除角色？');"/>
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/role/batchEnableState','确定启用角色？');"/>
			<input class="btn ol_colorbtn ol_bredbtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/role/batchLimitState','确定禁用角色？');"/>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

