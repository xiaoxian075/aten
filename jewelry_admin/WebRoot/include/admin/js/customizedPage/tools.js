var parentId;
var basePath = "";
var ossPath = "";

//上传单示例图片上传
function image_custom_upload(ident){
    //上传控件ID
    var file_id="file_"+ident;
    uploadComponent(file_id,null,false,ident,"hotel");
}



function randNumID() {
    var date = new Date();
    var seperator1 = "";
    var seperator2 = "";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds()+GetRandomNum(200,999);
    return currentdate;
}

function GetRandomNum(Min, Max) {
    var Range = Max - Min;
    var Rand = Math.random();
    return (Min + Math.round(Rand * Range));
}
/**
 * 删除模块
 */
function divclose(t) {
    $("#" + t).remove();
    $("#section_" + t).remove();
    $("#li_" + t).remove();
}
/**
 * 显示 操作 模块
 */
function showDiv(t) {
    $("#" + t + " >aa").show();
}
/**
 * 隐藏  操作 模块
 */
function hideDiv(t) {
    $("#" + t + " >aa").hide();
}
/**
 * 添加模版块
 */
function addDiv(cval, num,moduleType) {
    var divHtml = $(cval).html();
    var idval = randNumID();
    divHtml = "<div id='" + idval + "' num='" + num + "' moduleType='"+moduleType+"' style='width:100%;' onmouseover=\"showDiv('" + idval + "')\" onmouseout=\"hideDiv('" + idval + "')\" ><a>" + divHtml + "</a></div>";
    //添加分割行
    var divLink = "<div style=\"width:100%;\" id='section_"+idval+"' ><a>     <div act=\"href\" style=\"float:left;width:100%;height:100%;\">         </div>     </a>  </div>";

    $("#main>ul").append("<li class='mainLi' id='li_"+idval+"'>"+divHtml+divLink+"</li>");
    var aaHtml = "<aa class='aa' href='javascript:' onclick=\"divedit('" + idval + "'," + num + ")\" style='cursor:pointer;text-align:right;width:100%;float:left;position:relative;margin-top:-21px;background:#6694fb;display:none;' ><div style='cursor:pointer'>&nbsp;&nbsp;<span style='background:#7ba1f7'>编辑</span>&nbsp;&nbsp;<span onclick='divclose(\"" + idval + "\")' >&nbsp;X&nbsp;</span></div></aa>";
    $('#' + idval).append(aaHtml);
    resetMainLiHeight();
}
function dealString(str){
    str = str == null ? "":str;
    return str;
}
function dealInteger(num){
    num = num == null ? "0":num;
    return num;
}
$(document).ready(function () {

    $("#insert").click(function () {
        /**
         * 遍历 div 模块
         */
        var jsonData = "";
        var moduleList = "";
        $("#main>ul>li>div[moduletype]").each(function () {

            var viewData = "";
            var moduleUnique = $(this).attr("id");
            var moduleHeight = 0;
            var moduleWidth = $(this).width();
            var moduleType = $(this).attr("moduleType");
            var moduleRowsInterval = dealInteger($(this).attr("moduleRowsInterval"));
            var moduleColsInterval = dealInteger($(this).attr("moduleColsInterval"));
            /**
             * 遍历 展示块
             */

            $("#" + moduleUnique + " div[act=href]").each(function () {
                moduleHeight =moduleWidth/ $(this).height();
                var viewUrl =dealString($(this).attr("view_url"));
                var viewImg = dealString($(this).attr("view_img"));
                var viewUrlType = dealInteger($(this).attr("view_url_type"));
                var viewTitle = dealString($(this).attr("view_title"));
                var viewPrice = dealInteger($(this).attr("view_price"));
                var viewSalePrice = dealInteger($(this).attr("view_sale_price"));
                viewData =viewData + "{ \"viewUrl\": \""+viewUrl+"\"," +
                    " \"viewImg\": \""+viewImg+"\"," +
                    "\"viewUrlType\": \""+viewUrlType+"\"," +
                    "\"viewTitle\": \""+viewTitle+"\"," +
                    "\"viewPrice\": \""+viewPrice+"\"," +
                    "\"viewSalePrice\": \""+viewSalePrice+"\"" +
                    "},";
            });
            //商品列表模块 , 宽高比固定
            if(moduleType == "goodsList"){
                moduleHeight = "2";
            }
            moduleList += "{"
            +"                \"viewList\": ["
            +                   viewData
            +"                ], "
            +"        	    \"moduleUnique\": \""+moduleUnique+"\", "
            +"               \"moduleType\": \""+moduleType+"\","
            +"               \"moduleHeight\": \""+moduleHeight+"\","
            +"               \"moduleRowsInterval\": \""+moduleRowsInterval+"\","
            +"               \"moduleColsInterval\": \""+moduleColsInterval+"\""
            +"              },";
        });
        jsonData="{"
            +"    \"data\": {"
            +"          \"moduleList\": ["
            +           moduleList
            +"          ]"
            +"       }"
            +" }";
        var body = $("#main>ul").html();
        saveModule(body,jsonData);
    });

    function saveModule(body,json) {
        var pageUnique = $("#pageUnique").val();
        $.ajax({
            url: basePath + "/admin/customizedPage/saveModule",
            data: {jsonData:json,pageBody:body,pageUnique:pageUnique},
            type: "POST",
            async: false,
            dataType: "text",
            success: function (data) {
                art.dialog({
                    title: '友情提示',
                    content: data,
                    time:2000
                });
            },
            error: function (error) {
                alert(error+"请求失败，请联系管理员");
            }
        });
    }
    $("#show").click(function () {
        var divid = $("#stop").text();
        var moduleType = $("#"+divid).attr("moduleType");
        if(moduleType == "goodsList"){
            showGoodsListModule();
        } else if(moduleType == "bar") {
            showBarListMoudle()
        }else{
            showImgTextModule();
        }

        //$("#body").text($("#main").html());

    });

});
//显示商品列表模块
function showGoodsListModule(){
    var divid = $("#stop").text();
    var goodsList = "<div></div>";
    $("#"+divid+">a").html("");
    var countNum = 0;
    var goods = "";
    $("#"+divid).attr("moduleRowsInterval","30");
    $("#"+divid).attr("moduleColsInterval","30");
    $("#goodsList>div").each(function () {
        countNum =countNum +1 ;
        var goodsData = $(this).attr("goodsData");
        var goodsJson =JSON.parse(goodsData);
        var goodsPrice = ""
        if(goodsJson.view_price !== goodsJson.view_sale_price) {
            goodsPrice = "<div style='text-decoration:line-through'>￥"+goodsJson.view_price+"</div>"
        }
        goods =goods+
            "<div act='href' style='height:100%;width: 50%;float:left;height:203px;'" +
            " view_url = '"+goodsJson.view_url+"' view_img='"+goodsJson.view_img+"' view_url_type = '2' " +
            " view_title = '"+goodsJson.view_title+"' view_price = '"+goodsJson.view_price+"' view_sale_price = '"+goodsJson.view_price+"' " +
            " view_url = '"+goodsJson.view_url+"' goodsData = '"+goodsData+"'>"+
            "<div style='width: 100%;height: 100%;background-color: #FFFFFF;margin:5px;'>" +
            "  <div>" +
            "    <div><img width='140px' height='140px' src='"+goodsJson.view_img+"'/></div>" +
            "    <div >"+goodsJson.view_title+"</div>"+
            "    <div style='color:red;'>￥"+goodsJson.view_sale_price+"</div>"+goodsPrice+
            "  </div>" +
            "</div>"+
            "</div>";
        if(countNum == 2){
            $("#"+divid+">a").append(goods);
            countNum = 0;
            goods = "";
        }
    });
    if(countNum > 0){
        $("#"+divid+">a").append(goods);
    }

}
//显示轮播模块
function showBarListMoudle () {
   var flag = false;
    $('.bar-box').each(function() {
        if(!$(this).find('img').attr('src')) {
            flag = true;
        };
    });
   if(flag) {
    alert("请上传图片");
    return false;
   }
   var divid = $("#stop").text();
   var barList = $("<div style='width:100%;height:134px;overflow:hidden;position:relative' ></div>")
   var dotList = $("<div class='dots-list bar-dots'></div>")
   var num = 0
   $("#"+divid+">a").html("");
   $('.bar-box').each(function(e,i) {
        var href = $("#view_url" + num).val();
        var imgsrc = $("#hidden_view_img" + num).val();
        var view_url_type = $("#view_url_type" + num).val();
        var bar = "<div act='href' class='bar-item' style='position:absolute;width:100%;height:100%;text-align:center'"+
        " view_url='"+href+"' view_img='"+imgsrc+"' view_url_type='"+view_url_type+"'>"+
        "<img src="+ossPath +"/"+ imgsrc+">"+
        "</div>"
        var dot = "<i class='dot'></i>"
        barList.append(bar)
        dotList.append(dot)
        num++
        
   })
   barList.append(dotList)
   $("#"+divid+">a").append(barList)
}
//显示图文模块
function showImgTextModule(){
    var divid = $("#stop").text();
    var num = 0;

    $("#" + divid + " img").each(function () {
        var imgsrc = $("#hidden_view_img" + num).val();
        if (imgsrc.length > 0) {
            $(this).attr("src", ossPath +"/"+ imgsrc); 
        }
        num++;
    });
    num = 0;
    $("#" + divid + " div[act=href]").each(function () {
        var href = $("#view_url" + num).val();
        var imgsrc = $("#hidden_view_img" + num).val();
        var view_url_type = $("#view_url_type" + num).val();
        //$(this).attr("onclick", "window.location.href='" + href + "'");
        /**
         * 检查
         * 1.图片必填
         * 2.URL非空, URL类型 默认 H5
         */
        if (imgsrc.replace(/(^s*)|(s*$)/g, "").length ==0){
            alert("第["+(num+1)+"] 张图片未上传 , 请重新上传!")
            return false;
        }
        if (view_url_type.replace(/(^s*)|(s*$)/g, "").length ==0){
            if(href.replace(/(^s*)|(s*$)/g, "").length > 0){
                alert("第["+(num+1)+"] 重新选择链接!")
                return false;
            }
        }
        $(this).attr("view_url",  href );
        $(this).attr("view_img",  imgsrc);
        $(this).attr("view_url_type",  view_url_type);
        num++;
    });
}

function resInfo(key,imgFileId){
    $("#hidden_view_img"+key).val(imgFileId);
    var imgSrc =   ossPath + "/" + imgFileId;
    $("#img_view_img"+key).attr("src",imgSrc);
}
/*
 * 修改模块信息
 */
function divedit(id, n) {
//显示几个需要添加的图片
    $("#stop").text(id);
    $("#upimg").text("");
    var moduleType = $("#" + id + "").attr("moduletype");
    if(moduleType == "goodsList"){
        var i = 0;
        var txtID = "view_url0";
        var divHtml = "<div style='overflow:hidden;border-bottom:1px solid #000;margin:10px;'><div>" ;
        divHtml = divHtml + "<div style='clear:left' ><input type='hidden' id='view_url_type"+i+"' value=''/> <div>";
        divHtml = divHtml + "<div ><uu>跳转地址:&nbsp;</uu><view id='view"+i+"' style=''><input type='hidden' id='" + txtID + "'   /></view><a href='javascript:' onclick='toPath("+id+","+i+")'><img  style='width:30px;height:30px' src='"+basePath+"/include/admin/image/customized/link.png'></a><span id='note"+i+"'></span><div>";
        divHtml = divHtml + "<div id='goodsList'></div>";
        divHtml = divHtml + "</div>";
        $("#upimg").append(divHtml);
        resetEditGoodsListData(id);
    }else{
        var addStr=""
        barClass = ''
        if(moduleType == 'bar') {
            addStr="<div onclick='barAdd()' style='margin-left:5px;cursor:pointer;font-size:14px;'>添加轮播图</div>"
            barClass = 'bar-box'
            n = $("#" + id + "").find('.bar-item').length == 0 ? 1 : $("#" + id + "").find('.bar-item').length
        }
        $("#upimg").append(addStr)
        for (i = 0; i < n; i++) {
            var imgID = "view_img" + i;
            var txtID = "view_url" + i;
            var divHtml ="<div class='"+barClass+"' style='overflow:hidden;border-bottom:1px solid #000;margin:10px;'><div><uu >模块" + i + "&nbsp;:&nbsp;</uu>" ;
            divHtml = divHtml + "<div  id='viewDiv"+i+"' style='width:50%;height:50%' class='detail_img_list'>";
            divHtml = divHtml + "<img class='img_height120 separate' style='display:block' id='img_"+imgID+"' />";
            divHtml = divHtml + "<input name='hidden_" + imgID + "' id='hidden_" + imgID + "' type='hidden'/>";
            divHtml = divHtml + "    <div style='width:150px'>";
            divHtml = divHtml + "        <div style='width: 55%;float:left' id='file_"+imgID+"'></div>";
            divHtml = divHtml + "        <div style='float:left'><a id='cancel_"+imgID+"' onclick='cancelImg('"+imgID+"');' class='imgCancel uploadify-cancelbutton' href='javascript:void(0)'>取消</a></div>";
            divHtml = divHtml + "    </div>";
            divHtml = divHtml + "</div>";
            divHtml = divHtml + "<script>image_custom_upload('"+imgID+"');</script>";
            divHtml = divHtml + "<div style='clear:left' ><input type='hidden' id='view_url_type"+i+"' value=''/> <div>";
            divHtml = divHtml + "<div ><uu>跳转地址:&nbsp;</uu><view id='view"+i+"' style=''><input type='text' id='" + txtID + "'   /></view><a href='javascript:' onclick='toPath("+id+","+i+")'><img  style='width:30px;height:30px' src='"+basePath+"/include/admin/image/customized/link.png'></a><span id='note"+i+"'></span><div>";
            divHtml = divHtml + "</div>";
            $("#upimg").append(divHtml);

        }
        barI = $('.bar-box').length-1
        resetEditImgTextData(id);
    }

}
// $(function () {
//     $('.addSwiper').on('click',function () {
//         alert(11)
//     })
// })
var barI = $('.bar-box').length-1
function barAdd() {
    var id = randNumID();
    // barI = $('.bar-box').length
    barI++
    var imgID = "view_img" + barI;
    var txtID = "view_url" + barI;
    var divHtml ="<div  class='bar-box' style='overflow:hidden;margin:10px;'><div><uu >模块" + barI + "&nbsp;:&nbsp;</uu>" ;
    var divHtml = divHtml + "<div  id='viewDiv"+barI+"' style='width:50%;height:50%' class='detail_img_list'>";
    divHtml = divHtml + "<img class='img_height120 separate' style='display:block' id='img_"+imgID+"' />";
    divHtml = divHtml + "<input name='hidden_" + imgID + "' id='hidden_" + imgID + "' type='hidden'/>";
    divHtml = divHtml + "    <div style='width:150px'>";
    divHtml = divHtml + "        <div style='width: 55%;float:left' id='file_"+imgID+"'></div>";
    divHtml = divHtml + "        <div style='float:left'><a id='cancel_"+imgID+"' onclick='cancelImg('"+imgID+"');' class='imgCancel uploadify-cancelbutton' href='javascript:void(0)'>取消</a></div>";
    divHtml = divHtml + "    </div>";
    divHtml = divHtml + "</div>";
    divHtml = divHtml + "<script>image_custom_upload('"+imgID+"');</script>";
    divHtml = divHtml + "<div style='clear:left' ><input type='hidden' id='view_url_type"+barI+"' value=''/> <div>";
    divHtml = divHtml + "<div ><uu>跳转地址:&nbsp;</uu><view id='view"+barI+"' style=''><input type='text' id='" + txtID + "'   /></view><a onclick='toPath("+id+","+barI+")'><img  style='width:30px;height:30px' src='"+basePath+"/include/admin/image/customized/link.png'></a><span id='note"+barI+"'></span><div>";
    divHtml = divHtml + "</div>";
    $("#upimg").append(divHtml);
}
/**
 * 图文数据回填
 */
function resetEditImgTextData(id){
    /**
     * 数据回填
     */
    var num = 0;
    $("#" + id + " div[act=href]").each(function () {
        var imgSrc =   ossPath + "/" + $(this).attr("view_img");
        $("#view_url" + num).val($(this).attr("view_url"));
        $("#view_img" + num).val($(this).attr("view_img"));
        $("#view_url_type" + num).val($(this).attr("view_url_type"));
        $("#hidden_view_img" + num).val($(this).attr("view_img"));
        $("#img_view_img" + num).attr("src",imgSrc);
        num++;
    });
}
/**
 * 商品数据回填
 */
function resetEditGoodsListData(id){
    $("#" + id + " div[act=href]").each(function () {
        var goodsData = $(this).attr("goodsData");
        addEditGoods(goodsData);
    });
}
function toPath(id,obj) {
    var parentId = obj;
    var moduleType = $("#" + id + "").attr("moduletype");
    url = "/iframe/customizedPage/linkTools/"+parentId+"/"+moduleType;
    loadPage(url);
}
function openIframe(url) {
    parent.layer.open({
        type: 2,
        title: '链接小工具',
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true, //开启最大化最小化按钮
        area: ['800px', '600px'],
        content: url
    });
}

function loadPage(page){
    //page = "/iframe/customizedPage/linkTools";
    openIframe(page);
}

function initData(){
    ossPath = $("#ossPath").val();
    $("#main>ul").append($("#historyStr").html());
    resetMainLiHeight();
}
function resetMainLiHeight(){
    /**
     * 重置 li 高度
     */
    $("#main>ul>li").each(function () {
        var moduleUnique = $(this).attr("id");
        var liHeight = $("#"+moduleUnique+" div[act=href]").height();
        $(this).css("height",liHeight);
    });
}

function dataAdd(parentId,jsonStr,viewUrlType){
    var divid = $("#stop").text();
    var moduleType = $("#" + divid + "").attr("moduleType");
    if(moduleType == "goodsList"){
        var jsonObj = JSON.parse(jsonStr);
        var img = jsonObj.view_img.substring(10,jsonObj.view_img.length-2);
        var goodsName = jsonObj.view_title;
        jsonObj.view_img = img;
        if(goodsName.length> 10){
            goodsName = goodsName.substring(0,10)+"..."
            jsonObj.view_title = goodsName;
        }
        jsonStr = JSON.stringify(jsonObj);
        addEditGoods(jsonStr);
    }else{
        var jsonObj = JSON.parse(jsonStr);
        $("#view_url" + parentId).val(jsonObj.view_url);
        $("#view_url_type" + parentId).val(jsonObj.view_url_type);
    }
}
/**
 * 添加编辑区商品
 */
function addEditGoods(jsonStr){
    /**
     * 1.检查商品是否重复
     *   无重复  , 添加展示商品
     *   重复 , 无操作
     */
    if(jsonStr == null  ){
        return false;
    }
    var jsonObj = JSON.parse(jsonStr);
    var goodsId = "goods_"+jsonObj.view_url;
    if($("#"+goodsId).length > 0 ){
        alert("商品已添加!");
        return false;
    }
    var priceStr = ''
    if(jsonObj.view_price !== jsonObj.view_sale_price) {
        priceStr = "<span style='color:#666;text-decoration:line-through;margin-left:8px'>￥"+jsonObj.view_price+"</span>"
    }
    var goodsDiv =""+
        "<div id='"+goodsId+"' goodsData='{\"view_img\":\""+jsonObj.view_img+"\",\"view_title\":\""+jsonObj.view_title+"\",\"view_price\":\""+jsonObj.view_price+"\",\"view_sale_price\":\""+jsonObj.view_sale_price+"\",\"view_url\":\""+jsonObj.view_url+"\"}' style='width:100%;height:100px'>" +
        "<div style='float:left;width:100px;height:80px'>" +
        "<img style='width: 80px;height: 80px;' src='"+jsonObj.view_img+"'/>"+
        "</div>"+
        "<div style='width:200px;float:left'>" +
        "<ul>" +
        "<li>"+jsonObj.view_title+"</li>"+
        "<li class='price' style='color:red'>￥"+jsonObj.view_sale_price + priceStr+
        "</li>"+
        "<li style='text-align:right;'><span onclick='divclose(\"" + goodsId + "\")' >删除</span></li>"+
        "</ul>"+
        "</div>"+
        "</div>";

    $("#goodsList").append(goodsDiv)
    return true;
}