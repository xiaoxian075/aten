<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +  path+"/";
%>
<!DOCTYPE html>
<html>
<style>
    html { overflow-x:hidden; }
</style>
<jsp:include page="/WEB-INF/views/admin/common/head.jsp"/>
<body style="background-color: #fff;">
<div class="breadcrumbs" id="breadcrumbs" style="margin-top:5px;">
    <ul class="breadcrumb">
        <li>
            <span class="glyphicon glyphicon-home"></span>
            <a href="#" target="main" style="padding-left:5px;margin-left:5px;">首页</a>
        </li>
        <li>
            <a href="#">短信服务</a>
        </li>
        <li>
            <a href="<%=basePath%>rest/admin/sms/group">会员群组管理</a>
        </li>
        <li class="active">群组会员</li>
    </ul>
</div>
<div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_length" id="dataTables-example_length">

                <input type="hidden" id="groupId" name="groupId" value="${groupId}" />
                <input type="hidden" id="groupName" name="groupName" value="${groupName}" />
                <label>群组名称：</label>
                <label style="color:#356635">${groupName}</label>
                <%--<label>--%>
                    <%--<select class="form-control input-sm" name="groupId" id="groupId">--%>
                        <%--<option value=0 selected="selected">请选择</option>--%>
                    <%--</select>--%>
                <%--</label>--%>

                <%--&nbsp;&nbsp;--%>
                <%--<label>名称:</label>--%>
                <%--<label>--%>
                    <%--<input name="accName" id="accName" size="15" class="form-control input-sm"  placeholder="关键字" type="text" />--%>
                <%--</label>--%>

                <%--&nbsp;&nbsp;--%>
                <%--<button id="message-query" type="button" class="btn btn-sm btn-success" ><i class="icon-search"></i> 查询 </button>--%>
                <button type="button" class="btn btn-sm btn-success" onclick="myAdd();" style="float:right" ><i class="icon-search"></i>+新增会员</button>
            </div>
        </div>
    </div>
</div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">
                    <table id="check-table" class="table table-bordered table-hover" style="overflow-y:auto; border-spacing: 0px;">
                        <thead>
                        <tr role="row" style="background-color: #f5f5f5;">
                            <th style="width: 100px;">会员ID</th>
                            <th style="width: 200px;">会员名称</th>
                            <th style="width: 100px;">性别</th>
                            <th style="width: 150px;">手机号码</th>
                            <%--<th style="width: 100px;">所属群组</th>--%>
                            <th style="width: 150px;">创建时间</th>
                            <th style="width: 150px;">更新时间</th>
                            <th style="width: 150px;">操作</th>
                        </tr>
                        </thead>
                        <tbody role="alert" aria-live="polite" aria-relevant="all">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<div id="brand-edit" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" id="brand-content">
        </div>
    </div>
</div>
<!-- /.main-container-inner -->
<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="icon-double-angle-up icon-only bigger-110"></i>
</a>

<!-- /.main-container -->
<script id="tpl" type="text/x-handlebars-template">
    {{#each func}}
    <button type="button" class="btn btn-{{this.type}} btn-xs" onclick="{{this.fn}}">{{this.name}}</button>
    {{/each}}
</script>
<script>
    $(function () {
        //initGroup();
//        setTimeout(function () {
//            init();
//        }, 1000);
        init();
    });
    <%--var mapType = {};--%>
    <%--function initGroup() {--%>
        <%--$.ajax({--%>
            <%--type: "POST",--%>
            <%--url: "<%=basePath%>rest/admin/sms/getAllGroup",--%>
            <%--traditional: true,--%>
            <%--dataType: "json",--%>
            <%--data:{"state":0},--%>
            <%--cache: false,--%>
            <%--success: function (data) {--%>
                <%--if (data.code==0) {--%>
                    <%--var info = data.t;--%>
                    <%--for (var i=0; i < info.length; i++) {--%>
                        <%--var id = info[i].id;--%>
                        <%--var name = info[i].name;--%>
                        <%--$("#groupId").append("<option value="+id+">"+name+"</option>");--%>
                        <%--mapType[id] = name;--%>
                    <%--}--%>
                <%--}--%>
            <%--},--%>
            <%--error: function (error) {--%>
                <%--alert("error");--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>

    <%--function initAddGroup() {--%>
        <%--$.ajax({--%>
            <%--type: "POST",--%>
            <%--url: "<%=basePath%>rest/admin/sms/getAllGroup",--%>
            <%--traditional: true,--%>
            <%--dataType: "json",--%>
            <%--data:{"state":1},--%>
            <%--cache: false,--%>
            <%--success: function (data) {--%>
                <%--if (data.code==0) {--%>
                    <%--var info = data.t;--%>
                    <%--for (var i=0; i < info.length; i++) {--%>
                        <%--//$("#groupId").append("<option value="+info[i].id+">"+info[i].name+"</option>");--%>
                        <%--$("#addGroupId").append("<option value="+info[i].id+">"+info[i].name+"</option>");--%>
                    <%--}--%>
                <%--}--%>
            <%--},--%>
            <%--error: function (error) {--%>
                <%--alert("error");--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>

    function init() {
        $.init.initTemplate('tpl');
        var table = $.smsAccount.initTables("check-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    }

    function myAdd(){
        var groupId = $("#groupId").val();
        var groupName = $("#groupName").val();
        $.ajax({
            type: "GET",
            url: "<%=basePath%>rest/admin/sms/addAccountPage?groupId="+groupId+"&groupName="+groupName,
            traditional: true,
            dataType: "html",
            cache: false,
            success: function (data) {
                $("#brand-content").replaceWith(data);
                //$("#brand-edit").modal("show");
                $("#brand-edit").modal({backdrop:"static"});
                initAddGroup();
            },
            error: function (error) {
                alert(error.message);
            }
        });
    }

    function edit(id){
        var groupId = $("#groupId").val();
        var groupName = $("#groupName").val();
        $.ajax({
            type: "GET",
            url: "<%=basePath%>rest/admin/sms/editAccountPage?id="+id+"&groupId="+groupId+"&groupName="+groupName,
            traditional: true,
            dataType: "html",
            cache: false,
            success: function (data) {
                $("#brand-content").replaceWith(data);
                //$("#brand-edit").modal("show");
                $("#brand-edit").modal({backdrop:"static"});
                initAddGroup();
            },
            error: function (error) {
                alert(error.message);
            }
        });
    }

    function del(id) {
        if(!confirm("确定要删除记录【"+id+"】"))
            return;
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/sms/delAccount",
            traditional: true,
            dataType: "json",
            data: {"id":id},
            cache: false,
            success: function (data) {
                if (data.code==0)
                    table.ajax.reload();
                else
                    alert(data.desc);
            },
            error: function (error) {
                alert("删除失败");
            }
        });
    }

</script>