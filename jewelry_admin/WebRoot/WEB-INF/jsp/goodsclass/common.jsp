<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    function openIframe(url) {
        parent.layer.open({
            type: 2,
            title: '添加',
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['600px', '420px'],
            
            content: url
        });
    }
    function setSkuAttrIframe(id) {
        //获取该条配置数据
        var attr_id = $("#skuSetting_" + id).find(".sku_attr_id").val();
        var attr_name = $("#skuSetting_" + id).find(".sku_attr_name").val();
        var option_type = $("#skuSetting_" + id).find(".sku_option_type").val();
        var is_index = $("#skuSetting_" + id).find(".sku_is_index").val();
        var is_must = $("#skuSetting_" + id).find(".sku_is_must").val();
        var is_custom_value = $("#skuSetting_" + id).find(".sku_is_custom_value").val();
        var manual_fee = $("#skuSetting_" + id).find(".sku_manual_fee").val();
        parent.layer.open({
            type: 2,
            title: '配置' + attr_name,
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['600px', '420px'],
            content: "/setSkuAttrIframe?attr_id=" + attr_id + "&option_type=" + option_type + "&is_index=" + is_index
            + "&is_must=" + is_must + "&is_custom_value=" + is_custom_value + "&manual_fee=" + manual_fee
        });
    }

    function setKeyAttrIframe(id) {
        //获取该条配置数据
        var attr_id = $("#keySetting_" + id).find(".key_attr_id").val();
        var attr_name = $("#keySetting_" + id).find(".key_attr_name").val();
        var option_type = $("#keySetting_" + id).find(".key_option_type").val();
        var is_index = $("#keySetting_" + id).find(".key_is_index").val();
        var is_must = $("#keySetting_" + id).find(".key_is_must").val();
        var is_custom_value = $("#keySetting_" + id).find(".key_is_custom_value").val();
        var manual_fee = $("#keySetting_" + id).find(".key_manual_fee").val();
        parent.layer.open({
            type: 2,
            title: '配置' + attr_name,
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['600px', '420px'],
            content: "/setKeyAttrIframe?attr_id=" + attr_id + "&option_type=" + option_type + "&is_index=" + is_index
            + "&is_must=" + is_must + "&is_custom_value=" + is_custom_value + "&manual_fee=" + manual_fee
        });
    }
</script>
<script type="text/javascript">
    $(document).ready(function () {
        //分类级联
        $("#cat_id_div").cascadeSel({
            url: "/admin/cat/normalList",
            name: "cat_id",
            init_id: "${cfg_goods_top}"
        });
    });
</script>
<div class="table_div">
	<input type="hidden" name="cat_id" value="${cat.cat_id}" /> <input
		type="hidden" id="isEdit" value="${isEdit}" />
	<table width="100%">
		<tr>
			<td class="td_left">商品分类名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="cat_name" value="${cat.cat_name}" isrequired='yes'
				tipmsg="商品分类名称" maxlength='16' maxdatalength='16'
				style="width:380px;" /></td>
		</tr>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate sort_no"
				type="text" name="sort_no" value="${cat.sort_no}" isrequired='yes'
				dataType="jsInt" tipmsg="排序" maxlength='6' maxdatalength='6'
				setwidth="120" value="0" /></td>
		</tr>
		<c:if test="${!isEdit}">
		<tr>
			<td class="td_left">所属分类:</td>
			<td class="td_right_two">
				<div id="cat_id_div" tipmsg="所属分类" setwidth="200" setheight="25"></div>
				<input class="validate" changetip="cat_id_div" type="hidden"
				id="cat_id" name="parent_cat_id" value="${cat.parent_cat_id}" />
			</td>
		</tr>
		</c:if>
		<c:if test="${isEdit}">
		<tr>
			<td class="td_left">所属分类:</td>
			<td class="td_right_two">
				${cat.parent_cat_id}
			</td>
		</tr>
		</c:if>
		<tr>
			<td class="td_left">是否启用<span class="must_span">*</span></td>
			<td class="td_right_two"><select class="validate" name="state"
				isrequired='yes' type="select" tipmsg="状态" widthtip="100">
					<option value="">请选择</option>
					<option value="1"<c:if test="${cat.state==1}"> selected</c:if>>启用</option>
                    <option value="0" <c:if test="${cat.state==0}"> selected</c:if>>禁用</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_left">关联品牌:</td>
            <td class="td_right_two">
                <input type="button" value="选择品牌" class="btn operbtn" onclick="openIframe('/iframe/brandList')"/>
                <%--<input  name="" value="选择品牌" class="btn operbtn" onclick="openIframe('/brandList')"/>--%>
                <lable id="brands">
                    <c:if test="${!empty catBrandList}">
                        <c:forEach items="${catBrandList}" var="item" varStatus="status">
							<span class="brand_checked" checked_id="${item.brand_id}">
								 ${item.brand_name}
							</span>
                            <input type="hidden" name="brandIds" value="${item.brand_id}">
                        </c:forEach>
                    </c:if>

                </lable>

            </td>
        </tr>

        <tr>
            <td class="td_left">关联规格:</td>
            <td class="td_right_two">
                <input type="button" value="选择规格" class="btn operbtn" onclick="openIframe('/iframe/skuAttrList')"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <div class="layui-form ">
                    <table class="layui-table">
                        <thead>
                        <tr>
                            <th>商品规格</th>
                            <th>取值方式</th>
                            <th>是否为筛选属性</th>
                            <th>是否为必填属性</th>
                            <th>是否支持自定义</th>
                            <%--<th>是否支持新增属性值</th>--%>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="skuAttrs">
                        <c:if test="${!empty catSkuAttrList}">
                            <c:forEach items="${catSkuAttrList}" var="item" varStatus="status">
                                <tr id="skuSetting_${item.attr_id}">
                                    <input type="hidden" class="sku_attr_id"
                                           name="catSkuAttrList[${status.index}].attr_id" value="${item.attr_id}">
                                    <input type="hidden" class="sku_attr_name"
                                           name="catSkuAttrList[${status.index}].attr_name" value="${item.attr_name}">
                                    <td><span class="sku_attr_checked "
                                              manual_fee="${item.manual_fee}" checked_id="${item.attr_id}"
                                              option_type="${item.option_type}" is_index="${item.is_index}"
                                              is_must="${item.is_must}" is_custom_value="${item.is_custom_value}"
                                    >${item.attr_name}
                                    </span></td>
                                    <td><input type="hidden" class="sku_option_type"
                                               name="catSkuAttrList[${status.index}].option_type"
                                               value="${item.option_type}"/>
                                        <span><c:if test="${item.option_type==1}">单选</c:if><c:if
                                                test="${item.option_type==2}">多选</c:if><c:if
                                                test="${item.option_type==0}">文本框</c:if></span></td>
                                    <td><input type="hidden" class="sku_is_index"
                                               name="catSkuAttrList[${status.index}].is_index"
                                               value="${item.is_index}"/>
                                        <span><c:if test="${item.is_index==1}"><span style="color:green">是</span></c:if><c:if
                                                test="${item.is_index==0}"><span
                                                style="color:red">否</span></c:if></span></td>
                                    <td><input type="hidden" class="sku_is_must"
                                               name="catSkuAttrList[${status.index}].is_must" value="${item.is_must}"/>
                                        <span><c:if test="${item.is_must==1}"><span
                                                style="color:green">是</span></c:if><c:if test="${item.is_must==0}"><span
                                                style="color:red">否</span></c:if></span></td>
                                    <td><input type="hidden" class="sku_is_custom_value"
                                               name="catSkuAttrList[${status.index}].is_custom_value"
                                               value="${item.is_custom_value}"/>
                                        <span><c:if test="${item.is_custom_value==1}"><span
                                                style="color:green">是</span></c:if><c:if test="${item.is_custom_value==0}">
                                            <span style="color:red">否</span></span></c:if></td>
                                    <%--<td><input type="hidden" class="sku_manual_fee"--%>
                                               <%--name="catSkuAttrList[${status.index}].manual_fee"--%>
                                               <%--value="${item.manual_fee}"/>--%>
                                        <%--<span>${item.manual_fee}</span></td>--%>
                                    <td>
                                        <span class="layui-btn  layui-btn-mini"
                                              onclick="setSkuAttrIframe('${item.attr_id}')">配置</span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_left">关联属性:</td>
            <td class="td_right_two">
                <input type="button" value="选择属性" class="btn operbtn" onclick="openIframe('/iframe/keyAttrList')"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <div class="layui-form ">
                    <table class="layui-table">
                        <thead>
                        <tr>
                            <th>商品规格</th>
                            <th>取值方式</th>
                            <th>是否为筛选属性</th>
                            <th>是否为必填属性</th>
                            <th>是否支持自定义</th>
                            <%--<th>是否支持新增属性值</th>--%>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="keyAttrs">
                        <c:if test="${!empty catKeyAttrList}">
                            <c:forEach items="${catKeyAttrList}" var="item" varStatus="status">
                                <tr id="keySetting_${item.attr_id}">
                                    <input type="hidden" class="key_attr_id"
                                           name="catKeyAttrList[${status.index}].attr_id" value="${item.attr_id}">
                                    <input type="hidden" class="key_attr_name"
                                           name="catKeyAttrList[${status.index}].attr_name" value="${item.attr_name}">
                                    <td><span class="key_attr_checked "
                                              manual_fee="${item.manual_fee}" checked_id="${item.attr_id}"
                                              option_type="${item.option_type}" is_index="${item.is_index}"
                                              is_must="${item.is_must}" is_custom_value="${item.is_custom_value}"
                                    >${item.attr_name}
                                    </span></td>
                                    <td><input type="hidden" class="key_option_type"
                                               name="catKeyAttrList[${status.index}].option_type"
                                               value="${item.option_type}"/>
                                        <span><c:if test="${item.option_type==1}">单选</c:if><c:if
                                                test="${item.option_type==2}">多选</c:if><c:if
                                                test="${item.option_type==0}">文本框</c:if></span></td>
                                    <td><input type="hidden" class="key_is_index"
                                               name="catKeyAttrList[${status.index}].is_index"
                                               value="${item.is_index}"/>
                                        <span><c:if test="${item.is_index==1}"><span style="color:green">是</span></c:if><c:if
                                                test="${item.is_index==0}"><span
                                                style="color:red">否</span></c:if></span></td>
                                    <td><input type="hidden" class="key_is_must"
                                               name="catKeyAttrList[${status.index}].is_must" value="${item.is_must}"/>
                                        <span><c:if test="${item.is_must==1}"><span
                                                style="color:green">是</span></c:if><c:if test="${item.is_must==0}"><span
                                                style="color:red">否</span></c:if></span></td>
                                    <td><input type="hidden" class="key_is_custom_value"
                                               name="catKeyAttrList[${status.index}].is_custom_value"
                                               value="${item.is_custom_value}"/>
                                        <span><c:if test="${item.is_custom_value==1}"><span
                                                style="color:green">是</span></c:if><c:if test="${item.is_custom_value==0}">
                                            <span style="color:red">否</span></span></c:if></td>
                                    <%--<td><input type="hidden" class="key_manual_fee"--%>
                                               <%--name="catKeyAttrList[${status.index}].manual_fee"--%>
                                               <%--value="${item.manual_fee}"/>--%>
                                        <%--<span>${item.manual_fee}</span></td>--%>
                                    <td>
                                        <span class="layui-btn  layui-btn-mini"
                                              onclick="setKeyAttrIframe('${item.attr_id}')">配置</span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_left">推荐分成收益比例<span class="must_span">*</span></td>
            <td class="td_right_two">
                <input class="text validate" type="text"
                       name="divide_rate" value="${catRate.divide_rate}" isrequired='yes' tipmsg="推荐分成收益比例"
                       maxlength='3' maxdatalength='3' style="width:100px;" setWidth="300"/> <span>%</span>
            </td>
        </tr>
        <tr>
            <td class="td_left">手工费(元)<span class="must_span">*</span></td>
            <td class="td_right_two">
                <input class="text validate" type="text"
                       name="manual_fee" value="${catRate.manual_fee}" isrequired='yes' tipmsg="手工费"
                       maxlength='6' maxdatalength='6'  style="width:100px;" setWidth="300"/> 元
            </td>
        </tr>
        <tr>
            <td class="td_left">关联供应商:</td>
            <td class="td_right_two">
                <input type="button" value="选择供应商" class="btn operbtn" onclick="openIframe('/iframe/supplyList')"/>
                <lable id="supplys">
                    <c:if test="${!empty catSupplyList}">
                        <c:forEach items="${catSupplyList}" var="item" varStatus="status">
                            <span class="supply_checked" checked_id="${item.supply_id}">
                                ${item.supply_name}</span>
                            <input type="hidden" name="supplyIds" value="${item.supply_id}">

                        </c:forEach>
                    </c:if>
                </lable>
            </td>
        </tr>
        <tr>
            <td class="td_left">关联鉴定机构:</td>
            <td class="td_right_two">
                <input type="button" value="选择鉴定机构" class="btn operbtn" onclick="openIframe('/iframe/appraisalList')"/>
                <lable id="appraisals">
                    <c:if test="${!empty catAppraisalList}">
                        <c:forEach items="${catAppraisalList}" var="item" varStatus="status">
                            <span class="appraisal_checked" checked_id="${item.appraisal_id}">
                                  ${item.appraisal_name}</span>
                            <input type="hidden" name="appraisalIds" value="${item.appraisal_id}">

                        </c:forEach>
                    </c:if>
                </lable>
            </td>
        </tr>
        <%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
    </table>
</div>
<div class="layui-layer-shade" id="layui-layer-shade" times="1"
     style="display:none;z-index:100; background-color:#000; opacity:0.3; filter:alpha(opacity=30);"></div>