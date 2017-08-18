<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>优惠券统计</title>
</head>
<body>
<form id="validateForm" action="/admin/coupon/list" method="post">
    <div class="table_div">
        <table width="100%">
            <tr>
                <td class="td_left">优惠券名称:</td>
                <td class="td_right_two">
                    ${coupon.coupon_name}
                </td>
            </tr>
            <tr>
                <td class="td_left">优惠券类型:</td>
                <td class="td_right_two">
                    <c:if test="${coupon.coupon_type==1}"> 摇一摇优惠券</c:if>
                    <c:if test="${coupon.coupon_type==2}"> 系统推送优惠券</c:if>
                    <c:if test="${coupon.coupon_type==3}"> 普通优惠券</c:if>

                </td>
            </tr>

            <tr>
                <td class="td_left">发布时间:</td>
                <td class="td_right_two">
                    ${coupon.create_time}
                </td>
            </tr>
            <tr>
                <td class="td_left">截止时间:</td>
                <td class="td_right_two">
                    ${coupon.end_time}
                </td>
            </tr>
            <tr>
                <td class="td_left">优惠券面值:</td>
                <td class="td_right_two">
                    ${coupon.coupon_amount}元
                </td>
            </tr>
            <tr>
                <td class="td_left">使用门槛:</td>
                <td class="td_right_two">
                    ${coupon.use_amount}元
                </td>
            </tr>
            <tr>
                <td class="td_left">优惠券数量:</td>
                <td class="td_right_two">
                    <c:if test="${coupon.coupon_num==null}">0</c:if> ${coupon.coupon_num}张
                </td>
            </tr>
            <tr>
                <td class="td_left">每人限领数量:</td>
                <td class="td_right_two">
                    ${coupon.use_num}张
                </td>
            </tr>
            <tr>
                <td class="td_left">使用类型:</td>
                <td class="td_right_two">
                    <c:if test="${coupon.use_type!='2'}">全部商品</c:if>
                    <c:if test="${coupon.use_type=='2'}">指定商品</c:if>
                </td>
            </tr>
            <c:if test="${coupon.use_type=='2'}">
            <tr >
                <td></td>
                <td>
                    <table class="layui-table">
                        <thead>
                        <tr>
                            <th>宝贝标题</th>
                            <th>商家编码</th>
                            <th>价格(元)</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${!empty goodsList}">
                            <c:forEach items="${goodsList}" var="item" varStatus="status">
                                <tr>
                                    <td>${item.use_cat_name}</td>
                                    <td>${item.com_id}</td>
                                    <td>${item.fixed_price}</td>
                                </tr>

                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </td>
            </tr>
            </c:if>
            <tr>
                <td class="td_left">会员领取数量:</td>
                <td class="td_right_two">
                    ${account_get_num}张
                </td>
            </tr>
            <tr>
                <td class="td_left">会员使用数量:</td>
                <td class="td_right_two">
                    ${account_use_num}张
                </td>
            </tr>

        </table>
    </div>
    <%@ include file="/WEB-INF/common/search_hidden_field.jsp" %>
    <div class="row50 operbtndiv">
        <input type="button" class="btn return" onclick="returnGo('/admin/coupon/list')" value="返回列表"/>
    </div>
    </div>
</form>
</body>
</html> 

