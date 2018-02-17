package trickyquestion.messenger.util.android.event_bus_pojo;

public class ChangeThemeEvent {

    private  int color;
    private String  colorHex;

    public ChangeThemeEvent(int color) {
        this.color = color;
    }

    public ChangeThemeEvent(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getColorHex() {
        return colorHex;
    }

    public int getColor() {
        return color;
    }
}
