<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>字典管理</title>
</head>
<body>
	<form action="/admin/commpara/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增字典"
				onclick="addInfo('/admin/commpara/add');" /> <input
				class="btn ol_btn" type="button" value="排序"
				onclick="sortInfo('/admin/commpara/sort');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>字典编码:</td>
					<td><input type="text" name="para_code_s"
						value="${para_code_s}" /></td>
					<td>字典值名称:</td>
					<td><input type="text" name="para_name_s"
						value="${para_name_s}" /></td>
					<td>状态:</td>
					<td><select name="state_s" type="select">
							<option value="">请选择</option>
							<option value="1"
								<c:if test="${state_s==1}"> selected</c:if>>启用</option>
						<option value="0" <c:if test="${state_s==0}"> selected</c:if>>禁用</option>
					</select>
	  	  		</td>
	  	  		<td>
	  	  			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/commpara/list');"/>
		  	  		<input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/commpara/list');"/>
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
		  				<th width="5%">排序</th>
		  			
		  				<th width="10%">字典编码</th>
		  			
		  				<th width="10%">字典值名称</th>
		  			
		  				<th width="10%">字典值</th>
		  			
		  				<th width="10%">状态</th>
		  				
		  				<th width="5%">操作</th>
	  			</tr>
	  			<c:if test="${!empty commparaList}">
	  				<c:forEach items="${commparaList}" var="item" varStatus="status">
	  					<tr>
	  						<td>
		  						<input class="ids" type="checkbox" value="${item.para_id}"/>
		  					</td>
	  						<td>
	  							<input class="sort_id" type="hidden" value="${item.para_id}"/>
	  							<input class="sort_val sort_no" type="text" value="${item.sort_no}" maxlength="6" />
	  						</td>
							
		  					<td>${item.para_code}</td>
		  					
		  					<td>${item.para_name}</td>
		  					
		  					<td>${item.para_key}</td>
		  					
		  					<td>
		  						<c:if test="${item.state==1}"><span class="span_green">已启用</span></c:if>
								<c:if test="${item.state==0}"><span class="span_red">已禁用</span></c:if>
		  					</td>
		  						
		  					<td>
		  					
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="goInfo('/admin/commpara/edit','${item.para_id}');"/>
		  						<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/commpara/delete','${item.para_id}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty commparaList}"><td colspan="7">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="batchDiv">
	  		<span class="batch_span"><input class="all" type="checkbox" />全选</span>
	  		<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/commpara/batchDelete','确定删除字典？');"/>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

