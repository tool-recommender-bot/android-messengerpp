package org.solovyev.android.messenger.realms;

import android.content.Context;
import org.solovyev.android.messenger.MessengerEntity;
import org.solovyev.android.messenger.icons.RealmIconService;
import org.solovyev.android.messenger.users.User;
import org.solovyev.android.properties.AProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * User: serso
 * Date: 7/22/12
 * Time: 12:56 AM
 */
public interface RealmDef extends MessengerEntity {

    @Nonnull
    String FAKE_REALM_ID = "fake";

    /**
     * Method returns realm definition's identifier. Must be unique for all existed realm difinitions.
     * Realm definition id must contain only alpha-numeric symbols in lower case: [a-z][0-9]
     * @return realm definition id in application
     */
    @Nonnull
    String getId();

    /**
     * @return android string resource id for realm's name
     */
    int getNameResId();

    /**
     * @return android drawable resource id for realm's icon
     */
    int getIconResId();

    /**
     * Method does initial setup for realm definition.
     * NOTE: this method must be called on application start, e.g. in {@link android.app.Application#onCreate()} method
     * @param context application's context
     */
    void init(@Nonnull Context context);

    @Nonnull
    Class<? extends BaseRealmConfigurationFragment> getConfigurationFragmentClass();

    @Nonnull
    Realm newRealm(@Nonnull String realmId, @Nonnull User user, @Nonnull RealmConfiguration configuration);

    @Nonnull
    Class<? extends RealmConfiguration> getConfigurationClass();

    @Nonnull
    RealmBuilder newRealmBuilder(@Nonnull RealmConfiguration configuration, @Nullable Realm editedRealm);

    boolean equals(@Nullable Object o);

    int hashCode();

    /**
     * Returns list of translated user properties where property name = title, property value = value
     * @param user user which properties will be returned
     * @return list of translated user properties
     */
    @Nonnull
    List<AProperty> getUserProperties(@Nonnull User user, @Nonnull Context context);

    /**
     *
     * @return true if sent message should be notified immediately, false to wait until response from remote server will come (and then it must trigger event)
     */
    boolean notifySentMessagesImmediately();

    @Nonnull
    RealmIconService getRealmIconService();
}
