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
        if (occurenceTime < state.getCurrentTime()) { //Cannot go back in time
            throw new IllegalArgumentException("Cannot create an event that goes back in time!");
        }
        this.occurenceTime = occurenceTime;
        this.state = state;
    }

    protected double occurenceTime(){
        return this.occurenceTime;
    }

    protected SimState currentState(){
        return this.state;
    }


    abstract public Event[] invoke();


}
