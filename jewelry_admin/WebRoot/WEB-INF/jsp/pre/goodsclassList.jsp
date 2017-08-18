<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/29
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Title</title>
<%@ include file="/component/lay/pageGrid/resource.jsp"%>

</head>
<body>
	<div style="margin:10px;">
		<form class="layui-form " action="">
			<div class="layui-form-item">
				<label class="layui-form-label">分类名称:</label>
				<div class="layui-input-inline">
					<input type="text" name="cat_name_vague" placeholder="支持模糊查询"
						class="layui-input">
				</div>
				<div class="layui-input-group">

					<button class="layui-btn " style="background-color: #5a98de"
						table-id="catTable" lay-submit="" lay-filter="search">
						<i class="fa fa-search">&nbsp;</i>查询
					</button>
					<button type="reset" class="layui-btn layui-btn-primary">
						<i class="fa fa-refresh">&nbsp;</i>重置
					</button>
					<button onclick="choseValue('catTable')"
							style="background-color: #5a98de;" class="layui-btn"
							table-id="catTable" lay-submit="" lay-filter="search">
						<i class="fa fa-search">&nbsp;</i>确认
					</button>
				</div>
			</div>
		</form>
		<div class="layui-form ">
			<table class="layui-table" id="catTable" cyType="pageGrid"
				cyProps="url:'/getGoodsClassList',checkbox:'true',pageColor:'#5a98de',checked_name:'cat_checked'">
				<thead>
					<tr>
						<!--复选框-->
						<th width="20px;" param="{type:'checkbox'}">
							<%--<input type="checkbox" lay-skin="primary" lay-filter="allChoose">--%>
						</th>
						<!--isPrimary：是否是主键-->
						<th param="{name:'cat_id',isPrimary:'true',hide:'true'}">分类ID</th>
						<th param="{name:'cat_name',isShowName:'true',hide:'true'}">分类名称</th>
						<th param="{name:'parent_level_name'}">分类名称</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script>
    var hasCheckeds=$(parent.document).find(".cat_checked");
    var selects=new Array();
    //初始化需要勾选的复选框
    for(var i=0;i<hasCheckeds.length;i++){
        var checked_id= $(hasCheckeds[i]).attr("checked_id");
        var checked_name=$(hasCheckeds[i]).text();
        var select=new Array(2);
        select.id=checked_id;
        select.name=checked_name;
        selects.push(select);
    }
    function choseValue(table_id) {
        $(parent.document).find("#cats").html("");
        for(var i=0;i<selects.length;i++){
           $(parent.document).find("#cats").append('<span class="cat_checked" checked_id="'+selects[i].id+'">'+selects[i].name+' </span><input type="hidden" name="catIds" value="'+selects[i].id+'">');

        }
        closeWindow();
    }
</script>
</body>
</html>
