package trickyquestion.messenger.di

import android.app.Activity
import android.support.v4.app.Fragment
import trickyquestion.messenger.buisness.DataProvider
import trickyquestion.messenger.data.repository.ChatMessageRepository
import trickyquestion.messenger.data.repository.FriendRepository
import trickyquestion.messenger.screen.add_friend.buisness.AddFriendInteractor
import trickyquestion.messenger.screen.add_friend.buisness.AddFriendEventManager
import trickyquestion.messenger.screen.add_friend.ui.AddFriendPresenter
import trickyquestion.messenger.screen.add_friend.ui.IAddFriendPresenter
import trickyquestion.messenger.screen.add_friend.ui.IAddFriendView
import trickyquestion.messenger.screen.chat.buisness.ChatInteractor
import trickyquestion.messenger.screen.chat.buisness.ChatEventManager
import trickyquestion.messenger.screen.chat.ui.ChatPresenter
import trickyquestion.messenger.screen.chat.ui.IChatPresenter
import trickyquestion.messenger.screen.chat.ui.IChatView
import trickyquestion.messenger.screen.main.container.implementation.MainEventManager
import trickyquestion.messenger.screen.main.container.implementation.MainPresenter
import trickyquestion.messenger.screen.main.container.interfaces.IMainPresenter
import trickyquestion.messenger.screen.main.container.interfaces.IMainView
import trickyquestion.messenger.screen.main.tabs.friends.buisness.FriendsEventManager
import trickyquestion.messenger.screen.main.tabs.friends.buisness.FriendsInteractor
import trickyquestion.messenger.screen.main.tabs.friends.ui.FriendsPresenter
import trickyquestion.messenger.screen.main.tabs.friends.ui.IFriendsPresenter
import trickyquestion.messenger.screen.main.tabs.friends.ui.IFriendsView
import trickyquestion.messenger.screen.main.tabs.messages.buisness.MessagesEventManager
import trickyquestion.messenger.screen.main.tabs.messages.buisness.MessagesInteractor
import trickyquestion.messenger.screen.main.tabs.messages.ui.IMessagesPresenter
import trickyquestion.messenger.screen.main.tabs.messages.ui.IMessagesView
import trickyquestion.messenger.screen.main.tabs.messages.ui.MessagesPresenter
import trickyquestion.messenger.ui.activity.ApplicationRouter

object PresentationFactory {

    fun create(view: IMainView): IMainPresenter {
        val presenter = MainPresenter(view, getRouter(view))
        presenter.attach(MainEventManager(presenter))
        return presenter
    }

    fun create(view: IChatView): IChatPresenter {
        val interactor = ChatInteractor()
        val presenter = ChatPresenter(view, getRouter(view), interactor)
        presenter.attach(ChatEventManager(presenter))
        return presenter
    }

    fun create(view: IAddFriendView): IAddFriendPresenter {
        val interactor = AddFriendInteractor(DataProvider(), FriendRepository.INSTANCE)
        val presenter = AddFriendPresenter(view, getRouter(view), interactor)
        presenter.attach(AddFriendEventManager(presenter))
        return presenter
    }

    fun create(view: IFriendsView): IFriendsPresenter {
        val interactor = FriendsInteractor(FriendRepository.INSTANCE)
        val presenter = FriendsPresenter(view, getRouter(view), interactor)
        presenter.attach(FriendsEventManager(presenter))
        return presenter
    }

    fun create(view: IMessagesView): IMessagesPresenter {
        val friendRepository = FriendRepository.INSTANCE
        val messageRepository = ChatMessageRepository.INSTANCE
        val messagesInteractor = MessagesInteractor(friendRepository, messageRepository)
        val presenter = MessagesPresenter(view, getRouter(view), messagesInteractor)
        presenter.attach(MessagesEventManager(presenter))
        return presenter
    }

    private fun getRouter(view: Any) = when (view) {
        is Fragment -> ApplicationRouter.from(view.context)
        is Activity -> ApplicationRouter.from(view)
        else -> throw IllegalArgumentException("Router must accept fragment or activity")
    }
}
