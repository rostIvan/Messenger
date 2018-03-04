package trickyquestion.messenger.ui.abstraction.mvp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trickyquestion.messenger.ui.abstraction.activity.ApplicationRouter
import trickyquestion.messenger.ui.abstraction.interfaces.BaseRouter

abstract class MvpPresenter<V: MvpView, R: BaseRouter>
constructor(var view: V, var router: R) {

    // empty by default
    open fun onAttach(context: Context?) {}
    open fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {}
    open fun onViewCreated(view: View?, savedInstanceState: Bundle?) {}
    open fun onStart() {}
    open fun onResume() {}
    open fun onStop() {}
    open fun onDestroyView() {}
    open fun onDetach() {}
}