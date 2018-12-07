${'<#include "../include/header.html"/>'}
<!-- 绑定事件 -->
<script type="text/javascript">
    var viewModel = kendo.observable({
        model: {},
        refresh: function () {
            $('#Grid').data('kendoGrid').dataSource.read();
        },
        refreshMain: function (e) {
            this.set("lastSelectedRow", null);
            dsBrowseUp.page(1);
            gvBrowseUp.setCurrentRow(null);//将当前行置空
        },
        queryResource: function (e) {
            $('#Grid').data('kendoGrid').dataSource.page(1);
        },
        moreQueryFunction: function (e) {
            if (viewModel.get("browseDisabled")) {
                return false;
            }
            queryData();
        },
        closeWinSearch: function (e) {//关闭高级查询
            $("#div_query").data("kendoWindow").close();
        },
        querySearch: function (e) {
            var validator = $("#searchform").kendoValidator().data("kendoValidator");
            if (validator.validate()) {
                gvBrowseUp.setCurrentRow(null);//将当前行置空
                $('#Grid').data('kendoGrid').dataSource.read();
                viewModel.closeWinSearch();
            }
        },
        resetSearch: function (e) {
            var data = this.model.toJSON();
            //清空查询条件
            for (var key in data) {
                this.model.set(key, "");
            }
        }, removeheader: function (e) {
            var bMainGrid = $("#Grid").data('kendoGrid');
            var checked = bMainGrid.selectedDataItems();
            var BaseUrl = _basePath;
            AjaxJson(BaseUrl + "${headerRemoveUrl}",
                    JSON.stringify(checked), function (data) {
                        if (!data.success) {
                            kendo.ui.showErrorDialog({message: data.message});

                        } else {
                            dsBrowseUp.page(1);
                        }

                    }, false, 'POST', "application/json");
        }
    });


    //更多查询Func
    function queryData() {
        var win = $("#div_query").data("kendoWindow");
        win.center().open();
    }

    //查询窗口
    $(function () {
        $("#div_query").kendoWindow({
            title: "",
            width: 600,
            height: 350,
            actions: [
                "Pin",
                "Minimize",
                "Maximize",
                "Close"
            ],
            modal: true
        });
    });

    //Ajax 调用信息
    function AjaxJson(url, postData, callBack, async, type, contentType) {
        $.ajax({
            url: url,
            type: type != undefined ? type : "POST",
            data: postData,
            dataType: "json",
            async: async != undefined ? async : true,
            contentType: contentType == undefined ? "application/x-www-form-urlencoded" : "application/json",
            success: function (data) {
                if (data) {
                    if (!data.success && !callBack) {
                        alert(data.message);
                        return;
                    }
                }
                callBack(data);
            },
            error: function (data) {
                alert(data.responseText);
            }
        });
    }

</script>

<!--进入编辑窗口的div-->
<div id="dialog"></div>
<!--高级查询布局【每行最多放3列,class:不能随便定义】-->
<div id="div_query" style="display: none;">
    <div style="margin-top: 5px;">
        <form id="searchform" class="form-horizontal" method="post" enctype="application/json;charset=UTF-8">
            <!--高级查询 开始日期-->
        <#list columnsInfoHeader as infos>
            <#if (infos_index+1)%3 == 0>
            <div class="row">
            </#if>
            <div class="col-sm-4">
                <div class="form-group">
                    <label class="col-md-3 control-label">${'<@spring.message'}
                        "${headerDtoName?lower_case}.${infos.tableColumnsName?lower_case}"/></label>
                    <div class="col-md-7">
                        <input name="${infos.tableColumnsName}" id="${infos.tableColumnsName}"
                               data-label='${'<@spring.message'} "${headerDtoName?lower_case}.${infos.tableColumnsName?lower_case}"/>'
                               data-role="maskedtextbox"
                               type="text" style="width: 100%" data-bind="value:model.${infos.tableColumnsName}"
                               class="k-textbox">
                    </div>
                </div>
            </div>
            <#if (infos_index+1)%3 == 0>
            </div>
            </#if>
        </#list>

            <div style="text-align:right; line-height: 50px;height: 50px; position: fixed;bottom: 0px; background-color: #ededed; width: 100%; margin-left:-0.58em;">
                <div style="padding-right: 10px;">
                    <span class="btn btn-primary" data-bind="click:querySearch" type="submit"
                          style="padding:4px 10px; margin-right:5px;"><i class="fa fa-search"
                                                                         style="margin-right:3px;"></i>${'<@spring.message "hap.query"'}/></span>
                    <span class="btn btn-default" data-bind="click:resetSearch" type="submit"
                          style="padding:4.5px 10px; margin-right:5px;"><i class="fa fa-eraser"
                                                                           style="margin-right:3px;"></i>${'<@spring.message "hap.reset"'}/></span>
                    <span class="btn btn-danger" data-bind="click:closeWinSearch" type="button"
                          style="padding:4px 10px; margin-right:5px;"><i class="fa fa-undo"
                                                                         style="margin-right:3px;"></i>${'<@spring.message "hap.cancel"'}/></span>
                </div>
            </div>

        </form>
    </div>
</div>

<body>
<!--<div id="page-content">-->

<div id="page-content">
    <div class="pull-right" id="toolbar-btn" style="padding-left:10px;padding-bottom:10px">
        <span class="btn btn-success" style="float:left;margin-right:3px;"
              data-bind="click:refreshMain" type="submit"><i class="fa fa-refresh"
                                                             style="margin-right:3px;"></i><${'@spring.message "hap.refresh"'}/></span>
        <span class="btn btn-primary" style="float:left;margin-right:3px;"
              data-bind="click:moreQueryFunction" type="submit"><i class="fa fa-search"
                                                                   style="margin-right:3px;"></i>${'<@spring.message "hap.query"'}/></span>
        <span class="btn btn-primary" style="float:left;margin-right:5px;" onclick="openWindow()"><i
                class="fa fa-plus-square" style="margin-right:3px;"></i>${'<@spring.message "hap.new"/>'}</span>
        <span data-bind="click:removeheader" type="submit" class="btn btn-danger"
              style="float:left;margin-right:5px"><i class="fa fa-trash-o"
                                                     style="margin-right:3px;"></i>${'<@spring.message "hap.delete"/>'}</span>

    </div>
    <script>kendo.bind($('#toolbar-btn'), viewModel);</script>


    <div style="clear:both; margin-top: 10px;">
        <div id="Grid"></div>
    </div>
    <script>kendo.bind($('#Grid'), viewModel);</script>

    <div style="clear:both; margin-top: 30px;">
        <div id="DetailGrid"></div>
    </div>
    <script>kendo.bind($('#DetailGrid'), viewModel);</script>

    <div id="openWindow"></div>
</div>


<!-- 查询 -->
<script>

    kendo.bind($('#div_query'), viewModel);

    //验证
    $("#queryForm,#mainform").kendoValidator({
        messages: {
            required: '${'<@spring.message'} "hap.validation.notempty"/>'
        },
        invalidMessageType: "tooltip"
    });
</script>
<!-- 查询 -->


<!-- 数据源以及表格-->
<script type="text/javascript">

    //头数据源
    var BaseUrl = _basePath;
    dsBrowseUp = new kendo.data.DataSource({
        transport: {
            read: {
                url: BaseUrl + "${headerQueryUrl}",
                type: "POST",
                dataType: "json"
            },
            update: {
                url: BaseUrl + "${headerSubmitUrl}",
                type: "POST",
                contentType: "application/json"
            },
            destroy: {
                url: BaseUrl + "${headerRemoveUrl}",
                type: "POST",
                contentType: "application/json"
            },
            create: {
                url: BaseUrl + "${headerSubmitUrl}",
                type: "POST",
                contentType: "application/json"
            },
            parameterMap: function (options, type) {
                if (type !== "read" && options.models) {
                    var datas = Hap.prepareSubmitParameter(options, type);
                    return kendo.stringify(datas);
                } else if (type === "read") {
                    return Hap.prepareQueryParameter(viewModel.model.toJSON(), options)
                }
            }
        },
        batch: true,
        serverPaging: true,
        pageSize: 10,
        schema: {
            data: 'rows',
            total: 'total',
            model: {
                id: "${columnsInfoHeader[0].tableColumnsName}",
                fields: {},
                editable: false
            }
        }
    });

    //头表格
    gcBrowseUp = $("#Grid").kendoGrid({
        dataSource: dsBrowseUp,
        resizable: true,
        scrollable: true,
        navigatable: false,
        sortable: true,
        selectable: 'multiple, rowbox',
        pageable: {
            pageSizes: [5, 10, 20, 50],
            refresh: true,
            buttonCount: 5
        },
        columns: [
        <#list columnsInfoHeader as infos>
            {
                field: "${infos.tableColumnsName}",
                title: '${'<@spring.message'} "${headerDtoName?lower_case}.${infos.tableColumnsName?lower_case}"/>',
                width: 120
            },
        </#list>
            {
                title: '${'<@spring.message "hap.details"/>'}',
                width: 120,
                template: function (item) {
                    return Hap.createAnchor('${'<@spring.message "hap.details"/>'}', openWindow, item.${columnsInfoHeader[0].tableColumnsName});
                }
            }
        ],
        change: function (e) {
            for (var i = 0; i < selectedRows.length; i++) {
                var dataItem = this.dataItem(selectedRows[i]);
                gvBrowseUp.setCurrentRow(dataItem);
                break;
            }
        }, dataBound: function () {
            var rows = this.tbody.find("tr");
            if (rows.length > 0) {
                rows[0].click();
            }
        }
    });

    gvBrowseUp = gcBrowseUp.data("kendoGrid");


    gcBrowseUp.on('click', '.k-grid-content tr', function () {

        var row = $(this).closest("tr");
        $(this).parent().find("tr").removeClass("bkg");
        row.addClass("bkg");
        // 获取当前选择行数据
        gvBrowseUp.setCurrentRow(gvBrowseUp.dataItem($(this).context));
    });
    gvBrowseUp.setCurrentRow = function (rowItem) {
        if (gvBrowseUp.cusCurrentRow == rowItem)
            return;
        gvBrowseUp.cusCurrentRow = rowItem;

        dsBrowseDown.read();
    };

    gvBrowseUp.currentRow = function () {
        return gvBrowseUp.cusCurrentRow;
    };


    /*行数据源*/
    dsBrowseDown = new kendo.data.DataSource({
        transport: {
            read: {
                url: BaseUrl + "${lineQueryUrl}",
                type: "POST",
                dataType: "json"
            },
            update: {
                url: BaseUrl + "${lineSubmitUrl}",
                type: "POST",
                contentType: "application/json"
            },
            destroy: {
                url: BaseUrl + "${lineRemoveUrl}",
                type: "POST",
                contentType: "application/json"
            },
            create: {
                url: BaseUrl + "${lineSubmitUrl}",
                type: "POST",
                contentType: "application/json"
            },
            parameterMap: function (options, type) {
                if (type !== "read" && options.models) {
                    var map = {};
                    console.log(gvBrowseUp.currentRow());
                    if (gvBrowseUp.currentRow())
                        map.omHeaderId = gvBrowseUp.currentRow().omHeaderId;
                    else
                        map.omHeaderId = 0;
                    return map;
                } else if (type === "read") {
                    //return Hap.prepareQueryParameter(viewModel.model.toJSON(), options)
                    var map = {};
                    if (gvBrowseUp.currentRow())
                        map.omHeaderId = gvBrowseUp.currentRow().omHeaderId;
                    else
                        map.omHeaderId = 0;
                    return map;
                }

            }
        },
        batch: true,
        serverPaging: true,
        //pageSize: 10,
        schema: {
            data: 'rows',
            total: 'total',
            model: {
                id: "${columnsInfoLine[0].tableColumnsName}",
                fields: {},
                editable: false
            }
        }
    });


    gcBrowseDown = $("#DetailGrid").kendoGrid({
        dataSource: dsBrowseDown,
        resizable: true,
        scrollable: true,
        navigatable: false,
        sortable: true,
        selectable: 'multiple, rowbox',
        pageable: {
            pageSizes: [5, 10, 20, 50],
            refresh: true,
            buttonCount: 5
        },
        columns: [
        <#list columnsInfoLine as infos>
            {
                field: "${infos.tableColumnsName}",
                title: '${'<@spring.message'} "${lineDtoName?lower_case}.${infos.tableColumnsName?lower_case}"/>',
                width: 120
            },
        </#list>],
/*        editable: true,
        edit: function (e) {
            $(e.container).find("input").attr("readonly", "readonly");
        },*/
        change: function (e) {
            var selectedRows = this.select();
            for (var i = 0; i < selectedRows.length; i++) {
                var dataItem = this.dataItem(selectedRows[i]);
                gvBrowseDown.setCurrentRow(dataItem);
                break;
            }
        }
    });

    gvBrowseDown = gcBrowseDown.data("kendoGrid");
    gcBrowseDown.on('click', '.k-grid-content tr', function () {

        // 获取当前选择行数据
        gvBrowseDown.setCurrentRow(gvBrowseDown.dataItem($(this).context));

    });
    gvBrowseDown.setCurrentRow = function (rowItem) {
        gvBrowseDown.cusCurrentRow = rowItem;
    };
    gvBrowseDown.currentRow = function () {
        return gvBrowseDown.cusCurrentRow;
    };


    function openWindow(param) {
        var url = BaseUrl + '${lineUrl}';
        if (param) {
            url = url + '?${headerRelationColumn}=' + param;
        }
        var editWin = Hap.createWindow('#openWindow', {
            width: '85%',
            height: '75%',
            title: '明细',
            url: url
        });
        if (parent.autoResizeIframe) {
            parent.autoResizeIframe('${'$'}{RequestParameters.functionCode!}', 870, function () {
                editWin.center().open();
            })
        } else {
            editWin.center().open();
        }
    }


</script>


</body>
</html>