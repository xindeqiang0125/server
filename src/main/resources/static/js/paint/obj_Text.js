function Text() {
    this.id = shapeId.next();
    this.type = 'text';
    this.startX = 0;
    this.startY = 0;
    this.width = 0;
    this.height = 0;

    this.text;
    this.font = '';
    this.fontColor = '#000000';
    this.fontSize = 14;
}

Text.prototype = new Shape();
Text.prototype.constructor = Text;
Text.prototype.draw = function () {
    cxt.save();
    cxt.textBaseline = 'top';
    cxt.fillStyle = this.fontColor;
    // cxt.strokeStyle=this.fontColor;
    // cxt.font='italic small-caps bold 20px arial';
    cxt.font = 'normal normal ' + this.fontSize + 'px arial';
    for (var i = 0, len = this.text.length; i < len; i++) {
        var obj = this.text[i];
        cxt.fillText(obj, this.startX, this.startY + i * this.fontSize);
        this.width = Math.max(this.width, cxt.measureText(obj).width);
    }
    cxt.restore();
};
Text.prototype.setText = function (text) {
    this.text = text.split('\n');
    this.height = this.fontSize * this.text.length;
    return this;
};
Text.prototype.setStyle = function (fontColor, fontSize) {
    this.fontColor = fontColor;
    this.fontSize = fontSize;
    return this;
};
Text.prototype.move = function (moveX, moveY) {
    this.startX += moveX;
    this.startY += moveY;
    return this;
};
Text.prototype.reSize = function (zero, scaleX, scaleY) {
    if (scaleX < 0) this.startX = (this.startX + this.width - zero.x) * scaleX + zero.x;
    else this.startX = (this.startX - zero.x) * scaleX + zero.x;
    if (scaleY < 0) this.startY = (this.startY + this.height - zero.y) * scaleY + zero.y;
    else this.startY = (this.startY - zero.y) * scaleY + zero.y;
    return this;
};
Text.prototype.rotate = function (zero, angle) {
    var cos = Math.cos(angle * Math.PI / 180);
    var sin = Math.sin(angle * Math.PI / 180);
    var x = this.startX;
    var y = this.startY;
    this.startX = zero.x + (x - zero.x) * cos - (y - zero.y) * sin;
    this.startY = zero.y + (y - zero.y) * cos + (x - zero.x) * sin;
    return this;
};