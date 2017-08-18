<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/5
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>商品分类详情</title>
</head>
<body>

<form id="validateForm" action="/admin/goodsclass/list" method="post">
    <script type="text/javascript">
        $(document).ready(function () {
            //分类级联
            $("#cat_id_div").cascadeSel({
                url: "/admin/cat/normalList",
                name: "cat_id",
                init_id: "${cfg_goods_top}"
            });
            var cat_id = $("[name='cat_id']").val();
            if (cat_id) {
                $("#cat_id_div").find("select").attr("disabled", "disabled");
            }
        });
    </script>
    <div class="table_div">
        <input type="hidden" . name="cat_id" value="${cat.cat_id}"/>
        <table width="100%">
            <tr>
                <td class="td_left">商品分类名称:</td>
                <td class="td_right_two">
                    ${cat.cat_name}
                </td>
            </tr>
            <tr>
                <td class="td_left">排序:</td>
                <td class="td_right_two">
                    ${cat.sort_no}
                </td>
            </tr>

            <tr>
                <td class="td_left">所属分类:</td>
                <td class="td_right_two">
                    ${cat.parent_cat_id}
                </td>
            </tr>
            <tr>
                <td class="td_left">是否启用:</td>
                <td class="td_right_two">
                    <c:if test="${cat.state==1}"><span class="span_green">启用</span></c:if>
                    <c:if test="${cat.state==0}"><span class="span_red">禁用</span></c:if>

                </td>
            </tr>
            <tr>
                <td class="td_left">关联品牌:</td>
                <td class="td_right_two">
                    <lable id="brands">
                        <c:if test="${!empty catBrandList}">
                            <c:forEach items="${catBrandList}" var="item" varStatus="status">
                                <c:if test="${status.index!=0}">,</c:if> ${item.brand_name}
                            </c:forEach>
                        </c:if>

                    </lable>
                    <c:if test="${empty catBrandList}">-</c:if>
                </td>
            </tr>

            <tr>
                <td class="td_left">关联规格:</td>
                <td class="td_right_two">
                    <c:if test="${empty catSkuAttrList}">-</c:if>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div class="layui-form ">
                        <c:if test="${!empty catSkuAttrList}">
                            <table class="layui-table">
                                <thead>
                                <tr>
                                    <th>商品规格</th>
                                    <th>取值方式</th>
                                    <th>是否为筛选属性</th>
                                    <th>是否为必填属性</th>
                                    <th>是否支持自定义</th>
                                    <th>手工费(元)</th>
                                </tr>
                                </thead>
                                <tbody id="skuAttrs">

                                <c:forEach items="${catSkuAttrList}" var="item" varStatus="status">
                                    <tr>
                                        <td>${item.attr_name}</td>
                                        <td><span><c:if test="${item.option_type==1}">单选</c:if><c:if
                                                test="${item.option_type==2}">多选</c:if><c:if
                                                test="${item.option_type==0}">文本框</c:if></span>
                                        </td>
                                        <td>
                                        <span><c:if test="${item.is_index==1}"><span style="color:green">是</span></c:if><c:if
                                                test="${item.is_index==0}"><span
                                                style="color:red">否</span></c:if>
                                        </span></td>
                                        <td>
                                        <span><c:if test="${item.is_must==1}"><span
                                                style="color:green">是</span></c:if><c:if test="${item.is_must==0}"><span
                                                style="color:red">否</span></c:if></span>
                                        </td>
                                        <td>
                                        <span><c:if test="${item.is_custom_value==1}"><span
                                                style="color:green">是</span></c:if><c:if test="${item.is_custom_value==0}">
                                            <span style="color:red">否</span></span></c:if>
                                        </td>
                                        <td>
                                                ${item.manual_fee}
                                        </td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_left">关联属性:</td>
                <td class="td_right_two">
                    <c:if test="${empty catKeyAttrList}">-</c:if>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div class="layui-form ">
                        <c:if test="${!empty catKeyAttrList}">
                            <table class="layui-table">
                                <thead>
                                <tr>
                                    <th>商品规格</th>
                                    <th>取值方式</th>
                                    <th>是否为筛选属性</th>
                                    <th>是否为必填属性</th>
                                    <th>是否支持自定义</th>
                                    <th>手工费(元)</th>
                                </tr>
                                </thead>
                                <tbody id="keyAttrs">

                                <c:forEach items="${catKeyAttrList}" var="item" varStatus="status">
                                    <tr>
                                        <td>${item.attr_name}</td>
                                        <td>
                                        <span><c:if test="${item.option_type==1}">单选</c:if><c:if
                                                test="${item.option_type==2}">多选</c:if><c:if
                                                test="${item.option_type==0}">文本框</c:if></span></td>
                                        <td>
                                        <span><c:if test="${item.is_index==1}"><span style="color:green">是</span></c:if><c:if
                                                test="${item.is_index==0}"><span
                                                style="color:red">否</span></c:if></span></td>
                                        <td>
                                        <span><c:if test="${item.is_must==1}"><span
                                                style="color:green">是</span></c:if><c:if test="${item.is_must==0}"><span
                                                style="color:red">否</span></c:if></span></td>
                                        <td>
                                        <span><c:if test="${item.is_custom_value==1}"><span
                                                style="color:green">是</span></c:if><c:if test="${item.is_custom_value==0}">
                                            <span style="color:red">否</span></span></c:if></td>
                                        <td>
                                                ${item.manual_fee}
                                        </td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_left">推荐分成收益比例:</td>
                <td class="td_right_two">
                    ${catRate.divide_rate}%
                </td>
            </tr>
            <tr>
                <td class="td_left">手工费:</td>
                <td class="td_right_two">
                    ${catRate.manual_fee}元
                </td>
            </tr>
            <tr>
                <td class="td_left">关联供应商:</td>
                <td class="td_right_two">
                    <c:if test="${!empty catSupplyList}">
                        <c:forEach items="${catSupplyList}" var="item" varStatus="status">
                            <c:if test="${status.index!=0}">,</c:if> ${item.supply_name}
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty catSupplyList}">-</c:if>
                </td>
            </tr>
            <tr>
                <td class="td_left">关联鉴定机构:</td>
                <td class="td_right_two">
                    <c:if test="${!empty catAppraisalList}">
                        <c:forEach items="${catAppraisalList}" var="item" varStatus="status">
                            <c:if test="${status.index!=0}">,</c:if> ${item.appraisal_name}
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty catAppraisalList}">-</c:if>
                </td>
            </tr>
            <%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
        </table>
    </div>
    <div class="row50 operbtndiv">
        <input type="button" class="btn return" onclick="returnGo('/admin/goodsclass/list')" value="返回列表"/>
    </div>
</form>
</body>
</html>
