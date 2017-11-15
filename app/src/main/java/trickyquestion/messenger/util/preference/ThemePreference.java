package trickyquestion.messenger.util.preference;

import android.content.Context;
import android.content.SharedPreferences;

import trickyquestion.messenger.R;
import trickyquestion.messenger.util.Constants;

public class ThemePreference {
    private final Context context;
    private final SharedPreferences preferences;

    public ThemePreference(final Context context) {
        this.context = context;
        this.preferences = context
                .getSharedPreferences(Constants.PREFERENCE_THEME_COLOR, Context.MODE_PRIVATE);
    }

    public int getPrimaryColor() {
        return preferences.getInt(Constants.EXTRA_KEY_PRIMARY_COLOR, context.getResources().getColor(R.color.colorPrimaryGreen));
    }

    public int getSecondaryColor() {
        return preferences.getInt(Constants.EXTRA_KEY_SECONDARY_COLOR, context.getResources().getColor(R.color.colorWhite));
    }

    public void setPrimaryColor(int primaryColor) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.EXTRA_KEY_PRIMARY_COLOR, context.getResources().getColor(primaryColor));
        editor.apply();
        editor.commit();
    }

    public void setSecondaryColor(int secondaryColor) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.EXTRA_KEY_SECONDARY_COLOR, context.getResources().getColor(secondaryColor));
        editor.apply();
        editor.commit();
    }

}
