function LineArrow() {
    this.id = shapeId.next();
    this.type = 'linearrow';
    this.startX = 0;
    this.startY = 0;
    this.width = 0;
    this.height = 0;
    this.points = [{x: 10, y: 10}, {x: 30, y: 10}];
    this.lineWidth = 2;
    this.lineColor = '#000000';

    this.lineLength = 20;
}

LineArrow.prototype = new Shape();
LineArrow.prototype.constructor = LineArrow;
LineArrow.prototype.draw = function () {
    cxt.save();
    cxt.fillStyle = this.lineColor;
    cxt.strokeStyle = this.lineColor;
    cxt.lineWidth = this.lineWidth;
    cxt.lineCap = 'round';
    cxt.lineJoin = 'round';
    cxt.beginPath();
    cxt.moveTo(this.points[0].x, this.points[0].y);
    cxt.lineTo(this.points[1].x, this.points[1].y);
    cxt.closePath();
    cxt.fill();
    cxt.stroke();

    cxt.beginPath();
    cxt.lineWidth = 1;

    var jiao=Math.atan((this.points[1].y-this.points[0].y)/(this.points[1].x-this.points[0].x));
    var number1 = Math.max(5,this.lineWidth);
    var number2 = 2*number1;
    var point1={
        x:this.points[1].x+number1*Math.sin(jiao),
        y:this.points[1].y-number1*Math.cos(jiao)
    };
    var point2={
        x:2*this.points[1].x-point1.x,
        y:2*this.points[1].y-point1.y
    };
    var point3={
        x:this.points[1].x+(this.points[1].x-this.points[0].x)*number2/this.lineLength,
        y:this.points[1].y+(this.points[1].y-this.points[0].y)*number2/this.lineLength
    };
    cxt.moveTo(point1.x,point1.y);
    cxt.lineTo(point2.x,point2.y);
    cxt.lineTo(point3.x,point3.y);
    cxt.closePath();
    cxt.fill();
    cxt.stroke();
    cxt.restore();
};
LineArrow.prototype.setPoints = function (points) {
    this.points = points;
    this.startX = Math.min(points[0].x, points[1].x);
    this.startY = Math.min(points[0].y, points[1].y);
    var endX = Math.max(points[0].x, points[1].x);
    var endY = Math.max(points[0].y, points[1].y);
    this.width = endX - this.startX;
    this.height = endY - this.startY;
    this.startX -= this.lineWidth / 2;
    this.startY -= this.lineWidth / 2;
    this.width += this.lineWidth;
    this.height += this.lineWidth;

    this.lineLength = Math.sqrt(Math.pow(points[0].x - points[1].x, 2) + Math.pow(points[0].y - points[1].y, 2));
    return this;
};
LineArrow.prototype.setStyle = Line.prototype.setStyle;
LineArrow.prototype.setLineLength = Line.prototype.setLineLength;
LineArrow.prototype.move = Polygon.prototype.move;
LineArrow.prototype.reSize = Polygon.prototype.reSize;
LineArrow.prototype.rotate = Polygon.prototype.rotate;
