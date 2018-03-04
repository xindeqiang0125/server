function Polygon() {
    this.id = shapeId.next();
    this.type = 'polygon';
    this.startX = 0;
    this.startY = 0;
    this.width = 0;
    this.height = 0;
    this.points = new Array();
    this.lineWidth = 2;
    this.lineColor = '#000000';
    this.fillColor = '#ffffff';
}

Polygon.prototype = new Shape();
Polygon.prototype.constructor = Polygon;
Polygon.prototype.draw = function () {
    cxt.save();
    cxt.fillStyle = this.fillColor;
    cxt.strokeStyle = this.lineColor;
    cxt.lineWidth = this.lineWidth;
    cxt.lineCap = 'round';
    cxt.lineJoin = 'round';
    cxt.beginPath();
    cxt.moveTo(this.points[0].x, this.points[0].y);
    for (var i = 1, len = this.points.length; i < len; i++) {
        cxt.lineTo(this.points[i].x, this.points[i].y);
    }
    cxt.closePath();
    cxt.fill();
    cxt.stroke();
    cxt.restore();
};
Polygon.prototype.setPoints = function (points) {
    this.points = points;
    this.startX = points[0].x;
    this.startY = points[0].y;
    var endX = points[0].x, endY = points[0].y;
    for (var i = 1, len = points.length; i < len; i++) {
        endX = Math.max(endX, points[i].x);
        endY = Math.max(endY, points[i].y);
    }
    for (var i = 1, len = points.length; i < len; i++) {
        this.startX = Math.min(this.startX, points[i].x);
        this.startY = Math.min(this.startY, points[i].y);
    }
    this.width = endX - this.startX;
    this.height = endY - this.startY;
    return this;
};
Polygon.prototype.setStyle = function (lineWidth, lineColor, fillColor) {
    this.lineWidth = lineWidth;
    this.lineColor = lineColor;
    this.fillColor = fillColor;
    return this;
};
Polygon.prototype.move = function (moveX, moveY) {
    this.startX += moveX;
    this.startY += moveY;
    for (var i = 0, len = this.points.length; i < len; i++) {
        this.points[i].x += moveX;
        this.points[i].y += moveY;
    }
    return this;
};
Polygon.prototype.reSize = function (zero, scaleX, scaleY) {
    // this.startX = (this.startX - zero.x) * scaleX + zero.x;
    // this.startY = (this.startY - zero.y) * scaleY + zero.y;
    // this.width *= scaleX;
    // this.height *= scaleY;
    for (var i = 0, len = this.points.length; i < len; i++) {
        this.points[i].x = (this.points[i].x - zero.x) * scaleX + zero.x;
        this.points[i].y = (this.points[i].y - zero.y) * scaleY + zero.y;
    }
    this.setPoints(this.points);
    return this;
};
Polygon.prototype.rotate = function (zero, angle) {
    var cos = Math.cos(angle * Math.PI / 180);
    var sin = Math.sin(angle * Math.PI / 180);
    for (var i = 0, len = this.points.length; i < len; i++) {
        var x = this.points[i].x;
        var y = this.points[i].y;
        this.points[i].x = zero.x + (x - zero.x) * cos - (y - zero.y) * sin;
        this.points[i].y = zero.y + (y - zero.y) * cos + (x - zero.x) * sin;
    }
    this.setPoints(this.points);
    return this;
};