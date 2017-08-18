<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <a href="#">统计管理</a>
        </li>
        <li class="active">代理补贴统计</li>
    </ul>
</div>
<div class="row">
    <div class="col-xs-12">
        <h4><span style="color: red">以下统计全部截止到昨天的交易</span></h4>
    </div>
</div>
<div id="dataTables-example_wrapper1" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_length" id="dataTables-example_length1">
                <label>云付通帐号:</label>
                <label>
                    <input type="search" class="form-control input-sm" placeholder="云付通帐号" aria-controls="dataTables-example" name="yunPayLoginName" value=""/>
                </label>
                <%--<label>时间:</label>--%>
                <%--<label id="month">--%>
                    <%--<input name="strMonth" id="strMonth" size="9" class="form-control input-sm" type="text" onfocus="WdatePicker({errDealMode:1,dateFmt:'yyyy-MM',minDate:'2016-12',maxDate:'%y-${M}'})">--%>
                <%--</label>--%>
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
                    <table id="check-table" class="table table-bordered table-hover" style="overflow-y:auto; border-spacing: 0px;">
                        <thead>
                        <tr>
                            <th colspan="8" scope="col">代理统计表(单位：元)</th>
                        </tr>
                        <tr role="row" style="background-color: #f5f5f5;">
                            <th style="width: 30px;">序号</th>
                            <th style="width: 30px;">代理人</th>
                            <th style="width: 30px;">云付通账号</th>
                            <th style="width: 80px;">历史累计补贴总额</th>
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
<script type="text/javascript">
    $(function () {
        $.init.initTemplate('tpl');
        var table = $.agentStatisticsBt.initTables("check-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });
</script>
