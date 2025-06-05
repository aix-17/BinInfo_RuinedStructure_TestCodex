package com.example.binlookup.core.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object IntentUtils {
    
    fun openUrl(context: Context, url: String) {
        try {
            val formattedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                "https://$url"
            } else {
                url
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl))
            context.startActivity(intent)
        } catch (e: Exception) {
            // Handle error silently or show toast
        }
    }
    
    fun makePhoneCall(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            context.startActivity(intent)
        } catch (e: Exception) {
            // Handle error silently or show toast
        }
    }
    
    fun openMap(context: Context, latitude: Double, longitude: Double) {
        try {
            val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // Fallback to browser
                val browserIntent = Intent(Intent.ACTION_VIEW, 
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"))
                context.startActivity(browserIntent)
            }
        } catch (e: Exception) {
            // Handle error silently or show toast
        }
    }
}