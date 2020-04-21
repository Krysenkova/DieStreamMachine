package filepersistence;

import filepersistence.model.DataRecord;

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
     * @param dataRecord   A class that has all data to be saved
     * @throws IOException if something unexpected happened. Insufficient right, medium broken, offline..
     */
    void saveData(DataRecord dataRecord) throws FileNotFoundException, IOException;

    DataRecord readAndPrintDataRecord() throws FileNotFoundException, IOException;

    int size();

    boolean clear();

    // what else could we need? Get inspired by e.g. this:
    // https://docs.oracle.com/javase/7/docs/api/java/util/List.html

    // size() is usually a good idea
    // get at data set at a position as well.
    // what can go wrong.... Declare methods. Don't forget exceptions. Write comments!
}