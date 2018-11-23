${'<#include "../include/header.html"/>'}
<script type="text/javascript">
    var viewModel = Hap.createGridViewModel("#grid");
</script>
<body>
<div id="page-content">
    <div class="pull-left" id="toolbar-btn" style="padding-left:10px;padding-bottom:10px">
	<span class="btn btn-primary" style="float:left;margin-right:5px;" onclick="openWindow()"><i
            class="fa fa-plus-square" style="margin-right:3px;"></i>${'<@spring.message "hap.new"/>'}</span>
        <span data-bind="click:remove" class="btn btn-danger"
              style="float:left;margin-right:5px"><i class="fa fa-trash-o"
                                                     style="margin-right:3px;"></i>${'<@spring.message "hap.delete"/>'}</span>
        <span class="btn btn-primary k-grid-excel" style="float:left;margin-right:5px" data-bind="click:exportExcel"><i
                class="fa fa-file-excel-o"
                style="margin-right:3px;"></i>${'<@spring.message "hap.exportexcel"/>'}</span>
    </div>
    <script>kendo.bind($('#toolbar-btn'), viewModel);</script>
    <div class="pull-right" id="query-form" style="padding-bottom:10px;">
    <#list columnsInfoHeader as infos>
        <#if infos.tableColumnsName?ends_with("Code")||infos.tableColumnsName?ends_with("Name")||infos.tableColumnsName?ends_with("Type")||infos.tableColumnsName?ends_with("Number")>
            <input placeholder='${'<@spring.message '}"${headerDtoName}.${infos.tableColumnsName}"/>' type="text"
                   style="float:left;width:200px;margin-right:5px;" data-bind="value:model.${infos.tableColumnsName}"
                   class="k-textbox">
        </#if>
    </#list>
    <#list columnsInfoHeader as infos>
        <#if infos.tableColumnsName?ends_with("Code")||infos.tableColumnsName?ends_with("Name")||infos.tableColumnsName?ends_with("Type")||infos.tableColumnsName?ends_with("Number")>
            <span class="btn btn-primary" style="float:left;margin-right:5px;" data-bind="click:query" type="submit"><i
                    class="fa fa-search" style="margin-right:3px;"></i>${'<@spring.message "hap.query"/>'}</span>
            <span class="btn btn-default" type="button" data-bind="click:reset"><i class="fa fa-undo"
                                                                                   style="margin-right:3px;"></i>${'<@spring.message "hap.reset"/>'}</span>
        </#if>
    </#list>
        <div style="clear:both"></div>
    </div>
    <script>kendo.bind($('#query-form'), viewModel);</script>
    <div style="clear:both">
        <div id="grid"></div>
    </div>
    <div id="openWindow"></div>
</div>

<script type="text/javascript">
    Hap.initEnterQuery('#query-form', viewModel.query);
    var BaseUrl = _basePath;
    dataSource = new kendo.data.DataSource({
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
                id: "${columnsInfoHeader[0].tableColumnsName}",
                fields: {}
            }
        }
    });

    $("#grid").kendoGrid({
        dataSource: dataSource,
        resizable: true,
        scrollable: true,
        navigatable: false,
        sortable: true,
        selectable: 'multiple, rowbox',
        dataBound: function () {
            if (parent.autoResizeIframe) {
                parent.autoResizeIframe('${'$'}{RequestParameters.functionCode!}')
            }
        },
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
                    return Hap.createAnchor('${'<@spring.message "hap.details"/>'}', openWindow, item.${headerRelationColumn});
                }
            }
        ],
        editable: true
    }).data("kendoGrid");

    function openWindow(param) {
        var url = BaseUrl + '${lineUrl}';
        if (param) {
            url = url + '?${headerRelationColumn}=' + param;
        }
        var editWin = Hap.createWindow('#openWindow', {
            width: '90%',
            height: 745,
            title: 'Line',
            url: url
        })
        if (parent.autoResizeIframe) {
            parent.autoResizeIframe('${'$'}{RequestParameters.functionCode!}', 870, function () {
                editWin.center().open();
            })
        } else {
            editWin.center().open();
        }
    }

</script>
${'</body>'}
${'</html>'}