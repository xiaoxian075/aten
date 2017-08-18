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
                <form class="form-horizontal" role="form" id="editForm" onsubmit="return check()" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/deviceList/edit">
                    <c:choose>
                        <c:when test="${not empty info.id}">
                            <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                        </c:when>
                    </c:choose>
                    <input type="hidden" id="agentUnique" name="agentUnique" value="${info.agentUnique}" />
                    <div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">设备名称</label>
                            <div class="col-sm-3">
                                <input name="deviceName" id="deviceName" type="text" value="${info.deviceName }"  class="form-control input-sm" placeholder="必填">
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
                                <select class="form-control" name="deviceType">
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
                                <input name="merchantName" id="merchantName" type="text" value="${info.merchantName }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">商家云支付账号</label>
                            <div class="col-sm-3">
                                <input name="merchantYunPayAccount"  id="merchantYunPayAccount" type="text"  value="${info.merchantYunPayAccount }"  class="form-control input-sm" placeholder="必填" >
                            </div>
                            <div class="col-sm-5">
                                <input class="btn btn-success" type="button" value="检查账号信息" onclick="getInfo()"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">商家联系电话</label>
                            <div class="col-sm-3">
                                <input name="merchantPhone"  id="merchantPhone"  type="text"  value="${info.merchantPhone }"  class="form-control input-sm" placeholder="必填">
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
        if ($("#merchantName").val() == "") {
            alert("商户姓名不能为空");
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
</script>