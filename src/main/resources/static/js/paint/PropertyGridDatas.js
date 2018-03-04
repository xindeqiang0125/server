var polygonDatas = {
    "total": 8,
    "rows": [
        {"name": "id", "value": "", "group": "ID Settings"},
        {"name": "startX", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "startY", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "width", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "height", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "lineWidth", "value": "", "group": "Style", "editor": "numberbox"},
        {"name": "lineColor", "value": "", "group": "Style", "editor": "text"},
        {"name": "fillColor", "value": "", "group": "Style", "editor": "text"}
    ]
};

function setPolygonDatas(shape) {
    for (var i = 0, len = polygonDatas.rows.length; i < len; i++) {
        var obj = polygonDatas.rows[i];
        obj.value = shape[obj.name];
    }
    $('#cspaint_propertygrid').propertygrid({
        data: polygonDatas,
        onAfterEdit: function (index, row, changes) {
            var value = changes.value;
            if (value === null) return;
            switch (index) {
                case 1:
                    shape.move(value - shape.startX, 0);
                    break;
                case 2:
                    shape.move(0, value - shape.startY);
                    break;
                case 3:
                    shape.reSize({x: shape.startX, y: shape.startY}, value / shape.width, 1);
                    break;
                case 4:
                    shape.reSize({x: shape.startX, y: shape.startY}, 1, value / shape.height);
                    break;
                case 5:
                    shape.setStyle(value, shape.lineColor, shape.fillColor);
                    break;
                case 6:
                    shape.setStyle(shape.lineWidth, value, shape.fillColor);
                    break;
                case 7:
                    shape.setStyle(shape.lineWidth, shape.lineColor, value);
                    break;
            }
            screen.reDraw();
        }
    })
}

var rectDatas = {
    "total": 10,
    "rows": [
        {"name": "id", "value": "", "group": "ID Settings"},
        {"name": "startX", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "startY", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "width", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "height", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "lineWidth", "value": "", "group": "Style", "editor": "numberbox"},
        {"name": "lineColor", "value": "", "group": "Style", "editor": "text"},
        {"name": "fillColor", "value": "", "group": "Style", "editor": "text"},
        {"name": "shapeWidth", "value": "", "group": "Style", "editor": "numberbox"},
        {"name": "shapeHeight", "value": "", "group": "Style", "editor": "numberbox"}
    ]
};

function setRectDatas(shape) {
    for (var i = 0, len = rectDatas.rows.length; i < len; i++) {
        var obj = rectDatas.rows[i];
        obj.value = shape[obj.name];
    }
    $('#cspaint_propertygrid').propertygrid({
        data: rectDatas,
        onAfterEdit: function (index, row, changes) {
            var value = changes.value;
            if (value === null) return;
            switch (index) {
                case 1:
                    shape.move(value - shape.startX, 0);
                    break;
                case 2:
                    shape.move(0, value - shape.startY);
                    break;
                case 3:
                    shape.reSize({x: shape.startX, y: shape.startY}, value / shape.width, 1);
                    break;
                case 4:
                    shape.reSize({x: shape.startX, y: shape.startY}, 1, value / shape.height);
                    break;
                case 5:
                    shape.setStyle(value, shape.lineColor, shape.fillColor);
                    break;
                case 6:
                    shape.setStyle(shape.lineWidth, value, shape.fillColor);
                    break;
                case 7:
                    shape.setStyle(shape.lineWidth, shape.lineColor, value);
                    break;
                case 8:
                    shape.setShapeSize(value, shape.shapeHeight);
                    break;
                case 9:
                    shape.setShapeSize(shape.shapeWidth, value);
                    break;
            }
            screen.reDraw();
        }
    })
}

var circleDatas = {
    "total": 10,
    "rows": [
        {"name": "id", "value": "", "group": "ID Settings"},
        {"name": "startX", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "startY", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "width", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "height", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "lineWidth", "value": "", "group": "Style", "editor": "numberbox"},
        {"name": "lineColor", "value": "", "group": "Style", "editor": "text"},
        {"name": "fillColor", "value": "", "group": "Style", "editor": "text"},
        {"name": "a", "value": "", "group": "Style", "editor": "numberbox"},
        {"name": "b", "value": "", "group": "Style", "editor": "numberbox"}
    ]
};

function setCircleDatas(shape) {
    for (var i = 0, len = circleDatas.rows.length; i < len; i++) {
        var obj = circleDatas.rows[i];
        obj.value = shape[obj.name];
    }
    $('#cspaint_propertygrid').propertygrid({
        data: circleDatas,
        onAfterEdit: function (index, row, changes) {
            var value = changes.value;
            if (value === null) return;
            switch (index) {
                case 1:
                    shape.move(value - shape.startX, 0);
                    break;
                case 2:
                    shape.move(0, value - shape.startY);
                    break;
                case 3:
                    shape.reSize({x: shape.startX, y: shape.startY}, value / shape.width, 1);
                    break;
                case 4:
                    shape.reSize({x: shape.startX, y: shape.startY}, 1, value / shape.height);
                    break;
                case 5:
                    shape.setStyle(value, shape.lineColor, shape.fillColor);
                    break;
                case 6:
                    shape.setStyle(shape.lineWidth, value, shape.fillColor);
                    break;
                case 7:
                    shape.setStyle(shape.lineWidth, shape.lineColor, value);
                    break;
                case 8:
                    shape.setAB(value, shape.b);
                    break;
                case 9:
                    shape.setAB(shape.a, value);
                    break;
            }
            screen.reDraw();
        }
    })
}

var lineDatas = {
    "total": 8,
    "rows": [
        {"name": "id", "value": "", "group": "ID Settings"},
        {"name": "startX", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "startY", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "width", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "height", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "lineWidth", "value": "", "group": "Style", "editor": "numberbox"},
        {"name": "lineColor", "value": "", "group": "Style", "editor": "text"},
        {"name": "lineLength", "value": "", "group": "Style", "editor": "numberbox"}
    ]
};

function setLineDatas(shape) {
    for (var i = 0, len = lineDatas.rows.length; i < len; i++) {
        var obj = lineDatas.rows[i];
        obj.value = shape[obj.name];
    }
    $('#cspaint_propertygrid').propertygrid({
        data: lineDatas,
        onAfterEdit: function (index, row, changes) {
            var value = changes.value;
            if (value === null) return;
            switch (index) {
                case 1:
                    shape.move(value - shape.startX, 0);
                    break;
                case 2:
                    shape.move(0, value - shape.startY);
                    break;
                case 3:
                    shape.reSize({x: shape.startX, y: shape.startY}, value / shape.width, 1);
                    break;
                case 4:
                    shape.reSize({x: shape.startX, y: shape.startY}, 1, value / shape.height);
                    break;
                case 5:
                    shape.setStyle(value, shape.lineColor);
                    break;
                case 6:
                    shape.setStyle(shape.lineWidth, value);
                    break;
                case 7:
                    shape.setLineLength(value);
                    break;
            }
            screen.reDraw();
        }
    })
}

var textDatas = {
    "total": 8,
    "rows": [
        {"name": "id", "value": "", "group": "ID Settings"},
        {"name": "startX", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "startY", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "width", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "height", "value": "", "group": "Position/Size", "editor": "numberbox"},
        {"name": "text", "value": "", "group": "Style", "editor": "textarea"},
        {"name": "fontSize", "value": "", "group": "Style", "editor": "numberbox"},
        {"name": "fontColor", "value": "", "group": "Style", "editor": "text"}
    ]
};

function setTextDatas(shape) {
    for (var i = 0, len = textDatas.rows.length; i < len; i++) {
        var obj = textDatas.rows[i];
        obj.value = shape[obj.name];
    }
    $('#cspaint_propertygrid').propertygrid({
        data: textDatas,
        onAfterEdit: function (index, row, changes) {
            var value = changes.value;
            if (value === null) return;
            switch (index) {
                case 1:
                    shape.move(value - shape.startX, 0);
                    break;
                case 2:
                    shape.move(0, value - shape.startY);
                    break;
                case 3:
                    shape.reSize({x: shape.startX, y: shape.startY}, value / shape.width, 1);
                    break;
                case 4:
                    shape.reSize({x: shape.startX, y: shape.startY}, 1, value / shape.height);
                    break;
                case 5:
                    shape.setText(value);
                    break;
                case 6:
                    shape.setStyle(shape.fontColor, value);
                    break;
                case 7:
                    shape.setStyle(value,shape.fontSize);
                    break;
            }
            screen.reDraw();
        }
    })
}

function changePropertyGridDatas(shape) {
    if (shape === null) return;
    switch (shape.type) {
        case 'rect':
            setRectDatas(shape);
            break;
        case 'polygon':
            setPolygonDatas(shape);
            break;
        case 'circle':
            setCircleDatas(shape);
            break;
        case 'line':
        case 'linearrow':
            setLineDatas(shape);
            break;
        case 'text':
            setTextDatas(shape);
            break;
    }

}