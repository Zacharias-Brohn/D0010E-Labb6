package CarWashSimulator;

import static CarWashSimulator.SimulationStatistics.*;

/**
 * @author Niklas lehtola
 * StartEvent for a CarWashSimulation that generates the first event.
 */
public class StartEvent extends CarWashEvent {
    
    private double stopTime;
    
    /**
     * Creates a StartEvent at specified time
     * @param startTime the time to start the simulation
     * @param stopTime the time to stop the simulation
     * @param state the state to affect
     * @param seed  the seed that is addedd to the random streams
     * @param useSeed whether to use the seed or not
     * @param lambda the lambda value for the exponential stream
     * @param slowLowerBound lower bound of the time that slow carwashers take
     * @param slowUpperBound upper bound of the time that slow carwashers take
     * @param fastLowerBound lower bound of the time that fast carwashers take
     * @param fastUpperBound upper bound of the time that fast carwashers take
     * @throws IllegalArgumentException if stopTime is negative
     */
    public StartEvent(double startTime, double stopTime, CarWashState state,
        double fastLowerBound, double fastUpperBound, double slowLowerBound, 
        double slowUpperBound, long seed, boolean useSeed, double lambda) throws IllegalArgumentException {

        super(startTime, state);
        if (stopTime < 0) {
            throw new IllegalArgumentException("Stop time may not be negative!");
        }
        if (fastLowerBound < 0 || fastUpperBound < 0 || fastUpperBound < fastLowerBound ||
            slowLowerBound < 0 || slowUpperBound < 0 || slowUpperBound < slowLowerBound ||
            fastLowerBound > slowLowerBound || fastUpperBound > slowUpperBound) {
                throw new IllegalArgumentException("Illegal bound values");
        }
        if (lambda < 0) {
            throw new IllegalArgumentException("Illegal lambda value");
        }
        this.stopTime = stopTime;
        statistics()[Seed.index()]            = String.valueOf(seed); 
        statistics()[UseSeed.index()]         = String.valueOf(useSeed);
        statistics()[Lambda.index()]          = String.valueOf(lambda);
        statistics()[SlowLowerBound.index()]  = String.valueOf(slowLowerBound);
        statistics()[SlowUpperBound.index()]  = String.valueOf(slowUpperBound);
        statistics()[FastLowerBound.index()]  = String.valueOf(fastLowerBound);
        statistics()[FastUpperBound.index()]  = String.valueOf(fastUpperBound);
        state.setupCalcTime(seed, useSeed, lambda, slowLowerBound, slowUpperBound, fastLowerBound, fastUpperBound);
    }
    
    /**
     * Calculates the arrival time of the first car
     * and returns it along with the StopEvent at the given stopTime
     * @return CarWashEvent[] of the first carArrivalEvent and of the StopEvent
     */
    @Override
    public CarWashEvent[] invoke() {
        //Updates the times then the observer CarWashView that an event has happened with this event as a parameter..
        updateThings(this, occurenceTime(), currentState());

        //Constant for how many events are created at startup 
        final int    EVENTS_CREATED_AT_STARTUP = 3;
        final int    MULTIPLICATION_FACTOR = 1000;
        final double STOP_EVENT_BACKUP = stopTime*MULTIPLICATION_FACTOR;
        //The array that holds the new events
        CarWashEvent[] newEvents = new CarWashEvent[EVENTS_CREATED_AT_STARTUP];

        //Calculates the arrival time of the first car.
        double firstCarArrival = nextCarArrivalTime(occurenceTime());

        //Creates the actual events
        newEvents[0] = new StopEvent(stopTime, currentState());             //Stop event at specified time
        newEvents[1] = new StopEvent(STOP_EVENT_BACKUP, currentState());    //Backup stop event way past any reasonable simulation
        newEvents[2] = new ArrivalEvent(firstCarArrival, currentState());   //Arrival event for the first car

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
