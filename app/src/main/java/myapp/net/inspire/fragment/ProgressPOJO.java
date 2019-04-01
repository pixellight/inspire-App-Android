package myapp.net.inspire.fragment;

/**
 * Created by QPay on 3/24/2019.
 */

public class ProgressPOJO {
    private String time;
    private String eventName;

    public ProgressPOJO() {
    }

    public ProgressPOJO(String time, String eventName) {
        this.time = time;
        this.eventName = eventName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
