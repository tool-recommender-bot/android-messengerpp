package org.solovyev.android.messenger.chats;

import android.widget.ImageView;
import org.solovyev.android.messenger.MergeDaoResult;
import org.solovyev.android.messenger.accounts.AccountException;
import org.solovyev.android.messenger.entities.Entity;
import org.solovyev.android.messenger.messages.ChatMessage;
import org.solovyev.android.messenger.users.User;
import org.solovyev.common.listeners.JEventListener;
import org.solovyev.common.listeners.JEventListeners;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.solovyev.android.messenger.App.newTag;

/**
 * User: serso
 * Date: 5/24/12
 * Time: 9:11 PM
 */

/**
 * Implementation of this class must provide thread safeness
 */
@ThreadSafe
public interface ChatService extends JEventListeners<JEventListener<ChatEvent>, ChatEvent> {

	@Nonnull
	static final String TAG = newTag("ChatService");


	// initial initialization: will be called once on application start
	void init();

    /*
	**********************************************************************
    *
    *                           CHAT OPERATIONS
    *
    **********************************************************************
    */

	/**
	 * Method loads all user chats
	 *
	 * @param user user
	 * @return list of chats of current user
	 */
	@Nonnull
	List<Chat> loadUserChats(@Nonnull Entity user);

	/**
	 * Method updates chat (it's properties) in storage
	 *
	 * @param chat chat to be update
	 * @return updated chat
	 */
	@Nonnull
	Chat updateChat(@Nonnull Chat chat);

	/**
	 * Method returns chat identified by <var>chat</var> entity.
	 * NOTE: this method doesn't lookup realm for specified chat, all chats should be preloaded for realm.
	 *
	 * @param chat chat
	 * @return chat, null if no chat found
	 */
	@Nullable
	Chat getChatById(@Nonnull Entity chat);

	/**
	 * Method returns all participants of specified chat
	 *
	 * @param chat chat
	 * @return chat participants
	 */
	@Nonnull
	List<User> getParticipants(@Nonnull Entity chat);

	/**
	 * Method returns all participants of specified chat except <var>user</var>
	 *
	 * @param chat chat
	 * @param user user
	 * @return chat participants except <var>user</var>
	 */
	@Nonnull
	List<User> getParticipantsExcept(@Nonnull Entity chat, @Nonnull Entity user);

	@Nullable
	ChatMessage getLastMessage(@Nonnull Entity chat);

	void setChatIcon(@Nonnull Chat chat, @Nonnull ImageView imageView);

	void saveChatMessages(@Nonnull Entity accountChat, @Nonnull Collection<? extends ChatMessage> messages, boolean updateChatSyncDate);

	void onChatMessageRead(@Nonnull Chat chat, @Nonnull ChatMessage message);

	@Nonnull
	List<UiChat> getLastChats(@Nonnull User user, int count);

	@Nonnull
	List<UiChat> getLastChats(int count);

	void removeEmptyChats(@Nonnull User user);

    /*
    **********************************************************************
    *
    *                           PRIVATE CHAT
    *
    **********************************************************************
    */

	/**
	 * Method returns private chat id.
	 * NOTE: this ID is generated by application and differs from one provided by realm.
	 * Chat id provided by realm still can be get via {@link org.solovyev.android.messenger.entities.Entity#getAccountEntityId()}
	 * NOTE: generated chat id is time independent => this method call returns always same id for same users
	 *
	 * @param user1 first participant
	 * @param user2 second participant
	 * @return private chat id
	 */
	@Nonnull
	Entity getPrivateChatId(@Nonnull Entity user1, @Nonnull Entity user2);

	@Nullable
	Chat getPrivateChat(@Nonnull Entity user1, @Nonnull Entity user2) throws AccountException;

	/**
	 * Method returns private chat for specified users. In case if such chat doesn't exist new empty chat is created.
	 *
	 * @param user1 first participant
	 * @param user2 second participant
	 * @return private chat
	 */
	@Nonnull
	Chat getOrCreatePrivateChat(@Nonnull Entity user1, @Nonnull Entity user2) throws AccountException;

	/**
	 * Method returns second user in private chat (first user is always realm user)
	 *
	 * @param chat private chat
	 * @return second participant of the chat, null in case if chat is not private or second user cannot be retrieved
	 */
	@Nullable
	Entity getSecondUser(@Nonnull Chat chat);

    /*
    **********************************************************************
    *
    *                           SYNC
    *
    **********************************************************************
    */

	/**
	 * Method synchronizes all chat messages for user.
	 * NOTE: some realms might not return ALL user messages but only new
	 *
	 * @param user user for which sync should be done
	 * @return new chat messages for user
	 */
	@Nonnull
	List<ChatMessage> syncChatMessages(@Nonnull Entity user) throws AccountException;

	/**
	 * Method synchronizes newer chat messages for <var>chat</var>
	 * NOTE: this method try to synchronize only newer messages, i.e. messages after last message in local storage
	 *
	 * @param chat which messages should be synchronized
	 * @return list of newer messages if there exist ones
	 */
	@Nonnull
	List<ChatMessage> syncNewerChatMessagesForChat(@Nonnull Entity chat) throws AccountException;

	/**
	 * Method synchronizes older chat messages for <var>chat</var>
	 * NOTE: this method try to synchronize only older messages, i.e. messages before first message in local storage
	 *
	 * @param chat which messages should be synchronized
	 * @return list of older messages if there exist ones
	 */
	@Nonnull
	List<ChatMessage> syncOlderChatMessagesForChat(@Nonnull Entity chat, @Nonnull Entity user) throws AccountException;

	void syncChat(@Nonnull Entity chat, @Nonnull Entity user) throws AccountException;

	/**
	 * Method merges specified <var>chats</var> with already saved in the storage.
	 *
	 * @param user  user
	 * @param chats to be merged chats
	 * @return merge result
	 */
	@Nonnull
	MergeDaoResult<ApiChat, String> mergeUserChats(@Nonnull Entity user, @Nonnull List<? extends ApiChat> chats) throws AccountException;

	/**
	 * Method tries to save chat.
	 * If chats already exists in storage - it will be updated,
	 * if no chat exists -  it will be added,
	 *
	 * @param user user
	 * @param chat chat
	 * @return updated chat
	 */
	@Nonnull
	ApiChat saveChat(@Nonnull Entity user, @Nonnull ApiChat chat) throws AccountException;

	/**
	 * Key: chat for which unread messages exist, value: number of unread messages
	 *
	 * @return map of chats with unread messages counts for them
	 */
	@Nonnull
	Map<Entity, Integer> getUnreadChats();

	/**
	 * Must be called when number of unread messages has changed for a chat (user hsa read message)
	 *
	 * @param chat                chat
	 * @param unreadMessagesCount number of unread messages
	 */
	void onUnreadMessagesCountChanged(@Nonnull Entity chat, @Nonnull Integer unreadMessagesCount);

	int getUnreadMessagesCount(@Nonnull Entity chat);

	void removeChatsInAccount(@Nonnull String realmId);
}
