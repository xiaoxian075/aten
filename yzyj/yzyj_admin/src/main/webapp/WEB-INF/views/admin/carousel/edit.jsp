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
            <a href="#">轮播管理</a>
        </li>
        <li class="active">修改信息</li>
    </ul>
</div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <form class="form-horizontal" role="form" id="editForm" method="POST" onsubmit="return chkForm();" enctype="multipart/form-data" action="<%=basePath%>rest/admin/carousel/edit">
                    <c:choose>
                        <c:when test="${not empty info.id}">
                            <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                        </c:when>
                    </c:choose>
                    <input type="hidden" id="infoContent" name="content" />
                    <div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">轮播图片</label>
                            <div class="col-sm-3">
                                <c:if test="${info.id != 0}">
                                    <img src="<%=basePath%>picCommon/infoPic/${info.imgUrl}" style="width: 250px;height: 200px;">
                                </c:if>
                                <input id="file-Portrait1" id="imgUrl" type="file" name="file" multiple class="file-loading">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">标题</label>
                            <div class="col-sm-3">
                                <input type="text" name="title" id="title" value="${info.title }"
                                       class="form-control input-sm input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">轮播图位置</label>
                            <div class="col-sm-3">
                                <select class="form-control" name="type">
                                    <option value="">请选择</option>
                                    <c:forEach items="${carouselType}" var="ls">
                                        <option value="${ls.dictBh}" <c:if test="${info.type eq ls.dictBh}"> selected</c:if>>${ls.dictMc}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-3">
                                <script type="text/plain" id="editor" style="width: 1000px;height: 500px;">${info.content}</script>
                                <div class="con-split"></div>
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
<script type="text/javascript">
    UE.getEditor('editor');
    UE.getEditor('editor', {
        autoHeightEnabled: false
    });
    function chkForm() {
        var ue = UE.getEditor('editor').getContent();
        if($("#imgUrl").val()==""){
            alert("请上传轮播图片");
            return false;
        }
        if($("#title").val()==""){
            alert("请填写标题");
            return false;
        }
        $("#infoContent").val(ue);
        return true;
    };

    function returnGo(){
        window.history.back(-1);
    }
</script>