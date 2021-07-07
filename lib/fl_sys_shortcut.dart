import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class FlSysShortcut {
  static const MethodChannel _channel = const MethodChannel('fl_sys_shortcut');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  /// Toggle Wifi.
  ///
  /// If it is already turned on wifi ( ) will turn it off
  /// else it'll turn it on
  static Future<Null> wifi() async {
    if (Platform.isIOS) {
    } else {
      await _channel.invokeMethod('wifi');
    }
  }

  /// Return true if the wifi is alreay turned on.
  ///
  /// Return false if the wifi is turned off.
  static Future<bool> get checkWifi async {
    bool b = await _channel.invokeMethod('checkWifi');
    return b;
  }

  static Future<bool> get checkSilentMode async {
    bool b = await _channel.invokeMethod('checkSilentMode');
    return b;
  }

  static Future<bool> checkAirplaneMode() async {
    bool airplanemode = await _channel.invokeMethod('checkAirplaneMode');
    return airplanemode;
  }

  /// Toggle bluetooth.
  ///
  /// If it is already turned on bluetooth ( ) will turn it off
  /// else it'll turn it on
  static Future<Null> bluetooth() async {
    if (Platform.isIOS) {
    } else {
      var status = await _channel.invokeMethod('bluetooth');
      if (status != null) {
        return status;
      }
    }
  }

  //set vibration
  static Future<Null> vibration() async {
    if (Platform.isIOS) {
    } else {
      await _channel.invokeMethod('vibration');
    }
  }

  //set ring mode
  static Future<Null> setRingerMode(RingerMode mode) async {
    if (Platform.isIOS) {
    } else {
      print(mode.index);
      final Map params = <String, dynamic>{'mode': mode.index};
      await _channel.invokeMethod('setRingerMode', params);
    }
  }

  //set vibration
  static Future<String> get checkRingerMode async {
    String state;
    if (Platform.isIOS) {
    } else {
      var ringerId = await _channel.invokeMethod('checkRingerMode');
      switch (ringerId) {
        case 0:
          state = "silent";
          break;
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

  /// Check the application has access to change the DND settings
  static Future<bool> get isNotificationPolicyAccessGranted async {
    return await _channel.invokeMethod('isNotificationPolicyAccessGranted');
  }

  static void gotoPolicySettings() {
    _channel.invokeMethod('gotoPolicySettings');
  }

  /// Return true if the bluetooth is alreay turned on.
  ///
  /// Return false if the bluetooth is turned off.
  static Future<bool> get checkBluetooth async {
    bool b = await _channel.invokeMethod('checkBluetooth');
    if (b != null) {
      return b;
    }
    return null;
  }

  @Deprecated("old function")
  static Future<Null> silentMode(String mode) async {
    if (Platform.isIOS) {
    } else {
      final Map params = <String, dynamic>{'mode': mode};
      await _channel.invokeMethod('setMode', params);
    }
  }
}

enum RingerMode { silent, vibration, normal }
