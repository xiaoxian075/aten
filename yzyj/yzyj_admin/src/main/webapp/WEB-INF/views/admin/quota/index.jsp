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
                <a href="#">额度管理</a>
            </li>
            <li class="active">额度列表</li>
        </ul>
    </div>
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                     <a href="#" class="btn btn-sm btn-success" id="init"><i  class="icon-plus"></i> 初始化额度</a>
                    <span style="atsc-nav-right: full;float:right;margin-right: 15px;font-size: 15px;" > <span style="color:red"></span>单位：元</span>
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
                                    <th>组别</th>
                                    <th>借记卡单笔</th>
                                    <th>借记卡当天</th>
                                    <th>借记卡当月</th>
                                    <th>贷记卡单笔</th>
                                    <th>贷记卡当天</th>
                                    <th>贷记卡当月</th>
                                    <th>贷记卡单笔超额</th>
                                    <th>贷记卡当天超额</th>
                                    <th>贷记卡当月超额</th>
                                    <th>借记卡单笔超额</th>
                                    <th>借记卡当天超额</th>
                                    <th>借记卡当月超额</th>
                                    <th>备注</th>
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
        var table = $.Quota.initTables("user-table", null);
        var msg = $("#msg").val();
        if(msg != ""){
            alert(msg);
        }
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });

    function edit(id){
        window.location="<%=basePath%>rest/admin/quota/edit?id="+id;
    };

    $("#init").click(function(){
        $.ajax({
            url: "<%=basePath%>rest/admin/quota/init",
            type: "post",
            dataType: "json",
            success: function (json) {
                alert(json.message);
            }
        });
    })
</script>