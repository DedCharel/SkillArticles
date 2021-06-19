package ru.skillbranch.skillarticles.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.text.DateFormat


const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.shortFormat(): String? {
    val pattern = if(this.isSameDay(Date())) "HH:mm" else "dd.MM.yy"
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun  Date.isSameDay(date: Date):Boolean{
    val day1 = this.time/DAY
    val day2 = date.time/DAY
    return  day1 == day2
}
fun Date.add(value:Int, units:TimeUnits = TimeUnits.SECOND): Date{
    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE-> value * MINUTE
        TimeUnits.HOUR-> value * HOUR
        TimeUnits.DAY -> value * DAY

    }
    this.time = time
    return  this

}

fun Date.humanizeDiff(date: Date= Date()): String {
    var isFuture = false
    var interval:Long = 0
    if (this>date){
        isFuture = true
        interval = (this.time- date.time)/SECOND
    } else {
        interval =  (date.time-this.time ) / SECOND
    }
    val message:String = when(interval){
        in 0..1 -> "только что"
        in 2..45 -> if (isFuture) "через несколько секунд" else "несколько секунд назад"
        in 46..75 -> if (isFuture) "через минуту" else "минуту назад"
        in 76..45*60 ->if (isFuture) "через ${TimeUnits.MINUTE.plural((interval/60).toInt())}" else "${TimeUnits.MINUTE.plural((interval/60).toInt())} назад"
        in 45*60..75*60 ->if (isFuture) "через час" else "час назад"
        in 75*60..22*3600 ->if (isFuture) "через ${TimeUnits.HOUR.plural((interval/3600).toInt())}" else "${TimeUnits.HOUR.plural((interval/3600).toInt())} назад"
        in 22*3600..26*3600 -> if (isFuture) "через день" else "день назад"
        in 26*3600..360*3600*24 ->if (isFuture) "через ${TimeUnits.DAY.plural((interval/3600/24).toInt())}" else "${TimeUnits.DAY.plural((interval/3600/24).toInt())} назад"
        else -> if (isFuture) "более чем через год" else "более года назад"
    }
    return "$message"
}







enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(int: Int): String {
        var result: String = ""
        val rem100: Int = int % 100
        val rem: Int = int % 10
        if (rem100 in 10..19) {
            result = when (this.name) {
                SECOND.name -> "$int секунд"
                MINUTE.name -> "$int минут"
                HOUR.name -> "$int часов"
                DAY.name -> "$int дней"
                else -> ""
            }

        } else {
            when (rem) {
                0 -> {
                    result = when (this.name) {
                        SECOND.name -> "$int секунд"
                        MINUTE.name -> "$int минут"
                        HOUR.name -> "$int часов"
                        DAY.name -> "$int дней"
                        else -> ""
                    }
                }
                1 -> {
                    result = when (this.name) {
                        SECOND.name -> "$int секунду"
                        MINUTE.name -> "$int минуту"
                        HOUR.name -> "$int час"
                        DAY.name -> "$int день"
                        else -> ""
                    }
                }
                in 2..4 -> {
                    result = when (this.name) {
                        SECOND.name -> "$int секунды"
                        MINUTE.name -> "$int минуты"
                        HOUR.name -> "$int часа"
                        DAY.name -> "$int дня"
                        else -> ""
                    }
                }
                in 5..9 -> {
                    result = when (this.name) {
                        SECOND.name -> "$int секунд"
                        MINUTE.name -> "$int минут"
                        HOUR.name -> "$int часов"
                        DAY.name -> "$int дней"
                        else -> ""
                    }
                }
            }
        }


        return result
    }
}
