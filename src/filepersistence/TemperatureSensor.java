package filepersistence;

import filepersistence.model.DataRecord;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TemperatureSensor implements SensorDataStorage {

    private int size;
    private String sensorFileName;

    public TemperatureSensor(String sensorFileName) {
        this.sensorFileName = sensorFileName; // TODO randomize it if there is a lot of sensors
        this.size = 0;
    }

    @Override
    public void saveData(DataRecord dataRecord) throws FileNotFoundException, IOException {
        long time = dataRecord.getTimeStamp();
        float[] values = dataRecord.getReadValues();

        OutputStream os = new FileOutputStream(sensorFileName);
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeLong(time);
        size++;

        dos.writeInt(values.length);
        for (int i = 0; i < values.length; i++) {
            dos.writeFloat(values[i]);
        }

        os.close();
        dos.close();
    }

    @Override
    public DataRecord readAndPrintDataRecord() throws IOException {

        long readTimeStamps;
        float[] readValues;

        InputStream is = new FileInputStream(sensorFileName);
        DataInputStream dis = new DataInputStream(is);

        readTimeStamps = dis.readLong();
        int readValuesNumber = dis.readInt();
        readValues = new float[readValuesNumber];
        for (int i = 0; i < readValuesNumber; i++) {
            readValues[i] = dis.readFloat();;
        }

        is.close();
        dis.close();

        var dataRecord = new DataRecord(readTimeStamps, readValues);

        print(readTimeStamps, readValues);

        return dataRecord;
    }

    private void print(long readTimeStamps, float[] readValues) {
        System.out.println("Time: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(new Date(readTimeStamps)));
        for (int j = 0; j < readValues.length; j++) {
            System.out.println("Value " + (j + 1) + ": " + readValues[j]);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean clear() {
        // clear the file
        File file = new File(sensorFileName);

        boolean isDeleted = file.delete();
        if (isDeleted) size = 0;
        return isDeleted;
    }

}