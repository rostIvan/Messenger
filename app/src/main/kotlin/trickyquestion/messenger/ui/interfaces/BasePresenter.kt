package trickyquestion.messenger.ui.interfaces

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trickyquestion.messenger.buisness.BaseEventManager

interface BasePresenter {
    fun attach(eventManager: BaseEventManager) {}
    fun onAttach(context: Context?) {}
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {}
    fun onViewCreated(view: View?, savedInstanceState: Bundle?) {}
    fun onCreate(savedInstanceState: Bundle?) {}
    fun onStart() {}
    fun onResume() {}
    fun onStop() {}
    fun onDestroyView() {}
    fun onDestroy() {}
    fun onDetach() {}
    fun onFinish() {}
}
