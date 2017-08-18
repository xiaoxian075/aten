<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增物流承运商</title>
<script type="text/javascript">
    	function checkfastcode() {
    		var fast_code = $("#fast_code").val();
    		if (fast_code==null || fast_code=="")
    			return;
    			
    		$.ajax({
    		      url: "/admin/fastmail/checkcode",
    		      data: {"fast_code":fast_code},
    		      type: "POST",
    		      success: function (data) {
    		    	  data = JSON.parse(data);
    		        if (data.code != 0){
    		        	alert(data.desc);
    		        	$("#fast_code").val("");
    		        }
    		       },
    		       error: function () {
    		         alert("添加失败！");
    		       }
    		      });
    	}

	function checkfastname() {
		var fast_name = $("#fast_name").val();
		if (fast_name==null || fast_name=="")
			return;
			
		$.ajax({
		      url: "/admin/fastmail/checkname",
		      data: {"fast_name":fast_name},
		      type: "POST",
		      success: function (data) {
		    	  data = JSON.parse(data);
		        if (data.code != 0){
		        	alert(data.desc);
		        	$("#fast_name").val("");
		        }
		       },
		       error: function () {
		         alert("添加失败！");
		       }
		      });
	}
    </script>
</head>
<body>
	<form id="validateForm" action="/admin/fastmail/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/fastmail/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="新增物流承运商" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/fastmail/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

