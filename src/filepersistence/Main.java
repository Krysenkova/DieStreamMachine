package filepersistence;
import filepersistence.model.DataRecord;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // three example data sets
        String sensorName = "MyGoodOldSensor"; // does not change

        long[] timeStamps = new long[3];
        timeStamps[0] = System.currentTimeMillis();
        timeStamps[1] = timeStamps[0] + 1; // milli sec later
        timeStamps[2] = timeStamps[1] + 1000; // second later

        float[][] values = new float[3][];
        // 1st measure .. just one value
        float[] valueSet = new float[1];
        values[0] = valueSet;
        valueSet[0] = (float) 1.5; // example value 1.5 degrees

        // 2nd measure .. just three values
        valueSet = new float[3];
        values[1] = valueSet;
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;
        valueSet[2] = (float) 2.1;

        // 3rd measure .. two values
        valueSet = new float[2];
        values[2] = valueSet;
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;

        int timeStampsNumber = timeStamps.length;

        SensorDataStorage ts = new TemperatureSensor("temperatureSensor.txt");
        for (int i = 0; i < timeStampsNumber; i++) {
            ts.saveData(new DataRecord(timeStamps[i], values[i]));
            ts.readAndPrintDataRecord();
        }
    }
}

