function Circle() {
    this.id = shapeId.next();
    this.type = 'circle';
    this.startX = 0;
    this.startY = 0;
    this.width = 0;
    this.height = 0;
    this.points = [{x: 10, y: 20}, {x: 20, y: 10}, {x: 30, y: 20}, {x: 20, y: 30}];
    this.lineWidth = 2;
    this.lineColor = '#000000';
    this.fillColor = '#ffffff';
    this.a = 10;
    this.b = 10;
}

Circle.prototype = new Shape();
Circle.prototype.constructor = Circle;
Circle.prototype.draw = function () {
    cxt.save();
    cxt.fillStyle = this.fillColor;
    cxt.strokeStyle = this.lineColor;
    cxt.lineWidth = this.lineWidth;
    cxt.lineCap = 'round';
    cxt.lineJoin = 'round';

    // k=0.5522848
    var k = 0.5522848, a = 100, b = 100, x = 300, y = 150, ox = k * a, oy = k * b;
    cxt.beginPath();

    cxt.moveTo(this.points[0].x, this.points[0].y);
    var x1 = (this.points[1].x - this.points[3].x) / 2 * k + this.points[0].x;
    var y1 = (this.points[1].y - this.points[3].y) / 2 * k + this.points[0].y;
    var x2 = (this.points[0].x - this.points[2].x) / 2 * k + this.points[1].x;
    var y2 = (this.points[0].y - this.points[2].y) / 2 * k + this.points[1].y;
    cxt.bezierCurveTo(x1, y1, x2, y2, this.points[1].x, this.points[1].y);
    x1 = (this.points[2].x - this.points[0].x) / 2 * k + this.points[1].x;
    y1 = (this.points[2].y - this.points[0].y) / 2 * k + this.points[1].y;
    x2 = (this.points[1].x - this.points[3].x) / 2 * k + this.points[2].x;
    y2 = (this.points[1].y - this.points[3].y) / 2 * k + this.points[2].y;
    cxt.bezierCurveTo(x1, y1, x2, y2, this.points[2].x, this.points[2].y);
    x1 = (this.points[3].x - this.points[1].x) / 2 * k + this.points[2].x;
    y1 = (this.points[3].y - this.points[1].y) / 2 * k + this.points[2].y;
    x2 = (this.points[2].x - this.points[0].x) / 2 * k + this.points[3].x;
    y2 = (this.points[2].y - this.points[0].y) / 2 * k + this.points[3].y;
    cxt.bezierCurveTo(x1, y1, x2, y2, this.points[3].x, this.points[3].y);
    x1 = (this.points[0].x - this.points[2].x) / 2 * k + this.points[3].x;
    y1 = (this.points[0].y - this.points[2].y) / 2 * k + this.points[3].y;
    x2 = (this.points[3].x - this.points[1].x) / 2 * k + this.points[0].x;
    y2 = (this.points[3].y - this.points[1].y) / 2 * k + this.points[0].y;
    cxt.bezierCurveTo(x1, y1, x2, y2, this.points[0].x, this.points[0].y);
    cxt.closePath();
    cxt.fill();
    cxt.stroke();

    cxt.restore();
};
/**
 * setPoints外部不直接调用
 */
Circle.prototype.setPoints = function (points) {
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

    var zero = {
        x: (this.points[0].x + this.points[2].x) / 2,
        y: (this.points[0].y + this.points[2].y) / 2
    };
    this.a = Math.sqrt(Math.pow(this.points[0].x - zero.x, 2) + Math.pow(this.points[0].y - zero.y, 2));
    this.b = Math.sqrt(Math.pow(this.points[1].x - zero.x, 2) + Math.pow(this.points[1].y - zero.y, 2));
    return this;
};
Circle.prototype.setStyle = Polygon.prototype.setStyle;
Circle.prototype.setAB = function (a, b) {
    var zero = {
        x: (this.points[0].x + this.points[2].x) / 2,
        y: (this.points[0].y + this.points[2].y) / 2
    };
    // var oldA, oldB;
    // oldA = Math.sqrt(Math.pow(this.points[0].x - zero.x, 2) + Math.pow(this.points[0].y - zero.y, 2));
    // oldB = Math.sqrt(Math.pow(this.points[1].x - zero.x, 2) + Math.pow(this.points[1].y - zero.y, 2));
    for (var i = 0; i < 4; i = i + 2) {
        this.points[i].x = (this.points[i].x - zero.x) * a / this.a + zero.x;
        this.points[i].y = (this.points[i].y - zero.y) * a / this.a + zero.y;
    }
    for (var i = 1; i < 4; i = i + 2) {
        this.points[i].x = (this.points[i].x - zero.x) * b / this.b + zero.x;
        this.points[i].y = (this.points[i].y - zero.y) * b / this.b + zero.y;
    }
    this.setPoints(this.points);
    return this;
};
Circle.prototype.move = Polygon.prototype.move;
Circle.prototype.reSize = Polygon.prototype.reSize;
Circle.prototype.rotate = Polygon.prototype.rotate;