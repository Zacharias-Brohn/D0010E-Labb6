package CarWashSimulator;

import static CarWashSimulator.MachineType.*;

/**
 * @author Niklas Lehtola
 * Class that defines how an departing car behaves and how it affect the simulation
 */
class DepartureEvent extends CarWashEvent {
    Car leavingCar;

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
        final boolean CARQUEUE_IS_EMPTY = currentState().carQueue().isEmpty();
        final MachineType RELEASED_CARWASHER = releasedMachine(this.leavingCar);
        //Creates no new events if the queue is empty
        final int NEW_EVENTS_CREATED = CARQUEUE_IS_EMPTY ? 0 : 1;

        //The array that holds the events
        CarWashEvent[] newEvents = new CarWashEvent[NEW_EVENTS_CREATED];

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
            Car nextCar = dequeueNextCar(); //First in teh CarQueue
            nextCar.setType(RELEASED_CARWASHER); //The machine that was just released
            //Calculates when nextCar will leave and creates its departure event
            newEvents[0] = new DepartureEvent(carLeaving(nextCar), currentState(), nextCar);
        }

        //Updates the total idling time
        currentState().updateIdleTime(this.occurenceTime());
        //Adds the time this car has queued to the total
        currentState().updateTotalQueueingTime(this.leavingCar.getTimeQueued());
        
        //Updates the observer CarWashView that an event has happened with this event as a parameter.
        currentState().UpdateView(this);
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

    private Car dequeueNextCar(){
        return currentState().carQueue().dequeue();
    }

    private MachineType releasedMachine(Car car){
        return car.getType();
    }

}
