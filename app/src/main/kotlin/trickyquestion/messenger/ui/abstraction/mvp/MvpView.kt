package trickyquestion.messenger.ui.abstraction.mvp

import android.os.Bundle
import trickyquestion.messenger.ui.abstraction.activity.ABaseActivity

abstract class MvpView : ABaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresenter()?.onCreate(bundle = savedInstanceState)
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

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    open fun getPresenter(): MvpPresenter<*, *>? = null
}