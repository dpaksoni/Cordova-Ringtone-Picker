#Cordova Ringtone Picker Plugin#
Ringtone Picker is a Cordova plugin for android that returns list of available ringtones

## Adding the plugin to your project ##
To install the plugin, use the Cordova CLI and enter the following:<br />
`cordova plugin add ringtonepicker`

## Platforms ##
- Android


## Use ##

Update:
To show native ringtone picker, use the following code: <br />
`RingtonePicker.pickRingtone(successCallBack,errorCallBack);`

successCallBack will have the URI of selected sound. <br>
ErrorCallBack will have an integer representing following errors:<br>
1 - User canceled operation <br>
2 - Unknow error like returned URI was NULL

To get the list of available ringtones, use the following code: <br />
`RingtonePicker.getSoundsList(successCallBack,errorCallBack);`

successCallBack will have a JSON Array of JSON Objects containing URI and name of sound.


To hide the dialog, use the following code:<br />
`RingtonePicker.playSound(soundURI);`

soundURI is the URI of sound that you want to play. 
