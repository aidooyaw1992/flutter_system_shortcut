import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:fl_sys_shortcut/fl_sys_shortcut.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  bool wifiStatus = false;
  bool bleStatus = false;

  @override
  void initState() {
    super.initState();
    initPlatformState();

    FlSysShortcut.checkWifi.then((value) {
      setState(() {
        wifiStatus = value;
      });
    });

    // checkBluetooth();
  }

  Future<void> checkBluetooth() async {
    bool? _status;
    try {
      _status =
          await FlSysShortcut.checkBle;
    } on PlatformException {
      print("no blue tooth found");
    }
    if (!mounted) return;
    setState(() {
      bleStatus = _status ?? false;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    try {
      platformVersion =
          await FlSysShortcut.platformVersion ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }
    if (!mounted) return;
    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Container(
          child: Column(
            children: [
              Text('Running on: $_platformVersion\n'),
              SizedBox(height: 10),
              StatefulBuilder(
                builder: (context, state) {
                  return Column(
                    children: [
                      Text("Wifi State: $wifiStatus"),
                      Text("bluetooth State: $bleStatus"),
                    ],
                  );
                },
              ),
              Text("Switch to turn on/off wifi"),
              SizedBox(height: 10),
              Switch(
                  value: wifiStatus,
                  onChanged: (value) async {
                    setState(() {
                      wifiStatus = value;
                    });
                    await FlSysShortcut.switchWifi(wifiStatus);
                  }),
              SizedBox(height: 10),
              Text("Switch to turn on/off bluetooth"),
              SizedBox(height: 10),
              Switch(
                  value: bleStatus,
                  onChanged: (value) async {
                    setState(() {
                      bleStatus = value;
                    });
                    await FlSysShortcut.switchBluetooth(bleStatus);
                  }),
              ElevatedButton(
                onPressed: () async {
                  var status = await FlSysShortcut.checkRingerMode;
                  print(status);
                },
                child: Text("Check Ringer mode"),
              ),
              ElevatedButton(
                onPressed: () async {
                   await FlSysShortcut.switchRingerMode(RingerMode.vibration);
                },
                child: Text("Set Vibration mode"),
              ),
              ElevatedButton(
                onPressed: () async {
                   await FlSysShortcut.switchRingerMode(RingerMode.normal);
                },
                child: Text("Set Normal mode"),
              ),
              ElevatedButton(
                onPressed: () async {
                   await FlSysShortcut.setDoNotDisturbMode();
                },
                child: Text("Set Do not disturb mode"),
              ),
              ElevatedButton(
                onPressed: () async {
                  await FlSysShortcut.checkAirplaneMode;
                },
                child: Text("Check airplane mode"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
