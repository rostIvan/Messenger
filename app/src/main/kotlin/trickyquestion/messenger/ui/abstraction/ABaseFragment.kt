package trickyquestion.messenger.ui.abstraction

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import trickyquestion.messenger.util.android.preference.ThemePreference

abstract class ABaseFragment : Fragment() {
    lateinit var bind: Unbinder
    lateinit var themePreference: ThemePreference

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!javaClass.isAnnotationPresent(Layout::class.java)) return super.onCreateView(inflater, container, savedInstanceState)
        val layout = getLayoutFromAnnotation()
        val view = inflater?.inflate(layout.res, container, false)
        bind = ButterKnife.bind(this, view!!)
        init()
        return view
    }

    override fun onDestroyView() {
        bind.unbind()
        super.onDestroyView()
    }

    private fun init() {  themePreference = ThemePreference(context) }

    private fun getLayoutFromAnnotation() : Layout {
        val annotation = javaClass.getAnnotation(Layout::class.java)
        return annotation as Layout
    }
}