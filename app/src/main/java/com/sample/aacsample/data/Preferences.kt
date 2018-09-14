package com.sample.aacsample.data

import android.content.Context

class Preferences {
    companion object {

        inline fun <reified T> getVal(context: Context, key: PrefKey, clazz:Class<T>, default: T? = null) : T {
            return getPref(context).let {
//                Log.d("__DEBUG__", "T:${T::class.java}, Boolean:${Boolean::javaClass}")
                when(clazz) {
                    String::class.java -> it.getString(key.name.toLowerCase(), default as? String? ?: "") as T
                    Int::class.java -> it.getInt(key.name.toLowerCase(), default as? Int? ?: 0) as T
                    Long::class.java -> it.getLong(key.name.toLowerCase(), default as? Long? ?:0L) as T
                    Boolean::class.java -> it.getBoolean(key.name.toLowerCase(), default as? Boolean? ?:false) as T
                    Float::class.java -> it.getFloat(key.name.toLowerCase(), default as? Float? ?:0F) as T
                    else -> throw IllegalArgumentException("not support class[${T::class.java}]")
                }
            }
        }

        fun putVal(context: Context, key: PrefKey, value: Any) {
            getPref(context).edit().also {
                when(value) {
                    value is String -> it.putString(key.name.toLowerCase(), value as String)
                    value is Int -> it.putInt(key.name.toLowerCase(), value as Int)
                    value is Long -> it.putLong(key.name.toLowerCase(), value as Long)
                    value is Boolean -> it.putBoolean(key.name.toLowerCase(), value as Boolean)
                    value is Float -> it.putFloat(key.name.toLowerCase(), value as Float)
                    else -> throw IllegalArgumentException("not support value class[${value::class.java}]")
                }
            }.apply()
        }

        fun getPref(context: Context) = context.getSharedPreferences("aac_pref", Context.MODE_PRIVATE)
    }
}

enum class PrefKey {
    INITIALIZED,
    LOADED
}