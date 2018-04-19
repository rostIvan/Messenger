package trickyquestion.messenger.ui.mvp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trickyquestion.messenger.ui.fragment.ABaseFragment
import trickyquestion.messenger.ui.interfaces.BasePresenter

abstract class MvpView : ABaseFragment() {

    private lateinit var iPresenter: BasePresenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        iPresenter = getPresenter()
        iPresenter.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val onCreateView = super.onCreateView(inflater, container, savedInstanceState)
        iPresenter.onCreateView(inflater, container, savedInstanceState)
        return onCreateView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iPresenter.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        iPresenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        iPresenter.onResume()
    }

    override fun onStop() {
        super.onStop()
        iPresenter.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        iPresenter.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        iPresenter.onDetach()
    }

    abstract fun getPresenter(): BasePresenter
}