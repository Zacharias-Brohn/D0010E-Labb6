package CarWashSimulator;

import GenericSimulator.Event;

/**
 * @author Niklas lehtola
 * Abstract class for CarWashEvents
 */
abstract class CarWashEvent extends Event {
//The array that holds the new events
    protected CarWashEvent[] newEvents;
    private String[] statistics;

    /**
     * Constructor that creates the statistics array and populates it with empty strings
     * @param occurenceTime time the vent occurs at
     * @param state the current state
     * @throws IllegalArgumentException when time goes backwards in time or is negative
     */
    protected CarWashEvent(double occurenceTime, CarWashState state) throws IllegalArgumentException {
        super(occurenceTime, state);
        statistics = new String[7];
        for (int i = 0; i < statistics.length; i++) {
            statistics[i] = "";
        }
    }
    
    /**
     * Returns the currentState of the simulator
     * @return the currentState of the simulator
     */
    @Override
    protected CarWashState currentState() {
        return (CarWashState) super.currentState();
    }

    /**
     * Returns the timestamp of the next Car arrival
     * @return the timestamp of the next Car arrival
     */
    protected double nextCarArrivalTime(double occurenceTime){
        return currentState().calcTime().nextCarArrival(occurenceTime);
    }

    /**
     * Calculates and sets the departure time and queue time for the car
     * @param car the given Car to calculate departure time for
     */
    protected double carLeavingTime(Car car, double occurenceTime){
       return currentState().calcTime().carLeavingTime(car, occurenceTime);
    }

    /**
     * Returns the array holding the starting parameters
     * @return the array holding the starting parameters
     */
    protected String[] statistics() {
        return statistics;
    }

    /**
     * Updates current time, time statistics and the CarWashView
     * @param event the event that invokes it
     * @param occurenceTime time the event occurs at
     * @param s the current state
     */
    protected void updateThings(CarWashEvent event, double occurenceTime, CarWashState s) {
        //Updates the time statistics
        double oldTime = s.getCurrentTime();

        double oldITime = s.getTotalIdleTime();
        double addedITime = s.calcTime().calculateIdleTime(oldTime, occurenceTime, s.getAvailableMachines());
        s.setTotalIdleTime(oldITime + addedITime);

        double oldQTime = s.getTotalTimeQueued();
        double addedQTime = s.calcTime().calculateQueueTime(oldTime, occurenceTime, s.carQueue().size());
        s.setTotalQueueTime(oldQTime + addedQTime);
        
        s.setCurrentTime(occurenceTime);

        //Updates the view
        currentState().UpdateView(event);
    }
}
