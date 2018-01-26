package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 20.01.2018.
 */
public interface IFireDb {

    void write(int opId, String path, Object data);

    void pushUnder(int opId, String path, Object data);

    <T> void read(int opId, String path, Class<T> dataClass);

    <T> void subscribe(String path, Class<T> dataClass);

    void unsubscribe(String path);

}
