package trickyquestion.messenger.ui.interfaces

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface FragmentLifecycleCallbacks {
    fun onAttach(context: Context?)
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    fun onStart()
    fun onResume()
    fun onStop()
    fun onDestroyView()
    fun onDetach()
}