package CarWashSimulator;

import GenericSimulator.SimState;

//TODO: 

public class CarWashState extends SimState {
    private CarFactory carFactory = new CarFactory();
    private CarQueue carQueue = new CarQueue();
    private UniformRandomStream slowMachineStream;
    private UniformRandomStream fastMachineStream;
    private ExponentialRandomStream carStream;


    public CarWashState(UniformRandomStream slow, UniformRandomStream fast, ExponentialRandomStream cars){
        this.slowMachineStream = slow;
        this.fastMachineStream = fast;
        this.carStream = cars;
    }
}
