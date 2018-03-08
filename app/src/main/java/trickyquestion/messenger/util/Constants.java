package trickyquestion.messenger.util;

import android.graphics.Color;

import trickyquestion.messenger.R;

public class Constants {
    public static final int MAIN_LAYOUT = R.layout.activity_main;
    public static final int WAS_READ_MESSAGE_BACKGROUND = Color.argb(40, 0, 0, 0);
    public static final int ONLINE_STATUS_TEXT_COLOR = Color.argb(255, 7, 169, 132);
    public static final int OFFLINE_STATUS_TEXT_COLOR = Color.argb(200, 255, 0, 0);

    public static final int DURATION_ITEM_ANIMATION = 50;

    public static final String PREFERENCE_AUTH_DATA = "Auth data";
    public static final String PREFERENCE_SEARCH_QUERY = "Search query";
    public static final String EXTRA_KEY_AUTH_LOGIN = "Auth log";
    public static final String EXTRA_KEY_AUTH_PASSWORD = "Auth pass";
    public static final String EXTRA_KEY_USER_ID = "User ID";
    public static final String EXTRA_KEY_IS_AUTHENTICATED = "Ask auth";
    public static final String EXTRA_ASK_PASSWORD = "Ask pass";
    public static final String EXTRA_LAST_SEARCH_QUERY = "Last search query";
    public static final String EXTRA_FOCUS_SEARCH = "Show search";
    public static final String EXTRA_SHOW_NOTIFICATIONS = "Show notifications";
    public static final String PREFERENCE_THEME_COLOR = "Theme color";
    public static final String EXTRA_KEY_PRIMARY_COLOR = "Primary color";
    public static final String EXTRA_KEY_SECONDARY_COLOR = "Secondary color";
    public static final String PREFERENCE_NETWORK_DATA = "Network data";
    public static final String EXTRA_KEY_HEARTBEAT_FREQUENCY = "Heartbeat frequency";
    public static final String EXTRA_KEY_MULTICAST_PORT = "Multicast port";
    public static final String EXTRA_KEY_MULTICAST_GROUP_IP = "Multicast group Ip";

    public static final int DEFAULT_HEARTBEAT_FREQUENCY = 2500;
    public static final String DEFAULT_MULTICAST_GROUP_IP = "239.0.0.1";
    public static final int DEFAULT_MULTICAST_PORT = 5000;

    public static final String EXTRA_KEY_AUTH_PORT = "Auth port";
    public static final int DEFAULT_AUTH_PORT = 5001;

    public static final String EXTRA_KEY_AUTH_KEY_BIT_SIZE = "DH Pub key size";
    public static final int DEFAULT_AUTH_KEY_BIT_SIZE = 192;

    public static final String EXTRA_KEY_AUTH_TIMEOUT = "auth timeout";
    public static final int DEFAULT_AUTH_TIMEOUT = 10000;

    public static final String EXTRA_KEY_MSG_PORT = "Msg port";
    public static final int DEFAULT_MSG_PORT = 5002;

    public static final int MAX_MSG_SIZE = 1024;
}
