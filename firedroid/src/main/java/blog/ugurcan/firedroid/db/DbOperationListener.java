package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 20.01.2018.
 */
public interface DbOperationListener {

    <T> void onDbOperationCompleted(int opId, boolean isSuccessful, T data, Exception exception);

}
