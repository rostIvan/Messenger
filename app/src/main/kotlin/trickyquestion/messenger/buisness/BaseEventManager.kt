package trickyquestion.messenger.buisness

import de.greenrobot.event.EventBus

abstract class BaseEventManager {
    fun subscribe() { EventBus.getDefault().register(this) }
    fun unsubscribe() { EventBus.getDefault().unregister(this) }
}