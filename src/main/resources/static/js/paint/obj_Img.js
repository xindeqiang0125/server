function Img() {
    var that = this;
    this.id = shapeId.next();
    this.type = 'image';
    this.startX = 0;
    this.startY = 0;
    this.width = 50;
    this.height = 50;
    this.base64 = 'data:image/png;base64,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';
    this.isHMirror = false;
    this.isVMirror = false;
    this.angle = 0;
    this.hasLoaded = false;
    this.image = new Image();
    // this.image.src = null;
    this.image.onload = function (ev) {
        if (!that.hasLoaded) {
            that.width = this.width;
            that.height = this.height;
            that.hasLoaded = true;
        }
        screen.reDraw();
    };
}

Img.prototype = new Shape();
Img.prototype.constructor = Img;
Img.prototype.draw = function () {
    cxt.save();
    cxt.translate(this.startX + this.width / 2, this.startY + this.height / 2);
    cxt.scale(this.isHMirror ? -1 : 1, this.isVMirror ? -1 : 1);
    cxt.rotate(this.angle * Math.PI / 180);
    cxt.drawImage(this.image, -this.width / 2, -this.height / 2, this.width, this.height);
    cxt.restore();

};
Img.prototype.setImageUrl=function (url) {
    this.image.src = url;
};
Img.prototype.readFromFile = function (file) {
    var that = this;
    this.hasLoaded = false;
    var oFReader = new FileReader();
    oFReader.readAsDataURL(file);
    oFReader.onload = function (oFREvent) {
        // console.log(oFREvent.target.result);//base64
        that.base64 = oFREvent.target.result;
        that.image.src = that.base64;
    };
};

Img.prototype.reSize = function (zero, scaleX, scaleY) {
    if (scaleX < 0) {
        this.isHMirror = !this.isHMirror;
        scaleX = -scaleX;
    }
    if (scaleY < 0) {
        this.isVMirror = !this.isVMirror;
        scaleY = -scaleY;
    }
    this.startX = (this.startX - zero.x) * scaleX + zero.x;
    this.startY = (this.startY - zero.y) * scaleY + zero.y;
    this.width *= scaleX;
    this.height *= scaleY;
};
Img.prototype.rotate = function (zero, angle) {
    this.angle += angle;
};