package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 20.01.2018.
 */
public interface IFireDb {

    void write(int opId, String path, Object value);

    <T> void read(int opId, String path, Class<T> dataClass);

}
