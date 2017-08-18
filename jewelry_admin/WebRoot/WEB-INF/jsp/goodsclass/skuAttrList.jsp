<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/29
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="/component/lay/pageGrid/resource.jsp" %>

</head>
<body>
<div style="margin:10px;">
    <form class="layui-form " action="">
        <div class="layui-form-item">
            <label class="layui-form-label">商品规格:</label>
            <div class="layui-input-inline">
                <input type="text" name="attr_name_vague" placeholder="支持模糊查询" class="layui-input">
            </div>
            <div class="layui-input-group">
                <button class="layui-btn " style="background-color: #5a98de" table-id="skuAttrTable" lay-submit=""
                        lay-filter="search"><i class="fa fa-search">&nbsp;</i>查询
                </button>
                <button type="reset" class="layui-btn layui-btn-primary"><i class="fa fa-refresh">&nbsp;</i>重置</button>
                <button onclick="choseValue('skuAttrTable')" style="background-color: #5a98de;" class="layui-btn"
                        table-id="skuAttrTable" lay-submit="" lay-filter="search"><i class="fa fa-search">&nbsp;</i>确认
                </button>
            </div>
        </div>
    </form>
    <div class="layui-form ">
        <table class="layui-table" id="skuAttrTable" cyType="pageGrid"
               cyProps="url:'/getAttrList',checkbox:'true',pageColor:'#5a98de',checked_name:'sku_attr_checked'">
            <thead>
            <tr>
                <!--复选框-->
                <th width="20px;" param="{type:'checkbox'}">
                    <%--<input type="checkbox" lay-skin="primary" lay-filter="allChoose">--%>
                </th>
                <!--isPrimary：是否是主键-->
                <th param="{name:'attr_id',isPrimary:'true',hide:'true'}">ID</th>
                <th param="{name:'attr_name',isShowName:'true'}">规格名称</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<script>
    var hasCheckeds = $(parent.document).find(".sku_attr_checked");
    var selects = new Array();
    //初始化需要勾选的复选框
    for (var i = 0; i < hasCheckeds.length; i++) {
        var checked_id = $(hasCheckeds[i]).attr("checked_id");
        var option_type = $(hasCheckeds[i]).attr("option_type") || 1;
        var is_index = $(hasCheckeds[i]).attr("is_index") || 0;
        var is_must = $(hasCheckeds[i]).attr("is_must") || 0;
        var is_custom_value = $(hasCheckeds[i]).attr("is_custom_value") || 0;
        var manual_fee = $(hasCheckeds[i]).attr("manual_fee") || 0;
        var checked_name = $(hasCheckeds[i]).text();
        var select = new Array();
        select.id = checked_id;
        select.name = checked_name;
        select.option_type = option_type;
        select.is_index = is_index;
        select.is_must = is_must;
        select.is_custom_value = is_custom_value;
        select.manual_fee = manual_fee;
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
        select.option_type_name = option_type_name;
        select.is_index_name = (is_index == 1 ? '<span style="color:green">是</span>' : '<span style="color:red">否</span>');
        select.is_must_name = (is_must == 1 ? '<span style="color:green">是</span>' : '<span style="color:red">否</span>');
        select.is_custom_value_name = (is_custom_value == 1 ? '<span style="color:green">是</span>' : '<span style="color:red">否</span>');
        selects.push(select);
    }
    function choseValue(table_id) {
        if (selects.length > 8) {
            alert("最多只能选择八条数据");
        } else {
            $(parent.document).find("#skuAttrs").html("");
            for (var i = 0; i < selects.length; i++) {
                debugger;
                $(parent.document).find("#skuAttrs").append(
                    '<tr id="skuSetting_' + selects[i].id + '">' +
                    '<input type="hidden" class="sku_attr_id" name="catSkuAttrList[' + i + '].attr_id" value="' + selects[i].id + '">' +
                    '<input type="hidden" class="sku_attr_name" name="catSkuAttrList[' + i + '].attr_name" value="' + selects[i].name + '">' +
                    ' <td><span class="sku_attr_checked "   is_custom_value="' + selects[i].is_custom_value + '"' +
                    'checked_id="' + selects[i].id + '"  manual_fee="' + selects[i].manual_fee + '"	 option_type="' + selects[i].option_type + '" is_index="' + selects[i].is_index + '"  is_must="' + selects[i].is_must + '"' +
                    '>' + selects[i].name + '</span></td>' +
                    ' <td><input type="hidden" class="sku_option_type" name="catSkuAttrList[' + i + '].option_type" value="' + selects[i].option_type + '"/><span>' + selects[i].option_type_name + '</span></td>' +
                    ' <td><input type="hidden" class="sku_is_index" name="catSkuAttrList[' + i + '].is_index" value="' + selects[i].is_index + '"/><span>' + selects[i].is_index_name + '</span></td>' +
                    '  <td><input type="hidden" class="sku_is_must" name="catSkuAttrList[' + i + '].is_must" value="' + selects[i].is_must + '"/><span>' + selects[i].is_must_name + '</span></td>' +
                    '    <td><input type="hidden" class="sku_is_custom_value" name="catSkuAttrList[' + i + '].is_custom_value" value="' + selects[i].is_custom_value + '"/><span>' + selects[i].is_custom_value_name + '</span></td>' +

                    ' <span>' + selects[i].manual_fee + '</span></td>' +
                    '   <td>' +
                    '   <span class="layui-btn  layui-btn-mini" onclick="setSkuAttrIframe(' + selects[i].id + ')">配置</span>' +
                    '  </td></tr>'
                );
            }
            closeWindow();
//               '<tr><td><span class="sku_attr_checked" checked_id="'+selects[i].id+'" >'+selects[i].name+' </span></td>  <td>-</td> <td>-</td> <td>-</td> <td>-</td> <td> <span onclick="setAttr('+selects[i].id+',\''+selects[i].name+'\')" class="layui-btn  layui-btn-mini">配置</span> <span class="layui-btn layui-btn-delete layui-btn-mini">删除</span> </td> </tr>'

        }

    }
</script>
</body>
</html>
