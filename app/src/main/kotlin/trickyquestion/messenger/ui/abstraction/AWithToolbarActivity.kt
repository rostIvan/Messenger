package trickyquestion.messenger.ui.abstraction

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.Toolbar

@SuppressLint("Registered")
abstract class AWithToolbarActivity : ABaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customizeToolbar()
    }

    @NonNull abstract fun getToolbar() : Toolbar
    @NonNull open fun getToolbarTitle() : CharSequence = "Titanic"

    private fun customizeToolbar() {
        refreshThemeColor()
        setSupportActionBar(getToolbar())
        supportActionBar?.title = getToolbarTitle()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    protected fun refreshThemeColor() {
        getToolbar().setBackgroundColor(themePreference.primaryColor)
    }
}