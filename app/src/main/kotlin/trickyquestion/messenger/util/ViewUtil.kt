package trickyquestion.messenger.util

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import trickyquestion.messenger.R

fun EditText.setLineColor(color: Int) {
    this.background.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}

fun Context.redColor() : Int = ContextCompat.getColor(this, R.color.colorRed)
fun Context.greenColor() : Int = ContextCompat.getColor(this, R.color.colorPrimaryGreen)

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