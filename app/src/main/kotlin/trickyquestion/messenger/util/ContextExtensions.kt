package trickyquestion.messenger.util

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.log(text: CharSequence) {
    Log.d(this.javaClass.name, text.toString())
}