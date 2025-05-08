package CarWashSimulator;

import static CarWashSimulator.MachineType.*;

/**
 * @author Niklas Lehtola
 * Class that defines how an departing car behaves and how it affect the simulation
 */
class DepartureEvent extends CarWashEvent {
    private Car leavingCar;

    /**
     * Constructor for the departure event
     * @param occurenceTime the time the vent occurs at
     * @param state the state to affect
     * @param leavingCar the car leaving the system
     * @throws IllegalArgumentException if the occurenceTime is set in the past
     */
    protected DepartureEvent(double occurenceTime, CarWashState state, Car leavingCar) throws IllegalArgumentException {
            super(occurenceTime, state);
            this.leavingCar = leavingCar;
        }

    /**
     * Does nothing if the Car queue is empty, else it pops the first car 
     * in the queue and creates a departure event for it from the released carwash
     */
    @Override
    public CarWashEvent[] invoke() {
        //Updates the times then the observer CarWashView that an event has happened with this event as a parameter..
        updateThings(this, occurenceTime(), currentState());

        final boolean CARQUEUE_IS_EMPTY = currentState().carQueue().isEmpty();
        final MachineType RELEASED_CARWASHER = releasedMachine(leavingCar);
        //Creates no new events if the queue is empty
        final int NEW_EVENTS_CREATED = CARQUEUE_IS_EMPTY ? 0 : 1;

        //Adds the events into an array
        newEvents = new CarWashEvent[NEW_EVENTS_CREATED];

        //Two cases from here:
        //Case 1: CarQueue is empty -> a machine is released
        if (CARQUEUE_IS_EMPTY) {
            if (RELEASED_CARWASHER == FastMachine) {
                currentState().incrementFastMachines();
            } else if (RELEASED_CARWASHER == SlowMachine) {
                currentState().incrementSlowMachines();
            }
        //Case 2: CarQeueu is not empty -> The first Car in the queue takes the released carwasher and enters
        } else {;
            Car nextCar = dequeueNextCar(); //First in the CarQueue
            nextCar.setType(RELEASED_CARWASHER); //The machine that was just released
            //Calculates and sets the car's departure time.
            double leavingTime = carLeavingTime(nextCar, occurenceTime());
            //Adds the departure event at index 1 as it only exists if there's machines available
            newEvents[0] = new DepartureEvent(leavingTime, currentState(), nextCar);
        }
        
        //returns the new events created
        return newEvents;
    }

    /**
     * Returns the type of event
     * @return the type of event
     */
    @Override
    public String toString() {
        return "Departure";
    }

    /**
     * Returns the leaving car
     * @return the leaving car
     */
    Car getCar() {
        return leavingCar;
    }

    //--------------------------
    //  Helper functions
    //--------------------------
    
    private Car dequeueNextCar(){
        return currentState().carQueue().dequeue();
    }

    private MachineType releasedMachine(Car car){
        return car.getType();
    }

}
