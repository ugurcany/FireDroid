package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 20.01.2018.
 */
public interface DbOperationListener {

    <T> void onDbOperationSuccessful(int opId, T data);

    void onDbOperationFailed(int opId, Exception exception);

}
