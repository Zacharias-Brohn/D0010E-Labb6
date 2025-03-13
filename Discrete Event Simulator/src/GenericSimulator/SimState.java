package GenericSimulator;

import java.util.Observable;

//TODO: 

public abstract class SimState extends Observable {
    private boolean stopSim = false;
    private double currentTime;

    protected void forceStop() {
        this.stopSim = true;
    }

    boolean stopSim(){
        return this.stopSim;
    }

    void setCurrentTime(double time){
        this.currentTime = time;
    }

    double getCurrentTime() {
        return currentTime;
    }

    public static void main(String[] args) {
        
    }
}
