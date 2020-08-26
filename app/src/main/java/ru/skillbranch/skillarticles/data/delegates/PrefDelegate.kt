package ru.skillbranch.skillarticles.data.delegates

import ru.skillbranch.skillarticles.data.local.PrefManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrefDelegate<T>(private val defaultValue: T): ReadWriteProperty<PrefManager, T?> {
    private var value: T? = null
    override fun getValue(thisRef: PrefManager, property: KProperty<*>): T?  {
        val key = property.name
       return when (value){
            is Boolean -> thisRef.preferences.getBoolean(key,value as Boolean) as T
            is String -> thisRef.preferences.getString(key,value as String) as T
            is Float -> thisRef.preferences.getFloat(key,value as Float) as T
            is Int -> thisRef.preferences.getInt(key,value as Int) as T
            is Long -> thisRef.preferences.getLong(key,value as Long) as T
            else -> throw IllegalStateException("Type of property  is not supported")
        }


    }

    override fun setValue(thisRef: PrefManager, property: KProperty<*>, value: T?) {
            val key = property.name
            thisRef.preferences.edit().apply(){
        when(value) {
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