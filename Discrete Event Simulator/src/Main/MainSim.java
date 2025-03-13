package Main;

import CarWashSimulator.CarWashStartEvent;
import CarWashSimulator.CarWashState;
import CarWashSimulator.CarWashView;
import GenericSimulator.EventQueue;
import GenericSimulator.Simulator;


public class MainSim {
    public static void main(String[] args) {
        final boolean USE_SEED = true;
        CarWashView view = new CarWashView(true);
        CarWashState defaultState = new CarWashState(USE_SEED);
        CarWashStartEvent startEvent = new CarWashStartEvent(defaultState);

        Simulator sim = new Simulator(defaultState, new EventQueue(defaultState, startEvent), view);
    }
    


}
