<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="<%=basePath%>assets/js/ajaxfileupload.js"></script>
<style>
    .col-sm-3 img{max-width:50px;_width:expression(this.width > 50 ? "50px" : this.width);}
</style>
<%--<script type="text/javascript" src="<%=basePath%>assets/plugins/selectize.js/examples/js/jquery.js"></script>--%>
<link href="<%=basePath%>assets/file/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>assets/file/js/fileinput.min.js" type="text/javascript"></script>
<script src="<%=basePath%>assets/file/js/fileinput_locale_zh.js" type="text/javascript"></script>
<div class="modal-content" id="brand-content">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal" aria-hidden="true">
            &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">
            <c:choose>
                <c:when test="${not empty info.id}">
                    修改招商优势信息
                </c:when>
                <c:otherwise>
                    添加招商优势信息
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="editForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/attractMerchants/hz/edit">
            <c:choose>
                <c:when test="${not empty info.id}">
                    <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                </c:when>
            </c:choose>
            <div class="form-group" id="mcDiv">
                <div class="group">
                    <label class="col-sm-3 control-label">资质</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="title" maxlength="50" rows="3" value="${info.title}"/>
                    </div>

                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-9">
                        <textarea id="note" name="note" style="width:100%;height:100px" >${info.note}</textarea>
                    </div>
                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">排序</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="px" rows="3" value="${info.px}"/>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-info" id="edit">
                    提交
                </button>
            </div>
        </form>
    </div>

</div>
<script>
    $('#edit').click(function () {
        $.ajax({
            url: "<%=basePath%>rest/admin/attractMerchants/hz/edit",
            data: $("#editForm").serialize(),
            type: "POST",
            success: function (data) {
                alert("成功!");
                $("#brand-edit").modal("hide");
                table.ajax.reload();
            },
            error: function (error) {
                alert("失败")
            }
        });
    })
</script>