package streamMachine;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SensorDataStorageTest {
    @Test
    public void gutTest1() throws PersistenceException, IOException {
        String sensorName1 = "sensor1";
        SensorDataStorage machine1 = new SensorDataStorageImpl(sensorName1);
        machine1.clear();

        String sensorName2 = "sensor2";
        SensorDataStorage machine2 = new SensorDataStorageImpl(sensorName2);
        machine2.clear();

        long time1 = System.currentTimeMillis();
        float[] valueSet1 = new float[3];
        valueSet1[0] = (float) 0.7;
        valueSet1[1] = (float) 1.2;
        valueSet1[2] = (float) 2.1;

        machine1.saveData(time1, valueSet1);

        long time2 = System.currentTimeMillis();
        float[] valueSet2 = new float[2];
        valueSet2[0] = (float) 2.3;
        valueSet2[1] = (float) 4.2;

        // write 1st data set
        machine2.saveData(time2, valueSet2);

        // create second set
        long time2_1 = time2 + 1; // one milli second later
        float[] valueSet2_1 = new float[3];
        valueSet2_1[0] = (float) 0.2;
        valueSet2_1[1] = (float) 8.2;
        valueSet2_1[2] = (float) 10.4;

        // write 2nd data set
        machine2.saveData(time2_1, valueSet2_1);


        // restore values machine 1
        int size = machine1.size();
        Assert.assertEquals(1, size);

        SensorDataRecord dataRecord = machine1.getDataSet(0);
        Assert.assertEquals(time1, dataRecord.getTime());
        Assert.assertArrayEquals(valueSet1, dataRecord.getValues(), 0);

        // restore values machine 2
        size = machine2.size();
        Assert.assertEquals(2, size);

        // get dataset @ index 0
        dataRecord = machine2.getDataSet(0);
        Assert.assertEquals(time2, dataRecord.getTime());
        Assert.assertArrayEquals(valueSet2, dataRecord.getValues(), 0);

        // get dataset @ index 1
        dataRecord = machine2.getDataSet(1);
        Assert.assertEquals(time2_1, dataRecord.getTime());
        Assert.assertArrayEquals(valueSet2_1, dataRecord.getValues(), 0);
    }
}
