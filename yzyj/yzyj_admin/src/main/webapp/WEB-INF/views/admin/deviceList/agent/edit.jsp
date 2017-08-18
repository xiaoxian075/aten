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
        <li class="active">添加POS+</li>
    </ul>
</div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <form class="form-horizontal" role="form" id="editForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/deviceList/agentEdit">
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
                                <input name="deviceName" id="deviceName" type="text" value="${info.deviceName }" class="form-control input-sm input-sm" placeholder="必填">
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
<script>
    $('#editForm').submit(function () {
        var flag=true;
        var blnChk=chk;
        if(!blnChk){
            return false;
        }
    })

</script>

<script type="text/javascript">
    function isNumeric(strNumber) {
        var newPar = /^(-|\+)?\d+(\.\d+)?$/;
        return newPar.test(strNumber);
    }
    var chk = function() {
        if ($("#realName").value == "") {
            alert("真实名称不能为空");
            return false;
        }
        if ($("#phone").value == "") {
            alert("联系电话不能为空");
            return false;
        }
        if ($("#address").value == "") {
            alert("联系地址不能为空");
            return false;
        }
        if ($("#papersType").value == "") {
            alert("类型不能为空！");
            return false;
        }
        if ($("#papersNumber").value == "") {
            alert("验证类型号码不能为空");
            return false;
        }
        if ($("#yunPayLoginName").value == "") {
            alert("代理数不能为空");
            return false;
        }
        return true;
    };
</script>