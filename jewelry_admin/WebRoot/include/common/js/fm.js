/*-----运费模板style ----*/
/*-----Auther:cbq-----*/
/*----Version:1.0.0.0----*/

/****运费模板新增页脚本*****/
$(document).ready(function () {
    /**
     * 发货时间遍历
     * @param {[string]} id   [当前位置]
     */
    var _deliveryTimeArr =['一天内','两天内','三天内','一个星期','一个月'];
    addDeliveryTime = function(id){
      var list="";
 
      $.each(_deliveryTimeArr,function(n,value){
        list+=" <option value=''>"+value+"</option> ";
      });
      $(id).append(list)
      
    }
    addDeliveryTime('#FM_DeliveryTime');    

    /**
     * [为指定城市添加运费点击事件]
     */
    var num=0;//计数
    $(document).on('click','.fm_set_up_freight',function(){
      var fmPricingWays = $("#fmPricingWays input[type='radio']:checked").val();
   //   alert(fmPricingWays)
      switch(fmPricingWays){
        case "piece": 
          addAreaListHandle(this,addAreaHead("件","（件）"),addAreaList(num))
          break;
        case "weight":
          addAreaListHandle(this,addAreaHead("重量","（kg）"),addAreaList(num))
          break;
        case "volume":
          addAreaListHandle(this,addAreaHead("体积","（m³）"),addAreaList(num))
          break;
        default:  
          break 
      }                   
      num++;
      // alert(num)
    })   
    /**
     * *addAreaList 为指定城市添加运费点击事件
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
        case 'sellerBearFre'://卖家
        	art.dialog({
        	    title: '提示',
        	    content: _msgNo,
        	    okValue: '确认',
        	    cancelValue: '取消',
        	    width:300,
        	    ok: function () {
        	    	$('.fm_addArea_content').hide();
        	    },cancel: function () {
        	    	return false;
        	    }
        	});
/*          if(!fmConfirm(_msgNo)){
            return false;
          }else{
            $('.fm_addArea_content').hide();
          }*/
          break;
        case 'buyerBearFre'://买家
        	art.dialog({
        	    title: '提示',
        	    content: _msgYes,
        	    okValue: '确认',
        	    cancelValue: '取消',
        	    width:300,
        	    ok: function () {
        	    	$('.fm_addArea_content').hide();
        	    },cancel: function () {
        	    	return false;
        	    }
        	});
/*          if(!fmConfirm(_msgYes)){
            return false;
          }else{
            $('.fm_addArea_content').hide();
          }*/
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
      
      if(/*flag && */fmIsMail === "buyerBearFre"){  
        switch(fmPricingWays){
          case "piece": 
            _appendAreaContent = addAreaContent('piece',fmtplType,'件',' ');
            break;
          case "weight":
            _appendAreaContent = addAreaContent('weight',fmtplType,'重量','kg');
            break;
          case "volume":
            _appendAreaContent = addAreaContent('volume',fmtplType,'体积','m³');
            break;
          default:
            break              
        }
        $('#FM_addAreaCont').append(_appendAreaContent)        
        //表格样式
        if($(".fm_setPrice_table").length>0){
          $(".fm_setPrice_table").projectTable();
        }         
      }else{
        
      }
    }

    //选中省份或者城市全选中下属地区
    $(".J_Group,.J_Province").each(function () {
        $(this).change(function () {
            console.log($(this).parent().hasClass("group-label"))
            if ($(this).parent().hasClass("group-label")) {
                //省份
                if ($(this).prop('checked')) {
                    $(this).parent().parent().next("div").find("input[type='checkbox']").prop("checked", true);
                    return;
                } else {
                    $(this).parent().parent().next("div").find("input[type='checkbox']").prop("checked", false);
                    return;
                }
            }
            else {
                //城市
                console.log($(this).prop('checked'))
                if ($(this).prop('checked')) {
                    $(this).parent().parent().children().find("input[type='checkbox']").prop("checked", true);
                } else {
                    $(this).parent().parent().children().find("input[type='checkbox']").prop("checked", false);
                }
                var checkAll = true;
                $(this).parent().parent().parent().find("input.J_Province").each(function () {
                    if (!$(this).prop("checked")) {
                        checkAll = false;
                    }
                    return checkAll;
                });
                $(this).parent().parent().parent().prev(0).find("input.J_Group").prop("checked", checkAll);
            }
        });
    });
    //选择区县
    $(".J_City").each(function () {
        $(this).change(function () {
            // alert(222)
            var sum = 0;
            var cksum = 0;
            $(this).parent().parent().children().find("input").each(function () {
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
                $(this).parent().parent().prev().find("input").prop("checked", true);
            }
            else {
                $(this).parent().parent().prev().find("input").prop("checked", false);
            }
        });
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
});

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
  if(confirm("确定删除吗？")==true)
  {         
    var idlist="";
    var idCount=0;
    var name = "chkSetAreaList";

    $("input:checkbox[name='"+name+"']:checked").each(function(){
      idlist = idlist+$(this).val()+',';
      idCount++;
    });
    // alert(idCount)
    if(idCount===0){
      alert("请选择删除对象！");
      return ;
    }
    $("input:checkbox[name='"+name+"']:checked").each(function(){
    //  $("tr[class="+$(this).val()+"]").remove();
      $(this).parents('tr.setAreaList').remove();
      return true;  
    })

  }
}
// FM_theDefaultShipping
setVolumeShipping = function(){
    var html = $('#FM_theDefaultShipping').html();
    $('#volumeShipping .fm_addArea_container').append(html);
    $('#volumeShipping').show();
    alert(html)
}
/**
 * [ShowCounty 显示区县]
 * @param {[string]} obj [当前位置]
 */
ShowCounty = function(obj) {
    if ($(obj).parent().parent().hasClass("showCityPop")) {
        $(obj).parent().parent().removeClass("showCityPop");
    } else {
        $("div").removeClass("showCityPop");
        $(obj).parent().parent().addClass("showCityPop");
    }
}

/**
 * [HideCounty 隐藏区县]
 * @param {[string]} obj [当前位置]
 */
HideCounty = function(obj) {
    $(obj).parent().parent().parent().removeClass("showCityPop");
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
    var mode = $(obj).parent().siblings('.cell-area').find('p').prop("id");

    var offset = $(obj).offset();
   /* $("#area").css({ position: "absolute", 'top': offset.top + 10, 'left': offset.left - 20, 'z-index': 2 });*/
    $("#area").css({ position: "fixed", 'top': "20%" , 'left': 0, 'z-index': 2 ,'background':'#fff','margin':'0 auto','left':0,'right':0,'width':'580px'});
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
       /* if ($(this).prop("checked")) {
            text = text + $(this).next().text() + ",";
            value = value + $(this).val() + "|";
            return;
        }*/
        $(this).parent().parent().next().find(".J_Province").each(function () {
            if ($(this).prop("checked")) {
                text = text + $(this).next().text() + "，";
                value = value + $(this).val() + "|";
                return;
            }
            $(this).parent().next().find(".J_City").each(function () {
                if ($(this).prop("checked")) {
                    text = text + $(this).next().text() + "，";
                    value = value + $(this).val() + "|";
                    return;
                }
            });
        });
    });
    text = text.substr(1, text.length - 2);
    value = value.substr(1, value.length - 2);
    if ($("#proselect").val() == "0") {
        text = "全国";
        value = "0";
    }
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

//提交表单
SubmitTemplate = function() {
    submitData();
    // if (!CheckInput())
    //     return;
    var code = $('#FM_TemplateCode').val();//模板编号
    var name = $("#FM_TemplateTitle").val();//模板名
    var shopAddr = $("#FM_Address").val();//宝贝地址
    var dispatchTime = $("#FM_DeliveryTime").val();//发货时间
    var isInclPostage = $("input[name='bearFreight']:checked").val();//包邮
    var valuationModel = parseInt($("input[name='fmPricingWays']:checked").val());//计价方式
    
    name = name.replace(/(^\s*)|(\s*$)/g, "");
    if (name=="") {
    	art.dialog({
		    title: '提示',
		    content: "运费模板名称不能为空",
		    time:2000
		});
		return false;
    }
    // var isInclPostageByif = $("#J_SetFree").prop("checked") ? 1 : 0;//条件包邮
    //包邮条件
    // var inclPostages = new Array();
    // if ($("#J_SetFree").prop("checked")) {
    //     var inclpost = $("#J_Tbody").find("tr");
    //     for (var i = 0; i < inclpost.length; i++) {
    //         var inclPostage = new InclPostage(0);
    //         inclPostage.Region = inclpost.eq(i).find("input[name='inclpost']").val();
    //         inclPostage.CarryWay = inclpost.eq(i).find("select[name='transType']").val();
    //         inclPostage.First = inclpost.eq(i).find("input[firstname='preferentialStandard']").val();
    //         inclPostage.SelectIf = inclpost.eq(i).find("select[name='designated']").val();
    //         if (inclPostage.SelectIf == "2") {
    //             inclPostage.Secand = inclpost.eq(i).find("input[firstname='preferentialMoney']").val();
    //         }
    //         inclPostages.push(inclPostage);
    //     }
    // }
    //运送方式
    // var carryModes = new Array();
    // if ($("#J_buyerBearFre").prop("checked")) {
    //     //自有物流的记录数
    //     if ($("#J_DeliveryEXPRESS").prop("checked")) {
    //         var expressMode = new CarryMode("0", 1, 0);
    //         var expressInput = $("div[data-delivery='express']").find("div.default").find(":text");
    //         expressMode.FirstSum = parseFloat(expressInput.eq(0).val());
    //         expressMode.SecondSum = parseFloat(expressInput.eq(2).val());
    //         expressMode.FirstAmount = parseFloat(expressInput.eq(1).val());
    //         expressMode.SecondAmount = parseFloat(expressInput.eq(3).val());
    //         carryModes.push(expressMode);

    //         if ($("#expressdiv").is(":visible")) {
    //             $("tbody[mode='express']").find("tr").each(function () {
    //                 var carryMode = new CarryMode("0", 0, 0);
    //                 var inputs = $(this).find(":text");
    //                 carryMode.FirstSum = parseFloat(inputs.eq(0).val());
    //                 carryMode.SecondSum = parseFloat(inputs.eq(2).val());
    //                 carryMode.Region = $(this).find("input[name='express']").val();
    //                 carryMode.FirstAmount = parseFloat(inputs.eq(1).val());
    //                 carryMode.SecondAmount = parseFloat(inputs.eq(3).val());
    //                 carryModes.push(carryMode);
    //             });
    //         }
    //     }
        //ems的记录数
        // if ($("#J_DeliveryEMS").prop("checked")) {
        //     var emsMode = new CarryMode("0", 1, 1);
        //     var emsInput = $("div[data-delivery='ems']").find("div.default").find(":text");
        //     emsMode.FirstSum = parseFloat(emsInput.eq(0).val());
        //     emsMode.SecondSum = parseFloat(emsInput.eq(2).val());
        //     emsMode.FirstAmount = parseFloat(emsInput.eq(1).val());
        //     emsMode.SecondAmount = parseFloat(emsInput.eq(3).val());
        //     carryModes.push(emsMode);

        //     if ($("#emsdiv").is(":visible")) {
        //         $("tbody[mode='ems']").find("tr").each(function () {
        //             var inputs = $(this).find(":text");
        //             var carryMode = new CarryMode("0", 0, 1);
        //             carryMode.FirstSum = parseFloat(inputs.eq(0).val());
        //             carryMode.SecondSum = parseFloat(inputs.eq(2).val());
        //             carryMode.Region = $(this).find("input[name='ems']").val();
        //             carryMode.FirstAmount = parseFloat(inputs.eq(1).val());
        //             carryMode.SecondAmount = parseFloat(inputs.eq(3).val());
        //             carryModes.push(carryMode);
        //         });
        //     }
        // }
        //快递的记录数
        // if ($("#J_DeliveryPOST").prop("checked")) {
        //     var postMode = new CarryMode("0", 1, 2);
        //     var postInput = $("div[data-delivery='post']").find("div.default").find(":text");
        //     postMode.FirstSum = parseFloat(postInput.eq(0).val());
        //     postMode.SecondSum = parseFloat(postInput.eq(2).val());
        //     postMode.FirstAmount = parseFloat(postInput.eq(1).val());
        //     postMode.SecondAmount = parseFloat(postInput.eq(3).val());
        //     carryModes.push(postMode);

        //     if ($("#postdiv").is(":visible")) {
        //         $("tbody[mode='post']").find("tr").each(function () {
        //             var inputs = $(this).find(":text");
        //             var carryMode = new CarryMode("0", 0, 2);
        //             carryMode.FirstSum = parseFloat(inputs.eq(0).val());
        //             carryMode.SecondSum = parseFloat(inputs.eq(2).val());
        //             carryMode.Region = $(this).find("input[name='post']").val();
        //             carryMode.FirstAmount = parseFloat(inputs.eq(1).val());
        //             carryMode.SecondAmount = parseFloat(inputs.eq(3).val());
        //             carryModes.push(carryMode);
        //         });
        //     }
        // }
    // }
    //模板对象
    var fareTemplate =
        {
            Code: code,
            Name: name,
            ShopAddr: shopAddr,
            DispatchTime: dispatchTime,
            IsInclPostage: isInclPostage,
            ValuationModel: valuationModel,
            // IsInclPostageByif: isInclPostageByif,
            InclPostageProvisos: inclPostages,
            CarryModes: carryModes
        };
    // $.ajax({
    //     url: "Create",
    //     data: fareTemplate,
    //     type: "POST",
    //     success: function (data) {
    //         alert(data.msg);
    //         if (data.isok)
    //             location.href = "index";
    //     },
    //     error: function () {
    //         alert("添加失败！");
    //     }
    // });
}
//提交前验证
function CheckInput() {
    //判断运送方式
    var sum = 0;
    var isok = true;
    $("input[name='tplType']").each(function () {
        if ($(this).prop("checked"))
            sum++;
    });
    if (sum <= 0) {
        alert("请至少选择一种运送方式！");
        return false;
    }
    //判断发货时间
    if ($("#J_prescription").val() == "0") {
        alert("请设置发货时间！");
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
