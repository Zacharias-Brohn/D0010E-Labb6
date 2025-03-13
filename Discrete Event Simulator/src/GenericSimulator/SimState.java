package GenericSimulator;

import java.util.Observable;

/**
 * @author Niklas Lehtola
 * An abstract class describing what all discrete event simulators should have in common.
 */
public abstract class SimState extends Observable {
    private boolean stopSim = false;
    private double currentTime = 0;

    /**
     * Trips the flag that the simulation should stop
     */
    protected void forceStop() {
        this.stopSim = true;
    }

    /**
     * Returns if the stopSim flag is tripped or not
     * @return if the stopSim flag is tripped or not
     */
    boolean stopSim(){
        return this.stopSim;
    }

    /**
     * Returns the current time
     * @return the current time
     */
    protected double getCurrentTime() {
        return currentTime;
    }

    /**
     * Updates the current time
     * @param newTime the new time to update to
     */
    protected void updateCurrentTime(double newTime){
        this.currentTime = newTime;
    }
}
