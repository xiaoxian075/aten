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
        //设置每页显示条数
        defaultParam.limit=pageProps.pageSize||10;
        //从后台获取数据
        var R = PageGrid.getData(pageProps.url);
        //渲染数据,设置分页
        PageGrid.setPage(R, $grid,pageProps.url);
        //设置查询表单
        PageGrid.searchForm();
    };
    /*分页默认参数*/
    var defaultParam = {
        search: false,
        nd: new Date().getTime(),
        limit: 10,
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
                        data = {page:null};
                        alert(R.msg);
                    }
                }
            });
            return data;
        },
        /**通过表码获取数据 by chenyi 2017/7/5*/
        getDataByCode: function (codeName) {
            var data;
            $.ajax({
                url: $s.getDataByCode,
                async: false,
                data: {codeName: codeName},
                type: 'post',
                dataType: "json",
                success: function (R) {
                    if (R.code == 0) {
                        data = R;
                    } else {
                        data = {};
                        alert(R.msg);
                    }
                }
            });
            return data;
        },
        /**获取数据 by chenyi 2017/7/19*/
        getDataByEnum: function (enumName) {
            var data;
            $.ajax({
                url: $s.getDataByEnum,
                async: false,
                type: 'post',
                data: {enumName: enumName},
                dataType: "json",
                success: function (R) {
                    if (R.code == 0) {
                        data = R;
                    } else {
                        data = {};
                        alert(R.msg);
                    }
                }
            });
            return data;
        },
        /**设置分页 by chenyi 2017/6/21*/
        setPage: function (R, $grid,url) {
            debugger;
            var pageId = $grid.attr("id") + "_page";
            $("#" + pageId).remove();
            //创建分页div
            $grid.after('<div id="' + pageId + '"></div>');
            layui.use(['laypage', 'layer'], function () {
                var laypage = layui.laypage;
                laypage({
                    cont: pageId
                    ,totalCount:R.page?R.page.totalCount:0
                    , pages: R.page?R.page.totalPage : 0
                    ,pageSize:defaultParam.limit
                    , skip: true
                    , skin: pageProps.pageColor
                    , jump: function (obj) {
                        debugger;
                        //跳到分页页面
                        PageGrid.toPage(obj, $grid,url);
                    }
                });
            });

        },
        /**渲染表格数据 by chenyi 2017/6/21*/
        renderData: function (R, $grid, pageProps) {
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
            var data = R.page?R.page.list:[];

            var _th_length = _grid.find("thead th:visible ").length||0;
            if(data.length==0){
                _grid.find("tbody").append('<tr><td style="text-align: center" colspan="'+_th_length+'">暂无数据</td></tr>')
            }
            var primary = "";
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
                    var enumName = params.enumName;
                    var codeName = params.codeName;
                    var hide = params.hide || "false";
                    //该列是否是主键
                    var isPrimary = params.isPrimary || "false";
                    for (var key in data[i]) {
                        if (key == _name) {
                            //如果是枚举
                            if (enumName != null && enumName != undefined && enumName != "") {
                                var enumValues = PageGrid.getDataByEnum(enumName).data||"";
                                for(var _enum in enumValues){
                                    if(enumValues[_enum].code==data[i][key]){
                                        data[i][key]=enumValues[_enum].value
                                    }
                                }
                            }
                            //如果是表码
                            if (codeName != null && codeName != undefined && codeName != "") {
                                var codeValues = PageGrid.getDataByCode(codeName).data||"";
                                for(var _code in codeValues){
                                    if(codeValues[_code].code==data[i][key]){
                                        data[i][key]=codeValues[_code].value
                                    }
                                }
                            }
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
                    //添加全选复选框
                    $(_tr).prepend(' <td><input type="checkbox" primary=' + primary + ' lay-skin="primary"></td>');
                }
                PageGrid.renderCheckbox();
            }

        },
        /**跳到分页页面 by chenyi 2017/6/22*/
        toPage: function (obj, $grid,url) {
            debugger;
            defaultParam.page = obj.curr;
            //获取数据
            var R = PageGrid.getData(url);
            // //渲染表格数据
            PageGrid.renderData(R, $grid, pageProps);
        },
        /**查询表格 by chenyi 2017/6/23*/
        searchForm: function () {
            debugger;
            layui.use(['form'], function () {
                var form = layui.form();
                //监听提交
                form.on('submit(search)', function (data) {
                    debugger;
                    //获取对应的表格对象
                    var table_id = $(this).attr("table-id");
                    var _table = $("#" + table_id);
                    //获取表格参数
                    var props = _table.attr("cyProps");
                    if (!props) {
                        return
                    }
                    props = props ? props : "";
                    //将表格参数转为json
                    props = eval("({" + props + "})");
                    var conditions = data.field;
                    $.extend(defaultParam, conditions);
                    var R = PageGrid.getData(props.url);
                    PageGrid.setPage(R, _table,props.url);
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
        }
    }

})(jQuery);
$(document).ready(function () {
    //表格渲染查询
    var tables = $("[cyType='pageGrid']");
    for (var i = 0; i < tables.length; i++) {
        $(tables[i]).pageGrid();
    }

});