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
                <a href="#">财务模块</a>
            </li>
            <li class="active">POS机订单明细</li>
        </ul>
    </div>
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                        <label>订单编号:</label>
                        <label>
                            <input type="search" class="form-control input-sm" placeholder="订单编号" aria-controls="dataTables-example" name="orderNumber" value=""/>
                        </label>
                        <label>商户号:</label>
                        <label>
                            <input type="search" class="form-control input-sm" placeholder="商户号" aria-controls="dataTables-example" name="lklMerchantCode" value=""/>
                        </label>
                        <label>商户云帐号:</label>
                        <label>
                            <input type="search" class="form-control input-sm" placeholder="商户云帐号" aria-controls="dataTables-example" name="merchantYunId" value=""/>
                        </label>
                        <label>付款者云帐号:</label>
                        <label>
                            <input type="search" class="form-control input-sm" placeholder="付款者云帐号" aria-controls="dataTables-example" name="yunId" value=""/>
                        </label>
                        <label>创单时间:</label>
                        <label>
                            <input name="sdate" id="stratTime" size="9" class="form-control input-sm"  placeholder="起止时间" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{M:3});}'})" />
                        </label>&nbsp;-&nbsp;<label>
                            <input  name="edate" id="endTime" size="10" class="form-control input-sm"  placeholder="结束时间" type="text"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'stratTime\',{d:0});}'})" />
                        </label>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
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
    <div class="row">
        <div class="col-xs-12">
            <h4>历史刷卡总额:<span style="color: red">${skCount}</span>元</h4>
            <h4>历史扫码总额:<span style="color: red">${smCount}</span>元</h4>
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
                                    <th>商户号</th>
                                    <th>商户名称</th>
                                    <th>商户云帐号</th>
                                    <th>支付者云帐号</th>
                                    <th>金额(元)</th>
                                    <th>订单类型</th>
                                    <th style="width: 100px;">支付状态</th>
                                    <th style="width: 150px;">支付时间</th>
                                    <th>支付方式</th>
                                    <th>接收</th>
                                    <th>返回状态</th>
                                    <th>操作</th>
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
        var table = $.posOrder.initTables("user-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });

    function detail(orderNumber){
        window.location="<%=basePath%>rest/admin/posOrder/orderDetail?orderNumber="+orderNumber;
    };

    function exportExcel(){
        var orderNumber = $("input[name='orderNumber']").val();
        var lklMerchantCode = $("input[name='lklMerchantCode']").val();
        var merchantYunId = $("input[name='merchantYunId']").val();
        var sdate = $("input[name='sdate']").val();
        var edate = $("input[name='edate']").val();
        var orderType = $("select[name='orderType']").val();
        var payState = $("select[name='payState']").val();
        var payType = $("select[name='payType']").val();

        location.href = "<%=basePath%>rest/admin/export/exportOrderExcel?orderNumber="+orderNumber+
                "&lklMerchantCode="+lklMerchantCode+
                "&merchantYunId="+merchantYunId+
                "&sdate="+sdate+
                "&edate="+edate+
                "&orderType="+orderType+
                "&payState="+payState+
                "&payType="+payType;

    };
</script>