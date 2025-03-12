package GenericSimulator;

import java.util.PriorityQueue;

//TODO: 

public class EventQueue extends PriorityQueue {
    private SimState state;

    EventQueue(SimState state){
        this.state = state;
    }

    @Override
    public boolean add(Event e) {
        this.comparator()
        return super.add(e);
    }

}
