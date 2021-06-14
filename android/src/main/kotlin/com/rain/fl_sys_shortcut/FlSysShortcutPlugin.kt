package com.rain.fl_sys_shortcut

import Constants.DO_NOT_DISTURB_REQUEST_CODE
import Constants.METHOD_CHANNEL_NAME
import Constants.ON_DO_NOT_DISTURB_CALLBACK_CODE
import Constants.WIFI_SETTINGS_REQUEST_CODE
import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry


/** FlSysShortcutPlugin */
class FlSysShortcutPlugin : FlutterPlugin, MethodCallHandler, ActivityAware,
    PluginRegistry.RequestPermissionsResultListener, PluginRegistry.ActivityResultListener {
    private lateinit var channel: MethodChannel
    private lateinit var context: Context
    private lateinit var notificationManager: NotificationManager

    private var _activity: Activity? = null
    private val activity: Activity
        get() = _activity!!
    private lateinit var pluginMethods: PluginMethods

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, METHOD_CHANNEL_NAME)
        channel.setMethodCallHandler(this)
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        context = flutterPluginBinding.applicationContext
        pluginMethods = PluginMethods()
    }

    companion object {
        @JvmStatic
        fun registerWith(registrar: PluginRegistry.Registrar) {
            val channel = MethodChannel(registrar.messenger(), METHOD_CHANNEL_NAME)
            channel.setMethodCallHandler(FlSysShortcutPlugin())
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {

            "switch_wifi" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val intent = Intent(Settings.Panel.ACTION_WIFI)
                    activity.startActivityForResult(intent, WIFI_SETTINGS_REQUEST_CODE)
                } else {
                    val isEnabled: Boolean = call.argument("is_enabled")!!
                    pluginMethods.switchWifi(isEnabled = isEnabled, context = context)
                }
            }

            "check_wifi" -> {
                result.success(pluginMethods.checkWifiAvailability(context))
            }

            "check_bluetooth" -> {
                result.success(pluginMethods.checkBluetoothAvailability())
            }

            "switch_bluetooth" -> {
                val isEnabled: Boolean = call.argument("is_enabled")!!
                pluginMethods.switchBluetooth(isEnabled = isEnabled)
            }

            "switch_ringer_mode" -> {
                val mode: Int = call.argument("mode")!!
                Log.d("switch_ringer_mode", "mode: $mode")
                pluginMethods.switchRingerMode(ringerMode = mode, context = context)
            }

            "check_ringer_mode" -> {
                result.success(pluginMethods.checkRingerMode(context = context))
            }

            "set_do_not_disturb" -> {
                setSilentMode()
            }
            "getPlatformVersion" -> result.success("Android ${Build.VERSION.RELEASE}")

            "is_notification_policy_access_granted" -> result.success(isNotificationPolicyAccessGranted())

            "goto_policy_settings"-> gotoPolicySettings()

            "check_airplane_mode" -> checkAirplaneMode()
            else -> result.notImplemented()
        }
    }
    private fun isAboveMarshmello(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private fun isNotificationPolicyAccessGranted(): Boolean {
        return if (!isAboveMarshmello()) {
            false
        } else notificationManager.isNotificationPolicyAccessGranted
    }

    private fun gotoPolicySettings() {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    private fun checkAirplaneMode(): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Settings.System.getInt(
                    context.contentResolver,
                    Settings.System.AIRPLANE_MODE_ON, 0
                ) == 1
            } else {
                Settings.Global.getInt(
                    context.contentResolver,
                    Settings.Global.AIRPLANE_MODE_ON, 0
                ) == 1
            }
    }


    private fun checkNotificationPolicyPermission(): Boolean {
        Log.d("permissions", "checking Permissions")
        var status = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            status = ActivityCompat.checkSelfPermission(
                activity.applicationContext,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            ) == PackageManager.PERMISSION_GRANTED
        }
        Log.d("permissions", "checkNotificationPolicyPermission: $status")
        return status
    }

    private fun setSilentMode() {
        if (checkNotificationPolicyPermission()) {
            val notificationManager =
                activity.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && notificationManager.isNotificationPolicyAccessGranted) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
                val audioManager =
                    activity.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
            } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                Log.d("silent mode", "silentMode: notification access policy wasn't granted")
                // Open Setting screen to ask for permission
                val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                activity.startActivityForResult(intent, ON_DO_NOT_DISTURB_CALLBACK_CODE)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
                    DO_NOT_DISTURB_REQUEST_CODE
                )
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        _activity = binding.activity
        binding.addRequestPermissionsResultListener(this)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        TODO("Not yet implemented")
    }

    override fun onDetachedFromActivity() {
        _activity = null
    }

    private fun permissionDenied() {
        Log.d("permission denied", "permissionDenied")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>?,
        grantResults: IntArray?
    ): Boolean {
        if (requestCode == DO_NOT_DISTURB_REQUEST_CODE) {
            if (grantResults?.size!! > 0 && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                Log.d(
                    "permission result",
                    "onRequestPermissionsResult: notification access policy is granted"
                )
                return true
            } else {
                permissionDenied()
            }
        }
        return false
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode == ON_DO_NOT_DISTURB_CALLBACK_CODE) {
            Log.d("onActivityResult", "result: successful")
            return true
        }
        return false
    }

}
