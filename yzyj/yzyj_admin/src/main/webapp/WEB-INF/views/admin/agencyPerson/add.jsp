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
        <li class="active">添加代理人</li>
    </ul>
</div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <form class="form-horizontal" role="form" id="editForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/agencyPerson/edit">
                    <c:choose>
                        <c:when test="${not empty info.id}">
                            <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                        </c:when>
                    </c:choose>
                    <div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">真实姓名</label>
                            <div class="col-sm-3">
                                <input type="text" name="realName" id="realName" value="${info.realName }"
                                       class="form-control input-sm input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">联系电话</label>
                            <div class="col-sm-3">
                                <input name="phone" id="phone" type="text" value="${info.phone }"
                                       class="form-control input-sm input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">地址</label>
                            <div class="col-sm-3">
                                <input name="address" id="address" type="text" value="${info.address } "
                                       class="form-control input-sm input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">验证类型</label>
                            <div class="col-sm-3">
                                <input name="papersType" id="papersType" type="text" value="${info.papersType }"
                                       class="form-control input-sm input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group" style="border: medium  rgb(250,0,255)">
                            <label class="col-sm-4 control-label">证件号码</label>
                            <div class="col-sm-3">
                                <input name="papersNumber" id="papersNumber" type="text" value="${info.papersNumber }"
                                       class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group" style="border: medium  rgb(250,0,255)">
                            <label class="col-sm-4 control-label">代理人省份</label>
                            <div class="col-sm-3">
                                <input name="province"  id="province" type="text"  value="${info.province }"
                                       class="form-control input-sm" placeholder="必填" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">代理人城市</label>
                            <div class="col-sm-3">
                                <input name="city"  id="city"  type="text"  value="${info.city }"
                                       class="form-control input-sm"  placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">云支付账号</label>
                            <div class="col-sm-3">
                                <input name="yunPayLoginName" id="yunPayLoginName" type="text"  value="${info.yunPayLoginName }" class="form-control input-sm"  placeholder="必填">
                                <!-- 添加地址信息，便于地图查找 -->
                            </div>
                            <div class="col-sm-5">
                                <input class="btn btn-success" type="button" value="检查账号信息" onclick="getInfo()"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">备注</label>
                            <div class="col-sm-3">
                                <input name="note" type="text" value="${info.note }"
                                       class="form-control input-sm"
                                >
                                <!-- 添加地址信息，便于地图查找 -->
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-3">
                                <input type="submit" class="btn btn-primary" value="保&nbsp;&nbsp;&nbsp;&nbsp;存"/>
                                <button type="button" class="btn btn-default" style="background-color:#f4f4f4;" type="button" onClick="returnGo()" value="返回列表" >返回列表</button>
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
        var blnChk=chk();
        if(blnChk== false){
            return false;
        }
        return blnChk;
    })
    function returnGo(){
        window.history.back(-1);
        //window.location.href = "<%=basePath%>/rest/admin/agencyPerson/index";
    }

</script>

<script type="text/javascript">
    function isNumeric(strNumber) {
        var newPar = /^(-|\+)?\d+(\.\d+)?$/;
        return newPar.test(strNumber);
    }
    var chk = function() {
        if ($("#realName").val() == "") {
            alert("真实名称不能为空");
            return false;
        }
        if ($("#phone").val() == "") {
            alert("联系电话不能为空");
            return false;
        }
        if ($("#address").val() == "") {
            alert("联系地址不能为空");
            return false;
        }
        if ($("#papersType").val() == "") {
            alert("类型不能为空！");
            return false;
        }
        if ($("#papersNumber").val() == "") {
            alert("验证类型号码不能为空");
            return false;
        }
        if ($("#yunPayLoginName").val() == "") {
            alert("云支付账号不能为空");
            return false;
        }
        return true;
    };

    function getInfo(){
        var yunPayLoginName = $("#yunPayLoginName").val()
        if(yunPayLoginName == ""){
            alert("请输入云支付账号");
            return;
        }
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/agencyPerson/getInfo",
            traditional: true,
            data: {loginName: yunPayLoginName},
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