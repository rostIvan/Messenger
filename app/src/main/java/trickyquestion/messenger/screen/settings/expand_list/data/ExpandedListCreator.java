package trickyquestion.messenger.screen.settings.expand_list.data;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.content.res.AppCompatResources;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.R;
import trickyquestion.messenger.screen.settings.expand_list.model.SettingChild;
import trickyquestion.messenger.screen.settings.expand_list.model.SettingParent;
import trickyquestion.messenger.util.android.preference.AuthPreference;
import trickyquestion.messenger.util.android.preference.ThemePreference;

public class ExpandedListCreator {

    private final Context context;
    private final AuthPreference authPreference;
    private final ThemePreference themePreference;

    public ExpandedListCreator(final Context context) {
        this.context = context;
        this.authPreference = new AuthPreference(context);
        this.themePreference = new ThemePreference(context);
    }

    public List<ParentObject> getParents() {
        final List<ParentObject> parents = new ArrayList<>();
        final SettingParent parent1 = getFirstParent();
        final SettingParent parent2 = getSecondParent();
        final SettingParent parent3 = getThirdParent();
        parent1.setChildObjectList(getChildrenFirstParent());
        parent2.setChildObjectList(getChildrenSecondParent());
        parent3.setChildObjectList(getChildrenThirdParent());

        parents.add(parent1);
        parents.add(parent2);
        parents.add(parent3);
        return parents;
    }

    private SettingParent getFirstParent() {
        final SettingParent parent1 = new SettingParent();
        final Drawable icon = getDrawable(R.drawable.ic_settings_primary_green, themePreference.getPrimaryColor());
        parent1.setTitle("Account Setting");
        parent1.setImageDrawable(icon);
        return parent1;
    }

    private SettingParent getSecondParent() {
        final SettingParent parent2 = new SettingParent();
        final Drawable icon = getDrawable(R.drawable.ic_notification_primary_green, themePreference.getPrimaryColor());
        parent2.setTitle("Notification");
        parent2.setImageDrawable(icon);
        return parent2;
    }


    private SettingParent getThirdParent() {
        final SettingParent parent3 = new SettingParent();
        final Drawable icon = getDrawable(R.drawable.ic_color_managment_primary_green, themePreference.getPrimaryColor());
        parent3.setTitle("Color theme");
        parent3.setImageDrawable(icon);
        return parent3;
    }


    private List<Object> getChildrenFirstParent() {
        final Drawable drawableChild1 = getDrawable(R.drawable.ic_change_name_primary_green, themePreference.getPrimaryColor());
        final Drawable drawableChild2 = getDrawable(R.drawable.ic_change_pass_primary_green, themePreference.getPrimaryColor());
        final Drawable drawableChild3 = getDrawable(R.drawable.ic_ask_pass_primary_green, themePreference.getPrimaryColor());
        final Drawable drawableChild4 = getDrawable(R.drawable.ic_logout_primary_green, themePreference.getPrimaryColor());

        final SettingChild child1 = new SettingChild("Change name", false, drawableChild1, false);
        final SettingChild child2 = new SettingChild("Change password", false, drawableChild2, false);
        final SettingChild child3 = new SettingChild("Ask for password", authPreference.askPassword(), drawableChild3, false);
        final SettingChild child4 = new SettingChild("Log out", false, drawableChild4, true);
        final List<Object> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
        children.add(child3);
        children.add(child4);
        return children;
    }
    private List<Object> getChildrenSecondParent() {
        final Drawable drawableChild1 = getDrawable(R.drawable.ic_show_notification_primary_green, themePreference.getPrimaryColor());
        final SettingChild child1 = new SettingChild("Show notifications", authPreference.showNotifications(), drawableChild1, true);
        final List<Object> children = new ArrayList<>();
        children.add(child1);
        return children;
    }

    private List<Object> getChildrenThirdParent() {
        final Drawable drawableChild = getDrawable(R.drawable.ic_arrow_forward_24dp, themePreference.getPrimaryColor());
        final SettingChild child1 = new SettingChild("Standard", false, drawableChild, false);
        final SettingChild child2 = new SettingChild("Dark blue", false, drawableChild, false);
        final SettingChild child3 = new SettingChild("Red", false, drawableChild, false);
        final SettingChild child4 = new SettingChild("Orange", false, drawableChild, false);
        final SettingChild child5 = new SettingChild("Violet", false, drawableChild, false);
        final SettingChild child6 = new SettingChild("Black", false, drawableChild, false);
        final SettingChild child7 = new SettingChild("Pick color", false, drawableChild, true);
        final List<Object> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
        children.add(child3);
        children.add(child4);
        children.add(child5);
        children.add(child6);
        children.add(child7);
        return children;
    }

    private Drawable getDrawable(final int res, final int color) {
        final Drawable drawable = AppCompatResources.getDrawable(context, res);
        if (drawable != null) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        return drawable;
    }
}
