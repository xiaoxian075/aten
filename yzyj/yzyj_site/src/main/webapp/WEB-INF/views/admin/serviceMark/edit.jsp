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
<jsp:include page="/WEB-INF/views/admin/common/head.jsp"/>
<link href="<%=basePath%>ueditor1_3_6/themes/default/css/ueditor.min.css" type="text/css" rel="stylesheet">
<link href="<%=basePath%>ueditor1_3_6/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="<%=basePath%>ueditor1_3_6/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>ueditor1_3_6/ueditor.all.min.js"></script>
<script type="text/javascript" src="<%=basePath%>ueditor1_3_6/lang/zh-cn/zh-cn.js"></script>
<body style="background-color: #fff;">
<div class="breadcrumbs" id="breadcrumbs" style="margin-top:5px;">
    <ul class="breadcrumb">
        <li>
            <span class="glyphicon glyphicon-home"></span>
            <a href="#" target="main" style="padding-left:5px;margin-left:5px;">首页</a>
        </li>
        <li>
            <a href="#">服务管理</a>
        </li>
        <li class="active">
            <c:choose>
                <c:when test="${not empty info.id}">
                    修改服务
                </c:when>
                <c:otherwise>
                    添加服务
                </c:otherwise>
            </c:choose>
        </li>
    </ul>
</div>
<div class="row" style="width:100%;height:100%">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body" >
                <form class="form-horizontal" role="form" id="editForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/service/edit">
                    <input type="hidden" id="detail" name="detail" />
                    <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                    <div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">标题</label>
                            <div class="col-sm-3">
                                <input type="text" name="title" id="title" value="${info.title }" class="form-control input-sm input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">副标题</label>
                            <div class="col-sm-3">
                                <input type="text" name="titleDesc" id="titleDesc" value="${info.titleDesc }" class="form-control input-sm input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">类型</label>
                            <div class="col-sm-3">
                                <select class="form-control" name="type">
                                    <option value="">请选择</option>
                                    <c:forEach items="${serviceType}" var="ls">
                                        <option value="${ls.dictBh}" <c:if test="${info.type eq ls.dictBh}"> selected</c:if>>${ls.dictMc}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">排序</label>
                            <div class="col-sm-3">
                                <input type="text" name="px" id="px" value="${info.px }" class="form-control input-sm input-sm" placeholder="">
                            </div>
                        </div>
                        <c:if test="${not empty info.logo}">
                            <div class="form-group">
                                <label class="col-sm-1 control-label"></label>
                                <div class="col-sm-3" style="width:200px;height:100px">
                                    <img src="<%=basePath%>upload/service/${info.logo}" style="height:100%;width:100%" />
                                </div>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">图标</label>
                            <div class="col-sm-3">
                                <input id="file1" type="file" name="file1" class="file-loading"><p style="color:red;font-size:16px;"> </p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">内容</label>
                            <div class="col-sm-3">
                                <textarea id="content" name="content" style="width:100%;height:100px" >${info.content}</textarea>
                            </div>
                        </div>
                        <div class="form-group" style="height:400px">
                            <label class="col-sm-1 control-label">详情</label>
                            <div class="col-sm-3" style="width:800px;height:400px">
                                    <script type="text/plain" id="myEditor"  style="width:100%;height:100%;">${info.detail }  </script>
                                <input type="submit" class="btn btn-primary" id="edit" value="保&nbsp;&nbsp;&nbsp;&nbsp;存"/>
                                <button type="button" class="btn btn-default" style="background-color:#f4f4f4;" type="button" onClick="returnGo()" value="返回列表" >返回列表</button>
                                <!-- 添加地址信息，便于地图查找 -->
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
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    UE.getEditor('myEditor');
    $("#file1").fileinput({
        language: 'zh', //设置语言
        showUpload: false, //是否显示上传按钮
        showCaption: false,//是否显示标题
        overwriteInitial: true,
        browseClass: "btn btn-primary", //按钮样式
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        maxFileSize: 1024,
        maxFilesNum: 1
    });
    function returnGo(){
        window.history.back(-1);
    }
    $('#editForm').submit(function () {
        var body = UE.getEditor('myEditor').getContent();
        $("input[name='detail']").val(body);

        <%--$.ajaxFileUpload({--%>
            <%--url: "<%=basePath%>rest/admin/dynamicInfo/edit",--%>
            <%--fileElementId: 'file1',--%>
            <%--type: "POST",--%>
            <%--data: $("#editForm").serialize(),--%>
            <%--success: function (data) {--%>
                <%--alert("成功!");--%>
                <%--window.location.href="<%=basePath%>rest/admin/dynamicInfo/index"--%>
            <%--},--%>
            <%--error: function (error) {--%>
                <%--alert(error);--%>
            <%--}--%>
        <%--});--%>
    })
</script>

<script type="text/javascript">
    function isNumeric(strNumber) {
        var newPar = /^(-|\+)?\d+(\.\d+)?$/;
        return newPar.test(strNumber);
    }
</script>