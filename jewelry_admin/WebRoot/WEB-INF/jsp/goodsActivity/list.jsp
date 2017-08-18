<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>商品活动管理</title>
</head>
<body>
	<form action="/admin/goodsactivity/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增商品活动"
				onclick="addInfo('/admin/goodsactivity/add');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>活动名称:</td>
					<td><input type="text" name="activity_name_vague_s"
						value="${activity_name_vague_s}" /></td>
					<td>活动类型:</td>
					<td><select name="activity_type_s" type="select">
							<option value="">请选择</option>
							<option value="1"
								<c:if test="${activity_type_s==1}"> selected</c:if>>限时折扣</option>
                        <option value="2" <c:if test="${activity_type_s==2}"> selected</c:if>>限时减价</option>
                        <option value="3" <c:if test="${activity_type_s==3}"> selected</c:if>>免手工费</option>
                    </select>
                </td>
                <td>活动状态:</td>
                <td>
                    <select name="state_s" type="select">
                        <option value="">请选择</option>
                        <option value="0" <c:if test="${state_s==0}"> selected</c:if>>未开始</option>
                        <option value="1" <c:if test="${state_s==1}"> selected</c:if>>进行中</option>
                        <option value="2" <c:if test="${state_s==2}"> selected</c:if>>已结束</option>
                        <%--<option value="3" <c:if test="${activity_state_s==3}"> selected</c:if>>已过期</option>--%>
                    </select>
                </td>
                <td>
                    <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询"
                           onclick="goInfo('/admin/goodsactivity/list');"/>
                    <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
                           onclick="clearSearch('/admin/goodsactivity/list');"/>
                </td>
            </tr>
        </table>
    </div>
    <div class="show_line">
        <%@ include file="/WEB-INF/common/pageshowrow.jsp" %>
    </div>
    <div class="list_div">
        <table id="list_table" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th width="3%">
                    <input class="all" type="checkbox"/>
                </th>
                <th width="10%">活动名称</th>

                <th width="5%">活动类型</th>

                <th width="5%">活动状态</th>

                <th width="5%">状态</th>

                <th width="10%">活动开始时间</th>

                <th width="10%">活动结束时间</th>

                <th width="10%">操作</th>
            </tr>
            <c:if test="${!empty goodsActivityList}">
                <c:forEach items="${goodsActivityList}" var="item" varStatus="status">
                    <tr>

                        <td>
                            <input class="ids" type="checkbox" value="${item.activity_id}"/>
                        </td>
                        <td>${item.activity_name}</td>

                        <td>
                            <c:if test="${item.activity_type==1}">限时折扣</c:if>
                            <c:if test="${item.activity_type==2}">限时减价</c:if>
                            <c:if test="${item.activity_type==3}">免手工费</c:if>
                        </td>

                        <td>
                            <c:if test="${item.state==0}">未开始</c:if>
                            <c:if test="${item.state==1}"><span style="color:green">进行中</span></c:if>
                            <%--<c:if test="${item.state==2}"><span style="color:red">已结束</span></c:if>--%>
                            <c:if test="${item.state==2}"><span style="color:red">已过期</span></c:if>
                        </td>

                        <td>
                            <c:if test="${item.activity_state==0}"><span style="color:red">禁用</span></c:if>
                            <c:if test="${item.activity_state==1}"><span style="color:green">启用</span></c:if>
                        </td>

                        <td>${item.start_time}</td>

                        <td>${item.end_time}</td>

                        <td>
                            <!-- 状态操作 -->
                            <c:if test="${item.activity_state==1}">
                                <input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用"
                                       onclick="commonInfo('/admin/goodsactivity/limitState','${item.activity_id}','确定结束该商品活动？');"/>
                            </c:if>
                            <c:if test="${item.activity_state==0}">
                                <input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用"
                                       onclick="commonInfo('/admin/goodsactivity/enableState','${item.activity_id}','确定开始该商品活动？');"/>
                            </c:if>
                            <input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改"
                                   onclick="editInfo('/admin/goodsactivity/edit','${item.activity_id}');"/>
                            <input class="btn ol_colorbtn ol_redbtn" type="button" value="删除"
                                   onclick="delInfo('/admin/goodsactivity/delete','${item.activity_id}');"/>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty goodsActivityList}">
                <td colspan="8">暂无数据</td>
            </c:if>
        </table>
    </div>
    <div class="batchDiv">
        <span class="batch_span"><input class="all" type="checkbox"/>全选</span>
        <input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
               onclick="commonBatchInfo('/admin/goodsactivity/batchDelete','确定删除商品活动？');"/>
        <%--<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用"--%>
        <%--onclick="commonBatchInfo('/admin/goodsactivity/batchEnableState','确定启用商品活动？');"/>--%>
        <%--<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用"--%>
        <%--onclick="commonBatchInfo('/admin/goodsactivity/batchLimitState','确定禁用商品活动？');"/>--%>
    </div>
    <div class="page_contain">
        <%@ include file="/WEB-INF/common/pagelist.jsp" %>
    </div>
    <%@ include file="/WEB-INF/common/list_hidden_value.jsp" %>
</form>
</body>
</html>

