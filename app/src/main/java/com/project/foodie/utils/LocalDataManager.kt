package com.project.foodie.utils

import android.content.Context
import android.content.SharedPreferences


class LocalDataManager {
    companion object{
        fun setSharedPreference(context: Context, key: String?, value: String?) {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            val edit = sharedPref.edit()
            edit.putString(key, value)
            edit.apply()
        }

        fun getSharedPreference(context: Context, key: String?, defaultValue: String?): String? {
            return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
                .getString(key, defaultValue)
        }

        fun clearSharedPreference(context: Context) {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
            val edit = sharedPref.edit()
            edit.clear()
            edit.apply()
        }

        fun removeSharedPreference(context: Context, key: String?) {
            val sharedPref: SharedPreferences =
                context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
            val edit = sharedPref.edit()
            edit.remove(key)
            edit.apply()
        }
    }

}