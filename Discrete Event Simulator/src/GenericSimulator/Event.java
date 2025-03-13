package GenericSimulator;

//TODO: 

abstract public class Event {
    private double occurenceTime;
    private SimState state;

    protected Event(double occurenceTime, SimState state) throws IllegalArgumentException{
        if (occurenceTime < 0) { //Can't create events in negative time
            throw new IllegalArgumentException("Cannot create an event with negative occurence time!");
        } else if (occurenceTime < state.getCurrentTime()) { //Cannot go back in time
            throw new IllegalArgumentException("Cannot create an event that goes back in time!");
        }
        this.occurenceTime = occurenceTime;
        this.state = state;
    }

    protected double occurenceTime(){
        return this.occurenceTime;
    }

    protected SimState getState(){
        return this.state;
    }

    abstract public Event[] invoke();

    public static void main(String[] args) {
        
    }

}
