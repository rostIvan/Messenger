package trickyquestion.messenger.ui.util

import android.content.Context
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.context, message, duration).show()
}

fun Fragment.toast(message: CharSequence) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun Context.log(text: CharSequence) {
    Log.d(this.javaClass.name, text.toString())
}