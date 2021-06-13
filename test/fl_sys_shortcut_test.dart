import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:fl_sys_shortcut/fl_sys_shortcut.dart';

void main() {
  const MethodChannel channel = MethodChannel('fl_sys_shortcut');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await FlSysShortcut.platformVersion, '42');
  });
}
