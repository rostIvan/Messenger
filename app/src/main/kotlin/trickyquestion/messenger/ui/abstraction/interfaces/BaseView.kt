package trickyquestion.messenger.ui.abstraction.interfaces

interface BaseView {
    fun showToast(text: CharSequence) {}
    fun onUiThread(runnable: Runnable) {}
}