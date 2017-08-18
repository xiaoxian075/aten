<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>


    <style>
        #div-top{
            height:60px;
            line-height:60px;
            margin-left: 20px;
            font-weight:bold;
            font-size:16px;
        }
        .navbar-nav {
            height:35px;
            line-height:35px;
            width: 100%;
            margin: 0;
            float: left;

        }
        .nav {
            padding-left: 0;
            margin-bottom: 0;
            list-style: none;
        }
        .active-result{
            color: #0a0a0a;
        }

        #ul-label ul {
            margin: 0;
            padding: 0;
            height:35px;
            line-height:35px;
            background-color: #f1f1f1;
        }
        #ul-label ul,#ul-label li {
            margin-left: 20px;
            color: #ffffff;
            padding: 0px 10px;
        }
        #div2_cx label{
            padding: 0px 10px;
        }
        #ul-label ul,#ul-label ol {
            margin-top: 0;
            margin-bottom: 10px;
            width:100px;

        }
        #ul-label * {
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
        .form-group{
            width:100%;
        }
        .col-xs-1{
            width:120px;
        }
        .col-lg-1{
            width:120px;
        }
        .chosen-select {
            display: inline !important;
            visibility: hidden;
            opacity: 0;
            position: absolute;
            z-index: -1;
        }
    </style>
    <script type="text/javascript"
            src="/component/ueditor1.4.3/third-party/jquery-1.10.2.min.js"></script>
</head>
<body>

<%--<link rel="stylesheet" href="<%=basePath%>assets/css/ace.min.css" />--%>
<%--<link rel="stylesheet" href="<%=basePath%>assets/css/ace-rtl.min.css" />--%>
<%--<link rel="stylesheet" href="<%=basePath%>assets/css/ace-skins.min.css" />--%>
<%--<link rel="stylesheet" href="<%=basePath%>assets/css/ace-ie.min.css" />--%>
<div class="modal-content" id="brand-content" style="margin-left:-20%;width: 800px;height:700px">

    <div id="div-top">链接小工具</div>
    <div style="width:100%;height:35px;border-top:solid 1px #5cc53b;background-color:#87b97e;" id="linkTopLabel">
        <input type="hidden" id="labelViewType" value="2"/>
        <%--标签区域 H5链接 ,  --%>
        <ul id="ul-label" class="nav navbar-nav">
            <%--<li>H5链接</li>--%>
            <li onclick="labelFun('div1',this,1)">自定义页</li>
            <li onclick="labelFun('div2',this,2)" style="background-color:#318f39">基础商品</li>
            <li onclick="labelFun('div3',this,3)">活动商品</li>
            <li onclick="labelFun('div4',this,4)">活动列表</li>
            <li onclick="labelFun('div5',this,5)">品牌商品页</li>
        </ul>
    </div>
    <div id="div-label">
        <form  id="editForm">
            <input type="hidden" id="pageNo" name="pageNo" value="1" />
            <input type="hidden" id="getDataUrl" value="/admin/customizedPage/getViewProductTableList"/>
            <input type="hidden" id="dataTableId" value="cpTableTbody" />
            <div id="div1" act="label"  style="display:none" getDataUrl="customizedPage/getViewZDYTableList" dataTableId="zdyTableTbody" >
                <div class="modal-body">
                    <div class="form-group">
                        <%--遍历查询条件--%>
                        <label class="col-xs-1 col-sm-1 control-label text-right">标题</label>
                        <div class="col-lg-1 col-sm-1">
                            <input type="text" class="form-control" name="pageTitle">
                        </div>

                        <div class="text-right" style="padding-top:5px">
                            <button type="button" class="btn btn-sm btn-success" onclick="initTableData(1)" ><i class="icon-search"></i> 查询 </button>
                        </div>
                    </div>
                    <div>
                        <table class="table table-striped table-bordered table-hover"  >
                            <thead>
                            <tr>
                                <th>页面标题</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="zdyTableTbody">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div id="div2" act="label"  getDataUrl="/admin/customizedPage/getViewProductTableList" dataTableId="cpTableTbody" >
                <div class="modal-body">
                    <div>
                        <div id="div2_cx">
                            <div style="float: left">
                                <label >商品名称</label><input type="text"  name="cpName" style="width:60px">
                            </div>
                            <div style="float: left">
                                <label >型号</label><input type="text"  name="merchantNumber" style="width:60px">
                            </div>
                            <div  style="float: left;width:210px">
                                <div style="float: right;width:200px;margin-top: -5px">
                                    <select class="chosen-select" name="brandId" >
                                        <option value="">品牌</option>
                                        <c:forEach items="${brand}" var="brand">
                                            <option value="${brand.brandId}">${brand.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                            </div>


                            <div style="float: left;"  class="group" id="Ptypetree">
                                <label>类目</label>
                                <input type="hidden" readonly="readonly"  id="pid" name="pid"  >
                                <input type="text" readonly="readonly" id="pName" name="pName" >
                                <div class="zTreeDemoBackground left" style="display:none;position: absolute;z-index: 99999;width:260px "id="typetrees" >
                                    <ul id="typetree" class="ztree"></ul>
                                </div>
                            </div>
                            <div style="float: left">
                                <button type="button" class="btn btn-sm btn-success" onclick="initTableData(1)" ><i class="icon-search"></i> 查询 </button>
                            </div>
                        </div>

                    </div>
                    <div>
                        <table class="table table-striped table-bordered table-hover" id="commTable">
                            <thead>
                            <tr>
                                <th>商品名称</th>
                                <th>所属类目</th>
                                <th>基础价格</th>
                                <th>库存</th>
                                <th>销量</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="cpTableTbody">
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
            <div id="div3" act="label"  style="display:none"  getDataUrl="purchaseDetail/getHDSPList" dataTableId="HDSPTableTbody" >
                <div class="modal-body">
                    <div class="form-group">
                        <%--遍历查询条件--%>
                        <label class="col-xs-1 col-sm-1 control-label text-right">活动商品名称</label>
                        <div class="col-lg-1 col-sm-1">
                            <input type="text" class="form-control" name="productName">
                        </div>
                        <div class="text-right" style="padding-top:5px">
                            <button type="button" class="btn btn-sm btn-success" onclick="initTableData(1)" ><i class="icon-search"></i> 查询 </button>
                        </div>
                    </div>
                    <div>
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>活动名称</th>
                                <th>活动商品名称</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="HDSPTableTbody">
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
            <div id="div4" act="label"  style="display:none" getDataUrl="customizedPage/getViewHDLBTableList" dataTableId="HDLBTableTbody" >
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-xs-1 col-sm-1 control-label text-right">活动名称</label>
                        <div class="col-lg-1 col-sm-1">
                            <input type="text" class="form-control" name="hdmc">
                        </div>
                        <div class="text-right" style="padding-top:5px">
                            <button type="button" class="btn btn-sm btn-success" onclick="initTableData(1)" ><i class="icon-search"></i> 查询 </button>
                        </div>
                    </div>
                    <div>
                        <table class="table table-striped table-bordered table-hover"  >
                            <thead>
                            <tr>
                                <th>活动编号</th>
                                <th>活动名称</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="HDLBTableTbody">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div id="div5" act="label"  style="display:none" getDataUrl="customizedPage/getViewBrandTableList" dataTableId="ppTableTbody" >
                <div class="modal-body">
                    <div class="form-group">
                        <%--遍历查询条件--%>
                        <label class="col-xs-1 col-sm-1 control-label text-right">品牌</label>
                        <div class="col-lg-1 col-sm-1">
                            <input type="text" class="form-control" name="ppName">
                        </div>

                        <div class="text-right" style="padding-top:5px">
                            <button type="button" class="btn btn-sm btn-success" onclick="initTableData(1)" ><i class="icon-search"></i> 查询 </button>
                        </div>
                    </div>
                    <div>
                        <table class="table table-striped table-bordered table-hover"  >
                            <thead>
                            <tr>
                                <th>品牌名称</th>
                                <th>英文名称</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="ppTableTbody">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div align="center">
                <a href="javascript:void(0)" class="btn btn-sm btn-success"  id="upPage"> 上一页</a>
                <span id="pageInfo">无数据</span>
                <a href="javascript:void(0)" class="btn btn-sm btn-success"  id="downPage"> 下一页</a>
            </div>
        </form>
    </div>


</div>
</body>
</html>


<script type="text/javascript" src="/include/admin/js/customizedPage/commpage.js"></script>
<script>
//    $(function(){
//            $.init.initChosen();
//    })
    function labelFun(divId,obj,type){
        $("#labelViewType").val(type);
        //隐藏所有标签层
        $("#div-label div[act=label]").each(function () {
            $(this).hide();
        });
        $("#ul-label li").each(function () {
            $(this).css("background-color","");
        });
        //显示层

        $("#"+divId).show();
        $("#getDataUrl").val($("#"+divId).attr("getDataUrl"));
        $("#dataTableId").val($("#"+divId).attr("dataTableId"));
        $(obj).css("background-color","#318f39");
        initTableData(1);
        $("#pageNo").val(1);
        pageNo = 1;
        totalCount = 0;
        totalPages = 0;
    }
    function initTableData(pageNo){
        $("#pageNo").val(pageNo);
        $.ajax({
            type: "GET",
            url: $("#getDataUrl").val(),
            data: $("#editForm").serialize(),
            dataType: 'json',
            success: function (data) {
                totalCount = data.totalCount;
                totalPages = data.totalPages;
                pageNo= data.pageNo;
                $("#pageInfo").html("第 "+pageNo+" 页 ( 总共 "+totalPages+" 页 , 共 "+totalCount+" 条记录 )");
                $('#'+$("#dataTableId").val()).html("");

                $.each(data.data, function(i, item) {
                    //检查获取哪里数据
                    var html = "";
                    var dataTableId = $("#dataTableId").val();
                    if(dataTableId == "cpTableTbody"){
                        html = getCPData(item);
                    }else if(dataTableId == "zdyTableTbody"){
                        html = getZDYData(item);
                    }else if(dataTableId == "ppTableTbody"){
                        html = getPPData(item);
                    }else if(dataTableId == "HDSPTableTbody"){
                        html = getHDSPData(item);
                    }else if(dataTableId == "HDLBTableTbody"){
                        html = getHDLBData(item);
                    }

                    $('#'+dataTableId).append(html);
                });

            }
        });
    }
    initTableData(1);
    function parentFun(id,mc){
        $("#note"+parentId).html(mc);
        $("#codeindex_url"+parentId).val(id);
        $("#view_url_type"+parentId).val($("#labelViewType").val());
        $("#brand-edit").modal("hide");
    }
    /**
     * 产品 数据
     * @param item
     * @returns {string}
     */
    function getCPData(item){
        var merchantNumber = "";
        if(item.merchantNumber != null){
            merchantNumber = item.merchantNumber;
        }
        var html = "";
        html+="<tr >";
        html += "<td>"+item.productName+merchantNumber+"</td>";
        html += "<td>"+item.typeName+"</td>";
        html += "<td>"+item.price+"</td>";
        html += "<td>"+item.totalNum+"</td>";
        html += "<td>"+item.sales+"</td>";
        html += "<td><span style='width:80px;' class='btn btn-sm btn-success' onclick=\"parentFun('"+item.productId+"','"+item.productName+"')\">选择链接</span></td>";
        html += "</tr>";
        return html;
    }

    /**
     * 自定义 数据
     * @param item
     * @returns {string}
     */
    function getZDYData(item){
        var html = "";
        html+="<tr >";
        html += "<td>"+item.pageTitle+"</td>";
        html += "<td><span style='width:80px;' class='btn btn-sm btn-success' onclick=\"parentFun('"+item.pageUnique+"','"+item.pageTitle+"')\">选择链接</span></td>";
        html += "</tr>";
        return html;
    }

    /**
     * 品牌 数据
     * @param item
     * @returns {string}
     */
    function getPPData(item){
        var html = "";
        html+="<tr >";
        html += "<td>"+item.name+"</td>";
        html += "<td>"+item.englishName+"</td>";
        html += "<td><span style='width:80px;' class='btn btn-sm btn-success' onclick=\"parentFun('"+item.brandId+"[&]"+item.name+"','"+item.name+"')\">选择链接</span></td>";
        html += "</tr>";
        return html;
    }
    /**
     * 活动商品 数据
     * @param item
     * @returns {string}
     */
    function getHDSPData(item){
        var url = basePath+"rest/h5/activityPay?productId="+item.productId+".isOneActive=%22one%22.purchaseNumber="+item.purchaseNumber+".";
        var html = "";
        html+="<tr >";
        html += "<td>"+item.purchaseName+"</td>";
        html += "<td>"+item.productName+"</td>";
        html += "<td><span style='width:80px;' class='btn btn-sm btn-success' onclick=\"parentFun('"+url+"','"+item.productName+"')\">选择链接</span></td>";
        html += "</tr>";
        return html;
    }
    /**
     * 活动列表 数据
     * @param item
     * @returns {string}
     */
    function getHDLBData(item){
        var url = basePath+"rest/h5/activity?purchaseNumber="+item.purchaseNumber+"&type=4";
        var html = "";
        html+="<tr >";
        html += "<td>"+item.purchaseNumber+"</td>";
        html += "<td>"+item.name+"</td>";
        html += "<td><span style='width:80px;' class='btn btn-sm btn-success' onclick=\"parentFun('"+url+"','"+item.name+"')\">选择链接</span></td>";
        html += "</tr>";
        return html;
    }

</script>