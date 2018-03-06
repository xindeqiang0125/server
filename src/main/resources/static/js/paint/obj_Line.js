function Line() {
    this.id = shapeId.next();
    this.type = 'line';
    this.startX = 0;
    this.startY = 0;
    this.width = 0;
    this.height = 0;
    this.points = [{x: 10, y: 10}, {x: 30, y: 10}];
    this.lineWidth = 2;
    this.lineColor = '#000000';

    this.lineLength = 20;
}

Line.prototype = new Shape();
Line.prototype.constructor = Polygon;
Line.prototype.draw = function () {
    cxt.save();
    cxt.strokeStyle = this.lineColor;
    cxt.lineWidth = this.lineWidth;
    cxt.lineCap = 'round';
    cxt.lineJoin = 'round';
    cxt.beginPath();
    cxt.moveTo(this.points[0].x, this.points[0].y);
    cxt.lineTo(this.points[1].x, this.points[1].y);
    cxt.closePath();
    cxt.stroke();
    cxt.restore();

};
Line.prototype.setPoints = function (points) {
    this.points = points;
    this.startX = Math.min(points[0].x, points[1].x);
    this.startY = Math.min(points[0].y, points[1].y);
    var endX = Math.max(points[0].x, points[1].x);
    var endY = Math.max(points[0].y, points[1].y);
    this.width = endX - this.startX;
    this.height = endY - this.startY;
    // if (this.width < 20) {
    //     this.startX -= 10;
    //     this.width = 20;
    // }
    // if (this.height < 20) {
    //     this.startY -= 10;
    //     this.height = 20;
    // }
    // if (this.width < this.lineWidth) {
    //     this.startX -= this.lineWidth / 2;
    //     this.width = this.lineWidth;
    // }
    // if (this.height < this.lineWidth) {
    //     this.startY -= this.lineWidth / 2;
    //     this.height = this.lineWidth;
    // }
    this.startX -= this.lineWidth / 2;
    this.startY -= this.lineWidth / 2;
    this.width += this.lineWidth;
    this.height += this.lineWidth;
    this.lineLength = Math.sqrt(Math.pow(points[0].x - points[1].x, 2) + Math.pow(points[0].y - points[1].y, 2));
    return this;
};
Line.prototype.setStyle = function (lineWidth, lineColor) {
    this.lineWidth = parseInt(lineWidth);
    this.lineColor = lineColor;
    this.setPoints(this.points);
    return this;
};
Line.prototype.setLineLength = function (lineLength) {
    this.points[1].x = (this.points[1].x - this.points[0].x) * lineLength / this.lineLength + this.points[0].x;
    this.points[1].y = (this.points[1].y - this.points[0].y) * lineLength / this.lineLength + this.points[0].y;
    this.setPoints(this.points);
    return this;
};
Line.prototype.move = Polygon.prototype.move;
Line.prototype.reSize = Polygon.prototype.reSize;
Line.prototype.rotate = Polygon.prototype.rotate;