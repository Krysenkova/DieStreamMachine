package sensorData;

import streamMachine.PersistenceException;
import streamMachine.SensorDataStorage;
import transmission.DataConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class SensorDataReceiver implements Runnable{
    private final DataConnection connection;
    private final SensorDataStorage storage;
    private long[] timeStamps;
    private float[][] values;


    public SensorDataReceiver(DataConnection connection, SensorDataStorage storage) {
        this.connection = connection;
        this.storage = storage;
    }

    SensorDataStorage getStorage() {
        return storage;
    }

    public void getData() throws IOException, PersistenceException {
        DataInputStream dis = this.connection.getDataInputStream();
        String name = dis.readUTF();
        long time = dis.readLong();
        int length = dis.readInt();
        float[] values = new float[length];
        for (int i = 0; i < length; i++) {
            values[i] = dis.readFloat();
        }
        this.storage.saveData(time, values);
    }
    public void getAndWriteData(){
        Thread receiverThread = new Thread(this);
        receiverThread.start();
    }


    @Override
    public void run() {
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}
