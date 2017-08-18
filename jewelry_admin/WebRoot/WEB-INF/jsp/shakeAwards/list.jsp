<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>摇一摇优惠卷奖项设置管理</title>
  </head>
  <body>
  <form action="/admin/shakeawards/list" method="post" >
	  <input type="hidden" name="awards_id"  id="awards_id" value="${awards_id}">
      <input type="hidden" name="parameter_id"  value="${parameter_id}">
      <input type="hidden" name="shake_id"  value="${parameter_id}">
	  <div class="list_oper_div">
	  		<input class="btn ol_btn" type="button" value="新增奖项设置" onclick="addInfo('/admin/shakeawards/add',${parameter_id});"/>
          <input class="btn return" type="button" value="返回摇一摇活动列表"
                 onclick="goInfo('/admin/shake/list');" />
		  <input class="btn ol_btn" type="button" value="刷新活动概率" onclick="addInfo('/admin/shakeawards/refresh',${parameter_id});"/>
	  </div>
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
		  	  		<td>奖项名称:</td>
		  	  		<td><input type="text" name="awards_name_vague_s" value="${awards_name_vague_s}"/></td>
		  	  		<td>奖品类型:</td>
		  	  		<td>
		  	  			<select name="prize_type_s" type="select" >
							<option value="">请选择</option>
							<option value="1" <c:if test="${prize_type_s==1}"> selected</c:if>>优惠券</option>
							<option value="0" <c:if test="${prize_type_s==0}"> selected</c:if>>积分</option>
						</select>
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/shakeawards/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/shakeawards/list');"/>
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

		  				<th width="10%">奖项名称</th>

					    <th width="10%">所属活动</th>

		  				<th width="10%">奖品类型</th>


		  				<th width="10%">奖项级别名称</th>

		  				<th width="10%">中奖概率</th>

		  				<th width="10%">奖品数量</th>

						<th width="10%">已领取奖品数量</th>

		  			<th width="20%">操作</th>
	  			</tr>
	  			<c:if test="${!empty shakeAwardsList}">
	  				<c:forEach items="${shakeAwardsList}" var="item" varStatus="status">
	  					<tr>

		  					<td>${item.awards_name}</td>
							<td>${item.shake_name}</td>
		  					<td>
                                <c:if test="${item.prize_type==0}">积分</c:if>
                                <c:if test="${item.prize_type==1}">优惠券</c:if>
                            </td>
		  					<td>${item.awards_level_name}</td>
		  					<td>${item.awards_probability}</td>
		  					<td>${item.awards_num}</td>
							<td>${item.get_num}</td>
		  					<td>
								<input class="btn ol_colorbtn ol_bluebtn" type="button" value="中奖名单 " onclick="goInfo('/admin/shakeawards/winList','awards_id',${item.awards_id});"/>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="goInfo('/admin/shakeawards/view/${item.awards_id}');"/>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/shakeawards/edit/${item.awards_id}');"/>
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/shakeawards/delete/${item.awards_id}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty shakeAwardsList}"><td colspan="8">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <%--<div class="batchDiv">--%>
	  		<%--<span class="batch_span"><input class="all" type="checkbox" />全选</span>--%>
	  		<%--<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/shakeawards/batchDelete','确定删除摇一摇优惠卷奖项设置？');"/>--%>
	  <%--</div>--%>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>
  </body>
 </html>

