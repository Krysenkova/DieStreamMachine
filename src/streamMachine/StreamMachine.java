package streamMachine;

//import streamMachine.model.DataRecord;


import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * We assume: Each sensor gets its own storage engine. There wont be a parameter
 * sensor name.
 */
public interface StreamMachine {

    /**
     * This method can be called by a sensor to save a data set.
     *
     * @param time timeStamp
     * @param values measured values by sensor
     * @throws IOException if something unexpected happened. Insufficient right, medium broken, offline..
     */
    void saveData(long time, float[] values) throws FileNotFoundException, IOException, PersistenceException;

    //DataRecord readAndPrintDataRecord() throws FileNotFoundException, IOException;

    /**
     *
     * @return number of data sets
     */
    int size();

    void clear();

    /**
     *
     * @param index position of data set
     */
    SensorDataRecord getDataSet(int index) throws PersistenceException;

}