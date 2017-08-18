<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>测试管理</title>
</head>
<body>
	<form action="/admin/accountcoupon/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增测试"
				onclick="addInfo('/admin/accountcoupon/add');" /> <input
				class="btn ol_btn" type="button" value="排序"
				onclick="sortInfo('/admin/accountcoupon/sort');" />
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
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/accountcoupon/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/accountcoupon/list');"/>
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
	  				
		  				<th width="10%">主键</th>
		  			
		  				<th width="10%">创建时间</th>
		  			
		  				<th width="10%">帐户id</th>
		  			
		  				<th width="10%">优惠券id</th>
		  			
		  				<th width="10%">优惠券名称</th>
		  			
		  				<th width="10%">优惠券面值</th>
		  			
		  				<th width="10%">使用门槛</th>
		  			
		  				<th width="10%">使用类型 1 全部商品, 2 指定品类 </th>
		  			
		  				<th width="10%">截止有效期</th>
		  			
		  				<th width="10%">0 未使用 1 已使用 2 已过期</th>
		  				
		  			<th width="5%">操作</th>
	  			</tr>
	  			<c:if test="${!empty accountCouponList}">
	  				<c:forEach items="${accountCouponList}" var="item" varStatus="status">
	  					<tr>
	  						<td>${status.count}</td>
							
		  					<td>${item.id}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.create_time}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.account_id}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.coupon_id}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.coupon_name}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.coupon_amount}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.use_amount}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.use_type}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.end_time}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  					<td>${item.state}</td>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.id}"/>
		  					</td>
							
		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.id}"/>
	  							<input class="sort_val" type="text" value="0" maxlength="6" />
		  					</td>
		  					
		  					
		  						
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="goInfo('/admin/accountcoupon/view','${item.id}');"/>
		  						<!-- 状态操作 -->
		  						<c:if test="${item.state==1}">
		  							<input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用" onclick="commonInfo('/admin/accountcoupon/limitState','${item.id}','确定禁用该测试？');"/>
		  						</c:if>
								<c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用" onclick="commonInfo('/admin/accountcoupon/enableState','${item.id}','确定启用该测试？');"/>
								</c:if>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/accountcoupon/edit','${item.id}');"/>
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/accountcoupon/delete','${item.id}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty accountCouponList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="batchDiv">
	  		<span class="batch_span"><input class="all" type="checkbox" />全选</span>
	  		<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/accountcoupon/batchDelete','确定删除测试？');"/>
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/accountcoupon/batchEnableState','确定启用测试？');"/>
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/accountcoupon/batchLimitState','确定禁用测试？');"/>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

