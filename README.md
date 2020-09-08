# fl_sys_shortcut

A flutter plugin to use system shortcuts.

## Getting Started

### For using funtions and getters related to WIFI settings you need to add these two permissions in your AndroidManifext.xml file
```
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
```

### For using funtions and getters related to BLUETOOTH settings you need to add these two permissions in your AndroidManifext.xml file
```
<uses-permission android:name="android.permission.BLUETOOTH"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
```
### to acquire notification policy permission you need to add these two permissions in your AndroidManifext.xml file
```
<uses-permission android:name="android.permission.ACCESS_NOTIFICATION_POLICY"/>
```
### Make this import
import 'package:fl_sys_shortcut/fl_sys_shortcut.dart';

# Functions

### Toggle WIFI using this function

> await FlSysShortcut.wifi();// toggle wifi in android

### Toggle BLUETOOTH using this function

> await FlSysShortcut.bluetooth();// toggle bluetooth in android

### turn on Silent Mode using this function

> await FlSysShortcut.silentMode();// toggle silent mode in android

# Getters

### Get current WIFI state

> await FlSysShortcut.checkWifi;// return true/false
### Get current silent mode state

> await FlSysShortcut.checkWifi;// return true/false

### Get current BLUETOOTH state

> await FlSysShortcut.checkBluetooth;// return true/false

# Usage

FlatButton(
  child: Text("Check Wifi"),
  onPressed: () async {
    bool wifi = await FlSysShortcut.checkWifi;
  },
),
```
