package trickyquestion.messenger.ui.abstraction.interfaces

import android.os.Bundle

interface BaseRouter {
    fun openScreen(screen: Screen) {}
    fun openScreen(screen: Screen, bundle: Bundle?) {}
    fun back() {}
    fun exit() {}

    enum class Screen {
        MAIN,
        CHAT,
        ADD_FRIEND,
        SETTINGS
    }
}