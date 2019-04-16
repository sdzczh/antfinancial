$.extend({
    isNull: function(str) {//空值判断
        if(str==undefined || str=="" || str.length==0){
        	return true;
        }
        return false;
    }
})