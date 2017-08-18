$(function() {
	//菜单回选
	if($("#menu_right").val()!=""){
		var menu_right = $("#menu_right").val().split(",");
		$(".menu_box").each(function(){
			 if(menu_right.indexOf($(this).val())>-1){
				 $(this).attr("checked",true);
			 }
		});
	}
	//权限回选
	if($("#power_right").val()!=""){
		var power_right = $("#power_right").val().split(",");
		$(".power_box").each(function(){
			 if(power_right.indexOf($(this).val())>-1){
				 $(this).attr("checked",true);
			 }
		});
	}
	//第一级菜单
	$(".one_box").click(function(){
		if(this.checked){
			$(this).closest(".one_menu_div").find(".two_box,.three_box,.four_box").prop("checked",true);
		}else{
			$(this).closest(".one_menu_div").find(".two_box,.three_box,.four_box").prop("checked",false);
		}
	});
	//第二级菜单
	$(".two_box").click(function(){
		if(this.checked){
			$(".one_box").prop("checked",true);	
			$(this).closest(".two_menu_div").find(".three_box,.four_box").prop("checked",true);
		}else{
			$(this).closest(".two_menu_div").find(".three_box,.four_box").prop("checked",false);
		}
		var len = $(".two_box").length;
		var thislen = $(".two_box:checked").length;
		if(thislen==0){
			$(".one_box").prop("checked",false);
		}
	});
	//第三级菜单
	$(".three_box").click(function(){
		if(this.checked){
			//一级菜单
			$(".one_box").prop("checked",true);	
			//父节点二级菜单
			$(this).closest(".two_menu_div").find(".two_box").prop("checked",true);
			$(this).closest(".three_menu_div").find(".four_box").prop("checked",true);
		}else{
			$(this).closest(".three_menu_div").find(".four_box").prop("checked",false);
		}
		//第三级选中框无时
		var threelen = $(this).closest(".two_menu_div").find(".three_box:checked").length;
		if(threelen==0){
			$(this).closest(".two_menu_div").find(".two_box").prop("checked",false);
		}
		//第二级选中框无时
		var twolen = $(".two_box:checked").length;
		if(twolen==0){
			$(".one_box").prop("checked",false);
		}
	});
	//第四级菜单权限
	$(".four_box").click(function(){
		if(this.checked){
			//一级菜单
			$(".one_box").prop("checked",true);	
			//父节点二级菜单
			$(this).closest(".two_menu_div").find(".two_box").prop("checked",true);
		}else{
			
			
			//第三级选中框无时
			var threelen = $(this).closest(".two_menu_div").find(".three_box:checked").length;
			if(threelen==0){
				$(this).closest(".two_menu_div").find(".two_box").prop("checked",false);
			}
			//第二级选中框无时
			var twolen = $(".two_box:checked").length;
			if(twolen==0){
				$(".one_box").prop("checked",false);
			}
		}
	});
	
});

//角色表单提交
function  roleSubmitData(){
	var menu_str="";
	var power_str="";
	$(".menu_box").each(function(){
		if(this.checked==true){
			menu_str+=$(this).val()+",";
		}
	});
	$(".power_box").each(function(){
		if(this.checked==true){
			power_str+=$(this).val()+",";
		}
	});
	menu_str = deleteLastChar(menu_str, ",");
	power_str = deleteLastChar(power_str, ",");
	$("#menu_right").val(menu_str);
	$("#power_right").val(power_str);
	submitData();
}