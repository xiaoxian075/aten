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
                <input type="text" name="goods_name_vague" placeholder="宝贝标题" class="layui-input">
            </div>
            <div class="layui-input-group">

                <button class="layui-btn " style="background-color: #5a98de" table-id="goodsTable" lay-submit=""
                        lay-filter="search"><i class="fa fa-search">&nbsp;</i>查询
                </button>
                <button type="reset" class="layui-btn layui-btn-primary"><i class="fa fa-refresh">&nbsp;</i>重置</button>
                <button onclick="choseValue('goodsTable')" style="background-color: #5a98de;" class="layui-btn"
                        table-id="goodsTable" lay-submit="" lay-filter="search"><i class="fa fa-search">&nbsp;</i>确认
                </button>
            </div>
        </div>
    </form>
    <div class="layui-form ">
        <table class="layui-table" id="goodsTable" cyType="pageGrid"  discount="${discount_money}"
               cyProps="url:'/getGoodsListNotRelative/${activity_id}',checkbox:'true',pageColor:'#5a98de',checked_name:'goods_checked'">
            <thead>
            <tr>
                <!--复选框-->
                <th width="20px;" param="{type:'checkbox'}">
                    <%--<input type="checkbox" lay-skin="primary" lay-filter="allChoose">--%>
                </th>
                <!--isPrimary：是否是主键-->
                <th param="{name:'goods_id',isPrimary:'true',hide:'true'}">分类ID</th>
                <th param="{name:'goods_name',isShowName:'true'}">宝贝标题</th>
                <th param="{name:'lower_price',isPixedPrice:'true',hide:'true'}">价格(分)</th>
                <th param="{name:'price',isPrice:'true'}">价格(元)</th>
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
        var checked_price = $(hasCheckeds[i]).attr("checked_price");
        var checked_fixed_price = $(hasCheckeds[i]).attr("checked_fixed_price");
        var select = new Array(2);
        select.id = checked_id;
        select.name = checked_name;
        select.price = checked_price;
        select.fixed_price = checked_fixed_price;
        selects.push(select);
    }
    function fmoney(s, n)
    {
        n = n > 0 && n <= 20 ? n : 2;
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
        var l = s.split(".")[0].split("").reverse(),
            r = s.split(".")[1];
        t = "";
        for(i = 0; i < l.length; i ++ )
        {
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
        }
        return t.split("").reverse().join("") + "." + r;
    }
    function choseValue(table_id) {
         $(parent.document).find("#goods").html("");
         for (var i = 0; i < selects.length; i++) {
             var activity_type=${activity_type};
             var discount=${discount};
             var activity_price=0;
             //如果是限时折扣
             if(activity_type==1){
                 activity_price=selects[i].fixed_price*(discount/10);
                 activity_price=fmoney(activity_price/100,2);
             }
             //如果是黄金特惠
             if(activity_type==2){
                 activity_price=selects[i].fixed_price-(discount*100);
                 activity_price=fmoney(activity_price/100,2);
             }
             //如果是免手工费
             if(activity_type==3){
                 activity_price="免手工费";
             }

             $(parent.document).find("#goods").append('<tr> <input type="hidden" name="goodsIds" value="' + selects[i].id + '"> <td> <span class="goods_checked" checked_id="' + selects[i].id + '" checked_fixed_price="'+selects[i].fixed_price + '" checked_price="'+selects[i].price + '">'+selects[i].name + '</span></td> <td>'+selects[i].price + '</td> <td>'+activity_price+'</td> </tr>');


//
// '<span style="color:red">*</span><span class="goods_checked" checked_id="' + selects[i].id + '">'
//                + selects[i].name + ' </span><br><input type="hidden" name="goodsIds" value="' + selects[i].id + '">');

         }
         closeWindow();

    }
</script>
</body>
</html>
