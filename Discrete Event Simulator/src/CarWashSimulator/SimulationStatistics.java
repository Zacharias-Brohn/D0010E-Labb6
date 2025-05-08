package CarWashSimulator;

/**
 * @author Niklas lehtola
 * An enumerator solely to make it easier to index the statistic array
 */
public enum SimulationStatistics { 
    Seed(0), UseSeed(1), Lambda(2), 
    SlowLowerBound(3), SlowUpperBound(4),  
    FastLowerBound(5), FastUpperBound(6);

    private int index;
    /**
     * Constructor for the different values
     * @param index the index to assign the enumerator to
     */
    SimulationStatistics(int index) {
        this.index = index;
    }

    /**
     * Returns the index of the chosen value
     * @return the index of the chosen value
     */
    int index() {
        return index;
    }
}
