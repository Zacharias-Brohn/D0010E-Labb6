package GenericSimulator;

import java.util.Observable;

/**
 * @author Niklas Lehtola
 * An abstract class describing what all discrete event simulators should have in common.
 */
public abstract class SimState extends Observable {
    private boolean continueSim = true;
    private double currentTime = 0;

    /**
     * Creates the View of the Simulation (To be implemented by the subclasses)
     * @return The SimView
     */
    abstract protected SimView createView();

    /**
     * Trips the flag that the simulation should stop
     */
    protected void forceStop() {
        continueSim = false;
    }

    /**
     * Returns if the Sim should continue or not
     * @return if the Sim should continue or not
     */
    boolean continueSim(){
        return continueSim;
    }

    /**
     * Returns the current time
     * @return the current time
     */
    protected double getCurrentTime() {
        return currentTime;
    }   

    /**
     * Sets the current type
     * @param newTime the new time to set to
     */
    protected void setCurrentTime(double newTime) {
        currentTime = newTime;
    }
}
