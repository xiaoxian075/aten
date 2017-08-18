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
            <a href="#">额度管理</a>
        </li>
        <li class="active">编辑额度组别</li>
    </ul>
</div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <form class="form-horizontal" role="form" id="editForm" onsubmit="return check()" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/quota/edit">
                    <c:choose>
                        <c:when test="${not empty info.id}">
                            <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                        </c:when>
                    </c:choose>
                    <div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">借记卡单笔</label>
                            <div class="col-sm-3">
                                <input name="cxkOne" id="cxkOne" type="text" value="${info.cxkOne }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">借记卡当天</label>
                            <div class="col-sm-3">
                                <input name="cxkDay" id="cxkDay" type="text" value="${info.cxkDay }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">借记卡当月</label>
                            <div class="col-sm-3">
                                <input name="cxkMonth" id="cxkMonth" type="text" value="${info.cxkMonth }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">贷记卡单笔</label>
                            <div class="col-sm-3">
                                <input name="xykOne" id="xykOne" type="text" value="${info.xykOne }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">贷记卡当天</label>
                            <div class="col-sm-3">
                                <input name="xykDay" id="xykDay" type="text" value="${info.xykDay }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">贷记卡当月</label>
                            <div class="col-sm-3">
                                <input name="xykMonth" id="xykMonth" type="text" value="${info.xykMonth }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">贷记卡单笔超额</label>
                            <div class="col-sm-3">
                                <input name="xykExcessOne" id="xykExcessOne" type="text" value="${info.xykExcessOne }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">贷记卡当天超额</label>
                            <div class="col-sm-3">
                                <input name="xykExcessDay"  id="xykExcessDay" type="text"  value="${info.xykExcessDay }"  class="form-control input-sm" placeholder="必填" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">贷记卡当月超额</label>
                            <div class="col-sm-3">
                                <input name="xykExcessMonth"  id="xykExcessMonth"  type="text"  value="${info.xykExcessMonth }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">借记卡单笔超额</label>
                            <div class="col-sm-3">
                                <input name="cxkExcessOne" id="cxkExcessOne" type="text" value="${info.cxkExcessOne }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">借记卡当天超额</label>
                            <div class="col-sm-3">
                                <input name="cxkExcessDay"  id="cxkExcessDay" type="text"  value="${info.cxkExcessDay }"  class="form-control input-sm" placeholder="必填" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">借记卡当月超额</label>
                            <div class="col-sm-3">
                                <input name="cxkExcessMonth"  id="cxkExcessMonth"  type="text"  value="${info.cxkExcessMonth }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">备注</label>
                            <div class="col-sm-3">
                                <input name="note"  id="note"  type="text"  value="${info.note }"  class="form-control input-sm" disabled="disabled">
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
        if ($("#cxkOne").val() == "") {
            alert("储蓄卡单笔不能为空");
            return false;
        }
        if ($("#cxkDay").val() == "") {
            alert("储蓄卡当天不能为空");
            return false;
        }
        if ($("#cxkMonth").val() == "") {
            alert("储蓄卡当月不能为空");
            return false;
        }
        if ($("#xykOne").val() == "") {
            alert("信用卡单笔不能为空！");
            return false;
        }
        if ($("#xykDay").val() == "") {
            alert("信用卡当天不能为空");
            return false;
        }
        if ($("#xykMonth").val() == "") {
            alert("信用卡当月不能为空");
            return false;
        }
        if ($("#xykExcessOne").val() == "") {
            alert("信用卡单笔超额不能为空");
            return false;
        }
        if ($("#xykExcessDay").val() == "") {
            alert("信用卡当天超额不能为空");
            return false;
        }
        if ($("#xykExcessMonth").val() == "") {
            alert("信用卡当月超额不能为空");
            return false;
        }
        if ($("#note").val() == "") {
            alert("备注不能为空");
            return false;
        }
        return true;
    };
</script>