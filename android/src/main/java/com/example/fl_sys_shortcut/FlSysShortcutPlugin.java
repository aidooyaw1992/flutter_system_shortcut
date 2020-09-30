package com.example.fl_sys_shortcut;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlSysShortcutPlugin */
public class FlSysShortcutPlugin implements FlutterPlugin, MethodCallHandler {
  private static final String TAG = "FlSysShortcutPlugin";
  private static NotificationManager notificationManager;

  private static Context context;

//  private final int DO_NOT_DISTURB_REQUEST_CODE = 10001;
//  private final int ON_DO_NOT_DISTURB_CALLBACK_CODE = 100;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    context = flutterPluginBinding.getApplicationContext();
    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "fl_sys_shortcut");
    channel.setMethodCallHandler(new FlSysShortcutPlugin());
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "fl_sys_shortcut");
//    registrar.addRequestPermissionsResultListener(instance);
    channel.setMethodCallHandler(new FlSysShortcutPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method){
      case "wifi":
        wifi();
        break;
      case "checkWifi":
        result.success(checkWifi());
        break;
      case "bluetooth":
        bluetooth();
        break;
//      case "setRingerMode":
//        final int id = call.argument("mode");
//        setRingerMode(id);
//        break;
      case "vibration":
        setVibration();
        break;
      case "checkRingerMode":
        result.success(checkRingerMode());
        break;
      case "checkBluetooth":
        result.success(checkBluetooth());
        break;
      default:
        result.notImplemented();
    }
  }

  private void setRingerMode(int id) {
    Log.d(TAG, "ring Mode: "+ id);
    switch (id){
      case 0:
//        setSilentMode();
      break;
      case 1:
        setVibration();
      break;
      case 2:
        setNormal();
      default:
    }
  }


  private int checkRingerMode(){
    AudioManager audioManager = (AudioManager) this.context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    int result =  audioManager.getRingerMode();
    Log.d(TAG, "checkRingerMode: "+ result);
    return result;
  }

  private void setNormal(){
    AudioManager audioManager = (AudioManager) this.context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
  }
  private void setVibration(){
    AudioManager audioManager = (AudioManager) this.context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
  }

  private void wifi() {
    WifiManager wifiManager = (WifiManager) this.context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    if (wifiManager.isWifiEnabled()) {
      wifiManager.setWifiEnabled(false);
    } else {
      wifiManager.setWifiEnabled(true);
    }
  }

  private boolean checkWifi() {
    WifiManager wifiManager = (WifiManager) this.context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    return wifiManager.isWifiEnabled();
  }

//  private void setSilentMode() {
//    if(checkNotificationPolicyPermission()){
//
//      NotificationManager notificationManager = (NotificationManager) this.activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && notificationManager.isNotificationPolicyAccessGranted()) {
//          notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
//          AudioManager audioManager = (AudioManager) this.activity.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
//          audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//
//      }else {
//        Log.d(TAG, "silentMode: notification access policy wasnt granted");
//        // Open Setting screen to ask for permisssion
//        Intent intent = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//          intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//        }
//        activity.startActivityForResult( intent, ON_DO_NOT_DISTURB_CALLBACK_CODE );
//
//      }
//
//    }else{
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        ActivityCompat.requestPermissions(this.activity, new String[] {Manifest.permission.ACCESS_NOTIFICATION_POLICY}, DO_NOT_DISTURB_REQUEST_CODE);
//      }
//    }
//  }


  private void bluetooth() {
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (mBluetoothAdapter.isEnabled()) {
      mBluetoothAdapter.disable();
    } else {
      mBluetoothAdapter.enable();
    }
  }
//  private boolean checkNotificationPolicyPermission(){
//    Log.d(TAG, "checking Permissions");
//    boolean status = false;
//    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//      status = (ActivityCompat.checkSelfPermission(this.activity.getApplicationContext(), Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED);
//    }
//    Log.d(TAG, "checkNotificationPolicyPermission: "+status);
//    return status;
//  }

  private boolean checkBluetooth() {
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    return mBluetoothAdapter.isEnabled();
  }
  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
//    MethodChannel channel.setMethodCallHandler(null);
  }


  private void permissionDenied(){
    Log.d(TAG, "permissionDenied");
  }


}