function ShapeGroup() {
    this.id = shapeId.next();
    this.type = 'group';
    this.startX = -100;
    this.startY = -100;
    this.width = 0;
    this.height = 0;
    this.shapeList = [];
}

ShapeGroup.prototype = new Shape();
ShapeGroup.prototype.constructor = ShapeGroup;

ShapeGroup.prototype.add = function (shape) {
    if (!this.contains(shape)){
        var length = this.shapeList.length;
        if (0 == length) {
            this.startX = shape.startX;
            this.startY = shape.startY;
            this.width = shape.width;
            this.height = shape.height;
        } else {
            var endX = Math.max(this.startX + this.width, shape.startX + shape.width);
            var endY = Math.max(this.startY + this.height, shape.startY + shape.height);
            this.startX = Math.min(this.startX, shape.startX);
            this.startY = Math.min(this.startY, shape.startY);
            this.width = endX - this.startX;
            this.height = endY - this.startY;
        }
        this.shapeList.push(shape);
        this.onListChanged();
    }
};
ShapeGroup.prototype.delete = function (shape) {
    var start = $.inArray(shape, this.shapeList);
    if (-1 != start) {
        this.shapeList.splice(start, 1);
        var aaa = this.shapeList;
        this.startX = -100;
        this.startY = -100;
        this.width = 0;
        this.height = 0;
        this.shapeList = [];
        for (var i = 0, len = aaa.length; i < len; i++) {
            this.add(aaa[i]);
        }
    }
    this.onListChanged();
    return shape;
};
ShapeGroup.prototype.deleteAll = function () {
    this.startX = -100;
    this.startY = -100;
    this.width = 0;
    this.height = 0;
    this.shapeList = [];
    this.onListChanged();
};
ShapeGroup.prototype.contains = function (shape) {
    var start = $.inArray(shape, this.shapeList);
    return -1 != start;
};
ShapeGroup.prototype.isEmpty = function () {
    return 0 == this.shapeList.length;
};
ShapeGroup.prototype.draw = function () {
    for (var i = 0; this.shapeList.length > i; i++) {
        this.shapeList[i].draw();
    }
};

ShapeGroup.prototype.onListChanged = function () {
    // console.log(JSON.stringify(this));
};

ShapeGroup.prototype.move = function (moveX, moveY) {
    this.startX += moveX;
    this.startY += moveY;
    for (var i = 0, len = this.shapeList.length; i < len; i++) {
        this.shapeList[i].move(moveX, moveY)
    }
};

ShapeGroup.prototype.onMouseMove = function (e) {
    x('cspaint_canvas_select').style.cursor = 'move';
};
ShapeGroup.prototype.onMouseMoveInBorder = function (e, type) {
    x('cspaint_canvas_select').style.cursor = type;
};
ShapeGroup.prototype.onMouseOut = function (e) {
    x('cspaint_canvas_select').style.cursor = 'default';
};

ShapeGroup.prototype.reSize = function (zero, scaleX, scaleY) {
    this.startX = (this.startX - zero.x) * scaleX + zero.x;
    this.startY = (this.startY - zero.y) * scaleY + zero.y;
    this.width *= scaleX;
    this.height *= scaleY;
    // if (0==this.width)this.width=1;
    // if (0==this.height)this.height=1;
    for (var i = 0, len = this.shapeList.length; i < len; i++) {
        this.shapeList[i].reSize(zero, scaleX, scaleY);
    }
};
ShapeGroup.prototype.correctSize = function () {
    if (0 > this.width) {
        this.startX += this.width;
        this.width = -this.width;
    }
    if (0 > this.height) {
        this.startY += this.height;
        this.height = -this.height;
    }
    for (var i = 0, len = this.shapeList.length; i < len; i++) {
        this.shapeList[i].correctSize();
    }
};
ShapeGroup.prototype.rotate = function (zero,angle) {
    var shapeList = this.shapeList;
    this.deleteAll();
    for (var i = 0, len = shapeList.length; i < len; i++) {
        var shape = shapeList[i];
        shape.rotate(zero,angle);
        this.add(shape);
    }
};
ShapeGroup.prototype.setNewId=function () {
    this.id=shapeId.next();
    for (var i = 0, len = this.shapeList.length; i < len; i++) {
        var obj = this.shapeList[i];
        obj.setNewId();
    }
}
