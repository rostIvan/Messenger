package trickyquestion.messenger.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import trickyquestion.messenger.util.log
import trickyquestion.messenger.util.toast

@SuppressLint("Registered")
class EmptyActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toast("Hello!")
        log("onCreate")
    }
}