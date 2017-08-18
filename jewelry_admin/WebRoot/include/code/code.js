//初始加载
$(document).ready(function(){ 
	//表格样式
	$("#db_table").projectTable();
	//验证
	$(".field_type").change(function(){
		fieldChange(this)
	})
	//checkbox框改变值
	$(".is_must,.add_edit_field,.file_type,.method_name").click(function(){
		if(this.checked==true){
			$(this).val("yes");
		}else{
			$(this).val("no");
		}
	});
	//全选生成页面
	$(".all_file_type").click(function(){
		if(this.checked==true){
			$(".file_type").each(function(){
				$(this).val("yes");
				$(this).attr("checked","checked");
			});
		}else{
			$(".file_type").each(function(){
				$(this).val("no");
				$(this).removeAttr("checked");
			});
		}
	})
	//全选生成方法
	$(".all_method_name").click(function(){
		if(this.checked==true){
			$(".method_name").each(function(){
				$(this).val("yes");
				$(this).attr("checked","checked");
			});
		}else{
			$(".method_name").each(function(){
				$(this).val("no");
				$(this).removeAttr("checked");
			});
		}
	})
	//表主键设置不可选
	$(".field_type").each(function(){
		var field_type = $(this).val();
		var $tr = $(this).closest("tr");
		if(field_type=="tbkey"){
			/*$(this).attr("disabled","disabled");
			$tr.find(".data_type").attr("disabled","disabled");
			$tr.find(".field_length").attr("disabled","disabled");
			$tr.find(".field_note").attr("disabled","disabled");
			$tr.find(".tip_msg").attr("disabled","disabled");
			$tr.find(".add_edit_field").attr("disabled","disabled");
			$tr.find(".add_edit_field_sort").attr("disabled","disabled");
			$tr.find(".show_list_field").attr("disabled","disabled");
			$tr.find(".show_list_field_sort").attr("disabled","disabled");
			$tr.find(".search_list_field").attr("disabled","disabled");
			$tr.find(".search_list_field_sort").attr("disabled","disabled");
			$tr.find(".is_must").attr("disabled","disabled");
			$tr.find(".default_value").attr("disabled","disabled");*/
		}else if(field_type=="text" || field_type=="textarea"){
			//$tr.find(".default_value").attr("disabled","disabled");
		}
	});
	//必填属性改变状态事件
	$(".is_must").click(function(){
		var $tr = $(this).closest("tr");
		if(this.checked==true){
			//$tr.find(".tip_msg").removeAttr("disabled");
		}else{
			//$tr.find(".tip_msg").attr("disabled","disabled");
		}
	});
	//检测必填提示信息框的属性
	$(".is_must").each(function(){
		var val = $(this).val();
		if(val=="no"){
			var $tr = $(this).closest("tr");
			//$tr.find(".tip_msg").attr("disabled","disabled");
		}
	});
});

//查询验证
function selectTb(){
	//验证表名
	$("#selError").html("");
	if($("#table_name").val()==""){
		$("#selError").html("请输入表名!");
		return;
	}
	if($("#table_key").val()==""){
		$("#selError").html("请输入表主键!");
		return;
	}
	$("#tbForm").attr("action","/dbSelect").submit();
}

//生成代码
function codeBuild(){
	var is_code_submit=true;
    //验证生成的类或页面是否有选择
	var file_type_len=0;
	$(".file_type").each(function(){
		if($(this).val()=="yes"){
			file_type_len+=1;
		}
	});
	if(file_type_len==0){
		alert("请选择需要生成的类或页面！");
		is_code_submit=false;
	}
	if(!is_code_submit) return;
	//验证需要生成的方法是否有选择
	var method_name_len=0;
	$(".method_name").each(function(){
		if($(this).val()=="yes"){
			method_name_len+=1;
		}
	});
	if(method_name_len==0){
		alert("请选择需要生成的类或页面！");
		is_code_submit=false;
	}
	if(!is_code_submit) return;
	//验证字段说明，提示信息，默认值不能为空
	$(".field_note,.tip_msg,.default_value").each(function(){
		if($(this).val()=="" && $(this).attr("disabled")!="disabled"){
			var cssClass = $(this).attr("class");
			//验证空
			if(cssClass=="field_note"){
				tipMsg(this,"字段说明不能为空！");
				is_code_submit=false;
			}else if(cssClass=="tip_msg"){
				tipMsg(this,"提示信息不能为空！");
				is_code_submit=false;
			}else if(cssClass=="default_value"){
				//tipMsg(this,"默认值不能为空！"); 暂去
				//is_code_submit=false;
			}
		}
	});
	if(!is_code_submit) return;
	//验主默认值select框的格式
	$("#db_table").find("tr:gt(0)").each(function(){
		var type = $(this).find(".field_type").val();
		if(type=="select" || type=="radio"){
			var $dv = $(this).find(".default_value").val();
			if($dv!=""){
				if($dv.indexOf("|")>-1){
					var vals = $dv.split("|");
					for(var i=0;i<vals.length;i++){
						if(vals[i].indexOf(":")==-1){
							alert("select或radio默认值格式不正确！格式：key:value|key:value");
							is_code_submit=false;
						}
					}
				}else{
					if($dv.indexOf(":")==-1){
						alert("select或radio默认值格式不正确！格式：key:value|key:value");
						is_code_submit=false;
					}
				}
			}
			$()
		}
	});
	//提交表单
	if(is_code_submit){
		//执行生成代码
		$.ajax({
	        cache: true,
	        type: "POST",
	        url:"/codeBuild",
	        data:$('#tbForm').serialize(),
	        async: false,
	        error: function(request) {
	            alert("Connection error");
	        },
	        success: function(data) {
	            alert("生成成功!");
	        }
	    });
	}
}

//字段类型选择改变事件
function fieldChange(obj){
	var type = $(obj).val();
	var $tr = $(obj).closest("tr");
	var line_num = $tr.find("td").eq(0).html();
	var is_must = $tr.find(".is_must").val();
	//初始值
	initAttr($tr);
	/*
	//select，radio，图片控件
	if(type=="select" || type=="radio" || type=="imgcontrol"){
		$tr.find(".data_type").attr("disabled","disabled");
		$tr.find(".field_length").attr("disabled","disabled");
		//图片控件
		if(type=="imgcontrol"){
			$tr.find(".default_value").attr("disabled","disabled");
		}
	//隐藏域	
	}else if(type=="hidden"){
		$tr.find(".data_type").attr("disabled","disabled");
		$tr.find(".field_length").attr("disabled","disabled");
		$tr.find(".field_note").attr("disabled","disabled");
		$tr.find(".add_edit_field").attr("disabled","disabled");
		$tr.find(".add_edit_field_sort").attr("disabled","disabled");
		$tr.find(".show_list_field").attr("disabled","disabled");
		$tr.find(".show_list_field_sort").attr("disabled","disabled");
		$tr.find(".search_list_field").attr("disabled","disabled");
		$tr.find(".search_list_field_sort").attr("disabled","disabled");
		$tr.find(".is_must").attr("disabled","disabled");
		$tr.find(".default_value").attr("disabled","disabled");
	//编辑器	
	}else if(type=="ueditor"){
		$tr.find(".data_type").attr("disabled","disabled");
		$tr.find(".field_length").attr("disabled","disabled");
		$tr.find(".add_edit_field").attr("disabled","disabled");
		$tr.find(".add_edit_field_sort").attr("disabled","disabled");
		$tr.find(".show_list_field").attr("disabled","disabled");
		$tr.find(".show_list_field_sort").attr("disabled","disabled");
		$tr.find(".search_list_field").attr("disabled","disabled");
		$tr.find(".search_list_field_sort").attr("disabled","disabled");
		$tr.find(".is_must").attr("disabled","disabled");
		$tr.find(".default_value").attr("disabled","disabled");
	}else if(type=="text" || type=="textarea"){
		//$tr.find(".default_value").attr("disabled","disabled");
	}*/
}

//移除属性
function initAttr($tr){
	$tr.find(".data_type").removeAttr("disabled"); 
	$tr.find(".field_length").removeAttr("disabled"); 
	$tr.find(".field_note").removeAttr("disabled"); 
	$tr.find(".add_edit_field").removeAttr("disabled"); 
	$tr.find(".add_edit_field_sort").removeAttr("disabled"); 
	$tr.find(".show_list_field").removeAttr("disabled"); 
	$tr.find(".show_list_field_sort").removeAttr("disabled"); 
	$tr.find(".search_list_field").removeAttr("disabled"); 
	$tr.find(".search_list_field_sort").removeAttr("disabled"); 
	$tr.find(".is_must").removeAttr("disabled"); 
	$tr.find(".field_note").removeAttr("disabled"); 
	$tr.find(".default_value").removeAttr("disabled"); 
}