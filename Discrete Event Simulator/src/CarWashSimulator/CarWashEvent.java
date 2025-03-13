package CarWashSimulator;

import GenericSimulator.Event;

/**
 * @author Niklas lehtola
 * Abstract class for CarWashEvents
 */
abstract class CarWashEvent extends Event {

    /**
     * 
     * @param occurenceTime
     * @param state
     * @throws IllegalArgumentException
     */
    protected CarWashEvent(double occurenceTime, CarWashState state) throws IllegalArgumentException {
        super(occurenceTime, state);
    }
    
    /**
     * Returns the currentState of the simulator
     * @return the currentState of the simulator
     */
    @Override
    public CarWashState currentState() {
        return (CarWashState) super.currentState();
    }

    /**
     * Returns the timestamp of the next Car arrival
     * @return the timestamp of the next Car arrival
     */
    double nextCarArrival(){
        return currentState().calcTime().nextCarArrival(this.occurenceTime());
    }

    /**
     * Calculates the departure time of the given Car type
     * @param car the given Car to calculate departure time for
     * @return the timestamp when the Car departs
     */
    double carLeaving(Car car){
        return currentState().calcTime().leavingTime(car, currentState().getCurrentTime());
    }
}
