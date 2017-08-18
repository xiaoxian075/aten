<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>消息推送管理</title>
  </head>
  <body>
  <form action="/admin/sysmsg/list" method="post" >
	  <div class="list_oper_div">
	  		<input class="btn ol_btn" type="button" value="新增消息推送" onclick="addInfo('/admin/sysmsg/add');"/>
	  		<input class="btn ol_btn" type="button" value="排序" onclick="sortInfo('/admin/sysmsg/sort');"/>
	  </div>
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
		  	  		<td>品牌名称:</td>
		  	  		<td><input type="text" name="brand_name_s" value="${brand_name_s}"/></td>
		  	  		<td>状态:</td>
		  	  		<td>
		  	  			<select name="state_s" type="select" >
							<option value="">请选择</option>
							<option value="1" <c:if test="${state_s==1}"> selected</c:if>>启用</option>
							<option value="0" <c:if test="${state_s==0}"> selected</c:if>>禁用</option>
						</select>
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/sysmsg/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/sysmsg/list');"/>
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
	  				
		  				<th width="10%">消息标识</th>
		  			
		  				<th width="10%">帐户标识</th>
		  			
		  				<th width="10%">消息标题</th>
		  			
		  				<th width="10%">消息简介</th>
		  			
		  				<th width="10%">消息内容</th>
		  			
		  				<th width="10%">消息发布时间</th>
		  			
		  				<th width="10%">是否已读</th>
		  			
		  				<th width="10%">类型</th>
		  			
		  				<th width="10%">id</th>
		  				
		  			<th width="5%">操作</th>
	  			</tr>
	  			<c:if test="${!empty sysmsgList}">
	  				<c:forEach items="${sysmsgList}" var="item" varStatus="status">
	  					<tr>
	  						<td>${status.count}</td>
							
		  					<td>${item.sysmsg_id}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.sysmsg_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.sysmsg_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.account_id}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.sysmsg_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.sysmsg_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.sysmsg_title}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.sysmsg_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.sysmsg_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.introduction}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.sysmsg_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.sysmsg_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.sysmsg_content}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.sysmsg_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.sysmsg_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.in_date}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.sysmsg_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.sysmsg_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.is_read}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.sysmsg_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.sysmsg_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.skip_type}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.sysmsg_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.sysmsg_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.relation_id}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.sysmsg_id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.sysmsg_id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  						
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="goInfo('/admin/sysmsg/view','${item.sysmsg_id}');"/>
		  						<!-- 状态操作 -->
		  						<c:if test="${item.state==1}">
		  							<input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用" onclick="commonInfo('/admin/sysmsg/limitState','${item.sysmsg_id}','确定禁用该消息推送？');"/>
		  						</c:if>
								<c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用" onclick="commonInfo('/admin/sysmsg/enableState','${item.sysmsg_id}','确定启用该消息推送？');"/>
								</c:if>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/sysmsg/edit','${item.sysmsg_id}');"/>
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/sysmsg/delete','${item.sysmsg_id}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty sysmsgList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="batchDiv">
	  		<span class="batch_span"><input class="all" type="checkbox" />全选</span>
	  		<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/sysmsg/batchDelete','确定删除消息推送？');"/>
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/sysmsg/batchEnableState','确定启用消息推送？');"/>
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/sysmsg/batchLimitState','确定禁用消息推送？');"/>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

