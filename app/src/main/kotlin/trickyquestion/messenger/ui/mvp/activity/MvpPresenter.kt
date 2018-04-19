package trickyquestion.messenger.ui.mvp.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trickyquestion.messenger.ui.interfaces.ActivityLifecycleCallbacks
import trickyquestion.messenger.ui.interfaces.BasePresenter

abstract class MvpPresenter<V, R>
constructor(var view: V, var router: R) : ActivityLifecycleCallbacks, BasePresenter {

    // empty by default
    override fun onCreate(savedInstanceState: Bundle?) {}
    override fun onDestroy() {}
    override fun onStart() {}
    override fun onResume() {}
    override fun onStop() {}
    override fun onFinish() {}
    override fun onAttach(context: Context?) {}
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {}
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {}
    override fun onDestroyView() {}
    override fun onDetach() {}
}