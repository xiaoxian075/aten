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
                <a href="#">对账收益</a>
            </li>
            <li class="active">对账列表</li>
        </ul>
    </div>
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                    <label>云付通帐号:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="云付通帐号" aria-controls="dataTables-example" name="agentYPLoginName" value=""/>
                    </label>
                    <label>结算日期:</label>
                    <label>
                        <input name="strSdate" id="stratTime" size="9" class="form-control input-sm"  placeholder="起止时间" type="text" onFocus="WdatePicker({ dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-{%d-1}'})" />
                    </label>&nbsp;-&nbsp;
                    <label>
                        <input name="strEdate" id="endTime" size="10" class="form-control input-sm"  placeholder="结束时间" type="text" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-{%d-1}'})" />
                    </label>
                    <label>代理人:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="代理人" aria-controls="dataTables-example" name="agentName" value=""/>
                    </label>
                    <label>收益类型:</label>
                    <label>
                        <select class="form-control input-sm" name="type">
                            <option value="" selected="selected">请选择</option>
                            <option value="1">收益</option>
                            <option value="2">补贴</option>
                            <option value="3">返现</option>
                        </select>
                    </label>
                    <button id="message-query" type="button" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
                    <button type="button" onclick="exportExcel()" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 导出EXCEL </button>
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
                                    <th style="width: 80px;">序号</th>
                                    <th style="width: 160px;">收益时间</th>
                                    <th style="width: 80px;">结算日期</th>
                                    <th style="width: 80px;">收益金额(元)</th>
                                    <th style="width: 80px;">收益人</th>
                                    <th style="width: 80px;">云支付账号</th>
                                    <th style="width: 80px;">收益类型</th>
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
        var table = $.inComeRecord.initTables("check-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });

    function exportExcel(){
        var agentYPLoginName = $("input[name='agentYPLoginName']").val();
        var strSdate = $("input[name='strSdate']").val();
        var strEdate = $("input[name='strEdate']").val();
        var agentName = $("input[name='agentName']").val();
        var type = $("select[name='type']").val();

        location.href = "<%=basePath%>rest/admin/export/exportInComeExcel?agentYPLoginName="+agentYPLoginName+
                "&sdate="+strSdate+
                "&edate="+strEdate+
                "&agentName="+agentName+
                "&type="+type;

    };
</script>