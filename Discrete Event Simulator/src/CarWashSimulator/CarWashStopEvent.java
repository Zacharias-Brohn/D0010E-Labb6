package CarWashSimulator;



/**
 * @author Niklas lehtola
 * Describres how the stop event should behave for the CarWashSimulation
 */
class CarWashStopEvent extends CarWashEvent {

    CarWashStopEvent(double occurenceTime, CarWashState state) throws IllegalArgumentException {
        super(occurenceTime, state);
    }

    /**
     * Trips the stopSim flag and updates the time statistics one final time
     */
    @Override
    public CarWashEvent[] invoke() {
        currentState().forceStop();
        //Updates the time statistics
        currentState().updateIdleTime(this.occurenceTime());

        //Updates the observer CarWashView that an event has happened with this event as a parameter.
        currentState().UpdateView(this);
        return null;
    }

    /**
     * Returns the type of event
     * @return the type of event
     */
    @Override
    public String toString() {
        return "Stop";
    }
    


}
