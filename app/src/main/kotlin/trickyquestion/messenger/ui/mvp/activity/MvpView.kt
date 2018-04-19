package trickyquestion.messenger.ui.mvp.activity

import android.os.Bundle
import trickyquestion.messenger.ui.activity.ABaseActivity
import trickyquestion.messenger.ui.interfaces.BasePresenter

abstract class MvpView : ABaseActivity() {

    private lateinit var presenter: BasePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = getPresenter()
        presenter.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun finish() {
        super.finish()
        presenter.onFinish()
    }

    abstract fun getPresenter(): BasePresenter
}