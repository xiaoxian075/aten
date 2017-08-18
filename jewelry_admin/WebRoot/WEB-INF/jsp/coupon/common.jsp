<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    $(document).ready(function () {
        if($("#someGoods").attr("checked")=="checked"){
            $(".use_type_content").show();
        }
    });
</script>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">优惠券类型<span class="must_span">*</span></td>
			<td class="td_right_two"><select id="coupon_type"
				name="coupon_type" type="select" class="validate" isrequired='yes'
				tipmsg="">
					<option value="">请选择</option>
					<option value="1"<c:if test="${coupon.coupon_type==1}"> selected</c:if>>摇一摇优惠券</option>
                    <%--<option value="2" <c:if test="${coupon.coupon_type==2}"> selected</c:if>>系统推送优惠券</option>--%>
                    <option value="3" <c:if test="${coupon.coupon_type==3}"> selected="selected"</c:if>>普通优惠券</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_left">优惠券名称<span class="must_span">*</span></td>
            <td class="td_right_two">
                <input class="text validate" isrequired='yes' type="text" name="coupon_name" tipmsg="优惠券名称"
                       maxlength='30' maxdatalength='30' value="${coupon.coupon_name}"/>
            </td>
        </tr>
        <tr class="coupon_num">
            <td class="td_left">优惠券数量<span class="must_span">*</span></td>
            <td class="td_right_two">
                <input class="text validate " type="text" name="coupon_num" tipmsg="优惠券数量" isrequired='yes'
                       maxlength='6' maxdatalength='6' value="${coupon.coupon_num}"/>&nbsp;&nbsp;张
            </td>
        </tr>
        <tr  class="send_to" style="display: none">
            <td class="td_left">发送对象<span class="must_span">*</span></td>
            <td class="td_right_two">
                <input id="level" type="radio" name="send_to" value="1" checked="checked">按会员等级
                <input id="sex_age" type="radio" name="send_to" value="2" >按性别年龄
                <input type="radio" name="send_to" value="3">新注册用户
                <input type="radio" name="send_to" value="4">生日会员
            </td>
        </tr>
        <tr class="send_to" style="display: none">
            <td class="td_left">会员等级<span class="must_span">*</span></td>
            <td class="td_right_two">
                <input type="radio" name="send_to_value" value="1" checked="checked">普通会员
                <input type="radio" name="send_to_value" value="2">会员
            </td>
        </tr>
        <tr>
            <td class="td_left">优惠券面值<span class="must_span">*</span></td>
            <td class="td_right_two">
                <input class="text validate" type="text" name="coupon_amount" tipmsg="优惠券面值" isrequired='yes'
                       maxlength='5' maxdatalength='5' value="${coupon.coupon_amount}"/>&nbsp;&nbsp;元
            </td>
        </tr>
        <tr>
            <td class="td_left">使用门槛<span class="must_span">*</span></td>
            <td class="td_right_two">
                <input class="text validate" type="text" name="use_amount" tipmsg="使用门槛" isrequired='yes'
                       maxlength='7' maxdatalength='7' value="${coupon.use_amount}"/>&nbsp;&nbsp;元
            </td>
        </tr>
        <tr>
            <td class="td_left">每人限领数量<span class="must_span">*</span></td>
            <td class="td_right_two">
                <input class="text validate" type="text" name="use_num" tipmsg="每人限领数量" isrequired='yes'
                       maxlength='3' maxdatalength='3' value="${coupon.use_num}"/>&nbsp;&nbsp;张
            </td>
        </tr>
        <tr>
            <td class="td_left">使用类型<span class="must_span">*</span></td>
            <td class="td_right_two">
                <input type="radio" name="use_type" value="1" lay-filter="show" id="allGoods"
                       <c:if test="${coupon.use_type!='2'}">checked="checked"</c:if>>全部商品
                <input type="radio" name="use_type" value="2" lay-filter="show" id="someGoods"
                       <c:if test="${coupon.use_type=='2'}">checked="checked"</c:if>>指定商品

            </td>
        </tr>
        <tr>
            <td class="td_left">
				<span style="display: none" class="use_type_content">
					<input type="button" value="指定商品" class="btn operbtn" onclick="openIframe('/iframe/goodsList')"/>
				</span>
            </td>
            <td class="td_right_two">
				<span style="display: none" class="use_type_content">
				<lable id="goods">
					<c:if test="${!empty goodsList}">
                        <c:forEach items="${goodsList}" var="item" varStatus="status">
                            <div style="margin-top:5px;">
                            <span >${status.index+1}、</span><span  class="goods_checked" checked_id="${item.goods_id}">
                                ${item.goods_name}
                        </span><br>
                            <input type="hidden" name="goodsIds" value="${item.goods_id}">
                            </div>
                        </c:forEach>
                    </c:if>

				</lable>
				</span>
            </td>
        </tr>
        <tr>
            <td class="td_left">截止有效期<span class="must_span">*</span></td>
            <td class="td_right_two">
                <%--<input  class="text validate" type="text" name="end_time"    tipmsg="截止有效期" isrequired='yes'--%>
                <%--maxlength='20000'  maxdatalength='20000'    value="${coupon.end_time}"/>--%>
                <input type="text validate" id="end_date" name="end_time" value="${coupon.end_time}" tipmsg="截止有效期"
                       isrequired='yes'/>
            </td>
        </tr>
        <c:if test="${coupon.coupon_id!=null}">
            <input type="hidden" name="coupon_id" value="${coupon.coupon_id}"/>
        </c:if>

        <%@ include file="/WEB-INF/common/search_hidden_field.jsp" %>
    </table>
</div>

<script>
    //优惠券类型 选中事件
    $("#coupon_type").change(function () {
        var vlaue=$(this).val();
        //如果是摇一摇 或 普通
        if(vlaue=="1"||vlaue=="3"){
            $(".send_to").hide();
            $(".coupon_num").show();
        }
        //如果是系统推送
        if(vlaue=="2"){
            $(".send_to").show();
            $(".coupon_num").hide();
        }
    });
    $("#allGoods").click(function () {
        $(".use_type_content").hide();
    });

    $("#someGoods").click(function () {
        $(".use_type_content").show();
    });
    function openIframe(url) {
        parent.layer.open({
            type: 2,
            title: '添加',
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['800px', '520px'],
            content: url
        });
    }
    $(function () {
        // 时间设置
        $('#end_date').datetimepicker({
            lang: 'ch',
            timepicker: true,
            format: 'Y-m-d H:i',
            formatDate: 'Y-m-d H:i',
        });
    });
</script>