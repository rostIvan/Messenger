package trickyquestion.messenger.ui.abstraction.fragment

import android.os.Bundle
import android.view.View
import android.widget.EditText
import trickyquestion.messenger.util.*
import trickyquestion.messenger.util.android.preference.ThemePreference

abstract class AWithFieldFragment : ABaseFragment() {
    abstract fun getAllEditable(): List<EditText>
    lateinit var themePreference: ThemePreference

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init() {
        themePreference = ThemePreference(context)
        setEditTextLineColor(context.greenColor())
        setupListeners()
    }

    fun showError(text: CharSequence) {
        toast(text)
        setEditTextLineColor(context.redColor())
    }

    private fun setEditTextLineColor(color: Int) {
        getAllEditable().forEach { editText -> editText.setLineColor(color)}
    }

    private fun setupListeners() {
        getAllEditable().forEach { editText ->
            editText.onTextChanged { changedText -> checkEmptyInput(changedText) }
        }
    }

    private fun checkEmptyInput(changedText: String) {
        if (changedText.isEmpty())
            setEditTextLineColor(context.greenColor())
    }

}