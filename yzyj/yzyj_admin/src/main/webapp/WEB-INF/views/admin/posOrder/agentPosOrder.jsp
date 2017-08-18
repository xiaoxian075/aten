<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                <a href="#">财务模块</a>
            </li>
            <li class="active">POS机刷卡明细</li>
        </ul>
    </div>
    <input type="hidden" id="agentUnique" name="agentUnique" value="${agentUnique}" />
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                    <label>设备编码:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="设备编码" aria-controls="dataTables-example" name="machineCode" value=""/>
                    </label>
                    <label>商户云帐号:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="商户云帐号" aria-controls="dataTables-example" name="merchantYunId" value=""/>
                    </label>
                    <label>创单时间:</label>
                    <label>
                        <input name="sdate" id="stratTime" size="9" class="form-control input-sm"  placeholder="起止时间" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{M:3});}'})" />
                    </label>&nbsp;-&nbsp;<label>
                    <input  name="edate" id="endTime" size="10" class="form-control input-sm"  placeholder="结束时间" type="text"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'stratTime\',{d:0});}'})" />
                </label>
                    <label>订单类型:</label>
                    <label>
                        <select class="form-control input-sm" name="orderType">
                            <option value="" selected="selected">请选择</option>
                            <option value="1">POS机付款</option>
                            <option value="2">云支付付款</option>
                        </select>
                    </label>
                    <label>支付状态：</label>
                    <label>
                        <select class="form-control input-sm" name="payState">
                            <option value="" selected="selected">请选择</option>
                            <option value="0">未付款</option>
                            <option value="1">已付款</option>
                        </select>
                    </label>
                    <label>支付方式：</label>
                    <label>
                        <select class="form-control input-sm" name="payType">
                            <option value="" selected="selected">请选择</option>
                            <option value="00">借记卡</option>
                            <option value="01">贷记卡</option>
                            <option value="Y01">云支付扫码</option>
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
                        <table id="user-table" class="table table-bordered table-hover" style="overflow-y:auto; border-spacing: 0px;">
                            <thead>
                                <tr role="row" style="background-color: #f5f5f5;">
                                    <th style="width: 150px;">订单编号</th>
                                    <th style="width: 150px;">创建订单时间</th>
                                    <th>设备编码</th>
                                    <th>设备名称</th>
                                    <th>商户名称</th>
                                    <th>商户云支付账号</th>
                                    <th>实付金额(元)</th>
                                    <th>订单类型</th>
                                    <th>支付状态</th>
                                    <th style="width: 150px;">支付时间</th>
                                    <th>支付方式</th>
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
        var table = $.agentPosOrder.initTables("user-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });
    function edit(id){
        window.location="<%=basePath%>rest/admin/agencyPerson/edit?id="+id;
    }
    function editSatuts(id,state){
        var content;
        if (state == '1') {
            content = "是否启用当前用户"
        } else {
            content = "是否禁用当前用户"
        }
        var r = confirm(content);
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/agencyPerson/editStatus",
                traditional: true,
                data: {id: id, status: state},
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

    function exportExcel(){
        var machineCode = $("input[name='machineCode']").val();
        var merchantYunId = $("input[name='merchantYunId']").val();
        var sdate = $("input[name='sdate']").val();
        var edate = $("input[name='edate']").val();
        var orderType = $("select[name='orderType']").val();
        var payState = $("select[name='payState']").val();
        var payType = $("select[name='payType']").val();
        var agentUnique = $("input[name='agentUnique']").val();

        location.href = "<%=basePath%>rest/admin/export/exportAgentOrderExcel?machineCode="+machineCode+
                "&merchantYunId="+merchantYunId+
                "&agentUnique="+agentUnique+
                "&sdate="+sdate+
                "&edate="+edate+
                "&orderType="+orderType+
                "&payState="+payState+
                "&payType="+payType;

    };
</script>