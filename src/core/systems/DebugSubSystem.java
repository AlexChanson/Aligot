package core.systems;

import core.Event;

import java.util.Arrays;

public class DebugSubSystem  extends SubSystem{

    private String[] ignored;

    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        for (String type : ignored) {
            if (type.equals(event.type))
                return;
        }
        System.out.printf("ENGINE EVENT { type='%s', data='%s' }%n", event.type, String.valueOf(event.data));
    }

    public DebugSubSystem ignore(String... ignored){
        this.ignored = Arrays.copyOf(ignored, ignored.length);
        return this;
    }
}
