/*-----运费模板style ----*/
/*-----Auther:cbq-----*/
/*----Version:1.0.0.0----*/

/****运费模板新增页脚本*****/
$(document).ready(function () {

    /**
     * [为指定城市添加运费点击事件]
     */
    var num=0;//计数
    $(document).on('click','.fm_set_up_freight',function(){
      var fmPricingWays = $("#fmPricingWays input[type='radio']:checked").val();

      $('input[name="chkSetAreaList"].all').prop('checked',false)
   //   alert(fmPricingWays)
      switch(fmPricingWays){
        case "0": 
          addAreaListHandle(this,addAreaHead("件","（件）"),addAreaList(num))
          break;
        case "1":
          addAreaListHandle(this,addAreaHead("重量","（kg）"),addAreaList(num))
          break;
        case "2":
          addAreaListHandle(this,addAreaHead("体积","（m³）"),addAreaList(num))
          break;
        default:  
          break 
      }                   
      num++;
      // alert(num)
    })   
    
    //运送方式选择
    $("input[name='tplType']").each(function () {
        $(this).change(function () {
            var value = $(this).val();
            var text = $(this).next("label").text();
            if ($(this).prop('checked')) {
                if ($("#J_buyerBearFre").prop('checked')) {
                    $(this).parent().next("div").show();
                }
                $(".J_Service").each(function () {
                    $(this).append("<option value='" + value + "'>" + text + "</option>");
                });
                return;
            }
            else {
                $(this).parent().next("div").hide();
                $(".J_Service option[value='" + value + "']").remove();
                return;
            }
        });
    });

    //选// $(".J_Group,.J_Province").each(function () {
    //     $(this).change(function () {   
    $(document).on('change','.J_Group,.J_Province',function(){
            console.log($(this).closest('span').hasClass("group-label"))
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
    $(document).on('change','input[name="chkSetAreaList"].all',function(){
      $('input[name="chkSetAreaList"]').prop('checked',$(this).prop('checked'))
      if($(this).prop('checked') == false){
        $('.setAreaList').css('background-color','rgb(255, 255, 255)')
      }       

    })
    $(document).on('change','input[name="chkSetAreaList"]',function(){
      var listLenght = $('.fm_setPrice_table>tbody>tr').length;
      var checkedLenght = $('input[name="chkSetAreaList"]:checked').length;
      if($('input[name="chkSetAreaList"].all').prop('checked')){
        if(checkedLenght == listLenght+1){
          $('input[name="chkSetAreaList"]').prop('checked',true)
        }else{
          $('input[name="chkSetAreaList"].all').prop('checked',false)
        }
      }else{
        if( checkedLenght+1 == listLenght){
          $('input[name="chkSetAreaList"]').prop('checked',true)
        }else{
          $('input[name="chkSetAreaList"].all').prop('checked',false)
        }        
      }

      $('input[name="chkSetAreaList"]:checked').each(function(){
        $(this).parent().parent('.setAreaList').css('background-color','rgb(255, 255, 229)')
      })

      if($('input[name="chkSetAreaList"]').prop('checked') == false){
        $(this).parent().parent('.setAreaList').css('background-color','rgb(255, 255, 255)')
      }
    })
});

/**
 * *[selectShippingWays 选择运送方式]
 * @return {[type]} [description]
 */
selectShippingWays =function(){
  $('.fm_addArea_content').hide();
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
    switch(fmPricingWays){
      case "0": 
        _appendAreaContent = addAreaContent('piece',fmtplType,'件',' ');
        break;
      case "1":
        _appendAreaContent = addAreaContent('weight',fmtplType,'重量','kg');
        break;
      case "2":
        _appendAreaContent = addAreaContent('volume',fmtplType,'体积','m³');
        break;
      default:
        break              
    }
    $('#FM_addAreaCont').append(_appendAreaContent)        
    $('label[name="chkSetAreaList"]').hide();
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
  $('label[name="chkSetAreaList"]').show();       
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
selectIsMail = function(){
  var _msgYes = '您的运费设置将变为未设置状态，请设置运费。';
  var _msgNo = '选择“卖家承担运费”后，所有区域的运费将设置为0元且原运费设置无法恢复，请保存原有运费设置。';
  var isMailStatus =  $("input:radio[name='bearFreight']").val();

  $('input:radio[name="fmtplType"]').each(function(){
    $(this).prop('checked',false);
  })      
  switch(isMailStatus){
    case '1'://卖家
      if(!fmConfirm(_msgNo)){
        return false;
      }else{
        $('.fm_addArea_content').hide();
      }
      break;
    case '0'://买家
      if(!fmConfirm(_msgYes)){
        return false;
      }else{
        $('.fm_addArea_content').hide();
      }
      break;        

  }
    
}      
 /**
 * 计价方式
 * @return {[type]} [description]
 */  
selectPricingWays = function(){
  var _msg = '切换计价方式后，所设置当前模板的运输信息将被清空，确定继续么？';
  if(!fmConfirm(_msg)){
    return false;
  }else{
    $('input:radio[name="fmtplType"]').each(function(){
      $(this).prop('checked',false);
    })
    $('.fm_addArea_content').remove();
  }      
}  
/**
 * *删除指定地区邮费
 * @param  {[string]} id [当前位置]
 */
delectList = function(obj){
  var _currentTr = $(obj).parent().parent();
  var _currentDiv = _currentTr.siblings();
  
  if(confirm("确定删除吗？")==true)
  {        
    if(_currentDiv.length === 1){
      _currentTr.parent().empty();
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
      $(this).parents('tr.setAreaList').remove();
    })
    if($('.fm_addArea_list tbody tr').length<2){
        $('.fm_addArea_list tbody').empty()
        $('.batch_span input[name="chkSetAreaList"]').prop('checked',false)
        $('label[name="chkSetAreaList"]').hide();
    }

  }
}
/**
 * [setVolumeShipping 打开批量编辑运费窗口]
 */
setVolumeShipping = function(){
  if(!checkCheckedNum()){
    return;
  }   
  $('#volumeShipping').show();
  $('.fm_opp').show(); 
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
 * [setVolumeShipping 关闭批量编辑运费窗口]
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
  var idCount=0;
  var flag = true;   
  $("input:checkbox[name='chkSetAreaList']:checked").each(function(){
    idCount++;
  });
    // alert(idCount)
  if(idCount===0){
    flag = false;
    alert("请选择批量设置对象！");  
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
            text = text + $(this).parent().next().text() + ",";
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

//满足条件包邮模板
InclPostage = function(secand) {
    Region = "";
    CarryWay = 0;
    First = 0;
    SelectIf = 0;
    this.Secand = secand;
}
//运送方式模板
CarryMode = function(area, isdefault, carryway) {
    this.Region = area;
    this.IsDefault = isdefault;
    this.CarryWay = carryway;
    FirstSum = 0;
    SecondSum = 0;
    FirstAmount = 0;
    SecondAmount = 0;
}

addAreaContent = function(id,ways,name,unit){
    var list="";
    list+='          <div id="fm_set_'+id+'" class="fm_addArea_content" shipWays="'+ways+'">';
    list+='            <div class="list_div fm_addArea_title fm_blue" id="FM_theDefaultShipping">';
    list+='              <div class="fm_setPrice_info">';
    list+='                <label>';
    list+='                  默认运费<input class="text validate" type="text" name="firstPiece" isrequired="yes" tipmsg="" datatype="jsInt" value="1"/><span>'+name+'内</span>';
  list+='                          <input class="text validate" type="text"  isrequired="yes" name="firstPrice" tipmsg="" datatype="jsRmb" value="" placeholder="0.00">元,';
    list+='                   每增加  <input class="text validate" type="text" name="addPiece" isrequired="yes"  tipmsg="" datatype="jsInt" value="1"><span>'+name+'，</span>';
  list+='                   增加运费<input type="text" class="text validate" isrequired="yes" name="addPrice" tipmsg="" datatype="jsRmb" value="" placeholder="0.00">元 ;'
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
    list+='              <label name="chkSetAreaList"><span class="batch_span"><input class="all" type="checkbox" name="chkSetAreaList" value="全选">全选</span>';
    list+='              <input class="btn ol_colorbtn ol_bluebtn" name="chkSetAreaList" type="button" value="批量删除" onclick="subDelete()" />';
    list+='              <input class="btn ol_colorbtn ol_bluebtn" name="chkSetAreaList" type="button" value="批量设置运费" onclick="setVolumeShipping()"/></label>';
    list+='              <input class="btn ol_colorbtn ol_bluebtn fm_set_up_freight" name="setUpFreight" type="button" value="为指定城市添加运费" onclick=""/>';
    list+='            </div>';
    list+='          </div>    ';   
    return list;
  }

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
  addAreaList = function(num){
    var list="";
    list+='                  <tr class="setAreaList">';
    list+='                    <td>';
    list+='                      <input class="ids" type="checkbox" value="setAreaList" name="chkSetAreaList">';
    list+='                    </td>';
    list+='                    <td class="cell-area">';
    list+='                      <div class="area-group">';
    list+='                       <p class="area add_region_range" id="express'+num+'">未添加地区</p>';
    list+='                      </div>';
    list+='                      <input type="hidden" class="text validate" value="" name="inclpost" >';
    list+='                   </td>';
    list+='                    <td>';
    list+='                      <input class="input-text text validate" type="text" aria-label="首件" isrequired="yes" tipmsg="" datatype="jsInt"  value="1" data-field="start" name="fmfirstPiece">';
    list+='                    </td>';
    list+='                    <td>';
    list+='                      <input class="input-text text validate" type="text" aria-label="首费" isrequired="yes" tipmsg="" datatype="jsRmb""  value="" data-field="postage" name="fmfirstPrice" placeholder="0.00">';
    list+='                    </td>';
    list+='                     <td>';
    list+='                      <input class="input-text text validate" type="text" aria-label="续件" isrequired="yes" tipmsg="" datatype="jsInt"  value="1" data-field="plus" name="fmaddPiece">';
    list+='                    </td>';
    list+='                    <td>';
    list+='                      <input class="input-text text validate" type="text" aria-label="续费" isrequired="yes" tipmsg="" datatype="jsRmb"  value="" data-field="postageplus" name="fmaddPrice" placeholder="0.00">';
    list+='                    </td> ';                                            
    list+='                    <td class="list_td_left">';
    list+='                      <input class="btn ol_colorbtn ol_bluebtn selectAreas" type="button" value="选择地区" onclick="CheckArea(this)">';
    list+='                      <input class="btn ol_colorbtn ol_redbtn delectAreas" type="button" value="删除" onclick="delectList(this)">';
    list+='                    </td>';
    list+='                  </tr>    ';
    return list;
  }            

//解析地区获取最后一个id值
regionMg = function(str){
  var areaArr = new Array();
  var regionArr = new Array();
  var regionObj = {};
  areaArr = str.split('|'); 
  $.each(areaArr,function(i,item){
    var codeArr = new Array();
    codeArr = item.split('_');
    regionObj.rea_id = "0";
    regionObj.as_id = "0";
    regionObj.end_area = codeArr[codeArr.length-1];
    regionArr.push(regionObj)
  })
//  console.log(regionArr)
  return regionArr;
}
//当前时间
function CurrTime(){ 
  var YyMmDd;  
  var today = new Date(); 
  YyMmDd = today.getFullYear() + "-" + LongNumber(today.getMonth()+1) + "-" + LongNumber(today.getDate());   
  YyMmDd+= ' ' + LongNumber(today.getHours()) + ":" + LongNumber(today.getMinutes()) + ":" + LongNumber(today.getSeconds());   
  return YyMmDd; 
} 
function LongNumber(num){ 
  var tempNum; 
  if(parseInt(num)<10)    {tempNum = "0" + parseInt(num)} 
  else                    {tempNum = parseInt(num)} 
  return tempNum; 
}  
//提交前验证
function CheckInput() {
    //判断运送方式
    var sum = 0;
    var isok = true;

    if($("#FM_TemplateTitle").val() === ""){
        alert("请填写运费模板名称");
        return false;
    }
    if($("#area_id_div select.select").val() === "0"){
        alert("请填写宝贝地址");
        return false;
    }         
    if($("#FM_DeliveryTime").val() === "0"){
        alert("请填写发货时间");
        return false;
    }      
    $("input[name='fmtplType']").each(function () {
        if ($(this).prop("checked"))
            sum++;
    });
    if (sum <= 0) {
        alert("请至少选择一种运送方式！");
        return false;
    }
    //判断发货时间
    if ($("input[name='inclpost']").val() == "") {
        alert("未添加地区");
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