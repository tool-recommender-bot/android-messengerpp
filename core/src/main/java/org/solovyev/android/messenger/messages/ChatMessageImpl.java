package org.solovyev.android.messenger.messages;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.solovyev.android.messenger.chats.MessageDirection;
import org.solovyev.android.messenger.entities.Entity;
import org.solovyev.android.properties.AProperty;
import org.solovyev.android.properties.MutableAProperties;
import org.solovyev.common.JObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;

import static org.solovyev.android.properties.Properties.newProperties;

/**
 * User: serso
 * Date: 6/6/12
 * Time: 1:01 PM
 */
class ChatMessageImpl extends JObject implements MutableChatMessage {

	@Nonnull
	private Message message;

	private boolean read = false;

	@Nonnull
	private MessageDirection direction = MessageDirection.in;

	@Nonnull
	private MutableAProperties properties = newProperties(Collections.<AProperty>emptyList());

	private ChatMessageImpl(@Nonnull Message message) {
		this.message = message;
	}

	@Nonnull
	static ChatMessageImpl newInstance(@Nonnull Message message, boolean read) {
		final ChatMessageImpl result = new ChatMessageImpl(message);
		result.read = read;
		return result;
	}


	public boolean isRead() {
		return read;
	}

	@Nonnull
	@Override
	public MessageDirection getDirection() {
		return this.direction;
	}

	@Nonnull
	@Override
	public MutableChatMessage cloneRead() {
		final ChatMessageImpl clone = clone();
		clone.read = true;
		return clone;
	}

	@Nonnull
	@Override
	public ChatMessage cloneWithNewState(@Nonnull MessageState state) {
		final ChatMessageImpl clone = clone();
		clone.message = clone.message.cloneWithNewState(state);
		return clone;
	}

	@Nonnull
	@Override
	public Entity getChat() {
		return message.getChat();
	}

	@Nonnull
	@Override
	public ChatMessageImpl clone() {
		final ChatMessageImpl clone = (ChatMessageImpl) super.clone();

		clone.message = this.message.clone();

		clone.properties = this.properties.clone();

		return clone;
	}

	@Override
	@Nonnull
	public MutableAProperties getProperties() {
		return properties;
	}

	@Override
	public void setDirection(@Nonnull MessageDirection direction) {
		this.direction = direction;
	}

	@Nonnull
	@Override
	public Entity getEntity() {
		return this.message.getEntity();
	}

	@Override
	@Nonnull
	public Entity getAuthor() {
		return message.getAuthor();
	}

	@Override
	@Nullable
	public Entity getRecipient() {
		return message.getRecipient();
	}

	@Override
	@Nonnull
	public DateTime getSendDate() {
		return message.getSendDate();
	}

	@Override
	@Nonnull
	public String getTitle() {
		return message.getTitle();
	}

	@Override
	@Nonnull
	public String getBody() {
		return message.getBody();
	}

	@Nonnull
	@Override
	public MessageState getState() {
		return message.getState();
	}

	@Override
	@Nullable
	public Entity getSecondUser(@Nonnull Entity user) {
		return message.getSecondUser(user);
	}

	@Nonnull
	@Override
	public String getId() {
		return message.getId();
	}

	@Override
	@Nonnull
	public DateTime getLocalSendDateTime() {
		return message.getLocalSendDateTime();
	}

	@Override
	@Nonnull
	public LocalDate getLocalSendDate() {
		return message.getLocalSendDate();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ChatMessageImpl)) return false;

		ChatMessageImpl that = (ChatMessageImpl) o;

		if (!message.equals(that.message)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return message.hashCode();
	}

	@Override
	public String toString() {
		return "ChatMessageImpl{" +
				"liteChatMessage=" + message +
				'}';
	}

	@Override
	public boolean isPrivate() {
		return message.isPrivate();
	}
}
