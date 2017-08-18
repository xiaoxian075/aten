<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +  path+"/";
%>
<script>
    var basePath = "<%=basePath%>";
</script>
<%--<meta charset="utf-8"/>--%>
<%--<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />--%>
<%--<meta http-equiv="X-UA-Compatible" content="IE=9" />--%>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<%--<meta name="description" content=""/>--%>
<%--<meta name="author" content=""/>--%>

<link href="<%=basePath%>css/bootstrap/metisMenu.min.css" rel="stylesheet"/>
<link href="<%=basePath%>css/bootstrap/timeline.css" rel="stylesheet"/>
<link href="<%=basePath%>css/bootstrap/morris.css" rel="stylesheet"/>
<link href="<%=basePath%>css/bootstrap/font-awesome.min.css" rel="stylesheet" type="text/css"/>

<link href="<%=basePath%>jquery_ui/css/cupertino/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>jquery_ui/css/showLoading.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>assets/css/ztree/demo.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>assets/css/ztree/zTreeStyle/zTreeStyle.css" type="text/css">
<!--[if lte IE 6]>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/bootstrap-ie6.css"/>
<![endif]-->
<!--[if lte IE 7]>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/ie.css"/>
<![endif]-->
<link href="<%=basePath%>css/bootstrap/bootstrap.min.css" rel="stylesheet"/>
<link href="<%=basePath%>css/bootstrap/admin.css" rel="stylesheet"/>
<link href="<%=basePath%>css/ace.min.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
    if("ontouchend" in document) document.write("<script src='<%=basePath%>assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
</script>
<script type="text/javascript" src="<%=basePath%>assets/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/typeahead-bs2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/jquery.easy-pie-chart.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/jquery.sparkline.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/flot/jquery.flot.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/flot/jquery.flot.pie.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/flot/jquery.flot.resize.min.js"></script>
<!-- ace scripts -->
<script type="text/javascript" src="<%=basePath%>assets/js/ace-elements.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/ace.min.js"></script>
<%--choose plugin--%>
<script src="<%=basePath%>assets/js/chosen.jquery.min.js"></script>
<%--修复克隆时无法克隆textarea和select元素值的插件--%>
<script src="<%=basePath%>assets/js/jquery.fix.clone.js"></script>
<%--ztree--%>
<script type="text/javascript" src="<%=basePath%>assets/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<%--jquery datatables --%>
<script type="text/javascript" src="<%=basePath%>assets/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/jquery.dataTables.bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/handlebars-v3.0.1.js"></script>
<%--datetimepicker--%>
<script type="text/javascript" src="<%=basePath%>assets/js/date-time/bootstrap-timepicker.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/date-time/bootstrap-datepicker.min.js"></script>
<%--jquery-raty--%>

<script type="text/javascript" src="<%=basePath%>assets/js/jquery.raty/jquery.raty.js"></script>
<%--fileupload--%>
<script type="text/javascript" src="<%=basePath%>assets/js/ajaxfileupload.js"></script>
<%--<script type="text/javascript" src="<%=basePath%>assets/plugins/jquery-file-upload/css/jquery.fileupload-ui.css"></script>--%>
<%--<script type="text/javascript" src="<%=basePath%>assets/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>--%>
<!-- inline scripts related to this page -->
<script type="text/javascript" src="<%=basePath%>assets/js/common/common.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/common/init.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/jquery.gritter.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/app.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap/datePicker/WdatePicker.js"></script>
<%--<script type="text/javascript" src="<%=basePath%>js/withdraw.js"></script>--%>


<link href="<%=basePath%>css/bootstrap/chosen.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>css/fileinput.min.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<%=basePath%>js/bootstrap/chosen.jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/fileinput.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/fileinput_locale_zh.js"></script>
<script type="text/javascript" src="<%=basePath%>js/file_Portrait.js"></script>
<%--<%@ include file="/WEB-INF/views/admin/common/img_control.jsp"%>--%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/component/uploadify3.2.1/uploadify.css"/>
<script type="text/javascript"  src="<%=basePath%>/component/uploadify3.2.1/jquery.uploadify.min.js"></script>
<script type="text/javascript"  src="<%=basePath%>/component/uploadify3.2.1/upload.js"></script>