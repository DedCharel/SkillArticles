package ru.skillbranch.skillarticles.data.delegates

import android.annotation.SuppressLint
import ru.skillbranch.skillarticles.data.local.PrefManager
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class PrefDelegate<T>(private val defaultValue: T) {
    private var value: T? = null
    operator fun provideDelegate(
        thisRef: PrefManager,
        property: KProperty<*>
    ): ReadWriteProperty<PrefManager, T?> {
        val key = property.name
        return object : ReadWriteProperty<PrefManager, T?> {
            @Suppress("UNCHECKED_CAST")
            override fun getValue(thisRef: PrefManager, property: KProperty<*>): T? {
                return when (value) {
                    is Boolean -> thisRef.preferences.getBoolean(key, value as Boolean) as T
                    is String -> thisRef.preferences.getString(key, value as String) as T
                    is Float -> thisRef.preferences.getFloat(key, value as Float) as T
                    is Int -> thisRef.preferences.getInt(key, value as Int) as T
                    is Long -> thisRef.preferences.getLong(key, value as Long) as T
                    else -> throw IllegalStateException("Type of property  is not supported")
                }


            }

            @SuppressLint("CommitPrefEdits")
            override fun setValue(thisRef: PrefManager, property: KProperty<*>, value: T?) {
                thisRef.preferences.edit().apply() {
                    when (value) {
                        is Boolean -> putBoolean(key, value)
                        is String -> putString(key, value)
                        is Float -> putFloat(key, value)
                        is Int -> putInt(key, value)
                        is Long -> putLong(key, value)
                        else -> throw IllegalStateException("Type of property  is not supported")
                    }
                }
            }
        }
    }
}
