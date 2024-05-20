var Manager2 = cc.Class({
    statics: {
        // Singleton
        _instance: null,
        getInstance: function () {
            if (!Manager2._instance) {
                Manager2._instance = new Manager2();
            }
            return Manager2._instance;
        },
    },

    properties: {
        resultArray: [],
    },


    InitResultArray: function(size){
        if (this.resultArray)  this.resultArray.length = 0;
        
        this.resultArray = new Array(size).fill(false);

        console.log("Result Init -> " + size);
    },

    /**
     * 
     * @param {해당되는 ID 번호} index 
     */
    GetIt: function(index){
        this.resultArray[index] = true;
    },

    IsClearArray: function(idx){

        if(idx > this.resultArray.length){
            console.log('error out of range');

            return false;
        }


        return this.resultArray[idx];

    },


    IsCardGameClear: function(){
        var isClear = false;
        for(var i = 0; i < this.resultArray.length; i++){
            if(this.resultArray[i]) isClear  = true;
            else isClear = false;
            
        }

        return isClear;
    }


});
