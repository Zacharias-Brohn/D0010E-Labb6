package CarWashSimulator;

import static CarWashSimulator.MachineType.*;

/**
 * @author Niklas lehtola
 * Class that defines how an arriving car behaves and how it affects the simulation
 */
class ArrivalEvent extends CarWashEvent {

    private Car arrivedCar = null;
    
    /**
     * @param occurenceTime
     * @param state
     * @throws IllegalArgumentException
     */
    protected ArrivalEvent(double occurenceTime, CarWashState state) throws IllegalArgumentException {
        super(occurenceTime, state);
    }
    
    /**
     * Describes the effect this event has on the state of the simulation
     */
    @Override
    public CarWashEvent[] invoke() {
        arrivedCar = createCar();
        //Updates the times then the observer CarWashView that an event has happened with this event as a parameter..
        updateThings(this, occurenceTime(), currentState());


        //Constants that depend on the number of slots available in the system
        final boolean MACHINES_AVAILABLE = currentState().getAvailableMachines() > 0;
        final boolean NO_MACHINES_AVAILABLE = !MACHINES_AVAILABLE;
        final boolean CAR_QUEUE_IS_FULL = currentState().carQueue().isFull();
        //Constants for how many events are created at car arrrivals 
        final int MAX_EVENTS = 2;
        final int NEW_EVENTS_CREATED = MACHINES_AVAILABLE ? MAX_EVENTS : 1;

        //adds the events into an array
        newEvents = new CarWashEvent[NEW_EVENTS_CREATED];

        //Three cases from here:
       
        //Case 1: Neither space in queue or available machines -> Car gets rejected  
        if (NO_MACHINES_AVAILABLE && CAR_QUEUE_IS_FULL) {
            currentState().incrementRejectedCars();
        }
        else {
            currentState().incrementAdmittedCars();


             //Case 2: There are available machines -> car gets serviced immediately
            if (MACHINES_AVAILABLE) {
                //Priority to entering a fast type carwasher if there's one available
                arrivedCar.setType(chooseFastestAvailableMachine());
                //Calculates and sets the car's departure time.
                double leavingTime = carLeavingTime(arrivedCar, occurenceTime());
                //Adds the departure event at index 1 as it only exists if there's machines available

                newEvents[1] = new DepartureEvent(leavingTime, currentState(), arrivedCar);

            //Case 3: No machines available, but there's space in the queue -> car gets entered into the CarQueue
            } else {
                enqueueCar(arrivedCar);
            }
        }
        

        //Creates the next ArrivalEvent
        newEvents[0] = new ArrivalEvent(nextCarArrivalTime(occurenceTime()), currentState());

        //returns the created events
        return newEvents;
    }

    /**
     * Returns the arrived car
     * @return the arrived car
     */
    Car getCar() {
        return arrivedCar;
    }

    /**
     * Returns the type of event
     * @return the type of event
     */
    @Override
    public String toString() {
        return "Arrival";
    }

    //----------------------------------
    //  Helper functions
    //----------------------------------

    //Creates a car and enqueues it in the CarQueue
    private void enqueueCar(Car car){
        currentState().carQueue().enqueue(car);
    }

    //Creates a new car from the CarFactory and returns it
    private Car createCar(){
        Car car = currentState().createCar();
        return car;
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
