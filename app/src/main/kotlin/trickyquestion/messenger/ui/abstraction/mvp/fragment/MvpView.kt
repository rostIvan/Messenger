package trickyquestion.messenger.ui.abstraction.mvp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trickyquestion.messenger.ui.abstraction.fragment.ABaseFragment

abstract class MvpView : ABaseFragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getPresenter()?.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val onCreateView = super.onCreateView(inflater, container, savedInstanceState)
        getPresenter()?.onCreateView(inflater, container, savedInstanceState)
        return onCreateView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter()?.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        getPresenter()?.onStart()
    }

    override fun onResume() {
        super.onResume()
        getPresenter()?.onResume()
    }

    override fun onStop() {
        super.onStop()
        getPresenter()?.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getPresenter()?.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        getPresenter()?.onDetach()
    }

    open fun getPresenter(): MvpPresenter<*, *>? = null
}