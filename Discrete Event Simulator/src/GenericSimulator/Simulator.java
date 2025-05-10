package GenericSimulator;

/**
 * @author Niklas lehtola
 * Describes the generic simulator
 */
public class Simulator{
    private SimState state;
    @SuppressWarnings("unused")
    private SimView view;
    private EventQueue eventQueue;

    /**
     * Constructor for the simulator. It sets the SimState, EventQueue and SimView (If there is one)
     * @param state the concrete state the simulator is to use
     * @param queue the eventqueue to use
     */
    public Simulator(SimState state, EventQueue queue) throws IllegalArgumentException{
        if (state == null || queue == null) {
            throw new IllegalArgumentException("Neither state nor queue may be Null");
        }
        //The state passed into the Simulator has to be the same state as passed into the eventqueue start event.
        if (!state.equals(queue.peek().currentState())) {
            throw new IllegalArgumentException("State mismatch between state and queue");
        }
        this.state = state;
        this.eventQueue = queue;
        this.view = state.createView();
    }

    /**
     * The main loop of the simulator, it continues until a a stop event 
     * is invoked, internal flag has been tripped or it runs out of events
     */
    public void run() {
        //Continues to invoke events until a stop event, stop flag is tripped or no more events
        while (state.continueSim()) {
            if (emptyQueue()) {
                state.forceStop();
            }
            invokeNextEvent();
        }
    }

    //Triggers the effect of the invoked event
    private void invokeNextEvent(){
        //Removes the head event and invokes it. It returns a list of events to add to the queue
        Event[] events = eventQueue.poll().invoke();

        //Returns to the loop if the invoked event didnt create any new events.
        if (events == null) { 
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

}
