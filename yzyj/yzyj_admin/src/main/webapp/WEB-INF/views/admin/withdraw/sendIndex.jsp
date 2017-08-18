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
            <li class="active">下发提现记录</li>
        </ul>
    </div>
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                    <label>云付通提现编号:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="云付通提现编号" aria-controls="dataTables-example" name="yftNumber" value=""/>
                    </label>
                    <label>云付通帐号:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="云付通帐号" aria-controls="dataTables-example" name="loginName" value=""/>
                    </label>
                    <label>下发时间:</label>
                    <label>
                        <input name="sdate" id="stratTime" size="9" class="form-control input-sm"  placeholder="起止时间" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{M:3});}'})" />
                    </label>&nbsp;-&nbsp;
                    <label>
                        <input  name="edate" id="endTime" size="10" class="form-control input-sm"  placeholder="结束时间" type="text"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'stratTime\',{d:0});}'})" />
                    </label>
                    <label>账号名称:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="账号名称" aria-controls="dataTables-example" name="accountName" value=""/>
                    </label>
                    <label>流水号:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="流水号" aria-controls="dataTables-example" name="orderLogNo" value=""/>
                    </label>
                    <button id="message-query" type="button" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
                    <button type="button" onclick="exportExcel()" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 导出EXCEL </button>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <h4>累计提现:<span style="color: red">${allWithdraw}</span>元</h4>
            <h4>累计总下发:<span style="color: red">${allSend}</span>元</h4>
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
                                    <th style="width: 80px;">序号</th>
                                    <th style="width: 100px;">提现编号</th>
                                    <th style="width: 80px;">云账号</th>
                                    <th style="width: 80px;">提现金额(元)</th>
                                    <th style="width: 140px;">账户号</th>
                                    <th style="width: 80px;">账户名称</th>
                                    <th style="width: 160px;">开户行</th>
                                    <th style="width: 100px;">申请时间</th>
                                    <th style="width: 100px;">审批时间</th>
                                    <th style="width: 100px;">下发时间</th>
                                    <th style="width: 80px;">下发金额(元)</th>
                                    <th style="width: 80px;">流水号</th>
                                    <th style="width: 80px;">商户号</th>
                                    <th style="width: 80px;">终端号</th>
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
        var table = $.sendPosWithdrawRecord.initTables("check-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });

    function exportExcel(){
        var yftNumber = $("input[name='yftNumber']").val();
        var loginName = $("input[name='loginName']").val();
        var sdate = $("input[name='sdate']").val();
        var edate = $("input[name='edate']").val();
        var accountName = $("input[name='accountName']").val();
        var orderLogNo = $("input[name='orderLogNo']").val();

        location.href = "<%=basePath%>rest/admin/export/exportSendExcel?yftNumber="+yftNumber+
                "&loginName="+loginName+
                "&orderLogNo="+orderLogNo+
                "&sdate="+sdate+
                "&edate="+edate+
                "&accountName="+accountName;

    };
</script>