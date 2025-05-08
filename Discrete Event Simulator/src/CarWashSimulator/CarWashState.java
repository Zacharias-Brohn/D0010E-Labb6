package CarWashSimulator;

import GenericSimulator.SimState;


/**
 * @author Niklas lehtola
 * 
 * Class that descirbes the current state of the CarwashSimulation
 */
public class CarWashState extends SimState {

    private CarFactory    carFactory = new CarFactory();
    private CarQueue      carQueue;
    private CalculateTime calcTime;
    private boolean       shouldPrint;
    
    private int     maxQueueSize;
    private int     slowMachines;
    private int     fastMachines;
    private double  totalIdleTime  = 0;
    private double  totalQueueTime = 0;
    private int     admittedCars = 0;
    private int     rejectedCars   = 0;
    
    // -----------------------------------------------------
    //  Constructors
    // -----------------------------------------------------

    /**
     * Constructor for creating a fully customizable CarWashState
     * 
     * @param slowMachines the number of slow carwashers
     * @param fastMachines the number of fast carwashers
     * @param maxQueueSize max size queue of waiting cars
     * @param printOut wether the view should print the results or not
     */
    public CarWashState(int slowMachines, int fastMachines, int maxQueueSize, boolean shouldPrint){
        if (slowMachines < 0 || fastMachines < 0 || (fastMachines+slowMachines) <= 0) {
            throw new IllegalArgumentException("Machines may not be negative or non-existent");
        }
        if (maxQueueSize < 0) {
            throw new IllegalArgumentException("Queue may not be negative");
        }
        this.slowMachines = slowMachines;
        this.fastMachines = fastMachines;
        this.maxQueueSize = maxQueueSize;
        this.shouldPrint = shouldPrint;
        carQueue  = new CarQueue(maxQueueSize);
    }

    void setupCalcTime(long seed, boolean useSeed, double lambda, 
                        double slowLowerBound, double slowUpperBound, 
                        double fastLowerBound, double fastUpperBound) {
        //Creates the instance of calcTime specified
        this.calcTime = new CalculateTime(seed, useSeed, lambda, 
                                          slowLowerBound, slowUpperBound, 
                                          fastLowerBound, fastUpperBound);
    }

    protected CarWashView createView(){
        CarWashView view = new CarWashView(shouldPrint);
        this.addObserver(view);
        return view;
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
     * Returns the amount of admitted cars
     * @return the amount of admitted cars
     */
    int getAdmittedCars() {
        return this.admittedCars;
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
     * Sets the current time
     * @param newTime time to set to
     */
    @Override
    protected void setCurrentTime(double newTime){
        calcTime().isValidTime(getCurrentTime(), newTime);
        super.setCurrentTime(newTime);
    }

    /**
     * Updates the total idling time
     * @param newTime time to set to (may not be smaller than previous value)
     */
    void setTotalIdleTime(double newTime) {
        calcTime().isValidTime(getTotalIdleTime(), newTime);
        totalIdleTime = newTime;
    }

    /**
     * Updates the total queueing time
     * @param newTime time to set to (may not be smaller than previous value)
     */
    void setTotalQueueTime(double newTime) {
        calcTime().isValidTime(getTotalTimeQueued(), newTime);
        totalQueueTime = newTime;
    }

    /**
     * Creates and returns a car from the carFactory
     * @param enteredAtTime parameter for when the car entered the system
     * @return the created car
     */
    Car createCar(){
        return this.carFactory.createCar();
    }

    /**
     * Increments the amount of fast machines by 1
     */
    void incrementFastMachines(){
        fastMachines += 1;
    }

    /**
     * Decrements the amount of fast machines by 1
     */
    void decrementFastMachines(){
        fastMachines -= 1;
    }

    /**
     * Increments the amount of slow machines by 1
     */
    void incrementSlowMachines(){
        slowMachines += 1;
    }

    /**
     * Decrements the amount of slow machines by 1
     */
    void decrementSlowMachines(){
        slowMachines -= 1;
    }

     /**
     * Increments the amount of rejected cars by 1
     */
    void incrementAdmittedCars(){
        admittedCars += 1;
    }   


    /**
     * Increments the amount of rejected cars by 1
     */
    void incrementRejectedCars(){
        rejectedCars += 1;
    }   

    /**
     * Sends the latest event to CarWashView
     * @param event the latest event to be invoked
     */
    protected void UpdateView(CarWashEvent event){

        this.setChanged();
        this.notifyObservers(event);
    }  

    /**
     * Trips the flag to stop the simulation
     */
    @Override
    protected void forceStop() {
        super.forceStop();
    }
}
