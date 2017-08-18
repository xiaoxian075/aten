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
    <div class="layui-form ">
        <table class="layui-table" id="goodsTable" cyType="pageGrid"  discount="${discount_money}"
               cyProps="url:'/getUpList/${acid}',checkbox:'false',pageColor:'#5a98de',checked_name:'goods_checked'">
            <thead>
            <tr>
                <!--isPrimary：是否是主键-->
                <th param="{name:'account'}">会员账户</th>
                <th param="{name:'amount'}">付费金额</th>
                <th param="{name:'ioType'}">升级方式</th>
                <th param="{name:'billTime'}">升级时间</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

</body>
</html>
