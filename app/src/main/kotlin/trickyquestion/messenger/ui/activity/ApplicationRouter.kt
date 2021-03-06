package trickyquestion.messenger.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import trickyquestion.messenger.screen.add_friend.ui.AddFriendActivity
import trickyquestion.messenger.screen.main.container.implementation.MainActivity
import trickyquestion.messenger.screen.settings.view.SettingActivity
import trickyquestion.messenger.screen.chat.ui.ChatActivity
import trickyquestion.messenger.screen.login.ask_password.AskPasswordActivity
import trickyquestion.messenger.screen.login.sign_up.SignUpActivity
import trickyquestion.messenger.ui.interfaces.BaseRouter
import trickyquestion.messenger.ui.util.AnimatorResource

open class ApplicationRouter private constructor(val context: Context?) : BaseRouter {

    companion object {
        @JvmStatic fun from(context: Context?) = ApplicationRouter(context)
    }

    // for fragments use only this approach
    override fun use(view: Any) = ApplicationRouter((view as Fragment).context)

    override fun back() {  (context as Activity).onBackPressed()  }

    override fun exit() {
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        context?.startActivity(homeIntent)
    }

    open fun finishActivity() {
        (context as Activity).finish()
    }

    override fun openScreen(screen: BaseRouter.Screen, animatorResource: AnimatorResource) {
        open(screen)
        (context as Activity).overridePendingTransition(animatorResource.enterAnim, animatorResource.exitAnim)
    }

    override fun openScreen(screen: BaseRouter.Screen, bundle: Bundle?, animatorResource: AnimatorResource) {
        open(screen, bundle)
        (context as Activity).overridePendingTransition(animatorResource.enterAnim, animatorResource.exitAnim)
    }

    override fun openScreen(screen: BaseRouter.Screen) { open(screen) }

    override fun openScreen(screen: BaseRouter.Screen, bundle: Bundle?) { open(screen, bundle) }

    private fun open(screen: BaseRouter.Screen, bundle: Bundle? = null) = when(screen) {
        BaseRouter.Screen.SIGN_UP -> showActivity(SignUpActivity::class.java, bundle)
        BaseRouter.Screen.ASK_PASSWORD -> showActivity(AskPasswordActivity::class.java, bundle)
        BaseRouter.Screen.CHAT -> showActivity(ChatActivity::class.java, bundle)
        BaseRouter.Screen.ADD_FRIEND -> showActivity(AddFriendActivity::class.java, bundle)
        BaseRouter.Screen.SETTINGS -> showActivity(SettingActivity::class.java, bundle)
        BaseRouter.Screen.MAIN -> showActivity(MainActivity::class.java, bundle)
    }

    private fun showActivity(clazz: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(context, clazz)
        if(bundle != null) intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(intent)
    }
}