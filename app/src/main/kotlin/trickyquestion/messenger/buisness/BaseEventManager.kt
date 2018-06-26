package trickyquestion.messenger.buisness

import de.greenrobot.event.EventBus

abstract class BaseEventManager {
    open fun subscribe() { EventBus.getDefault().register(this) }
    open fun unsubscribe() { EventBus.getDefault().unregister(this) }
}