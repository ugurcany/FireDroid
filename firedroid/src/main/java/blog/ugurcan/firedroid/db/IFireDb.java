package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 20.01.2018.
 */
public interface IFireDb {

    void writeTo(int opId, String path, Object data);

    void pushUnder(int opId, String path, Object data);

    <T> void readFrom(int opId, String path, Class<T> dataClass);


    <T> void subscribeToDataChange(String path, Class<T> dataClass);

    void unsubscribeFromDataChange(String path);


    <T> void subscribeToChildDataChange(String path, Class<T> dataClass);

    void unsubscribeFromChildDataChange(String path);

}
