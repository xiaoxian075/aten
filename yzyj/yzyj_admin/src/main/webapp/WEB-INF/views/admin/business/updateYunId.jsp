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
                <a href="#">商户列表</a>
            </li>
            <li class="active">修改账号</li>
        </ul>
    </div>
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                        <label>云支付账号:</label>
                        <label>
                            <input type="search" class="form-control input-sm" placeholder="云支付账号" aria-controls="dataTables-example" name="yunPayAccount"  value=""/>
                        </label>
                    <button id="message-query" type="button" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
                </div>
            </div>
        </div>
        <label>云支付账号修改成:</label>
        <label>
            <input type="search" class="form-control input-sm" placeholder="云支付账号" aria-controls="dataTables-example" id="yunPayAccountUpdate" name="yunPayAccountUpdate"  value=""/>
        </label>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <h4><span style="color: red">有风险，请谨慎操作</span></h4>
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
                                    <th >商户编码</th>
                                    <th >商户名称</th>
                                    <th >法人姓名</th>
                                    <th >营业执照号</th>
                                    <th >商户联系人姓名</th>
                                    <th >账户号</th>
                                    <th >账户名称</th>
                                    <th >云支付账号</th>
                                    <th >添加时间</th>
                                    <th >代理人</th>
                                    <th >操作</th>
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
        var table = $.businessInfo.initTables("user-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });
    function edit(id){
        var yunPayAccountUpdate = $("#yunPayAccountUpdate").val();
        if(yunPayAccountUpdate == ""){
            alert("请填写要修改的云支付账号");
            this.blur();
            return false;
        }
        var r = confirm("你确认要修改该商户云支付账号吗?");
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/business/editBusinessInfo",
                traditional: true,
                data: {id: id,yunPayAccount:yunPayAccountUpdate},
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.success) {
                        alert(data.message);
                        $("input[name='yunPayAccountUpdate']").val("")
                        table.ajax.reload();
                    }else{
                        alert(data.message);
                        $("input[name='yunPayAccountUpdate']").val("")
                        table.ajax.reload();
                    }
                },
                error: function (error) {
                    alert("操作失败");
                }
            });
        }
    };
</script>