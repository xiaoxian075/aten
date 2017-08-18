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
        <li class="active">产品管理</li>
    </ul>
</div>
<div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_length" id="dataTables-example_length">
                <label>
                    <input type="search" class="form-control input-sm" placeholder="标题" aria-controls="dataTables-example" id="title" value=""/>
                </label>
                <label>
                    <span style="width:200px;">产品类型</span><select class="form-control input-sm" id="type"  >
                        <option value="">请选择</option>
                        <c:forEach items="${product_info_type}" var="ls">
                            <option value="${ls.dictBh}">${ls.dictMc}</option>
                        </c:forEach>
                    </select>
                </label>
                <button id="message-query" type="button" class="btn btn-sm btn-success" ><i class="icon-search"></i> 查询 </button>
                <a href="#" class="btn btn-sm btn-success" id="add"><i  class="icon-plus"></i> 新增</a>
            </div>
        </div>
    </div>
</div>
<div></div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">
                    <table id="user-table" class="table table-bordered table-hover" style="overflow-y:auto; border-spacing: 0px;">
                        <thead>
                        <tr role="row" style="background-color: #f5f5f5;">
                            <th>标题</th>
                            <th>类型</th>
                            <th>排序</th>
                            <th>创建时间</th>
                            <th>链接</th>
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
        var table = $.dynamicInfo.initTables("user-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });
    $("#add").click(function(){
        edit("");
    })

    function edit(id) {
        window.location.href="<%=basePath%>rest/admin/productInfo/edit?id="+id;
    }
    function del(id){
        if (confirm("确定要删除数据吗?删除后将无法还原。")) {
            $.ajax({
                url: "<%=basePath%>rest/admin/productInfo/delete",
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