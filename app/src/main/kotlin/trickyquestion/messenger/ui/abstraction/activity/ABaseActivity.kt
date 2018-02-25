package trickyquestion.messenger.ui.abstraction.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import trickyquestion.messenger.ui.abstraction.interfaces.Layout
import trickyquestion.messenger.ui.abstraction.mvp.MvpView
import trickyquestion.messenger.util.android.preference.ThemePreference


@SuppressLint("Registered")
abstract class ABaseActivity : AppCompatActivity() {
    lateinit var themePreference: ThemePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!javaClass.isAnnotationPresent(Layout::class.java)) return
        val layout = getLayoutFromAnnotation()
        setContentView(layout.res)
        ButterKnife.bind(this)
        init()
    }

    private fun init() {  themePreference = ThemePreference(this) }

    private fun getLayoutFromAnnotation() : Layout {
        val annotation = javaClass.getAnnotation(Layout::class.java)
        return annotation as Layout
    }
}