package trickyquestion.messenger.setting.expandList;

public class SettingChild {

    private String title;
    private boolean checked;
    private int imageResource;

    public SettingChild(final String title, final boolean checked, final int imageResource) {
        this.title = title;
        this.checked = checked;
        this.imageResource = imageResource;
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

    public int getImageResourse() {
        return imageResource;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
