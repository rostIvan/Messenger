package trickyquestion.messenger.data.repository

import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend

abstract class TempRepository : ACrudRepository<Friend>() {

    override fun notifyChanges() {
        TODO("not implemented")
    }
}