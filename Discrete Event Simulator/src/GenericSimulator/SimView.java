package GenericSimulator;

import java.util.Observer;

//TODO: 

public abstract class SimView implements Observer {
    private SimState state;
    
    public void update(SimState state){
        this.state = state;


    }

    protected SimState state(){
        return this.state;
    }

    abstract void update();

    public static void main(String[] args) {
        
    }
}

