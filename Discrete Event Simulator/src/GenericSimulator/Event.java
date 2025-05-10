package GenericSimulator;


/**
 * @author Niklas Lehtola
 * 
 * The abstract class that describes an event
 */
abstract public class Event {
    private double occurenceTime;
    private SimState state;

    protected Event(double occurenceTime, SimState state) throws IllegalArgumentException{
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null or empty.");
        }
        if (occurenceTime < state.getCurrentTime()) { //Cannot go back in time
            throw new IllegalArgumentException("Cannot go back in time.");
        }
        if (occurenceTime < 0) {
            throw new IllegalArgumentException("Cannot occur at negative time");
        }
        this.occurenceTime = occurenceTime;
        this.state = state;
    }

    /**
     * The time the event occurs
     * @return the time the event occurs
     */
    protected double occurenceTime(){
        return occurenceTime;
    }

    /**
     * Returns the SimState conenctd to the simulation
     * @return the SimState conenctd to the simulation
     */
    protected SimState currentState(){
        return state;
    }


    /**
     * The abstract method to invoke the effect of the event
     * @return Ab array of Events created by the onvoking of the event
     */
    abstract public Event[] invoke();


}
