package GenericSimulator;

import java.util.Arrays;
import java.util.List;

//TODO: JavaDoc

public class Simulator extends Thread {
    private SimState state;
    private SimView view;
    private EventQueue eventQueue;

    public Simulator(SimState state, EventQueue queue){
        this.state = state;
        this.eventQueue = queue;
    }

    @Override
    public void run(){
        this.state.addObserver(this.view); //Adds SimView as an observer of SimState

        //Continues to invoke events until flag is tripped or no more events
        while (continueSim()) {
            if (emptyQueue()) {
                return;
            }
            invokeNextEvent();
        }
    }


    private void invokeNextEvent(){
        //Moves current time forward to the time of this event
        updateCurrentTime();

        //Removes the first Event and invokes its effect and saves any events it creates
        List<Event> events = Arrays.asList(this.eventQueue.poll().invoke());

        //Return if the last event didnt create any new events.
        if (events.isEmpty()) { 
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
        this.state.setCurrentTime(eventQueue.peek().occurenceTime()); 
    }

    public static void main(String[] args) {
        
    }

}
