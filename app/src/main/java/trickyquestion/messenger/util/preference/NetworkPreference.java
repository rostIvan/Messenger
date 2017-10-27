package trickyquestion.messenger.util.preference;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.regex.Pattern;
import trickyquestion.messenger.util.Constants;

public class NetworkPreference {
    private final SharedPreferences preferences;

    public NetworkPreference(final Context context) {
        this.preferences = context.getSharedPreferences(Constants.PREFERENCE_NETWORK_DATA, Context.MODE_PRIVATE);
    }

    public void setHeartbeatFrequency(final int heartbeatFrequency) {
        checkHeartbeatFrequency(heartbeatFrequency);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.EXTRA_KEY_HEARTBEAT_FREQUENCY, heartbeatFrequency);
        editor.apply();
        editor.commit();
    }

    public void setMulticastGroupIp(final String multicastGroupIp) {
        checkMulticastGroupIp(multicastGroupIp);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.EXTRA_KEY_MULTICAST_GROUP_IP, multicastGroupIp);
        editor.apply();
        editor.commit();
    }

    public void setMulticastPort(final int multicastPort) {
        checkMulticastPort(multicastPort);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.EXTRA_KEY_MULTICAST_PORT, multicastPort);
        editor.apply();
        editor.commit();
    }

    public int getHeartbeatFrequency() {
        return preferences.getInt(Constants.EXTRA_KEY_HEARTBEAT_FREQUENCY, Constants.DEFAULT_HEARTBEAT_FREQUENCY);
    }

    public String getMulticastGroupIp() {
        return preferences.getString(Constants.EXTRA_KEY_MULTICAST_GROUP_IP, Constants.DEFAULT_MULTICAST_GROUP_IP);
    }

    public int getMulticastPort() {
        return preferences.getInt(Constants.EXTRA_KEY_MULTICAST_PORT, Constants.DEFAULT_MULTICAST_PORT);
    }


    private void checkMulticastGroupIp(String multicastGroupIp) {
        final String pattern = "^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}$";
        if ( !Pattern.matches(pattern, multicastGroupIp) )
            throw new RuntimeException("multicast GroupIp is not valid");
    }

    private void checkHeartbeatFrequency(int heartbeatFrequency) {
        if ( heartbeatFrequency < 1 || heartbeatFrequency > 100_000)
            throw new RuntimeException("heartbeat frequency is not valid");
    }
    private void checkMulticastPort(int multicastPort) {
        if (multicastPort < 1 || multicastPort > 65655)
            throw new RuntimeException("multicast Port is not valid");
    }
}
