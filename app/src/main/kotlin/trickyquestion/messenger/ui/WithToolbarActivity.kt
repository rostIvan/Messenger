package trickyquestion.messenger.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.Toolbar

@SuppressLint("Registered")
abstract class WithToolbarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customizeToolbar()
    }

    @NonNull abstract fun getToolbar() : Toolbar
    @NonNull open fun getToolbarTitle() : CharSequence = "Titanic"

    private fun customizeToolbar() {
        getToolbar().setBackgroundColor(themePreference.primaryColor)
        setSupportActionBar(getToolbar())
        supportActionBar?.title = getToolbarTitle()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}