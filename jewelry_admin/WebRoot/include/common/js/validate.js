//参数类型datatype="jsInt" datatype="jsRmb" datatype="jsPhone"
//isrequired="yes" maxdatalength="20" type="select/checkbox/radio/text/hidden/textarea" tipmsg="提示信息"
//select 控件参数  
function submitDataTest(){
	$("#validateForm").submit();
}


//提交表单
var isCheck = true;
function submitData(){
	var is_pass_check = checkSubmitData();
	//验证通过，提交表单
	if(is_pass_check){
		//alert("验证通过");
		$("#validateForm").submit();
	}else{
		goErrorPos();
	}
}

//通过ajax提交表单
function submitForm(url){
    var is_pass_check = checkSubmitData();
    //验证通过，提交表单
    if(is_pass_check){
        $.ajax({
            //url: "/sys/menu/list",
            url: url,
            async: false,
            type: 'post',
            data:$("#validateForm").serialize(),
            dataType: "text",
            success: function (msg) {
            	if(msg=="success"){
                    //清空数据
                    $("#validateForm").find(".layui-form tbody").html("");
                    $("#validateForm").find("input[type='text']").val("");
                    $("#validateForm").find("textarea").val("");
                    $("#validateForm").find("select").val("");
                    $("#area_id").val("");//所属地区
                    $("#cat_id").val("");//所属分类
                    art.dialog({
                        title: '友情提示',
                        content: "操作成功！",
                        time:2000
                    });
				}else{
                    art.dialog({
                        title: '友情提示',
                        content: msg,
                        time:2000
                    });
				}

            }, error: function () {
                   alert("系统繁忙");
            }
        });

    }else{
        goErrorPos();
    }
}
//验证数据
function checkSubmitData(){
	$(".wrong").remove();//全部移除错误提示信息
	isCheck = true;
	$(".validate").each(function(){
		//验证工具
		validation(this);
	})
	return isCheck;
}

//跳转出错位置
function goErrorPos(){
	//定位在出错位置
	$("html,body").animate({scrollTop: $(".wrong").eq(0).offset().top-200}, 100);
}

//提示错误信息
//参数说明 obj:当前对象，tipmsg;提示信息，leftoffset:左偏移，topoffset:右偏移，widthtip:加宽，heighttip:加高
function tipMsg(obj,msg){
	var $this = $(obj);
	//x轴偏量
	var Off_set_x =5;
	//y轴偏量
	var Off_set_y =4;
	//指定 左偏移距离
	var x = $this.offset().left-Off_set_x;
	var leftoffset = $this.attr("leftoffset");
	if (typeof(leftoffset) != "undefined" && leftoffset!="") { 
		x = x + leftoffset;
		x = Number(x) + Number(leftoffset);
	}
	//指定 上偏移距离
	var y = $this.offset().top-Off_set_y;
	var topoffset = $this.attr("topoffset");
	if (typeof(topoffsett) != "undefined" && topoffset!="") { 
		y = Number(y) + Number(topoffset);
	}
	//alert(x+"====="+y);
	//指定 加宽
	var w = $this.width()+Off_set_x;
	var widthtip = $this.attr("widthtip");
	if (typeof(widthtip) != "undefined" && widthtip!="") { 
		w = Number(w) + Number(widthtip);
	}
	//指定 加高
	var h = $this.height()+Off_set_y;
	var heighttip = $this.attr("heighttip");
	if (typeof(heighttip) != "undefined" && heighttip!="") { 
		h = Number(h) + Number(heighttip);
	}
	//设定宽
	var setwidth = $this.attr("setwidth");
	if (typeof(setwidth) != "undefined" && setwidth!="") { 
		w=setwidth;
	}
	//设定高
	var setheight = $this.attr("setheight");
	if (typeof(setheight) != "undefined" && setheight!="") { 
		h=setheight;
	}
	//alert(+x+"==="+y+"==="+w+"==="+h+"==="+msg);
	//创建一个提示层，并设置位置，大小
	$(document.body).append("<span class='wrong' style='left:"+x+"px;top:"+y+"px;width:"+w+"px;height:auto'>"+msg+"</span>");
	//绑定事件
	$(".wrong").on("mouseover", function(){
		$(this).remove();
	});
	isCheck = false;
}

//通用验证方法入口
function validation(obj){
	$this=$(obj);
	var isrequired = $this.attr("isrequired");//是否必填属性
	var datatype =$this.attr("datatype");//验证数据类型
	var mindatalength =$this.attr("mindatalength");//验证最小数据长度
	var maxdatalength =$this.attr("maxdatalength");//验证最大数据长度
	var controltype =$this.attr("type");//需要验证的控件的类型
	//提示信息
	var $tipmsg = $this.attr("tipmsg");
	var tipmsg ="";
	if (typeof($tipmsg) != "undefined" && $tipmsg!="") { 
		tipmsg=$tipmsg;
	}
	//alert(isrequired+"==="+datatype+"==="+maxdatalength+"==="+controltype+"==="+tipmsg);
    var $value = $.trim($this.val());
	//验证文本框必填性
	if(isrequired=="yes" && controltype=="text"){
		if($value==""){
			var msg = "该文本框不能为空!";
			if(tipmsg!=""){
				msg = "请填写"+tipmsg+"!";
			}
			tipMsg(obj,msg);
			return false;
		}
	}
	//验证文本域必填性
	if(isrequired=="yes" && controltype=="textarea"){
		if($value==""){
			var msg = "该文本框不能为空!";
			if(tipmsg!=""){
				msg = "请填写"+tipmsg+"!";
			}
			tipMsg(obj,msg);
			return false;
		}
	}
	//验证密码框必填性
	if(isrequired=="yes" && controltype=="password"){
		if($value==""){
			var msg = "该密码框不能为空!";
			if(tipmsg!=""){
				msg = "请填写"+tipmsg+"!";
			}
			tipMsg(obj,msg);
			return false;
		}
	}
	//验证选择框必选性
	if(isrequired=="yes" && controltype=="select"){
		if($value==""){
			var msg = "请选择选项框!";
			if(tipmsg!=""){
				msg = "请选择"+tipmsg+"!";
			}
			tipMsg(obj,msg);
			return false;
		}
	}
	
	//复选框验证
	if(isrequired=="yes" && controltype=="checkbox"){
		var cbName = $this.attr("name");
		var cbLength =$("input:checkbox[name="+cbName+"]:checked").length;
		if(cbLength==0){
			var msg = "请选择复选框!";
			var changetip =eval($this.attr("changetip"));
			tipmsg = $(changetip).attr("tipmsg");
			if(tipmsg!=""){
				msg = "请选择"+tipmsg+"!";
			}
			tipMsg(changetip,msg);
			return false;
		}
	}
	
	//单选框验证
	if(isrequired=="yes" && controltype=="radio"){
		var rdName = $this.attr("name");
		var rdLength =$("input:radio[name="+rdName+"]:checked").length;
		if(rdLength==0){
			var msg = "请选择单选框!";
			var changetip =eval($this.attr("changetip"));
			tipmsg = $(changetip).attr("tipmsg");
			if(tipmsg!=""){
				msg = "请选择"+tipmsg+"!";
			}
			tipMsg(changetip,msg);
			return false;
		}
	}
	
	//验证隐藏域的必填性
	if(isrequired=="yes" && controltype=="hidden"){
		var changetip =eval($this.attr("changetip"));
		tipmsg = $(changetip).attr("tipmsg");
		if($value==""){
			var msg = "请填写隐藏域中的值!";
			if(tipmsg!=""){
				msg = "请选择"+tipmsg+"!";
			}
			tipMsg(changetip,msg);
			return false;
		}
		var last =$this.attr("level");//验证是否已选到最后一级
		if(last=="last"){
			if($(changetip).find(".select:last-child").val()==""){
				msg = tipmsg + "请选择到最后一级!";
				tipMsg(eval($(changetip)),msg);
				return false;
			}
		}
	}
	
	//验证最大长度
	var minRealLen = getRealLen($value);
	//alert(realLen+"======"+maxdatalength);
	if(minRealLen<mindatalength){
		var msg = "文本框最小长度不能小于"+mindatalength+"!";
		if(tipmsg!=""){
			msg = tipmsg+"不能小于【"+mindatalength+"】个字符限制!";
		}
		tipMsg(obj,msg);
		return false;		
	}
	//验证最小长度
	var realLen = getRealLen($value);
	//alert(realLen+"======"+maxdatalength);
	if(realLen>maxdatalength){
		var msg = "文本框最大长度不能超过"+maxdatalength+"!";
		if(tipmsg!=""){
			msg = tipmsg+"超过最大【"+maxdatalength+"】个字符限制!";
		}
		tipMsg(obj,msg);
		return false;		
	}
	
	//验证数据类型
	switch(datatype){
		case 'jsInt':
		  checkDigital(obj,tipmsg);
		  break;
		case 'jsRmb':
		  checkRMB(obj,tipmsg);
		  break;
		case 'jsEmail':
		  checkEmail(obj,tipmsg);
		  break;
		case 'jsPhone':
		  checkTel(obj,tipmsg);
		  break;
		case 'jsMobile':
		  checkMobile(obj,tipmsg);
		  break;
		case 'jsLetter':
		  checkLetter(obj,tipmsg);
		  break;
		case 'jsLetterOrNum':
			  checkLetterOrNum(obj,tipmsg);
			  break;
		case 'jsLetterNum':
		  checkLetterNum(obj,tipmsg);
		  break;	  
		case 'jsQq':
		  checkQQ(obj,tipmsg);
		  break;	  
		case 'jsCard':
		  checkCard(obj,tipmsg);
		  break;	 	  
		case 'jsLongitude':
		  checkLongitude(obj,tipmsg);
		  break;	 	
		case 'jsLatitude':
		  checkLatitude(obj,tipmsg);
		  break;	 		
		case 'jsLetterline':
		  checkLetterline(obj,tipmsg);
		  break;	
		case 'jsNumline':
		  checkNumLine(obj,tipmsg);
		  break;		  
		case 'jsLong':
		  checkLong(obj,tipmsg);
		  break; 
		case 'jsCenterLine':
		  checkCenterLine(obj,tipmsg);
		  break;  
		default:
		 //alert("0000");
	}
}


$(document).ready(function() {
	$(".validate").on("blur", function(){
		//validation(this);
	});
});




//验证只能输入正整数 >=0 
function checkDigital(obj,tipmsg){
	var num_value=$(obj).val();
	num_value = $.trim(num_value);
    var re =/^(0|([1-9]\d*))$/;
	if (!re.test(num_value) || num_value<0){
		obj.value="";
        obj.focus();
	    if(tipmsg!=""){
	    	msg = tipmsg+"必须是正确的整数!";
	    }
		tipMsg(obj,msg)
		return false;
	}
	return true;	
}

//验证RMB类型
function checkRMB(obj,tipmsg){
	//过滤tab键
	/*evt = evt||window.event;
	if(evt != null){
		if(evt.keyCode == 9){
			return;
		}
	}*/
    var obj_value=$(obj).val();
	//验证是否RMB正则表达式
	var reg = /^(\d){1,8}(\.)?(\d(\d)?)?$/;
	if(!reg.test(obj_value) || obj_value<=0){		
		var msg = "请输入正确的金额!";
		if(tipmsg!=""){
	    	msg = tipmsg+"格式不正确!";
	    }
		tipMsg(obj,msg);
		return false;
	}else{
		return true;
	}
}

//验证手机的有效性
function checkTel(obj,tipmsg){
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
	if(!myreg.test($(obj).val())){ 
	    var msg = "请输入有效的手机号码!";
	    if(tipmsg!=""){
	    	msg = tipmsg+"格式不正确!";
	    }
	    tipMsg(obj,msg);
		return false;
	} 
	return true;
}
		
function checkMobile(obj,tipmsg){
	var myreg = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$|(^(13[0-9]|15[0|2|3|6|7|8|9]|18[3|6|7|8|9])\d{8}$)/; 
	if(!myreg.test($(obj).val())){ 
	    var msg = "请输入有效的联系方式!";
	    if(tipmsg!=""){
	    	msg = tipmsg+"格式不正确!";
	    }
	    tipMsg(obj,msg);
		return false;
	} 
	return true;
}

//校验邮箱
function checkEmail(obj,tipmsg){
	var myreg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/ ; 
	if(!myreg.test($(obj).val()) && $.trim($(obj).val())!=""){ 
	    var msg = "请输入有效的邮箱!";
	    if(tipmsg!=""){
	    	msg = tipmsg+"格式不正确!";
	    }
	    tipMsg(obj,msg);
		return false;
	} 
	return true;
}


//验证字母与数字的组合
function checkLetterNum(obj,tipmsg){  
     var myreg = /^(([a-z]+[0-9]+)|([0-9]+[a-z]+))[a-z0-9]*$/i;
     if (!myreg.test($(obj).val())){  
    	 var msg = "请输入字母与数字的组合!";
    	 if(tipmsg!=""){
 	    	msg = tipmsg+"必须是字母与数字的组合!";
 	     }
 	     tipMsg(obj,msg);
         return false;  
     }else{
    	 return true;  
     }  
} 

//验证字母或数字
function checkLetterOrNum(obj,tipmsg){  
   var myreg = /^[A-Za-z0-9_]*$/;
   if (!myreg.test($(obj).val())){  
  	 var msg = "请输入字母或数字!";
  	 if(tipmsg!=""){
	    	msg = tipmsg+"必须是字母或数字!";
	     }
	     tipMsg(obj,msg);
       return false;  
   }else{
  	 return true;  
   }  
}

//验证字母
function checkLetter(obj,tipmsg){  
    var myreg =  /^[a-zA-Z]+$/;  
    if (!myreg.test($(obj).val())){  
   	 	var msg = "请输入有效的字母!";
   	 	if(tipmsg!=""){
	    	msg = tipmsg+"必须是有效的字母!";
	    }
	    tipMsg(obj,msg);
        return false;  
    }else{
    	return true;  
    }  
} 

//验证QQ号
function checkQQ(obj,tipmsg){  
    var myreg =  /^[1-9]\d{4,8}$/;  
    if (!myreg.test($(obj).val()) && $.trim($(obj).val())!=""){  
   	 	var msg = "请输入有效的QQ!";
   	 	if(tipmsg!=""){
	    	msg = tipmsg+"格式不正确!";
	    }
	    tipMsg(obj,msg);
        return false;  
    }else{
    	return true;  
    }  
} 

//验证经度
function checkLongitude(obj,tipmsg){  
    var myreg = /^-?((0|[1-9]\d?|1[1-7]\d)(\.\d{1,7})?|180(\.0{1,7})?)?$/;  
    if (!myreg.test($(obj).val()) && $.trim($(obj).val())!=""){  
   	 	var msg = "请输入有效的经度!";
   	 	if(tipmsg!=""){
	    	msg = tipmsg+"格式不正确!";
	    }
	   tipMsg(obj,msg);
       return false;  
    }
    return true; 
} 

//验证纬度
function checkLatitude(obj,tipmsg){  
    var myreg =  /^-?(?:90(?:\.0{1,7})?|(?:[1-8]?\d(?:\.\d{1,7})?))$/;  
    if (!myreg.test($(obj).val()) && $.trim($(obj).val())!=""){  
   	 	var msg = "请输入有效的纬度!";
   	 	if(tipmsg!=""){
	    	msg = tipmsg+"格式不正确!";
	    }
	    tipMsg(obj,msg);
        return false;  
    }
    return true;
} 
//验证传真，电话
function checkNumLine(obj,tipmsg){
	var myreg=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	if (!myreg.test($(obj).val()) && $.trim($(obj).val())!=""){  
   	 	var msg = "格式不正确！";
   	 	if(tipmsg!=""){
	    	msg = tipmsg+"格式不正确!";
	    }
	    tipMsg(obj,msg);
        return false;  
    }
    return true;
}

//验证只能是中线或者是中文
function checkCenterLine(obj,tipmsg){
	var num_value = $.trim($(obj).val());
	num_value = num_value.replace("-","");
    var re =/^(0|([0-9]\d*))$/;
	if (!re.test(num_value)&&num_value!=""){
	    if(tipmsg!=""){
	    	msg = tipmsg+"格式不正确!";
	    }
		tipMsg(obj,msg)
		return false;
	}
	return true;	
}

//验证字母下划线
function checkLetterline(obj,tipmsg){
	var myreg =  /^[a-zA-Z0-9_]{1,}$/;
    if (!myreg.test($(obj).val()) && $.trim($(obj).val())!=""){  
   	 	var msg = "格式不正确";
   	 	if(tipmsg!=""){
	    	msg = tipmsg+"字母,数字或下划线!";
	    }
	    tipMsg(obj,msg);
        return false;  
    }
    return true;
}


//验证身份证号
function checkCard(obj,tipmsg){  
	if(!IdentityCodeValid($(obj).val()) && $.trim($(obj).val())!=""){
		var msg = "请输入有效的身份证号码!";
   	 	if(tipmsg!=""){
	    	msg = tipmsg+"格式不正确!";
	    }
   	 	tipMsg(obj,msg);
   	 	return false;  
	}
    return true;  
} 

//中文算两个字符，转成两个单字节后计算长度
function  getRealLen(str) {
	  if (str == null) return 0;
	  if (typeof str != "string"){
	    str += "";
	  }
	  return str.replace(/[^\x00-\xff]/g,"01").length;
}

//校验经纬度，一个文本框中
function checkLong(obj,tipmsg){
	var value = $(obj).val();
	if(value.indexOf(",")==-1){
		tipMsg(obj,"输入的经纬度有误！");
		return false;
	}
	var checklong = value.split(",");  
	if(checklong.length<2){
		tipMsg(obj,"输入的经纬度有误！");
		return false;
	}
	//验证数据
	var longVal = checklong[0].replace(".","").replace("-","");
	var lohtVal = checklong[1].replace(".","").replace("-","");
	//验证正数
	var re =/^(0|([1-9]\d*))$/;
	if (!re.test(longVal)&&longVal!=""){
		tipMsg(obj,"输入的经纬度有误！");
		return false;
	}
	if (!re.test(lohtVal)&&longVal!=""){
		tipMsg(obj,"输入的经纬度有误！");
		return false;
	}
	$(obj).parent("td").find(".longitude").val(checklong[0]);	
	$(obj).parent("td").find(".latitude").val(checklong[1]);	
	return true;
	}


//身份证号合法性验证 ,支持15位和18位身份证号,支持地址编码、出生日期、校验位验证
function IdentityCodeValid(code) { 
  var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
  var tip = "";
  var pass= true;
  
  if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
      tip = "身份证号格式错误";
      pass = false;
  }
  
 else if(!city[code.substr(0,2)]){
      tip = "地址编码错误";
      pass = false;
  }
  else{
      //18位身份证需要验证最后一位校验位
      if(code.length == 18){
          code = code.split('');
          //∑(ai×Wi)(mod 11)
          //加权因子
          var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
          //校验位
          var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
          var sum = 0;
          var ai = 0;
          var wi = 0;
          for (var i = 0; i < 17; i++)
          {
              ai = code[i];
              wi = factor[i];
              sum += ai * wi;
          }
          var last = parity[sum % 11];
          if(parity[sum % 11] != code[17]){
              tip = "校验位错误";
              pass =false;
          }
      }
  }
  return pass;
}