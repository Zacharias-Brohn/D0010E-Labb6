package Main;

import CarWashSimulator.StartEvent;
import CarWashSimulator.CarWashState;
import GenericSimulator.EventQueue;
import GenericSimulator.Simulator;

/**
 * @author Niklas Lehtola
 * The main program
 */
public class MainSim {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Simulator sim1 = config1(); //Settings from instruction
        Simulator sim2 = config2(); //Other settings without seed
        Simulator sim3 = config3(); //Wont print
        sim1.run();
        System.out.println("\n\n");
        //sim2.run();
        System.out.println("\n\n");
        //sim3.run();
        //CarWashState_test();
    }

    public static Simulator config1(){
        boolean useSeed = true;
        boolean print = true;
        double startTime = 0;
        double stopTime = 15;
        int maxQ = 5;
        int fastM = 2;
        int slowM = 2;
        double slowLB = 3.5;
        double slowUB = 6.7;
        double fastLB = 2.8;
        double fastUB = 4.6;
        long seed = 1234;
        double lambda = 2.0;
        CarWashState state = new CarWashState(slowM, fastM, maxQ, print);
        StartEvent start = new StartEvent(startTime, stopTime, state, fastLB, fastUB, slowLB, slowUB, seed, useSeed, lambda);
        EventQueue queue = new EventQueue(state, start);
        return new Simulator(state, queue);
    }
    
    public static Simulator config2(){
        boolean useSeed = false;
        boolean print = true;
        double startTime = 5;
        double stopTime = 40;
        int maxQ = 8;
        int fastM = 3;
        int slowM = 1;
        double slowLB = 4.5;
        double slowUB = 7.7;
        double fastLB = 1.8;
        double fastUB = 2.6;
        long seed = 12345;
        double lambda = 3.0;
        CarWashState state = new CarWashState(slowM, fastM, maxQ, print);
        StartEvent start = new StartEvent(startTime, stopTime, state, fastLB, fastUB, slowLB, slowUB, seed, useSeed, lambda);
        EventQueue queue = new EventQueue(state, start);
        return new Simulator(state, queue);
    }

    public static Simulator config3(){
        boolean useSeed = false;
        boolean print = false;
        double startTime = 5;
        double stopTime = 40;
        int maxQ = 8;
        int fastM = 3;
        int slowM = 1;
        double slowLB = 4.5;
        double slowUB = 7.7;
        double fastLB = 1.8;
        double fastUB = 2.6;
        long seed = 12345;
        double lambda = 3.0;
        CarWashState state = new CarWashState(slowM, fastM, maxQ, print);
        StartEvent start = new StartEvent(startTime, stopTime, state, fastLB, fastUB, slowLB, slowUB, seed, useSeed, lambda);
        EventQueue queue = new EventQueue(state, start);
        return new Simulator(state, queue);
    }
    
    @SuppressWarnings("unused")
    public static void CarWashState_test() {
        int maxQ = -1;
        int fastM = 3;
        int slowM = 1;
        boolean print = false;
        CarWashState state;
        try {
            state = new CarWashState(slowM, fastM, maxQ, print); //Should give error on maxQ
        } catch (IllegalArgumentException e){
            System.out.println(e);
        }

        maxQ = 1;
        fastM = -1;

        try {
            state = new CarWashState(slowM, fastM, maxQ, print); //Should give error on fastM
        } catch (IllegalArgumentException e){
            System.out.println(e);
        }

        fastM = 1;
        slowM = -1;

        try {
            state = new CarWashState(slowM, fastM, maxQ, print); //Should give error on slowM
        } catch (IllegalArgumentException e){
            System.out.println(e);
        }

        fastM = 0;
        slowM = 0;

        try {
            state = new CarWashState(slowM, fastM, maxQ, print); //Should give error on slowM + fastM <= 0
        } catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

}
