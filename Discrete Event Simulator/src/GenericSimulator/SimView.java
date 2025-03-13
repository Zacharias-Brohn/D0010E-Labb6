package GenericSimulator;

import java.util.Observer;

//TODO: 

/**
 * @author Niklas Lehtola
 * An abstract class for the simView that 
 */
public abstract class SimView implements Observer {
    private SimState state;
    
    /**
     * Returns the current state of the simulation
     * @return the current state of the simulation
     */
    protected SimState currentState(){
        return this.state;
    }
}

