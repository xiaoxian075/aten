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
            <a href="#">短信服务</a>
        </li>
        <li class="active">群发记录</li>
    </ul>
</div>
<div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_length" id="dataTables-example_length">

                <label>手机号</label>
                <label>
                    <input name="paraMobile" id="paraMobile" size="15" class="form-control input-sm"  placeholder="关键字" type="text" />

                </label>

                &nbsp;&nbsp;
                <label>状态</label>
                <label>
                    <select class="form-control input-sm" name="paraState" id="paraState">
                        <option value=0 selected="selected">请选择</option>
                        <option value=1>成功</option>
                        <option value=2>失败</option>
                    </select>
                </label>

                &nbsp;&nbsp;
                <label>时间</label>
                <label>
                    <input name="paraStartTime" id="paraStartTime" size="19" class="form-control input-sm"  placeholder="起止时间" type="text" onFocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})" />
                -
                    <input name="paraEndTime" id="paraEndTime" size="19" class="form-control input-sm"  placeholder="终止时间" type="text" onFocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})" />
                </label>


                &nbsp;&nbsp;
                <button id="message-query" type="button" class="btn btn-sm btn-success" ><i class="icon-search"></i> 查询 </button>
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
                            <th style="width: 100px;">序号</th>
                            <th style="width: 200px;">手机号码</th>
                            <th style="width: 100px;">发送条数</th>
                            <th style="width: 150px;">费用</th>
                            <th style="width: 100px;">发送状态</th>
                            <th style="width: 300px;">备注</th>
                            <th style="width: 150px;">发送时间</th>
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
        init();
    });

    function init() {
        $.init.initTemplate('tpl');
        var table = $.smsRecord.initTables("check-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    }

</script>