//初始加载
$(document).ready(function(){ 
	var pr = $("#plat_role").val();
	$("#menu_id_div").html("");
	//分类级联
	$("#menu_id_div").cascadeSel({
		 url:"/admin/sysmenu/normalList?pr="+pr,
		 name:"menu_id",
		 li_id:"menu_id",
	     li_name:"menu_name",
	     range_val:""
	});	
	$("#plat_role").change(function(){
		var pr = $(this).val();
		$("#menu_id_div").html("");
		//分类级联
		$("#menu_id_div").cascadeSel({
			 url:"/admin/sysmenu/normalList?pr="+pr,
			 name:"menu_id",
			 li_id:"menu_id",
		     li_name:"menu_name",
		     range_val:""
		});	
	});	
});


//权限菜单提交
function powerSubmit(){
	var power_is_pass_check =true;
	power_is_pass_check = checkSubmitData();
	/*if($("#menu_id").attr("isrequired")=='yes'){
		var last_val = $("#menu_id_div").find("select:last option:selected").val();
		if(last_val==""){
			tipMsg($("#menu_id_div"));
			power_is_pass_check =false;
		}
	}*/
	//验证成功后提交
	if(power_is_pass_check){
		$("#validateForm").submit();
	}
	
}
