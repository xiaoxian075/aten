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
            <a href="#">设备管理</a>
        </li>
        <li class="active">待审批返现列表</li>
    </ul>
</div>

<input type="hidden" id="agentUnique" value="${info.agentUnique}" />
<input type="hidden" id="id" value="${info.id}" />
<input type="hidden" id="quotaGroup" value="${info.quotaGroup}" />

<div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_length" id="dataTables-example_length">
                <label>拉卡拉商户号:</label>
                <label>
                    <input type="search" class="form-control input-sm" placeholder="拉卡拉商户号" aria-controls="dataTables-example" name="lklMerchantCode" value=""/>
                </label>
                <label>拉卡拉终端号:</label>
                <label>
                    <input type="search" class="form-control input-sm" placeholder="拉卡拉终端号" aria-controls="dataTables-example" name="lklTerminalCode" value=""/>
                </label>
                <label>商家云支付账号:</label>
                <label>
                    <input type="search" class="form-control input-sm" placeholder="商家云支付账号" aria-controls="dataTables-example" name="merchantYunPayAccount" value=""/>
                </label>
                <label>代理人:</label>
                <label>
                    <input type="search" class="form-control input-sm" placeholder="代理人" aria-controls="dataTables-example" name="realName" value=""/>
                </label>
                <button id="message-query" type="button" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
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
                            <th >代理人</th>
                            <th >设备编码</th>
                            <th >激活码</th>
                            <th >拉卡拉设备编码</th>
                            <th >拉卡拉激活码</th>
                            <th >拉卡拉商户号</th>
                            <th >拉卡拉终端号</th>
                            <th >商家姓名</th>
                            <th >商家云支付账号</th>
                            <th >商家联系电话</th>
                            <th >提交时间</th>
                            <th >启用\禁用</th>
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
        var table = $.waitSubsidyList.initTables("user-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });

    function deviceDetail(id){
        window.location="<%=basePath%>rest/admin/deviceList/deviceDetail?id="+id;
    };

    function passSubsidy(id,agentUnique){
        var r = confirm("确认通过设备返现审批?");
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/deviceList/passSubsidy",
                traditional: true,
                data: {id: id,agentUnique: agentUnique},
                dataType: "json",
                cache: false,
                success: function (data) {
                    alert(data.message);
                    table.ajax.reload();
                },
                error: function (error) {
                    alert("操作失败");
                }
            });
        }
    };

    function rejectSubsidy(id,agentUnique){
        var r = confirm("确认驳回设备返现审批?");
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/deviceList/rejectSubsidy",
                traditional: true,
                data: {id: id,agentUnique: agentUnique},
                dataType: "json",
                cache: false,
                success: function (data) {
                    alert(data.message);
                    table.ajax.reload();
                },
                error: function (error) {
                    alert("操作失败");
                }
            });
        }
    }
</script>