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
            <li class="active">分账提现列表</li>
        </ul>
    </div>
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                    <label>提现日期起:</label>
                    <label>
                        <input name="sdate" id="sdate" size="15" class="form-control input-sm"  placeholder="提现日期起" type="text" onfocus="WdatePicker({ dateFmt: 'yyyyMMdd' ,maxDate:'%y-%M-{%d-1}'})" />
                    </label>
                    &nbsp;&nbsp; - &nbsp;&nbsp;
                    <label>
                        <input name="edate" id="edate" size="15" class="form-control input-sm"  placeholder="提现日期止" type="text" onfocus="WdatePicker({ dateFmt: 'yyyyMMdd' ,maxDate:'%y-%M-{%d-1}'})" />
                    </label>
                    &nbsp;&nbsp;
                    <button id="message-query" type="button" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
                </div>
            </div>
        </div>
    </div>
    <div id="dataTables-example_wrapper1" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length1">
                    <label>载入日期:</label>
                    <label>
                        <input name="reloadDay" id="reloadDay" size="15" class="form-control input-sm"  placeholder="日期" type="text" onfocus="WdatePicker({ dateFmt: 'yyyyMMdd' ,maxDate:'%y-%M-{%d-1}'})" />
                    </label>
                    &nbsp;&nbsp;
                    <button id="message-query1" type="button" class="btn btn-danger btn-xs" onclick="reloadTiXian()"> 载入数据</button>
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
                                    <th style="width: 160px;">提现日期</th>
                                    <th style="width: 110px;">提现总金额</th>
                                    <th style="width: 100px;">提现笔数</th>
                                    <th style="width: 150px;">平台到账总金额</th>
                                    <th style="width: 300px;">商户到账总金额</th>
                                    <th style="width: 300px;">更新时间</th>
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
        var table = $.TiXianRecord.initTables("check-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });

    function reloadTiXian(){
        var reloadDay = $("#reloadDay").val();
        if(reloadDay == ''){
            alert("请先选择要载入数据的时间");
            return false;
        }
        var r = confirm("确认载入【"+reloadDay+"】的数据?");
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/lklBill/reloadTiXian",
                traditional: true,
                data: {reloadDay: reloadDay},
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.success) {
                        alert("载入成功");
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
</script>