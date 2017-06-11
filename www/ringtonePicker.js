var RingtonePicker = {
	getSoundsList : function(successCallBack,errorCallBack){
		cordova.exec(successCallBack, errorCallBack, "RingtonePicker", "getSoundsList", null);
	},
	playSound : function(soundUri){
		cordova.exec(null,null,'RingtonePicker','playSound',[soundUri]);
	},
	timerPlaySound : function(soundUri, time){
		cordova.exec(null,null,'RingtonePicker','timerPlaySound',[soundUri, time]);
	},
	pickRingtone : function(successCallBack,errorCallBack){
		cordova.exec(successCallBack, errorCallBack, "RingtonePicker", "pickRingtone", null);
	}
}
module.exports = RingtonePicker;