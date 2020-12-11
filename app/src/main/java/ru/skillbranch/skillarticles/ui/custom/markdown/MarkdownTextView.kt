package ru.skillbranch.skillarticles.ui.custom.markdown

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.text.Spannable
import android.text.Spanned
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.core.graphics.withTranslation
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.extensions.attrValue

@SuppressLint("ViewConstructor")
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
class MarkdownTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr){



//    override var fontSize: Float = fontSize
//        set(value) {
//            textSize = value
//            field = value
//        }
//
//    override val spannableContent: Spannable
//        get() = text as Spannable
//
//    val color = context.attrValue(R.attr.colorOnBackground) //colorOnBackground
//    private val focusRect = Rect()

    private val searchBgHelper = SearchBgHelper(context)


    override fun onDraw(canvas: Canvas) {
        if (text is Spanned && layout != null) {
            canvas.withTranslation(totalPaddingLeft.toFloat(), totalPaddingTop.toFloat()) {
                searchBgHelper.draw(canvas, text as Spanned, layout)
            }
            super.onDraw(canvas)
        }
    }
}