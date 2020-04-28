package sensorData;

import streamMachine.SensorDataStorage;
import transmission.DataConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class SensorDataReceiver {
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

    public void getData() throws IOException {
        DataInputStream dis = this.connection.getDataInputStream();
        int timeStampsSize = dis.readInt();
        this.timeStamps = new long[timeStampsSize];
        this.values = new float[timeStampsSize][];
        for (int i = 0; i < timeStampsSize; i++) {
            this.timeStamps[i] = dis.readLong();
            int valueSize = dis.readInt();
            this.values[i] = new float[valueSize];
            for (int j = 0; j < valueSize; j++) {
                this.values[i][j] = dis.readFloat();
            }
        }
        dis.close();
    }
}
