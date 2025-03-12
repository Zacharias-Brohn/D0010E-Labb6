package CarWashSimulator;

import random.ExponentialRandomStream;
import random.UniformRandomStream;

//TODO: 

class CarWashState {
    private CarFactory carFactory = new CarFactory();
    private CarQueue carQueue = new CarQueue();
    private UniformRandomStream slowMachineStream;
    private UniformRandomStream fastMachineStream;
    private ExponentialRandomStream carStream;


    CarWashState(UniformRandomStream slow, UniformRandomStream fast, ExponentialRandomStream cars){

    }
}
