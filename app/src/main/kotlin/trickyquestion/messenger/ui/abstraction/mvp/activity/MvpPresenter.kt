package trickyquestion.messenger.ui.abstraction.mvp.activity

import android.os.Bundle
import trickyquestion.messenger.ui.abstraction.interfaces.BaseRouter
import trickyquestion.messenger.ui.abstraction.interfaces.ActivityLifecycleCallbacks

abstract class MvpPresenter<V : MvpView, R : BaseRouter>
constructor(var view: V, var router: R) : ActivityLifecycleCallbacks {

    // empty by default
    override fun onCreate(bundle: Bundle?) {}
    override fun onDestroy() {}
    override fun onStart() {}
    override fun onResume() {}
    override fun onStop() {}
    override fun onFinish() {}
}