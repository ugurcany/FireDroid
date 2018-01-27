package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 26.01.2018.
 */
public interface DataChangeListener {

    <T> void onDataChanged(T data);

    void onSubscriptionFailed(Exception exception);

}
