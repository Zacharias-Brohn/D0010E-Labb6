package CarWashSimulator;


/**
 * @author Niklas Lehtola
 * 
 * A class that handles all the time calculations
 */
class CalculateTime {
    private ExponentialRandomStream carStream;
    private UniformRandomStream slowMachineStream;
    private UniformRandomStream fastMachineStream;

    private double slowLowerBound;
    private double slowUpperBound;
    private double fastLowerBound;
    private double fastUpperBound;
    private double lambda;
    private long seed;

    /**
     * Constructor for the randomstreams used to calculate time
     * @param seed unique seed that structures the random number to be the same every run with the same seed
     * @param useSeed whether to use the seed or not
     * @param lambda what to divide the car arrival time by
     * @param slowLowerBound lower bound of time in slow carwashers
     * @param slowUpperBound upper bound of time in slow carwashers
     * @param fastLowerBound lower bound of time in fast carwashers
     * @param fastUpperBound upper bound of time in fast carwashers
     */
    CalculateTime(long seed, boolean useSeed, double lambda, 
                  double slowLowerBound, double slowUpperBound, 
                  double fastLowerBound, double fastUpperBound){

        //Two version depending on if the seed is to be used or not
        if (useSeed) {  //"structured" randoms
            this.carStream = new ExponentialRandomStream(lambda, seed);
            this.slowMachineStream = new UniformRandomStream(slowLowerBound, slowUpperBound, seed);
            this.fastMachineStream = new UniformRandomStream(fastLowerBound, fastUpperBound, seed);

        } else {        //Non-structured "randoms"
            this.carStream = new ExponentialRandomStream(lambda);
            this.slowMachineStream = new UniformRandomStream(slowLowerBound, slowUpperBound);
            this.fastMachineStream = new UniformRandomStream(fastLowerBound, fastUpperBound); 
        }
        this.slowLowerBound = slowLowerBound;
        this.slowUpperBound = slowUpperBound;
        this.fastLowerBound = fastLowerBound;
        this.fastUpperBound = fastUpperBound;
        this.lambda = lambda;
        this.seed = seed;
    }

    /**
     * Calculates the arrival of the next car.
     * @return arrival time of the next car.
     */
    double nextCarArrival(double currentTime){
        return currentTime + this.carStream.next();
    }

    /**
     * Returns slowLowerBound
     * @return slowLowerBound
     */
    public double getSlowLowerBound() {
        return slowLowerBound;
    }

    /**
     * Returns slowUpperBound
     * @return slowUpperBound
     */
    public double getSlowUpperBound() {
        return slowUpperBound;
    }

    /**
     * Returns FastLowerBound
     * @return FastLowerBound
     */
    public double getFastLowerBound() {
        return fastLowerBound;
    }

    /**
     * Returns FastUpperBound
     * @return FastUpperBound
     */
    public double getFastUpperBound() {
        return fastUpperBound;
    }

    /**
     * Returns Lambda
     * @return Lambda
     */
    public double getLambda() {
        return lambda;
    }

    /**
     * Returns the seed
     * @return the seed
     */
    public long getSeed() {
        return seed;
    }

    /**
     * Calculates when the Car will leave the carwasher it just entered\n
     * Also calculates how long the car spent in the Queue
     * @param car the car to calculate for
     * @return the calculated departure time.
     */
    double leavingTime(Car car , double currentTime){
        double timePassed;
        switch (car.getType()) {
            case SlowMachine:
                timePassed = this.slowMachineStream.next();
                break;
            case FastMachine:
                timePassed = this.fastMachineStream.next();
                break;
            default:
                return currentTime; //Should never happen
        }
        car.setTimeQueued(calculateQueueTime(car, timePassed));
        return currentTime + timePassed;
    }

    /**
     * Calculates the time available machines has spent idle since last event
     * @param currentTime the time of the last event
     * @param newTime     the time of this event
     * @param availableMachines number of available machines
     * @return the time machines idled since last event was invoked
     */
    double calculateIdleTime(double currentTime, double newTime, int availableMachines){
        isValidTime(currentTime, newTime);
        return (newTime - currentTime)*availableMachines;
    }

    /**
     * Controls that time has not moved in reverse
     * @param currentTime the time of the last event
     * @param newTime     the time of this event
     * @return true if the time is valid else throws an IllegalArgumentException
     * @throws IllegalArgumentException if newTime is lower than oldTime
     */
    boolean isValidTime(double currentTime, double newTime) throws IllegalArgumentException {
        if (newTime < currentTime) {
            throw new IllegalArgumentException("Time cannot move backwards");
        }
        return true;
    }

     

     //Calculates the time the car spent in the CarQueue
     private double calculateQueueTime(Car car, double timePassed){
        return car.leftSystemAtTime() - (car.enteredSystemAtTime() +  timePassed);
     }

}
