package CarWashSimulator;

import java.util.Observable;

import GenericSimulator.SimView;

/**
 * @author Niklas lehtola
 * 
 */
public class CarWashView extends SimView{
    private boolean print;

    /**
     * Constructor of the view, 
     * @param print determines if there should be any output at all
     */
    public CarWashView(boolean print){
        this.print = print;
        
    }

    public void setObservable(CarWashState s){
        s.addObserver(this);
    }

    /**
     * Returns the current CarWashState
     * @return the current CarWashState
     */
    @Override
    protected CarWashState currentState() {
        return (CarWashState) super.currentState();
    }

    
    /**
     * 
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!shouldPrint()){
            return;
        }
        String eventType = arg.toString();
        String divider = "-------------------------------------------";
        CalculateTime c = currentState().calcTime();

        switch (eventType) {
            case "Start":
                System.out.printf("Fast machines: %d\nSlow machines: %d +\n", 
                                  currentState().getFastMachines(), currentState().getSlowMachines());
                System.out.printf("Fast distribution: (%.1f, %.1f)\nSlow distribution: (%.1f, %.1f)\n",
                                c.getFastLowerBound(), c.getFastUpperBound(), c.getSlowLowerBound(), c.getSlowUpperBound());
                System.out.printf("Exponential distribution with lambda = %.1f", c.getLambda());
                System.out.printf("seed = %s\n Max queue size: %d\n%s\n", c.getSeed(), currentState().getMaxQueueSize(), divider);
                break;
       
            default:
                break;
       }



    }

    private boolean shouldPrint(){
        return this.print;
    }
}
