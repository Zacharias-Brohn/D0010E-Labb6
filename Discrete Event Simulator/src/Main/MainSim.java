package Main;

import GenericSimulator.EventQueue;
import GenericSimulator.Simulator;
import CarWashSimulator.CarWashStartEvent;
import CarWashSimulator.CarWashState;
import CarWashSimulator.ExponentialRandomStream;
import CarWashSimulator.UniformRandomStream;
import java.lang.Thread;

//TODO: 

public class MainSim {
    long seed = 1234;
    ExponentialRandomStream ex = new ExponentialRandomStream(0, seed);
    UniformRandomStream un = new UniformRandomStream(1.0,2.0, seed);
    CarWashState s = new CarWashState(un, un, ex);
    CarWashStartEvent startEvent = new CarWashStartEvent(0, s);
    EventQueue e = new EventQueue(s, startEvent);
    Simulator simOne = new Simulator(s, e);
    simOne.start();

}
