package trickyquestion.messenger.ui.abstraction.interfaces

import android.os.Bundle

interface LifecycleCallbacks {
    fun onCreate(bundle: Bundle?)
    fun onDestroy()
    fun onStart()
    fun onResume()
    fun onStop()
}