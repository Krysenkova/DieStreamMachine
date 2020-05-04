package streamMachine;

import java.io.*;


public class SensorDataStorageImpl implements SensorDataStorage {
    private final String sensorName;
    private final String sensorFileName;
    private int size = 0;

    public SensorDataStorageImpl(String sensorName) {
        this.sensorName = sensorName;
        this.sensorFileName = this.sensorName + ".txt";
        try {
            this.readMetaData();
        } catch (IOException e) {
            //ignore
        }
    }

    private String getMetaFileName() {
        return this.sensorFileName + "_meta.txt";
    }

    private void saveMetaData() throws IOException {
        String metaDataFileName = this.getMetaFileName();
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(metaDataFileName));

        dos.writeUTF(this.sensorName);
        dos.writeInt(this.size);
    }

    private void readMetaData() throws IOException {
        String metaDataFileName = this.getMetaFileName();
        DataInputStream dis = new DataInputStream(new FileInputStream(metaDataFileName));

        dis.readUTF();
        this.size = dis.readInt();

    }

    @Override
    public void saveData(long time, float[] values) throws PersistenceException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(this.sensorFileName, true);
        } catch (FileNotFoundException e) {
            throw new PersistenceException((e.getLocalizedMessage()));
        }
        DataOutputStream dos = new DataOutputStream(os);
        try {

            dos.writeLong(time);
            dos.writeInt(values.length);
            for (int i = 0; i < values.length; i++) {
                dos.writeFloat(values[i]);
            }
            dos.close();
            this.size++;
            this.saveMetaData();
        } catch (IOException e) {
            throw new PersistenceException(e.getLocalizedMessage());
        }

    }

    @Override
    public int size() {
        return this.size;
    }

    private String getFileName() {
        return this.sensorName + ".txt";
    }

    @Override
    public void clear() {
        File file = new File(this.getMetaFileName());
        file.delete();

        file = new File(this.getFileName());
        file.delete();

        this.size = 0;
        this.sensorData = null;
    }

    private SensorDataRecord[] sensorData = null;

    private void readFromFile() throws IOException {
        this.sensorData = new SensorDataRecord[this.size];
        //open file
        InputStream is = new FileInputStream(this.sensorFileName);
        DataInputStream dis = new DataInputStream(is);

        for (int i = 0; i < this.size; i++) {
            //read data set
            long readTimeStamps = dis.readLong();

            int readValuesNumber = dis.readInt();
            float[] readValues = new float[readValuesNumber];
            for (int j = 0; j < readValuesNumber; j++) {
                readValues[j] = dis.readFloat();
            }

            // keep it in memory
            this.sensorData[i] = new SensorDataRecordImpl(readTimeStamps, readValues);
        }
    }


    @Override
    public SensorDataRecord getDataSet(int index) throws PersistenceException {
        if (index < 0 || index >= this.size) {
            throw new PersistenceException("index negative or to big");
        }
        if (this.sensorData == null) {
            try {
                this.readFromFile();
            } catch (IOException e) {
                throw new PersistenceException(e.getLocalizedMessage());
            }
        }
        return this.sensorData[index];
    }
}

