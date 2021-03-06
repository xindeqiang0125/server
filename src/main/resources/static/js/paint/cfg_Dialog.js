$('#cfg_dialog').dialog({
    title: '图形组态',
    width: 600,
    closed: true,
    cache: false,
    modal: true
});
$('#cfg_datagrid').datagrid({
    fitColumns: true,
    singleSelect: true,
    rownumbers: true,
    striped: true,
    fit: true,
    columns: [[
        {field: 'id', title: 'ID', width: 10, align: 'center'},
        {field: 'itemId', title: '测点号', width: 20, align: 'center'},
        {field: 'detail', title: '描述', width: 100, align: 'center'}
    ]],
    onDblClickRow: function (index, row) {
        screen.deleteConfiguration(row);
        $('#cfg_datagrid').datagrid('deleteRow',index);
    }
});
$('#searchItem_dialog').dialog({
    title: '查询ITEM',
    width: 600,
    closed: true,
    cache: false,
    modal: true
});
$('#searchItem_datagrid').datagrid({
    url: '/manage/getitemsbypage',
    fitColumns: true,
    singleSelect: true,
    rownumbers: true,
    striped: true,
    fit: true,
    columns: [[
        {field: 'id', title: 'ID', width: 10, align: 'center'},
        {field: 'itemName', title: '名称', width: 100, align: 'center'},
        {field: 'notes', title: '注释', width: 100, align: 'center'}
    ]],
    onDblClickRow: function (index, row) {
        $('#searchItem_dialog').dialog('close');
        $('#cfg_point').val(row.id);
    }
});
$('#searchItem_itemName').textbox({
    width: 200,
    onClickButton: function () {
        $('#searchItem_datagrid').datagrid({
            url: '/manage/getitemsbypage',
            queryParams: {
                groupId: $("#searchItem_groupId").combobox('getValue'),
                itemName: $("#searchItem_itemName").textbox('getValue'),
                rows: 20
            }
        })
    },
    onChange: function () {
        $('#searchItem_datagrid').datagrid({
            url: '/manage/getitemsbypage',
            queryParams: {
                groupId: $("#searchItem_groupId").combobox('getValue'),
                itemName: $("#searchItem_itemName").textbox('getValue'),
                rows: 20
            }
        })
    }
});
$('#cfg_action').combobox({
    valueField: 'value',
    textField: 'text',
    width: 150,
    height: 30,
    label: '动作',
    labelWidth: 30,
    panelHeight: 'auto',
    panelMaxHeight: 100,
    editable: false,
    data: [{
        text: 'java',
        value: 'Java'
    }, {
        text: 'perl',
        value: 'Perl'
    }, {
        text: 'ruby',
        value: 'Ruby'
    }]
});
$('#cfg_condition').textbox({
    width: 250,
    height: 60,
    label: '条件',
    labelWidth: 30,
    multiline: true
});

document.getElementById('cfg_point').ondblclick = function (e) {
    $('#searchItem_dialog').dialog('open');
};

var cfgActions = {
    group: [
        {text: 'position.x', value: 'position.x'},
        {text: 'position.y', value: 'position.y'},
        {text: 'hide', value: 'hide'},
        {text: 'twinkle', value: 'twinkle'}
    ],
    image: [
        {text: 'position.x', value: 'position.x'},
        {text: 'position.y', value: 'position.y'},
        {text: 'hide', value: 'hide'},
        {text: 'twinkle', value: 'twinkle'},
        {text: 'angle', value: 'angle'}
    ],
    rect: [
        {text: 'position.x', value: 'position.x'},
        {text: 'position.y', value: 'position.y'},
        {text: 'width.left', value: 'width.left'},
        {text: 'width.right', value: 'width.right'},
        {text: 'height.top', value: 'height.top'},
        {text: 'height.bottom', value: 'height.bottom'},
        {text: 'lineColor', value: 'lineColor'},
        {text: 'fillColor', value: 'fillColor'},
        {text: 'hide', value: 'hide'},
        {text: 'twinkle', value: 'twinkle'}
    ],
    circle: [
        {text: 'position.x', value: 'position.x'},
        {text: 'position.y', value: 'position.y'},
        {text: 'a', value: 'a'},
        {text: 'b', value: 'b'},
        {text: 'radius', value: 'radius'},
        {text: 'angle', value: 'angle'},
        {text: 'lineColor', value: 'lineColor'},
        {text: 'fillColor', value: 'fillColor'},
        {text: 'hide', value: 'hide'},
        {text: 'twinkle', value: 'twinkle'}
    ],
    polygon: [
        {text: 'position.x', value: 'position.x'},
        {text: 'position.y', value: 'position.y'},
        {text: 'lineColor', value: 'lineColor'},
        {text: 'fillColor', value: 'fillColor'},
        {text: 'hide', value: 'hide'},
        {text: 'twinkle', value: 'twinkle'}
    ],
    line: [
        {text: 'position.x', value: 'position.x'},
        {text: 'position.y', value: 'position.y'},
        {text: 'start.x', value: 'start.x'},
        {text: 'start.y', value: 'start.y'},
        {text: 'end.x', value: 'end.x'},
        {text: 'end.y', value: 'end.y'},
        {text: 'lineColor', value: 'lineColor'},
        {text: 'lineLength.start', value: 'lineLength.start'},
        {text: 'lineLength.end', value: 'lineLength.end'},
        {text: 'hide', value: 'hide'},
        {text: 'twinkle', value: 'twinkle'}
    ],
    linearrow: [
        {text: 'position.x', value: 'position.x'},
        {text: 'position.y', value: 'position.y'},
        {text: 'start.x', value: 'start.x'},
        {text: 'start.y', value: 'start.y'},
        {text: 'end.x', value: 'end.x'},
        {text: 'end.y', value: 'end.y'},
        {text: 'lineColor', value: 'lineColor'},
        {text: 'lineLength.start', value: 'lineLength.start'},
        {text: 'lineLength.end', value: 'lineLength.end'},
        {text: 'hide', value: 'hide'},
        {text: 'twinkle', value: 'twinkle'}
    ],
    text: [
        {text: 'position.x', value: 'position.x'},
        {text: 'position.y', value: 'position.y'},
        {text: 'text', value: 'text'},
        {text: 'fontColor', value: 'fontColor'},
        {text: 'fontSize', value: 'fontSize'},
        {text: 'hide', value: 'hide'},
        {text: 'twinkle', value: 'twinkle'}
    ]
};

function initCfgDialog(shape) {
    $('#cfg_shapeId').text(shape.id);
    $('#cfg_shapeType').text(shape.type);
    $('#cfg_action').combobox({
        data: cfgActions[shape.type]
    });
    $('#cfg_datagrid').datagrid('loadData',getCfgsByShapeId(shape.id));
}

function openCfgDialog(shape) {
    initCfgDialog(shape);
    $('#cfg_dialog').dialog('open');
}

function closeCfgDialog(shape) {
    $('#cfg_dialog').dialog('close');
}

function addCfg() {
    var shapeId = $('#cfg_shapeId').text();
    var point = $('#cfg_point').val();
    var action = $('#cfg_action').val();
    var condition = $('#cfg_condition').val();
    if (shapeId != '' && point != '' && action != '') {
        var configuration = new Configuration(shapeId, point);
        configuration.setCfg(action, condition);
        screen.addConfiguration(configuration);
        $('#cfg_datagrid').datagrid('appendRow',configuration);
        // console.log(configuration);
    }

}