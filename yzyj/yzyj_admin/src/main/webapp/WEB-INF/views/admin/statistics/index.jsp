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
            <li class="active">历史统计</li>
        </ul>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <h4><span style="color: red">以下统计全部截止到昨天的交易</span></h4>
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
                                    <th colspan="8" scope="col">信息统计表(单位：元)</th>
                                </tr>
                                <tr role="row" style="background-color: #f5f5f5;">
                                    <th style="width: 30px;">统计类目</th>
                                    <th style="width: 80px;">27账号总收益</th>
                                    <th style="width: 80px;">云智公司总收益</th>
                                    <th style="width: 80px;">代理人总收益</th>
                                    <th style="width: 80px;">POS机刷卡总金额</th>
                                    <th style="width: 80px;">POS机刷卡总笔数</th>
                                    <th style="width: 80px;">POS机扫码总金额</th>
                                    <th style="width: 80px;">POS机扫码总笔数</th>
                                </tr>
                            </thead>
                            <tbody role="alert" aria-live="polite" aria-relevant="all">
                            <tr class="text-c">
                                <td>总数</td>
                                <td><span style="color: red">${yzCount}</span></td>
                                <td><span style="color: red">${yzCountQFZY}</span></td>
                                <td><span style="color: red">${agentCount}</span></td>
                                <td><span style="color: red">${skCount}</span></td>
                                <td><span style="color: red">${skCountBS}</span></td>
                                <td><span style="color: red">${smCount}</span></td>
                                <td><span style="color: red">${smCountBS}</span></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length1" id="dataTables-example_length">
                    <label>按日查询:</label>
                    <label>
                        <input name="strDay" id="strDay" size="9" class="form-control input-sm" type="text" onfocus="WdatePicker({errDealMode:1,dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d-1}'})">
                    </label>
                        <button id="message-query1" onclick="_submit_message_query1()" type="button" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="overflow-y:auto;">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-body">
                    <div id="DataTables_Table_0_wrapper1" class="dataTables_wrapper" role="grid">
                        <table id="check-table1" class="table table-bordered table-hover" style="overflow-y:auto; border-spacing: 0px;">
                            <thead>
                            <tr>
                                <th colspan="8" scope="col" id="dayTitle">信息统计表(单位：元)</th>
                            </tr>
                            <tr role="row" style="background-color: #f5f5f5;">
                                <th style="width: 30px;">统计类目</th>
                                <th style="width: 80px;">27账号总收益</th>
                                <th style="width: 80px;">云智公司总收益</th>
                                <th style="width: 80px;">代理人总收益</th>
                                <th style="width: 80px;">POS机刷卡总金额</th>
                                <th style="width: 80px;">POS机刷卡总笔数</th>
                                <th style="width: 80px;">POS机扫码总金额</th>
                                <th style="width: 80px;">POS机扫码总笔数</th>
                            </tr>
                            </thead>
                            <tbody role="alert" aria-live="polite" aria-relevant="all">
                            <tr class="text-c">
                                <td>今日</td>
                                <td id="dayYzCount"></td>
                                <td id="dayYzCountQFZY"></td>
                                <td id="dayAgentCount"></td>
                                <td id="daySkCount"></td>
                                <td id="daySkCountBS"></td>
                                <td id="daySmCount"></td>
                                <td id="daySmCountBS"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="dataTables-example_wrapper2" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length2">
                    <label>按月查询:</label>
                    <label>
                        <input name="strMonth" id="strMonth" size="9" class="form-control input-sm" type="text" onfocus="WdatePicker({errDealMode:1,dateFmt:'yyyy-MM',minDate:'2016-12',maxDate:'%y-${M}'})">
                    </label>
                    <button id="message-query2" type="button" onclick="_submit_message_query2()" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="overflow-y:auto;">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-body">
                    <div id="DataTables_Table_0_wrapper2" class="dataTables_wrapper" role="grid">
                        <table id="check-table2" class="table table-bordered table-hover" style="overflow-y:auto; border-spacing: 0px;">
                            <thead>
                            <tr>
                                <th colspan="8" scope="col" id="monthTitle">信息统计表(单位：元)</th>
                            </tr>
                            <tr role="row" style="background-color: #f5f5f5;">
                                <th style="width: 30px;">统计类目</th>
                                <th style="width: 80px;">27账号总收益</th>
                                <th style="width: 80px;">云智公司总收益</th>
                                <th style="width: 80px;">代理人总收益</th>
                                <th style="width: 80px;">POS机刷卡总金额</th>
                                <th style="width: 80px;">POS机刷卡总笔数</th>
                                <th style="width: 80px;">POS机扫码总金额</th>
                                <th style="width: 80px;">POS机扫码总笔数</th>
                            </tr>
                            </thead>
                            <tbody role="alert" aria-live="polite" aria-relevant="all">
                            <tr class="text-c">
                                <td>本月</td>
                                <td id="monthYzCount"></td>
                                <td id="monthYzCountQFZY"></td>
                                <td id="monthAgentCount"></td>
                                <td id="monthSkCount"></td>
                                <td id="monthSkCountBS"></td>
                                <td id="monthSmCount"></td>
                                <td id="monthSmCountBS"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
    function _submit_message_query1(){
        var date = $("#strDay").val();
        if(date == "" ){
            alert("请选择查询日期");
            return false;
        }
        var type = 1;
        var data = {"dayTime":date,"type":type};
        $.ajax({
            type: "POST",
            data:data,
            url : getRootPath() + "/rest/admin/statistics/getCountByType",
            dataType: "json",
            success: (function(data){
                if(data.success){
                    $("#dayTitle").text("["+date+"] 信息统计表(单位:元)");
                    $("#dayYzCount").text((data.data.yzCount/100).toFixed(2));
                    $("#dayYzCountQFZY").text((data.data.yzCountQFZY/100).toFixed(2));
                    $("#dayAgentCount").text((data.data.agentCount/100).toFixed(2));
                    $("#daySkCount").text((data.data.skCount/100).toFixed(2));
                    $("#daySkCountBS").text(data.data.skCountBS);
                    $("#daySmCount").text((data.data.smCount/100).toFixed(2));
                    $("#daySmCountBS").text(data.data.smCountBS);
                }else{
                    alert(data.message);
                }
            }),
            error: function (error) {
                alert("操作失败");
            }
        });
    }
    function _submit_message_query2(){
        var date = $("#strMonth").val();
        if(date == "" ){
            alert("请选择查询月份");
            return false;
        }
        var type = 2;
        var data = {"monthTime":date,"type":type};
        $.ajax({
            type: "POST",
            data:data,
            url : getRootPath() + "/rest/admin/statistics/getCountByType",
            dataType: "json",
            success: (function(data){
                if(data.success){
                    $("#monthTitle").text("["+date+"] 信息统计表(单位:元)");
                    $("#monthYzCount").text((data.data.yzCount/100).toFixed(2));
                    $("#monthYzCountQFZY").text((data.data.yzCountQFZY/100).toFixed(2));
                    $("#monthAgentCount").text((data.data.agentCount/100).toFixed(2));
                    $("#monthSkCount").text((data.data.skCount/100).toFixed(2));
                    $("#monthSkCountBS").text(data.data.skCountBS);
                    $("#monthSmCount").text((data.data.smCount/100).toFixed(2));
                    $("#monthSmCountBS").text(data.data.smCountBS);
                }else{
                    alert(data.message);
                }
            }),
            error: function (error) {
                alert("操作失败");
            }
        });
    }
</script>
</html>
