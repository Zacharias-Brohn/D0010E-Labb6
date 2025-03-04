package GenericSimulator;

//TODO: 

public class Simulator implements Runnable {
    private SimState state;
    private SimView view;
    private EventQueue eventQueue;

    Simulator(SimState state, EventQueue queue){
        this.state = state;
        this.eventQueue = queue;

    }

    void invokeNextEvent(){

    }



    public void run(){
        
    }
}
