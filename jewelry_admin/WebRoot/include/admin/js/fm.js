/*-----运费模板style ----*/
/*-----Auther:cbq-----*/
/*----Version:1.0.0.0----*/

/****运费模板新增页脚本*****/
$(document).ready(function () {
  setInterval ("$('.wrong').remove()", 6000);
  /**
   * [为指定城市添加运费点击事件]
   */
  var num=0;//计数
  $(document).on('click','.fm_set_up_freight',function(){
    var fmPricingWays = $("#fmPricingWays input[type='radio']:checked").val();
    num = $('#FM_Tbody tr.setAreaList').length;
    $('input:checkbox[name="chkSetAreaList"].all').prop('checked',false)
    var obj = {
      areasTxt:'未选择地区',
      areasId:'',
      express_start:"1",
      express_price:"",
      express_plus:'1',
      express_priceplus:'',
      as_id: 0,
      ship_id : 1       
    }//设置地区运费对象      
  //   alert(fmPricingWays)
    switch(fmPricingWays){
      case "0": 
        addAreaListHandle(this,addAreaHead("件","（件）"),addAreaList(obj,num))
        break;
      case "1":
        addAreaListHandle(this,addAreaHead("重量","（kg）"),addAreaList(obj,num))
        break;
      case "2":
        addAreaListHandle(this,addAreaHead("体积","（m³）"),addAreaList(obj,num))
        break;
      default:  
        break 
    }                   
    num++;
    // alert(num)
  })   

  //运送方式选择
  // $("input[name='tplType']").each(function () {
  //     $(this).change(function () {
  //         var value = $(this).val();
  //         var text = $(this).next("label").text();
  //         if ($(this).prop('checked')) {
  //             if ($("#J_buyerBearFre").prop('checked')) {
  //                 $(this).parent().next("div").show();
  //             }
  //             $(".J_Service").each(function () {
  //                 $(this).append("<option value='" + value + "'>" + text + "</option>");
  //             });
  //             return;
  //         }
  //         else {
  //             $(this).parent().next("div").hide();
  //             $(".J_Service option[value='" + value + "']").remove();
  //             return;
  //         }
  //     });
  // });

  //选大区域或省
  // $(".J_Group,.J_Province").each(function () {
  //     $(this).change(function () {   
  $(document).on('change','.J_Group,.J_Province',function(){
          // console.log($(this).closest('span').hasClass("group-label"))
          if ($(this).closest('span').hasClass("group-label")) {
             // alert(11111)
              //省份
              if ($(this).prop('checked')) {
                  $(this).siblings('img.indeterminate').hide()
                  $(this).closest('.gcity').siblings('.province-list').find('img.indeterminate').hide()
                  $(this).closest('.gcity').next("div").find("input[type='checkbox']").prop("checked", true);
                  return;
              } else {
                $(this).siblings('img.indeterminate').hide()
                $(this).closest('.gcity').siblings('.province-list').find('img.indeterminate').hide()
                $(this).closest('.gcity').next("div").find("input[type='checkbox']").prop("checked", false);
                return;
              }
          }
          else {
             // alert(222)
              //城市
              $(this).siblings('img.indeterminate').hide()
              console.log($(this).prop('checked'))
              if ($(this).prop('checked')) {
                  $(this).closest('.ecity').children().find("input[type='checkbox']").prop("checked", true);
                  var checkedL = $(this).closest('.province-list').find(".gareas input[type='checkbox']:checked").length;
                  var checkedBoxL = $(this).closest('.province-list').find(".gareas input[type='checkbox']").length;
                  // alert(checkedL+''+checkedBoxL)
                  if(checkedL>0 && checkedL!=checkedBoxL){
                    $(this).closest('.province-list').prev('.gcity').find('img.indeterminate').show()
                  }else{
                    $(this).closest('.province-list').prev('.gcity').find('img.indeterminate').hide()
                  }                    
              } else {
                  $(this).closest('.ecity').children().find("input[type='checkbox']").prop("checked", false);
                  var checkedL = $(this).closest('.province-list').find(".gareas input[type='checkbox']:checked").length;
                  if(checkedL>0){
                    $(this).closest('.province-list').prev('.gcity').find('img.indeterminate').show()
                  }else{
                    $(this).closest('.province-list').prev('.gcity').find('img.indeterminate').hide()
                  }
              }
              var checkAll = true;
              $(this).closest('.province-list').find("input.J_Province").each(function () {
                  if (!$(this).prop("checked")) {
                      checkAll = false;
                  }
                  return checkAll;
              });
              $(this).closest('.province-list').prev(0).find("input.J_Group").prop("checked", checkAll);
          }
      });
  //选择区县
  // $(".J_City").each(function () {
  //     $(this).change(function () {
  $(document).on('change','.J_City',function(){
      // alert(222)
      var sum = 0;
      var cksum = 0;
      var psun = 0;
      var pcksum = 0;
      $(this).closest('.citys').children().find("input").each(function () {
          sum = sum + 1;
          if ($(this).prop("checked")) {
              cksum++;
          }
      });
      if ($(this).prop("checked")) {
          cksum = cksum + 1;
      }
      else {
          cksum = cksum - 1;
      }
      if (cksum == sum) {
          $(this).closest('.citys').prev().find("input").prop("checked", true);
          $(this).closest('.citys').prev().find(".indeterminate").hide();
      }
      else {
          $(this).closest('.citys').prev().find("input").prop("checked", false);
          $(this).closest('.province-list').prev().find("input").prop("checked", false);
          $(this).closest('.citys').prev().find(".indeterminate").show();
          $(this).closest('.province-list').prev().find(".indeterminate").show();
      }

      psum = $(this).closest('.province-list').find('.ecity').length;
      pcksum = $(this).closest('.province-list').find('.gareas input[type="checkbox"]:checked').length;
      if(psum == pcksum){
        $(this).closest('.province-list').prev().find(".indeterminate").hide();
        $(this).closest('.province-list').prev().find("input").prop("checked", true);
      }
  });
  // 
  $("input[name='number']").each(function () {
      $(this).css("ime-mode", "disabled");
      $(this).keyup(function () {
          var val = $(this).val();
          if (isNaN(val)) {
              val = val.substr(0, val.length - 1);
              $(this).val(val);
          }
      });
      $(this).keydown(function (event) {
          if ((event.ctrlKey && event.which == 67) || (event.ctrlKey && event.which == 86)) {
              return false;
          }
          return true;
      });
  });

  /**列表全选反选**/
  $(document).on('change','input:checkbox[name="chkSetAreaList"].all',function(){
    $('input:checkbox[name="chkSetAreaList"]').prop('checked',$(this).prop('checked'))
    if($(this).prop('checked') == false){
      $('.setAreaList').css('background-color','rgb(255, 255, 255)')
    }       

  })
  $(document).on('change','input:checkbox[name="chkSetAreaList"]',function(){
    var listLenght = $('.fm_setPrice_table>tbody>tr').length;
    var checkedLenght = $('input:checkbox[name="chkSetAreaList"]:checked').length;
    if($('input:checkbox[name="chkSetAreaList"].all').prop('checked')){
      if(checkedLenght == listLenght+1){
        $('input:checkbox[name="chkSetAreaList"]').prop('checked',true)
      }else{
        $('input:checkbox[name="chkSetAreaList"].all').prop('checked',false)
      }
    }else{
      if( checkedLenght+1 == listLenght){
        $('input:checkbox[name="chkSetAreaList"]').prop('checked',true)
      }else{
        $('input:checkbox[name="chkSetAreaList"].all').prop('checked',false)
      }        
    }

    $('input:checkbox[name="chkSetAreaList"]:checked').each(function(){
      $(this).parent().parent('.setAreaList').css('background-color','rgb(255, 255, 229)')
    })

    if($(this).prop('checked')){
      $(this).parent().parent('.setAreaList').css('background-color','rgb(255, 255, 229)')
    }else{
       $(this).parent().parent('.setAreaList').css('background-color','rgb(255, 255, 255)')
    }
  })
});
$
var delectArr = new Array();//更新是指定地区运费被删除
var sendTime = new Array();//发货时间
var areaArr = {};//地区选择
/**
 * *[selectShippingWays 选择运送方式]
 * @return {[type]} [description]
 */
selectShippingWays =function(obj){
  var _msg = '切换运送方式后，所设置当前模板的运输信息将被清空，确定继续么？';
  var sum = 0;
  var bearFreight = $('input[name="bearFreight"]:checked').val();
  $("#fmtplType label").each(function () {
      if ($(this).prop("class") === "fm_cell6 selected"){
        sum++;
      }   
  });

  if (sum > 0 && bearFreight==="0") {
		art.dialog({
		    title: '提示',
		    content: _msg,
		    okValue: '确认',
		    cancelValue: '取消',
		    width:300,
		    ok: function () {
		    	tmp(obj);
		    },cancel: function () {
		    	$('#fmPricingWays .selected').find('input').prop('checked',true);
		        $(obj).prop('checked',false);
		        //return false;
		    }
		});
/*    if(!fmConfirm(_msg)){
      $('#fmtplType .selected').find('input').prop('checked',true);
      $(obj).prop('checked',false);
      return false;
    }*/
  } else {
	  tmp(obj);
  }
}
var tmp = function(obj) {
  $('#fmtplType .selected').removeClass('selected');
  $(obj).parent().addClass('selected');
//  $('.fm_addArea_content').hide();
  $('.fm_addArea_content').remove();
  var fmIsMail =  $('input:radio[name="bearFreight"]:checked').val();
  var fmPricingWays = $('input:radio[name="fmPricingWays"]:checked').val();
  var fmtplType = $('input:radio[name="fmtplType"]:checked').val();

  var _appendAreaContent = "";
  // var flag = true;//当前计价方式下的运送方式是否已设置

  // $('.fm_addArea_content').each(function(){
  //   if ($(this).attr('shipways') == fmtplType ) {
  //     $(this).show();
  //     flag = false;
  //     return;
  //   }else{
  //   }
  // })
  
  if(/*flag && */fmIsMail === "0"){  
    var obj = {
      areasTxt:'未选择地区',
      areasId:'',
      express_start:"1",
      express_price:"",
      express_plus:'1',
      express_priceplus:'',
      as_id: 0,
      ship_id : 1,
    }
    switch(fmPricingWays){
      case "0": 
        _appendAreaContent = addAreaContent('piece',obj,fmtplType,'件','件');
        break;
      case "1":
        _appendAreaContent = addAreaContent('weight',obj,fmtplType,'重量','kg');
        break;
      case "2":
        _appendAreaContent = addAreaContent('volume',obj,fmtplType,'体积','m³');
        break;
      default:
        break              
    }
    $('#FM_addAreaCont').append(_appendAreaContent)        
    $('.batchDiv>div').hide();
    //表格样式
    // if($(".fm_setPrice_table").length>0){
    //   $(".fm_setPrice_table").projectTable();
    // }         
  }else{
    
  }
}
/**
 * *addAreaList 为指定城市添加运费
 * @param {[string]} id   [当前位置]
 * @param {[string]} head [列表头部]
 * @param {[string]} list [列表内容]
 */
addAreaListHandle = function(id,head,list){
  var _currentDiv = $(id).parent().siblings('.fm_addArea_list').find('.fm_setPrice_table tbody')
  if (_currentDiv.find('tr').length===0) {
    _currentDiv.append(head+list);
  }else{
     _currentDiv.append(list);
  } 
  $('.batchDiv>div').show();       
}

/**
 * [fmConfirm 确认框]
 * @param  {[string]} msg [提示信息]
 * @return {[type]}     [description]
 */
fmConfirm = function(msg){
  return confirm(msg);
}    
/**
 * *选择是否包邮
 * @return {[type]} [description]
 */  
selectIsMail = function(obj){
  var _msgYes = '您的运费设置将变为未设置状态，请设置运费。';
  var _msgNo = '选择“卖家承担运费”后，所有区域的运费将设置为0元且原运费设置无法恢复，请保存原有运费设置。';
  var isMailStatus =  $("input:radio[name='bearFreight']").val();

  $('input:radio[name="fmtplType"]').each(function(){
    $(this).prop('checked',false);
    $(this).parent().removeClass('selected')
  })      
  switch(isMailStatus){
    case '1'://卖家
    	art.dialog({
    	    title: '提示',
    	    content: _msgNo,
    	    okValue: '确认',
    	    cancelValue: '取消',
    	    width:300,
    	    ok: function () {
    	    	$('#bearFreight .selected').find('input').removeClass('selected');
    	        $(obj).parent().addClass('selected');    
    	        $('.fm_addArea_content').hide();
    	    },cancel: function () {
    	    	$('#bearFreight .selected').find('input').prop('checked',true);
    	        $(obj).prop('checked',false);
    	        //return false;
    	    }
    	});
/*      if(!fmConfirm(_msgNo)){
        $('#bearFreight .selected').find('input').prop('checked',true);
        $(obj).prop('checked',false);
        return false;
      }else{  
        $('#bearFreight .selected').find('input').removeClass('selected');
        $(obj).parent().addClass('selected');    
        $('.fm_addArea_content').hide();
      }*/
      break;
    case '0'://买家
    	art.dialog({
    	    title: '提示',
    	    content: _msgYes,
    	    okValue: '确认',
    	    cancelValue: '取消',
    	    width:300,
    	    ok: function () {
    	    	$('#bearFreight .selected').find('input').removeClass('selected');
    	        $(obj).parent().addClass('selected');    
    	        $('.fm_addArea_content').hide();
    	    },cancel: function () {
    	    	$('#bearFreight .selected').find('input').prop('checked',true);
    	        $(obj).prop('checked',false);
    	        //return false;
    	    }
    	});
/*       if(!fmConfirm(_msgYes)){
        $('#bearFreight .selected').find('input').prop('checked',true);
        $(obj).prop('checked',false);
        return false;
      }else{  
        $('#bearFreight .selected').find('input').removeClass('selected');
        $(obj).parent().addClass('selected');    
        $('.fm_addArea_content').hide();
      }*/
      break; 
    default:
      break;       
  }    
}      
 /**
 * 计价方式
 * @return {[type]} [description]
 */  
selectPricingWays = function(obj){
  var _msg = '切换计价方式后，所设置当前模板的运输信息将被清空，确定继续么？';
	art.dialog({
	    title: '提示',
	    content: _msgYes,
	    okValue: '确认',
	    cancelValue: '取消',
	    width:300,
	    ok: function () {
	    	$('#fmPricingWays .selected').find('input').removeClass('selected');
	        $(obj).parent().addClass('selected');    
	        $('input:radio[name="fmtplType"]').each(function(){
	          $(this).prop('checked',false);
	          $(this).parent().removeClass('selected')
	        })
	        $('.fm_addArea_content').remove();
	    },cancel: function () {
	    	$('#fmPricingWays .selected').find('input').prop('checked',true);
	        $(obj).prop('checked',false);
	        //return false;
	    }
	});
/*  if(!fmConfirm(_msg)){
    $('#fmPricingWays .selected').find('input').prop('checked',true);
    $(obj).prop('checked',false);
    return false;
  }else{  
    $('#fmPricingWays .selected').find('input').removeClass('selected');
    $(obj).parent().addClass('selected');    
    $('input:radio[name="fmtplType"]').each(function(){
      $(this).prop('checked',false);
      $(this).parent().removeClass('selected')
    })
    $('.fm_addArea_content').remove();
  } */     
}  
/**
 * *删除指定地区邮费
 * @param  {[string]} id [当前位置]
 */
delectList = function(obj){
  var _currentTr = $(obj).parent().parent();
  var _currentDiv = _currentTr.siblings();
  var as_id = $(obj).closest('tr.setAreaList').attr('as_id');
  var obj={};
  if(confirm("确定删除吗？")==true)
  {    
  if(as_id !=="0"){
    obj = delectAreasObj(parseInt(as_id))
    delectArr.push(obj);
    console.log(delectArr);
  }    
    if(_currentDiv.length === 1){
      _currentTr.parent().empty();
     $('.batchDiv>div').hide();
    }else{
      _currentTr.remove(); 
    
    }
  }
}     
/**
 * [subDelete 批量删除保存所选项的ID的脚本]
 * @return {[boolean]} [description]
 */
subDelete = function (){
  if(!checkCheckedNum()){
    return;
  }   
  if(confirm("确定删除吗？")==true)
  {         
    $("input:checkbox[name='chkSetAreaList']:checked").each(function(){
    //  $("tr[class="+$(this).val()+"]").remove();
      var as_id = $(this).closest('tr.setAreaList').attr('as_id');
      var obj={};
      if(as_id !=="0" && as_id){
    //    alert(as_id)
        obj = delectAreasObj(as_id)
        delectArr.push(obj);
        console.log(delectArr);
      } 
      $(this).parents('tr.setAreaList').remove();
    })
    if($('.fm_addArea_list tbody tr').length<2){
      $('.fm_addArea_list tbody').empty()
      $('.batch_span input:checkbox[name="chkSetAreaList"]').prop('checked',false)
      $('.batchDiv>div').hide();
    }

  }
}
/**
 * [setVolumeShipping 打开批量编辑运费窗口]
 */
setVolumeShipping = function(){
  if(!checkCheckedNum()){
    return;
  }else{
    $('#volumeShipping').show();
    $('.fm_opp').show();     
  }    
}

/**
 * [saveVolumeShipping 批量编辑运费]
 * @param  {[type]} obj [description]
 * @return {[type]}     [description]
 */
saveVolumeShipping = function(obj){
  var _currentDiv = $(obj).parent().prev();
  var firstPiece = _currentDiv.find("input[name='firstPiece']").val();
  var firstPrice = _currentDiv.find("input[name='firstPrice']").val();
  var addPiece = _currentDiv.find("input[name='addPiece']").val();
  var addPrice = _currentDiv.find("input[name='addPrice']").val();
  $(".fm_addArea_list input:checkbox[name='chkSetAreaList']:checked").each(function(){
    $(this).parent().siblings('td').find("input[name='fmfirstPiece']").prop('value',firstPiece)
    $(this).parent().siblings('td').find("input[name='fmfirstPrice']").prop('value',firstPrice)
    $(this).parent().siblings('td').find("input[name='fmaddPiece']").prop('value',addPiece)
    $(this).parent().siblings('td').find("input[name='fmaddPrice']").prop('value',addPrice)
  });    
  // alert(firstPiece+','+firstPrice+','+addPiece+','+addPrice)
  closeVolumeShipping();
}
/**
 * [closeVolumeShipping 关闭批量编辑运费窗口]
 */
closeVolumeShipping = function(){
  $('#volumeShipping').hide();
  $('.fm_opp').hide();     
}
/**
 * [checkCheckedNum 批量处理前检查复选框选中个数]
 * @return {[boolean]} [是否选中]
 */
checkCheckedNum = function(){
  var idCount=$("input:checkbox[name='chkSetAreaList']:checked").length;
  var flag = true;   
  $("input:checkbox[name='chkSetAreaList']:checked").each(function(){
    idCount++;
  });
    // alert(idCount)
  if(idCount===0){
    flag = false;
    //alert("请选择批量设置对象！");  
    art.dialog({
	    title: '信息',
	    content: "请选择批量设置对象！",
	    time:2000
	});
  }   
  return flag;     

}
/**
 * [ShowCounty 显示区县]
 * @param {[string]} obj [当前位置]
 */
ShowCounty = function(obj) {
  if ($(obj).closest('.ecity').hasClass("showCityPop")) {
    $(obj).closest('.ecity').removeClass("showCityPop");
  } else {
    $("div").removeClass("showCityPop");
    $(obj).closest('.ecity').addClass("showCityPop");
  }
}

/**
 * [HideCounty 隐藏区县]
 * @param {[string]} obj [当前位置]
 */
HideCounty = function(obj) {
  $(obj).closest('.ecity').removeClass("showCityPop");
}

/**
 * [HideArea 取消选择地区]
 */
HideArea = function() {
  $("#area").children().find("input[type='checkbox']").prop("checked", false);
  $("#area").hide();
  $('.fm_opp').hide()
}
/**
 * [CheckArea 打开选择地区]
 * @param {[string]} obj [当前位置]
 */
CheckArea = function(obj) {
  $('#area #J_CityList').html(areaContent())
  var _areaDiv =  $(obj).parent().siblings('.cell-area');
  var mode = _areaDiv.find('p').prop("id");
  var area = _areaDiv.find('input[name="inclpost"]').val();
  $("img.indeterminate").hide();
  if(area !=""){
    var areaArr = new Array();
    areaArr = area.split('|');      
  }
  $.each(areaArr,function(i){
    console.log(areaArr[i])
    var code = areaArr[i].split('_').length;
    var id = "";
    console.log("code"+code);
    switch(code){
      case 1:
        id="J_Group_"+areaArr[i];
        $('#'+id).prop('checked',true) 
        // console.log($('#'+id).parent().siblings('.province-list').find('input[type="checkbox"]')) 
        $('#'+id).closest('.gcity').siblings('.province-list').find('input[type="checkbox"]').prop('checked',true)
        break;
      case 2:
        id="J_Province_"+areaArr[i];
        $('#'+id).prop('checked',true) 
        $('#'+id).closest('.gareas').siblings('.citys').find('input[type="checkbox"]').prop('checked',true)
        $('#'+id).closest('.province-list').prev().find('img.indeterminate').show()
        break;
      case 3:
        id="J_City_"+areaArr[i]; 
        $('#'+id).prop('checked',true)  
        $('#'+id).closest('.citys').prev().find('img.indeterminate').show()
        $('#'+id).closest('.province-list').prev().find('img.indeterminate').show()      
        break;
      default: 

    }
    console.log(id);
              
  })

  var offset = $(obj).offset();
 /* $("#area").css({ position: "absolute", 'top': offset.top + 10, 'left': offset.left - 20, 'z-index': 2 });*/
  $("#area").css({ 'position': "absolute", 'min-width':'480px',
    'top': "20%" , 'left': 0, 'z-index': 2 ,'background':'#fff','margin':'0 auto','left':0,'right':0,'width':'580px'});
  $("#area").show();
  $('.fm_opp').show();
  $("#area").prop("mode", mode);  
}

/**
 * [AreaSure 选定地区]
 */
AreaSure = function() {
  var pid = $("#area").prop("mode");
  var text = "，";
  var value = "|";
  $("#area").find(".J_Group").each(function () {
      if ($(this).prop("checked")) {
          text = text + $(this).parent().next().text() + "，";
          value = value + $(this).val() + "|";
          return;
      }
      $(this).closest('.gcity').next().find(".J_Province").each(function () {
          if ($(this).prop("checked")) {
              text = text + $(this).parent().next().text() + "，";
              value = value + $(this).val() + "|";
              //alert(value)
              return;
          }
          $(this).closest('.gareas').next().find(".J_City").each(function () {
              if ($(this).prop("checked")) {
                  text = text + $(this).parent().next().text() + "，";
                  value = value + $(this).val() + "|";
                  // alert($(this).val())
                  return;
              }
          });
      });
  });
  text = text.substr(1, text.length - 2);
  value = value.substr(1, value.length - 2);
	if(text ==""){
		$("#area").hide();
		$(".fm_opp").hide()
		return;
	}
  // if ($("#proselect").val() == "0") {
  //     text = "全国";
  //     value = "0";
  // }
  $("p[id='" + pid + "']").html(text);
  $("p[id='" + pid + "']").parent().next().val(value);

  $("#area").find("input[type='checkbox']").prop("checked", false);
  $("#area").hide();
  $(".fm_opp").hide();
}

/**
 * *满足条件包邮模板
 */
InclPostage = function(secand) {
  Region = "";
  CarryWay = 0;
  First = 0;
  SelectIf = 0;
  this.Secand = secand;
}
/**
 * *运送方式模板
 * @param {[string]} area   [所属地区]
 * @param {[string]} isdefault [是否默认]
 * @param {[string]} carryway [运送方式]
 */
CarryMode = function(area, isdefault, carryway) {
  this.Region = area;
  this.IsDefault = isdefault;
  this.CarryWay = carryway;
  FirstSum = 0;
  SecondSum = 0;
  FirstAmount = 0;
  SecondAmount = 0;
}
/**
 * *默认运费模板
 * @param {[string]} id   [方式:piece、Weight、volume]
 * @param {[string]} obj [信息对象]
 * @param {[string]} ways [对应checked值：2,0,1]
 * @param {[string]} name [运送方式：件数、重量、体积]
 * @param {[string]} unit [对应运送方式的单位：件、kg、（m³）]
 */
addAreaContent = function(id,obj,ways,name,unit){
  var list="";
  list+='          <div id="fm_set_'+id+'_'+ways+'" class="fm_addArea_content" shipWays="'+ways+'">';
  list+='            <div class="list_div fm_addArea_title fm_blue" id="FM_theDefaultShipping" as_id='+obj.as_id+' ship_id = '+obj.ship_id+'>';
  list+='              <div class="fm_setPrice_info">';
  list+='                <label>';
  list+='                  默认运费<input class="text validate" type="text" name="firstPiece" isrequired="yes" tipmsg="" maxlength="5" maxdatalength="5" datatype="jsInt" value="'+obj.express_start+'"/><span>'+unit+'内</span>';
  list+='                          <input class="text validate" type="text"  isrequired="yes" name="firstPrice" tipmsg="" maxlength="8" maxdatalength="8" datatype="jsRmb" value="'+obj.express_price+'" placeholder="0.00">元,';
  list+='                   每增加  <input class="text validate" type="text" name="addPiece" isrequired="yes"  tipmsg="" maxlength="5" maxdatalength="5" datatype="jsInt" value="'+obj.express_plus+'" readonly><span>'+unit+'，</span>';
  list+='                   增加运费<input type="text" class="text validate" isrequired="yes" name="addPrice" tipmsg="" maxlength="8" maxdatalength="8" datatype="jsRmb" value="'+obj.express_priceplus+'" placeholder="0.00">元 ;'
  list+='                </label> ';                   
  list+='              </div>';
  list+='            </div>';
  list+='            <div class="list_div fm_addArea_list">';
  list+='              <table class="fm_setPrice_table" border="0" cellspacing="0" cellpadding="0">';
  list+='                <tbody id="FM_Tbody" mode="post">';
  list+='                </tbody>';
  list+='              </table>';
  list+='            </div>';

  list+='            <div class="batchDiv">';
  list+='              <div class="batch_div_all"><label class="batch_span"><input class="all" type="checkbox" name="chkSetAreaList" value="全选">全选</label>';
  list+='              <input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量删除" onclick="subDelete()" />';
  list+='              <input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量设置运费" onclick="setVolumeShipping()"/></div>';
  list+='              <input class="btn ol_colorbtn ol_bluebtn fm_set_up_freight" name="setUpFreight" type="button" value="为指定城市添加运费" onclick=""/>';
  list+='            </div>';
  list+='          </div>    ';   
  return list;
}
/**
 * *列表头部
 * @param {[string]} name [运送方式：件数、重量、体积]
 * @param {[string]} unit [对应运送方式的单位：件、kg、（m³）]
 */
addAreaHead = function(name,unit){
  var list="";
  list+='<tr>';
  list+='                     <th width="3%">';
  list+='                       <input class="all" type="checkbox" name="chkSetAreaList"/>';
  list+='                     </th>';
  list+='                     <th width="15%">指定地区</th>';
  list+='                     <th width="10%">首'+(name+unit)+'</th>';
  list+='                     <th width="10%">首费</th>';
  list+='                     <th width="10%">续'+(name+unit)+'</th>';
  list+='                     <th width="10%">续费</th>';
  list+='                     <th width="15%">操作</th>';
  list+='                 </tr> ';
  return list;
}
/**
* *列表行
* @param {[string]} obj [信息对象]
* @param {[int]} num [行数标记]
*/
addAreaList = function(obj,num){
  var list="";
  list+='                  <tr class="setAreaList" as_id='+obj.as_id+' ship_id = '+obj.ship_id+'>';
  list+='                    <td>';
  list+='                      <input class="ids" type="checkbox" value="setAreaList" name="chkSetAreaList">';
  list+='                    </td>';
  list+='                    <td class="cell-area">';
  list+='                      <div class="area-group">';
  list+='                       <p class="area add_region_range" id="express'+num+'">'+obj.areasTxt+'</p>';
  list+='                      </div>';
  list+='                      <input type="hidden" class="text validate" value="'+obj.areasId+'" name="inclpost" >';
  list+='                   </td>';
  list+='                    <td>';
  list+='                      <input class="input-text text validate" type="text" aria-label="首件" isrequired="yes" tipmsg="" datatype="jsInt"  value="'+obj.express_start+'" data-field="start" name="fmfirstPiece">';
  list+='                    </td>';
  list+='                    <td>';
  list+='                      <input class="input-text text validate" type="text" aria-label="首费" isrequired="yes" tipmsg="" datatype="jsRmb""  value="'+obj.express_price+'" data-field="postage" name="fmfirstPrice" placeholder="0.00">';
  list+='                    </td>';
  list+='                     <td>';
//  list+='                      <input class="input-text text validate" type="text" aria-label="续件" isrequired="yes" tipmsg="" datatype="jsInt"  value="'+obj.express_plus+'" data-field="plus" name="fmaddPiece">';
  list+='                      <input class="input-text text validate" type="text" aria-label="续件" isrequired="yes" tipmsg="" datatype="jsInt"  value="'+1+'" data-field="plus" name="fmaddPiece" readonly>';
  list+='                    </td>';
  list+='                    <td>';
  list+='                      <input class="input-text text validate" type="text" aria-label="续费" isrequired="yes" tipmsg="" datatype="jsRmb"  value="'+obj.express_priceplus+'" data-field="postageplus" name="fmaddPrice" placeholder="0.00">';
  list+='                    </td> ';                                            
  list+='                    <td class="list_td_left">';
  list+='                      <input class="btn ol_colorbtn ol_bluebtn selectAreas" type="button" value="选择地区" onclick="CheckArea(this)">';
  list+='                      <input class="btn ol_colorbtn ol_redbtn delectAreas" type="button" value="删除" onclick="delectList(this)">';
  list+='                    </td>';
  list+='                  </tr>    ';
  return list;
}            

 /**
 * *解析地区获取最后一个id值
 * @param {[string]} idStr [地区Id]
 * @param {[string]} nameStr [对应地区名称]
 * @return {[Array]} regionArr 【地区集合】
 */
regionMg = function(idStr,nameStr){
  var areaArr = new Array();
  var nameArr = new Array();
  var regionArr = new Array();
  areaArr = idStr.split('|'); 
  nameArr = nameStr.split("，")
  $.each(areaArr,function(i,item){
    var regionObj = {};
   // var codeArr = new Array();
   // codeArr = item.split('_');
    regionObj.rea_id = 0;
    regionObj.as_id = 0;
   // regionObj.end_area = codeArr[codeArr.length-1];
    regionObj.end_area = item;
    regionObj.end_area_name = $.trim(nameArr[i]);
    regionArr.push(regionObj)
  })
  console.log(regionArr)
  return regionArr;
}
/**
 * *当前时间
 */
CurrTime = function(){ 
  var YyMmDd;  
  var today = new Date(); 
  YyMmDd = today.getFullYear() + "-" + LongNumber(today.getMonth()+1) + "-" + LongNumber(today.getDate());   
  YyMmDd+= ' ' + LongNumber(today.getHours()) + ":" + LongNumber(today.getMinutes()) + ":" + LongNumber(today.getSeconds());   
  return YyMmDd; 
} 
LongNumber = function(num){ 
  var tempNum; 
  if(parseInt(num)<10){
    tempNum = "0" + parseInt(num)
  } 
  else{
    tempNum = parseInt(num)
  } 
  return tempNum; 
}  
//提交前验证
CheckInput = function() {
  //判断运送方式
  var sum = 0;
  var isok = true;

  if($("#FM_TemplateTitle").val() === ""){
      //alert("请填写运费模板名称");
      art.dialog({
		    title: '信息',
		    content: "请填写运费模板名称",
		    time:2000
		});
      return false;
  }
  if($("#area_id_div select.select:last-child").val() === ""){
      //alert("请填写宝贝地址");
      art.dialog({
		    title: '信息',
		    content: "请填写宝贝地址",
		    time:2000
		});
      return false;
  }         
  if($("#FM_DeliveryTime").val() === "0"){
      //alert("请填写发货时间");
      art.dialog({
		    title: '信息',
		    content: "请填写发货时间",
		    time:2000
		});
      return false;
  }      
  $("input[name='fmtplType']").each(function () {
      if ($(this).prop("checked"))
          sum++;
  });
  if (sum <= 0) {
      //alert("请至少选择一种运送方式！");
      art.dialog({
		    title: '信息',
		    content: "请至少选择一种运送方式！",
		    time:2000
		});
      return false;
  }
  //判断发货时间
  if ($("input[name='inclpost']").val() == "") {
      //alert("未添加地区");
      art.dialog({
		    title: '信息',
		    content: "未添加地区",
		    time:2000
		});
      return false;
  }
  //判断所有可见表单
  $("input[type='text']:visible").each(function () {
      if ($.trim($(this).val()) == "") {
          $(this).focus();
          isok = false;
          return false;
      }
      else return true;
  });
  return isok;
}

 /**
 * *删除某地区运费编辑
 * @param {[int]} as_id [设置对象as_id不为0，ship_id置为0]
 */
delectAreasObj = function(as_id){
  var delectMode ={};
  var postInput = $("#FM_theDefaultShipping").find(".fm_setPrice_info").find("input[type='text']");
  delectMode.as_id = as_id;
  delectMode.ship_id = 0;
  delectMode.express_id="";
  delectMode.express_start = 0;
  delectMode.express_plus = 0;
  delectMode.express_price = 0;
  delectMode.express_priceplus = 0;
  delectMode.reach_area = new Array();
  delectMode.default_ship = 0;
  return delectMode;
}
//对应子集组合
pushRArr = function(arr,value){
  arr.push(value)
  return arr;
}
//设置对象
pushArr = function(myobj,idkey,namekey,id,name){
  myobj[idkey] = id;
  myobj[namekey] = name;
  return myobj;
}

$.ajax({
   url: "/admin/logitemp/getinit",
   data: "",
   type: "POST",
   success: function (data) {
    console.log(data);     
     if (data.code == 0){
    //  console.log(data.info);
      var sendTimeList="";
      $.each(data.info.sendtime,function(i,item){
        sendTimeList+=" <option value='"+item.id+"'>"+item.name+"</option> ";
      })
      $('#FM_DeliveryTime').append(sendTimeList);
      
      var region = "";
      var privinces1 = new Array();
      var privinces2 = new Array();
      var privinces3 = new Array();
      var privinces4 = new Array();
      var privinces5 = new Array();
      var privinces6 = new Array();
      var privinces7 = new Array();
      var privinces0 = new Array();
      var regions = new Array();
      var regionArr1={};
      var regionArr2={};
      var regionArr3={};
      var regionArr4={};
      var regionArr5={};
      var regionArr6={};
      var regionArr7={};
      var regionArr0={};
      
      var rProvincesArr1 = new Array(),
        rProvincesArr2 = new Array(),
        rProvincesArr3 = new Array(),
        rProvincesArr4 = new Array(),
        rProvincesArr5 = new Array(),
        rProvincesArr6 = new Array(),
        rProvincesArr7 = new Array(),
        rProvincesArr0 = new Array();
      $.each(data.info.logiArea,function(i,p_item){
      //  console.log(items.name)
        var privincesArr={};
        var pCitysArr = new Array();

        var citys = new Array();
        region = p_item.region;
        privincesArr = pushArr(privincesArr,'area_id','area_name',p_item.area_id,p_item.area_name)
        //privincesArr['area_id'] = p_item.area_id;
        //privincesArr['area_name'] = p_item.area_name;
        $.each(p_item.child_area,function(i,c_item){
          var citysArr = {};
          citysArr['prev_id'] = {
            'id': p_item.area_id,
            'name': p_item.area_name
          }
          
          privincesArr[p_item.area_id] = pushRArr(pCitysArr,c_item.area_id); 
          citysArr = pushArr(citysArr,'area_id','area_name',c_item.area_id,c_item.area_name)
        //  citysArr['area_id'] = item.area_id;
        //  citysArr['area_name'] = item.area_name; 
          citys.push(citysArr);
        })
        privincesArr['citys'] = citys;
        switch(region){
          case "1":           
            privincesArr['prev_id'] = {
              'id': 1,
              'name':  "华东" 
            }
            regionArr1[1]=pushRArr(rProvincesArr1,p_item.area_id);
            regionArr1 = pushArr(regionArr1,'region_id','region_name',"1","华东")
            if(privincesArr.length!==0){
              privinces1.push(privincesArr);
            }
            break;
          case "2":
            privincesArr['prev_id'] = {
              'id': 2,
              'name':  "华北" 
            }
            regionArr2[2]=pushRArr(rProvincesArr2,p_item.area_id);
            regionArr2 = pushArr(regionArr2,'region_id','region_name',"2","华北")
            if(privincesArr.length!==0){
              privinces2.push(privincesArr);
            //  console.log(privincesArr)
            }
            break;
          case "3":
            privincesArr['prev_id'] = {
              'id': 3,
              'name': "华中"  
            }
            regionArr3[3]=pushRArr(rProvincesArr3,p_item.area_id);
            regionArr3 = pushArr(regionArr3,'region_id','region_name',"3","华中")
            if(privincesArr.length!==0){
              privinces3.push(privincesArr);
            }
            break;
          case "4":
            privincesArr['prev_id'] = {
              'id': 4,
              'name': "华南"  
            }
            regionArr4[4]=pushRArr(rProvincesArr4,p_item.area_id);
            regionArr4 = pushArr(regionArr4,'region_id','region_name',"4","华南")
            if(privincesArr.length!==0){
              privinces4.push(privincesArr);
            }
            break;
          case "5":
            privincesArr['prev_id'] = {
              'id': 5,
              'name':  "东北" 
            }
            regionArr5[5]=pushRArr(rProvincesArr5,p_item.area_id);
            regionArr5 = pushArr(regionArr5,'region_id','region_name',"5","东北")
            if(privincesArr.length!==0){
              privinces5.push(privincesArr);
            }
            break;
          case "6":
            privincesArr['prev_id'] = {
              'id': 6,
              'name':  "西北" 
            }
            regionArr6[6]=pushRArr(rProvincesArr6,p_item.area_id);
            regionArr6 = pushArr(regionArr6,'region_id','region_name',"6","西北")
            if(privincesArr.length!==0){
              privinces6.push(privincesArr);
            }
            break;
          case "7":
            privincesArr['prev_id'] = {
              'id': 7,
              'name':  "西南" 
            }
            regionArr7[7]=pushRArr(rProvincesArr7,p_item.area_id);
            regionArr7 = pushArr(regionArr7,'region_id','region_name',"7","西南")
            if(privincesArr.length!==0){
              privinces7.push(privincesArr);
            }
            break;
          default:
            privincesArr['prev_id'] = {
              'id': 0,
              'name':  "其他" 
            }
            regionArr0[0]=pushRArr(rProvincesArr0,p_item.area_id);
            regionArr0 = pushArr(regionArr0,'region_id','region_name',"0","其他")
            if(privincesArr.length!==0){
              privinces0.push(privincesArr);
            }
            break;
         
        }
        
        //成功后储存到本地中
        //var str=JSON.stringify(sendTime) ;
        //localStorage.setItem('newsList',str);
      })
      regionArr1["provinces"] = privinces1;
      regions.push(regionArr1);
      regionArr2["provinces"] = privinces2;
      regions.push(regionArr2);
      regionArr3["provinces"] = privinces3;
      regions.push(regionArr3);
      regionArr4["provinces"] = privinces4;
      regions.push(regionArr4);
      regionArr5["provinces"] = privinces5;
      regions.push(regionArr5);
      regionArr6["provinces"] = privinces6;
      regions.push(regionArr6);
      regionArr7["provinces"] = privinces7;
      regions.push(regionArr7);
      if(!privinces0){
        regionArr0["provinces"] = privinces0;
        regions.push(regionArr0);
      }
      
      
      areaArr['areas'] = regions;
    //  areaList = areaArr;
      console.log(areaArr)
    //  console.log(JSON.stringify(sendTime))
    //  console.log(JSON.stringify(areaArr))
     }
   },
   error: function () {
     //alert("失败");
     art.dialog({
 	    title: '信息',
 	    content: "请选择批量设置对象！",
 	    time:2000
 	});
   }
});
//对应子集组合
pushRArr = function(arr,value){
  arr.push(value)
  return arr;
}
//设置对象
pushArr = function(myobj,idkey,namekey,id,name){
  myobj[idkey] = id;
  myobj[namekey] = name;
  return myobj;
}

areaContent = function(){
  var html="";
//  var areaList = JSON.stringify(areaArr)
//  console.log('111'+areaList.areas)
  $.each(areaArr.areas,function(i,r_item){
  //  console.log("111"+r_item)
    html+=' <li class="pro_'+r_item.region_id+' hidden">';
    html+='    <div class=" dcity clearfix">';
    html+='      <div class="ecity gcity">';
    html+='        <span class="group-label">';
    html+='          <label style="position: relative;">';
    html+='            <input type="checkbox" id="J_Group_'+r_item.region_id+'" class="J_Group" value="'+r_item.region_id+'">';
    html+='             <img src="/include/admin/image/interChecked.png" alt="" class="indeterminate hide" style="" />';
    html+='          </label>';
    html+='          <label for="J_Group_'+r_item.region_id+'">'+r_item.region_name+' </label>';
    html+='        </span>';
    html+='      </div>';
    html+='        <div class="province-list">';
    $.each(r_item.provinces,function(j,p_item){
      html+='          <div class="ecity" as_id="1" rea_id="0">';
      html+='              <span class="gareas">';
      html+='                <label style="position: relative;">';
      html+='                  <input type="checkbox" class="J_Province" id="J_Province_'+r_item.region_id+'_'+p_item.area_id+'" value="'+r_item.region_id+'_'+p_item.area_id+'">';
      html+='                  <img src="/include/admin/image/interChecked.png" alt="" class="indeterminate hide" style="" />';
      html+='                </label>';
      html+='                <label for="J_Province_'+r_item.region_id+'_'+p_item.area_id+'">';
      html+='                  '+p_item.area_name+'<span class="checkedNum txtRed"></span>';
      html+='                </label>';
      html+='                <span onclick="ShowCounty(this)" class="trigger"></span>';
      html+='              </span>';
      html+='              <div class="citys">';
      $.each(p_item.citys,function(j,c_item){
        html+='                  <span class="areas">';
        html+='                    <label style="position: relative;">';
        html+='                      <input type="checkbox" class="J_City" id="J_City_'+r_item.region_id+'_'+p_item.area_id+'_'+c_item.area_id+'" value="'+r_item.region_id+'_'+p_item.area_id+'_'+c_item.area_id+'">';
        html+='                      <img src="/include/admin/image/interChecked.png" alt="" class="indeterminate hide" style="" />';
        html+='                    </label>';
        html+='                    <label for="J_City_'+r_item.region_id+'_'+p_item.area_id+'_'+c_item.area_id+'">'+c_item.area_name+'</label>';
        html+='                  </span>';
      })
      html+='                  <p style="text-align: right;">';
      html+='                      <input type="button" class="close_button" onclick="HideCounty(this)" value="关闭">';
      html+='                  </p>';
      html+='              </div>';
      html+='          </div>';
    })
    html+='        </div>';
  
    html+='    </div>';
    html+='  </li> ';
  })
  return html;
}
//创建提交的数据对象
submitDataObj = function(ship_id,com_id,goods_id){
   var name = $("#FM_TemplateTitle").val();//模板名
  var shopAddr = $("#area_id_div>select:last-child").val();//宝贝地址
  var dispatchTimeId = $("#FM_DeliveryTime").val();//发货时间
  var dispatchTime =  $("#FM_DeliveryTime").find('option[value="'+dispatchTimeId+'"]').text();
  var isInclPostage = $("input[name='bearFreight']:checked").val();//包邮
  var valuationModel = $("#fmPricingWays input[name='fmPricingWays']:checked").val();//计价方式
  var plType = $("#fmtplType input[name='fmtplType']:checked").val();//快递方式
  var modifyTime = CurrTime();
  var carryModes = {};
  var regionArr = new Array();
//  if(CheckInput()){
  //快递的记录数
  var carryArr = new Array();
   if(delectArr.length>0){
    // console.log(delectArr)
    $.each(delectArr,function(i,item){
       carryArr.push(item);
    })
   
  }
  if(isInclPostage === "0"){
    var postMode ={};
    var postInput = $("#FM_theDefaultShipping").find(".fm_setPrice_info").find("input[type='text']");
    postMode.as_id = parseInt($("#FM_theDefaultShipping").attr('as_id'));
    postMode.ship_id = parseInt($("#FM_theDefaultShipping").attr('ship_id'));
    postMode.express_start = parseFloat(postInput.eq(0).val());
    postMode.express_plus = parseFloat(postInput.eq(2).val());
    postMode.express_price = parseFloat(postInput.eq(1).val());
    postMode.express_priceplus = parseFloat(postInput.eq(3).val());
    postMode.reach_area = new Array();
    postMode.default_ship = "1";
    carryArr.push(postMode);
  }else{
  
  }
  if ($("#FM_Tbody>tr").length > 0) {
    $("tbody[mode='post']").find("tr").not(':first-child').each(function () {
    var inputs = $(this).find(":text");
    var carryMode = {};
    var regionStr = $(this).find("input[name='inclpost']").val();
    var regionNameStr = $(this).find('.area-group p').text();
  //  alert(regionNameStr )
    carryMode.as_id = parseInt($(this).attr('as_id'));
    carryMode.ship_id = parseInt($(this).attr('ship_id'));
    carryMode.express_start = parseFloat(inputs.eq(0).val());
    carryMode.express_plus = parseFloat(inputs.eq(2).val());
    carryMode.reach_area = regionMg(regionStr,regionNameStr);
    carryMode.express_price = parseFloat(inputs.eq(1).val());
    carryMode.express_priceplus = parseFloat(inputs.eq(3).val());
    carryMode.default_ship = "0";
    carryArr.push(carryMode);
    });
  }
 // }
  //carryModes.areas = carryArr
  
  // }
  //模板对象
  var fareTemplate =
    {
    "ship_id":ship_id,
    "com_id":com_id,
    "goods_id":goods_id,         
    "ship_name": name,
    "start_area": shopAddr,
    "send_time_id":dispatchTimeId,
    "send_time": dispatchTime,
    "valuation_mode":valuationModel,    
    "express_id_str":plType,   
    "free_ship":isInclPostage,
    "tem_modify_time":modifyTime, 
    "area_info":carryArr
    };
//  console.log(fareTemplate)
  return fareTemplate;
}
//是否包邮、计价方式、运送方式设置为只读模式
var checkboxReadonly = function(){
  $('input[name="bearFreight"]').attr('disabled',true);
  $('input[name="fmPricingWays"]').attr('disabled',true);
  $('input[name="fmtplType"]').attr('disabled',true);
}
/**
 * *单选框操作状态改变
 * @param  {[string]} obj [当前位置]
 * @param  {[string]} val [对应的值]
 */
selectChecked = function(obj,val){
  $(obj).each(function(){
    if(parseInt($(this).val()) === val){
      $(this).prop('checked',true);
      $(this).parent().addClass('selected');
    }else{
      $(this).prop('checked',false);
      $(this).parent().removeClass('selected');
    }
  })
}
/**
 * *运费模块回调数据
 * @param  {[int]} fm_ship_id [模块的标识]
 */
updataInitData = function (fm_ship_id) {
  // body...
  //获取该模块的数据
  $.ajax({
     url: "/admin/logitemp/getone",
     data: {"ship_id": fm_ship_id},
     type: "POST",
     success: function (data) {
     console.log(data)
     if(data.code == 0){
        myobj = data.info;
      console.log(myobj)
      goods_id = myobj.goods_id;
      com_id = myobj.com_id;
      $("#FM_TemplateTitle").prop('value',myobj.ship_name);//模板名
    //  $("#area_id_div>select:last-child").prop('value',myobj.start_area);//宝贝地址
      $("#FM_DeliveryTime").find("option[value='"+myobj.send_time_id+"']").prop("selected",true);//发货时间
      selectChecked("input[name='bearFreight']",myobj.free_ship)//是否包邮
      selectChecked("#fmPricingWays input[name='fmPricingWays']",myobj.valuation_mode)//运送方式
      $("#fmtplType input[name='fmtplType']").each(function(){
        if($(this).val() === myobj.express_id_str){
          $(this).prop('checked',true);
          $(this).parent().addClass('selected');
        }else{
          $(this).prop('checked',false);
          $(this).parent().removeClass('selected');
        }
      })
      var _appendAreaContent = "";
      
      var defaultObj ={};
      var specifyArr = new Array();
      $.each(myobj.area_info,function(i,item){
        if(item.default_ship === 1){//默认运费
          defaultObj=item;
        }else{
          var areasIdStr = "",areasTxtStr="";
          var length = item.reach_area.length - 1;
          $.each(item.reach_area,function(j,item2){
          //  obj = judgeIdAndValue(item2.end_area);
          //  alert(judgeIdAndValue(item2.end_area))
            if(j=== length){
              areasIdStr+= item2.end_area;  
              areasTxtStr+= item2.end_area_name;
            }else{
              areasIdStr+= item2.end_area +'|'; 
              areasTxtStr+= item2.end_area_name +'，';
            }
          })
          var aObj = {
            as_id: item.as_id,
            ship_id : item.ship_id,  
            default_ship:item.default_ship,
            express_plus:item.express_plus,
            express_price:item.express_price,
            express_priceplus:item.express_priceplus,
            express_start:item.express_start,
            areasId : areasIdStr,
            areasTxt : areasTxtStr
          };
        //  console.log(areasIdStr+','+item.reach_area.length)
        //  console.log(areasTxtStr)
          specifyArr.push(aObj);
        //  console.log(specifyArr)
        }
      })
      //买家承担运费
      if($("input[name='bearFreight']:checked").val() === "0"){
        switch(myobj.valuation_mode){
          case 0: 
          //console.log(defaultObj)
          _appendAreaContent = addAreaContent('piece',defaultObj,fmtplType,'件','件');
          break;
          case 1:
          _appendAreaContent = addAreaContent('weight',defaultObj,fmtplType,'重量','kg');
          break;
          case 2:
          _appendAreaContent = addAreaContent('volume',defaultObj,fmtplType,'体积','m³');
          break;
          default:
          break     
        }
        $('#FM_addAreaCont').append(_appendAreaContent) 
      }
      if(myobj.free_ship == 1 || specifyArr == ""){
        $('.batchDiv>div').hide(); 
      }else{
        var num=0;//计数
        switch(myobj.valuation_mode){
          case 0: 
            $.each(specifyArr,function(i,item){
              addAreaListHandle('.fm_set_up_freight',addAreaHead("件","（件）"),addAreaList(item,num))  
              num++;    
            })  
            break;
          case 1:
            $.each(specifyArr,function(i,item){
              console.log(item)
              addAreaListHandle('.fm_set_up_freight',addAreaHead("重量","（kg）"),addAreaList(item,num))  
              num++;    
            }) 
            break;
          case 2:
            $.each(specifyArr,function(i,item){
              addAreaListHandle('.fm_set_up_freight',addAreaHead("体积","（m³）"),addAreaList(item,num));
              num++;    
            })    
            break;
          default:  
            break 
        } 
      }
    } 
  },
    error: function () {
     //alert("获取失败！");
     art.dialog({
		    title: '信息',
		    content: "获取失败！",
		    time:2000
		});
     returnGo('/admin/logitemp/list')
  } 
  })  
}

