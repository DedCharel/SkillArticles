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


            override fun getValue(thisRef: PrefManager, property: KProperty<*>): T? {
                if (value == null) {
                    @Suppress("UNCHECKED_CAST")
                    value = when (defaultValue) {
                        is Boolean -> thisRef.preferences.getBoolean(key, defaultValue as Boolean) as T
                        is String -> thisRef.preferences.getString(key, defaultValue as String) as T
                        is Float -> thisRef.preferences.getFloat(key, defaultValue as Float) as T
                        is Int -> thisRef.preferences.getInt(key, defaultValue as Int) as T
                        is Long -> thisRef.preferences.getLong(key, defaultValue as Long) as T
                        else -> throw IllegalStateException("Type of property  is not supported")
                    }
                }
            return value
            }


            override fun setValue(thisRef: PrefManager, property: KProperty<*>, value: T?) {
                with(thisRef.preferences.edit()) {
                    when (value) {
                        is Boolean -> putBoolean(key, value)
                        is String -> putString(key, value)
                        is Float -> putFloat(key, value)
                        is Int -> putInt(key, value)
                        is Long -> putLong(key, value)
                        else -> throw IllegalStateException("Only primitive type can be stored into Preference")
                    }
                    apply()
                }
                this@PrefDelegate.value = value
            }
        }
    }
}
