package streamMachine;

public class SensorDataRecordImpl implements SensorDataRecord {
    private final long readTimeStamps;
    private final float[] readValues;

    public SensorDataRecordImpl(long readTimeStamps, float[] readValues) {
        this.readTimeStamps = readTimeStamps;
        this.readValues = readValues;
    }

    @Override
    public long getTime() {
        return this.readTimeStamps;
    }

    @Override
    public float[] getValues() {
        return this.readValues;
    }
}
