<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="<%=basePath%>assets/js/ajaxfileupload.js"></script>
<link href="<%=basePath%>video/video-js.css" rel="stylesheet" type="text/css">
<!-- video.js must be in the <head> for older IEs to work. -->
<script src="<%=basePath%>video/video.js"></script>
<!-- Unless using the CDN hosted version, update the URL to the Flash SWF -->
<script>
    videojs.options.flash.swf = "video-js.swf";
</script>
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
                    修改帐号
                </c:when>
                <c:otherwise>
                    添加企业视频
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="editForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/propaganda/edit">
            <c:choose>
                <c:when test="${not empty info.id}">
                    <input class="form-control" name="id" maxlength="50" rows="3" value="${info.id}" type="hidden"/>
                    <input class="form-control" name="picPath" maxlength="50" rows="3" value="${info.picPath}" type="hidden"/>
                    <input class="form-control" name="path" maxlength="50" rows="3" value="${info.path}" type="hidden"/>
                </c:when>
            </c:choose>
            <div class="form-group" id="mcDiv">
                <div class="group">
                    <label class="col-sm-3 control-label">标题</label>

                    <div class="col-sm-9">
                        <input class="form-control" name="title" id="title" maxlength="50" rows="3" value="${info.title}" onblur="checkmc();"/>
                    </div>

                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">类型</label>

                    <div class="col-sm-9">
                        <select class="form-control" name="type">
                            <option value="">请选择</option>
                            <c:forEach items="${videoType}" var="ls">
                                <option value="${ls.dictBh}" <c:if test="${info.type eq ls.dictBh}"> selected</c:if>>${ls.dictMc}</option>
                            </c:forEach>
                        </select>
                    </div>

                </div>
            </div>
            <c:if test="${not empty info.picPath}">
                <div class="form-group">
                    <label class="col-sm-3 control-label"></label>
                    <div class="col-sm-9" style="width:150px;height:80px">
                        <c:if test="${not empty info.picPath}">
                            <img src="${path}${info.picPath}" style="width:100%;height:100%"/>
                        </c:if>
                    </div>
                </div>
            </c:if>
            <div class="form-group">
                <div class="group">
                    <label class="col-sm-2 control-label" >视频封面</label>
                    <div class="col-sm-10">
                        <input id="file1" type="file" name="file1"  class="file-loading" ><p style="color:red;font-size:16px;">(图片规格：320px*240px)</p>
                    </div>
                </div>

            </div>
            <c:if test="${not empty info.picPath}">
                <div class="form-group">
                    <label class="col-sm-3 control-label"></label>
                    <div class="col-sm-9">
                        <video id="video1" class="video-js vjs-default-skin" src="${path}${info.path}" controls preload="none"  poster="${path}${info.picPath}" data-setup="{}">
                            <source src="${path}${info.path}" type="video/mp4" />
                        </video>
                    </div>
                </div>
            </c:if>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-2 control-label" >视频</label>
                    <div class="col-sm-10">
                        <input id="file2" type="file" name="file2"  class="file-loading" ><p style="color:red;font-size:16px;">(图片规格：320px*240px)</p>
                    </div>
                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">描述</label>
                    <div class="col-sm-9">
                        <textarea class="form-control" name="content" maxlength="50" rows="3">${info.content}</textarea>
                    </div>
                </div>
            </div>
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">排序</label>
                    <div class="col-sm-9">
                            <input class="form-control" name="px"
                                   onkeyup="this.value=this.value.replace(/\D/g,'' )"
                                   onafterpaste="this.value=this.value.replace(/\D/g,'')"value="${info.px}"
                            />
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
        $("#file1").fileinput({
            language: 'zh', //设置语言
            allowedFileExtensions : ['jpg', 'png','gif'],//接收的文件后缀
            showUpload: false, //是否显示上传按钮
            showCaption: false,//是否显示标题
            overwriteInitial:true,
            browseClass: "btn btn-primary", //按钮样式
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            maxFileSize: 5000,
            maxFilesNum: 1
//        initialPreview: [ //预览图片的设置
//                    "<img src='" + imageurl + "' class='file-preview-image' alt='肖像图片' title='肖像图片'>"
//        ]
        });
        $("#file2").fileinput({
            language: 'zh', //设置语言
            showUpload: false, //是否显示上传按钮
            showCaption: false,//是否显示标题
            overwriteInitial:true,
            browseClass: "btn btn-primary", //按钮样式
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            maxFileSize: 10240000,
            maxFilesNum: 1
//        initialPreview: [ //预览图片的设置
//                    "<img src='" + imageurl + "' class='file-preview-image' alt='肖像图片' title='肖像图片'>"
//        ]
        });
        $('#editForm').submit(function () {
            var flag=true;
            var mc=$("#title").val();
            if(mc==""||mc==null){
                alert("请填写标题");
                flag= false;
            }
            if (!flag){
                return false;
            }
        })
        function checkmc(){
            var mc=$("#title").val();
            if(mc==""||mc==null){
                $("#mcDiv").addClass("has-error");
                $("input[name='title']").attr("placeholder","请填写");
                return false;
            }else{
                $("#mcDiv").removeClass("has-error");
                return true;
            }
        }
    </script>

</div>
