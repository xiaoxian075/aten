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
                <a href="#">提现管理</a>
            </li>
            <li class="active">商户提现记录</li>
        </ul>
    </div>
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                    <label>云付通帐号:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="云付通帐号" aria-controls="dataTables-example" name="loginName" value=""/>
                    </label>
                    <label>审批时间:</label>
                    <label>
                        <input name="sdate" id="stratTime" size="9" class="form-control input-sm"  placeholder="起止时间" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{M:3});}'})" />
                    </label>&nbsp;-&nbsp;
                    <label>
                        <input  name="edate" id="endTime" size="10" class="form-control input-sm"  placeholder="结束时间" type="text"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'stratTime\',{d:0});}'})" />
                    </label>
                    <label>账号名:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="账号名" aria-controls="dataTables-example" name="accountName" value=""/>
                    </label>
                    <button id="message-query" type="button" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
                    <button type="button" class="btn btn-default" style="background-color:#f4f4f4;" onclick="importExcel()">导入提现</button>
                    <h4><span style="color: red">记录是云付通那边已经审批通过才能下发到拉卡拉商户号</span></h4>
                </div>
            </div>
        </div>
    </div>
   <%-- <div class="row">
        <div class="col-xs-12">
            <h4>已提现总额:<span style="color: red">${count}</span>元</h4>
        </div>
    </div>--%>
    <div class="row" style="overflow-y:auto;">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-body">
                    <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">
                        <table id="check-table" class="table table-bordered table-hover" style="overflow-y:auto; border-spacing: 0px;">
                            <thead>
                                <tr role="row" style="background-color: #f5f5f5;">
                                    <th style="width: 80px;">序号</th>
                                    <th style="width: 100px;">云付通账号</th>
                                    <th style="width: 80px;">提现金额(元)</th>
                                    <th style="width: 160px;">账户号</th>
                                    <th style="width: 80px;">账户名称</th>
                                    <th style="width: 160px;">开户行</th>
                                    <th style="width: 100px;">申请时间</th>
                                    <th style="width: 100px;">审批时间</th>
                                    <th style="width: 80px;">操作</th>
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
        var table = $.posWithdrawRecord.initTables("check-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });

    function isSend(pid){
        var r = confirm("确认要下发提现记录到拉卡拉?");
        if (r) {
            //盖住，防止重复下发
            $.fn.jqLoading({ height: 50, width: 240, text: "正在下发，请耐心等待....", backgroundImage:"<%=basePath%>/images/loading.gif"});
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/withdraw/isSend",
                traditional: true,
                data: {pid: pid},
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.success) {
                        alert("下发成功");
                        $.fn.jqLoading("destroy");
                        table.ajax.reload();
                    }else{
                        alert(data.message);
                        $.fn.jqLoading("destroy");
                        table.ajax.reload();
                    }
                },
                error: function (error) {
                    $.fn.jqLoading("destroy");
                    alert("操作失败");
                }
            });
        }
    };

    function deleteSend(pid){
        var r = confirm("确认要删除提现记录?");
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/withdraw/delete",
                traditional: true,
                data: {pid: pid},
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.success) {
                        alert("删除成功");
                        table.ajax.reload();
                    }else{
                        alert(data.message);
                        table.ajax.reload();
                    }
                },
                error: function (error) {
                    alert("操作失败");
                }
            });
        }
    }

    function importExcel(){
        $.ajax({
            type: "GET",
            url: "<%=basePath%>rest/admin/withdraw/showBrand",
            traditional: true,
            dataType: "html",
            cache: false,
            success: function (data) {
                $("#brand-content").replaceWith(data);
                $("#brand-edit").modal("show");
            },
            error: function (error) {
                alert("导入失败");
            }
        });
    }
</script>