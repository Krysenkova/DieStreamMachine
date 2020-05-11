package sensorData;

import streamMachine.PersistenceException;
import streamMachine.SensorDataStorage;

import java.io.DataInputStream;
import java.io.IOException;

class SensorDataSetReader extends Thread {
    private final DataInputStream dis;
    private final SensorDataStorage storage;

    SensorDataSetReader(DataInputStream dis, SensorDataStorage storage) {
        this.dis = dis;
        this.storage = storage;
    }

    public void run() {
        try {
            String name = this.dis.readUTF();
            long time = this.dis.readLong();
            int len = this.dis.readInt();
            float[] values = new float[len];
            for(int i = 0; i < len; i++) {
                values[i] = this.dis.readFloat();
            }
            this.storage.saveData(time, values);
        } catch (IOException | PersistenceException e) {
            System.err.println("Problems when reading / writing sensor data");
        }
    }
}
