package streamMachine;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SensorDataStorageImpl implements SensorDataStorage {
    public SensorDataStorageImpl(String fileName) {
    }

    @Override
    public void saveData(long time, float[] values) throws FileNotFoundException, IOException, PersistenceException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public SensorDataRecord getDataSet(int index) throws PersistenceException {
        return null;
    }
}
