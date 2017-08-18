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
                <a href="#">轮播管理</a>
            </li>
            <li class="active">列表</li>
        </ul>
    </div>
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                        <label>
                            <input type="search" class="form-control input-sm" placeholder="姓名、云支付帐号" aria-controls="dataTables-example" name="queryBody" id="queryBody" value=""/>
                        </label>
                    <button id="message-query" type="button" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
                    <button type="button" class="btn btn-default" style="background-color:#f4f4f4;" onclick="add()">添加信息</button>
                </div>
            </div>
        </div>
    </div>
    <input type="hidden" id="msg" value="${msg}" />
    <div class="row" style="overflow-y:auto;">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-body">
                    <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">
                        <table id="user-table" class="table table-bordered table-hover" style="overflow-y:auto; border-spacing: 0px;">
                            <thead>
                                <tr role="row" style="background-color: #f5f5f5;">
                                    <th>序号</th>
                                    <th>轮播图片</th>
                                    <th>标题</th>
                                    <th>类型</th>
                                    <th>时间</th>
                                    <th>状态</th>
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
        var table = $.carouselAPP.initTables("user-table", null);
        var msg = $("#msg").val();
        if(msg != ""){
            alert(msg);
        }
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });

    function add(){
        window.location="<%=basePath%>rest/admin/carousel/edit";
    };

    function edit(id){
        window.location="<%=basePath%>rest/admin/carousel/edit?id="+id;
    };

    function del(id){
        if (confirm("确定要删除数据吗?")) {
            $.ajax({
                url: "<%=basePath%>rest/admin/carousel/delete",
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
    };

    function editSatuts(id,state){
        var content;
        if (state == '1') {
            content = "是否启用当前信息"
        } else {
            content = "是否禁用当前信息"
        }
        var r = confirm(content);
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/carousel/editStatus",
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
                    alert("操作失败");
                }
            });
        }
    }
</script>