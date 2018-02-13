package trickyquestion.messenger.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import butterknife.ButterKnife
import butterknife.Unbinder
import trickyquestion.messenger.util.*

abstract class ALoginFragment : Fragment() {
    abstract fun getLayout() : Int
    abstract fun getAllEditable(): List<EditText>

    lateinit var bind: Unbinder

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayout(), container, false)
        bind = ButterKnife.bind(this, view!!)
        init()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind.unbind()
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