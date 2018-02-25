package trickyquestion.messenger.ui.abstraction.interfaces

import android.support.annotation.LayoutRes

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Layout (@LayoutRes val res: Int)
