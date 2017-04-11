package core;

public class Event {
    /**
     * The type of the event, should be self-explanatory
     */
    public final String type;
    /**
     * Any object, type should be consistent for a defined event type
     */
    public Object data = null;

    public Event(String type) {
        this.type = type;
    }

    public Event(String type, Object data) {
        this.type = type;
        this.data = data;
    }
}
