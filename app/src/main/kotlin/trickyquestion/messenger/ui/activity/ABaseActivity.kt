package trickyquestion.messenger.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import trickyquestion.messenger.ui.interfaces.BaseView
import trickyquestion.messenger.ui.interfaces.Layout
import trickyquestion.messenger.ui.util.toast
import trickyquestion.messenger.util.android.preference.ThemePreference


abstract class ABaseActivity : AppCompatActivity(), BaseView {
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


    override fun showToast(text: CharSequence) { toast(text) }

    override fun onUiThread(runnable: Runnable) { runOnUiThread(runnable) }
}