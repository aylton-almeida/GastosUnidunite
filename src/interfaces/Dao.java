package interfaces;

import java.util.List;

public interface Dao<T> {
    List<T> getAllObjects() throws Exception;

    T getObject(Object key) throws Exception;

    void addObject(T o) throws Exception;

    void updateObject(T o) throws Exception;

    void deleteObject(T o) throws Exception;
}
