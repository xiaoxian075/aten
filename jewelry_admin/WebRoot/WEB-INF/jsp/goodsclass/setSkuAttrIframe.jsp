<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/3
  Time: 8:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Title</title>
<script type="text/javascript"
	src="/component/ueditor1.4.3/third-party/jquery-1.10.2.min.js"></script>
<link rel="stylesheet" href="/component/lay/layui/css/layui.css">
<script src="/component/lay/layer/layer.js"></script>
<script src="/component/lay/layui/layui.js"></script>
</head>
<body>
	<input type="hidden" id="attr_id" value="skuSetting_${catAttr.attr_id}">
	<form class="layui-form" style="padding:20px;" action=""
		id="skuSetting_'+selects[i].id+'">
		<%--<div class="layui-form-item" style="margin-top: 20px;">--%>
		<%--<label class="layui-form-label-left">属性名</label>--%>
		<%--<div class="layui-input-inline">--%>
		<%--<input type="text" name="attr_name"  disabled value=" "  class="layui-input">--%>
		<%--</div>--%>
		<%--</div>--%>
		<div class="layui-form-item">
			<label class="layui-form-label-left">取值方式</label>
			<div class="layui-input-block">

				<input type="radio" name="option_type" value="1" lay-filter="show"
					title="单选" <c:if test="${catAttr.option_type=='1'}">checked</c:if>>
				<input type="radio" name="option_type" value="2" lay-filter="show"
					title="多选" <c:if test="${catAttr.option_type=='2'}">checked</c:if>>
				<input type="radio" name="option_type" value="0" lay-filter="hide"
					title="文本框" <c:if test="${catAttr.option_type=='0'}">checked</c:if>>

			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label-left">是否为筛选属性</label>
			<div class="layui-input-block">
				<input type="radio" name="is_index" value="1" title="是"
					<c:if test="${catAttr.is_index=='1'}">
                   checked</c:if>>
				<input type="radio" name="is_index" value="0" title="否"
					<c:if test="${catAttr.is_index=='0'}">
                   checked</c:if>>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label-left">是否为必填属性</label>
			<div class="layui-input-block">
				<input type="radio" name="is_must" value="1" title="是"
					<c:if test="${catAttr.is_must=='1'}">checked</c:if>> <input
					type="radio" name="is_must" value="0" title="否"
					<c:if test="${catAttr.is_must=='0'}">checked</c:if>>
			</div>
		</div>
		<div class="layui-form-item" id="is_custom_value">
			<label class="layui-form-label-left">是否支持自定义属性值</label>
			<div class="layui-input-block">
				<input type="radio" name="is_custom_value" value="1" title="是"
					<c:if test="${catAttr.is_custom_value=='1'}">checked  </c:if>>
				<input type="radio" name="is_custom_value" value="0" title="否"
					<c:if test="${catAttr.is_custom_value=='0'}">checked  </c:if>>

			</div>
		</div>
		<%--<div class="layui-form-item" id="is_custom_value">--%>
		<%--<label class="layui-form-label-left">手工费(元)</label>--%>
		<%--<div class="layui-input-inline">--%>
		<%--<input type="text" name="manual_fee" lay-verify="manual_fee" value="${catAttr.manual_fee}" class="layui-input">--%>
		<%--</div>--%>
		<%--</div>--%>

		<div class="layui-form-item">
			<div class="layui-input-block-left">
				<button class="layui-btn" lay-submit="" lay-filter="submit">保存</button>
				<button class="layui-btn" onclick="closeWindow()">返回</button>
			</div>
		</div>
	</form>
</body>
<script>
    $(document).ready(function () {
        layui.use('form', function () {
            var form = layui.form();
            form.render();


            //监听提交
            form.on('submit(submit)', function (data) {
                //获取对应的表格对象
                var option_type = data.field.option_type;
                var is_index = data.field.is_index;
                var is_must = data.field.is_must;
                var is_custom_value = data.field.is_custom_value;
                var manual_fee = data.field.manual_fee;
                //设置关联规格列表中的值
                var attr_id = $("#attr_id").val();
                $(parent.document).find("#" + attr_id + " input.sku_option_type").val(option_type);
                var option_type_name = "";
                if (option_type == "1") {
                    option_type_name = "单选";
                }
                if (option_type == "2") {
                    option_type_name = "多选";
                }
                if (option_type == "0") {
                    option_type_name = "文本框";
                }
                $(parent.document).find("#" + attr_id + " input.sku_option_type").next().html(option_type_name);
                $(parent.document).find("#" + attr_id + " input.sku_is_index").val(is_index);
                $(parent.document).find("#" + attr_id + " input.sku_is_index").next().html(is_index == 1 ? '<span style="color:green">是</span>' : '<span style="color:red">否</span>');
                $(parent.document).find("#" + attr_id + " input.sku_is_must").val(is_must);
                $(parent.document).find("#" + attr_id + " input.sku_is_must").next().html(is_must == 1 ? '<span style="color:green">是</span>' : '<span style="color:red">否</span>');
                $(parent.document).find("#" + attr_id + " input.sku_is_custom_value").val(is_custom_value);
                $(parent.document).find("#" + attr_id + " input.sku_is_custom_value").next().html(is_custom_value == 1 ? '<span style="color:green">是</span>' : '<span style="color:red">否</span>');
                $(parent.document).find("#" + attr_id + " input.sku_manual_fee").val(manual_fee);
                $(parent.document).find("#" + attr_id + " input.sku_manual_fee").next().html(manual_fee);


                $($(parent.document).find(".sku_attr_checked")).attr("option_type",option_type);
                $($(parent.document).find(".sku_attr_checked")).attr("is_index",is_index);
                $($(parent.document).find(".sku_attr_checked")).attr("is_must",is_must);
                $($(parent.document).find(".sku_attr_checked")).attr("manual_fee",manual_fee);
                $($(parent.document).find(".key_attr_checked")).attr("manual_fee", manual_fee);
                manual_fee
                closeWindow();
                return false;
            });
            //监听单选
            form.on('radio(show)', function (data) {
                //获取对应的表格对象
//                $("[name='is_custom_value']").removeAttr("disabled");
//                $($("[name='is_custom_value']")[1]).click();
//                $("[name='is_custom_value']").attr("disabled","");
                form.render();
                return false;
            });
            form.on('radio(hide)', function (data) {
                debugger;
                //获取对应的表格对象
//                $("[name='is_custom_value']").removeAttr("disabled");
//                $($("[name='is_custom_value']")[0]).click();
//                $("[name='is_custom_value']").attr("disabled","");
                form.render();
                return false;
            });
            //自定义验证规则 金额必须是整数
//            form.verify({
//                manual_fee:[/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/, '请输入有效的金额']
//            });
        });


    });

    //关闭iframe
    function closeWindow() {
        var frameindex = parent.layer.getFrameIndex(window.name);
        layui.use(['form', 'layer'], function () {
            parent.layer.close(frameindex);
        });
    }
</script>
</html>
