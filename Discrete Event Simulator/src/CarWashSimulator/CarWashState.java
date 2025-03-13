package CarWashSimulator;

import GenericSimulator.SimState;
import static CarWashSimulator.CarWashDefaultConstants.*;


/**
 * @author Niklas lehtola
 * 
 * Class that descirbes the current state of the CarwashSimulation
 */
public class CarWashState extends SimState {

    private CarFactory carFactory = new CarFactory();
    private CarQueue carQueue;
    private CalculateTime calcTime;
    private int maxQueueSize;

    private int slowMachines;
    private int fastMachines;
    private double totalIdleTime = 0;
    private double totalQueueTime = 0;
    private int rejectedCars = 0;
    
    // -----------------------------------------------------
    //  Constructors
    // -----------------------------------------------------

    /**
     * Constructor for creating a fully customizable CarWashState
     * 
     * @param slowMachines the number of slow carwashers
     * @param fastMachines the number of fast carwashers
     * @param maxQueueSize max size queue of waiting cars
     * @param seed  the seed that is addedd to the random streams
     * @param lambda the lambda value for the exponential stream
     * @param slowLowerBound lower bound of the time that slow carwashers take
     * @param slowUpperBound upper bound of the time that slow carwashers take
     * @param fastLowerBound lower bound of the time that fast carwashers take
     * @param fastUpperBound upper bound of the time that fast carwashers take
     */
    public CarWashState(int slowMachines, int fastMachines, int maxQueueSize, 
                        long seed, boolean useSeed, double lambda, 
                        double slowLowerBound, double slowUpperBound, 
                        double fastLowerBound, double fastUpperBound){

        //Creates the instance of calcTime specified
        this.calcTime = new CalculateTime(seed, useSeed, lambda, 
                                          slowLowerBound, slowUpperBound, 
                                          fastLowerBound, fastUpperBound);
        this.slowMachines = slowMachines;
        this.fastMachines = fastMachines;
        this.maxQueueSize = maxQueueSize;
        carQueue  = new CarQueue(maxQueueSize);
        
    }

    /**
     * Constructor that creates a CarWashState with the Default saved values.
     */
    public CarWashState(boolean useSeed){
        this(SLOW_MACHINES, FAST_MACHINES, MAX_QUEUE_SIZE, 
             DEFAULT_SEED, useSeed, DEFAULT_LAMBDA,
             SLOW_LOWER_BOUND, SLOW_UPPER_BOUND, 
             FAST_LOWER_BOUND, FAST_UPPER_BOUND);
    }

    CarWashView createDisplay(){
        return new CarWashView(true);
    }

    // -----------------------------------------------------
    //  Query Methods
    // -----------------------------------------------------
    
    /**
     * Returns the max queue size
     * @return the max queue size
     */
    int getMaxQueueSize() {
        return maxQueueSize;
    }

    /**
     * Returns the current number of available slow carwashers
     * @return the current number of available slow carwashers
     */
    int getSlowMachines() {
        return this.slowMachines;
    }

    /**
     * Returns the current number of available fast carwashers
     * @return the current number of available fast carwashers
     */
    int getFastMachines() {
        return this.fastMachines;
    }

    /**
     * Returns the number of available machines
     * @return the number of available machines
     */
    int getAvailableMachines(){
        return getFastMachines() + getSlowMachines();
    }

    /**
     * Returns the instance of the class that calculates time
     * @return the instance of the class that calculates time
     */
    CalculateTime calcTime(){
        return this.calcTime;
    }

    /**
     * Returns the CarQueue
     * @return the CarQueue
     */
    CarQueue carQueue(){
        return this.carQueue;
    }

    /**
     * Returns the current time.
     * @return the current time
     */
    @Override
    protected double getCurrentTime() {
        return super.getCurrentTime();
    }

    /**
     * Returns the amount of rejected cars
     * @return the amount of rejected cars
     */
    int getRejectedCars() {
        return this.rejectedCars;
    }

    /**
     * Returns the total time machines spent idling
     * @return the total time machines spent idling
     */
    double getTotalIdleTime(){
        return this.totalIdleTime;
    }

    /**
     * Returns the total time Cars spent in the CarQueue
     * @return the total time Cars spent in the CarQueue
     */
    double getTotalTimeQueued(){
        return this.totalQueueTime;
    }
    
    // -----------------------------------------------------
    //  Update Methods
    // -----------------------------------------------------

    /**
     * Trips the flag that the simulation should stop
     */
    @Override
    protected void forceStop() {
        super.forceStop();
    }
    /**
     * Creates and returns a car from the carFactory
     * @param enteredAtTime parameter for when the car entered the system
     * @return the created car
     */
    Car createCar(double enteredAtTime){
        return this.carFactory.createCar(enteredAtTime);
    }

    /**
     * Increments the amount of fast machines by 1
     */
    void incrementFastMachines(){
        this.fastMachines += 1;
    }

    /**
     * Decrements the amount of fast machines by 1
     */
    void decrementFastMachines(){
        this.fastMachines -= 1;
    }

    /**
     * Increments the amount of slow machines by 1
     */
    void incrementSlowMachines(){
        this.slowMachines += 1;
    }

    /**
     * Decrements the amount of slow machines by 1
     */
    void decrementSlowMachines(){
        this.slowMachines -= 1;
    }

    /**
     * Increments the amount of rejected cars by 1
     */
    void incrementRejectedCars(){
        this.rejectedCars += 1;
    }

    /**
     * Updates the total time carwashers spent idling since last invoked event
     * @param newTime time of the latest event
     */
    void updateIdleTime(double newTime){
        calcTime().isValidTime(getCurrentTime(), newTime);
        this.totalIdleTime += calcTime().calculateIdleTime(getCurrentTime(), newTime, getAvailableMachines());
    }

    /**
     * Adds newTime to the current total queueing tme
     * @param newTime time to add to the total queueing time
     * @throws IllegalArgumentException if newTime is negative
     */
    void updateTotalQueueingTime(double newTime) throws IllegalArgumentException {
        if (newTime < 0 ) {
            throw new IllegalArgumentException("Total queueing time cannot decrease!");
        }
        this.totalQueueTime += newTime;
    }


    /**
     * Updates the current time to the newtime
     * @param newTime the new time 
     */
    @Override
    protected void updateCurrentTime(double newTime) {
        calcTime().isValidTime(getCurrentTime(), newTime);
        super.updateCurrentTime(newTime);
    }
    

    /**
     * Sends the latest event to CarWashView
     * @param event the latest event to be invoked
     */
    protected void UpdateView(CarWashEvent event){

        this.setChanged();
        this.notifyObservers(event);
    }  
}
