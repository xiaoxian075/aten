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
                    修改导航
                </c:when>
                <c:otherwise>
                    添加导航
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="editForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/navigation/edit">
            <input  name="id" id="id" value="${info.id}"  type="hidden"/>
            <input  name="logo" id="logo" value="${info.logo}"  type="hidden"/>
            <div class="form-group" id="mcDiv">
                <div class="group">
                    <label class="col-sm-3 control-label">名称</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="name" maxlength="50"onblur="checkmc();" rows="3" value="${info.name}"/>
                    </div>

                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">父级导航</label>
                    <div class="col-sm-9">
                        <select class="form-control" name="pid">
                            <option value="">请选择</option>
                            <c:forEach items="${navigation}" var="ls">
                                <option value="${ls.id}" <c:if test="${info.pid eq ls.id}"> selected</c:if>>${ls.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">URL</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="url" maxlength="255" rows="3" value="${info.url}" />
                    </div>
                </div>
            </div>
            <c:if test="${not empty info.id}">
                <div class="form-group">
                    <label class="col-sm-3 control-label"></label>
                    <div class="col-sm-3" style="width:80px;width:50px">
                        <c:if test="${not empty info.logo}">
                            <img src="${path}${info.logo}" style="height:100%;width:100%"/>
                        </c:if>
                    </div>
                </div>
            </c:if>
            <div class="form-group">
                <div class="group">
                    <label class="col-sm-3 control-label">LOGO</label>
                    <div class="col-sm-9">
                        <input id="file1" type="file" name="file1" class="file-loading"><p style="color:red;font-size:16px;">(图片规格：60px*60)</p>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="group">
                    <label class="col-sm-3 control-label">排序</label>
                    <div class="col-sm-9">
                        <input class="form-control" name="px" maxlength="3" rows="3"
                               onkeyup="this.value=this.value.replace(/\D/g,'' )"
                               onafterpaste="this.value=this.value.replace(/\D/g,'')" value="${info.px}"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-info" id="brand-save">
                    提交
                </button>
            </div>
        </form>
    </div>

</div>
<script>
    $("#file1").fileinput({
        language: 'zh', //设置语言
        showUpload: false, //是否显示上传按钮
        showCaption: false,//是否显示标题
        overwriteInitial: true,
        browseClass: "btn btn-primary", //按钮样式
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        maxFileSize: 1024,
        maxFilesNum: 1
//        initialPreview: [ //预览图片的设置
//                    "<img src='" + imageurl + "' class='file-preview-image' alt='肖像图片' title='肖像图片'>"
//        ]
    });
    $("#pName").click(function () {
        $("#menustrees").show();
    });
    $("#brand-save").click(function (e) {
        e.preventDefault();
        var flag = true;

        var mc = $("input[name='name']").val();
        if (mc == "" || mc == null) {
            $("#mcDiv").addClass("has-error");
            flag = false;
        }

        if ($("#mcDiv").hasClass("has-error")) {
            flag = false;
        }
        if (flag) {
            $.ajaxFileUpload({
                url: '<%=basePath%>rest/admin/navigation/edit',
                fileElementId: 'file1',
                type: "POST",
                data: {//加入的文本参数
                    "name": mc,
                    "id": $("input[name='id']").val(),
                    "path": $("input[name='name']").val(),
                    "url": $("input[name='url']").val(),
                    "px": $("input[name='px']").val(),
                    "pid": $("select[name='pid']").val(),
                },
                success: function (data) {
                    $("#brand-edit").modal("hide");
                    table.ajax.reload();
                },
                error: function () {
                    alert("请求失败，请联系管理员");

                }
            });
        }
    })
    function checkmc() {
        var mc = $("input[name='name']").val();
        if (mc == "" || mc == null) {
            $("#mcDiv").addClass("has-error");
            $("input[name='name']").attr("placeholder", "请填写");
            return false;
        } else {
            $("#mcDiv").removeClass("has-error");
            return true;
        }
    }

</script>
