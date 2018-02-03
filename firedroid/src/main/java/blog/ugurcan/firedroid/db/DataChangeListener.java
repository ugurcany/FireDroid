package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 26.01.2018.
 */
public interface DataChangeListener extends _SubscriptionStateListener {

    <T> void onDataChanged(T data);

}
