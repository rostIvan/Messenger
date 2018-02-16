package trickyquestion.messenger.ui.abstraction

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import butterknife.ButterKnife
import butterknife.Unbinder
import trickyquestion.messenger.util.*

abstract class AWithFieldFragment : ABaseFragment() {
    abstract fun getAllEditable(): List<EditText>

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init() {
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