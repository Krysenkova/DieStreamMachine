package sensorData;

import streamMachine.SensorDataStorage;
import transmission.DataConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class SensorDataReceiver {
   // private final DataConnection connection;
   private final SensorDataStorage storage;

    public SensorDataReceiver(DataConnection connection, SensorDataStorage storage) throws IOException {
       // this.connection = connection;
        this.storage = storage;

        SensorDataSetReader reader = new SensorDataSetReader(new DataInputStream(connection.getDataInputStream()), storage);
        reader.start();
    }

    SensorDataStorage getStorage() {
        return storage;
    }

}
