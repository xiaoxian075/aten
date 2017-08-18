<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
    <title>配置专题页</title>
    <style>
        body {
            padding-left: 0px;
            padding-right: 0px;
            padding-top: 0px;
            padding-bottom: 0px;
            margin: 0px;
            background-color: #F9F9F9;
            font-family: Verdana, Arial,Vrinda,Tahoma;
            line-height: 175%;
            font-size:12px;
            color:#666;
        }
        html {
            padding-left: 0px;
            padding-right: 0px;
            padding-top: 0px;
            padding-bottom: 0px;
            margin: 0px;
        }

        a {
            color: #666;
            text-decoration:none;
        }
        a:hover {
            color: #0099CC;
        }

        img {
            border-top-width: 0px;
            border-right-width: 0px;
            border-bottom-width: 0px;
            border-left-width: 0px;
            display:inline;
        }
        ul{
            margin:0;
            padding:0;
            list-style-position: outside;
            list-style-type: none;
        }
        li{
            margin:0;
            padding:0;
        }
        div{
            margin:0;
            padding:0;
        }

        .clear{ clear:both}

        main{
            width: 99%;
            border: 1px solid #B4C9C6;
            margin: 0px;
            background-color:#FFFFFF;
            padding: 5px 0 5px 0;
            overflow: auto;
            height: auto !important;
            height:570px;
            min-height:570px !important;
        }
        .contentbox{position:relative; font-size:12px;}
        .contentbox th{border-bottom:1px solid #B4C9C6; text-align:left;}
        .contentbox input,.contentbox select,.contentbox textarea{ font-size:12px;}

        .aa span{
            cursor: pointer;
        }
        uu{
            height:20px;line-height:20px;float:left;margin-top:3px;
        }
    </style>

    <style type="text/css">
        #main{
            overflow: auto;
            overflow-x: hidden;
            position: relative;
        }
        .mainUl{
            padding: 0;
            margin: 0;
        }
        .mainLi{
            list-style: none;
            cursor: move;
        }
        .module-gallery{
            width: 195px;
            background: #eeeeee;
        }
        .module-list{
            overflow:hidden;
        }
        .module-row:first-child .module-list{
            display: block
        }
        .module-title{
            padding: 10px 15px;
            color: #333333;
            border-bottom: 1px solid #dddddd;
            background-color: #eeeeee;
            border-top-right-radius: 1px;
            border-top-left-radius: 1px;
            cursor: pointer;
        }
        .module-title .arrow-fold{
            float: right;
            width: 12px;
            height: 9px;
            margin: 6px 0 0 0;
            background-image: url('/include/admin/image/customized/arrow.png');
            background-position: center;
            background-repeat: no-repeat;
            background-size: 100%;
        }
        .module-list .module{
            width: 87px;
            height: 79px;
            font-size: 12px;
            line-height: 20px;
            text-align: center;
            padding: 5px;
            padding-top: 10px;
            cursor: pointer;
            padding-bottom: 0;
            position: relative;
            float: left;
        }
        .module .thumb {
            width: 60px;
            height: 42px;
            overflow: hidden;
            margin: 0 auto;
        }
        .module .thumb img{
            width: 60px;
            display: block;
        }
        .module:hover .thumb img{
            margin-top: -60px;
        }
        .module .title{
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            line-height: 1.5;
        }
        .module-move .price{
            color: red;
            font-size: 14px;
        }
        .dots-list {
            margin-top: -25px;
            width: 100%;
            text-align: center;
        }
        .bar-dots{
            position: absolute;
            left: 0;
            bottom: 10px;
        }
        .dots-list .dot{
            width: 5px;
            height: 5px;
            border-radius: 50%;
            background: #ccc;
            margin: 0 5px;
            display: inline-block;
        }
        .dots-list .dot:first-child{
            background: #8EC31F
        }
        .bar-item{
            position: absolute;
            width: 100%;
            height: 100%;
            text-align: center;
        }
    </style>
    <script type="text/javascript" src="/include/admin/js/customizedPage/ddsort.js"></script>
    <script type="text/javascript" src="/include/admin/js/customizedPage/tools.js"></script>
</head>
<body>
<%--<div style="border:1px solid red;height:600px;width: 800px;">--%>

    <%--<div class="detail_img_list">--%>
        <%--<img class="img_height120 separate" id="img_one_name" />--%>
        <%--<input type="hidden" class="validate" changetip="one_name_div"  id="hidden_one_name" name="one_name" value="one_url" />--%>
            <%--<div style="width:150px">--%>
                <%--<div style="width: 55%;float:left" id="file_one_name"></div>--%>
                <%--<div style="float:left"><a id="cancel_one_name1" onclick="cancelImg('one_name');" class="imgCancel uploadify-cancelbutton" href="javascript:void(0)">取消</a></div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<script>image_custom_upload("one_name");</script>--%>
    <%--</div>--%>

<%--</div>--%>


<div class="main" style="height:auto; padding-top:1px;">
    <input type ="hidden" id="ossPath" value ="${ossPath}"/>
    <input type ="hidden" id="pageUnique" value ="${info.pageUnique}"/>
    <%--模块--%>
    <div style="width:100%">
        <div style="width:330px;float:left;margin:15px 0px 0px 15px;">
            <!--模块区-->
            <div class="module-gallery">
                <div class="module-row">
                    <div class="module-title">
                        <span>图文类</span>
                        <i class="arrow-fold"></i>
                    </div>
                    <div class="module-list">
                        <div class="module module-one"  onclick="addDiv('.mk0','1','one')">
                            <div class="thumb">
                                <img src="/include/admin/image/customized/one_row.png">
                            </div>
                            <div class="title">单列图片模块</div>
                        </div>
                        <div class="module module-two" onclick="addDiv('.mk2','2','two')">
                            <div class="thumb">
                                <img src="/include/admin/image/customized/two_row.png">
                            </div>
                            <div class="title">双列图片模块</div>
                        </div>
                        <div class="module module-three" onclick="addDiv('.mk1','3','three')">
                            <div class="thumb">
                                <img src="/include/admin/image/customized/three_row.png">
                            </div>
                            <div class="title">三列图片模块</div>
                        </div>
                        <div class="module module-four" onclick="addDiv('.mk3','4','four')">
                            <div class="thumb">
                                <img src="/include/admin/image/customized/four_row.png">
                            </div>
                            <div class="title">四列图片模块</div>
                        </div>
                        <div class="module module-bar style='display:none'" onclick="addDiv('.mk6','1','bar')">
                            <div class="thumb">
                                <img src="/include/admin/image/customized/swiper.png">
                            </div>
                            <div class="title">轮播图模块</div>
                        </div>
                    </div>
                </div>
                <div class="module-row">
                    <div class="module-title">
                        <span>宝贝类</span>
                        <i class="arrow-fold"></i>
                    </div>
                    <div class="module-list">
                        <div class="module"  onclick="addDiv('.mk5','1','goodsList')">
                            <div class="thumb">
                                <img src="/include/admin/image/customized/goods.png">
                            </div>
                            <div class="title">智能双列</div>
                        </div>
                    </div>
                </div>
                <div class="module-move" style="display: none">
                    <div style="width:150px">
                        <a class="mbcss" onclick="addDiv('.mk0','1','one')">
                            <div style="width:100%;" class="mk0">
                                <div act="href" style="float:left;width:100%;height:100%;"><img src="/include/admin/image/customized/one.png" width="100%" height="100%"></div>
                            </div>
                        </a>
                        <a class="mbcss" onclick="addDiv('.mk1','3','three')">
                            <div style="width:100%;" class="mk1">
                                <div act="href" style="float:left;width:33.3%;height:100%;"><img src="/include/admin/image/customized/img1.png" width="100%" height="100%"></div>
                                <div act="href" style="float:left;width:33.3%;height:100%;"><img src="/include/admin/image/customized/img2.png" width="100%" height="100%"></div>
                                <div act="href" style="float:left;width:33.3%;height:100%;"><img src="/include/admin/image/customized/img3.png" width="100%" height="100%"></div>
                            </div>
                        </a>
                        <a class="mbcss" onclick="addDiv('.mk2','2','two')">
                            <div style="width:100%;" class="mk2">
                                <div act="href" style="float:left;width:50%;height:100%;"><img src="/include/admin/image/customized/img1.png" width="100%" height="100%"></div>
                                <div act="href" style="float:left;width:50%;height:100%;"><img src="/include/admin/image/customized/img2.png" width="100%" height="100%"></div>
                            </div>
                        </a>
                        <a class="mbcss" onclick="addDiv('.mk3','4','four')">
                            <div style="width:100%;" class="mk3">
                                <div act="href" style="float:left;width:25%;height:100%;"><img src="/include/admin/image/customized/img1.png" width="100%" height="100%"></div>
                                <div act="href" style="float:left;width:25%;height:100%;"><img src="/include/admin/image/customized/img2.png" width="100%" height="100%"></div>
                                <div act="href" style="float:left;width:25%;height:100%;"><img src="/include/admin/image/customized/img3.png" width="100%" height="100%"></div>
                                <div act="href" style="float:left;width:25%;height:100%;"><img src="/include/admin/image/customized/img4.png" width="100%" height="100%"></div>
                            </div>
                        </a>
                    </div>
                    <div style="width:150px;">宝贝类</div>
                    <div style="width:150px">
                        <a class="mbcss" onclick="addDiv('.mk5','1','goodsList')">
                            <div class="mk5">
                                <div act="href" style="float:left;width:49%;height:100%;background:#fff">
                                  <img src="/include/admin/image/customized/goods_pic.jpg" width="100%" height="100%">
                                  <div style="padding-left:5px">钟爱一生</div>
                                  <div class="price" style="color:red;padding-left:5px">￥100.00</div>
                                </div>
                                <div act="href" style="float:right;width:49%;height:100%;background:#fff">
                                  <img src="/include/admin/image/customized/goods_pic.jpg" width="100%" height="100%">
                                  <div style="padding-left:5px">钟爱一生</div>
                                  <div class="price" style="color:red;padding-left:5px">￥100.00</div>
                                </div>
                            </div>
                        </a>
                    </div>
                    <!-- 轮播类 -->
                    <div  tyle="width:150px">
                     <a class="mbcss">
                        <div class="mk6">
                            <div style="float:left;height:134px;">
                                <img src="/include/admin/image/customized/one.png" width="100%" height="100%">
                                <div class="dots-list">
                                    <i class="dot"></i>
                                    <i class="dot"></i>
                                    <i class="dot"></i>
                                </div>
                            </div>
                        </div>  
                     </a>
                    </div>
                </div>
            </div>
        </div>
        <%--展示区域--%>
        <div style="width:460px;float:left;margin:15px 0px 0px 0px;">
            <div id="main" style="width:304px;height: 585px;border:1px solid #ddd;
            position:relative;overflow-y:scroll;padding:5px; ">
                <ul class="mainUl">

                </ul>
            </div>
            <div align="center" style="width:304px;padding-top:10px;">
            <button id="insert" class="btn btn-success ol_btn">保存</button>
            </div>
        </div>
        <%--设置区域--%>
        <div style="float:left;margin-top:15px;">
            <input type="hidden" id="stop" />
            <div id="upimg"  style="width:430px;height: 660px;border:1px solid #ddd;position:relative;overflow:scroll; ">

            </div>
            <div align="center" style="padding:10px 0px 10px 0px;"><button id="show" class="btn btn-success  ol_btn">预览</button></div>
            <div  >
		                  注:同一行模块中多张图片宽度,高度必须一至(尺寸不做限制)<br>
		                   上传图片小于2M
            </div>
        </div>

    </div>

    <div id="body"></div>

    <div id="historyStr" style="display:none">
        ${info.pageBody}
    </div>


    <script type="text/javascript">
        window.onload=function(){
            initData();
        }
    </script>


</div>
<div class="layui-layer-shade" id="layui-layer-shade" times="1"
     style="display:none;z-index:100; background-color:#000; opacity:0.3; filter:alpha(opacity=30);"></div>

<script src="/include/admin/js/customizedPage/ddsort.js"></script>
<script>
    $("#main").DDSort({
        target: 'li',
        floatStyle: {
            'border': '1px solid #ccc',
            'background-color': '#fff'
        }
    });
    $(function () {
        $('.module-row .module-title').click(function () {
            $(this).siblings('.module-list').slideToggle();
        })
    })
</script>
</body>


</html>

