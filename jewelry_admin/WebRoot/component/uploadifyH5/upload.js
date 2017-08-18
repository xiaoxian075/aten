var no_picture=$("#noPicture").val();//"/include/admin/image/no_picture.png";
var oss_url = $("#ossImgServerUrl").val();

//上传单示例图片上传
function image_one_upload(ident){
	//上传控件ID
	var file_id="file_"+ident;
	uploadComponent(file_id,null,false,ident,"hotel");
}

//多示例图片上传
function image_more_upload(ident){
	var file_id = "file_"+ident;
	uploadComponent(file_id,null,false,ident,"more");
}

//图库多图片上传处理
function image_pictureMore_upload (ident){
	var file_id = "file_"+ident;
	var queue_id = "fileQueue_"+ident;
	uploadComponent(file_id,null,true,ident,"picture");
}


//二次封装图片上传控件
//file_id 上传控件标识,queue_id 显示序列ID ,is_multi 是否开启多图片上传
//other 通过传递参数,来处理相应的操作
function uploadComponent(file_id,queue_id,is_multi,ident,other){
	no_picture=$("#noPicture").val();
	oss_url = $("#ossImgServerUrl").val();
	$('#'+file_id).Huploadify({
		auto:true,
		fileTypeExts:'*.png;*.jpg;*.ico;*.gif;',
		multi:is_multi,
		formData:null,//发送给服务端的参数，格式：{key1:value1,key2:value2}  
	    fileObjName:'myFile',//在后端接受文件的参数名称
	    fileSizeLimit:2048,//允许上传的文件大小，单位KB  
		showUploadedPercent:false,
		showUploadedSize:false,
		removeTimeout:10000,
		buttonText:'上传图片',
		removeTimeout: 50,//上传完成后进度条的消失时间，单位毫秒  
		//uploader:'/admin/upload/image',
		uploader:'/admin/upload/oss',
		onUploadStart:function(data){
			console.log(data.name+'开始上传');
		},
		onInit:function(obj){
			console.log('初始化');
			console.log(obj);
		},
		onUploadComplete:function(data){
			console.log(data.name+'上传完成');
		},
		onCancel:function(data){
			console.log(data.name+'删除成功');
		},
		onClearQueue:function(queueItemCount){
			console.log('有'+queueItemCount+'个文件被删除了');
		},
		onDestroy:function(){
			console.log('destroyed!');
		},
		onSelect:function(data){
			console.log(data.name+'加入上传队列');
		},
		onQueueComplete:function(queueData){
			console.log('队列中的文件全部上传完成',queueData);
			if(other=="picture"){
				$("#pictureForm").submit();
	   		}
		},
		onUploadSuccess:function(file,data){  
            if(other=="hotel"){
	   			hotelOther(ident,data);
	   		}else if(other=="picture"){
	   			loadPicture(ident,data);
	   		}else if(other=="more"){
	   			moreImage(ident,data);
	   		}
        },  
        onUploadError:function(file,response){  
            jQuery.longhz.alert("上传失败!");  
        }  
	});
}

//多图片上传
function loadPicture(ident,data){
	var s=data.lastIndexOf("/");
	var e=data.lastIndexOf(".");
	//处理数据
	var picture_name = data.substring(s+1,e);
	var img_path = data.replace(oss_url, "");
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
	    },error : function(err) {
	    	console.log(err);
	    }    
	});  
}



//单张图片
function hotelOther(ident,data){
	var img_id = "img_"+ident;
	var hidden_id = "hidden_"+ident;
	$("#"+img_id).attr("src",data);
	var real_data = data.replace(oss_url, "");
	$("#"+hidden_id).val(real_data);
}

//多示例图片上传
function moreImage(ident,data){
	var img_id = "img_"+ident;
	var hidden_id = "hidden_"+ident;
	$("#"+img_id).attr("src",data);
	var real_data = data.replace(oss_url, "");
	$("#"+hidden_id).val(real_data);
	var imgstr="";
	$(".more_image_list").each(function(){
		if($(this).val()!="" && $(this).val().indexOf(no_picture)==-1){
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
	$("#hidden_detail_"+ident).val("");
	$("#img_detail_"+ident).attr("src",no_picture);
	var imgstr="";
	$(".more_detail_image").each(function(){
		var imgSrc = $(this).attr("src");
		if(imgSrc!="" && imgSrc.indexOf(no_picture)==-1){
			imgstr+=$(this).parent().children(".more_image_list").val()+",";
		}
	})
	imgstr = deleteLastChar(imgstr, ",");
	$("#more_image").val(imgstr);
}