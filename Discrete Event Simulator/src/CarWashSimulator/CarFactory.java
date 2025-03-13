package CarWashSimulator;


/**
 * @author Niklas lehtola
 * Describes the CarFactory that creates cars
 */
class CarFactory {
    private int nextID = 0;

    /**
     * Empty Constructor
     */
    CarFactory(){

    }

    /**
     * Creates a car from the internal ID buffer and then increments the ID
     * @param enteredAtTime the time the car entered the system
     * @return the created Car
     */
    public Car createCar(double enteredAtTime){
        Car newCar = new Car(getNextID(), enteredAtTime);
        incrementID();
        return newCar;
    }

     private int getNextID(){
        return this.nextID;
    }

    private void incrementID(){
        this.nextID += 1;
    }

    
}



