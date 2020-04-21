package filepersistence.model;

import java.util.Arrays;

public class DataRecord {
    private long timeStamp;
    private float[] readValues;

    public DataRecord(long timeStamp, float[] readValues) {
        this.timeStamp = timeStamp;
        this.readValues = readValues;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public float[] getReadValues() {
        return readValues;
    }

    @Override
    public String toString() {
        return "DataRecord{" +
                "timeStamp=" + timeStamp +
                ", readValues=" + Arrays.toString(readValues) +
                '}';
    }
}