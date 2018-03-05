function Configuration(shapeId,itemId) {
    this.id=cfgId.next();
    this.shapeId=shapeId;
    this.itemId=itemId;
    this.detail='';
}
Configuration.prototype.setCfg=function (action,condition) {
    this.detail=action+' when '+condition;
};
function getCfgsByShapeId(shapeId) {
    var cfgs=[];
    for (var i = 0, len = screen.configurationList.length; i < len; i++) {
        var obj = screen.configurationList[i];
        if (obj.shapeId==shapeId){
            cfgs.push(obj);
        }
    }
    return cfgs;
}