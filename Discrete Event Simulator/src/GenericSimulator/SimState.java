package GenericSimulator;

import java.util.Observable;

//TODO: 

public abstract class SimState extends Observable {
    private boolean stopSim = false;
    private int totalTime;

    public void forceStop() {
        this.stopSim = true;
    }
}
