var mouseDownX, mouseDownY;
var canvas = document.getElementById("cspaint_canvas");
var canvas2 = document.getElementById("cspaint_canvas_select");
var cxt = canvas.getContext("2d");
var cxtSel = canvas2.getContext("2d");
var shapeType;
var selectedShapeGroup;
var clipboard;
var moveOrResize = '';
var noOnClick = false;
/**
 * ShapeId生成器
 * @type {{id: number, next: shapeId.next}}
 */
var shapeId = {
    id: 0,
    next: function () {
        this.id++;
        return this.id;
    }
};
var cfgId = {
    id: 0,
    next: function () {
        this.id++;
        return this.id;
    }
};

function deleteRelatedCfgs(shape) {
    var cfgs = getCfgsByShapeId(shape.id);
    for (var i = 0, len = cfgs.length; i < len; i++) {
        var obj = cfgs[i];
        screen.deleteConfiguration(obj);
    }
    if (shape.type == 'group') {
        for (var i = 0, len = shape.shapeList.length; i < len; i++) {
            deleteRelatedCfgs(shape.shapeList[i]);
        }
    }
}

/**
 * screen静态对象
 * @type {{shapeList: Array, configurationList: Array, addShape: screen.addShape, deleteShape: screen.deleteShape, addConfiguration: screen.addConfiguration, deleteConfiguration: screen.deleteConfiguration, clear: screen.clear, toBottom: screen.toBottom, toTop: screen.toTop, toUpper: screen.toUpper, toLower: screen.toLower, contains: screen.contains, reDraw: screen.reDraw}}
 */
var screen = {
    shapeList: [],
    configurationList: [],
    addShape: function (shape) {
        this.shapeList.push(shape);
    },
    deleteShape: function (shape) {
        var start = $.inArray(shape, this.shapeList);
        if (-1 != start) this.shapeList.splice(start, 1);
        //删掉相关联的组态信息
        deleteRelatedCfgs(shape);
    },
    addConfiguration: function (configuration) {
        this.configurationList.push(configuration);
    },
    deleteConfiguration: function (configuration) {
        var start = $.inArray(configuration, this.configurationList);
        if (-1 != start) this.configurationList.splice(start, 1);
    },
    clear: function () {
        this.shapeList.splice(0, this.shapeList.length);
        this.configurationList.splice(0, this.configurationList.length);
    },
    toBottom: function (shape) {
        this.deleteShape(shape);
        this.shapeList.unshift(shape);
    },
    toTop: function (shape) {
        this.deleteShape(shape);
        this.addShape(shape);
    },
    toUpper: function (shape) {
        var number = $.inArray(shape, this.shapeList);
        if ((this.shapeList.length - 1) > number) {
            var value;
            value = this.shapeList[number];
            this.shapeList[number] = this.shapeList[number + 1];
            this.shapeList[number + 1] = value;
        }
    },
    toLower: function (shape) {
        var number = $.inArray(shape, this.shapeList);
        if (0 < number) {
            var value;
            value = this.shapeList[number];
            this.shapeList[number] = this.shapeList[number - 1];
            this.shapeList[number - 1] = value;
        }
    },
    contains: function (shape) {
        var inArray = $.inArray(shape, this.shapeList);
        return -1 != inArray;
    },
    reDraw: function () {
        cxt.clearRect(0, 0, canvas.width, canvas.height);
        for (var i = 0; this.shapeList.length > i; i++) {
            this.shapeList[i].draw();
        }
    }
};

function invertColor(color) {
    if (color.substr(0, 1) == '#') {
        var r = parseInt(color.substr(1, 2), 16);
        var g = parseInt(color.substr(3, 2), 16);
        var b = parseInt(color.substr(5, 2), 16);
        r = (255 - r).toString(16);
        g = (255 - g).toString(16);
        b = (255 - b).toString(16);
        if (r.length == 1) r = '0' + r;
        if (g.length == 1) g = '0' + g;
        if (b.length == 1) b = '0' + b;
        return '#' + r + g + b;
    } else if (color.substr(0, 4).toLowerCase() == 'rgba') {
        color = color.substring(color.indexOf('(') + 1, color.indexOf(')'));
        var split = color.split(',');
        split[0] = 255 - parseInt(split[0]);
        split[1] = 255 - parseInt(split[1]);
        split[2] = 255 - parseInt(split[2]);
        split[3] = 1;
        var r = 'rgba(' + split[0] + ',' + split[1] + ',' + split[2] + ',' + split[3] + ')';
        return r;
    }

};
//<editor-fold desc="初始化控件">
$('#cspaint_propertygrid').propertygrid({
    showGroup: true,
    scrollbarSize: 0,
    fit: true,
    rowStyler: function (index, row) {
        if (row.name.indexOf('Color') != -1) {
            return 'background-color:' + row.value + ';color:' + invertColor(row.value);
        }
    },
    editorHeight: 90,
    data: {
        "total": 4, "rows": [
            {"name": "ID", "value": "Bill Smith", "group": "ID Settings", "editor": "text"},
            {"name": "Address", "value": "", "group": "ID Settings", "editor": "text"},
            {"name": "SSN", "value": "123-456-7890", "group": "ID Settings", "editor": "text"},
            {
                "name": "Email", "value": "bill@gmail.com", "group": "Marketing Settings", "editor": {
                "type": "validatebox",
                "options": {
                    "validType": "email"
                }
            }
            }
        ]
    }
});
$('#canvas_size_dialog').dialog({
    modal: true,
    title: '设置文档大小',
    closed: true,
    buttons: [{
        text: '确定',
        iconCls: 'icon-ok',
        handler: function () {
            setCanvasSize();
            $('#canvas_size_dialog').dialog('close');
        }
    }, {
        text: '取消',
        iconCls: 'icon-cancel',
        handler: function () {
            $('#canvas_size_dialog').dialog('close');
        }
    }]
});
$('#canvas_width').numberbox({
    label: '文档宽度：',
    value: '500',
    labelPosition: 'top'
});
$('#canvas_height').numberbox({
    label: '文档高度：',
    value: '400',
    labelPosition: 'top'
});

$('#context_menu').menu({});

//</editor-fold>

function x(id) {
    return document.getElementById(id);
}

function initPage() {
    var bodyHeight = document.body.offsetHeight;
    var bodyWidth = document.body.offsetWidth;
    var menuHeight = x('cspaint_menu').offsetHeight;
    var propertygridContainerWidth = 300;
    x('propertygrid_container').style.height = (bodyHeight - menuHeight) + 'px';
    x('propertygrid_container').style.width = propertygridContainerWidth + 'px';
    x('canvas_container').style.height = (bodyHeight - menuHeight) + 'px';
    x('canvas_container').style.width = (bodyWidth - propertygridContainerWidth - 20) + 'px';
    $('#cspaint_propertygrid').propertygrid({fit: true});
    $('#cspaint_canvas_panel').panel({fit: true});
}

function openCanvasSizeDialog() {
    $('#canvas_size_dialog').dialog('open');
}

function setCanvasSize() {
    x('cspaint_canvas').width = $('#canvas_width').numberbox('getValue');
    x('cspaint_canvas').height = $('#canvas_height').numberbox('getValue');
    x('cspaint_canvas_select').width = $('#canvas_width').numberbox('getValue');
    x('cspaint_canvas_select').height = $('#canvas_height').numberbox('getValue');
    // $('#cspaint_canvas').height($('#canvas_height').numberbox('getValue'));
    // $('#cspaint_canvas').width($('#canvas_width').numberbox('getValue'));
    screen.reDraw();
}

function isPointInRect(ponitX, pointY, RectX, RectY, RectWidth, RectHeight) {
    return ponitX > RectX && ponitX < (RectX + RectWidth) && pointY > RectY && pointY < (RectY + RectHeight);
}

function getClickedShape(x, y) {
    for (var i = screen.shapeList.length - 1; 0 <= i; i--) {
        if (screen.shapeList[i].startX < x &&
            (screen.shapeList[i].startX + screen.shapeList[i].width) > x &&
            screen.shapeList[i].startY < y &&
            (screen.shapeList[i].startY + screen.shapeList[i].height) > y
        ) {
            return screen.shapeList[i];
        }
    }
    return null;
};


x('cspaint_canvas_select').onclick = function (e) {
    x('cspaint_canvas').onclick(e);
};
x('cspaint_canvas_select').onmousedown = function (e) {
    x('cspaint_canvas').onmousedown(e);
};
x('cspaint_canvas_select').onmousemove = function (e) {
    x('cspaint_canvas').onmousemove(e);
};
x('cspaint_canvas_select').onmouseup = function (e) {
    x('cspaint_canvas').onmouseup(e);
};
x('cspaint_canvas_select').onmouseout = function (e) {
    this.onmouseup(e)
};
x('cspaint_canvas_select').oncontextmenu = function (e) {
    x('cspaint_canvas').oncontextmenu(e)
};
x('cspaint_canvas_select').ondblclick = function (e) {
    x('cspaint_canvas').ondblclick(e)
};

function isReSizeSE(e) {
    return isPointInRect(e.offsetX, e.offsetY,
        selectedShapeGroup.startX + selectedShapeGroup.width - 5,
        selectedShapeGroup.startY + selectedShapeGroup.height - 5,
        10, 10);
}

function isReSizeNW(e) {
    return isPointInRect(e.offsetX, e.offsetY,
        selectedShapeGroup.startX - 5,
        selectedShapeGroup.startY - 5,
        10, 10);
}

function isReSizeNE(e) {
    return isPointInRect(e.offsetX, e.offsetY,
        selectedShapeGroup.startX + selectedShapeGroup.width - 5,
        selectedShapeGroup.startY - 5,
        10, 10);
}

function isReSizeSW(e) {
    return isPointInRect(e.offsetX, e.offsetY,
        selectedShapeGroup.startX - 5,
        selectedShapeGroup.startY + selectedShapeGroup.height - 5,
        10, 10);
}

function isReSizeS(e) {
    return isPointInRect(e.offsetX, e.offsetY,
        selectedShapeGroup.startX + 5,
        selectedShapeGroup.startY + selectedShapeGroup.height - 5,
        selectedShapeGroup.width - 10, 10);
}

function isReSizeN(e) {
    return isPointInRect(e.offsetX, e.offsetY,
        selectedShapeGroup.startX + 5,
        selectedShapeGroup.startY - 5,
        selectedShapeGroup.width - 10, 10);
}

function isReSizeE(e) {
    return isPointInRect(e.offsetX, e.offsetY,
        selectedShapeGroup.startX + selectedShapeGroup.width - 5,
        selectedShapeGroup.startY - 5,
        10, selectedShapeGroup.height - 10);
}

function isReSizeW(e) {
    return isPointInRect(e.offsetX, e.offsetY,
        selectedShapeGroup.startX - 5,
        selectedShapeGroup.startY + 5,
        10, selectedShapeGroup.height - 10);
}

function isMove(e) {
    return isPointInRect(e.offsetX, e.offsetY, selectedShapeGroup.startX + 5, selectedShapeGroup.startY + 5, selectedShapeGroup.width - 10, selectedShapeGroup.height - 10);
}

x('cspaint_canvas').onmousedown = function (e) {
    if (1 == e.buttons) {
        if (isMove(e)) moveOrResize = 'move';
        else if (isReSizeNW(e)) moveOrResize = 'nw-resize';
        else if (isReSizeSW(e)) moveOrResize = 'sw-resize';
        else if (isReSizeN(e)) moveOrResize = 'n-resize';
        else if (isReSizeW(e)) moveOrResize = 'w-resize';
        else if (isReSizeSE(e)) moveOrResize = 'se-resize';
        else if (isReSizeNE(e)) moveOrResize = 'ne-resize';
        else if (isReSizeS(e)) moveOrResize = 's-resize';
        else if (isReSizeE(e)) moveOrResize = 'e-resize';
        else moveOrResize = 'selectByMouseDrag';

        mouseDownX = e.offsetX;
        mouseDownY = e.offsetY;

    }
};
x('cspaint_canvas').onmousemove = function (e) {
    if (isMove(e)) selectedShapeGroup.onMouseMove(e);
    else if (isReSizeNW(e)) selectedShapeGroup.onMouseMoveInBorder(e, 'nw-resize');
    else if (isReSizeSW(e)) selectedShapeGroup.onMouseMoveInBorder(e, 'sw-resize');
    else if (isReSizeN(e)) selectedShapeGroup.onMouseMoveInBorder(e, 'n-resize');
    else if (isReSizeW(e)) selectedShapeGroup.onMouseMoveInBorder(e, 'w-resize');
    else if (isReSizeSE(e)) selectedShapeGroup.onMouseMoveInBorder(e, 'se-resize');
    else if (isReSizeNE(e)) selectedShapeGroup.onMouseMoveInBorder(e, 'ne-resize');
    else if (isReSizeS(e)) selectedShapeGroup.onMouseMoveInBorder(e, 's-resize');
    else if (isReSizeE(e)) selectedShapeGroup.onMouseMoveInBorder(e, 'e-resize');
    else selectedShapeGroup.onMouseOut(e);

    if (1 == e.buttons) {
        if ('move' == moveOrResize) {
            selectedShapeGroup.move(e.offsetX - mouseDownX, e.offsetY - mouseDownY);
            mouseDownX = e.offsetX;
            mouseDownY = e.offsetY;
            screen.reDraw();
        } else if ('selectByMouseDrag' == moveOrResize && e.offsetX != mouseDownX) {
            cxtSel.clearRect(0, 0, canvas2.width, canvas2.height);
            cxtSel.save();
            cxtSel.strokeStyle = 'rgba(0,0,0,1)';
            cxtSel.lineWidth = 2;
            cxtSel.lineCap = 'square';
            cxtSel.lineJoin = 'square';
            cxtSel.strokeRect(mouseDownX, mouseDownY, e.offsetX - mouseDownX, e.offsetY - mouseDownY);
            cxtSel.restore();
            noOnClick = true;
        } else if ('' != moveOrResize) {
            var zero = {x: 0, y: 0};
            var scaleX = 1, scaleY = 1;
            if ('nw-resize' == moveOrResize) {
                zero.x = selectedShapeGroup.startX + selectedShapeGroup.width;
                zero.y = selectedShapeGroup.startY + selectedShapeGroup.height;
                scaleX = (e.offsetX - zero.x) / (mouseDownX - zero.x);
                scaleY = (e.offsetY - zero.y) / (mouseDownY - zero.y);
            } else if ('sw-resize' == moveOrResize) {
                zero.x = selectedShapeGroup.startX + selectedShapeGroup.width;
                zero.y = selectedShapeGroup.startY;
                scaleX = (e.offsetX - zero.x) / (mouseDownX - zero.x);
                scaleY = (e.offsetY - zero.y) / (mouseDownY - zero.y);
            } else if ('n-resize' == moveOrResize) {
                zero.y = selectedShapeGroup.startY + selectedShapeGroup.height;
                scaleY = (e.offsetY - zero.y) / (mouseDownY - zero.y);
            } else if ('w-resize' == moveOrResize) {
                zero.x = selectedShapeGroup.startX + selectedShapeGroup.width;
                scaleX = (e.offsetX - zero.x) / (mouseDownX - zero.x);
            } else if ('se-resize' == moveOrResize) {
                zero.x = selectedShapeGroup.startX;
                zero.y = selectedShapeGroup.startY;
                scaleX = (e.offsetX - zero.x) / (mouseDownX - zero.x);
                scaleY = (e.offsetY - zero.y) / (mouseDownY - zero.y);
            } else if ('ne-resize' == moveOrResize) {
                zero.x = selectedShapeGroup.startX;
                zero.y = selectedShapeGroup.startY + selectedShapeGroup.height;
                scaleX = (e.offsetX - zero.x) / (mouseDownX - zero.x);
                scaleY = (e.offsetY - zero.y) / (mouseDownY - zero.y);
            } else if ('s-resize' == moveOrResize) {
                zero.y = selectedShapeGroup.startY;
                scaleY = (e.offsetY - zero.y) / (mouseDownY - zero.y);
            } else if ('e-resize' == moveOrResize) {
                zero.x = selectedShapeGroup.startX;
                scaleX = (e.offsetX - zero.x) / (mouseDownX - zero.x);
            }
            if (e.ctrlKey &&
                (moveOrResize == 'nw-resize' || moveOrResize == 'sw-resize' || moveOrResize == 'ne-resize' || moveOrResize == 'se-resize')
            ) {
                scaleX = scaleX / Math.abs(scaleX) * (Math.abs(scaleX) + Math.abs(scaleY)) / 2;
                scaleY = scaleX;
            }
            selectedShapeGroup.reSize(zero, scaleX, scaleY);
            mouseDownX = e.offsetX;
            mouseDownY = e.offsetY;
            screen.reDraw();
        }
    }
};
x('cspaint_canvas').onmouseup = function (e) {
    if (noOnClick) {
        selectByMouseDrag(mouseDownX, mouseDownY, e.offsetX - mouseDownX, e.offsetY - mouseDownY);
    }
    moveOrResize = '';
    selectedShapeGroup.correctSize();
};
x('cspaint_canvas').onmouseout = function (e) {
    this.onmouseup(e)
};
x('cspaint_canvas').onclick = function (e) {
    if (!noOnClick) {
        var clickedShape = getClickedShape(e.offsetX, e.offsetY);
        if (clickedShape == null) {
            if (!e.ctrlKey) {
                selectedShapeGroup.deleteAll();
            }
        } else {
            clickedShape.onClick(e);
        }
        selectedShapeGroup.drawBorder();
    }
    noOnClick = false;
};
x('cspaint_canvas').oncontextmenu = function (e) {
    e.preventDefault();
    $('#context_menu').menu('show', {left: e.clientX, top: e.clientY});
};
x('cspaint_canvas').ondblclick = function (e) {
    var clickedShape = getClickedShape(e.offsetX, e.offsetY);
    if (clickedShape != null) {
        clickedShape.onDoubleClick(e);
    }
};

function test() {
    screen.clear();
    var polygon1 = new Polygon();
    polygon1.setStyle(4, '#0000ff', '#00ff00').setPoints([{x: 50, y: 50}, {x: 80, y: 50}, {x: 80, y: 80}, {
        x: 50,
        y: 80
    }])
    ;
    var polygon2 = new Polygon();
    polygon2.setStyle(2, '#00aaff', '#aaff00').setPoints([{x: 100, y: 50}, {x: 140, y: 50}, {x: 140, y: 80}, {
        x: 100,
        y: 80
    }])
    ;
    var polygon3 = new Polygon();
    polygon3.setStyle(6, '#ffffff', '#000000').setPoints([{x: 50, y: 100}, {x: 80, y: 100}, {x: 80, y: 140}, {
        x: 50,
        y: 140
    }])
    ;
    var polygon4 = new Polygon();
    polygon4.setStyle(8, '#ff00ff', '#ffff00').setPoints([{x: 150, y: 50}, {x: 210, y: 50}, {x: 210, y: 80}, {
        x: 150,
        y: 80
    }])
    ;
    var polygon5 = new Polygon();
    polygon5.setStyle(10, '#0f0fff', '#f0ff0f').setPoints([{x: 50, y: 150}, {x: 80, y: 150}, {x: 80, y: 210}, {
        x: 50,
        y: 210
    }])
    ;
    var shapeGroup = new ShapeGroup();
    var shapeGroup2 = new ShapeGroup();
    shapeGroup.add(polygon3);
    shapeGroup2.add(polygon4);
    shapeGroup2.add(shapeGroup);

    screen.addShape(shapeGroup2);
    // screen.deleteShape(shapeGroup2);
    screen.addShape(polygon5);
    screen.addShape(polygon1);
    screen.addShape(polygon2);

    screen.reDraw();
    // alert(rect.id);
}


$(function () {
    selectedShapeGroup = new ShapeGroup();
    initPage();
    setCanvasSize();

    test();
});




