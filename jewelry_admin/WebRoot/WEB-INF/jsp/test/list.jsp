<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>测试管理</title>
</head>
<body>
	<form action="/admin/test/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增测试"
				onclick="addInfo('/admin/test/add');" /> <input class="btn ol_btn"
				type="button" value="排序" onclick="sortInfo('/admin/test/sort');" />
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
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/test/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/test/list');"/>
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
	  				<th width="3%">
	  					<input class="all" type="checkbox" />
	  				</th>
	  				
	  				<th width="10%">排序</th>
	  				
		  				<th width="10%">1</th>
		  			
		  				<th width="10%">1</th>
		  			
		  				<th width="10%">1</th>
		  			
		  				<th width="10%">0：预售结束时间后 1：支付成功后</th>
		  			
		  				<th width="10%">1</th>
		  			
		  				<th width="10%">1</th>
		  			
		  				<th width="10%">最多三位</th>
		  				
		  			<th width="5%">操作</th>
	  			</tr>
	  			<c:if test="${!empty testList}">
	  				<c:forEach items="${testList}" var="item" varStatus="status">
	  					<tr>
	  						<td>${status.count}</td>
							
		  					<td>${item.full_id}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.full_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.full_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.goods_id}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.full_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.full_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.presale_endtime}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.full_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.full_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.send_time_type}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.full_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.full_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.send_day_num}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.full_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.full_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.send_time}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.full_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.full_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.limit_buy_num}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.full_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.full_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  						
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="goInfo('/admin/test/view','${item.full_id}');"/>
		  						<!-- 状态操作 -->
		  						<c:if test="${item.state==1}">
		  							<input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用" onclick="commonInfo('/admin/test/limitState','${item.full_id}','确定禁用该测试？');"/>
		  						</c:if>
								<c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用" onclick="commonInfo('/admin/test/enableState','${item.full_id}','确定启用该测试？');"/>
								</c:if>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/test/edit','${item.full_id}');"/>
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/test/delete','${item.full_id}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty testList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="batchDiv">
	  		<span class="batch_span"><input class="all" type="checkbox" />全选</span>
	  		<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/test/batchDelete','确定删除测试？');"/>
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/test/batchEnableState','确定启用测试？');"/>
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/test/batchLimitState','确定禁用测试？');"/>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

