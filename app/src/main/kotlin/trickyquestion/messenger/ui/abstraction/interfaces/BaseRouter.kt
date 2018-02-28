package trickyquestion.messenger.ui.abstraction.interfaces

import android.os.Bundle
import trickyquestion.messenger.ui.abstraction.activity.ApplicationRouter

interface BaseRouter {
    fun openScreen(screen: Screen) {}
    fun openScreen(screen: Screen, bundle: Bundle?) {}
    fun openScreen(screen: Screen, bundle: Bundle?, animatorResource: ApplicationRouter.AnimatorResource) {}
    fun openScreen(screen: Screen, animatorResource: ApplicationRouter.AnimatorResource) {}
    fun back() {}
    fun exit() {}

    enum class Screen {
        MAIN,
        CHAT,
        ADD_FRIEND,
        SETTINGS
    }
}