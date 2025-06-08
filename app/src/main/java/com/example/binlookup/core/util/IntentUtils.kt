package com.example.binlookup.core.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri

object IntentUtils {

    fun openUrl(context: Context, url: String) {
        try {
            val formattedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                "https://$url"
            } else {
                url
            }
            val intent = Intent(Intent.ACTION_VIEW, formattedUrl.toUri())
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("IntentUtils", "Failed to open URL", e)
        }
    }

    fun makePhoneCall(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, "tel:$phoneNumber".toUri())
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("IntentUtils", "Failed to make phone call", e)
        }
    }

    fun openMap(context: Context, latitude: Double, longitude: Double) {
        try {
            val uri = "geo:$latitude,$longitude?q=$latitude,$longitude".toUri()
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude".toUri()
                )
                context.startActivity(browserIntent)
            }
        } catch (e: Exception) {
            Log.e("IntentUtils", "Failed to open map", e)
        }
    }
}