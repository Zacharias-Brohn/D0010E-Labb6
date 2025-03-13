package CarWashSimulator;


/**
 * @author Niklas lehtola
 * Desribes the behaviour of a Car in the CarWashSimulator
 */
class Car {
    private int id;
    private MachineType type;
    private double enteredSystemAtTime;
    private double leftSystemAtTime;
    private double timeQueued;
    

    /**
     * Constructor that creates a Car with its ID and the time it entered the system
     * @param id    the car's ID
     * @param enteredSystemAtTime   the time the car entered the system
     */
    Car(int id, double enteredSystemAtTime){
        this.id = id;
        this.enteredSystemAtTime = enteredSystemAtTime;
    }

    /**
     * Returns the ID of the Car
     * @return the ID of the Car
     */
    int getID(){
        return this.id;
    }

     /**
     * Returns the time the Car entered the system.
     * @return the time the Car entered the CarWashSystem.
     */
    double enteredSystemAtTime(){
        return this.enteredSystemAtTime;
    }

    /**
     * Returns the time the Car left the system or -1 if it is still in the system
     * @return the time the Car left the system or -1 if it is still in the system
     */
    double leftSystemAtTime(){
        return this.leftSystemAtTime;
    }

    /**
     * Returns the carwasher type the car entered on being serviced
     * @return the carwasher type the car entered on being serviced
     */
    MachineType getType(){
        return this.type;
    }

    /**
     * Sets the carwasher type to the type the Car entered on being serviced.
     * @param type type of carwasher, either slow or fast.
     */
    void setType(MachineType type){
        this.type = type;
    }

    double getTimeQueued(){
        return this.timeQueued;
    }

    public void setTimeQueued(double timeQueued) {
        this.timeQueued = timeQueued;
    }

    

   


}
