package GenericSimulator;

import java.util.Comparator;
/**
 * @author Niklas Lehtola
 * Custom Comparator that sorts Events according to the time the occur
 */
class SortedEvents implements Comparator<Event> {

    /**
     * Compares the occurenceTime of Events and sorts least first.
     * @param e1 The first event 
     * @param e2 The second event 
     */
    @Override
    public int compare(Event e1, Event e2) {
        return Double.compare(e1.occurenceTime(), e2.occurenceTime());
    }  
}
