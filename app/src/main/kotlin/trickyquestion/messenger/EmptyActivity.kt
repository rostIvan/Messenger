package trickyquestion.messenger

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle

@SuppressLint("Registered")
class EmptyActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toast("Hello!")
        log("onCreate")
    }
}