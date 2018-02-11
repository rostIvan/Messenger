package trickyquestion.messenger.util

import android.app.Activity
import android.util.Log
import android.widget.Toast

fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Activity.log(text: CharSequence) {
    Log.d(this.javaClass.name, text.toString())
}