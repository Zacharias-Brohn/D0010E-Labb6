package GenericSimulator;

import java.util.Observable;

//TODO: 

public abstract class SimState extends Observable {
    private boolean stopSim = false;
    private int currentTime;

    void forceStop() {
        this.stopSim = true;
    }

    boolean stopSim(){
        return this.stopSim;
    }
}
