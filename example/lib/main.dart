import 'package:flutter/material.dart';

import 'package:fl_sys_shortcut/fl_sys_shortcut.dart';


void main() => runApp(Main());

class Main extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('System Shortcuts'),
        ),
        body: MyApp(),
      ),
    );
  }
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Body();
  }
}

class Body extends StatelessWidget {
  const Body({
    Key key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
        child: ListView(
      children: <Widget>[
        FlatButton(
          child: Text("Wifi"),
          onPressed: () async {
            await FlSysShortcut.wifi();
          },
        ),
        FlatButton(
          child: Text("Check Wifi"),
          onPressed: () async {
            bool b = await FlSysShortcut.checkWifi;
            Scaffold.of(context).showSnackBar(
              SnackBar(
                content: Text("Wifi Turned On Check - $b"),
                duration: Duration(seconds: 2),
              ),
            );
          },
        ),
        FlatButton(
          child: Text("Set Bluetooth"),
          onPressed: () async {
            await FlSysShortcut.bluetooth();
          },
        ),
        
        FlatButton(
          child: Text("Set Ringer Mode normal"),
          onPressed: () async {
            await FlSysShortcut.setRingerMode(RingerMode.normal);
          },
        ),
        FlatButton(
          child: Text("Set Ringer Mode silent "),
          onPressed: () async {
            await FlSysShortcut.setRingerMode(RingerMode.silent);
          },
        ),
        FlatButton(
          child: Text("Set Ringer mode Vibration"),
          onPressed: () async {
            await FlSysShortcut.setRingerMode(RingerMode.vibration);
          },
        ),
        FlatButton(
          child: Text("check ringer mode"),
          onPressed: () async {
          String state= await FlSysShortcut.checkRingerMode;
            Scaffold.of(context).showSnackBar(
              SnackBar(
                content: Text("Ringer mode  Check - $state"),
                duration: Duration(seconds: 2),
              ),
            );
          },
        ),
        
        FlatButton(
          child: Text("Check Bluetooth"),
          onPressed: () async {
            bool b = await FlSysShortcut.checkBluetooth;
            Scaffold.of(context).showSnackBar(
              SnackBar(
                content: Text("Bluetooth Turned On Check - $b"),
                duration: Duration(seconds: 2),
              ),
            );
          },
        ),
      ],
    ));
  }
}
