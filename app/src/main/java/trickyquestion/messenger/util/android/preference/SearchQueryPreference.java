package trickyquestion.messenger.util.android.preference;

import android.content.Context;
import android.content.SharedPreferences;

import trickyquestion.messenger.util.Constants;

public class SearchQueryPreference {
    private SharedPreferences preferences;

    public SearchQueryPreference(final Context context) {
        preferences = context.getSharedPreferences(Constants.PREFERENCE_SEARCH_QUERY, Context.MODE_PRIVATE);
    }

    public void setLastQuery(final String last) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.EXTRA_LAST_SEARCH_QUERY, last);
        editor.apply();
    }

    public String getLastQuery() {
        return preferences.getString(Constants.EXTRA_LAST_SEARCH_QUERY, null);
    }

    public void setFocused(final boolean focused) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.EXTRA_FOCUS_SEARCH, focused);
        editor.apply();
    }

    public boolean isSearchFocused() {
        return preferences.getBoolean(Constants.EXTRA_FOCUS_SEARCH, false);
    }
}
