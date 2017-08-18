<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    #div-top{
        height:60px;
        line-height:60px;
        margin-left: 20px;
        font-weight:bold;
        font-size:16px;
    }
    .navbar-nav {
        width: 100%;
        margin: 0;
        float: left;

    }
    .nav {
        padding-left: 0;
        margin-bottom: 0;
        list-style: none;
    }
    ul {
        margin: 0;
        padding: 0;
        height:35px;
        line-height:35px;
        background-color: #f1f1f1;


    }
    ul li {
        margin-left: 20px;
        color: #6495ED;
    }
    ul, ol {
        margin-top: 0;
        margin-bottom: 10px;
        width:100px;

    }
    * {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }
    body {
        font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
        font-size: 14px;
        line-height: 1.42857143;
        color: #333;
    }
</style>
<div class="modal-content" id="brand-content" style="margin-left:-20%;width: 800px;height:500px">
    <div id="div-top">链接小工具</div>
    <div style="width:100%;height:35px;border-bottom:solid 1px #c4c2c5; border-top:solid 1px #c4c2c5 ">
        <%--标签区域 H5链接 ,  --%>
        <ul class="nav navbar-nav">
            <li>H5链接</li>
            <li>自定义页</li>
            <li>基础商品</li>
            <li>活动商品</li>
            <li>活动列表</li>
        </ul>
    </div>
    <div id="div-main">

    </div>

</div>


<script type="text/javascript" src="<%=basePath%>/js/commpage.js"></script>
<script>

    function initTableData(pageNo){
        var supplierId ="" ;
        $.ajax({
            type: "GET",
            url: $("#dataLink").val(),
            data: {
                pageNo: pageNo
            },
            dataType: 'json',
            success: function (data) {
                totalCount = data.totalCount;
                totalPages = data.totalPages;
                pageNo= data.pageNo;
                $("#pageInfo").html("第 "+pageNo+" 页 ( 总共 "+totalPages+" 页 , 共 "+totalCount+" 条记录 )");
                $('#commTableTbody').html("");
                $.each(data.data, function(i, item) {
                    var html = "";
                    html+="<tr ondblclick=\"parentFun('"+item.productId+"','"+item.productName+"')\">";
                    html += "<input type='hidden' id='h"+i+"' value='' />";
                    html += "<td>"+item.productName+"</td>";
                    html += "<td>"+item.typeName+"</td>";
                    html += "<td>"+item.price+"</td>";
                    html += "<td>"+item.isPromotion+"</td>";
                    html += "<td>"+item.promotionPrice+"</td>";
                    html += "<td>"+item.totalNum+"</td>";
                    html += "<td>"+item.sales+"</td>";
                    html += "<td>"+item.createTime+"</td>";
                    html += "<td>"+item.status+"</td>";
                    html += "</tr>";
                    $('#commTableTbody').append(html);
                });

            }
        });
    }
    initTableData(1);
    function parentFun(v1,v2){
        $("#note"+parentId).html(v2);
        $("#codeindex_url"+parentId).val(v1);
        $("#brand-edit").modal("hide");
    }
</script>
