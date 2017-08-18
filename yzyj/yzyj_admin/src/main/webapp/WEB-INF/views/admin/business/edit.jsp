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
            <a href="#">商户管理</a>
        </li>
        <li class="active">商户列表</li>
    </ul>
</div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <form class="form-horizontal" role="form" id="editForm" onsubmit="return check()" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/business/editBusiness">
                    <c:choose>
                        <c:when test="${not empty info.id}">
                            <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                        </c:when>
                    </c:choose>
                    <%--<input type="hidden" id="agencyName" name="agencyName" value="" />--%>
                    <div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">商户名称</label>
                            <div class="col-sm-3">
                                <input name="merchantName" id="merchantName" type="text" value="${info.merchantName }"  class="form-control input-sm" placeholder="必填">
                            </div>
                            <label class="col-sm-2 control-label">经营地址</label>
                            <div class="col-sm-3">
                                <input name="merchantAddress" id="merchantAddress" type="text" value="${info.merchantAddress }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">法人姓名</label>
                            <div class="col-sm-3">
                                <input name="merchantLegalPerson" id="merchantLegalPerson" type="text" value="${info.merchantLegalPerson }"  class="form-control input-sm" placeholder="必填">
                            </div>
                            <label class="col-sm-2 control-label">法人身份证号</label>
                            <div class="col-sm-3">
                                <input name="merchantIdentityCard" id="merchantIdentityCard" type="text" value="${info.merchantIdentityCard }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">营业执照号</label>
                            <div class="col-sm-3">
                                <input name="businessLicense" id="businessLicense" type="text" value="${info.businessLicense }"  class="form-control input-sm" placeholder="必填">
                            </div>
                            <label class="col-sm-2 control-label">商户联系人姓名</label>
                            <div class="col-sm-3">
                                <input name="merchantPersonName" id="merchantPersonName" type="text" value="${info.merchantPersonName }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">商户联系人电话</label>
                            <div class="col-sm-3">
                                <input name="merchantPersonPhone"  id="merchantPersonPhone" type="text"  value="${info.merchantPersonPhone }"  class="form-control input-sm" placeholder="必填" >
                            </div>
                            <label class="col-sm-2 control-label">对账单发送的邮箱</label>
                            <div class="col-sm-3">
                                <input name="email"  id="email"  type="text"  value="${info.email }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">账户号</label>
                            <div class="col-sm-3">
                                <input name="accountNumber"  id="accountNumber"  type="text"  value="${info.accountNumber }"  class="form-control input-sm" placeholder="必填">
                            </div>
                            <label class="col-sm-2 control-label">账户名称</label>
                            <div class="col-sm-3">
                                <input name="accountName"  id="accountName"  type="text"  value="${info.accountName }"  class="form-control input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">开户行及支行</label>
                            <div class="col-sm-3">
                                <input name="openBank"  id="openBank"  type="text"  value="${info.openBank }"  class="form-control input-sm" placeholder="必填">
                            </div>
                            <label class="col-sm-2 control-label">云支付账号</label>
                            <c:choose>
                                <c:when test="${info.id != 0}">
                                    <div class="col-sm-3">
                                        <input name="yunPayAccount"  id="yunPayAccount"  type="text"  value="${info.yunPayAccount }" readonly="readonly" class="form-control input-sm" placeholder="必填">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="col-sm-3">
                                        <input name="yunPayAccount"  id="yunPayAccount1"  type="text"  value="" class="form-control input-sm" placeholder="必填">
                                    </div>
                                    <div class="col-sm-0">
                                        <input class="btn btn-success" type="button" value="检查账号信息" onclick="getInfo()"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">市场跟进人员</label>
                            <div class="col-sm-3">
                                <input name="followUpName"  id="followUpName"  type="text"  value="${info.followUpName }"  class="form-control input-sm" placeholder="必填">
                            </div>
                            <label class="col-sm-2 control-label">代理人</label>
                            <div class="col-sm-3">
                                <select class="chosen-select form-control" name="agentUnique" id="agentUnique" readonly="readonly" data-placeholder="请选择代理人">
                                    <option value=""></option>
                                    <c:forEach items="${agencyList}" var="agency">
                                        <option value="${agency.agentUnique},${agency.realName},${info.id}" <c:if test="${info.agentUnique==agency.agentUnique}">selected</c:if>>${agency.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <c:if test="${!empty infoP}">
                            <div class="form-group">
                                <div class="group">
                                    <label class="col-sm-2 control-label">营业执照照片</label>
                                    <c:forEach items="${infoP}" var="picList" varStatus="s">
                                        <c:if test="${picList.type == 1}">
                                            <div class="col-sm-2">
                                                <div style="position:absolute;top:0;right:0; font-size:24px"><a href="javascript:void(0)" onclick="delPic('${picList.id}')">×</a></div>
                                                <img src="<%=basePath%>picCommon/license/${picList.picturePath}" style="display: inline-block;max-width: 100%;">
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">营业执照</label>
                            <div class="col-sm-10">
                                <input id="file-Portrait1" type="file" name="file1" multiple class="file-loading">
                            </div>
                        </div>
                        <c:if test="${!empty infoP}">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">法人身份证照片正反面照片</label>
                                <c:forEach items="${infoP}" var="picList" varStatus="s">
                                    <c:if test="${picList.type == 2}">
                                        <div class="col-sm-2">
                                            <div style="position:absolute;top:0;right:0; font-size:24px"><a href="javascript:void(0)" onclick="delPic('${picList.id}')">×</a></div>
                                             <img src="<%=basePath%>picCommon/idCard/${picList.picturePath}" style="display: inline-block;max-width: 100%;">
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">法人身份证照片正反面</label>
                            <div class="col-sm-10">
                                <input id="file-Portrait2" type="file" name="file2" multiple class="file-loading">
                            </div>
                        </div>
                        <c:if test="${!empty infoP}">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">结算银行卡正反面照片</label>
                                <c:forEach items="${infoP}" var="picList" varStatus="s">
                                    <c:if test="${picList.type == 3}">
                                        <div class="col-sm-2">
                                            <div style="position:absolute;top:0;right:0; font-size:24px"><a href="javascript:void(0)" onclick="delPic('${picList.id}')">×</a></div>
                                            <img src="<%=basePath%>picCommon/bankCard/${picList.picturePath}" style="display: inline-block;max-width: 100%;">
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">结算银行卡正反面</label>
                            <div class="col-sm-10">
                                <input id="file-Portrait3" type="file" name="file3" multiple class="file-loading">
                            </div>
                        </div>
                        <c:if test="${!empty infoP}">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">经营场所照片三张照片</label>
                                <c:forEach items="${infoP}" var="picList" varStatus="s">
                                    <c:if test="${picList.type == 4}">
                                        <div class="col-sm-2">
                                            <div style="position:absolute;top:0;right:0; font-size:24px"><a href="javascript:void(0)" onclick="delPic('${picList.id}')">×</a></div>
                                            <img src="<%=basePath%>picCommon/site/${picList.picturePath}" style="display: inline-block;max-width: 100%;">
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">经营场所照片三张</label>
                            <div class="col-sm-10">
                                <input id="file-Portrait4" type="file" name="file4" multiple class="file-loading">
                            </div>
                        </div>
                        <c:if test="${!empty infoP}">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">其他照片</label>
                                <c:forEach items="${infoP}" var="picList" varStatus="s">
                                    <c:if test="${picList.type == 5}">
                                        <div class="col-sm-2">
                                            <div style="position:absolute;top:0;right:0; font-size:24px"><a href="javascript:void(0)" onclick="delPic('${picList.id}')">×</a></div>
                                            <img src="<%=basePath%>picCommon/other/${picList.picturePath}" style="display: inline-block;max-width: 100%;">
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">其他照片</label>
                            <div class="col-sm-10">
                                <input id="file-Portrait5" type="file" name="file5" multiple class="file-loading">
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
        if ($("#merchantName").val() == "") {
            alert("商户名称不能为空");
            return false;
        }
        if ($("#merchantAddress").val() == "") {
            alert("商户地址不能为空");
            return false;
        }
        if ($("#merchantLegalPerson").val() == "") {
            alert("法人姓名不能为空");
            return false;
        }
        if ($("#merchantIdentityCard").val() == "") {
            alert("法人身份证不能为空！");
            return false;
        }
        if ($("#businessLicense").val() == "") {
            alert("营业执照号不能为空");
            return false;
        }
        if ($("#accountNumber").val() == "") {
            alert("账户号不能为空");
            return false;
        }
        if ($("#openBank").val() == "") {
            alert("开户行及支行不能为空");
            return false;
        }
        if ($("#yunPayAccount").val() == "") {
            alert("云支付账号不能为空");
            return false;
        }
        if ($("#agentUnique").val() == "") {
            alert("请选择代理人");
            return false;
        }
        return true;
    };

    function getInfo(){
        var yunPayAccount = $("#yunPayAccount1").val()
        if(yunPayAccount == ""){
            alert("请输入云支付账号");
            return;
        }
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/agencyPerson/getInfo",
            traditional: true,
            data: {loginName: yunPayAccount},
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

    function delPic(id){
        var r = confirm("确认要删除照片");
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/business/delPic",
                traditional: true,
                data: {id: id},
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
    };
</script>
