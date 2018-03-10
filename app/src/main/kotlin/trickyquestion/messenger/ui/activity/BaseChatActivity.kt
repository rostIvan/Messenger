package trickyquestion.messenger.ui.activity

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import trickyquestion.messenger.R

abstract class BaseChatActivity : AWithToolbarActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_chat, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val settingsMenuItem = menu.findItem(R.id.clear_messages)
        val s = SpannableString(settingsMenuItem.title)
        s.setSpan(ForegroundColorSpan(themePreference.primaryColor), 0, s.length, 0)
        settingsMenuItem.title = s
        return super.onPrepareOptionsMenu(menu)
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.alpha_to_one, R.anim.translate_right_slide)
    }
}