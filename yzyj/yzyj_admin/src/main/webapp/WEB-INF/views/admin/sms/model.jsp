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
        <li class="active">模板管理</li>
    </ul>
</div>
<div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_length" id="dataTables-example_length">
                <label>模板内容:</label>
                <label>
                    <input name="tpl_content" id="tpl_content" size="15" class="form-control input-sm"  placeholder="关键字" type="text" />
                </label>
                &nbsp;&nbsp;
                <label>审核状态:</label>
                <label>
                    <select class="form-control input-sm" name="check_status" id="check_status">
                        <option value="0" selected="selected">请选择</option>
                        <option value="1">审核通过</option>
                        <option value="2">审核中</option>
                        <option value="3">审核失败</option>
                    </select>
                </label>
                &nbsp;&nbsp;
                <button id="message-query" type="button" class="btn btn-sm btn-success" ><i class="icon-search"></i> 查询模板 </button>
                <button type="button" class="btn btn-sm btn-success" onclick="myAdd();" style="float:right" ><i class="icon-search"></i> 新增模板 </button>
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
                            <th style="width: 80px;">模板ID</th>
                            <th style="width: 150px;">短信主题</th>
                            <th style="width: 150px;">短信签名</th>
                            <th style="width: 200px;">模板内容</th>
                            <th style="width: 100px;">状态</th>
                            <th style="width: 200px;">未通过原因</th>
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
       init();
    });

    function init() {
        $.init.initTemplate('tpl');
        var table = $.smsModel.initTables("check-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    }

    function myAdd(){
        $.ajax({
            type: "GET",
            url: "<%=basePath%>rest/admin/sms/addModelPage",
            traditional: true,
            dataType: "html",
            cache: false,
            success: function (data) {
                $("#brand-content").replaceWith(data);
                //$("#brand-edit").modal("show");
                $("#brand-edit").modal({backdrop:"static"}); /*设置为static后可以防止不小心点击其他区域是弹出框消失*/
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
            url: "<%=basePath%>rest/admin/sms/delModel",
            traditional: true,
            dataType: "json",
            data: {"id":id},
            cache: false,
            success: function (data) {
                //data = JSON.parse(data);
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

    function edit(id) {
        $.ajax({
            type: "GET",
            url: "<%=basePath%>rest/admin/sms/editModelPage",
            traditional: true,
            dataType: "html",
            data:{"id":id},
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

    function synModel(id) {
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/sms/synModel",
            traditional: true,
            dataType: "json",
            data: {"id":id},
            cache: false,
            success: function (data) {
                //data = JSON.parse(data);
                if (data.code==0)
                    table.ajax.reload();
                else
                    alert(data.desc);
            },
            error: function (error) {
                alert("更新失败");
            }
        });
    }
</script>