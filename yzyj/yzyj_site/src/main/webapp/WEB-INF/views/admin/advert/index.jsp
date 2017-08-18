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
            <a href="#">基础管理</a>
        </li>
        <li class="active">申请入驻信息</li>
    </ul>
</div>
<div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_length" id="dataTables-example_length">
                <label>
                    <input type="search" class="form-control input-sm" placeholder="公司名" aria-controls="dataTables-example" id="company" value=""/>
                </label>
                <button id="message-query" type="button" class="btn btn-sm btn-success" ><i class="icon-search"></i> 查询 </button>
                <a href="javascript:void(0)" class="btn btn-sm btn-success" id="add"><i  class="icon-plus"></i> 新增广告</a>
            </div>
        </div>
    </div>
</div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">
                    <table id="user-table" class="table table-bordered table-hover" style="overflow-y:auto; border-spacing: 0px;">
                        <thead>
                        <tr role="row" style="background-color: #f5f5f5;">
                            <th>广告名称</th>
                            <th>类型</th>
                            <th>URL路径</th>
                            <th>所属广告位</th>
                            <th>是否可用</th>
                            <th>操作</th>
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
        $.init.initTemplate('tpl');
        var table = $.advertinfo.initTables("user-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });
    $("#add").click(function(){
        edit("");
    })

    function edit(id) {
        $.ajax({
            type: "GET",
            url: "<%=basePath%>rest/admin/advert/edit",
            traditional: true,
            data:{id:id},
            dataType: "html",
            cache: false,
            success: function (data) {
                $("#brand-content").replaceWith(data);
                $("#brand-edit").modal("show");
            },
            error: function (error) {
                alert(111);
            }
        });
    }
    function editAdvert(id,state){


        var content;
        if (state == '1') {
            content = "是否启用当前广告"
        } else {
            content = "是否禁用用当前广告"
        }
        var r = confirm(content);
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/advert/editAdvert",
                traditional: true,
                data: {id: id, status: state},
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.success) {
                        alert("操作成功");
                        table.ajax.reload();
                    }
                },
                error: function (error) {
                    alert(111);
                }
            });
        }
    }
    function del(id){
        if (confirm("确定要删除数据吗?删除后将无法还原。")) {
            $.ajax({
                url: "<%=basePath%>rest/admin/advert/delete",
                data: {id:id},
                type: "post",
                dataType: "json",
                success: function (json) {
                    alert(json.message);
                    if(json.success) {
                        table.ajax.reload();
                    }
                }
            });
        }
    }
</script>