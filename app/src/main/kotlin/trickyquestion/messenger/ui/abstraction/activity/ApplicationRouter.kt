package trickyquestion.messenger.ui.abstraction.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import trickyquestion.messenger.screen.add_friend.ui.AddFriendActivity
import trickyquestion.messenger.screen.chat.view.ChatActivity
import trickyquestion.messenger.screen.main.container.implementation.MainActivity
import trickyquestion.messenger.screen.settings.view.SettingActivity
import trickyquestion.messenger.ui.abstraction.interfaces.BaseRouter

class ApplicationRouter private constructor(private val context: Context) : BaseRouter {

    companion object {
        @JvmStatic fun from(context: Context) : ApplicationRouter {
            return ApplicationRouter(context)
        }
    }

    override fun back() {  (context as Activity).onBackPressed()  }

    override fun exit() {
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(homeIntent)
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
        BaseRouter.Screen.CHAT -> showActivity(ChatActivity::class.java, bundle)
        BaseRouter.Screen.ADD_FRIEND -> showActivity(AddFriendActivity::class.java, bundle)
        BaseRouter.Screen.SETTINGS -> showActivity(SettingActivity::class.java, bundle)
        BaseRouter.Screen.MAIN -> showActivity(MainActivity::class.java, bundle)
    }

    private fun showActivity(clazz: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(context, clazz)
        if(bundle != null) intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    class AnimatorResource (var enterAnim: Int, var exitAnim: Int)
}