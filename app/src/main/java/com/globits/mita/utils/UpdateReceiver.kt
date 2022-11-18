package com.globits.mita.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast


class UpdateReceiver(var status :Boolean) : BroadcastReceiver() {




    override fun onReceive(context: Context, intent: Intent?) {
        if (isOnline(context)) {
            if (status)
            {
                status = !status
            }
            else
            {
                Toast.makeText(context, "Internet Connection", Toast.LENGTH_SHORT).show()
            }

        } else {

            Toast.makeText(context, "Internet Disconnection", Toast.LENGTH_SHORT).show()
        }
    }

    fun isOnline(context: Context): Boolean {
        val cm = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

//    override fun onReceive(context: Context, intent: Intent) {
//        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
//            if (netwold(context)) {
//                Toast.makeText(context, "Internet Connection", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Internet Disconnection", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    //kiem tra mang
//    private fun netwold(context: Context): Boolean {
//        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            ?: return false
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val network = manager.activeNetwork ?: return false
//            val networkCapabilities = manager.getNetworkCapabilities(network)
//            networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
//        } else {
//            val networkInfo = manager.activeNetworkInfo
//            networkInfo != null && networkInfo.isConnected
//        }
//    }

}