function toBottom() {
    if (selectedShapeGroup.shapeList.length == 1) {
        screen.toBottom(selectedShapeGroup.shapeList[0]);
        screen.reDraw();
    }
}

function toTop() {
    if (selectedShapeGroup.shapeList.length == 1) {
        screen.toTop(selectedShapeGroup.shapeList[0]);
        screen.reDraw();
    }
}

function toUpper() {
    if (selectedShapeGroup.shapeList.length == 1) {
        screen.toUpper(selectedShapeGroup.shapeList[0]);
        screen.reDraw();
    }
}

function toLower() {
    if (selectedShapeGroup.shapeList.length == 1) {
        screen.toLower(selectedShapeGroup.shapeList[0]);
        screen.reDraw();
    }
}

function makeGroup() {
    if (selectedShapeGroup.shapeList.length > 1) {
        var newGroup = new ShapeGroup();

        for (var i = 0; i < screen.shapeList.length; i++) {
            var obj = screen.shapeList[i];
            if (selectedShapeGroup.contains(obj)) {
                newGroup.add(obj);
                screen.deleteShape(obj);
                i--;
            }
        }
        // for (var i = 0, len = selectedShapeGroup.shapeList.length; i < len; i++) {
        //     var obj = selectedShapeGroup.shapeList[i];
        //     newGroup.add(obj);
        //     screen.deleteShape(obj);
        // }
        selectedShapeGroup.deleteAll();
        screen.addShape(newGroup);
    }
}

function unMakeGroup() {
    if (selectedShapeGroup.shapeList.length == 1 && selectedShapeGroup.shapeList[0].type == 'group') {
        var group = selectedShapeGroup.shapeList[0];
        screen.deleteShape(group);
        var shapeList = group.shapeList;
        for (var i = 0, len = shapeList.length; i < len; i++) {
            screen.addShape(shapeList[i]);
        }
    }
}

function del() {
    if (selectedShapeGroup.shapeList.length > 0) {
        for (var i = 0, len = selectedShapeGroup.shapeList.length; i < len; i++) {
            var obj = selectedShapeGroup.shapeList[i];
            screen.deleteShape(obj);
        }
        selectedShapeGroup.deleteAll();
        screen.reDraw();
    }
}

function topAlign() {
    if (selectedShapeGroup.shapeList.length > 1) {
        var shapeList = selectedShapeGroup.shapeList;
        for (var i = 1, len = shapeList.length; i < len; i++) {
            var obj = shapeList[i];
            obj.move(0, shapeList[0].startY - obj.startY);
        }
        selectedShapeGroup.deleteAll();
        screen.reDraw();
    }
}

function bottomAlign() {
    if (selectedShapeGroup.shapeList.length > 1) {
        var shapeList = selectedShapeGroup.shapeList;
        for (var i = 1, len = shapeList.length; i < len; i++) {
            var obj = shapeList[i];
            obj.move(0, (shapeList[0].startY + shapeList[0].height) - (obj.startY + obj.height));
        }
        selectedShapeGroup.deleteAll();
        screen.reDraw();
    }
}

function rightAlign() {
    if (selectedShapeGroup.shapeList.length > 1) {
        var shapeList = selectedShapeGroup.shapeList;
        for (var i = 1, len = shapeList.length; i < len; i++) {
            var obj = shapeList[i];
            obj.move((shapeList[0].startX + shapeList[0].width) - (obj.startX + obj.width), 0);
        }
        selectedShapeGroup.deleteAll();
        screen.reDraw();
    }
}

function leftAlign() {
    if (selectedShapeGroup.shapeList.length > 1) {
        var shapeList = selectedShapeGroup.shapeList;
        for (var i = 1, len = shapeList.length; i < len; i++) {
            var obj = shapeList[i];
            obj.move(shapeList[0].startX - obj.startX, 0);
        }
        selectedShapeGroup.deleteAll();
        screen.reDraw();
    }
}

function hMirror() {
    if (!selectedShapeGroup.isEmpty()) {
        var zero = {
            x: selectedShapeGroup.startX + selectedShapeGroup.width / 2,
            y: selectedShapeGroup.startY + selectedShapeGroup.height / 2
        };
        selectedShapeGroup.reSize(zero, -1, 1);
        selectedShapeGroup.correctSize();
        screen.reDraw();
    }
}

function vMirror() {
    if (!selectedShapeGroup.isEmpty()) {
        var zero = {
            x: selectedShapeGroup.startX + selectedShapeGroup.width / 2,
            y: selectedShapeGroup.startY + selectedShapeGroup.height / 2
        };
        selectedShapeGroup.reSize(zero, 1, -1);
        selectedShapeGroup.correctSize();
        screen.reDraw();
    }
}

function rotateAngle(angle) {
    if (!selectedShapeGroup.isEmpty()) {
        var zero = {
            x: selectedShapeGroup.startX + selectedShapeGroup.width / 2,
            y: selectedShapeGroup.startY + selectedShapeGroup.height / 2
        };
        selectedShapeGroup.rotate(zero, angle);
        // selectedShapeGroup.deleteAll();
        screen.reDraw();
    }
}

function selectAll() {
    selectedShapeGroup.deleteAll();
    for (var i = 0, len = screen.shapeList.length; i < len; i++) {
        var obj = screen.shapeList[i];
        selectedShapeGroup.add(obj);
        selectedShapeGroup.drawBorder();
    }
}

function selectByMouseDrag(x,y,w,h) {
    selectedShapeGroup.deleteAll();
    for (var i = 0, len = screen.shapeList.length; i < len; i++) {
        var obj = screen.shapeList[i];
        if (isPointInRect(obj.startX,obj.startY,x,y,w,h)&&
            isPointInRect(obj.startX+obj.width,obj.startY+obj.height,x,y,w,h)){
            selectedShapeGroup.add(obj);
        }
    }
    selectedShapeGroup.drawBorder();
}

function copy() {
    if (!selectedShapeGroup.isEmpty()) {
        clipboard=deepCopy(selectedShapeGroup);
    }
}

function paste() {
    if (!clipboard.isEmpty()){
        clipboard.setNewId();
        clipboard.move(10,10);
        selectedShapeGroup=deepCopy(clipboard);
        for (var i = 0, len = selectedShapeGroup.shapeList.length; i < len; i++) {
            var obj = selectedShapeGroup.shapeList[i];
            screen.addShape(obj);
        }
        selectedShapeGroup.drawBorder();
        screen.reDraw();
    }
}

function newFile() {
    shapeId.id=0;
    cfgId.id=0;
    screen.clear();
    screen.reDraw();
}

function openFile(files) {
    $('#openfile').click();
}
function fileChange() {
    var file = x('openfile').files[0];
    var fileReader = new FileReader();
    fileReader.onload=function (e) {
        var file = JSON.parse(e.target.result);
        screen.clear();
        loadShapesToMemory(file.screen.shapeList);
        loadCfgsToMemory(file.screen.configurationList);
        screen.reDraw();
        shapeId.id=file.shapeId.id;
        cfgId.id=file.cfgId.id;
        // console.log(screen);
    };
    fileReader.readAsText(file);
}
function loadShapeGroup(shapeGroup) {
    var res=new ShapeGroup();
    res.id=shapeGroup.id;
    var shape;
    for (var i = 0, len = shapeGroup.shapeList.length; i < len; i++) {
        var obj = shapeGroup.shapeList[i];
        switch (obj.type){
            case 'group':
                shape=loadShapeGroup(obj);
                break;
            case 'rect':
                shape=new Rect();
                for (var j in obj) shape[j]=obj[j];
                break;
            case 'polygon':
                shape=new Polygon();
                for (var j in obj) shape[j]=obj[j];
                break;
            case 'circle':
                shape=new Circle();
                for (var j in obj) shape[j]=obj[j];
                break;
            case 'line':
                shape=new Line();
                for (var j in obj) shape[j]=obj[j];
                break;
            case 'linearrow':
                shape=new LineArrow();
                for (var j in obj) shape[j]=obj[j];
                break;
            case 'text':
                shape=new Text();
                for (var j in obj) shape[j]=obj[j];
                break;
        }
        res.add(shape);
    }
    return res;
}
function shapeJsonToObject(obj) {
    var shape;
    switch (obj.type) {
        case 'group':
            shape = loadShapeGroup(obj);
            break;
        case 'rect':
            shape = new Rect();
            for (var j in obj) shape[j] = obj[j];
            break;
        case 'polygon':
            shape = new Polygon();
            for (var j in obj) shape[j] = obj[j];
            break;
        case 'circle':
            shape = new Circle();
            for (var j in obj) shape[j] = obj[j];
            break;
        case 'line':
            shape = new Line();
            for (var j in obj) shape[j] = obj[j];
            break;
        case 'linearrow':
            shape = new LineArrow();
            for (var j in obj) shape[j] = obj[j];
            break;
        case 'text':
            shape = new Text();
            for (var j in obj) shape[j] = obj[j];
            break;
    }
    return shape;
}
function loadShapesToMemory(shapeList) {
    for (var i = 0, len = shapeList.length; i < len; i++) {
        var obj = shapeList[i];
        var shape = shapeJsonToObject(obj);
        screen.addShape(shape);
    }
}
function loadCfgsToMemory(configurationList) {
    for (var i = 0, len = configurationList.length; i < len; i++) {
        var obj = configurationList[i];
        var cfg = new Configuration();
        for (var j in obj) cfg[j] = obj[j];
        screen.addConfiguration(cfg);
    }
}

function saveFile() {
    var file={};
    file.shapeId=shapeId;
    file.cfgId=cfgId;
    file.screen=screen;
    export_raw('file.xcs',JSON.stringify(file))
}
function saveAsFile() {
    saveFile();
}
function fake_click(obj) {
    var ev = document.createEvent("MouseEvents");
    ev.initMouseEvent(
        "click", true, false, window, 0, 0, 0, 0, 0
        , false, false, false, false, 0, null
    );
    obj.dispatchEvent(ev);
}
//name-文件名；data-要保存的字符串。
function export_raw(name, data) {
    var urlObject = window.URL || window.webkitURL || window;
    var export_blob = new Blob([data],{type:'text/json'});
    var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a");
    save_link.href = urlObject.createObjectURL(export_blob);
    save_link.download = name;
    // location.href=save_link;
    fake_click(save_link);
}






function deepCopy(p,c) {
    var c= c||{};
    for (var i in p) {
        if (typeof p[i] ==='object'){
            c[i]=(p[i].constructor===Array)?[]:{};
            deepCopy(p[i],c[i]);
        }else {
            c[i]=p[i];
        }
    }
    return c;
}


function addRect() {
    var rect = new Rect();
    rect.setStyle(16, '#ff0000', '#00ff00').setShapeSize(85, 146);
    screen.addShape(rect);
    screen.reDraw();
}

function addCircle() {
    var circle = new Circle();
    // circle.setPoints([{x:10,y:20},{x:20,y:10},{x:30,y:20},{x:20,y:30}]);
    circle.setAB(156, 87);
    screen.addShape(circle);
    screen.reDraw();
}

function addPolygon() {
    var polygon = new Polygon();
    polygon.setPoints([{x: 30, y: 30}, {x: 30, y: 60}, {x: 60, y: 60}, {x: 60, y: 30}, {x: 45, y: 45}])
        .setStyle(6, '#ff0000', '#00ff00');
    screen.addShape(polygon);
    screen.reDraw();
}

function addLine() {
    var line = new Line();
    // line.setPoints([{x: 10, y: 10}, {x: 30, y: 30}])

    line.setStyle(20, '#ff0000');
    line.setLineLength(40);
    screen.addShape(line);
    screen.reDraw();
}

function addLineArrow() {
    var lineArrow = new LineArrow();
    lineArrow.setStyle(20, '#ff0000').setLineLength(150);
    screen.addShape(lineArrow);
    screen.reDraw();
}

function addText() {
    var text = new Text();
    text.setStyle('#0000ff',23,'rgba(255,255,255,0)').setText('qqq辛德强\nAAA\n得分');
    screen.addShape(text);
    screen.reDraw();
}

function showGalleryPanel() {
    $("#gallery_panel").show();
}

function hideGalleryPanel() {
    $("#gallery_panel").hide();
}

function galleryFileChange() {
    var file = x('galleryfile').files[0];
    gallery.loadFromFile(file,function () {
        updateGalleryPanel();
    });
}

function saveGallery() {
    gallery.saveToFile();
}

function addShapeToGallery(name) {
    if (selectedShapeGroup.shapeList.length==1){
        $('#addGallery_dialog').dialog('open');
    }
}
function updateGalleryPanel() {
    $('#gallery_panel span').remove();
    for (var i = 0, len = gallery.gallery.length; i < len; i++) {
        var obj = gallery.gallery[i];
        var span = document.createElement("span");
        span.innerText=obj.name;
        span.onclick=function () {
            var shape = gallery.get(this.innerText).shape;
            shape=deepCopy(shape);
            shape.setNewId();
            screen.addShape(shape);
            screen.reDraw();
        };
        span.oncontextmenu=function (e) {
            galleryName=this.innerText;
            e.preventDefault();
            $('#gallery_menu').menu('show', {left: e.clientX, top: e.clientY});
        };

        $('#gallery_panel').append(span);
    }

}
function deleteShapeFromGallery() {
    gallery.delete(galleryName);
    updateGalleryPanel();
    galleryName=null;
}