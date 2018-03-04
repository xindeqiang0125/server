x('canvas_container').onkeydown = function (e) {
    // console.log(e.key);
    switch (e.key) {
        case 's':
            shapeSquare();
            break;
        case 'a':
            if (e.ctrlKey) selectAll();
            break;
        case 'c':
            if (e.ctrlKey) copy();
            break;
        case 'v':
            if (e.ctrlKey) paste();
            break;
        case '+':
            if (e.ctrlKey) rotateAngle(45);
            else rotateAngle(1);
            break;
        case '-':
            if (e.ctrlKey) rotateAngle(-45);
            else rotateAngle(-1);
            break;
        case 'Delete':
            del();
            break;
    }
    return false;
};

function shapeSquare() {
    if (!selectedShapeGroup.isEmpty()) {
        var zero = {
            x: selectedShapeGroup.startX,
            y: selectedShapeGroup.startY
        };
        if (selectedShapeGroup.width > selectedShapeGroup.height) {
            selectedShapeGroup.reSize(zero, 1, selectedShapeGroup.width / selectedShapeGroup.height);
        } else {
            selectedShapeGroup.reSize(zero, selectedShapeGroup.height / selectedShapeGroup.width, 1);
        }
        screen.reDraw();
    }
}