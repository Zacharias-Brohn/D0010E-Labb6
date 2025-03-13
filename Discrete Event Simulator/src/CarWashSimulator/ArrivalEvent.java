package CarWashSimulator;

import static CarWashSimulator.MachineType.*;

/**
 * @author Niklas lehtola
 * Class that defines how an arriving car behaves and how it affect the simulation
 */
class ArrivalEvent extends CarWashEvent {

    private Car arrivedCar;
    
    /**
     * @param occurenceTime
     * @param state
     * @throws IllegalArgumentException
     */
    protected ArrivalEvent(double occurenceTime, CarWashState state) throws IllegalArgumentException {
        super(occurenceTime, state);
    }
    
    /**
     * 
     */
    @Override
    public CarWashEvent[] invoke() {
        //Constants that depend on the number of slots available in the system
        final boolean MACHINES_AVAILABLE = currentState().getAvailableMachines() > 0;
        final boolean NO_MACHINES_AVAILABLE = !MACHINES_AVAILABLE;
        final boolean CAR_QUEUE_HAS_SPACE = !currentState().carQueue().isFull();
        //Constants for how many events are created at car arrrivals 
        final int MAX_EVENTS = 2;
        final int NEW_EVENTS_CREATED = MACHINES_AVAILABLE ? MAX_EVENTS : 1;

        //Updates the time statistics
        currentState().updateIdleTime(this.occurenceTime());


        //The array that holds the events
        CarWashEvent[] newEvents = new CarWashEvent[NEW_EVENTS_CREATED];

        //Three cases from here:
        //Case 1: There are available machines -> car gets created and serviced immediately
        if (MACHINES_AVAILABLE) { 
            //creates a car for the arrival event and creates the departure event with said car
            this.arrivedCar = currentState().createCar(this.occurenceTime());
            //Priority to entering a fast type carwasher if there's one avaialble
            this.arrivedCar.setType(chooseFastestAvailableMachine());

            //Adds the departure event at index 1 as it only exists if there's machines available

            //newEvents[1] = new DepartureEvent

        //Case 2: No machines available, but there's space in the queue -> car gets created and entered into the CarQueue
        } else if (NO_MACHINES_AVAILABLE && CAR_QUEUE_HAS_SPACE) { 
            enqeueuCar(currentState().createCar(this.occurenceTime()));

        //Case 3: Neither space in queue or available machines -> Car gets rejected    
        } else {
            currentState().incrementRejectedCars();
        }

        //Creates the next ArrivalEvent
        newEvents[0] = new ArrivalEvent(nextCarArrival(), currentState());

        //Updates the observer CarWashView that an event has happened with this event as a parameter.
        currentState().UpdateView(this);

        //returns the created events
        return newEvents;
    }

    /**
     * Returns the type of event
     * @return the type of event
     */
    @Override
    public String toString() {
        return "Arrival";
    }

    //Enqueues the given car in the CarQueue
    private void enqeueuCar(Car car){
        currentState().carQueue().enqueue(car);
    }

    //Returns the fastest available carwasher type
    private MachineType chooseFastestAvailableMachine(){
        if (currentState().getFastMachines() > 0) {
            currentState().decrementFastMachines();
            return FastMachine;
        } else {
            currentState().decrementSlowMachines();
            return SlowMachine;
        }
    }
    



}
