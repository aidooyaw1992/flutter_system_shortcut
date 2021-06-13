package com.rain.fl_sys_shortcut

import android.app.Activity
import android.content.Context

interface BaseMethods {
    fun checkWifiAvailability(context: Context): Boolean
    fun checkBluetoothAvailability(): Boolean
    fun checkRingerMode(context: Context): Int

    fun switchRingerMode(ringerMode: Int, context: Context)
    fun switchWifi(isEnabled: Boolean, context: Context)
    fun switchBluetooth(isEnabled: Boolean)
}