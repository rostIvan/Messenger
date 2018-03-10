package trickyquestion.messenger.ui.interfaces

interface BaseView {
    fun showToast(text: CharSequence) {}
    fun onUiThread(runnable: Runnable) {}
}