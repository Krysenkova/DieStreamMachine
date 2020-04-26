package streamMachine;

public interface SensorDataRecord {
    /**
     *
     * @return time of value creation
     */
    long getTime();

    /**
     *
     * @return actual values
     */
    float[] getValues();
}
