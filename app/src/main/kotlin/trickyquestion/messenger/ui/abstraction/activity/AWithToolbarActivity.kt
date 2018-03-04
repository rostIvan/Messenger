package trickyquestion.messenger.ui.abstraction.activity

import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.Toolbar
import trickyquestion.messenger.R
import trickyquestion.messenger.ui.abstraction.mvp.activity.MvpView

abstract class AWithToolbarActivity : MvpView() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customizeToolbar()
    }

    @NonNull protected abstract fun getToolbar() : Toolbar
    @NonNull open fun getToolbarTitle() : CharSequence = resources.getString(R.string.app_name)

    private fun customizeToolbar() {
        refreshThemeColor()
        setSupportActionBar(getToolbar())
        supportActionBar?.title = getToolbarTitle()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getToolbar().setNavigationOnClickListener { ApplicationRouter.from(this).back() }
    }

    protected fun refreshThemeColor() {
        getToolbar().setBackgroundColor(themePreference.primaryColor)
    }
}