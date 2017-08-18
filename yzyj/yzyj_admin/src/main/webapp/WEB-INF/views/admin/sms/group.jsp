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
        <li class="active">会员群组管理</li>
    </ul>
</div>
<div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_length" id="dataTables-example_length">
                <label>群组名称：</label>
                <label>
                    <input name="accName" id="accName" size="15" class="form-control input-sm"  placeholder="关键字" type="text" />
                </label>

                &nbsp;&nbsp;
                <button id="message-query" type="button" class="btn btn-sm btn-success" ><i class="icon-search"></i> 查询 </button>
                <button type="button" class="btn btn-sm btn-success" onclick="myAdd();" style="float:right" ><i class="icon-search"></i>+新增群组</button>
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
                            <th style="width: 100px;">群组ID</th>
                            <th style="width: 200px;">群组名称</th>
                            <th style="width: 100px;">群组成员</th>
                            <%--<th style="width: 100px;">状态</th>--%>
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

    <form id="formsh" action="<%=basePath%>rest/admin/sms/account" method="get">
        <input type="hidden" id="groupId" name="groupId"/>
        <input type="hidden" id="groupName" name="groupName"/>
    </form>
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
        init();
    });

    function init() {
        $.init.initTemplate('tpl');
        var table = $.smsGroup.initTables("check-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    }

    function myAdd(){
        $.ajax({
            type: "GET",
            url: "<%=basePath%>rest/admin/sms/addGroupPage",
            traditional: true,
            dataType: "html",
            cache: false,
            success: function (data) {
                $("#brand-content").replaceWith(data);
                //$("#brand-edit").modal("show");
                $("#brand-edit").modal({backdrop:"static"});
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
            url: "<%=basePath%>rest/admin/sms/delGroup",
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

    function enableState(id) {
        if(!confirm("确定要启用记录【"+id+"】"))
            return;
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/sms/enableGroup",
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
                alert("启用失败");
            }
        });
    }

    function limitState(id) {
        if(!confirm("确定要禁用记录【"+id+"】"))
            return;
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/sms/limitGroup",
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
                alert("禁用失败");
            }
        });
    }

    function showAccount(id,name) {
        $("#groupId").val(id);
        $("#groupName").val(name);
        $("#formsh").submit();
    }

</script>