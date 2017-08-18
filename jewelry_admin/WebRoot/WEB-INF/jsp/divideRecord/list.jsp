<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>记录分成比例记录流管理</title>
</head>
<body>
	<form action="/admin/dividerecord/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增记录分成比例记录流"
				onclick="addInfo('/admin/dividerecord/add');" /> <input
				class="btn ol_btn" type="button" value="排序"
				onclick="sortInfo('/admin/dividerecord/sort');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>品牌名称:</td>
					<td><input type="text" name="brand_name_s"
						value="${brand_name_s}" /></td>
					<td>状态:</td>
					<td><select name="state_s" type="select">
							<option value="">请选择</option>
							<option value="1"
								<c:if test="${state_s==1}"> selected</c:if>>启用</option>
							<option value="0" <c:if test="${state_s==0}"> selected</c:if>>禁用</option>
						</select>
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/dividerecord/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/dividerecord/list');"/>
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
	  				
		  				<th width="10%">-</th>
		  			
		  				<th width="10%">-</th>
		  			
		  				<th width="10%">-</th>
		  			
		  				<th width="10%">-</th>
		  			
		  				<th width="10%">-</th>
		  			
		  				<th width="10%">-</th>
		  				
		  			<th width="5%">操作</th>
	  			</tr>
	  			<c:if test="${!empty divideRecordList}">
	  				<c:forEach items="${divideRecordList}" var="item" varStatus="status">
	  					<tr>
	  						<td>${status.count}</td>
							
		  					<td>${item.dr_record}</td>
		  					
		  					<td>${item.rate_id}</td>
		  					
		  					<td>${item.divide_rate}</td>
		  					
		  					<td>${item.oper_man_id}</td>
		  					
		  					<td>${item.oper_man}</td>
		  					
		  					<td>${item.oper_time}</td>
		  					
		  						
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/dividerecord/edit','${item.brand_id}');"/>
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/dividerecord/delete','${item.brand_id}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty divideRecordList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="batchDiv">
	  		<span class="batch_span"><input class="all" type="checkbox" />全选</span>
	  		<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/dividerecord/batchdelete','确定删除记录分成比例记录流？');"/>
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/dividerecord/batchenablestate','确定启用记录分成比例记录流？');"/>
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/dividerecord/batchlimitstate','确定禁用记录分成比例记录流？');"/>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

