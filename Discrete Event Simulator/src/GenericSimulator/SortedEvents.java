package GenericSimulator;

import java.util.Comparator;
/**
 * Custom Comparator that sorts Events according to occurenceTime
 */
class SortedEvents implements Comparator<Event> {

    /**
     * Compares the occurenceTime of Events and sorts least first.
     */
    @Override
    public int compare(Event e1, Event e2) {
        return Double.compare(e1.occurenceTime(), e2.occurenceTime());
    }  
}
