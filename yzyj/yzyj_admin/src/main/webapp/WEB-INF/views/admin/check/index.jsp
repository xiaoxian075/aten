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
                    <label>结算日期:</label>
                    <label>
                        <input name="checkDay" id="checkDay" size="15" class="form-control input-sm"  placeholder="结算时间" type="text" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd' ,maxDate:'%y-%M-{%d-1}'})" />
                    </label>
                    &nbsp;&nbsp;
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
                                <tr role="row" style="background-color: #f5f5f5;">
                                    <th style="width: 80px;">序号</th>
                                    <th style="width: 160px;">结算日期</th>
                                    <th style="width: 100px;">刷卡笔数</th>
                                    <th style="width: 110px;">刷卡总金额</th>
                                    <th style="width: 150px;">拉卡拉对账状态</th>
                                    <th style="width: 300px;">描述</th>
                                    <th style="width: 100px;">扫码笔数</th>
                                    <th style="width: 110px;">扫码总金额</th>
                                    <th style="width: 150px;">云支付对账状态</th>
                                    <th style="width: 300px;">描述</th>
                                    <th style="width: 150px;">对账时间</th>
                                    <th style="width: 160px;">操作</th>
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
        var table = $.checkRecord.initTables("check-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });
    function detail(checkDay){
        alert("暂未开放");
        return ;
       /* window.location="<%=basePath%>rest/admin/deviceList/index?agentUnique="+agentUnique;*/
    }

    function restart(checkDay,checkUnique){
        var r = confirm("确认要重新对账?");
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/check/restartCheck",
                traditional: true,
                data: {checkDay: checkDay,checkUnique:checkUnique},
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