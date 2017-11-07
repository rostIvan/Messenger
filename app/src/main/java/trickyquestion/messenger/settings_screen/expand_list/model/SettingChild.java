package trickyquestion.messenger.settings_screen.expand_list.model;

public class SettingChild {

    private String title;
    private boolean checked;
    private int imageResource;
    private boolean isLast;

    public SettingChild(final String title, final boolean checked, final int imageResource, final boolean isLast) {
        this.title = title;
        this.checked = checked;
        this.imageResource = imageResource;
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

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
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
