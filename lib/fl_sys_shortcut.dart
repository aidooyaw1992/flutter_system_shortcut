
import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class FlSysShortcut {
  static const MethodChannel _channel =
      const MethodChannel('fl_sys_shortcut');

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

  /// Return true if the bluetooth is alreay turned on.
  ///
  /// Return false if the bluetooth is turned off.
  static Future<bool> get checkBluetooth async {
    bool b = await _channel.invokeMethod('checkBluetooth');
    return b;
  }


  static Future<Null> silentMode(String mode) async{
    if (Platform.isIOS) {
    } else {
      final Map params = <String, dynamic> {
      'mode': mode
    };
      await _channel.invokeMethod('setMode', params);
    }
  }
}
