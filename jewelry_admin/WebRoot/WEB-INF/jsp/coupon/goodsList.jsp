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


            <%--<label class="layui-form-label">分类</label>--%>
            <div class="layui-input-inline">
                <input value="" id="cat" cyType="treeTool"
                       cyProps="url:'/admin/goodsclass/select',name:'cat_id'"
                       onclick="openZtree(this)" placeholder="请选择分类" class="layui-input"/>
                <%--<input type="text"   style="display: none" name="parentId"  class="layui-input">--%>
                <!-- 选择菜单 -->
            </div>

            <%--<label class="layui-form-label">宝贝标题:</label>--%>
            <div class="layui-input-inline">
                <input type="text" name="name_code_vague" placeholder="宝贝标题"
                       class="layui-input">
            </div>
            <div class="layui-input-group">

                <button class="layui-btn " style="background-color: #5a98de"
                        table-id="goodsTable" lay-submit="" lay-filter="search">
                    <i class="fa fa-search">&nbsp;</i>查询
                </button>
                <button type="reset" class="layui-btn layui-btn-primary">
                    <i class="fa fa-refresh">&nbsp;</i>重置
                </button>
                <button onclick="choseValue('goodsTable')"
                        style="background-color: #5a98de;" class="layui-btn"
                        table-id="goodsTable" lay-submit="" lay-filter="search">
                    <i class="fa fa-search">&nbsp;</i>确认
                </button>
            </div>
        </div>
    </form>
    <div class="layui-form ">
        <table class="layui-table" id="goodsTable" cyType="pageGrid"
               cyProps="url:'/getGoodsList',checkbox:'true',pageColor:'#5a98de',checked_name:'goods_checked'">
            <thead>
            <tr>
                <!--复选框-->
                <th width="20px;" param="{type:'checkbox'}">
                    <%--<input type="checkbox" lay-skin="primary" lay-filter="allChoose">--%>
                </th>
                <!--isPrimary：是否是主键-->
                <th param="{name:'goods_id',isPrimary:'true',hide:'true'}">分类ID</th>
                <th param="{name:'goods_name',isShowName:'true'}">宝贝标题</th>
                <th param="{name:'lower_price'}">价格(元)</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<script>
    var hasCheckeds = $(parent.document).find(".goods_checked");
    var selects = new Array();
    //初始化需要勾选的复选框
    for (var i = 0; i < hasCheckeds.length; i++) {
        var checked_id = $(hasCheckeds[i]).attr("checked_id");
        var checked_name = $(hasCheckeds[i]).text();
        var select = new Array(2);
        select.id = checked_id;
        select.name = checked_name;
        selects.push(select);
    }
    function choseValue(table_id) {
        $(parent.document).find("#goods").html("");
        for (var i = 0; i < selects.length; i++) {
            var index = i + 1;
            $(parent.document).find("#goods").append('<div style="margin-top:5px;"><span>' + index + '、</span><span class="goods_checked" checked_id="' + selects[i].id + '">' + selects[i].name + ' </span><br><input type="hidden" name="goodsIds" value="' + selects[i].id + '"></div>');

        }
        closeWindow();
    }
</script>
</body>
</html>
