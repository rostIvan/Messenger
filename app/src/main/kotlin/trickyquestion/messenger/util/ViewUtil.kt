package trickyquestion.messenger.util

import android.content.Context
import android.graphics.PorterDuff
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.melnykov.fab.FloatingActionButton
import com.ogaclejapan.smarttablayout.SmartTabLayout
import trickyquestion.messenger.R

fun EditText.setLineColor(color: Int) {
    this.background.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}

fun applyThemeColor(views: List<View>, color: Int) {
    for (view in views) {
        if (view is Toolbar || view is AppBarLayout || view is SmartTabLayout) view.setBackgroundColor(color)
        if (view is FloatingActionButton) view.colorNormal = color
    }
}

fun Context.redColor() : Int = ContextCompat.getColor(this, R.color.colorRed)
fun Context.greenColor() : Int = ContextCompat.getColor(this, R.color.colorPrimaryGreen)
fun Context.whiteColor() : Int = ContextCompat.getColor(this, R.color.colorWhite)
fun Context.blackColor() : Int = ContextCompat.getColor(this, R.color.colorBlack)

fun EditText.onTextChanged(afterTextChange: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            afterTextChange.invoke(s.toString())
        }
    })
}

fun EditText.onTextChanged(onTextChange: OnTextChange) {
    this.onTextChanged { s ->  onTextChange.doSomething(s) }
}

interface OnTextChange {
    fun doSomething(text: String)
}
