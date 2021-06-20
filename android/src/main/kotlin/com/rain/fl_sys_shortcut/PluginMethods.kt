package com.rain.fl_sys_shortcut

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.media.AudioManager
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.util.Log


class PluginMethods : BaseMethods {

    override fun checkWifiAvailability(context: Context): Boolean {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }

    override fun checkBluetoothAvailability(): Boolean {
        val bt: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bt == null) {
            Log.d(
                "bluetooth module",
                "checkBluetoothAvailability: emulator doesn't support bluetooth"
            )
        }
        return bt!!.isEnabled
    }

    override fun checkRingerMode(context: Context): Int {
        val audioManager: AudioManager =
            context.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val result: Int = audioManager.ringerMode
        Log.d("checkRingerMode", "result: $result")
        return result
    }



    override fun switchRingerMode(ringerMode: Int, context: Context) {
        val audioManager: AudioManager =
            context.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (ringerMode == 1) {
                audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
        } else {
            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        }
    }

    override fun switchWifi(isEnabled: Boolean, context: Context) {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) wifiManager.isWifiEnabled = isEnabled
    }

    override fun switchBluetooth(isEnabled: Boolean) {
        val btAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (btAdapter!!.isEnabled) btAdapter.disable() else btAdapter.enable()
    }

}