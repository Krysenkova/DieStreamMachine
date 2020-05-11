package sensorData;

import streamMachine.PersistenceException;
import streamMachine.SensorDataRecord;
import streamMachine.SensorDataStorage;
import org.junit.Assert;
import org.junit.Test;
import streamMachine.SensorDataStorageImpl;
import transmission.DataConnection;
import transmission.DataConnector;

import java.io.IOException;

public class SensorDataTransmissionTest {
    private static final int PORTNUMBER = 9876;

    @Test
    public void gutTransmissionTest() throws IOException, InterruptedException, PersistenceException {
        // create example data set
        String sensorName = "MyGoodOldSensor"; // does not change
        long timeStamp = System.currentTimeMillis();
        float[] valueSet = new float[3];
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;
        valueSet[2] = (float) 2.1;
        //receiver side
        // create storage
        SensorDataStorage dataStorage = new SensorDataStorageImpl(sensorName);
        dataStorage.clear();
        // create connections
        DataConnection receiverConnection = new DataConnector(PORTNUMBER);
        DataConnection senderConnection = new DataConnector("localhost", PORTNUMBER);
        // create receiver
        SensorDataReceiver sensorDataReceiver = new SensorDataReceiver(receiverConnection, dataStorage);

        // create sender
        SensorDataSender sensorDataSender = new SensorDataSender(senderConnection);

        //execute communication and test
        // send data with TCP
        sensorDataSender.sendData(sensorName, timeStamp, valueSet);

        Thread.sleep(1);

        SensorDataStorage dataStorageReceived = sensorDataReceiver.getStorage();
        SensorDataRecord dataRecord = dataStorageReceived.getDataSet(0);
        long timeStampReceived = dataRecord.getTime();
        float[] valueSetReceived = dataRecord.getValues();


        // test
        Assert.assertEquals(timeStamp, timeStampReceived);
        Assert.assertArrayEquals(valueSet, valueSetReceived, 0);
    }
}