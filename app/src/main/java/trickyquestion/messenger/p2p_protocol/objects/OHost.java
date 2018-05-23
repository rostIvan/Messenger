package trickyquestion.messenger.p2p_protocol.objects;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.interfaces.IHost;
import trickyquestion.messenger.util.Constants;

/**
 * Created by Subaru on 16.12.2017.
 */

public class OHost implements IHost {

    private SharedPreferences preferences;
    private UUID id;

    public OHost(Context context) {
        id = UUID.fromString("00000000-0000-0000-0000-000000000000");
        preferences = context.getSharedPreferences(Constants.PREFERENCE_AUTH_DATA, Context.MODE_PRIVATE);
    }

    @Override
    public UUID getID() {
        return UUID.fromString(preferences.getString(Constants.EXTRA_KEY_USER_ID, id.toString()));
    }

    @Override
    public String getName() {
        return preferences.getString(Constants.EXTRA_KEY_AUTH_LOGIN, "NULL");
    }

    @Override
    public void setName(String newName) {
        this.preferences.edit().putString(Constants.EXTRA_KEY_AUTH_LOGIN, newName).apply();
    }

    @Override
    public void reCreate(UUID id, String name) {
        this.preferences.edit().putString(Constants.EXTRA_KEY_USER_ID, id.toString()).apply();
        this.preferences.edit().putString(Constants.EXTRA_KEY_AUTH_LOGIN,name).apply();
    }
}
