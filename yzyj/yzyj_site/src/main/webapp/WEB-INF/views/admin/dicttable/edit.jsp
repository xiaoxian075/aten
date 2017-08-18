<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/admin/common/commEdit.jsp"/>
<div class="modal-content" id="brand-content">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal" aria-hidden="true">
            &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">
            <c:choose>
                <c:when test="${not empty info.sysId}">
                    修改字典
                </c:when>
                <c:otherwise>
                    添加字典
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="editForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/dicttable/edit">
            <c:choose>
                <c:when test="${not empty info.sysId}">
                    <input class="form-control" id="sysId" name="sysId" maxlength="50" rows="3" value="${info.sysId}" type="hidden"/>
                </c:when>
            </c:choose>
            <div class="form-group" id="mcDiv">
                <div class="group">
                    <label class="col-sm-3 control-label">字典表编码</label>

                    <div class="col-sm-9">
                        <input class="form-control" name="dictTableBh" maxlength="50" rows="3" value="${info.dictTableBh}" />
                    </div>

                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">字典表名称</label>

                    <div class="col-sm-9">
                        <input class="form-control" name="dictTableMc" maxlength="50" rows="3" value="${info.dictTableMc}"/>
                    </div>

                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">字典编号</label>

                    <div class="col-sm-9">
                        <input class="form-control" name="dictBh" maxlength="50" rows="3" value="${info.dictBh}"/>
                    </div>

                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">字典名称</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="dictMc" maxlength="50" rows="3" value="${info.dictMc}"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <input type="button" class="btn btn-success" id="edit" value="修改"/>
                <input type="button" class="btn btn-success" id="insert" value="新增"/>
            </div>
        </form>
    </div>
    <script>
        $('#edit').click(function () {
            $.ajax({
                url: "<%=basePath%>rest/admin/dicttable/edit",
                data: $("#editForm").serialize(),
                type: "POST",
                success: function (data) {
                    alert("成功!");
                    table.ajax.reload();
                },
                error: function (error) {
                    alert(error);
                }
            });
        })
        $("#insert").click(function () {
            $("#sysId").val("");
            $.ajax({
                url: "<%=basePath%>rest/admin/dicttable/edit",
                data: $("#editForm").serialize(),
                type: "POST",
                success: function (data) {
                    alert("成功!");
                    table.ajax.reload();
                },
                error: function (error) {
                    alert(error);
                }
            });
        })
    </script>

</div>
