<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>测试</title>
<!-- 	<script type="text/javascript" src="/admin/com/js/jquery-3.2.1.min.js"></script> -->
<script type="text/javascript">
		function mysub() {
			var user = {
					"name":"xiaoxian",
					"age":31
			};
			$.ajax({
				data:user,
				type:"POST",
				dataType:"json",
				url:"/admin/test/ajax",
				//xhrFields:{withCredentials: true},
				error:function(data){
					alert("fail");
				},
				success:function(data) {
					var code = data.code;
					var desc = data.desc;
					if (code!=0) {
						alert("fail");
						return;
					}
					
					var info = data.info;
					cbOk(info);	//回调
				}
			});
		}
		
		function cbOk(info) {
			alert("hello");
		}
	</script>
</head>
<body>
	<input id="mysubmit" type="button" value="ajax提交" onclick="mysub();" />
</body>
</html>