package com.hindgyan.hanumanchalisa.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*
class LanguageUtil {
    companion object
    {
        fun Context.setAppLocale(language: String): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = resources.configuration
            config.setLocale(locale)
            config.setLayoutDirection(locale)
            return createConfigurationContext(config)
        }
    }
}