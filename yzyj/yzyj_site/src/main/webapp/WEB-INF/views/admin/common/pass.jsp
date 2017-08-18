
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
            <a href="#">系统管理</a>
        </li>
        <li class="active">个人信息</li>
    </ul>
</div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <form class="form-horizontal" role="form" id="editForm" enctype="multipart/form-data">
                    <c:choose>
                        <c:when test="${not empty info.id}">
                            <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                        </c:when>
                    </c:choose>
                    <div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-3">
                                ${info.username}
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">角色</label>
                            <div class="col-sm-3">
                                ${info.rid}
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">创建时间</label>
                            <div class="col-sm-3">
                                <fmt:formatDate value="${info.createtime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                            </div>
                        </div>
                        <%--<c:if test="${agencyPerson.agentUnique != null}">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">当前余额</label>
                                <div class="col-sm-1">
                                    ${agencyPerson.remainingSum} (元)
                                </div>
                                <c:if test="${agencyPerson.isWithdraw == 1}">
                                    <div class="col-sm-0">
                                        <input type="button" class="btn btn-success btn-xs" id="withdraw" value="提&nbsp;&nbsp;&nbsp;&nbsp;现"/>
                                    </div>
                                </c:if>
                            </div>
                        </c:if>
                        <div class="form-group" style="display: none;" id="moneyDiv">
                            <label class="col-sm-2 control-label">提现金额</label>
                            <div class="col-sm-3">
                                <input type="text" name="money" id="money" placeholder="请输入您要提现的金额" class="form-control input-sm">
                            </div>
                        </div>--%>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">旧密码</label>
                            <div class="col-sm-3">
                                <input type="password" name="oldPass" id="oldPass" class="form-control input-sm">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">新密码</label>
                            <div class="col-sm-3">
                                <input name="newPass" id="newPass" type="password" class="form-control input-sm">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">确认新密码</label>
                            <div class="col-sm-3">
                                <input name="confirmPass" id="confirmPass" type="password"  class="form-control input-sm" >
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-3">
                                <input type="button" class="btn btn-primary" id="pass-save" value="保&nbsp;&nbsp;&nbsp;&nbsp;存"/>
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
    $('#pass-save').click(function () {
        if ($("#oldPass").val() == "") {
            alert("旧密码不能为空");
            return false;
        }
        if ($("#newPass").val() == "") {
            alert("新密码不能为空");
            return false;
        }
        if ($("#confirmPass").val() == "") {
            alert("确认新密码不能为空");
            return false;
        }
        if (true){
            $.ajax({
                url: "<%=basePath%>rest/admin/user/updatePass",
                data: $("#editForm").serialize(),
                type: "POST",
                success: function (data) {
                    alert(data.message);
                    if (data.success) {
                        window.parent.location="<%=basePath%>rest/admin/logout";
                    }
                },
                error: function (error) {
                    alert("请求失败！请联系管理员");
                }
            });
        }
    })
</script>