package GenericSimulator;

import java.util.Arrays;
import java.util.List;

//TODO: JavaDoc

/**
 * @author Niklas lehtola
 * Describes the generic simulator
 */
public class Simulator{
    private SimState state;
    private SimView view;
    private EventQueue eventQueue;

    /**
     * Constructor for the simukator
     * @param state the concrete state the simulator is to use
     * @param queue the eventqueue to use
     */
    public Simulator(SimState state, EventQueue queue){
        this.state = state;
        this.eventQueue = queue;
        run();
    }

    public Simulator(SimState state, EventQueue queue, SimView view){
        this(state, queue);
        this.view = view;
    }

    /**
     * The main loop of the simulator, it continues 
     * until a an internal flag has been tripped or it runs out of events
     */
    public void run() {
        //Continues to invoke events until flag is tripped or no more events
        while (continueSim()) {
            if (emptyQueue()) {
                return;
            }
            invokeNextEvent();
            //Moves current time forward to the time of the next event
            updateCurrentTime();
        }
    }

    private void invokeNextEvent(){
        //Removes the first Event, invokes its effect and saves any events it creates in a list
        List<Event> events = Arrays.asList(this.eventQueue.poll().invoke());

        

        //Returns to the loop if the invoked event didnt create any new events.
        if (events.isEmpty() || events == null) { 
            return;
        }

        //Add the new events to the queue
        for (Event event : events){
            this.eventQueue.add(event);
        }
        
    }


    //Checks if the queue is empty
    private boolean emptyQueue(){
        return this.eventQueue.isEmpty(); //Queue is empty if first Object is null
        
    }

    //Checks if the flag to stop is tripped.
    private boolean continueSim(){ 
        return !this.state.stopSim();
    }

    //Updates the current time to the time of the event invoked
    private void updateCurrentTime(){

        this.state.updateCurrentTime(eventQueue.peek().occurenceTime()); 
    }

}
