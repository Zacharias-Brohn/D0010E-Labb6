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
     * @return the created Car
     */
    Car createCar(){
        Car newCar = new Car(nextID);
        nextID += 1;
        return newCar;
    }  
}



