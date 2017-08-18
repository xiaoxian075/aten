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
function getRootPath() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0, pos);
    var projectName = pathName
        .substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht + projectName);
}
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
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.Quota = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "quotaGroup"},
                {"data": "cxkOne"},
                {"data": "cxkDay"},
                {"data": "cxkMonth"},
                {"data": "xykOne"},
                {"data": "xykDay"},
                {"data": "xykMonth"},
                {"data": "xykExcessOne"},
                {"data": "xykExcessDay"},
                {"data": "xykExcessMonth"},
                {"data": "cxkExcessOne"},
                {"data": "cxkExcessDay"},
                {"data": "cxkExcessMonth"},
                {"data": "note"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 2,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 3,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 4,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 5,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 6,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 7,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 8,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 9,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 10,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 11,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 12,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 13,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 15,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.id + "\')",
                            "type": "success"
                        });
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getList",
                data: function (d) {
                    //添加额外的参数传给服务器
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
                        context.func.push({
                            "name": "初始化",
                            "fn": "oneInit(\'" + row.id + "\','5')",
                            "type": "primary"
                        });
                        var html = template(context);
                        return html;
                    }
                }

            ]
            var ajax = {
                url: "getList",
                data: function (d) {
                    d.queryBody = $("#queryBody").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.deviceList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "realName"},
                {"data": "deviceCode"},
                {"data": "activationCode"},
                {"data": "lklMachineCode"},
                {"data": "lklActivationCode"},
                {"data": "lklMerchantCode"},
                {"data": "lklTerminalCode"},
                {"data": "deviceName"},
                {"data": "deviceType"},
                {"data": "merchantName"},
                {"data": "merchantYunPayAccount"},
                {"data": "merchantPhone"},
                {"data": "createTime"},
                {"data": "state"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets:8,
                    render: function (data, type, row) {
                        switch (data) {
                            case 10:
                                return "POS机"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets:12,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 13,
                    render: function (data, type, row) {
                        switch (data) {
                            case "1":
                                return "启用"
                                break;
                            case "0":
                                return "禁用"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 14,
                    render: function (data, type, row) {
                        var context = {func: []};
                        if (row.approvalStatus == 1 || row.approvalStatus == 2 ||  row.approvalStatus == 4) {
                            context.func.push({
                                "name": "等待审批",
                                "type": "danger"
                            });
                        }else if(row.approvalStatus == 3){
                            context.func.push({
                                "name": "修改",
                                "fn": "edit(" + row.id + ",'5')",
                                "type": "success"
                            });
                            var text, type, state;
                            if (row.state == "1") {
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
                            context.func.push({
                                "name": "删除",
                                "fn": "del(\'" + row.deviceUnique + "\',\'" + state + "\')",
                                "type": "danger"
                            });
                        }
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
                    d.queryBody = $("#queryBody").val();
                    d.agentUnique = $("#agentUnique").val();
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
    $.businessList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "merchantNo"},
                {"data": "merchantName"},
                {"data": "merchantLegalPerson"},
                {"data": "businessLicense"},
                {"data": "merchantPersonName"},
                {"data": "accountNumber"},
                {"data": "accountName"},
                {"data": "yunPayAccount"},
                {"data": "createTime"},
                {"data": "agencyName"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets:8,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return data;
                        }
                    }
                },
                {
                    targets: 10,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "详情",
                            "fn": "edit(\'" + row.id + "\','5')",
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
                    d.merchantNo = $("input[name='merchantNo']").val();
                    d.merchantName = $("input[name='merchantName']").val();
                    d.yunPayAccount = $("input[name='yunPayAccount']").val();
                    d.sdate = $("input[name='sdate']").val();
                    d.edate = $("input[name='edate']").val();
                    d.agencyName = $("input[name='agencyName']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.businessInfo = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "merchantNo"},
                {"data": "merchantName"},
                {"data": "merchantLegalPerson"},
                {"data": "businessLicense"},
                {"data": "merchantPersonName"},
                {"data": "accountNumber"},
                {"data": "accountName"},
                {"data": "yunPayAccount"},
                {"data": "createTime"},
                {"data": "agencyName"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets:8,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return data;
                        }
                    }
                },
                {
                    targets: 10,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.id + "\','5')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getBusinessInfoByYunId",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.yunPayAccount = $("input[name='yunPayAccount']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.deviceApproveList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "realName"},
                {"data": "deviceCode"},
                {"data": "activationCode"},
                {"data": "lklMachineCode"},
                {"data": "lklActivationCode"},
                {"data": "lklMerchantCode"},
                {"data": "lklTerminalCode"},
                {"data": "deviceName"},
                {"data": "deviceType"},
                {"data": "merchantName"},
                {"data": "merchantYunPayAccount"},
                {"data": "merchantPhone"},
                {"data": "createTime"},
                {"data": "approvalStatus"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 8,
                    render: function (data, type, row) {
                        switch (data) {
                            case 10:
                                return "POS机"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 12,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 13,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "待审批"
                                break;
                            case 2:
                                return "审批中"
                                break;
                            case 3:
                                return "审批通过"
                                break;
                            case 4:
                                return "审批驳回"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 14,
                    render: function (data, type, row) {
                        var context = {func: []};
                        if (row.approvalStatus == 1 || row.approvalStatus == 4) {
                            context.func.push({
                                "name": "修改",
                                "fn": "edit(\'" + row.id + "\','5')",
                                "type": "danger"
                            });
                            context.func.push({
                                "name": "提交审批",
                                "fn": "reApproval(\'" + row.id + "\',\'" + row.approvalStatus + "\')",
                                "type": "success"
                            });
                        }
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getApproveList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.deviceCode = $("input[name='deviceCode']").val();
                    d.lklMachineCode = $("input[name='lklMachineCode']").val();
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                    d.lklTerminalCode = $("input[name='lklTerminalCode']").val();
                    d.merchantYunPayAccount = $("input[name='merchantYunPayAccount']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.deviceApproveListLead = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "realName"},
                {"data": "deviceCode"},
                {"data": "activationCode"},
                {"data": "lklMachineCode"},
                {"data": "lklActivationCode"},
                {"data": "lklMerchantCode"},
                {"data": "lklTerminalCode"},
                {"data": "deviceName"},
                {"data": "deviceType"},
                {"data": "merchantName"},
                {"data": "merchantYunPayAccount"},
                {"data": "merchantPhone"},
                {"data": "createTime"},
                {"data": "approvalStatus"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 8,
                    render: function (data, type, row) {
                        switch (data) {
                            case 10:
                                return "POS机"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 12,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 13,
                    render: function (data, type, row) {
                        switch (data) {
                            case 2:
                                return "待审批"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 14,
                    render: function (data, type, row) {
                        var context = {func: []};
                            context.func.push({
                                "name": "通过",
                                "fn": "update(\'" + row.id + "\','3')",
                                "type": "success"
                            });
                            context.func.push({
                                "name": "驳回",
                                "fn": "update(\'" + row.id + "\','4')",
                                "type": "danger"
                            });
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getApproveListLead",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.deviceCode = $("input[name='deviceCode']").val();
                    d.lklMachineCode = $("input[name='lklMachineCode']").val();
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                    d.lklTerminalCode = $("input[name='lklTerminalCode']").val();
                    d.merchantYunPayAccount = $("input[name='merchantYunPayAccount']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.restCodeList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "realName"},
                {"data": "deviceCode"},
                {"data": "activationCode"},
                {"data": "lklMachineCode"},
                {"data": "lklActivationCode"},
                {"data": "lklMerchantCode"},
                {"data": "lklTerminalCode"},
                {"data": "deviceName"},
                {"data": "deviceType"},
                {"data": "merchantName"},
                {"data": "merchantYunPayAccount"},
                {"data": "merchantPhone"},
                {"data": "createTime"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 8,
                    render: function (data, type, row) {
                        switch (data) {
                            case 10:
                                return "POS机"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 12,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 13,
                    render: function (data, type, row) {
                        var context = {func: []};
                            context.func.push({
                                "name": "重置",
                                "fn": "update(\'" + row.machineCode + "\',\'" + row.deviceUnique + "\')",
                                "type": "danger"
                            });
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getRestCodeList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.deviceAlLeadList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "realName"},
                {"data": "deviceCode"},
                {"data": "activationCode"},
                {"data": "lklMachineCode"},
                {"data": "lklActivationCode"},
                {"data": "lklMerchantCode"},
                {"data": "lklTerminalCode"},
                {"data": "merchantName"},
                {"data": "merchantYunPayAccount"},
                {"data": "merchantPhone"},
                {"data": "createTime"},
                {"data": "approvalStatus"},
                {"data": "state"},
                {"data": null},
            ];
            var columnDefs = [
                {
                    targets: 10,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 11,
                    render: function (data, type, row) {
                        switch (data) {
                            case 3:
                                return "审批通过"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 12,
                    render: function (data, type, row) {
                        switch (data) {
                            case "0":
                                return "禁用"
                                break;
                            case "1":
                                return "启用"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 13,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "详情",
                            "fn": "deviceDetail(\'" + row.id + "\')",
                            "type": "danger"
                        });
                        if (row.state == 1 && (row.subsidyStatus == 0 || row.subsidyStatus == 3)) {
                            context.func.push({
                                "name": "返现",
                                "fn": "submitSubsidy(\'" + row.id + "\',\'" + row.agentUnique + "\')",
                                "type": "success"
                            });
                        }
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getalLeadList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.deviceCode = $("input[name='deviceCode']").val();
                    d.lklMachineCode = $("input[name='lklMachineCode']").val();
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                    d.lklTerminalCode = $("input[name='lklTerminalCode']").val();
                    d.merchantYunPayAccount = $("input[name='merchantYunPayAccount']").val();
                    d.agentName = $("input[name='realName']").val();
                    d.state = $("select[name='state']").val();
                    d.subsidyStatus = $("select[name='subsidyStatus']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.waitSubsidyList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "realName"},
                {"data": "deviceCode"},
                {"data": "activationCode"},
                {"data": "lklMachineCode"},
                {"data": "lklActivationCode"},
                {"data": "lklMerchantCode"},
                {"data": "lklTerminalCode"},
                {"data": "merchantName"},
                {"data": "merchantYunPayAccount"},
                {"data": "merchantPhone"},
                {"data": "subsidyTime"},
                {"data": "state"},
                {"data": null},
            ];
            var columnDefs = [
                {
                    targets: 10,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 11,
                    render: function (data, type, row) {
                        switch (data) {
                            case "0":
                                return "禁用"
                                break;
                            case "1":
                                return "启用"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 12,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "详情",
                            "fn": "deviceDetail(\'" + row.id + "\')",
                            "type": "danger"
                        });
                        context.func.push({
                            "name": "通过",
                            "fn": "passSubsidy(\'" + row.id + "\',\'" + row.agentUnique + "\')",
                            "type": "success"
                        });
                        context.func.push({
                            "name": "退回",
                            "fn": "rejectSubsidy(\'" + row.id + "\',\'" + row.agentUnique + "\')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getWaitSubsidyList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                    d.lklTerminalCode = $("input[name='lklTerminalCode']").val();
                    d.merchantYunPayAccount = $("input[name='merchantYunPayAccount']").val();
                    d.realName = $("input[name='realName']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }
    },

    $.waitTyjList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "tyjNumber"},
                {"data": "lklMerchantCode"},
                {"data": "lklTerminalCode"},
                {"data": "merchantYunPayAccount"},
                {"data": "tyjTime"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 4,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 5,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "通过",
                            "fn": "passTyj(\'" + row.deviceUnique + "\',\'" + row.id + "\')",
                            "type": "success"
                        });
                        context.func.push({
                            "name": "驳回",
                            "fn": "rejectTyj(\'" + row.deviceUnique + "\',\'" + row.id + "\')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getWaitTyjList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                    d.lklTerminalCode = $("input[name='lklTerminalCode']").val();
                    d.merchantYunPayAccount = $("input[name='merchantYunPayAccount']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.deviceAlTyjList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "tyjNumber"},
                {"data": "lklMerchantCode"},
                {"data": "lklTerminalCode"},
                {"data": "merchantYunPayAccount"},
                {"data": "tyjTime"}
            ];
            var columnDefs = [
                {
                    targets: 4,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                }
            ]
            var ajax = {
                url: "getDeviceAlTyjList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                    d.lklTerminalCode = $("input[name='lklTerminalCode']").val();
                    d.merchantYunPayAccount = $("input[name='merchantYunPayAccount']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },


    $.deviceAlSubsidyList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "realName"},
                {"data": "deviceCode"},
                {"data": "activationCode"},
                {"data": "lklMachineCode"},
                {"data": "lklActivationCode"},
                {"data": "lklMerchantCode"},
                {"data": "lklTerminalCode"},
                {"data": "merchantName"},
                {"data": "merchantYunPayAccount"},
                {"data": "merchantPhone"},
                {"data": "subsidyTime"},
                {"data": "state"}
            ];
            var columnDefs = [
                {
                    targets: 10,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 11,
                    render: function (data, type, row) {
                        switch (data) {
                            case "0":
                                return "禁用"
                                break;
                            case "1":
                                return "启用"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                }
            ]
            var ajax = {
                url: "getDeviceAlSubsidyList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                    d.lklTerminalCode = $("input[name='lklTerminalCode']").val();
                    d.merchantYunPayAccount = $("input[name='merchantYunPayAccount']").val();
                    d.realName = $("input[name='realName']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.quotaGroupAlLeadList = {
        initTables: function (tableId, permissions) {
            alert(3);
            var columns = [
                {"data": "deviceCode"},
                {"data": "lklMerchantCode"},
                {"data": "lklTerminalCode"},
                {"data": "merchantName"},
                {"data": "merchantYunPayAccount"},
                {"data": "createTime"},
                {"data": "state"},
            ];
            var columnDefs = [
                {
                    targets: 5,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 6,
                    render: function (data, type, row) {
                        switch (data) {
                            case "0":
                                return "禁用"
                                break;
                            case "1":
                                return "启用"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                }
            ]
            var ajax = {
                url: "getalLeadList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.deviceCode = $("input[name='deviceCode']").val();
                    d.lklMachineCode = $("input[name='lklMachineCode']").val();
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                    d.lklTerminalCode = $("input[name='lklTerminalCode']").val();
                    d.merchantYunPayAccount = $("input[name='merchantYunPayAccount']").val();
                    d.state = $("select[name='state']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);
            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.waitRepairOrderList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "orderNumber"},
                {"data": "repairMoney"},
                {"data": "repairTime"},
                {"data": "userName"},
                {"data": "alTime"},
                {"data": "lklPayTime"},
                {"data": "alStatus"},
                {"data": null},
            ];
            var columnDefs = [
                {
                    targets: 2,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
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
                    targets: 5,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
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
                    targets: 7,
                    render: function (data, type, row) {
                        switch (data) {
                            case 0:
                                return "待审批"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 8,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "通过",
                            "fn": "pass(\'" + row.id + "\',\'" + row.orderNumber + "\')",
                            "type": "success"
                        });
                        context.func.push({
                            "name": "驳回",
                            "fn": "reject(\'" + row.id + "\',\'" + row.orderNumber + "\')",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getWaitRepairOrderList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    //d.orderNumber = $("#orderNumber").val();
                    d.orderNumber = $("input[name='orderNumber']").val();
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

    $.posWithdrawRecord = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "pid"},
                {"data": "loginName"},
                {"data": "amount"},
                {"data": "bankAccount"},
                {"data": "accountName"},
                {"data": "bankName"},
                {"data": "withdrawTime"},
                {"data": "auditTime"},
                {"data": null},
            ];
            var columnDefs = [
                {
                    targets: 2,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data).toFixed(2);
                        }
                    }
                },
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
                    targets: 7,
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
                        if (row.isSend == 0 ) {
                            context.func.push({
                                "name": "下发",
                                "fn": "isSend(\'" + row.pid + "\')",
                                "type": "success"
                            });
                            context.func.push({
                                "name": "删除",
                                "fn": "deleteSend(\'" + row.pid + "\')",
                                "type": "danger"
                            });
                        };
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getPosWithdrawList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.loginName = $("input[name='loginName']").val();
                    d.sdate = $("input[name='sdate']").val();
                    d.edate = $("input[name='edate']").val();
                    d.accountName = $("input[name='accountName']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }
    },

    $.sendPosWithdrawRecord = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "pid"},
                {"data": "yftNumber"},
                {"data": "loginName"},
                {"data": "amount"},
                {"data": "bankAccount"},
                {"data": "accountName"},
                {"data": "bankName"},
                {"data": "withdrawTime"},
                {"data": "auditTime"},
                {"data": "isSendTime"},
                {"data": "isSendMoney"},
                {"data": "orderLogNo"},
                {"data": "lklMerchantCode"},
                {"data": "lklTerminalCode"}
            ];
            var columnDefs = [
                {
                    targets: 3,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data).toFixed(2);
                        }
                    }
                },
                {
                    targets: 7,
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
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 9,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                }
            ]
            var ajax = {
                url: "getIsSendList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.yftNumber = $("input[name='yftNumber']").val();
                    d.loginName = $("input[name='loginName']").val();
                    d.sdate = $("input[name='sdate']").val();
                    d.edate = $("input[name='edate']").val();
                    d.accountName = $("input[name='accountName']").val();
                    d.orderLogNo = $("input[name='orderLogNo']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }
    },

    $.alreadyRepairOrderList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "orderNumber"},
                {"data": "repairMoney"},
                {"data": "repairTime"},
                {"data": "userName"},
                {"data": "alTime"},
                {"data": "lklPayTime"},
                {"data": "alStatus"},
            ];
            var columnDefs = [
                {
                    targets: 2,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
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
                    targets: 5,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
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
                    targets: 7,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "已通过"
                                break;
                            case 2:
                                return "被驳回"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
            ]
            var ajax = {
                url: "getAlreadyRepairOrderList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    //d.orderNumber = $("#orderNumber").val();
                    d.orderNumber = $("input[name='orderNumber']").val();
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

    $.agencyPerson = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "realName"},
                {"data": "phone"},
                {"data": "address"},
                {"data": "papersType"},
                {"data": "papersNumber"},
                {"data": "province"},
                {"data": "city"},
                {"data": "yunPayLoginName"},
                {"data": "note"},
                {"data": "joinTime"},
                {"data": "status"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 10,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd");
                        }
                    }
                },
                {
                    targets: 11,
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
                    targets: 12,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "设备",
                            "fn": "device(\'" + row.agentUnique + "\','5')",
                            "type": "success"
                        });
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.agentUnique + "\','5')",
                            "type": "success"
                        });
                        var text, type, state;
                        if (row.status == 1) {
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
                            "fn": "editSatuts(\'" + row.agentUnique + "\',\'" + state + "\')",
                            "type": type
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(\'" + row.agentUnique + "\','5')",
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
                    d.queryBody = $("input[name='queryBody']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.carouselAPP = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "imgUrl"},
                {"data": "title"},
                {"data": "type"},
                {"data": "addTime"},
                {"data": "status"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 1,
                    render: function (data, type, row) {
                        return "<img src=\'"+getRootPath()+"/picCommon/infoPic"+"/"+data+"\' style='width: 150px;height: 100px'>";
                    }
                },
                {
                    targets: 3,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "APP主页轮播图"
                                break;
                            case 2:
                                return "POS机轮播图"
                                break;
                            case 3:
                                return "售货机轮播图"
                                break;
                        }
                    }
                },
                {
                    targets: 4,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 5,
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
                    targets: 6,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(\'" + row.id + "\','5')",
                            "type": "success"
                        });
                        var text, type, state;
                        if (row.status == 1) {
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
                    d.queryBody = $("input[name='queryBody']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.agentDeviceList = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "realName"},
                {"data": "deviceName"},
                {"data": "deviceType"},
                {"data": "merchantName"},
                {"data": "lklMerchantCode"},
                {"data": "merchantYunPayAccount"},
                {"data": "merchantPhone"},
                {"data": "moneyCount"},
                {"data": "dayCount"},
                {"data": "createTime"},
                {"data": "state"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 2,
                    render: function (data, type, row) {
                        switch (data) {
                            case 10:
                                return "POS机"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 7,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 8,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 9,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 10,
                    render: function (data, type, row) {
                        switch (data) {
                            case "1":
                                return "启用"
                                break;
                            case "0":
                                return "禁用"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }

                },
                {
                    targets: 11,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "暂无权限",
                            //"fn": "edit(" + row.id + ",'5')",
                            "type": "success"
                        });
                        /*var text, type, state;
                        if (row.state == "1") {
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
                        context.func.push({
                            "name": "删除",
                            "fn": "del(" + row.id + ",'5')",
                            "type": "success"
                        });*/
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getAgentList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.merchantYunPayAccount = $("input[name='merchantYunPayAccount']").val();
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                    d.state = $("select[name='state']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);


            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.tradeDetailList = {
        initTables: function (tableId,permissions) {
            var columns = [
                {"data": "realName"},
                {"data": "lklMachineCode"},
                {"data": "lklTerminalCode"},
                {"data": "deviceName"},
                {"data": "deviceType"},
                {"data": "merchantName"},
                {"data": "lklMerchantCode"},
                {"data": "merchantYunPayAccount"},
                {"data": "merchantPhone"},
                {"data": "moneyCount"},
                {"data": "dayCount"},
                {"data": "createTime"},
                {"data": "state"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 4,
                    render: function (data, type, row) {
                        switch (data) {
                            case 10:
                                return "POS机"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 9,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 10,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 11,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 12,
                    render: function (data, type, row) {
                        switch (data) {
                            case "1":
                                return "启用"
                                break;
                            case "0":
                                return "禁用"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 13,
                    render: function (data, type, row) {
                        var countCondition = parseInt($("#countCondition").val());
                        var context = {func: []};
                        if (parseInt(row.moneyCount) >= countCondition) {
                            context.func.push({
                                "name": "退押金",
                                "fn": "submitTyj(" + row.id + ",'"+ row.deviceUnique +"')",
                                "type": "danger"
                            });
                        };
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getTradeDetailList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.merchantYunPayAccount = $("input[name='merchantYunPayAccount']").val();
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                    d.merchantName = $("input[name='merchantName']").val();
                    d.lklMachineCode = $("input[name='lklMachineCode']").val();
                    d.lklTerminalCode = $("input[name='lklTerminalCode']").val();
                    d.agentUnique = $("input[name='agentUnique']").val();
                    d.state = $("select[name='state']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.posOrder = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "orderNumber"},
                {"data": "createTime"},
                {"data": "lklMerchantCode"},
                {"data": "merchantName"},
                {"data": "merchantYunId"},
                {"data": "yunId"},
                {"data": "totalFee"},
                {"data": "orderType"},
                {"data": "payState"},
                {"data": "payTime"},
                {"data": "payType"},
                {"data": "appPayState"},
                {"data": "orderState"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 1,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 6,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 7,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "POS机付款"
                                break;
                            case 2:
                                return "云支付扫码"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 8,
                    render: function (data, type, row) {
                        switch (data) {
                            case 0:
                                return "未支付"
                                break;
                            case 1:
                                return "已支付"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 9,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 10,
                    render: function (data, type, row) {
                        switch (data) {
                            case "00":
                                return "借记卡"
                                break;
                            case "01":
                                return "贷记卡"
                                break;
                            case "Y01":
                                return "扫码支付"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 11,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "成功"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 12,
                    render: function (data, type, row) {
                        switch (data) {
                            case 2:
                                return "成功"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {   targets: 13,
                    render: function (data, type, row) {
                        var context = {func: []};
                        if (row.payState == 1 && row.payType != "Y01") {
                            context.func.push({
                                "name": "详情",
                                "fn": "detail(\'" + row.orderNumber + "\')",
                                "type": "danger"
                            });
                        };
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
                    d.orderNumber = $("input[name='orderNumber']").val();
                    d.lklMerchantCode = $("input[name='lklMerchantCode']").val();
                    d.merchantYunId = $("input[name='merchantYunId']").val();
                    d.yunId = $("input[name='yunId']").val();
                    d.sdate = $("input[name='sdate']").val();
                    d.edate = $("input[name='edate']").val();
                    d.orderType = $("select[name='orderType']").val();
                    d.payState = $("select[name='payState']").val();
                    d.payType = $("select[name='payType']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.repairOrderIndex = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "orderNumber"},
                {"data": "createTime"},
                {"data": "machineCode"},
                {"data": "deviceName"},
                {"data": "merchantName"},
                {"data": "merchantYunId"},
                {"data": "yunId"},
                {"data": "totalFee"},
                {"data": "cardNo"},
                {"data": "orderType"},
                {"data": "payState"},
                {"data": "payTime"},
                {"data": "payType"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 1,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 7,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 9,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "POS机付款"
                                break;
                            case 2:
                                return "云支付扫码"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 10,
                    render: function (data, type, row) {
                        switch (data) {
                            case 0:
                                return "未支付"
                                break;
                            case 1:
                                return "已支付"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 11,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 12,
                    render: function (data, type, row) {
                        switch (data) {
                            case "00":
                                return "借记卡"
                                break;
                            case "01":
                                return "贷记卡"
                                break;
                            case "Y01":
                                return "扫码支付"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 13,
                    render: function (data, type, row) {
                        var context = {func: []};
                        var text, type;
                        if (row.payState == 0 ) {
                            text = "补单";
                            type = "danger"
                            context.func.push({
                                "name": text,
                                "fn": "editOrderStatus(\'" + row.orderNumber + "\')",
                                "type": type
                            });
                        };
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getRepairOrderList",
                /*error:function(){
                    alert("11")
                    //覆盖原生的error方法
                },*/
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.orderNumber = $("input[name='orderNumber']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);
            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },
    $.agentPosOrder = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "orderNumber"},
                {"data": "createTime"},
                {"data": "machineCode"},
                {"data": "deviceName"},
                {"data": "merchantName"},
                {"data": "merchantYunId"},
                {"data": "totalFee"},
                {"data": "orderType"},
                {"data": "payState"},
                {"data": "payTime"},
                {"data": "payType"}
            ];
            var columnDefs = [
                {
                    targets: 1,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 6,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 7,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "POS机付款"
                                break;
                            case 2:
                                return "云支付扫码"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 8,
                    render: function (data, type, row) {
                        switch (data) {
                            case 0:
                                return "未支付"
                                break;
                            case 1:
                                return "已支付"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                },
                {
                    targets: 9,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 10,
                    render: function (data, type, row) {
                        switch (data) {
                            case "00":
                                return "借记卡"
                                break;
                            case "01":
                                return "贷记卡"
                                break;
                            case "Y01":
                                return "扫码支付"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                }
            ]
            var ajax = {
                url: "getAgentPosOrderList",
//                error:function(){
//                    alert("11")
//                    //覆盖原生的error方法
//                },
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.machineCode = $("input[name='machineCode']").val();
                    d.merchantYunId = $("input[name='merchantYunId']").val();
                    d.sdate = $("input[name='sdate']").val();
                    d.edate = $("input[name='edate']").val();
                    d.orderType = $("select[name='orderType']").val();
                    d.payState = $("select[name='payState']").val();
                    d.payType = $("select[name='payType']").val();
                    d.agentUnique = $("input[name='agentUnique']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.checkRecord = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "checkDay"},
                {"data": "orderNum"},
                {"data": "accountCount"},
                {"data": "lklCheckStatus"},
                {"data": "lklCheckResult"},
                {"data": "ypOrderNum"},
                {"data": "ypAccountCount"},
                {"data": "ypCheckStatus"},
                {"data": "ypCheckResult"},
                {"data": "checkDate"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 3,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 7,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 10,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 11,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "详情",
                            "fn": "detail(\'" +  row.checkDay + "\','5')",
                            "type": "success"
                        });
                        var text, type;
                        if (!((row.lklCheckStatus == 10000 && row.ypCheckStatus == 10010) || row.ypCheckStatus == 10011)) {
                            text = "重新对账";
                            type = "danger"
                            context.func.push({
                                "name": text,
                                "fn": "restart(\'" + row.checkDay + "\',\'" + row.checkUnique + "\')",
                                "type": type
                            });
                        };
                        var html = template(context);
                        return html;
                    }

                }

            ]
            var ajax = {
                url: "getList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.checkDay = $("input[name='checkDay']").val();
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

    $.smsModel = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "title"},
                {"data": "sign"},
                {"data": "content"},
                {"data": "state"},
                {"data": "reason"},
                {"data": "createTime"},
                {"data": "updateTime"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 4,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "审核通过";
                                break;
                            case 2:
                                return "审核中";
                                break;
                            case 3:
                                return "审核失败";
                                break;
                            default:
                                return "";
                                break;
                        }
                    }
                },
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
                    targets: 7,
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
                            "fn": "edit(" +  row.id + ")",
                            "type": "primary"
                        });
                        //if (row.state==2) {
                        //    context.func.push({
                        //        "name": "更新",
                        //        "fn": "synModel(" +  row.id + ")",
                        //        "type": "primary"
                        //    });
                        //}
                        context.func.push({
                            "name": "删除",
                            "fn": "del(" +  row.id + ")",
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
                    //添加额外的参数传给服务器
                    d.content = $("input[name='tpl_content']").val();
                    d.state = $("select[name='check_status']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.smsGroup = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "name"},
                {"data": "accCount"},
                //{"data": "state"},
                {"data": "createTime"},
                {"data": "updateTime"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 1,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": data,
                            "fn": "showAccount(" +  row.id + ",'"+ row.name + "')",
                            "type": "primary"
                        });
                        var html = template(context);
                        return html;
                    }
                },
                //{
                //    targets: 3,
                //    render: function (data, type, row) {
                //        switch (data) {
                //            case 1:
                //                return "启用";
                //                break;
                //            case 0:
                //                return "禁用";
                //                break;
                //            default:
                //                return "";
                //                break;
                //        }
                //    }
                //},
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
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 5,
                    render: function (data, type, row) {
                        var context = {func: []};
                        //if (row.state==0) {
                        //    context.func.push({
                        //        "name": "启用",
                        //        "fn": "enableState(" +  row.id + ")",
                        //        "type": "primary"
                        //    });
                        //} else if (row.state==1) {
                        //    context.func.push({
                        //        "name": "禁用",
                        //        "fn": "limitState(" + row.id + ")",
                        //        "type": "danger"
                        //    });
                        //}
                        context.func.push({
                            "name": "删除",
                            "fn": "del(" + row.id + ")",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getGroupList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.accName = $("input[name='accName']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }
    },

    $.smsAccount = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "name"},
                {"data": "sex"},
                {"data": "phone"},
                //{"data": "groupId"},
                {"data": "createTime"},
                {"data": "updateTime"},
                {"data": null}
            ];
            var columnDefs = [
                {
                    targets: 2,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "男";
                                break;
                            case 2:
                                return "女";
                                break;
                            default:
                                return "";
                                break;
                        }
                    }
                },
                //{
                //    targets: 4,
                //    render: function (data, type, row) {
                //        return mapType[data];
                //    }
                //},
                {
                    targets: 4,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 5,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 6,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "修改",
                            "fn": "edit(" + row.id + ")",
                            "type": "primary"
                        });
                        context.func.push({
                            "name": "删除",
                            "fn": "del(" + row.id + ")",
                            "type": "danger"
                        });
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getAccountList",
                data: function (d) {
                    d.groupId = $("#groupId").val();
                    //d.accName = $("#accName").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.smsRecord = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "mobile"},
                {"data": "count"},
                {"data": "fee"},
                {"data": "state"},
                {"data": "msg"},
                {"data": "createTime"}
            ];
            var columnDefs = [
                {
                    targets: 4,
                    render: function (data, type, row) {
                        switch (data) {
                            case 0:
                                return "成功";
                                break;
                            default:
                                return "失败";
                                break;
                        }
                    }
                },
                {
                    targets: 6,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                }
            ]
            var ajax = {
                url: "getSmsRecordList",
                data: function (d) {
                    d.paraMobile = $("#paraMobile").val();
                    d.paraState = $("#paraState").val();
                    d.paraStartTime = $("#paraStartTime").val();
                    d.paraEndTime = $("#paraEndTime").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.inComeRecord = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "time"},
                {"data": "checkDay"},
                {"data": "money"},
                {"data": "agentName"},
                {"data": "agentYPLoginName"},
                {"data": "type"}
            ];
            var columnDefs = [
                {
                    targets: 1,
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
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 6,
                    render: function (data, type, row) {
                        switch (data) {
                            case "1":
                                return "收益"
                                break;
                            case "2":
                                return "补贴"
                                break;
                            case "3":
                                return "返现"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                }
            ]
            var ajax = {
                url: "getList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.agentYPLoginName = $("input[name='agentYPLoginName']").val();
                    d.sdate = $("input[name='strSdate']").val();
                    d.edate = $("input[name='strEdate']").val();
                    d.agentName = $("input[name='agentName']").val();
                    d.type = $("select[name='type']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.TxnRecord = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "txnDate"},
                {"data": "txnVirtualAccount"},
                {"data": "txnCountBs"},
                {"data": "txnJyMoney"},
                {"data": "txnRzMoney"},
                {"data": "txnHbMoney"},
                {"data": "txnBfjMoney"},
                {"data": "updateTime"}
            ];
            var columnDefs = [
                {
                    targets: 8,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                }
            ]
            var ajax = {
                url: "getTxnList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.sdate = $("input[name='sdate']").val();
                    d.edate = $("input[name='edate']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.TiXianRecord = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "tixianDate"},
                {"data": "tixianMoney"},
                {"data": "tixianCountBs"},
                {"data": "tixianPtMoney"},
                {"data": "tixianShMoney"},
                {"data": "updateTime"}
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
                }
            ]
            var ajax = {
                url: "getTiXianList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.sdate = $("input[name='sdate']").val();
                    d.edate = $("input[name='edate']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.agentStatistics = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "realName"},
                {"data": "yunPayLoginName"},
                {"data": "remainingSum"},
                {"data": "totalFee"},
                {"data": "dayCount"},
                {"data": "monthCount"}
            ];
            var columnDefs = [
                {
                    targets: 3,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 4,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 5,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 6,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
            ]
            var ajax = {
                url: "getAgentStatisticsList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.yunPayLoginName = $("input[name='yunPayLoginName']").val();
                    d.strDay = $("input[name='strDay']").val();
                    d.strMonth = $("input[name='strMonth']").val();
                    d.realName = $("input[name='realName']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },


    $.agentStatisticsBt = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "realName"},
                {"data": "yunPayLoginName"},
                {"data": "monthCount"}
            ];
            var columnDefs = [
                {
                    targets: 3,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                }
            ]
            var ajax = {
                url: "getAgentStatisticsBtList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.yunPayLoginName = $("input[name='yunPayLoginName']").val();
                    //d.strMonth = $("input[name='strMonth']").val();
                    d.realName = $("input[name='realName']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.tradeCount = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "realName"},
                {"data": "yunPayLoginName"},
                {"data": "agentDeviceAllCountMoney"},
                {"data": "agentDeviceMonthCountMoney"},
                {"data": "agentDeviceCount"},
                {"data": "agentDeviceMonthCount"},
                {"data":null}
            ];
            var columnDefs = [
                {
                    targets: 2,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 3,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 6,
                    render: function (data, type, row) {
                        var context = {func: []};
                        context.func.push({
                            "name": "查看交易详情",
                            "fn": "tradeDetail(\'" + row.agentUnique + "\','5')",
                            "type": "success"
                        });
                        var html = template(context);
                        return html;
                    }
                }
            ]
            var ajax = {
                url: "getTradeCountList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.yunPayLoginName = $("input[name='yunPayLoginName']").val();
                    d.realName = $("input[name='realName']").val();
                    d.strMonth = $("input[name='strMonth']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }
    },

    $.inComeAgentRecord = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "time"},
                {"data": "checkDay"},
                {"data": "money"},
                {"data": "agentName"},
                {"data": "agentYPLoginName"},
                {"data": "type"}
            ];
            var columnDefs = [
                {
                    targets: 1,
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
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 6,
                    render: function (data, type, row) {
                        switch (data) {
                            case "1":
                                return "代理收益"
                                break;
                            case "2":
                                return "云智补贴"
                                break;
                            case "3":
                                return "购机返现"
                                break;
                            case "4":
                                return "购机退款"
                                break;
                            default:
                                return ""
                                break;
                        }
                    }
                }
            ]
            var ajax = {
                url: "getinComeAgentList",
                data: function (d) {
                    //添加额外的参数传给服务器
//                    d.lx = $("select[name='lx']").val();
                    d.sdate = $("input[name='sdate']").val();
                    d.edate = $("input[name='edate']").val();
                    d.type = $("select[name='type']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);

            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.WithdrawRecord = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "orderNumber"},
                {"data": "createTime"},
                {"data": "totalFee"},
                {"data": "state"},
                {"data": "note"},
                {"data": "note"}
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
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                targets: 4,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "提现成功"
                                break;
                            case 0:
                                return "提现失败"
                                break;
                        }
                    }
                },
            ]
            var ajax = {
                url: "getList",
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
    },

    $.WithdrawAgencyRecord = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "orderNumber"},
                {"data": "realName"},
                {"data": "yunPayLoginName"},
                {"data": "createTime"},
                {"data": "totalFee"},
                {"data": "state"},
                {"data": "note"}
            ];
            var columnDefs = [
                {
                    targets: 4,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },
                {
                    targets: 5,
                    render: function (data, type, row) {
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                    targets: 6,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "提现成功"
                                break;
                            case 0:
                                return "提现失败"
                                break;
                        }
                    }
                },
            ]
            var ajax = {
                url: "getAgencyList",
                data: function (d) {
                    //添加额外的参数传给服务器
                    d.yunPayLoginName = $("input[name='yunPayLoginName']").val();
                    d.sdate = $("input[name='sdate']").val();
                    d.edate = $("input[name='edate']").val();
                    d.realName = $("input[name='realName']").val();
                }
            }
            var table = $.init.initDataTables(tableId, ajax, columns, columnDefs, null);
            $("#message-query").on("click", function () {
                table.ajax.reload();
            })
            return table;
        }

    },

    $.Account = {
        initTables: function (tableId, permissions) {
            var columns = [
                {"data": "id"},
                {"data": "orderNumber"},
                {"data": "createTime"},
                {"data": "totalFee"},
                {"data": "state"},
                {"data": "note"}
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
                        if (null == data) {
                            return null;
                        } else {
                            return (data/100).toFixed(2);
                        }
                    }
                },
                {
                targets: 4,
                    render: function (data, type, row) {
                        switch (data) {
                            case 1:
                                return "提现成功"
                                break;
                            case 0:
                                return "提现失败"
                                break;
                        }
                    }
                },
            ]
            var ajax = {
                url: "getList",
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

                },
                {
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

    }
