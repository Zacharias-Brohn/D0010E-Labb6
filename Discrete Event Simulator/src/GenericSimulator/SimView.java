package GenericSimulator;

import java.util.Observer;

/**
 * @author Niklas Lehtola
 * An abstract class for the simView that subclasses may decide to implement
 */
public abstract class SimView implements Observer {
    protected boolean shouldPrint;
}

