function Shape() {
    this.onClick=function (e) {
        if (e.ctrlKey){
            if (selectedShapeGroup.contains(this)) selectedShapeGroup.delete(this);
            else selectedShapeGroup.add(this);
        }else {
            selectedShapeGroup.deleteAll();
            selectedShapeGroup.add(this);
        }
        changePropertyGridDatas(selectedShapeGroup.shapeList[0]);
    };

    this.move=function (moveX, moveY) {
        this.startX += moveX;
        this.startY += moveY;
    };

    this.reSize=function (zero, scaleX, scaleY) {
        this.startX=(this.startX-zero.x)*scaleX+zero.x;
        this.startY=(this.startY-zero.y)*scaleY+zero.y;
        this.width*=scaleX;
        this.height*=scaleY;
    };
    this.correctSize=function () {
        if (0 > this.width) {
            this.startX += this.width;
            this.width = -this.width;
        }
        if (0 > this.height) {
            this.startY += this.height;
            this.height = -this.height;
        }
    };

    this.rotate=function (zero,angle) {


    };

    this.setNewId=function () {
        this.id=shapeId.next();
    };

    this.containsPoint=function (x, y) {
        var b = this.startX < x && (this.startX + this.width) > x &&
                this.startY < y && (this.startY + this.height) > y;
        return b;
    };
    this.drawBorder=function () {
        cxtSel.clearRect(0, 0, canvas2.width, canvas2.height);
        cxtSel.save();
        cxtSel.strokeStyle = 'rgba(0,0,0,1)';
        cxtSel.lineWidth = 2;
        cxtSel.lineCap = 'square';
        cxtSel.lineJoin = 'square';
        cxtSel.strokeRect(this.startX, this.startY, this.width, this.height);
        cxtSel.restore();
    };
}