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
                    修改申请入驻信息
                </c:when>
                <c:otherwise>
                    添加申请入驻信息
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="editForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/applyMerchants/edit">
            <c:choose>
                <c:when test="${not empty info.id}">
                    <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                </c:when>
            </c:choose>
            <div class="form-group" id="mcDiv">
                <div class="group">
                    <label class="col-sm-3 control-label">公司名</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="company" maxlength="50" rows="3" value="${info.company}"/>
                    </div>

                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">品类</label>
                    <div class="col-sm-9">
                        <textarea id="note" name="category" style="width:100%;height:100px" >${info.category}</textarea>
                    </div>
                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">联系方式</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="contactWay" rows="3" value="${info.contactWay}"/>
                    </div>
                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">电话</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="mobile" rows="3" value="${info.mobile}"/>
                    </div>
                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">QQ</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="qq" rows="3" value="${info.qq}"/>
                    </div>
                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">邮箱</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="email" rows="3" value="${info.email}"/>
                    </div>
                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">其它</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="note" rows="3" value="${info.note}"/>
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
            url: "<%=basePath%>rest/admin/applyMerchants/edit",
            data: $("#editForm").serialize(),
            type: "POST",
            success: function (data) {
                alert("成功!");
                $("#brand-edit").modal("hide");
                table.ajax.reload();
            },
            error: function (error) {
                alert(error);
            }
        });
    })
</script>