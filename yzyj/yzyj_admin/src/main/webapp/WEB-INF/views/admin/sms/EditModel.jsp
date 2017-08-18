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
            修改模板
        </h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="editForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/sms/editModel">
                       <c:choose>
                            <c:when test="${not empty smsModel}">
                                <input class="form-control" id="hiddenId" name="id" maxlength="30" value="${smsModel.id}" type="hidden"/>
                            </c:when>
                        </c:choose>-
            <div class="form-group" id="mcDiv">
                <div class="group">
                    <label class="col-sm-3 control-label">输入短信主题</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="title" id="title" maxlength="20" disabled="true" style="width:200px;" value="${smsModel.title}"/>
                    </div>
                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">输入短信签名</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="sign" id="sign" maxlength="8" value="${smsModel.sign}" style="width:200px;"/>
                    </div>

                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">输入模板内容</label>
                    <div class="col-sm-9">
                        <%--<textarea name="content" id="content" rows="5" cols="65">${smsModel.content}</textarea>--%>
                        <textarea name="content" id="content" rows="5" style="width:300px" maxlength="70" placeholder="请输入模板内容，70个字符以内">${smsModel.content}</textarea>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <input type="button" class="btn btn-default" id="brand-close" data-dismiss="modal" value="关闭" />
                <input type="button" class="btn btn-info" id="brand-save" value="提交" onclick="mySubmit();"/>
            </div>
        </form>
    </div>
    <script>
        function mySubmit() {
            var id = $("#hiddenId").val();

            var title = $("#title").val();
            if(title==""||title==null){
                alert("主题不能为空");
                return false;
            }

            var sign = $("#sign").val();
            if(sign==""||sign==null){
                alert("签名不能为空");
                return false;
            }

            var content = $("#content").val();
            if(content==""||content==null){
                alert("模板不能为空");
                return false;
            }

            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/sms/editModel",
                traditional: true,
                dataType: "json",
                data: {"id":id,"title":title,"sign":sign,"content":content},
                cache: false,
                success: function (data) {
                    if (data.code==0) {
                        alert("修改成功");
                        $("#brand-close").click();
                        table.ajax.reload();
                    } else {
                        alert(data.desc);
                    }


                },
                error: function (error) {
                    alert("失败");
                }
            });
        }
//        $('#editForm').submit(function () {
//
//            var type = $("input[name='type']:checked").val();
//            if(type==""||type==null){
//                alert("请选择类型");
//                return false;
//            }
//
//            var sign = $("input[name='sign']").val();
//            if(sign==""||sign==null){
//                alert("请填写签名");
//                return false;
//            }
//
//            var content = $("textarea[name='content']").val();
//            if(content==""||content==null){
//                alert("请填写模板内容");
//                return false;
//            }
//
//            return true;
//        });
    </script>

</div>
