package streamMachine;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * We assume: Each sensor gets its own storage engine. There wont be a parameter
 * sensor name.
 */
public interface SensorDataStorage {

    /**
     * This method can be called by a sensor to save a data set.
     *
     * @param time   timeStamp
     * @param values measured values by sensor
     * @throws IOException if something unexpected happened. Insufficient right, medium broken, offline..
     */
    void saveData(long time, float[] values) throws IOException, PersistenceException;

    /**
     * @return number of data sets
     */
    int size();

    /**
     * clears the data written to file
     */
    void clear();

    /**
     * @param index position of data set
     */
    SensorDataRecord getDataSet(int index) throws PersistenceException;
}
