package streamMachine;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException, PersistenceException {
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

        StreamMachine machine = new StreamMachineFS("TemperatureSensor1") {
        };
        for (int i = 0; i < timeStampsNumber; i++) {
            machine.saveData(timeStamps[i], values[i]);

            InputStream is = new FileInputStream("TemperatureSensor1");
            DataInputStream dis = new DataInputStream(is);

            long readTimeStamps = dis.readLong();
            System.out.println("Time: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(new Date(readTimeStamps)));
            int readValuesNumber = dis.readInt();
            float[] readValues = new float[readValuesNumber];
            for (int j = 0; j < readValuesNumber; j++) {
                readValues[j] = dis.readFloat();
                System.out.println("Value " + (j + 1) + ": " + readValues[j]);
            }
            dis.close();

        }

    }
}

