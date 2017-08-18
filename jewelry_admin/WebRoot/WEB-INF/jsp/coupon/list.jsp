<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>优惠券列表</title>
</head>
<body>
	<form action="/admin/coupon/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增优惠券"
				onclick="addInfo('/admin/coupon/add');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>优惠券名称:</td>
					<td><input type="text" name="coupon_name_vague_s"
						value="${coupon_name_vague_s}" /></td>
					<td>优惠券类型:</td>
					<td><select name="coupon_type_s" type="select">
							<option value="">请选择</option>
							<option value="1"
								<c:if test="${coupon_type_s==1}"> selected</c:if>>摇一摇优惠券</option>
							<%--<option value="2" <c:if test="${coupon_type_s==2}"> selected</c:if>>系统推送优惠券</option>--%>
							<option value="3" <c:if test="${coupon_type_s==3}"> selected</c:if>>普通优惠券</option>

						</select>
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/coupon/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/coupon/list');"/>
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

				     	<th width="10%">优惠券名称</th>
		  			
		  				<th width="10%">类型</th>

					    <th width="10%">使用门槛(元)</th>

					    <th width="10%">面值(元)</th>

					    <th width="10%">数量</th>

					    <th width="10%">发布时间</th>

					    <th width="10%">截止有效时间</th>

				    	<th width="10%">状态</th>
		  			    <th width="10%">操作</th>
	  			</tr>
	  			<c:if test="${!empty couponList}">
	  				<c:forEach items="${couponList}" var="item" varStatus="status">
	  					<tr>
		  					<td>
		  						<input class="ids" type="checkbox" value="${item.coupon_id}"/>
		  					</td>

							<td>${item.coupon_name}</td>
							
		  				    <td>
								<c:if test="${item.coupon_type==1}"> 摇一摇优惠券</c:if>
								<c:if test="${item.coupon_type==2}"> 系统推送优惠券</c:if>
								<c:if test="${item.coupon_type==3}"> 普通优惠券</c:if>
							</td>

							<td>${item.use_amount}</td>
							<td>${item.coupon_amount}</td>
							<td>${item.coupon_num}</td>
							<td>${item.create_time}</td>
							<td>${item.end_time}</td>
							<!-- 状态操作 -->
							<td>
							<c:if test="${item.state==1}">
								<span class="span_green">正常</span>
							</c:if>
								<c:if test="${item.state==0}">
									<span class="span_red">禁用</span>
								</c:if>
							<c:if test="${item.state==2}">
						     	<span class="span_red">  已过期</span>
							</c:if>
							</td>
		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="goInfo('/admin/coupon/view','${item.coupon_id}');"/>
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/coupon/delete','${item.coupon_id}');"/>
								<c:if test="${item.state==1}">
									<input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用" onclick="commonInfo('/admin/coupon/limitState','${item.coupon_id}','确定禁用${item.coupon_name}?');"/>
								</c:if>
								<c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用" onclick="commonInfo('/admin/coupon/enableState','${item.coupon_id}','确定启用${item.coupon_name}?');"/>
								</c:if>
							</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty couponList}"><td colspan="10">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="batchDiv">
		  <span class="batch_span"><input class="all" type="checkbox" />全选</span>
		  <input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/coupon/delete','确定批量删除优惠券?');"/>
		  <input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/coupon/batchEnableState','确定批量启用优惠券?');"/>
		  <input class="btn ol_colorbtn ol_redbtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/coupon/batchLimitState','确定批量禁用优惠券?');"/>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

