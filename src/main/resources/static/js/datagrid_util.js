//有2个按钮，增删，datagrid不带分页
function initDatagridPanel2(dg_selector, dg_url, dg_columns, dialog_selector, dialog_title, delete_url, form_selector, save_url) {
    dg_selector.datagrid({
        url: dg_url,
        columns: dg_columns,
        fitColumns: true,
        singleSelect: true,
        rownumbers: true,
        striped: true,
        fit: true,
        toolbar: [{
            iconCls: 'icon-add',
            handler: function () {
                dialog_selector.dialog('open');
            }
        }, '-', {
            iconCls: 'icon-remove',
            handler: function () {
                var rows = dg_selector.datagrid('getSelections');
                if (rows.length == 0)
                    alert('请选择1行');
                else {
                    if(confirm("确定要删除所选的"+rows.length+"条数据?")){
                        $.post(delete_url, {id: rows[0].id}, function (data) {
                            dg_selector.datagrid('reload');
                            showMessager(data);
                        })
                    }
                }
            }
        }]

    });
    dialog_selector.dialog({
        title: dialog_title, closed: true, cache: false, modal: true,
        buttons: [{
            text: '保存',
            iconCls: 'icon-save',
            handler: function () {
                form_selector.submit();
            }
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                dialog_selector.dialog('close');
            }
        }]
    });
    form_selector.form({
        url: save_url,
        iframe:false,
        success: function (data) {
            var res=JSON.parse(data);
            dialog_selector.dialog('close');
            dg_selector.datagrid('reload');
            showMessager(res);
        }
    });
}
//有三个按钮，增删改，datagrid带分页
function initDatagridPanel3(datagridSelector,
                            datagridGetUrl,
                            datagridColumns,
                            toolbarSelector,
                            addButtonSelector,
                            editButtonSelector,
                            deleteButtonSelector,
                            dialogSelector,
                            dialogTitle,
                            formSelector,
                            saveUrl,
                            deleteUrl,
                            dialogOpenDataInitFuc) {
    datagridSelector.datagrid({
        url: datagridGetUrl,
        columns: datagridColumns,
        idField:'id',
        fitColumns: true,
        ctrlSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 20,
        striped: true,
        fit: true,
        toolbar: toolbarSelector,
        onSelect:function (index, row) {

        }
    });
    dialogSelector.dialog({
        title: dialogTitle,
        closed: true,
        cache: false,
        modal: true,
        buttons: [{
            text: '保存',
            iconCls: 'icon-save',
            handler: function () {
                formSelector.submit();
            }
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                dialogSelector.dialog('close');
            }
        }]
    });
    formSelector.form({
        url: saveUrl,
        iframe:false,
        success: function (data) {
            var res=JSON.parse(data);
            dialogSelector.dialog('close');
            datagridSelector.datagrid('reload');
            showMessager(res);
        }
    });

    addButtonSelector.click(function () {
        var row={};
        dialogOpenDataInitFuc(row);
        dialogSelector.dialog('open');
    });
    editButtonSelector.click(function () {
        var rows=datagridSelector.datagrid('getSelections');
        if(rows.length!=1)
            alert('请选择1行');
        else {
            dialogOpenDataInitFuc(rows[0]);
            dialogSelector.dialog('open');
        }
    });
    deleteButtonSelector.click(function () {
        var rows=datagridSelector.datagrid('getSelections');
        if(rows.length==0)
            alert('请选择至少1行');
        else {
            var ids=new Array();
            if(confirm("确定要删除所选的"+rows.length+"条数据?")){
                for(var i=0;rows.length>i;i++){
                    ids[i]=rows[i].id;
                }
                $.ajax({
                    url:deleteUrl,
                    type:'post',
                    data:JSON.stringify(ids),
                    contentType:'application/json;charset=utf-8',
                    success:function (data) {
                        datagridSelector.datagrid('reload');
                        showMessager(data);
                    }
                })
            }
        }
    });
}
// data={
//     result:'',
//     msg:''
// }
function showMessager(data) {
    $.messager.show({
        title: data.result,
        msg:data.msg,
        timeout:2000,
        showType:'slide'
    });
}