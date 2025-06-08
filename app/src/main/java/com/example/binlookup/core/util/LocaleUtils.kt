package com.example.binlookup.core.util

import android.app.Activity
import android.content.Context
import android.os.LocaleList
import java.util.Locale

object LocaleUtils {
    fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val config = resources.configuration
        config.setLocale(locale)
        config.setLocales(LocaleList(locale))
        resources.updateConfiguration(config, resources.displayMetrics)

        if (context is Activity) {
            context.recreate()
        }
    }
}
