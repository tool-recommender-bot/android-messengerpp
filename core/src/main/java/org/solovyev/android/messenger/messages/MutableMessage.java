package org.solovyev.android.messenger.messages;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.DateTime;
import org.solovyev.android.messenger.entities.Entity;
import org.solovyev.android.properties.AProperty;
import org.solovyev.android.properties.MutableAProperties;

public interface MutableMessage extends Message {

	void setAuthor(@Nonnull Entity author);

	void setSendDate(@Nonnull DateTime sendDate);

	void setTitle(@Nonnull String title);

	void setBody(@Nonnull String body);

	void setRecipient(@Nullable Entity recipient);

	void setState(@Nonnull MessageState state);

	void setChat(@Nonnull Entity chat);

	void setRead(boolean read);

	@Nonnull
	@Override
	MutableMessage clone();

	@Nonnull
	MutableMessage cloneRead();

	@Nonnull
	@Override
	MutableMessage cloneWithNewState(@Nonnull MessageState state);

	@Nonnull
	@Override
	MutableAProperties getProperties();

	void setProperties(@Nonnull List<AProperty> properties);

	@Nonnull
	MutableMessage cloneWithNewChat(@Nonnull Entity chat);

	@Override
	MutableMessage cloneAndMerge(@Nonnull Message that);
}
