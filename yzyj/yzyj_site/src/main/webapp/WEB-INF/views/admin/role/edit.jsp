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
                    修改角色
                </c:when>
                <c:otherwise>
                    添加角色
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="editForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/role/edit">
            <c:choose>
                <c:when test="${not empty info.id}">
                    <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                </c:when>
            </c:choose>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">编号</label>

                    <div class="col-sm-9">
                        <input class="form-control" name="roleNumber" maxlength="50" rows="3" value="${info.roleNumber}"/>
                    </div>

                </div>
            </div>
            <div class="form-group" id="mcDiv">
                <div class="group">
                    <label class="col-sm-3 control-label">名称</label>

                    <div class="col-sm-9">
                        <input class="form-control" name="roleName" maxlength="50" rows="3" value="${info.roleName}" onblur="checkmc();"/>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <button type="submit" class="btn btn-info" id="brand-save">
                    提交
                </button>
            </div>
        </form>
    </div>
    <script>
        $('#editForm').submit(function () {
            var flag=true;
            var mc=$("input[name='roleName']").val();
            if(mc==""||mc==null){
                $("#mcDiv").addClass("has-error");
                flag= false;
            }

            if($("#mcDiv").hasClass("has-error")){
                alert("请填写名称");
                flag= false;
            }
            if (!flag){
                return false;
            }
        })
        <%--$("#brand-save").click(function(e) {--%>
        <%--var flag=true;--%>

        <%--var mc=$("input[name='name']").val();--%>
        <%--if(mc==""||mc==null){--%>
        <%--$("#mcDiv").addClass("has-error");--%>
        <%--flag= false;--%>
        <%--}--%>

        <%--if($("#mcDiv").hasClass("has-error")){--%>
        <%--flag= false;--%>
        <%--}--%>
        <%--if(flag) {--%>
        <%--&lt;%&ndash;$.ajaxFileUpload({&ndash;%&gt;--%>
        <%--&lt;%&ndash;url: '<%=basePath%>rest/admin/brand/edit',&ndash;%&gt;--%>
        <%--&lt;%&ndash;fileElementId:'file1',&ndash;%&gt;--%>
        <%--&lt;%&ndash;type: "POST",&ndash;%&gt;--%>
        <%--&lt;%&ndash;data: {//加入的文本参数&ndash;%&gt;--%>
        <%--&lt;%&ndash;"name": mc,&ndash;%&gt;--%>
        <%--&lt;%&ndash;"brandId":$("input[name='brandId']").val(),&ndash;%&gt;--%>
        <%--&lt;%&ndash;"logo":$("input[name='logo']").val(),&ndash;%&gt;--%>
        <%--&lt;%&ndash;},&ndash;%&gt;--%>
        <%--&lt;%&ndash;success: function(data) {&ndash;%&gt;--%>
        <%--&lt;%&ndash;$("#brand-edit").modal("hide");&ndash;%&gt;--%>
        <%--&lt;%&ndash;table.ajax.reload();&ndash;%&gt;--%>
        <%--&lt;%&ndash;},&ndash;%&gt;--%>
        <%--&lt;%&ndash;error: function() {&ndash;%&gt;--%>
        <%--&lt;%&ndash;alert("请求失败，请联系管理员");&ndash;%&gt;--%>

        <%--&lt;%&ndash;}&ndash;%&gt;--%>
        <%--&lt;%&ndash;});&ndash;%&gt;--%>
        <%--$("#editForm").submit();--%>
        <%--&lt;%&ndash;$.ajax({&ndash;%&gt;--%>
        <%--&lt;%&ndash;url: "<%=basePath%>rest/admin/brand/edit",&ndash;%&gt;--%>
        <%--&lt;%&ndash;data: $("#editForm").serialize(),&ndash;%&gt;--%>
        <%--&lt;%&ndash;type: "POST",&ndash;%&gt;--%>
        <%--&lt;%&ndash;success: function (data) {&ndash;%&gt;--%>
        <%--&lt;%&ndash;if (data.success) {&ndash;%&gt;--%>
        <%--&lt;%&ndash;$("#brand-edit").modal("hide");&ndash;%&gt;--%>
        <%--&lt;%&ndash;table.ajax.reload();&ndash;%&gt;--%>
        <%--&lt;%&ndash;}&ndash;%&gt;--%>
        <%--&lt;%&ndash;},&ndash;%&gt;--%>
        <%--&lt;%&ndash;error: function (error) {&ndash;%&gt;--%>
        <%--&lt;%&ndash;alert("请求失败，请联系管理员");&ndash;%&gt;--%>
        <%--&lt;%&ndash;}&ndash;%&gt;--%>
        <%--&lt;%&ndash;});&ndash;%&gt;--%>
        <%--}--%>
        <%--})--%>
        function checkmc(){
            var mc=$("input[name='roleName']").val();
            if(mc==""||mc==null){
                $("#mcDiv").addClass("has-error");
                $("input[name='roleName']").attr("placeholder","请填写");
                return false;
            }else{
                $("#mcDiv").removeClass("has-error");
                return true;
            }
        }
    </script>

</div>
