var no_picture="/include/admin/image/no_picture.png";

//上传图片的方法   ident:标识
function image_one_upload(ident){
	var file_id = "file_"+ident;
	var queue_id = "fileQueue_"+ident;
	uploadComponent(file_id,queue_id,false,ident,"hotel");
}

//图库多图片上传处理
function image_pictureMore_upload (ident){
	var file_id = "file_"+ident;
	var queue_id = "fileQueue_"+ident;
	uploadComponent(file_id,queue_id,true,ident,"picture");
}

//多示例图片上传
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
	   'swf' :'/component/uploadify3.2.1/uploadify.swf', //引入flash
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
		   		if(other=="hotel"){
		   			hotelOther(ident,data);
		   		}else if(other=="picture"){
		   			loadPicture(ident,data);
		   		}else if(other=="more"){
		   			moreImage(ident,data);
		   		}
	   },onError: function(event, queueID, file){    
	              alert(file.name + " 上传失败");    
	   },onQueueComplete:function(stats) {  
		   $("#pictureForm").submit();
           //版本不一样方法也存在差异
           //alert("成功上传的文件数：" + stats.uploadsSuccessful + " =上传出错的文件数：" +stats.uploadsErrored + " -上传的文件总大小：" + stats.uploadSize);
           //cancel();
	   },
  	}); 
}

//多图片上传
function loadPicture(ident,data){
	var s=data.lastIndexOf("/");
	var e=data.lastIndexOf(".");
	//处理数据
	var picture_name = data.substring(s+1,e);
	var img_path = data;
	var gal_id =$("#gp_id").val();
	//ajax 保存数据
	$.ajax( {    
	    url:'/admin/picture/insert',// 跳转到 action    
	    data:{    
	    	 picture_name : picture_name,    
             img_path : img_path,    
             gal_id : gal_id
	    },    
	    type:'post',    
	    cache:false,    
	    dataType:'text',    
	    success:function(data) {   
	    	/*$("#selectTb").remove();
	    	var liHtml="<li class='gal_list_li'><a href='###'>";
	    	liHtml+="<img class='gal_list_li_img img' src='"+img_path+"'> </a>";
	    	liHtml+="<p class='gal_list_li_p'>"+picture_name+"</p>";
	    	liHtml+="</li>";
	    	$(".gal_list_div").prepend(liHtml);*/
	   },error : function(err) {
	    	console.log(err);
	        alert("请求失败");   
	    }    
	});  
}

//单张图片
function hotelOther(ident,data){
	var img_id = "img_"+ident;
	var hidden_id = "hidden_"+ident;
	$("#"+img_id).attr("src",data);
	$("#"+hidden_id).val(data);
}

//多张图片上传组
function moreImage(ident,data){
	var img_id = "img_"+ident;
	var hidden_id = "hidden_"+ident;
	$("#"+img_id).attr("src",data);
	$("#"+hidden_id).val(data);
	var imgstr="";
	$(".more_image_list").each(function(){
		if($(this).val()!="" && $(this).val().indexOf("no_picture")==-1){
			imgstr+=$(this).val()+",";
		}
	})
	imgstr = deleteLastChar(imgstr, ",");
	$("#more_image").val(imgstr);
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
