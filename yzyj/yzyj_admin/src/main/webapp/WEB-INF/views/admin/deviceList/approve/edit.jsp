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
            <a href="#">代理管理</a>
        </li>
        <li class="active">添加POS机</li>
    </ul>
</div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <form class="form-horizontal" role="form" id="editForm" onsubmit="return check()" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/deviceList/editApproval">
                    <c:choose>
                        <c:when test="${not empty info.id}">
                            <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                        </c:when>
                    </c:choose>
                    <div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">代理人</label>
                            <div class="col-sm-3">
                                <select class="chosen-select form-control" name="agentUnique" id="agentUnique" data-placeholder="请选择代理人">
                                    <option value=""></option>
                                    <c:forEach items="${agencyList}" var="agency">
                                        <option value="${agency.agentUnique}" <c:if test="${info.agentUnique==agency.agentUnique}">selected</c:if>>${agency.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">设备名称</label>
                            <div class="col-sm-3">
                                <select class="form-control" name="deviceName" id="deviceName">
                                    <option value="">请选择</option>
                                    <c:forEach items="${deviceName}" var="ls">
                                        <option value="${ls.dictBh}" <c:if test="${info.deviceName eq ls.dictBh}"> selected</c:if>>${ls.dictMc}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">拉卡拉设备编码</label>
                            <div class="col-sm-3">
                                <input name="lklMachineCode" id="lklMachineCode" type="text" value="${info.lklMachineCode }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">拉卡拉激活码</label>
                            <div class="col-sm-3">
                                <input name="lklActivationCode" id="lklActivationCode" type="text" value="${info.lklActivationCode }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">拉卡拉商户号</label>
                            <div class="col-sm-3">
                                <input name="lklMerchantCode" id="lklMerchantCode" type="text" value="${info.lklMerchantCode }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">拉卡拉终端号</label>
                            <div class="col-sm-3">
                                <input name="lklTerminalCode" id="lklTerminalCode" type="text" value="${info.lklTerminalCode }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">设备类型</label>
                            <div class="col-sm-3">
                                <select class="form-control" name="deviceType" id="deviceType">
                                    <option value="">请选择</option>
                                    <c:forEach items="${deviceType}" var="ls">
                                        <option value="${ls.dictBh}" <c:if test="${info.deviceType eq ls.dictBh}"> selected</c:if>>${ls.dictMc}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">商家姓名</label>
                            <div class="col-sm-3">
                                <select class="chosen-select form-control" name="merchantName" id="merchantName" data-placeholder="请选择商家">
                                    <option value=""></option>
                                    <c:if test="${info.id != null}">
                                        <c:forEach items="${business}" var="ls">
                                            <option value="${ls.id},${ls.merchantName}" <c:if test="${info.merchantId eq ls.id}"> selected</c:if>>${ls.merchantName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">商家云支付账号</label>
                            <div class="col-sm-3">
                                <input name="merchantYunPayAccount"  id="merchantYunPayAccount" type="text"  value="${info.merchantYunPayAccount }"  readonly="readonly"  class="form-control input-sm" placeholder="必填" >
                            </div>
                            <div class="col-sm-5">
                                <input class="btn btn-success" type="button" value="检查账号信息" onclick="getInfo()"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">商家联系电话</label>
                            <div class="col-sm-3">
                                <input name="merchantPhone"  id="merchantPhone"  type="text"  value="${info.merchantPhone }"  readonly="readonly" class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-3">
                                <input type="submit" class="btn btn-primary" value="保&nbsp;&nbsp;&nbsp;&nbsp;存"/>
                                <button type="button" class="btn btn-default" style="background-color:#f4f4f4;" type="button" value="返回列表" onclick="history.go(-1)" >返回列表</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">
    function check(){
        if($("#agentUnique").val() == ""){
            alert("请选择代理人");
            return false;
        }
        if ($("#deviceName").val() == "") {
            alert("设备名称不能为空");
            return false;
        }
        if ($("#lklMachineCode").val() == "") {
            alert("设备编码不能为空");
            return false;
        }
        if ($("#lklActivationCode").val() == "") {
            alert("激活码不能为空");
            return false;
        }
        if ($("#lklMerchantCode").val() == "") {
            alert("商户号不能为空！");
            return false;
        }
        if ($("#lklTerminalCode").val() == "") {
            alert("终端号不能为空");
            return false;
        }
        if ($("#deviceType").val() == "") {
            alert("设备类型不能为空");
            return false;
        }
        if ($("#merchantName").val() == "") {
            alert("请选择商户姓名");
            return false;
        }
        if ($("#merchantYunPayAccount").val() == "") {
            alert("商户云支付不能为空");
            return false;
        }
        if ($("#merchantPhone").val() == "") {
            alert("商户联系电话不能为空");
            return false;
        }
        return true;
    };

    function getInfo(){
        var merchantYunPayAccount = $("#merchantYunPayAccount").val()
        if(merchantYunPayAccount == ""){
            alert("请输入云支付账号");
            return;
        }
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/agencyPerson/getInfo",
            traditional: true,
            data: {loginName: merchantYunPayAccount},
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.success) {
                    alert("存在云支付账号");
                }else{
                    alert("不存在云支付账号");
                }
            },
            error: function (error) {
                alert("没找到该云支付账号");
            }
        });
    }
    $("#agentUnique").change(function(){
        var agentUnique = $("#agentUnique").val();
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/business/getBusinessNameByAgentUnique",
            traditional: true,
            data: {agentUnique: agentUnique},
            dataType: "json",
            cache: false,
            success: function (data) {
                $("#merchantName option:not(:first)").remove();
                var selObj = $("#merchantName");
                var dd = data.data;
                for(var i=0;i<dd.length;i++){
                    selObj.append("<option value='" + dd[i].id+","+dd[i].merchantName + "'>" + dd[i].merchantName + "</option>");
                }
                selObj.trigger("chosen:updated");
            },
            error: function (error) {
            }
        });
    })

    $("#merchantName").change(function(){
        var merchantName = $("#merchantName").val();
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/business/getBusinessNameById",
            traditional: true,
            data: {merchantName: merchantName},
            dataType: "json",
            cache: false,
            success: function (data) {
                var dd = data.data;
                $("#merchantYunPayAccount").val(dd.yunPayAccount);
                $("#merchantPhone").val(dd.merchantPersonPhone);
            },
            error: function (error) {
            }
        });
    })
</script>