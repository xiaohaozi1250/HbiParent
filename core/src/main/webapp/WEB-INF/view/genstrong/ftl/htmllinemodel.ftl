${'<#include "../include/header.html"/>'}
<script type="text/javascript">
    var viewModel = kendo.observable({
        model: {}
    });
    var viewModel_line = Hap.createGridViewModel("#lineGrid");
</script>
<body>
<div id="page-content">
    <form class="form-horizontal">
        <div class="panel-body">
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
                               class="k-textbox" required>
                    </div>
                </div>
            </div>
            <#if (infos_index+1)%3 == 0>
            </div>
            </#if>
        </#list>
        </div>
    </form>
    <script>  kendo.bind($('#page-content'), viewModel);</script>
</div>
<div id="page-content-line">
    <div class="panel" id="itemQuery" style="padding: 0px;border:none;box-shadow: none;">
        <div class="pull-left" style="padding-left:10px;;padding-bottom:10px">
<span class="btn btn-primary k-grid-add" style="float:left;margin-right:5px" data-bind="click:create"><i
        class="fa fa-plus-square" style="margin-right:3px;"></i>${'<@spring.message "hap.new"/>'}</span>
            <span data-bind="click:remove" class="btn btn-danger" style="float:left;margin-right:5px"><i
                    class="fa fa-trash-o"
                    style="margin-right:3px;"></i>${'<@spring.message "hap.delete"/>'}</span>
        </div>

        <div class="pull-right" style="padding-right:20px;">
        <#list columnsInfoLine as infos>
            <#if infos.tableColumnsName?ends_with("Code")||infos.tableColumnsName?ends_with("Name")||infos.tableColumnsName?ends_with("Type")||infos.tableColumnsName?ends_with("Number")>
                <input placeholder='${'<@spring.message '}"${lineDtoName}.${infos.tableColumnsName}"/>' type="text"
                       style="float:left;width:200px;margin-right:5px;"
                       data-bind="value:model.${infos.tableColumnsName}"
                       class="k-textbox">
            </#if>
        </#list>
        <#list columnsInfoLine as infos>
            <#if infos.tableColumnsName?ends_with("Code")||infos.tableColumnsName?ends_with("Name")||infos.tableColumnsName?ends_with("Type")||infos.tableColumnsName?ends_with("Number")>
                <span class="btn btn-primary" style="float:left;margin-right:5px;" data-bind="click:query"
                      type="submit"><i
                        class="fa fa-search" style="margin-right:3px;"></i>${'<@spring.message "hap.query"/>'}</span>
                <span class="btn btn-default" type="button" data-bind="click:reset"><i class="fa fa-undo"
                                                                                       style="margin-right:3px;"></i>${'<@spring.message "hap.reset"/>'}</span>
            </#if>
        </#list>
        </div>
    </div>
    <script>kendo.bind($('#page-content-line'), viewModel_line);</script>
    <div style="clear: both;">
        <div id="lineGrid"></div>
    </div>
</div>
<div class="text-right k-window-toolbar" id="gridToolbar" style="padding-right:34px;">
             <span class="btn btn-success" style="margin-right: 10px;"
                   onclick="saveHeader()">${'<@spring.message "hap.save"/>'}</span>
    <span class="btn btn-default" onclick="closeLineWindow()" data-hotkey="hotkey_cancel"
          type="button">${'<@spring.message "hap.cancel"/>'}</span>
</div>
<script>kendo.bind($('#gridToolbar'), viewModel);</script>

<script type="text/javascript">
    Hap.initEnterQuery('#lineQuery', viewModel_line.query);
    var BaseUrl = _basePath;
    var ${columnsInfoHeader[0].tableColumnsName} =
    '${'$'}{RequestParameters.${columnsInfoHeader[0].tableColumnsName}!""}';
    if (!$.isEmpty(${columnsInfoHeader[0].tableColumnsName})) {
        $.ajax({
            url: BaseUrl + '${headerQueryUrl}?${columnsInfoHeader[0].tableColumnsName}=' + ${columnsInfoHeader[0].tableColumnsName},
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                var data = result.rows[0];
                for (var i in data) {
                    viewModel.model.set(i, data[i]);
                }
                console.log(viewModel.model);
            }
        });
    }

    lineDataSource = new kendo.data.DataSource({
        transport: {
            read: {
                url: BaseUrl + "${lineQueryUrl}?${columnsInfoHeader[0].tableColumnsName}=" + ${columnsInfoHeader[0].tableColumnsName},
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
                    var datas = Hap.prepareSubmitParameter(options, type);
                    for (var i in datas) {
                        var data = datas[i];
                    }
                    return kendo.stringify(datas);
                } else if (type === "read") {
                    var p = Hap.prepareQueryParameter(viewModel_line.model.toJSON(), options);
                    return p;
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
                id: "${columnsInfoLine[0].tableColumnsName}",
                fields: {}
            }
        }
    });

    var unitPrice;
    var linegrid = $("#lineGrid").kendoGrid({
        dataSource: lineDataSource,
        resizable: true,
        scrollable: true,
        navigatable: false,
        rownumber: true,
        autoBind: false,
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
        <#list columnsInfoLine as infos>
            {
                field: "${infos.tableColumnsName}",
                title: '${'<@spring.message'} "${lineDtoName?lower_case}.${infos.tableColumnsName?lower_case}"/>',
                width: 120
            },
        </#list>],
        editable: true
    }).data('kendoGrid');

    if (!$.isEmpty(${columnsInfoHeader[0].tableColumnsName})) {
        lineDataSource.page(1);
    }
    function closeLineWindow() {
        window.parent.$('#openWindow').data("kendoWindow").close();
    }

    function saveHeader() {
        if ($.isEmpty(${columnsInfoHeader[0].tableColumnsName})) {
            viewModel.model.__status = "add";
        } else {
            viewModel.model.__status = "update";
        }

        Hap.submitForm({
            url: BaseUrl + '${headerSubmitUrl}',
            formModel: viewModel.model,
            grid: {
                "lines": $("#lineGrid"),
            },
            success: function (datas) {
                window.parent.viewModel.refresh();
                closeLineWindow();
            }
        });
    }

</script>
${'</body>'}
${'</html>'}