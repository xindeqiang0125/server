function Rect() {
    this.id = shapeId.next();
    this.type = 'rect';
    this.startX = 0;
    this.startY = 0;
    this.width = 0;
    this.height = 0;
    this.points = [{x: 10, y: 10}, {x: 30, y: 10}, {x: 30, y: 30}, {x: 10, y: 30}];
    this.lineWidth = 2;
    this.lineColor = '#000000';
    this.fillColor = '#ffffff';
    this.shapeWidth = 20;
    this.shapeHeight = 20;
}

Rect.prototype = new Shape();
Rect.prototype.constructor = Rect;
Rect.prototype.draw = Polygon.prototype.draw;
Rect.prototype.setPoints = function (points) {
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

    this.shapeWidth = Math.sqrt(Math.pow(points[0].x - points[1].x, 2) + Math.pow(points[0].y - points[1].y, 2));
    this.shapeHeight = Math.sqrt(Math.pow(points[0].x - points[3].x, 2) + Math.pow(points[0].y - points[3].y, 2));
    return this;
};
Rect.prototype.setStyle = Polygon.prototype.setStyle;
Rect.prototype.setShapeSize = function (width, height) {
    this.points[1].x = (this.points[1].x - this.points[0].x) * width / this.shapeWidth + this.points[0].x;
    this.points[1].y = (this.points[1].y - this.points[0].y) * width / this.shapeWidth + this.points[0].y;
    this.points[3].x = (this.points[3].x - this.points[0].x) * height / this.shapeHeight + this.points[0].x;
    this.points[3].y = (this.points[3].y - this.points[0].y) * height / this.shapeHeight + this.points[0].y;
    this.points[2].x = this.points[3].x + (this.points[1].x - this.points[0].x);
    this.points[2].y = this.points[1].y + (this.points[3].y - this.points[0].y);
    this.setPoints(this.points);
    return this;
};
Rect.prototype.move = Polygon.prototype.move;
Rect.prototype.reSize = Polygon.prototype.reSize;
Rect.prototype.rotate = Polygon.prototype.rotate;