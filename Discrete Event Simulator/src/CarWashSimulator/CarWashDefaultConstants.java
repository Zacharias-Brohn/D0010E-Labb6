package CarWashSimulator;


/**
 * @author Niklas lehtola
 * Constants that are used i nthe default constructor of the CarWashState
 */
class CarWashDefaultConstants {

    //Constants that ease the creation of the default State of the CarWash

    //number of machines
    final public static int SLOW_MACHINES = 2;
    final public static int FAST_MACHINES = 2;
    //Queue size before the car just leaves
    final public static int MAX_QUEUE_SIZE = 5;

    //Constants about the creation of the RandomStreams
    final public static long DEFAULT_SEED = 1234;
    //SlowMachinesStream
    final public static double SLOW_LOWER_BOUND = 3.5;
    final public static double SLOW_UPPER_BOUND = 6.7;
    //FastMachineStream
    final public static double FAST_LOWER_BOUND = 2.8;
    final public static double FAST_UPPER_BOUND = 4.6;
    //CarStream
    final public static double DEFAULT_LAMBDA = 2.0;

    //StartEvent
    final public static double DEFAULT_START_TIME = 0;
    final public static double DEFAULT_STOP_TIME = 15.0;
    
}
