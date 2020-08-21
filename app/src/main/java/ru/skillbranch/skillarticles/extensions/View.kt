package ru.skillbranch.skillarticles.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.widget.NestedScrollView

fun View.setMarginOptionally(left:Int = marginLeft, top : Int = marginTop, right : Int = marginRight, bottom : Int = marginBottom){
    var param = this.layoutParams as ViewGroup.MarginLayoutParams
    param.setMargins(left, top, right, bottom)

}