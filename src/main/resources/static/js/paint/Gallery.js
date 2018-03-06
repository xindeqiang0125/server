function Gallery() {
    this.gallery = [];
}

Gallery.prototype.contains = function (name) {
    for (var i = 0, len = this.gallery.length; i < len; i++) {
        var obj = this.gallery[i];
        if (obj.name == name) return true;
    }
    return false;
};
Gallery.prototype.get = function (name) {
    for (var i = 0, len = this.gallery.length; i < len; i++) {
        var obj = this.gallery[i];
        if (obj.name == name) return obj;
    }
    return null;
};
Gallery.prototype.add = function (name, shape) {
    if (this.contains(name)) return;
    var obj = {};
    obj.name = name;
    obj.shape = deepCopy(shape);
    this.gallery.push(obj);
};
Gallery.prototype.delete = function (name) {
    console.log(name);
    var newVar = this.get(name);
    console.log(newVar);
    if (newVar != null) {
        var start = $.inArray(newVar, this.gallery);
        console.log(start);
        if (-1 != start) this.gallery.splice(start, 1);
    }
};
Gallery.prototype.update = function (name, newShape) {
    this.delete(name);
    this.add(name, newShape);
};
Gallery.prototype.saveToFile = function () {
    export_raw('gallery.ku', JSON.stringify(this.gallery));
};
Gallery.prototype.loadFromFile = function (file,callback) {
    var that=this;
    var fileReader = new FileReader();
    fileReader.onload = function (e) {
        var res = JSON.parse(e.target.result);
        for (var i = 0, len = res.length; i < len; i++) {
            var obj = res[i];
            obj.shape=shapeJsonToObject(obj.shape);
        }
        that.gallery = res;
        callback();
    };
    fileReader.readAsText(file);
};