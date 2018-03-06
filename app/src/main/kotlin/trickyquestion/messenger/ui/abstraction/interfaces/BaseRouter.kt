package trickyquestion.messenger.ui.abstraction.interfaces

import android.os.Bundle
import android.support.v4.app.Fragment
import trickyquestion.messenger.util.AnimatorResource

interface BaseRouter {
    fun openScreen(screen: Screen) {}
    fun openScreen(screen: Screen, bundle: Bundle?) {}
    fun openScreen(screen: Screen, bundle: Bundle?, animatorResource: AnimatorResource) {}
    fun openScreen(screen: Screen, animatorResource: AnimatorResource) {}
    fun back() {}
    fun exit() {}
    fun use(fragment: Fragment) : BaseRouter

    enum class Screen {
        MAIN,
        CHAT,
        ADD_FRIEND,
        SETTINGS
    }
}