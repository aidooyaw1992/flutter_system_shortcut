
import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class FlSysShortcut {
  static const MethodChannel _channel =
      const MethodChannel('com.rain.fl_sys_shortcut/method');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool> get checkWifi async {
    bool b = await _channel.invokeMethod('check_wifi');
    return b;
  }
  static Future<bool> get checkBle async {
    bool b = await _channel.invokeMethod('check_bluetooth');
    return b;
  }

  static Future<Null> switchWifi(bool status) async {
    if (Platform.isIOS) {
    } else {
      final Map params = <String, dynamic>{'is_enabled': status};
      await _channel.invokeMethod('switch_wifi', params);
    }
  }


  static Future<Null>switchBluetooth(bool status) async {
    if (Platform.isIOS) {
    } else {
      final Map params = <String, dynamic>{'is_enabled': status};
      await _channel.invokeMethod('switch_bluetooth', params);
    }
  }
  static Future<Null>setDoNotDisturbMode() async {
    if (Platform.isIOS) {
    } else {
      await _channel.invokeMethod('set_do_not_disturb');
    }
  }

  //set ring mode
  static Future<Null> switchRingerMode(RingerMode mode) async {
    if (Platform.isIOS) {
    } else {
      print(mode.index);
      final Map params = <String, dynamic>{'mode': mode.index};
      await _channel.invokeMethod('switch_ringer_mode', params);
    }
  }
  static Future<String?> get checkRingerMode async {
    String? state;
    if (Platform.isIOS) {
    } else {
      var ringerId = await _channel.invokeMethod('check_ringer_mode');
      switch (ringerId) {
        case 1:
          state = "vibration";
          break;
        case 2:
          state = "normal";
          break;
        default:
          state = null;
      }
    }
    return state;
  }



}

enum RingerMode {  normal, vibration }