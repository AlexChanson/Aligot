package utility;


/**
 * Created by ben on 20/05/17.
 */
public class GameTimer {

    private double maxTime;
    private double time;
    public boolean enabled;

    public GameTimer(double maxTime){
        this.maxTime = maxTime;
        time = 0;
        enabled = true;
    }

    /**
     * increment internal time if timer is enabled
     * @param increment time to pass
     * @return true if timer is expired else false
     */
    public boolean increment(double increment){
        if (enabled) {
            this.time += increment;
            if (time >= maxTime){
                time = maxTime;
                return true;
            }
        }
        return false;
    }

    public void restart(){
        time = 0;
        enabled = true;
    }

    /**
     *
     * @return true if timer is expired else false
     */
    public boolean done(){
        return time >= maxTime;
    }

    public double getTime() {
        return time;
    }

    public double getMaxTime() {
        return maxTime;
    }
}
