package CarWashSimulator;


/**
 * @author Niklas lehtola
 * Desribes the behaviour of a Car in the CarWashSimulator
 */
class Car {
    private int id;
    private MachineType machineType = null;    

    /**
     * Constructor that creates a Car with its ID
     * @param id    the car's ID
     */
    Car(int id){
        this.id = id;
    }

    /**
     * Returns the ID of the Car
     * @return the ID of the Car
     */
    int getID(){
        return this.id;
    }

    /**
     * Returns the carwasher type the car entered on being serviced
     * @return the carwasher type the car entered on being serviced
     */
    MachineType getType(){
        return machineType;
    }

    /**
     * Sets the carwasher type to the type the Car entered on being serviced.
     * @param type Type of carwasher, either slowMachine or fastMachine.
     * @throws IllegalStateException when trying to set the type when it has already been set before
     */
    void setType(MachineType type) throws IllegalStateException{
        if (machineType != null) {
            throw new IllegalStateException("Cannot set the type twice.");
        }
        machineType = type;
    }


}
