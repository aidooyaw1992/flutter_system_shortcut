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

  /// Toggle bluetooth.
  ///
  /// If it is already turned on bluetooth ( ) will turn it off
  /// else it'll turn it on
  static Future<Null> bluetooth() async {
    if (Platform.isIOS) {
    } else {
      await _channel.invokeMethod('bluetooth');
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

  /// Return true if the bluetooth is alreay turned on.
  ///
  /// Return false if the bluetooth is turned off.
  static Future<bool> get checkBluetooth async {
    bool b = await _channel.invokeMethod('checkBluetooth');
    return b;
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
