package streamMachine;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteAndReadDataSet {

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

        DataOutputStream dos = null;
        try {
            OutputStream os = new FileOutputStream(sensorName);
            dos = new DataOutputStream(os);
            dos.writeUTF(sensorName);

            int timeStampsNumber = timeStamps.length;
            dos.writeInt(timeStampsNumber);

            for (int i = 0; i < timeStampsNumber; i++) {
                dos.writeLong(timeStamps[i]);

                int valuesNumber = values[i].length;
                dos.writeInt(valuesNumber);
                for (int j = 0; j < valuesNumber; j++) {
                    dos.writeFloat(values[i][j]);
                }
            }
        } catch (IOException ex) {
            System.err.println("Couldn’t write data (fatal)");
            System.exit(0);
        }

        dos.close();


        DataInputStream dis = null;
        String readSensorName = "";
        long[] readTimeStamps = null;
        float[][] readValues = null;

        try {
            InputStream is = new FileInputStream(sensorName);
            dis = new DataInputStream(is);
            readSensorName = dis.readUTF();

            int readTimeStampNumber = dis.readInt();
            readTimeStamps = new long[readTimeStampNumber];
            readValues = new float[readTimeStampNumber][];
            for (int i = 0; i < readTimeStampNumber; i++) {
                readTimeStamps[i] = dis.readLong();
                int readValuesNumber = dis.readInt();
                float[] setOfValues = new float[readValuesNumber];
                for (int j = 0; j < readValuesNumber; j++) {
                    setOfValues[j] = dis.readFloat();
                }
                readValues[i] = setOfValues;
            }
        } catch (IOException ex) {
            System.err.println("couldn’t read data (fatal)");
            System.exit(0);
        }

        dis.close();

        System.out.println("Sensor Name: " + readSensorName);
        for (int i = 0; i < readTimeStamps.length; i++) {
            System.out.println("Time: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(new Date(readTimeStamps[i])));
            for (int j = 0; j < readValues[i].length; j++) {
                System.out.println("Value " + (j + 1) + ": " + readValues[i][j]);
            }
        }
    }
}


