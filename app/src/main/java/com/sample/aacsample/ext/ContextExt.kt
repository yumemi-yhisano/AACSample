package com.sample.aacsample.ext

import android.content.Context
import com.sample.aacsample.data.PrefKey

inline fun <reified T> Context.getPrefVal(key: PrefKey, default: T? = null) : T {
    return getSharedPreferences("aac_pref", Context.MODE_PRIVATE).let {
        when(T::class) {
            String::class -> it.getString(key.name.toLowerCase(), default as? String? ?: "") as T
            Int::class -> it.getInt(key.name.toLowerCase(), default as? Int? ?: 0) as T
            Long::class -> it.getLong(key.name.toLowerCase(), default as? Long? ?:0L) as T
            Boolean::class -> it.getBoolean(key.name.toLowerCase(), default as? Boolean? ?:false) as T
            Float::class -> it.getFloat(key.name.toLowerCase(), default as? Float? ?:0F) as T
            else -> throw IllegalStateException("not support class[${T::class}]")
        }
    }
}

fun Context.putPrefVal(key: PrefKey, value: Any) {
    getSharedPreferences("aac_pref", Context.MODE_PRIVATE).edit().also {
        when(value) {
            is String -> it.putString(key.name.toLowerCase(), value)
            is Int -> it.putInt(key.name.toLowerCase(), value)
            is Long -> it.putLong(key.name.toLowerCase(), value)
            is Boolean -> it.putBoolean(key.name.toLowerCase(), value)
            is Float -> it.putFloat(key.name.toLowerCase(), value)
            else -> throw IllegalStateException("not support value class[${value::class.java}]")
        }
    }.apply()
}
