<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>账单详情页</title>
    <script type="text/javascript">
        $(function () {
            // 时间设置
            $("#start_date_s").datetimepicker({
					format:'Y-m-d',
					language: 'zh',
					minTime: true,
					maxTime: true,
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

<form action="/admin/billflow/list" method="post">
    <div class="list_oper_div">
        <input class="btn return" type="button" value="返回账单列表"
               onclick="goInfo('/admin/manabills/list');" />
    </div>
    <div class="searchDiv">
        <table class="searchTable">
            <tr>
                <input type="hidden" id="parameter_id" name="parameter_id" value="${parameter_id}"/>
                <td>交易流水号:</td>
                <td><input type="text" name="trade_id_vague_s" value="${trade_id_vague_s}"/></td>
                <td>支付方式:</td>
                <td>
                    <select name="pay_way_s" type="select">
                        <option value="">请选择</option>
                        <option value="1" <c:if test="${pay_way_s==1}"> selected</c:if>>余额支付</option>
                        <option value="2" <c:if test="${pay_way_s==2}"> selected</c:if>>云付通支付</option>
                    </select>
                </td>
                <td>收益类型:</td>
                <td>
                    <select name="bill_type_s" type="select">
                        <option value="">请选择</option>
                        <option value="1" <c:if test="${bill_type_s==1}"> selected</c:if>>收入</option>
                        <option value="0" <c:if test="${bill_type_s==0}"> selected</c:if>>支出</option>
                    </select>
                </td>
                <td>账单日期:</td>
                <td>
                    <input type="text" id="start_date_s" name="start_date_s" value="${start_date_s}"/> -
                    <input type="text" id="end_date_s" name="end_date_s" value="${end_date_s}"/>
                </td>
                <td>
                    <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询"
                           onclick="searchInfo('/admin/billflow/list');"/>
                    <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
                           onclick="clearSearch('/admin/billflow/list');"/>
                    <input class="btn ol_colorbtn ol_bluebtn" type="button" value="导出"
                           onclick="goInfo('/admin/billflow/export');"/>
                </td>
            </tr>
        </table>
    </div>
    <div class="show_line" style="margin:10px 0px 10px 15px;font-size:14px;">
      	<b >总金额:</b><span style="color: red">${total_assets}</span>元 &nbsp;&nbsp;&nbsp;
      	<b>可用金额:</b><span style="color: red">${balance}</span>元 &nbsp;&nbsp;&nbsp;
      	<b>冻结金额:</b><span style="color: red">${frozen_amount}</span>元
    </div>
    <div class="show_line">
        <%@ include file="/WEB-INF/common/pageshowrow.jsp" %>
    </div>
    <div class="list_div">
        <table id="list_table" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th width="10%">交易流水号</th>

                <th width="10%">支付方式</th>

                <th width="10%">交易金额(元)</th>

                <th width="10%">账单时间</th>

                <th width="10%">订单类型</th>

                <th width="10%">收益类型</th>

                <th width="10%">订单号</th>

                <th width="10%">备注</th>
            </tr>
            <c:if test="${!empty billFlowList}">
                <c:forEach items="${billFlowList}" var="item" varStatus="status">
                    <tr>
                        <td>${item.trade_id}</td>
                        <td>
                            <c:if test="${item.pay_way==1}">余额支付</c:if>
                            <c:if test="${item.pay_way==2}">云付通支付</c:if>
                        </td>
                        <td>${item.amount}</td>
                        <td>${item.bill_time_str}</td>
                        <td>
                            ${item.order_type_name}
                        </td>
                        <td>
                            <c:if test="${item.bill_type==1}">收入</c:if>
                            <c:if test="${item.bill_type==0}">支出</c:if>
                        </td>
                        <td>${item.ralation_id}</td>
                        <td>${item.note}</td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty billFlowList}">
                <td colspan="8">暂无数据</td>
            </c:if>
        </table>
    </div>

    <div class="page_contain">
        <%@ include file="/WEB-INF/common/pagelist.jsp" %>
    </div>
    <%@ include file="/WEB-INF/common/list_hidden_value.jsp" %>
</form>
</body>
</html>

