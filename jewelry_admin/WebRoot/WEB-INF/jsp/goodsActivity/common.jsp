<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
    function checkValue(url) {
        //验证类型与折扣
        var activity_type = $("[name='activity_type']").val();
        var discount = $("[name='discount']").val();
        if (activity_type == "") {
            layer.msg('请先选择活动类型', {icon: 5});
            //layer.alert("请先选择活动类型");
            return;
        }
        //如果是限时特价
        if (activity_type == 1) {
            //var reg = /^\d{1,10}$/ ;
            var reg = /^(\d(\.\d)?|10)$/;
            if (!reg.test(discount)) {
                layer.msg('折扣数值必须为0~10,精确到0.1', {icon: 5});
                //layer.alert("折扣数值必须为0~10,精确到0.1");
                return;
            }
            if (discount == "") {
                layer.msg('请先填写折扣', {icon: 5});
                //layer.alert("请先填写折扣");
                return;
            }
        }
        //如果是黄金特惠
        if (activity_type == 2) {
            //var reg = /^\d{1,10}$/ ;
            var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
            if (!reg.test(discount)) {
                layer.msg('请输入正确的金额', {icon: 5});
                //layer.alert("请输入正确的金额");
                return;
            }
            if (discount == "") {
                layer.msg('请先填写优惠金额', {icon: 5});
                //layer.alert("请先填写优惠金额");
                return;
            }
        }
        //如果是免手工费
        if (activity_type == 3) {
            discount = "20";
        }
        openIframe(url, activity_type, discount);
    }
    function openIframe(url, activity_type, discount) {
        var activity_id = $("#activity_id").val() || "null";
        parent.layer.open({
            type: 2,
            title: '添加',
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['800px', '520px'],
            content: url + "/" + activity_type + "/" + discount + "/" + activity_id
        });

    }
    $(function () {
        $("[name='activity_type']").change(function () {
            if ($(this).val() == 1) {
                $("#discount").show()
                $("#discountName").html("折扣(折)");
                $("#_discount").val("");
            }
            if ($(this).val() == 2) {
                $("#discount").show();
                $("#discountName").html("优惠金额(元)");
                $("#_discount").val("");
            }
            if ($(this).val() == 3) {
                $("#discount").hide();
                $("#_discount").val("-");
            }
        });
    });
    $(function () {
        // 时间设置
        $('#start_time,#end_time').datetimepicker({
            lang: 'ch',
            timepicker: true,
            format: 'Y-m-d H:i',
            formatDate: 'Y-m-d H:i',
        });
    });
    $(document).ready(function () {
        var value = $("#_discount").val();
        if (value == "") {
            $("#_discount").val("-");
        }
        var type = $("[name='activity_type']").val();
        if (type == 1) {
            $("#discount").show()
            $("#discountName").html("折扣(折)");
        }
        if (type == 2) {
            $("#discount").show();
            $("#discountName").html("优惠金额(元)");
        }
        if (type == 3) {
            $("#_discount").val("-");
            $("#discount").hide();
        }
    });
</script>
<div class="table_div">
    <table width="100%">
        <tr>
            <td class="td_left">活动名称<span style="color:red">*</span></td>
            <td class="td_right_two"><input class="text validate"
                                            type="text" name="activity_name" isrequired='yes' tipmsg="活动名称"
                                            <c:if test="${isEdit=='true'}">disabled="disabled"</c:if>
                                            maxlength='100' maxdatalength='100'
                                            value="${goodsActivity.activity_name}"/></td>
        </tr>
        <tr>
            <td class="td_left">活动类型:<span style="color:red">*</span></td>
            <td class="td_right_two"><select name="activity_type"
                                             class="validate" isrequired='yes' type="select" tipmsg="活动类型"
                                             <c:if test="${isEdit=='true'}">disabled="disabled"</c:if>
                                             widthtip="100">
                <option value="">请选择</option>
                <option value="1"<c:if test="${goodsActivity.activity_type==1}"> selected</c:if>>限时折扣</option>
                <option value="2" <c:if test="${goodsActivity.activity_type==2}"> selected</c:if>>限时减价</option>
                <option value="3" <c:if test="${goodsActivity.activity_type==3}"> selected</c:if>>免手工费</option>
            </select>
            </td>
        </tr>
        <tr id="discount" style="display: none">
            <td class="td_left"><span id="discountName">折扣(折)</span><span style="color:red">*</span></td>
            <td class="td_right_two">
                <input class="text validate" type="text" name="discount" tipmsg="折扣" id="_discount" isrequired='yes'
                       <c:if test="${isEdit=='true'}">disabled="disabled"</c:if>
                       maxlength='10' maxdatalength='10' value="${goodsActivity.discount}"/>
                <span style="color:red" class="error-msg"></span>
            </td>
        </tr>
        <%--<tr>--%>
        <%--<td class="td_left">活动状态 0：未开始 1.进行中  2.已结束<span class="sp_span">:</span></td>--%>
        <%--<td class="td_right_two">--%>
        <%--<input  class="text " type="text" name="activity_state"    tipmsg="活动状态 0：未开始 1.进行中  2.已结束" --%>
        <%--maxlength='11'  maxdatalength='11'    value="${goodsActivity.activity_state}"/>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <tr>
            <td class="td_left">活动开始时间<span style="color:red">*</span></td>
            <td class="td_right_two">
                <input class="text validate" type="text" name="start_time" tipmsg="活动开始时间" id="start_time"
                       isrequired='yes'
                       maxlength='20000' maxdatalength='20000' value="${goodsActivity.start_time}"/>
            </td>
        </tr>
        <tr>
            <td class="td_left">活动结束时间<span style="color:red">*</span></td>
            <td class="td_right_two">
                <input class="text validate" type="text" name="end_time" tipmsg="活动结束时间" id="end_time" isrequired='yes'
                       maxlength='20000' maxdatalength='20000' value="${goodsActivity.end_time}"/>
            </td>
        </tr>
        <tr>
            <td class="td_left">活动说明:</td>
            <td class="td_right_two">
                <textarea name="introduce" rows="8" cols="50">${goodsActivity.introduce}</textarea>
            </td>
        </tr>
        <%--<tr>--%>
        <%--<td class="td_left">活动图片<span class="sp_span">:</span></td>--%>
        <%--<td class="td_right_two">--%>
        <%--<input  class="text " type="text" name="activity_img"    tipmsg="活动图片" --%>
        <%--maxlength='60'  maxdatalength='60'    value="${goodsActivity.activity_img}"/>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <tr>
            <td class="td_left">
                关联商品
            </td>
            <td class="td_right_two">
                <input type="button" value="选择商品" class="btn operbtn"
                       onclick="checkValue('/iframe/goodsActivityList')"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <div class="layui-form ">
                    <table class="layui-table">
                        <thead>
                        <tr>
                            <th>商品名称</th>
                            <th>商品价格</th>
                            <th>活动价格</th>
                        </tr>
                        </thead>
                        <tbody id="goods">
                        <c:if test="${!empty goodsList}">
                            <c:forEach items="${goodsList}" var="item" varStatus="status">
                                <tr><input type="hidden" name="goodsIds" value="${item.goods_id}">
                                    <td>
                                    <span class="goods_checked" checked_id="${item.goods_id}"
                                          checked_fixed_price="${item.fixed_price}"
                                          checked_price="${item.price}">${item.goods_name}</span></td>
                                    <td>${item.price}</td>
                                    <td>${item.activity_price}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
        <%--<tr>--%>
        <%--<td></td>--%>
        <%--<td>--%>
        <%--<lable id="goods">--%>
        <%--<c:if test="${!empty goodsList}">--%>
        <%--<c:forEach items="${goodsList}" var="item" varStatus="status">--%>
        <%--<span style="color:red">*</span><span class="goods_checked" checked_id="${item.goods_id}">--%>
        <%--${item.goods_name}--%>
        <%--</span><br>--%>
        <%--<input type="hidden" name="goodsIds" value="${item.goods_id}">--%>
        <%--</c:forEach>--%>
        <%--</c:if>--%>

        <%--</lable>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <c:if test="${goodsActivity.activity_id!=null}">
            <input type="hidden" id="parameter_id" name="parameter_id" value="${goodsActivity.activity_id}"/>
            <input type="hidden" id="activity_id" name="activity_id" value="${goodsActivity.activity_id}"/>
        </c:if>

        <%@ include file="/WEB-INF/common/search_hidden_field.jsp" %>
    </table>
</div>

