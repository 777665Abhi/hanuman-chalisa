package com.hindgyan.hanumanchalisa.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharePreData {
    companion object {
         const val name = "SharePreData"
         const val LOGIN_KEY = "login"
         const val LANGUAGE_KEY = "language"

        /**Login preference*/
        fun setLoginStatus(activity: Activity, loginStatus: Boolean) {
            val sharedPreferences: SharedPreferences = activity.getSharedPreferences(name, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(LOGIN_KEY, loginStatus)
            editor.commit()
        }

        fun  getLoginStatus(activity: Activity):Boolean
        {
            val sharedPreferences: SharedPreferences = activity.getSharedPreferences(name, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(LOGIN_KEY,false)
        }

        /**Language preference*/
        fun setLanguage(activity: Activity,language:String)
        {
            val sharedPreferences: SharedPreferences = activity.getSharedPreferences(name, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(LANGUAGE_KEY, language)
            editor.commit()
        }

        fun getLanguage(activity: Activity): String
        {
            val sharedPreferences: SharedPreferences = activity.getSharedPreferences(name, Context.MODE_PRIVATE)
            return sharedPreferences.getString(LANGUAGE_KEY,"hi").toString()
        }
    }

}