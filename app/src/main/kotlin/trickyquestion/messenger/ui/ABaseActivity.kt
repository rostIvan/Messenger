package trickyquestion.messenger.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import trickyquestion.messenger.util.android.preference.ThemePreference

@SuppressLint("Registered")
abstract class ABaseActivity : AppCompatActivity() {

    lateinit var themePreference: ThemePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        ButterKnife.bind(this)
        init()
    }

    private fun init() {  themePreference = ThemePreference(this) }

    @NonNull
    abstract fun getContentView(): Int
}