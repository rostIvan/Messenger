package trickyquestion.messenger.ui.mvp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trickyquestion.messenger.ui.interfaces.BasePresenter

abstract class MvpPresenter<V, R>
constructor(var view: V, var router: R) : BasePresenter {
    // empty by default
    override fun onAttach(context: Context?) {}
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {}
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {}
    override fun onStart() {}
    override fun onCreate(savedInstanceState: Bundle?) {}
    override fun onDestroy() {}
    override fun onFinish() {}
    override fun onResume() {}
    override fun onStop() {}
    override fun onDestroyView() {}
    override fun onDetach() {}
}