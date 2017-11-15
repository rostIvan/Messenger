package trickyquestion.messenger.util.event_bus_pojo;

public class ChangeThemeEvent {

    private final int color;

    public ChangeThemeEvent(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
