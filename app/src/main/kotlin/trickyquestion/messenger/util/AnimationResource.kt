package trickyquestion.messenger.util

class AnimatorResource(var enterAnim: Int, var exitAnim: Int) {
    companion object {
        @JvmStatic
        fun with(enterAnim: Int, exitAnim: Int) : AnimatorResource = AnimatorResource(enterAnim, exitAnim)
    }
}