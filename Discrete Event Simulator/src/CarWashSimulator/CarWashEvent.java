package CarWashSimulator;
import GenericSimulator.Event;

//TODO: 

class CarWashEvent extends Event {

    CarWashEvent(double occurenceTime, CarWashState state) throws IllegalArgumentException {
        super(occurenceTime, state);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Event[] invoke() {
        return null;
    }


}
