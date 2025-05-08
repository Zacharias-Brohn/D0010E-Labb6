package CarWashSimulator;

import java.util.Observable;
import static CarWashSimulator.SimulationStatistics.*;
import GenericSimulator.SimView;

/**
 * @author Niklas lehtola
 *         The view of the CarWash
 */
public class CarWashView extends SimView {

    /**
     * Constructor for CarwashView
     * 
     * @param print whether to print the result or not
     */
    public CarWashView(boolean print) {
        super.shouldPrint = print;
    }

    /**
     * Updates the view when given the signal from the event being invoked
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!super.shouldPrint) {
            return;
        }
        CarWashEvent event = (CarWashEvent) arg;
        CarWashState state = event.currentState();
        String divLine = "-------------------------------------------------------------------";
        String ID = "";
        switch (event.toString()) {
            case "Start":
                // Prints out the starting parameters
                System.out.printf("Fast machines: %d\n", state.getFastMachines());
                System.out.printf("Slow machines: %d\n", state.getSlowMachines());
                System.out.printf("Fast distribution (%s, %s)\n",
                        event.statistics()[FastLowerBound.index()],
                        event.statistics()[FastUpperBound.index()]);
                System.out.printf("Slow distribution (%s, %s)\n",
                        event.statistics()[SlowLowerBound.index()],
                        event.statistics()[SlowUpperBound.index()]);
                System.out.printf("Exponential distribution with lambda = %s\n", event.statistics()[Lambda.index()]);
                if (event.statistics()[UseSeed.index()].equals("true")) {
                    System.out.printf("seed: %s\n", event.statistics()[Seed.index()]);
                }
                System.out.printf("Max Queue Size: %d\n", state.getMaxQueueSize());
                System.out.println(divLine);
                // Prints out the Column titles
                printFormattedLine("Time", "Event", "Id", "Fast", "Slow",
                        "IdleTime", "QueueTime", "QueueSize", "Rejected");
                // prints out the FIrst row of the result
                printFormattedLine(String.format("%.2f", state.getCurrentTime()),
                        event.toString(), "", "", "", "", "", "", "");
                break;

            // Prints out every row that is an arrival event
            case "Arrival":
                ArrivalEvent arr = (ArrivalEvent) event;
                Car car = arr.getCar();
                ID = String.valueOf(car.getID());
                printFormattedEventline(arr, ID);
                break;

            // Prints out every row that is a departure event
            case "Departure":
                DepartureEvent dep = (DepartureEvent) event;
                car = dep.getCar();
                ID = String.valueOf(car.getID());
                printFormattedEventline(dep, ID);
                break;

            // Prints out the last row
            case "Stop":
                printFormattedEventline(event, ID);
                System.out.println(divLine);

                // calculates the statistics for the simuation
                double qTime = state.getTotalTimeQueued();
                double mean = qTime / state.getAdmittedCars();

                // Prints out the statistics for the simulation
                System.out.printf("%-22s%6.2f\n", "Total idle machine time:", state.getTotalIdleTime());
                System.out.printf("%-23s%7.2f\n", "Total queueing time:", qTime);
                System.out.printf("%-23s%7.2f\n", "Mean queueing time:", mean);
                System.out.printf("%-26s%-7s\n", "Rejected Cars:", state.getRejectedCars());
                break;
            default:
                break;
        }

    }

    // -----------------------------------------
    // Helpter functions to print easier
    // -----------------------------------------

    private void printFormattedEventline(CarWashEvent event, String ID) {
        CarWashState state = event.currentState();
        printFormattedLine(String.format("%.2f", state.getCurrentTime()),
                event.toString(), ID,
                String.format("%d", state.getFastMachines()),
                String.format("%d", state.getSlowMachines()),
                String.format("%.2f", state.getTotalIdleTime()),
                String.format("%.2f", state.getTotalTimeQueued()),
                String.format("%d", state.carQueue().size()),
                String.format("%d", state.getRejectedCars()));
    }

    private static void printFormattedLine(String time, String event, String id,
            String fast, String slow, String iTime,
            String qTime, String qSize, String rejected) {
        System.out.printf("%10s\t%-10s%8s%8s%8s%12s%12s%12s%12s   \n",
                time, event, id, fast, slow, iTime, qTime, qSize, rejected);

    }
}
