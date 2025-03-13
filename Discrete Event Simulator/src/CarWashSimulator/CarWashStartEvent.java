package CarWashSimulator;

import GenericSimulator.Event;
import GenericSimulator.SimState;

//TODO: 

public class CarWashStartEvent extends Event {

    public CarWashStartEvent(double time, SimState state) throws IllegalArgumentException {
            super(time, state);
        }
    
        @Override
    public Event[] invoke() {
        return null;
    }

    

}
