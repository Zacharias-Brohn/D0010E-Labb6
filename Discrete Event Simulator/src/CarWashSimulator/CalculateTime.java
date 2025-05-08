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
            this.carStream         = new ExponentialRandomStream(lambda, seed);
            this.slowMachineStream = new UniformRandomStream(slowLowerBound, slowUpperBound, seed);
            this.fastMachineStream = new UniformRandomStream(fastLowerBound, fastUpperBound, seed);

        } else {        //Non-structured "randoms"
            this.carStream         = new ExponentialRandomStream(lambda);
            this.slowMachineStream = new UniformRandomStream(slowLowerBound, slowUpperBound);
            this.fastMachineStream = new UniformRandomStream(fastLowerBound, fastUpperBound); 
        }
    }

    /**
     * Calculates the arrival of the next car.
     * @return arrival time of the next car.
     */
    double nextCarArrival(double currentTime){
        return currentTime + carStream.next();
    }

    /**
     * Calculates when the Car will leave the carwasher it just entered\n
     * @param car the car to calculate for
     * @param currentTime The current time
     */
    double carLeavingTime(Car car, double currentTime) throws IllegalArgumentException{
        double timePassed, leavingTime;
        //Calculates the time passed in the carwash based on the type of machine it entered
        switch (car.getType()) {
            case SlowMachine:
                timePassed = slowMachineStream.next();
                break;
            case FastMachine:
                timePassed = fastMachineStream.next();
                break;
            default:
                timePassed = 0; //Should never happen
        }
        //Calculate and assign the time the car leaves
        leavingTime = currentTime + timePassed;
        return leavingTime;
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
     * Calculates the time cars has spent in queue since last event
     * @param currentTime the time of the last event
     * @param newTime     the time of this event
     * @param queueLength number of cars in queue
     * @return the time cars spent in queue since last invoked event
     */
    double calculateQueueTime(double currentTime, double newTime, int queueLength) {
        isValidTime(currentTime, newTime);
        return (newTime - currentTime)*queueLength;
    }


    /**
     * Controls that time has not moved in reverse
     * @param oldTime the previous time 
     * @param newTime the new time
     * @return true if the time is valid else throws an IllegalArgumentException
     * @throws IllegalArgumentException if newTime is lower than oldTime
     */
    boolean isValidTime(double oldTime, double newTime) throws IllegalArgumentException {
        if (newTime < oldTime) {
            throw new IllegalArgumentException("Time cannot move backwards");
        }
        return true;
    }
}
