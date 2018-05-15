package trickyquestion.messenger.ui.interfaces

import android.os.Bundle
import android.support.v4.app.Fragment
import trickyquestion.messenger.ui.util.AnimatorResource

interface BaseRouter {
    fun openScreen(screen: Screen) {}
    fun openScreen(screen: Screen, bundle: Bundle?) {}
    fun openScreen(screen: Screen, bundle: Bundle?, animatorResource: AnimatorResource) {}
    fun openScreen(screen: Screen, animatorResource: AnimatorResource) {}
    fun back() {}
    fun exit() {}
    fun use(view: Any) : BaseRouter

    enum class Screen {
        SIGN_UP,
        ASK_PASSWORD,
        MAIN,
        CHAT,
        ADD_FRIEND,
        SETTINGS
    }
}