/**
 * Created by huangdw on 2015/11/25.
 */
$.app = {
    url: 'rest/workstation/myapply',
    initApply: function () {
        $("#query").click(function () {
            $.app.turnPage(1);
        })
    },

    initData: function () {
        var data = {};
        $(":input").each(function (a, b) {
            var name = $(b).attr("name");//获取name属性
            if ('' != name && undefined != name && '' != $(b).val()) {
                data[name] = $(b).val();
            }
        });
        return data;
    },
    initWorkstation: function (id) {
        $.app.initsearchForm(id);
        //删除id = workstation的元素
        //在当前tab下添加workstation标签
        $(id).append($("#workstation").detach());
        $.app.turnPage(1);
    },
    initsearchForm: function (id) {
        $(id).append($("#searchForm").detach());

    },
    changeTab: function () {
        var url;
        $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
            var tabname = e.target.className;
            var id = e.target.hash;
            if ('myapply' == tabname) {
                $(".labelText").parent().show();
                $(".labelText").html("审批人");
            } else if ('myapprove' == tabname) {
                $(".labelText").parent().show();
                $(".labelText").html("申请人");
            } else {
                $(".labelText").parent().hide();
            }
            $.app.url = 'rest/workstation/' + tabname;
            $.app.initWorkstation(id);
        })
    },
    turnPage: function (n) {
        $.ajax({
            type: "GET",
            url: $.app.url + '?pageNo=' + n,
            data: $.app.initData(),
            dataType: "html",
            cache: false,
            success: function (data) {
                $('#workstation').replaceWith(data);
            },
            error: function (error) {
                alert(111);
            }
        });
    },
    initDatePicker: function () {
        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1
        });
        $('.form_date').datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });
        $('.form_time').datetimepicker({
            format: 'hh:ii:ss',
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 1,
            minView: 0,
            maxView: 1,
            forceParse: 0
        });
    }
},
    $.common = {
        tableURL: function (form) {
            return form[0].action;

        },
        initData: function (form) {
            var formId = form[0].id;
            var data = {};
            $("#" + formId + " :input").each(function (a, b) {
                var name = $(b).attr("name");//获取name属性
                if ('' != name && undefined != name && '' != $(b).val()) {
                    data[name] = $(b).val();
                }
            });
            return data;
        },
        turnPage: function (n, child) {
            var url, form;
            //找到当前table
            var table = $(child).closest(".table-pagination").prev("table");
            if (!table.length) {
                //找不到table,直接找form
                form = $(child).closest("form");
            } else {
                //找到tabel对应的form
                form = $(table).prev("form");
            }
            url = $.common.tableURL(form) + '?pageNo=' + n;
            $.ajax({
                type: "GET",
                url: url,
                data: $.common.initData(form),
                dataType: "html",
                cache: false,
                success: function (data) {
                    $('#content').html(data);
                },
                error: function (error) {
                    alert(111);
                }
            });
        },
        initQuery: function () {
            $("#query").click(function () {
                $.common.turnPage(1, this);
            })
        }
    },
    $.typetree = {
        typetreesSetting: {
            check: {
                enable: true,
                chkboxType: {"Y": "s", "N": "s"}
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        },
        typetreesSetting: {
            view: {
                selectedMulti: false        //禁止多点选中
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: "0"
                }
            }, callback: {
                onClick: function (event, treeId, treeNode) {
                    $("#pName").val(treeNode.name);
                    $("#pid").val(treeNode.id);
                    $("#typetrees").hide();
                }
            }
        },
        initTypeTree: function (basePath) {
            $.ajax({
                type: "GET",
                url: basePath+"rest/admin/type/showType",
                dataType: "json",
                cache: false,
                success: function (data) {
                    $.fn.zTree.init($("#typetree"), $.typetree.typetreesSetting, data.data);
                },
                error: function (error) {
                    alert("网络错误，请联系管理员");
                }
            });
        }
    },
    $.typenavigatetree = {
        typenavigatetreesSetting: {
            check: {
                enable: true,
                chkboxType: { "Y": "", "N": "" }
            },
            data: {
                simpleData: {
                    enable: true
                }
            }, callback: {
                onClick: function (event, treeId, treeNode) {
                    $("#pName").val(treeNode.name);
                    $("#pid").val(treeNode.id);
                    alert("1");
                }
            }
        },
        initTypeTree: function (basePath) {
            $.ajax({
                type: "GET",
                url: basePath+"rest/admin/type/showTypeNavigate",
                dataType: "json",
                cache: false,
                success: function (data) {
                    $.fn.zTree.init($("#typetree"), $.typenavigatetree.typenavigatetreesSetting, data.data);
                },
                error: function (error) {
                    alert("网络错误，请联系管理员");
                }
            });
        }
    },
    $.menutree = {
    menutreesSetting: {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "ps", "N": "ps"}
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
            selectedMulti: true        //禁止多点选中
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: "0"
            }
        }, callback: {
            onClick: function (event, treeId, treeNode) {
                $("#menuId").val(treeNode.id);
            }
        }
    },
    initMenuTree: function (roleid) {
        $.ajax({
            type: "GET",
            url: "getMenuTree",
            dataType: "json",
            cache: false,
            data:{roleid:roleid},
            success: function (data) {
                $.fn.zTree.init($("#menuZtree"), $.menutree.menutreesSetting, data.data);
            },
            error: function (error) {
                alert("网络错误，请联系管理员");
            }
        });
    }
},
    $.menustree = {
        menustreesSetting: {
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: {"Y": "s", "N": "s"}
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        },
        menustreesSetting: {
            view: {
                selectedMulti: false        //禁止多点选中
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: "0"
                }
            }, callback: {
                onClick: function (event, treeId, treeNode) {
                    $("#pName").val(treeNode.name);
                    $("#pid").val(treeNode.id);
                    $("#menustrees").hide();
                }
            }
        },
        initMenuTree: function () {
            $.ajax({
                type: "GET",
                url: "getMenuTree",
                dataType: "json",
                cache: false,
                success: function (data) {
                    $.fn.zTree.init($("#menusZtree"), $.menustree.menustreesSetting, data.data);
                },
                error: function (error) {
                    alert("网络错误，请联系管理员");
                }
            });
        }
    },
    $.producttypetree = {
        producttypetreesSetting: {
            check: {
                enable: true,
                chkboxType: {"Y": "s", "N": "s"}
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        },
        producttypetreesSetting: {
            view: {
                selectedMulti: false        //禁止多点选中
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: "0"
                }
            }, callback: {
                onClick: function (event, treeId, treeNode) {
                    $("#pName").val(treeNode.name);
                    $("#typeId").val(treeNode.id);
                    $("#typetrees").hide();
//                    $.ajax({
//                        type: "GET",
//                        url: "getProperyByTypeId",
//                        data:{typeId:treeNode.id},
//                        dataType: "json",
//                        cache: false,
//                        success: function (data) {
//                            var ls=data.data;
//                            if(ls!=null) {
//                                $("#property").empty();
//                                for (var i = 0; i <ls.length;i++){
//
//                                    var valueLs=ls[i].propertyValueList;
//                                    var str='';
//                                    for(var j=0;j<valueLs.length;j++){
//                                        str=str+'<input type="checkbox" name="value'+i+'" value="'+valueLs[j].valueId+'" onclick="slectBox('+ls.length+')">'+valueLs[j].valueName;
//                                    }
//                                    var html='<div class="form-group"> <div class="group"><label class="col-sm-1 control-label" >'+ls[i].propertyName+'</label> <div class="col-sm-11">  <input type="hidden" id="property'+i+'" name="pid" value="'+ls[i].propertyName+'">' +str+'</div></div></div>';
//                                    $("#property").append(html);
//                                    str='';
//                                }
//                                    }
//
//                        },
//                        error: function (error) {
//                            alert("网络错误，请联系管理员");
//                        }
//                    });
                }
            }
        },
        initTypeTree: function (basePath) {
            $.ajax({
                type: "GET",
                url: basePath+"rest/admin/type/showType",
                dataType: "json",
                cache: false,
                success: function (data) {
                    $.fn.zTree.init($("#typetree"), $.producttypetree.producttypetreesSetting, data.data);
                },
                error: function (error) {
                    alert("网络错误，请联系管理员");
                }
            });
        }
    },
    $.page = {
        turnPage: function (no) {
            $("#pageNo").val(no);
            $("#searchForm").submit();
        }
    }