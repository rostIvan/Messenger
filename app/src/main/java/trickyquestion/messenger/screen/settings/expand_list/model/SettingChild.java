package trickyquestion.messenger.screen.settings.expand_list.model;

import android.graphics.drawable.Drawable;

public class SettingChild {

    private String title;
    private boolean checked;
    private Drawable drawable;
    private boolean isLast;

    public SettingChild(final String title, final boolean checked, final Drawable drawable, final boolean isLast) {
        this.title = title;
        this.checked = checked;
        this.drawable = drawable;
        this.isLast = isLast;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable= drawable;
    }

    public Drawable getIconDrawable() {
        return drawable;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
