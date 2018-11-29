function Img() {
    var that = this;
    this.id = shapeId.next();
    this.type = 'image';
    this.startX = 0;
    this.startY = 0;
    this.width = 100;
    this.height = 100;
    this.base64 = '';
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

    // this.toJSON = function () {
    //     return {
    //         "id": this.id,
    //         "type": this.type,
    //         "startX": this.startX,
    //         "startY": this.startY,
    //         "width": this.width,
    //         "height": this.height,
    //         "base64": this.base64,
    //         "isHMirror": this.isHMirror,
    //         "isVMirror": this.isVMirror,
    //         "angle": this.angle
    //     };
    // };
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