package io.github.vnicius.picpay.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Network utils functions
 */
class NetworkUtils {
    companion object {
        /**
         * Check if has any connection
         * @param context of the app
         */
        fun hasConnection(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnected == true
        }
    }
}