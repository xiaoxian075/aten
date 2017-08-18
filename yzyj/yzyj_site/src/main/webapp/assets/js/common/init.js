/**
 * Created by luocj on 2015/12/9.
 */
$(function () {
    $.init.initChosen();
    //$('.datepicker').datepicker({autoclose: true, language: 'cn'}).next().on(ace.click_event, function () {
    //    $(this).prev().focus();
    //});
    $('.endDate').val(new Date().format("yyyy-MM-dd hh:ii"));
    var date = new Date();
    date.setDate(date.getDate() - 7);
    $('.startDate').val(date.format("yyyy-MM-dd hh:ii"));
    $("input[name^='endDate']").on('click', function () {
        var row = $(this).closest(".row");
        //查找关联的startDate;
        var startDatePicker = row.find("input[name^='startDate']");
        var startDate = startDatePicker.val();
    })
    $("input[name^='startDate']").on('click', function () {
        var row = $(this).closest(".row");
        //查找关联的endDate;
        var endDatePicker = row.find("input[name^='endDate']");
        var endDate = endDatePicker.val();
    })
    $(".nav-tabs li:first a").click();//默认显示第一个tab
    //更多查询滑动
    $('.more-querybt').click(function () {
        $('.more-query').slideToggle("100")
    });
});
var template;
var starTemplate
$.init = {
    initDataTables: function (tableId, ajax, columns, columnDefs, initfunction, dom) {
        var options = {
            processing: true,//启用“查询中”字样
            searching: false,//禁用自带的搜索
            ordering: false,//禁用排序
            serverSide: true,//开启服务端获取数据
            ajax: ajax,
            columns: columns,
            columnDefs: columnDefs,
            language: {
                processing: "查询中...",
                lengthMenu: "_MENU_ 条/页",
                zeroRecords: "没有找到记录",
                info: "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )，共 _TOTAL_ 条记录",
                infoEmpty: "无记录",
                infoFiltered: "(从 _MAX_ 条记录过滤)",
                paginate: {
                    previous: "上一页",
                    next: "下一页"
                }
            },
            dom: 't<"row"<"col-sm-4"i><"col-sm-2"l><"col-sm-6"p>>',
            initComplete: initfunction
        }
        if (null != dom) {
            options['dom'] = dom;
        }
        table = $('#' + tableId).DataTable(options);
        return table;
    },
    initTemplate: function (tplId) {
        var tpl = $("#" + tplId).html();
        //预编译模板
        template = Handlebars.compile(tpl);
    },
    initstarTemplate: function (tplId) {
        var tpl = $("#" + tplId).html();
        //预编译模板
        starTemplate = Handlebars.compile(tpl);
    },
    initChosen: function () {
        $(".chosen-select").chosen({
                width: '100%',
                search_contains: true,//开启模糊查询
                no_results_text: '找不到...'
            }
        );
    }

},
    $.checkAuth = {
        hasPermission: function (id, permissions) {
            if (-1 != $.inArray(id, permissions)) {
                return true;
            }
            return false;
        }
    }
    ,
        $.dynamicInfo = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "title"},
                {"data": "type"},
                {"data": "px"},
                {"data": "createTime"},
                {"data": null},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 1,
                    render: function (data, type, row) {
                        switch(data){
                            case 10:
                                return "集团动态";
                                break;
                            case 11:
                                return "公司动态";
                                break;
                            default:
                                return "";
                        }
                    }
                },
                {
                    targets: 3,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 4,
                    render: function (data, type, row) {
                        return basePath+"product.html?id="+row.id;
                    }
                },
                {
                    targets: 5,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.id + "\','5')",
                            "type": "success"
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(\'" + row.id + "\','5')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }

            ]
            var ajax = {
                url: "getList",
                data: function (d) {
                    d.type = $("#type").val();
                    d.title = $("#title").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

        },

    $.attractMerchantsYS = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "title"},
                {"data": "note"},
                {"data": "px"},
                {"data": "createTime"},
                {"data": null}
            ];
            var columnDefs = [

                {
                    targets: 3,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 4,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.id + "\','5')",
                            "type": "success"
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(\'" + row.id + "\','5')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }

            ]
            var ajax = {
                url: "getList",
                data: function (d) {
                    d.title = $("#title").val();
                    d.note = $("#note").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.advertinfo = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "name"},
                {"data": "type"},
                {"data": "url"},
                {"data": "advertisingName"},
                {"data": "status"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 1,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "首页广告位"
                                break;
                            default:
                                return "";
                        }
                    }

                },
                {
                    targets: 4,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "可用"
                                break;
                            case 0:
                                return "禁用"
                                break;
                        }
                    }

                },
                {
                    targets: 5,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.id + "\','5')",
                            "type": "purple"
                        });
                        var text, type, state;
                        if (row.status == '1') {
                            text = "禁用";
                            type = "danger"
                            state = "0";
                        } else {
                            text = "启用";
                            type = "success"
                            state = "1";
                        }
                        context.func.push({
                            "name": text,
                            "fn": "editAdvert(\'" + row.id + "\',\'" + state + "\')",
                            "type": type
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(\'" + row.id + "\','5')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }

                }
            ]
            var ajax = {
                url: "getList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.username = $("input[name='username']").val();
                    d.nickname = $("input[name='nickname']").val();
//                    d.lx = $("select[name='lx']").val();
//                    d.yhxm = $("input[name='yhxm']").val();
//                    d.sfyd = $("select[name='sfyd']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.applyMerchants = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "company"},
                {"data": "category"},
                {"data": "contactWay"},
                {"data": "mobile"},
                {"data": "qq"},
                {"data": "email"},
                {"data": "createTime"},
                {"data": "note"},
                {"data": null}
            ];
            var columnDefs = [

                {
                    targets: 6,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 8,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.id + "\','5')",
                            "type": "success"
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(\'" + row.id + "\','5')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }

            ]
            var ajax = {
                url: "getList",
                data: function (d) {
                    d.company = $("#company").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.attractMerchantsHZ = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "title"},
                {"data": "note"},
                {"data": "px"},
                {"data": "createTime"},
                {"data": null}
            ];
            var columnDefs = [

                {
                    targets: 3,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 4,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.id + "\','5')",
                            "type": "success"
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(\'" + row.id + "\','5')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }

            ]
            var ajax = {
                url: "getList",
                data: function (d) {
                    d.title = $("#title").val();
                    d.note = $("#note").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
        $.dicttable = {
            initTables: function (tableId, permissions) {
                var columns = [
                    {"data": "dictTableBh"},
                    {"data": "dictTableMc"},
                    {"data": "dictBh"},
                    {"data": "dictMc"},
                    {"data": null}
                ];
                var columnDefs = [
                    {


                    },

                    {
                        targets: 4,
                        render: function (data, type, row) {
                            var context = {func: []};
                            context.func.push({
                                "name": "修改",
                                "fn": "edit(\'" + row.sysId + "\','5')",
                                "type": "success"
                            });
                            context.func.push({
                                "name": "删除",
                                "fn": "del(\'" + row.sysId + "\','5')",
                                "type": "danger"
                            });
                            var html = template(context);
                            return html;
                        }
                    }

                ]
                var ajax = {
                    url: "getList",
                    data: function (d) {
                        d.dictTableMc = $("#dictTableMc").val();
                        d.dictMc = $("#dictMc").val();
                    }
                }
                var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


                $("#message-query").on("click", function () {
                    table.ajax.reload();
                })
                return table;
            }

        },
    $.userinfo = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "username"},
                {"data": "nickname"},
                {"data": "roleName"},
                {"data": "status"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 3,
                    render: function (data, type, row) {
                        switch (data) {
                            case '1':
                                return "可用"
                                break;
                            case '0':
                                return "禁用"
                                break;
                        }
                    }
                },
                {
                    targets: 4,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "初始化密码",
                            "fn": "updatePass(\'" + row.id + "\','5')",
                            "type": "purple"
                        });
                        context.func.push({
                            "name": "角色分配",
                            "fn": "editRole(\'" + row.id + "\','5')",
                            "type": "primary"
                        });
                        var text, type, state;
                        if (row.status == '1') {
                            text = "禁用";
                            type = "danger"
                            state = "0";
                        } else {
                            text = "启用";
                            type = "success"
                            state = "1";
                        }
                        context.func.push({
                            "name": text,
                            "fn": "editSatuts(\'" + row.id + "\',\'" + state + "\')",
                            "type": type
                        });
                        var html = template(context);
                        return html;
                    }

                }
            ]
            var ajax = {
                url: "getList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.username = $("input[name='username']").val();
                    d.nickname = $("input[name='nickname']").val();
//                    d.lx = $("select[name='lx']").val();
//                    d.yhxm = $("input[name='yhxm']").val();
//                    d.sfyd = $("select[name='sfyd']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.roleinfo = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "roleName"},
                {"data": "enabled"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 1,
                    render: function (data, type, row) {
                        switch (data) {
                            case '1':
                                return "可用"
                                break;
                            case '0':
                                return "禁用"
                                break;
                        }
                    }

                }, {
                    targets: 2,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "update(\'" + row.id + "\','5')",
                            "type": "purple"
                        });
                        var text, type, state;
                        if (row.enabled == '1') {
                            text = "禁用";
                            type = "danger"
                            state = "0";
                        } else {
                            text = "启用";
                            type = "success"
                            state = "1";
                        }
                        context.func.push({
                            "name": text,
                            "fn": "editSatuts(\'" + row.id + "\',\'" + state + "\')",
                            "type": type
                        });
                        var html = template(context);
                        return html;
                    }

                }
            ]
            var ajax = {
                url: "getList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.username = $("input[name='username']").val();
                    d.nickname = $("input[name='nickname']").val();
//                    d.lx = $("select[name='lx']").val();
//                    d.yhxm = $("input[name='yhxm']").val();
//                    d.sfyd = $("select[name='sfyd']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.propagandainfo = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "type"},
                {"data": "title"},
                {"data": "createTime"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 2,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }

                },
                {
                    targets: 0,
                    render: function (data, type, row) {
                        switch (data) {
                            case 2:
                                return "重大活动"
                                break;
                            case 1:
                                return "新闻媒体"
                                break;
                            case 0:
                                return "公司文化"
                                break;
                        }
                    }

                }, {
                    targets: 3,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "update(\'" + row.id + "\','5')",
                            "type": "purple"
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(\'" + row.id + "\','5')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }

                }
            ]
            var ajax = {
                url: "getList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.title = $("input[name='title']").val();
//                    d.lx = $("select[name='lx']").val();
//                    d.yhxm = $("input[name='yhxm']").val();
//                    d.sfyd = $("select[name='sfyd']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.recruitmentinfo = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "departmentName"},
                {"data": "position"},
                {"data": "createTime"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 2,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }

                },
                {
                    targets: 3,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "update(\'" + row.id + "\','5')",
                            "type": "purple"
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(\'" + row.id + "\','5')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }

                }
            ]
            var ajax = {
                url: "getList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.position = $("input[name='position']").val();
//                    d.lx = $("select[name='lx']").val();
//                    d.yhxm = $("input[name='yhxm']").val();
//                    d.sfyd = $("select[name='sfyd']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.navigationinfo = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "name"},
                {"data": "url"},
                {"data": "px"},
                {"data": "status"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 3,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "可用"
                                break;
                            case 0:
                                return "禁用"
                                break;
                        }
                    }

                },
                {
                    targets: 4,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.id + "\','5')",
                            "type": "purple"
                        });
                        var text, type, state;
                        if (row.status == '1') {
                            text = "禁用";
                            type = "danger"
                            state = "0";
                        } else {
                            text = "启用";
                            type = "success"
                            state = "1";
                        }
                        context.func.push({
                            "name": text,
                            "fn": "editAdvert(\'" + row.id + "\',\'" + state + "\')",
                            "type": type
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(\'" + row.id + "\','5')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }

                }
            ]
            var ajax = {
                url: "getList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
//                    d.lx = $("select[name='lx']").val();
//                    d.yhxm = $("input[name='yhxm']").val();
//                    d.sfyd = $("select[name='sfyd']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    } ,
    $.planInfo = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "title"},
                {"data": "px"},
                {"data": "createTime"},
                {"data": null}
            ];
            var columnDefs = [

                {
                    targets: 2,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },

                {
                    targets: 3,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.id + "\','5')",
                            "type": "success"
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(\'" + row.id + "\','5')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }

            ]
            var ajax = {
                url: "getList",
                data: function (d) {
                    //d.type = $("#type").val();
                    //d.title = $("#title").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.serviceInfo = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "title"},
                {"data": "type"},
                {"data": "px"},
                {"data": "createTime"},
                {"data": null}
            ];
            var columnDefs = [

                {
                    targets: 1,
                    render: function (data, type, row) {
                        switch(data){
                            case 1:
                                return "服务项目";
                                break;
                            case 2:
                                return "服务范围";
                                break;
                            case 3:
                                return "定制需求";
                                break;
                            default:
                                return "";
                        }
                    }
                },
                {
                    targets: 3,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },

                {
                    targets: 4,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.id + "\','5')",
                            "type": "success"
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(\'" + row.id + "\','5')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }

            ]
            var ajax = {
                url: "getList",
                data: function (d) {
                    //d.type = $("#type").val();
                    //d.title = $("#title").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    }
    function getRootPath() {
    // 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    // 获取主机地址之后的目录，如： uimcardprj/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    // 获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    // 获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName
        .substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht + projectName);
}