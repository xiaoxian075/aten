<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>会员提现帐单管理</title>
</head>
<body>
<form id="form" action="/admin/accountwithdrawbill/list" method="post">
    <div class="searchDiv">
        <table class="searchTable">
            <tr>
                <td>会员账号:</td>
                <td><input type="text" name="login_name_vague_s"
                           value="${login_name_vague_s}"/></td>
                <td>状态:</td>
                <td><select name="audit_state_s" type="select">
                    <option value="">请选择</option>
                    <option value="0"
                            <c:if test="${audit_state_s==0}"> selected</c:if>>审批中
                    </option>
                    <option value="1" <c:if test="${audit_state_s==1}"> selected</c:if>>审批通过</option>
                    <option value="2" <c:if test="${audit_state_s==2}"> selected</c:if>>审批未通过</option>
                </select>
                </td>

                <td class="td_left">提现时间:</td>
                <td class="td_right_two">
                    <input class="text w130" type="text"
                           id="start_time" name="start_time_s" maxlength='20'
                           maxdatalength='20' value="${start_time_s}"/> -
                    <input
                            class="text w130" type="text" id="end_time"
                            name="end_time_s" maxlength='20' maxdatalength='20'
                            value="${end_time_s}"/>
                </td>
                <td>
                    <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询"
                           onclick="goInfo('/admin/accountwithdrawbill/list');"/>
                    <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
                           onclick="clearSearch('/admin/accountwithdrawbill/list');"/>
                    <input class="btn ol_colorbtn ol_bluebtn" type="button" value="导出"
                           onclick="goInfo('/admin/accountwithdrawbill/export');"/>
                    <%--<input class="btn ol_colorbtn ol_greenbtn" type="button" value="统计" onclick="setTotalAmount();"/>--%>
                </td>
            </tr>
        </table>
    </div>

    <div class="show_line" style="margin:10px 0px 10px 15px;font-size:14px;">
        <b>提现金额:</b><span id="totalAmount">${totalAmount}元</span>
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


                <th width="10%">会员账号</th>

                <th width="10%">提现金额</th>

                <th width="10%">提现时间</th>

                <th width="10%">提现备注</th>

                <th width="10%">审批状态</th>

                <th width="5%">操作</th>
            </tr>
            <c:if test="${!empty accountWithdrawBillList}">
                <c:forEach items="${accountWithdrawBillList}" var="item" varStatus="status">
                    <tr>


                        <td>
                            <input class="ids" type="checkbox" value="${item.id}"/>
                        </td>

                        <td>${item.login_name}</td>
                        <td>${item.amount}</td>

                        <td>${item.create_time}</td>
                        <td>${item.audit_note}</td>

                        <td>
                            <c:if test="${item.audit_state==0}">审核中</c:if>
                            <c:if test="${item.audit_state==1}"><span class="span_green">审批通过</span></c:if>
                            <c:if test="${item.audit_state==2}"><span class="span_red">审批未通过</span></c:if>
                        </td>


                        <td>
                            <c:if test="${item.audit_state!=0}">
                                <input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看"
                                       onclick="goInfo('/admin/accountwithdrawbill/view','${item.id}');"/>
                            </c:if>
                            <c:if test="${item.audit_state==0}">
                                <input class="btn ol_colorbtn ol_bluebtn" type="button" value="审批"
                                       onclick="editInfo('/admin/accountwithdrawbill/edit','${item.id}');"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty accountWithdrawBillList}">
                <td colspan="7">暂无数据</td>
            </c:if>
        </table>
    </div>
    <%--<div class="batchDiv">--%>
    <%--<span class="batch_span"><input class="all" type="checkbox" />全选</span>--%>
    <%--<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/accountwithdrawbill/batchDelete','确定删除会员提现帐单？');"/>--%>
    <%--<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/accountwithdrawbill/batchEnableState','确定启用会员提现帐单？');"/>--%>
    <%--<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/accountwithdrawbill/batchLimitState','确定禁用会员提现帐单？');"/>--%>
    <%--</div>--%>
    <div class="page_contain">
        <%@ include file="/WEB-INF/common/pagelist.jsp" %>
    </div>
    <%@ include file="/WEB-INF/common/list_hidden_value.jsp" %>
</form>
<script type="text/javascript">
    $("#start_time").datetimepicker({
        format:'Y-m-d',
        language: 'zh',
        minTime: true,
        maxTime: true,
        timepicker:false,
        onSelectDate: function () {
            var starttime=$("#start_time").val();
            $("#end_time").datetimepicker({
                minDate: starttime,
                maxDate: false,
            });
        },
    });

    $("#end_time").datetimepicker({
        format:'Y-m-d',
        language: 'zh',
        minTime: true,
        maxTime: true,
        timepicker:false,
        onSelectDate: function () {
            var endtime=$("#end_time").val();
            $("#start_time").datetimepicker({
                minDate: false,
                maxDate: endtime,
            });
        },
    });
</script>
</body>
</html>

