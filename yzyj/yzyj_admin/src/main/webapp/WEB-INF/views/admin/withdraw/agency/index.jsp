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
                <a href="#">提现记录</a>
            </li>
            <li class="active">代理人提现列表</li>
        </ul>
    </div>
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                    <c:if test="${type == 1}">
                        <label>提现人:</label>
                        <label>
                            <input type="search" class="form-control input-sm" placeholder="姓名" aria-controls="dataTables-example" name="realName" value=""/>
                        </label>
                        <label>云付通账号:</label>
                        <label>
                            <input type="search" class="form-control input-sm" placeholder="云付通账号" aria-controls="dataTables-example" name="yunPayLoginName" value=""/>
                        </label>
                    </c:if>
                    <label>提现时间:</label>
                        <label>
                            <input name="sdate" id="stratTime" size="9" class="form-control input-sm"  placeholder="起止时间" type="text" onfocus="WdatePicker({maxDate:'%y-%M-%d'})" />
                        </label>&nbsp;-&nbsp;<label>
                        <input  name="edate" id="endTime" size="10" class="form-control input-sm"  placeholder="结束时间" type="text"   onfocus="WdatePicker({maxDate:'%y-%M-%d'})" />
                        </label>
                    <button id="message-query" type="button" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <h4>累计提现总额:<span style="color: red">${count}</span>元</h4>
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
                                    <th style="width: 160px;">提现编号</th>
                                    <th style="width: 80px;">提现人</th>
                                    <th style="width: 80px;">云付通账号</th>
                                    <th style="width: 160px;">提现时间</th>
                                    <th style="width: 80px;">提现金额(元)</th>
                                    <th style="width: 80px;">状态</th>
                                    <th style="width: 80px;">备注</th>
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
        var table = $.WithdrawAgencyRecord.initTables("check-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });
</script>