package CarWashSimulator;

import java.util.PriorityQueue;

//TODO: 

class CarQueue extends PriorityQueue {

    CarQueue(){

    }

    @Override
    public boolean add(Car e) {
        e.setID(0);
        return super.add(e);
    }
}
