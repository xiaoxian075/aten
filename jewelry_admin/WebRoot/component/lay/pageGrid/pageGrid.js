/**
 * Created by 陈熠 on 2017/6/21
 * email   :  228112142@qq.com
 */
(function ($) {
    /* 入口函数 */
    $.fn.pageGrid = function () {
        //当前表格对象
        var $grid = this;
        //获取表格参数
        pageProps = $grid.attr("cyProps");
        if (!pageProps) {
            return
        }
        pageProps = pageProps ? pageProps : "";
        //将表格参数转为json
        pageProps = eval("({" + pageProps + "})");
        //对应表格的已选中的数据
        checked_name = pageProps.checked_name;
        //从后台获取数据
        var R = PageGrid.getData(pageProps.url);
        //渲染数据,设置分页
        PageGrid.setPage(R, $grid);
        //设置查询表单
        PageGrid.searchForm();
    };
    /*分页默认参数*/
    var nameHead = "";
    var defaultParam = {
        search: false,
        nd: new Date().getTime(),
        limit: 5,
        page: 1,
        sidx: '',
        order: 'desc',
        _: new Date().getTime()
    };
    /*默认配置*/
    var pageProps = {};
    /*方法对象*/
    var PageGrid = {
        /**获取数据 by chenyi 2017/6/21*/
        getData: function (url) {
            var data;
            $.ajax({
                //url: "/sys/menu/list",
                url: url,
                async: false,
                data: defaultParam,
                type: 'post',
                dataType: "json",
                success: function (R) {
                    if (R.code == 0) {
                        data = R;
                    } else {
                        data = {};
                    }
                }, error: function () {
                    alert("系统繁忙");
                }
            });
            return data;
        },
        /**设置分页 by chenyi 2017/6/21*/
        setPage: function (R, $grid) {
            if (!R) {
                return
            }
            ;
            var pageId = $grid.attr("id") + "_page";
            $("#" + pageId).remove();
            //创建分页div
            $grid.after('<div id="' + pageId + '"></div>');
            layui.use(['laypage', 'layer'], function () {
                var laypage = layui.laypage
                    , layer = layui.layer;
                laypage({
                    cont: pageId
                    , pages: R.page.totalPage || 0
                    , skip: true
                    , skin: pageProps.pageColor
                    , jump: function (obj) {
                        //跳到分页页面
                        PageGrid.toPage(obj, $grid);
                    }
                });
            });

        },
        /**渲染表格数据 by chenyi 2017/6/21*/
        renderData: function (R, $grid, pageProps) {
            if (!R) {
                return
            }
            ;
            //获取表格参数中的name
            var _grid = $grid;
            //获取所有th
            var _th = _grid.find("thead th");
            _grid.find("tbody").remove();
            //创建tbody
            _grid.append("<tbody><tbody/>");
            //判断是否有隐藏的列
            for (var i = 0; i < _th.length; i++) {
                var isHide = eval("(" + $(_th[i]).attr("param") + ")").hide || "false";
                if (isHide == "true") {
                    $(_th[i]).hide();
                }
            }

            //删除多余的tbody
            if (_grid.find("tbody").length > 1) {
                for (var i = 1; i < _grid.find("tbody").length; i++) {
                    $(_grid.find("tbody")[i]).remove();
                }

            }
            //获取将要渲染的数据
            var data = R.page.list;
            var primary = "";
            var show_name = "";
            var price =0;
            var fixed_price =0;
            for (var i = 0; i < data.length; i++) {
                //为数据创建tr
                $(_grid.find("tbody")).append("<tr></tr>");
                //获取新建的tr
                var _tr = _grid.find("tbody tr")[_grid.find("tbody tr").length - 1];
                //循环所有th  获取param中的name
                for (var j = 0; j < _th.length; j++) {
                    //获取当前th的所有参数
                    var th_param = $(_th[j]).attr("param");
                    var params = eval("(" + th_param + ")");
                    //获取param属性中的name
                    var _name = params.name || "";
                    var render = params.render;
                    var hide = params.hide || "false";
                    var isShowName = params.isShowName || "false";
                    var isPrice = params.isPrice || "false";
                    var isPixedPrice = params.isPixedPrice || "false";
                    //该列是否是主键
                    var isPrimary = params.isPrimary || "false";
                    for (var key in data[i]) {
                        if (key == _name) {
                            //如果有渲染的方法 先进去渲染方法
                            if (render != null && render != undefined && render != "") {
                                var func = eval((render));
                                data[i][key] = func(data[i], i, data[i][key]);
                            }
                            var value = data[i][key] || "";
                            //如果是主键并且主键不为空，设置主键值
                            if (isPrimary == "true") {
                                primary = value;
                            }
                            //如果是要显示的值
                            if (isShowName == "true") {
                                show_name = value;
                            }
                            //如果是商品价格
                            if (isPixedPrice == "true") {
                                fixed_price = value;
                            }
                            //如果是商品价格(分)
                            if (isPrice == "true") {
                                price = value;
                            }
                            if (hide == "true") {
                                $(_tr).append('<td style="display: none" name=' + _name + '>' + value + '</td>');
                            } else {
                                $(_tr).append('<td name=' + _name + '>' + value + '</td>');
                            }
                        }
                    }

                }
                //判断是否有复选框
                var isCheckbox = pageProps.checkbox || "true";
                //如果需要复选框
                if (isCheckbox == "true") {
                    //如果折扣金额大于原价  不可选择
                    var discount=$(_grid).attr("discount");
                    if(discount!=""&&discount*100>fixed_price){
                        $(_tr).prepend(' <th><input type="checkbox" disabled lay-filter="filter" fixed_price="'+fixed_price+'" price=' + price + '   show_name=' + show_name + ' primary=' + primary + ' lay-skin="primary"></th>');
                    }else{
                        $(_tr).prepend(' <th><input type="checkbox"  lay-filter="filter" fixed_price="'+fixed_price+'" price=' + price + '   show_name=' + show_name + ' primary=' + primary + ' lay-skin="primary"></th>');
                    }


                }
                PageGrid.renderCheckbox();
                PageGrid.isChecked();
            }

        },
        /**跳到分页页面 by chenyi 2017/6/22*/
        toPage: function (obj, $grid) {
            defaultParam.page = obj.curr;
            //获取数据
            var R = PageGrid.getData(pageProps.url);
            // //渲染表格数据
            PageGrid.renderData(R, $grid, pageProps);
        },
        /**查询表格 by chenyi 2017/6/23*/
        searchForm: function () {
            layui.use(['form'], function () {
                var form = layui.form();
                //监听提交
                form.on('submit(search)', function (data) {
                    //获取对应的表格对象
                    var table_id = $(this).attr("table-id");
                    var _table = $("#" + table_id);
                    var conditions = data.field;
                    $.extend(defaultParam, conditions);
                    var R = PageGrid.getData(pageProps.url);
                    PageGrid.setPage(R, _table);
                    return false;
                });


            });
        },
        renderCheckbox: function () {
            layui.use('form', function () {
                var $ = layui.jquery, form = layui.form();
                form.render('checkbox');
                //全选
                form.on('checkbox(allChoose)', function (data) {
                    var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
                    child.each(function (index, item) {
                        item.checked = data.elem.checked;
                    });
                    form.render('checkbox');
                });

            });
        },
        isChecked: function () {
            debugger;
            var hasCheckeds = $(parent.document).find("." + checked_name);
            var checkedIds = new Array();
            for (var i = 0; i < hasCheckeds.length; i++) {
                checkedIds.push($(hasCheckeds[i]).attr("checked_id"));
            }
            //获取所有的复选框
            var checkboxs = $("[type='checkbox']");
            for (var i = 0; i < checkboxs.length; i++) {
                var primaryId = $(checkboxs[i]).attr("primary");
                // //判断是否已选中
                if ($.inArray(primaryId, checkedIds) != -1) {
                    //如果需要勾选
                    $(checkboxs[i]).attr("checked", "");
                }
                for (var j = 0; j < selects.length; j++) {
                    if (primaryId==selects[j].id) {
                        //如果需要勾选
                        $(checkboxs[i]).attr("checked", "");
                    }
                }

            }
            layui.use('form', function () {
                var form = layui.form();
                form.on('checkbox(filter)', function (data) {
                    //黄金特惠金额不能大于原价
                    // layer.alert("该商品原价低于折扣金额，不可选择!");
                    // return;
                    //获取当前选中的id
                    var checked_id = $(data.elem).attr("primary");
                    var checked_name = $(data.elem).attr("show_name");
                    var checked_price = $(data.elem).attr("price");
                    var checked_fixed_price = $(data.elem).attr("fixed_price");
                    var state = data.elem.checked;//是否被选中，true或者false
                    if (state == true) {
                        var select = new Array();
                        select.id = checked_id;
                        select.name = checked_name;
                        select.option_type = 1;
                        select.is_index = 0;
                        select.is_must = 0;
                        select.is_custom_value = 0;
                        select.manual_fee = 0;
                        select.price = checked_price;
                        select.fixed_price = checked_fixed_price;
                        select.option_type_name = "单选";
                        select.is_index_name = '<span style="color:red">否</span>';
                        select.is_must_name = '<span style="color:red">否</span>';
                        select.is_custom_value_name = '<span style="color:red">否</span>';
                        selects.push(select);
                    } else {
                        //删除
                        for (var i = 0; i < selects.length; i++) {
                            if (selects[i].id == checked_id) {
                                selects.splice(i, 1);
                            }
                        }
                    }
                    // console.log(data.elem); //得到checkbox原始DOM对象
                    // console.log(data.elem.checked); //是否被选中，true或者false
                    // console.log(data.value); //复选框value值，也可以通过data.elem.value得到
                    // console.log(data.othis); //得到美化后的DOM对象
                });
                form.render('checkbox');

            });
        }
    }

})(jQuery);
$(document).ready(function () {
    //表格渲染查询
    $("[cyType='pageGrid']").pageGrid();
    // $("#userTable").pageGrid();

});