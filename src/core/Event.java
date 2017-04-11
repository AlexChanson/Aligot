package core;

public class Event {
    public final String type;
    public Object data = null;

    public Event(String type) {
        this.type = type;
    }

    public Event(String type, Object data) {
        this.type = type;
        this.data = data;
    }
}
