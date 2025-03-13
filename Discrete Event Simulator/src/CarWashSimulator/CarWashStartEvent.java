package CarWashSimulator;

import static CarWashSimulator.CarWashDefaultConstants.*;

/**
 * @author Niklas lehtola
 * StartEvent for a CarWashSimulation that generates the first event.
 */
public class CarWashStartEvent extends CarWashEvent {
    
    private double stopTime;
    
    /**
     * Creates a StartEvent at time 0 for the given state
     * @param stopTime the time to stop the simulation
     * @param state the state to affect
     * @throws IllegalArgumentException if stopTime is negative
     */
    public CarWashStartEvent(double startTime, double stopTime, CarWashState state) throws IllegalArgumentException {
        super(startTime, state);
        if (stopTime < 0) {
            throw new IllegalArgumentException("Stop time may not be negative!");
        }
        this.stopTime = stopTime;
    }

    /**
     * Creates a start event with the default stop stime
     * @param state the state to affect
     */
    public CarWashStartEvent(CarWashState state){
        this(DEFAULT_START_TIME, DEFAULT_STOP_TIME, state);
    }
    
    /**
     * Calculates the arrival time of the first car
     * and returns it along with the StopEvent at the given stopTime
     * @return CarWashEvent[] of the first carArrivalEvent and of the StopEvent
     */
    @Override
    public CarWashEvent[] invoke() {
        //Constant for how many events are created at startup 
        final int EVENTS_CREATED_AT_STARTUP = 3;
        final double STOP_EVENT_BACKUP = this.stopTime*1000;
        //The array that holds the new events
        CarWashEvent[] newEvents = new CarWashEvent[EVENTS_CREATED_AT_STARTUP];

        //Calculates the arrival time of the first car.
        double firstCarArrival = nextCarArrival();

        //Creates the actual events
        newEvents[0] = new CarWashStopEvent(this.stopTime, currentState()); //Stop event at specified time
        newEvents[1] = new CarWashStopEvent(STOP_EVENT_BACKUP, currentState()); //Backup stop event way past any reasonable simulation
        newEvents[2] = new ArrivalEvent(firstCarArrival, currentState()); //Arival event for the first car

        //Updates the observer CarWashView that an event has happened with this event as a parameter.
        currentState().UpdateView(this);

        return newEvents;
    }

    /**
     * Returns the type of event
     * @return the type of event
     */
    @Override
    public String toString() {
        return "Start";
    }
    

}
