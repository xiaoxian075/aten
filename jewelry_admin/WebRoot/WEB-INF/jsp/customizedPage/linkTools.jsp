<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        img {
            width:80px;
            height:80px;
        }
    </style>
    <title>选择链接</title>
    <script type="text/javascript" src="/component/ueditor1.4.3/third-party/jquery-1.10.2.min.js"></script>
    <script src="/component/lay/pageGrid/common.js"></script>
    <link rel="stylesheet" href="/component/lay/layui/css/layui.css">
    <script src="/component/lay/layer/layer.js"></script>
    <script src="/component/lay/layui/layui.js"></script>
    <script src="/component/lay/pageGrid/pageTool.js"></script>
    <script>
        window.onload = function () { 
            var moduleType =  $("#moduleType").val();
            if(moduleType == "goodsList") {
                $('.goodsNone').css('display','none');
                viewUrlType="2"
            }
        }
        //选择一条数据,数据回填
        function selectOne(){
            var checked=$(".layui-form-checked");
            if(checked.length==0){
                alert("请选择一条记录");
                return ;
            }
            var moduleType =  $("#moduleType").val();
            if(moduleType != "goodsList"){
                if(checked.length > 1){
                    alert("只能选择一条记录");
                    return ;
                }
            }
            var codeIndexUrl;
            codeIndexUrl = "";
            for(var i=0;i<checked.length;i++){
                var _this=$(checked[i]).prev();
                if($(_this).attr("primary") != null ){
                    codeIndexUrl =codeIndexUrl + $(_this).attr("primary")+",";
                }
            }
            if(codeIndexUrl!=null && codeIndexUrl.length > 0){
                codeIndexUrl = codeIndexUrl.substr(0,codeIndexUrl.length -1);
            }

            //关闭窗口
            closeWindow();
        }

        //数据渲染对象
        var Render = {
            customImg: function (rowdata, index, value) {
                    return '<img src="'+value+'">';
            },
            customBtn: function (rowdata, index, value) {
                var data = JSON.stringify(rowdata);
                var jsonStrId = window.parent.randNumID();
                return "<input type='hidden' id='"+jsonStrId+"' value='"+data+"' />" +
                        "<input type='button' value=' 选择链接 ' onclick='parentDataAdd("+jsonStrId+")' />";
            }

        };

    </script>
</head>
<body>
<input  type="hidden" id="parentId"  value="${parentId}">
<input  type="hidden"  id="moduleType" value="${moduleType}">
<div class="layui-tab" style="margin:0">
    <ul class="layui-tab-title">
        <li class=" goodsNone" viewUrlType="1">自定义页</li>
        <li viewUrlType="2" select="*" class="layui-this">普通商品</li>
        <li viewUrlType="3" select="*" selected >营销活动商品</li>
        <li viewUrlType="4" select="1" class="goodsNone">优惠券领取</li>
    </ul>
    <div class="layui-tab-content">

        <%--专题页面--%>
        <div class="layui-tab-item" class="goodsNone">
            <form class="layui-form " action="">
                <div class="layui-form-item">
                    <label class="layui-form-label">页面标题</label>
                    <div class="layui-input-inline">
                        <input type="text" name="page_title_vague" placeholder="请输入标题" class="layui-input">
                    </div>
                    <div class="layui-input-inline">
                        <button class="layui-btn search-btn" table-id="customizedPage" lay-submit="" lay-filter="search"><i class="fa fa-search">&nbsp;</i>查询
                        </button>
                        <button type="reset" class="layui-btn layui-btn-primary"><i class="fa fa-refresh">&nbsp;</i>重置</button>
                    </div>
                </div>
            </form>
            <div class="layui-form ">
                <table class="layui-table" id="customizedPage" cyType="pageGrid"
                       cyProps="url:'/admin/customizedPage/getCustomizedPageList',checkbox:'false',pageColor:'#2991d9',pageSize:'5'">
                    <thead>
                    <tr>
                        <!--isPrimary：是否是主键-->
                        <th width="10%" param="{name:'pageTitle'}">页面标题</th>
                        <th width="10%" param="{name:'pageTitle',render:'Render.customBtn'}">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
        <%--普通商品--%>
        <div class="layui-tab-item layui-show">
            <form class="layui-form " action="">
                <div class="layui-form-item">
                    <label class="layui-form-label">普通商品</label>
                    <div class="layui-input-inline">
                        <input type="text" name="goods_name_vague" placeholder="请输入商品名称" class="layui-input">
                    </div>
                    <div class="layui-input-inline">
                        <button class="layui-btn search-btn" table-id="goods" lay-submit="" lay-filter="search"><i class="fa fa-search">&nbsp;</i>查询
                        </button>
                        <button type="reset" class="layui-btn layui-btn-primary"><i class="fa fa-refresh">&nbsp;</i>重置</button>
                    </div>
                </div>
            </form>
            <div class="layui-form ">
                <table class="layui-table" id="goods" cyType="pageGrid"
                       cyProps="url:'/getGoodsList',checkbox:'false',pageColor:'#2991d9',pageSize:'5'">
                    <thead>
                    <tr>
                        <!--isPrimary：是否是主键-->
                        <th width="10%" param="{name:'list_img',render:'Render.customImg'}">商品图片</th>
                        <th width="10%" param="{name:'goods_name'}">商品名称</th>
                        <th width="10%" param="{name:'lower_price'}">商品价格</th>
                        <th width="10%" param="{name:'list_img',render:'Render.customBtn'}">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
       <%--营销活动商品--%>
        <div class="layui-tab-item " >
            <form class="layui-form " action="">
                <div class="layui-form-item">
                    <label class="layui-form-label">商品名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="goods_name" placeholder="请输入" class="layui-input">
                    </div>
                    <div class="layui-input-inline">
                        <button class="layui-btn search-btn" table-id="activityGoods" lay-submit="" lay-filter="search"><i class="fa fa-search">&nbsp;</i>查询
                        </button>
                        <button type="reset" class="layui-btn layui-btn-primary"><i class="fa fa-refresh">&nbsp;</i>重置</button>
                    </div>
                </div>
            </form>
            <div class="layui-form ">
                <table class="layui-table" id="activityGoods" cyType="pageGrid"
                       cyProps="url:'/admin/customizedPage/getActivityGoodsList',checkbox:'false',pageColor:'#2991d9',pageSize:'5'">
                    <thead>
                    <tr>
                        <!--isPrimary：是否是主键-->
                        <th width="10%" param="{name:'activity_name'}">活动名称</th>
                        <th width="10%" param="{name:'list_img',render:'Render.customImg'}">商品图片</th>
                        <th width="10%" param="{name:'goods_name'}">商品名称</th>
                        <th width="10%" param="{name:'goods_price'}">商品价格</th>
                        <th width="10%" param="{name:'goods_sale_price'}">活动价格</th>
                        <th width="10%" param="{name:'list_img',render:'Render.customBtn'}">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
        <%--优惠券领取--%>
        <div class="layui-tab-item" class="goodsNone">
            <form class="layui-form " action="">
                <div class="layui-form-item">
                    <label class="layui-form-label">优惠券名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="coupon_name_vague" placeholder="请输入优惠券名称" class="layui-input">
                    </div>
                    <div class="layui-input-inline">
                        <button class="layui-btn search-btn" table-id="coupon" lay-submit="" lay-filter="search"><i class="fa fa-search">&nbsp;</i>查询
                        </button>
                        <button type="reset" class="layui-btn layui-btn-primary"><i class="fa fa-refresh">&nbsp;</i>重置</button>
                    </div>
                </div>
            </form>
            <div class="layui-form ">
                <table class="layui-table" id="coupon" cyType="pageGrid"
                       cyProps="url:'/getCouponList',checkbox:'false',pageColor:'#2991d9',pageSize:'5'">
                    <thead>
                    <tr>
                        <th width="10%" param="{name:'coupon_name'}">优惠券名称</th>
                        <th width="10%" param="{name:'last_time'}">过期时间</th>
                        <th width="10%" param="{name:'coupon_name',render:'Render.customBtn'}">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
</div>
<script>
    layui.use('element', function () {
    });

</script>
</body>
</html>
<script>

    //数据回填
    function parentDataAdd(index){
        var viewUrlType = $(".layui-this").attr("viewUrlType");
        var parentId=$("#parentId").val();
        //数据回填
        var jsonStr = $("#"+index).val();
        var jsonObj = JSON.parse(jsonStr);
        var resetJsonObj = {} ;
        if(viewUrlType == "1"){//自定义页
            resetJsonObj.view_url = jsonObj.pageUnique;
            resetJsonObj.view_url_type = viewUrlType;
        }else if (viewUrlType == "2"){//普通商品
            resetJsonObj.view_title = jsonObj.goods_name;
            resetJsonObj.view_img = jsonObj.list_img;
            resetJsonObj.view_price = jsonObj.lower_price;
            resetJsonObj.view_sale_price = jsonObj.lower_price;
            resetJsonObj.view_url = jsonObj.goods_id;
            resetJsonObj.view_url_type = "2";
        }else if (viewUrlType == "3"){//营销活动商品
            resetJsonObj.view_title = jsonObj.goods_name;
            resetJsonObj.view_img = jsonObj.list_img;
            resetJsonObj.view_price = jsonObj.goods_price;
            resetJsonObj.view_sale_price = jsonObj.goods_sale_price;
            resetJsonObj.view_url = jsonObj.goods_id;
            resetJsonObj.view_url_type = "2";
        }else if (viewUrlType == "4"){//
            resetJsonObj.view_url = jsonObj.coupon_id;
            resetJsonObj.view_url_type = "3";
        }
        jsonStr = JSON.stringify(resetJsonObj);
        window.parent.dataAdd(parentId,jsonStr,viewUrlType);
        var moduleType =  $("#moduleType").val();
        if(moduleType != "goodsList"){
            //关闭窗口
            closeWindow();
        }
    }
</script>