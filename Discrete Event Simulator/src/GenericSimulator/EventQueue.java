package GenericSimulator;

import java.util.PriorityQueue;

/**
 * @author Niklas Lehtola
 * A PriorityQueue that takes Events and sorts them based on the occurenceTime of said Events
 */
public class EventQueue extends PriorityQueue<Event>  {  
    /**
     * Constructor that takes a starting event to kick off the simulation
     * @param startEvent The event that kicks off the simulation
     */
    public EventQueue(SimState state, Event startEvent){
        super(new SortedEvents()); //Creates a PriorityQueue with our custom comparator that sorts according to occurenceTime
        if (startEvent == null) {
            throw new IllegalArgumentException("StartEvent may not be null");
        }
        this.add(startEvent);
    }
}
