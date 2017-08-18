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
            <a href="#">官网动态信息</a>
        </li>
        <li class="active">
            <c:choose>
                <c:when test="${not empty info.id}">
                    修改动态
                </c:when>
                <c:otherwise>
                    添加动态
                </c:otherwise>
            </c:choose>
        </li>
    </ul>
</div>
<div class="row" style="width:100%;height:100%">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body" >
                <form class="form-horizontal" role="form" id="editForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/recruitment/edit">
                    <input type="hidden" id="content" name="content" />
                    <c:choose>
                        <c:when test="${not empty info.id}">
                            <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                        </c:when>
                    </c:choose>
                    <div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">职位</label>
                            <div class="col-sm-3">
                                <input type="text" name="position" id="position" value="${info.position }" class="form-control input-sm input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">部门</label>
                            <div class="col-sm-3">
                                <select class="form-control" name="department">
                                    <option value="">请选择</option>
                                    <c:forEach items="${department}" var="ls">
                                        <option value="${ls.dictBh}" <c:if test="${info.department eq ls.dictBh}"> selected</c:if>>${ls.dictMc}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">人数</label>
                            <div class="col-sm-3">
                                <input type="text" name="num" id="num" value="${info.num }" class="form-control input-sm input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">工作经验</label>
                            <div class="col-sm-3">
                                <input type="text" name="experience" id="experience" value="${info.experience }" class="form-control input-sm input-sm" placeholder="必填">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">工资范围</label>
                            <div class="col-sm-3">
                                <input type="text" name="salary" id="salary" value="${info.salary }" class="form-control input-sm input-sm" placeholder="必填">
                            </div>
                        </div>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-4 control-label">排序</label>--%>
                            <%--<div class="col-sm-3">--%>
                                <%--<input type="text" name="px" id="px" value="${info.px }" class="form-control input-sm input-sm" placeholder="">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="form-group" style="height:400px">
                            <label class="col-sm-4 control-label">职位描述</label>
                            <div class="col-sm-3" style="width:800px;height:400px">
                                <script type="text/plain" id="myEditor"  style="width:100%;height:100%;">${info.content }  </script>
                                <input type="submit" class="btn btn-primary" value="保&nbsp;&nbsp;&nbsp;&nbsp;存"/>
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
    $('#editForm').submit(function () {
        var content = UE.getEditor('myEditor').getContent();
        $("input[name='content']").val(content);
    })
    function returnGo(){
        window.history.back(-1);
    }

</script>

<script type="text/javascript">
    function isNumeric(strNumber) {
        var newPar = /^(-|\+)?\d+(\.\d+)?$/;
        return newPar.test(strNumber);
    }
</script>