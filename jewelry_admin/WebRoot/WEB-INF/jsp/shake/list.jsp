<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>摇一摇活动管理</title>
	  <script type="text/javascript">
          $(function () {
              // 时间设置

              $("#start_date_s").datetimepicker({
                  format:'Y-m-d',
                  language: 'zh',
                  minTime: true,
                  maxTime: true,
                  timepicker:false,
                  onSelectDate: function () {
                      var starttime=$("#start_date_s").val();
                      $("#end_date_s").datetimepicker({
                          minDate: starttime,
                          maxDate: false,
                      });
                  },
              });

              $("#end_date_s").datetimepicker({
                  format:'Y-m-d',
                  language: 'zh',
                  minTime: true,
                  maxTime: true,
                  timepicker:false,
                  onSelectDate: function () {
                      var endtime=$("#end_date_s").val();
                      $("#start_date_s").datetimepicker({
                          minDate: false,
                          maxDate: endtime,
                      });
                  },
              });
          });
	  </script>
  </head>
  <body>
  <form action="/admin/shake/list" method="post" >
	  <div class="list_oper_div">
	  		<input class="btn ol_btn" type="button" value="新增摇一摇活动" onclick="addInfo('/admin/shake/add');"/>

	  </div>
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
		  	  		<td>活动名称:</td>
		  	  		<td><input type="text" name="shake_name_vague_s" value="${shake_name_vague_s}"/></td>
		  	  		<td>状态:</td>
		  	  		<td>
		  	  			<select name="state_s" type="select" >
							<option value="">请选择</option>
							<option value="1" <c:if test="${state_s==1}"> selected</c:if>>启用</option>
							<option value="0" <c:if test="${state_s==0}"> selected</c:if>>禁用</option>
						</select>
		  	  		</td>
					<%--<td>活动日期:</td>--%>
					<%--<td>--%>
						<%--<input type="text" id="start_date_s" name="start_date_s" value="${start_date_s}"/>---%>
						<%--<input type="text" id="end_date_s" name="end_date_s" value="${end_date_s}"/>--%>
					</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/shake/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/shake/list');"/>
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
						<th width="5%">活动id</th>

		  				<th width="10%">活动名称</th>

					    <%--<th width="5%">预计活动参考人数</th>--%>
		  			
		  				<th width="10%">开始时间</th>
		  			
		  				<th width="10%">结束时间</th>

				    	<th width="5%">活动状态</th>

		  				<th width="5%">状态</th>

					<th width="8%">每人最大中奖次数</th>

					<th width="5%">每日抽奖次数</th>

					<th width="5%">中奖概率</th>
		  				
		  			    <th width="15%">操作</th>
	  			</tr>
	  			<c:if test="${!empty shakeList}">
	  				<c:forEach items="${shakeList}" var="item" varStatus="status">
	  					<tr>
							<td><input class="ids" type="checkbox" value="${item.shake_id}" /></td>
							<td>${item.shake_id}</td>
		  					<td>${item.shake_name}</td>

							<%--<td>${item.lottery_activity_num}</td>--%>

							<td>${item.start_time}</td>

							<td>${item.end_time}</td>

							<td><span style="color:<c:if test="${item.shakeState=='进行中'}">green</c:if><c:if test="${item.shakeState=='已过期'}">red</c:if>">${item.shakeState}</span></td>


							<td>
								<c:if test="${item.state==1}"><span style="color:green">启用</span></c:if>
								<c:if test="${item.state==0}"><span style="color:red">禁用</span></c:if>
							</td>

							<td>${item.everyone_draw_num}</td>

							<td>${item.draw_num_day}</td>

							<td>${item.probability_winning}%</td>

		  					<td>
								<input class="btn ol_colorbtn ol_bluebtn" type="button" value="中奖名单 " onclick="goInfo('/admin/shakewinningrecord/list','${item.shake_id}');"/>
								<input class="btn ol_colorbtn ol_bluebtn" type="button" value="奖项设置 " onclick="goInfo('/admin/shakeawards/list','${item.shake_id}');"/>
		  						<%--<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="goInfo('/admin/shake/view','${item.shake_id}');"/>--%>
		  						<!-- 状态操作 -->
		  						<c:if test="${item.state==1}">
		  							<input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用" onclick="commonInfo('/admin/shake/limitState','${item.shake_id}','确定禁用该摇一摇活动？');"/>
		  						</c:if>
								<c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用" onclick="commonInfo('/admin/shake/enableState','${item.shake_id}','确定启用该摇一摇活动？');"/>
								</c:if>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/shake/edit','${item.shake_id}');"/>
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/shake/delete','${item.shake_id}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty shakeList}"><td colspan="11">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="batchDiv">
	  		<span class="batch_span"><input class="all" type="checkbox" />全选</span>
	  		<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/shake/batchDelete','确定删除摇一摇活动？');"/>
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/shake/batchEnableState','确定启用摇一摇活动？');"/>
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/shake/batchLimitState','确定禁用摇一摇活动？');"/>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

