package CarWashSimulator;



/**
 * @author Niklas lehtola
 * Describres how the stop event should behave for the CarWashSimulation
 */
class StopEvent extends CarWashEvent {

    StopEvent(double occurenceTime, CarWashState state) throws IllegalArgumentException {
        super(occurenceTime, state);
    }

    /**
     * Trips the stopSim flag and updates the time statistics one final time
     */
    @Override
    public CarWashEvent[] invoke() {
        //Updates the times then the observer CarWashView that an event has happened with this event as a parameter..
        updateThings(this, occurenceTime(), currentState());

        //Trips the flag that the sim should stop
        currentState().forceStop();

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
