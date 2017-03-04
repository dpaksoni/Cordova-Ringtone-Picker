var RingtonePicker = {
	getSoundsList : function(successCallBack,errorCallBack){
		cordova.exec(successCallBack, errorCallBack, "RingtonePicker", "getSoundsList", null);
	},
	playSound:function(soundUri){
		cordova.exec(null,null,'RingtonePicker','playSound',[soundUri]);
	},
	pickRingtone : function(successCallBack,errorCallBack){
		cordova.exec(successCallBack, errorCallBack, "RingtonePicker", "pickRingtone", null);
	}
}
module.exports = RingtonePicker;