<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>物流承运商管理</title>
</head>
<body>
	<form action="/admin/fastmail/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增物流承运商"
				onclick="addInfo('/admin/fastmail/add');" /> <input
				class="btn ol_btn" type="button" value="排序"
				onclick="sortInfo('/admin/fastmail/sort');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>物流承运商名称:</td>
					<td><input type="text" name="fast_name_s"
						value="${fast_name_s}" /></td>
					<td>状态:</td>
					<td><select name="state_s" type="select">
							<option value="">请选择</option>
							<option value="1"
								<c:if test="${state_s==1}"> selected</c:if>>启用</option>
							<option value="0" <c:if test="${state_s==0}"> selected</c:if>>禁用</option>
						</select>
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/fastmail/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/fastmail/list');"/>
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
	  				
		  				<th width="7%">排序</th>
		  				
		  				<th width="7%">物流承运商编号</th>
		  			
		  				<th width="7%">物流承运商名称</th>
		  			
		  				<th width="7%">物流承运商logo</th>
		  			
		  				<!-- <th width="7%">是否支持保价</th>
		  			
		  				<th width="7%">配送方式描述</th>
		  			
		  				<th width="7%">保价费率</th>
		  			
		  				<th width="7%">最低保价额</th>
		  			
		  				<th width="7%">最高保价额</th>
		  			
		  				<th width="7%">是否支持到付</th>
		  			
		  				<th width="7%">配送方式</th>
		  			
		  				<th width="7%">快运单号格式</th> -->
		  			
		  				<th width="4%">状态</th>
		  				
		  			<th width="5%">操作</th>
	  			</tr>
	  			<c:if test="${!empty fastmailList}">
	  				<c:forEach items="${fastmailList}" var="item" varStatus="status">
	  					<tr>
	  						<td>
		  						<input class="ids" type="checkbox" value="${item.fast_id}"/>
		  					</td>
					
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.fast_id}"/>
	  							<input class="sort_val" type="text" value="${item.sort_no}" maxlength="6" />
		  					</td>
		  					
		  					<td>${item.fast_code}</td>
		  					
		  					<td>${item.fast_name}</td>
		  					
		  					<td><img class="img180" src="${ossImgServerUrl}${item.fast_logo}"/></td>
		  					
		  					<%-- 
		  					<td>
		  						<c:if test="${item.is_insured==0}">否</c:if>
		  						<c:if test="${item.is_insured==1}">是</c:if>
		  					</td>
		  					
		  					<td>${item.fast_desc}</td>
		  					
		  					<td>${item.rate}</td>
		  					
		  					<td>${item.mix_insured}</td>
		  					
		  					<td>${item.max_insured}</td>
		  					
		  					<td>
		  						<c:if test="${item.is_reach_pay==0}">否</c:if>
		  						<c:if test="${item.is_reach_pay==1}">是</c:if>
		  					</td>
		  					
		  					
		  					<td>${item.default_temp}</td>
		  					
		  					<td>${item.waybill_rule}</td>
		  					 --%>
		  					<td>
		  						<c:if test="${item.state==0}">已禁用</c:if>
		  						<c:if test="${item.state==1}">已启用</c:if>
		  					</td>
		  					
		  						
		  					<td>
		  						<%-- <input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="goInfo('/admin/fastmail/view','${item.fast_id}');"/> --%>
		  						<!-- 状态操作 -->
		  						<c:if test="${item.state==1}">
		  							<input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用" onclick="commonInfo('/admin/fastmail/limitState','${item.fast_id}','确定禁用该物流承运商？');"/>
		  						</c:if>
								<c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用" onclick="commonInfo('/admin/fastmail/enableState','${item.fast_id}','确定启用该物流承运商？');"/>
								</c:if>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/fastmail/edit','${item.fast_id}');"/>
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/fastmail/delete','${item.fast_id}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty fastmailList}"><td colspan="15">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="batchDiv">
	  		<span class="batch_span"><input class="all" type="checkbox" />全选</span>
	  		<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/fastmail/batchDelete','确定删除物流承运商？');"/>
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/fastmail/batchEnableState','确定启用物流承运商？');"/>
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/fastmail/batchLimitState','确定禁用物流承运商？');"/>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

