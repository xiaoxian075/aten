var no_picture="/include/admin/image/no_picture.png";
//上传图片的方法   ident:标识
function image_one_upload(ident){
	var file_id = "file_"+ident;
	var queue_id = "fileQueue_"+ident;
	uploadComponent(file_id,queue_id,false,ident,"car");
}
//汽车多图片上传处理
function image_carMore_upload(ident){
	var file_id = "file_"+ident;
	var queue_id = "fileQueue_"+ident;
	uploadComponent(file_id,queue_id,false,ident,"carMore");
}
//图库多图片上传
function image_more_upload(ident){
	var file_id = "file_"+ident;
	var queue_id = "fileQueue_"+ident;
	uploadComponent(file_id,queue_id,true,ident,"more");
}


//上传组件
//file_id 上传控件标识,queue_id 显示序列ID ,is_multi 是否开启多图片上传
//other 通过传递参数,来处理相应的操作
function uploadComponent(file_id,queue_id,is_multi,ident,other){
  $("#"+file_id).uploadify({ 
	   'auto':true,       //是否允许自动上传
	   'swf' :'/site/component/uploadify3.2.1/uploadify.swf', //引入flash
	   'buttonText':'上传图片',     //设置button文字
	   'removeCompleted': true,//是否移除掉队列中已经完成上传的文件。false为不移除
	   'removeTimeout': 0,//设置上传完成后删除掉文件的延迟时间，默认为3秒。
	   'height': '22',
       'width': '80', 
	   'method':'PSOT',     //提交方式
	   'multi':is_multi,      //是否多文件上传
	   'fileObjName' : 'myFile',   //文件对象名称,用于后台获取文件对象时使用
	   'preventCaching':'true',   //防止浏览器缓存
	   //'formData':{'JSESSIONID':$("#sessionGetId").val()}, //动态传参
	   'queueID': queue_id,  //显示在某个div的位置  custom-queue div的id 
	   'buttonImage' : '', 
	   //'uploader' : '/upload/image;jsessionid='+$("#sessionGetId").val(),
	   'uploader' : '/admin/upload/image',
	   'fileTypeExts' : '*.jpg;*.png;*.gif',
	   
	   //选择图片完成
       'onSelect' : function() {
    	   //cancel(fileID, suppressEvent)
       },
	   //onUploadStart 动态传参的关键
	   'onUploadStart':function(){
		   //$("#file_upload").uploadify("settings","formData",{'emergencyId': $("#id").val()});
		   $("#"+file_id).uploadify("settings");
	   },
	   'onFallback' : function() {//检测FLASH失败调用  
	     alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");  
	    
	   },  
	   //上传成功回调涵数
	   'onUploadSuccess':function(file, data, response){  
		   		 //alert(data+"response:"+response);
		   		//var Data = eval('('+data+')');
	            //alert(file.name+"上传"+data.result);
		   		//汽车模块图片上传特殊处理
		   		if(other=="car"){
		   			carOther(ident,data);
		   		}else if(other=="carMore"){
		   			carMore(ident,data);
		   		}else if(other=="more"){
		   			moreImage(ident,data);
		   		}
	   },onError: function(event, queueID, file){    
	              alert(file.name + " 上传失败");    
	   },'onQueueComplete':function(stats) {  
	           //版本不一样方法也存在差异
	           //alert("成功上传的文件数：" + stats.uploadsSuccessful + " =上传出错的文件数：" +stats.uploadsErrored + " -上传的文件总大小：" + stats.uploadSize);
	           //cancel();
	   },
  	}); 
}
//单张图片
function carOther(ident,data){
	var img_id = "img_"+ident;
	var hidden_id = "hidden_"+ident;
	$("#"+img_id).attr("src",data);
	$("#"+hidden_id).val(data);
}
//多张图片上传组
function carMore(ident,data){
	var img_id = "img_"+ident;
	var hidden_id = "hidden_"+ident;
	$("#"+img_id).attr("src",data);
	$("#"+hidden_id).val(data);
	var imgstr="";
	$(".car_image_list").each(function(){
		if($(this).val()!="" && $(this).val().indexOf("no_picture")==-1){
			imgstr+=$(this).val()+",";
		}
	})
	imgstr = deleteLastChar(imgstr, ",");
	$("#car_image").val(imgstr);
}
//全局图片库数数量统计
var img_num=0;
$(document).ready(function(){ 
	if($("#hidden_img_num").length>0){
		var hid_img_num = Number($("#hidden_img_num").val());
		if(hid_img_num==0){
			img_num=0;
		}else{
			img_num=hid_img_num+1;
		}
	}
});
//多图片无限制上传显示
function moreImage(ident,data){
	var img_type=$("#hidden_"+ident).val();
	var liStr="<li>";
	liStr+="<img class='li_img' src='"+data+"'/>";
	liStr+="<img class='li_del_img' onclick='delMoreImage(this);' src='/include/admin/image/delete.png'>";
	liStr+="<input type='hidden' name='carImageList["+img_num+"].img_path' value='"+data+"'/>";
	liStr+="<input type='hidden' name='carImageList["+img_num+"].img_type' value='"+img_type+"'/>";
	liStr+="</li>";
	$("#ul_"+ident).append(liStr);
	img_num+=1;//自增
}
//删除图片
function delMoreImage(obj){
	$(obj).parent("li").remove();
}
//取消单个图片选择
function cancelImg(ident){
	$("#hidden_"+ident).val("");
	$("#img_"+ident).attr("src",no_picture);
}

//取消多个单图片选择,选择数据
function cancelMoreImg(ident){
	$("#hidden_"+ident).val("");
	$("#img_"+ident).attr("src",no_picture);
	var imgstr="";
	$(".car_image_list").each(function(){
		if($(this).val()!="" && $(this).val().indexOf("no_picture")==-1){
			imgstr+=$(this).val()+",";
		}
	})
	imgstr = deleteLastChar(imgstr, ",");
	$("#car_image").val(imgstr);
}
