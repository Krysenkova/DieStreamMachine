package filepersistence;

import filepersistence.model.DataRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SensorDataStorageTest {

    // constants
    public static final String testSensorName = "TestTemperatureSensor.txt";

    // variables
    SensorDataStorage mySensor;
    DataRecord dataRecord;

    @Before
    public void setUp() throws Exception {
        mySensor = new TemperatureSensor(testSensorName);
        dataRecord = new DataRecord(5L, new float[]{1f, 2f, 3f});
    }

    @After
    public void tearDown() throws Exception {
        File file = new File(testSensorName);
        file.delete();
    }

    @Test
    public void test_save_and_read_data() throws IOException {
        mySensor.saveData(dataRecord);
        DataRecord readDataRecord = mySensor.readAndPrintDataRecord();

        assertTrue(dataRecord.getTimeStamp() == readDataRecord.getTimeStamp());
        assertTrue(Arrays.equals(dataRecord.getReadValues(), readDataRecord.getReadValues()));

        assertFalse(readDataRecord.getTimeStamp() == -1);
        assertFalse(readDataRecord.getReadValues().equals(new float[]{1f,2f,3f}));
    }


    @Test
    public void test_size() throws IOException {
        mySensor.saveData(dataRecord);
        assertEquals(mySensor.size(), 1);
    }

    @Test
    public void test_clear() throws IOException {
        mySensor.saveData(dataRecord);
        assertTrue(mySensor.clear());
    }
}