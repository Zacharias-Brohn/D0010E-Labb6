package CarWashSimulator;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * @author Niklas lehtola
 * A FIFO-queue implementation for Cars in a CarWashSimulator
 */
class CarQueue{
    private ArrayList<Car> carQueue = new ArrayList<>();
    private int maxQueueSize;

    // -----------------------------------------------------
    //  Constructors
    // -----------------------------------------------------

    /**
     * Constructor for the CarQeueue that takes its maxSize as a parameter
     * @param maxQueueSize the maxSize of the queue
     */
    CarQueue(int maxQueueSize){
        this.maxQueueSize = maxQueueSize;
    }

    // -----------------------------------------------------
    //  Query Methods
    // -----------------------------------------------------

    /**
     * Returns true if the queue is empty
     * @return true if the queue is empty
     */
    boolean isEmpty(){
        return this.carQueue.isEmpty();
    }    

    /**
     * Returns true if the queue is full
     * @return true if the queue is full
     */
    boolean isFull(){
        return carQueue.size() == this.maxQueueSize;
    }

    /**
     * Returns the current size of the queue
     * @return the current size of the queue
     */
    int size(){
        return this.carQueue.size();
    }

    /**
     * Returns the first Car in the queue but doesn't remove it
     * @return the first Car in the queue but doesn't remove it
     */
    Car peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return this.carQueue.get(0);
    }

    // -----------------------------------------------------
    //  Update Methods
    // -----------------------------------------------------

    /**
     * Adds a car to the back of the queue
     * 
     * @param car the car to add to the back of the queue
     * @return true if a car was successfully added to the queue, else false.
     */
    boolean enqueue(Car car) throws IllegalArgumentException {
        if (car == null) {
            throw new IllegalArgumentException("Element may not be null!");
        }
        //Returns false if queue already at max size.
        if (size() >= this.maxQueueSize) {
            return false;
        }
        //Adds the Car to the queue and returns true;
        this.carQueue.add(car);
        return true;
    }

    /**
     * Removes the first car of the queue and returns it.
     * @return the first Car in the queue after removing it from the queue.
     */
    Car dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        Car firstCar = peek();
        this.carQueue.remove(0);
        return firstCar;
    }

    

}
