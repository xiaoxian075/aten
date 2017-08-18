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
            <a href="#">设备管理</a>
        </li>
        <li class="active">设备详情</li>
    </ul>
</div>
<input type="hidden" id="updateBefore" name="updateBefore" value="${detail.quotaGroup }" />
<input type="hidden" id="deviceUnique" name="deviceUnique" value="${detail.deviceUnique }" />
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <form class="form-horizontal" role="form">
                    <div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">额度组别</label>
                            <div class="col-sm-3">
                                <select class="chosen-select form-control" name="quotaGroup" id="quotaGroup">
                                    <option value=""></option>
                                    <c:forEach items="${qGroup}" var="group">
                                        <option value="${group.quotaGroup}" <c:if test="${detail.quotaGroup==group.quotaGroup}">selected</c:if>>${group.note}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">设备编码</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.deviceCode}"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">设备激活码</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.activationCode }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">拉卡拉设备编码</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.lklMachineCode}"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">拉卡拉激活码</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.lklActivationCode }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">拉卡拉商户号</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.lklMerchantCode }"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">拉卡拉终端号</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.lklTerminalCode }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">设备名称</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.deviceName }"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">设备类型</label>
                            <div class="col-sm-3">
                                <input type="text"  value="POS机"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">商家名称</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.merchantName}"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">商户账号</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.merchantYunPayAccount }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">商户联系电话</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.merchantPhone}"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">添加时间</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.strCreateTime }" class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">状态</label>
                            <div class="col-sm-3">
                                <input type="text"  value="已通过"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">启用\禁用</label>
                            <div class="col-sm-3">
                                <select class="chosen-select form-control" name="state" id="state" disabled="disabled">
                                    <c:choose>
                                        <c:when test="${detail.state == '1'}">
                                            <option >启用</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option>禁用</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">返现状态</label>
                            <div class="col-sm-3">
                                <select class="chosen-select form-control" name="subsidyStatus" id="subsidyStatus" disabled="disabled">
                                    <c:choose>
                                        <c:when test="${detail.subsidyStatus == 0}">
                                            <option>等待提交</option>
                                        </c:when>
                                        <c:when test="${detail.subsidyStatus == 1}">
                                            <option>等待审批</option>
                                        </c:when>
                                        <c:when test="${detail.subsidyStatus == 2}">
                                            <option>审批通过</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option>被驳回</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">操作时间</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.subsidyTime }" class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-3">
                                <button type="button" class="btn btn-default"  style="background-color:#f4f4f4;" type="button" value="返回列表" onclick="history.go(-1)" >返回列表</button>
                            </div>
                            <div class="col-sm-offset-1" id="saveDiv" style="display: none">
                                <button type="button" class="btn btn-primary" id="saveButton" type="button" value="保存额度" onclick="saveGroup()">保存额度</button>
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
    $("#quotaGroup").change(function(){
        var updateBefore = $("#updateBefore").val();
        var updateAfter = $("#quotaGroup").val();
        if(updateBefore == updateAfter){
            $("#saveDiv").css('display','none');
        }else{
            $("#saveDiv").css('display','block');
        }
    });

    function  saveGroup(){
        var quotaGroup = $("#quotaGroup").val();
        var deviceUnique = $("#deviceUnique").val();
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/deviceList/saveGroup",
            traditional: true,
            data: {deviceUnique: deviceUnique,quotaGroup: quotaGroup},
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.success) {
                    alert("保存成功");
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
</script>
