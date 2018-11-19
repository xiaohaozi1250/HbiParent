${'<#include "../include/header.html"/>'}
<script src="${base.contextPath}/lib/bootstrap-3.3.7/js/bootstrap.min.js"></script>

<!-- 绑定事件 -->
<script type="text/javascript">
    var viewModel = kendo.observable({
        model: {
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
        }
    });


</script>


<!--高级查询布局【每行最多放3列,class:不能随便定义】-->
<div class="div_query" id = "div_query" style="height: 450px;display: none;" title="<@spring.message "task.picknotify"/>">
    <div style="margin-top: 5px;">
        <form id="searchform" class="form-horizontal" method="post" enctype="application/json;charset=UTF-8">
           <!--高级查询 开始日期
           <div class="col-xs-4">
            <div class="form-group">
                <div class="col-xs-9">
                    <label class="control-label"><@spring.message "common.startdate"/>:</label>
                    <input id="startDate" placeholder='<@spring.message "common.startdate"/>'
                           data-bind="value:model.startDate">
                </div>
            </div>
         </div>
         -->
        </form>
    </div>
</div>

<body>
<!--<div id="page-content">-->

<div id="page-content">
    <div class="pull-left" id="query-form" functionDiv="true"
         style="right: 10px; position: absolute; top: 0px;">
        <span class="btn btn-success" style="float:left;margin-right:3px;" data-bind="click:refreshMain,disabled:isEditMode" type="submit"><i class="fa fa-refresh" style="margin-right:3px;"></i><@spring.message "hap.refresh"/></span>
        <span class="btn btn-primary" style="float:left;margin-right:3px;" data-bind="click:moreQueryFunction,disabled:browseDisabled" type="submit"><i class="fa fa-search" style="margin-right:3px;"></i><@spring.message "hap.query"/></span>
        <div style="clear:both"></div>
    </div>
    <script>kendo.bind($('#query-form'), viewModel);</script>

    <div style="clear:both">
        <div id="Grid"></div>
    </div>
    <script>kendo.bind($('#Grid'), viewModel);</script>

    <div style="clear:both; margin-top: 10px;">
        <div id="DetailGrid"></div>
    </div>
    <script>kendo.bind($('#DetailGrid'), viewModel);</script>
</div>


<!-- 查询 -->
<script>
    $("#startDate").kendoDatePicker({
        format: "yyyy-MM-dd"
    });

    $("#endDate").kendoDatePicker({
        format: "yyyy-MM-dd"
    });

    kendo.bind($('#div_query'), viewModel);

    kendo.bind($('#mainform'), editRowViewModel);
    //验证
    $("#queryForm,#mainform").kendoValidator({
        messages: {
            required: '<@spring.message "hap.validation.notempty"/>'
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
                url: BaseUrl + "/trm/taskxj/query",
                type: "POST",
                dataType: "json"
            },
            update: {
                url: BaseUrl + "/trm/taskxj/submit",
                type: "POST",
                contentType: "application/json"
            },
            destroy: {
                url: BaseUrl + "/trm/taskxj/remove",
                type: "POST",
                contentType: "application/json"
            },
            create: {
                url: BaseUrl + "/trm/taskxj/submit",
                type: "POST",
                contentType: "application/json"
            },
            parameterMap: function (options, type) {
                if (type !== "read" && options.models) {
                    var datas = Hap.prepareSubmitParameter(options, type)
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
                id: "id",
                fields: {}
            }
        }
    });

    //头表格
    gcBrowseUp = $("#Grid").kendoGrid({
        dataSource: dsBrowseUp,
        height: '100%',
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
            {
                field: "sourceBillCode",
                title: '<@spring.message "Taskxj.sourceBillCode"/>',
                width: 120
            }
        ],
        editable: true,
        edit: function (e) {
            $(e.container).find("input").attr("readonly", "readonly");
        },
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
        editRowViewModel.set("model", rowItem == null ? {} : rowItem);
        editRowViewModel.model.isSerialLoaded = false;

        dsBrowseDown.read();
    };

    gvBrowseUp.currentRow = function () {
        return gvBrowseUp.cusCurrentRow;
    };



    /*设置浏览区明细数据源*/
    dsBrowseDown = new kendo.data.DataSource({
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
                url: BaseUrl + "/trm/taskxj/detail/remove",
                type: "POST",
                contentType: "application/json"
            },
            create: {
                url: BaseUrl + "/trm/taskxj/detail/submit",
                type: "POST",
                contentType: "application/json"
            },
            parameterMap: function (options, type) {
                if (type !== "read" && options.models) {
                    var map = {};
                    console.log(gvBrowseUp.currentRow());
                    if (gvBrowseUp.currentRow())
                        map.taskId = gvBrowseUp.currentRow().id;
                    else
                        map.taskId = 0;
                    return map;
                } else if (type === "read") {
                    //return Hap.prepareQueryParameter(viewModel.model.toJSON(), options)
                    var map = {};
                    if (gvBrowseUp.currentRow())
                        map.taskId = gvBrowseUp.currentRow().id;
                    else
                        map.taskId = 0;
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
                id: "id",
                fields: {}
            }
        }
    });


    gcBrowseDown = $("#BrowseDetailGrid").kendoGrid({
        dataSource: dsBrowseDown,
        height: '100%',
        resizable: true,
        scrollable: true,
        navigatable: false,
        sortable: true,
        selectable: 'multiple, rowbox',
        columns: [
            {
                field: "lineNum",
                title: '<@spring.message "Taskxj.lineNum"/>',
                width: 60
            }
        ],
        editable: true,
        edit: function (e) {
            $(e.container).find("input").attr("readonly", "readonly");
        },
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



    //自适应
    Hap.autoResizeGrid("#Grid");


</script>


</body>
</html>