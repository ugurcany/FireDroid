package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 26.01.2018.
 */
public interface DataChangeListener extends _SubscriptionListener {

    <T> void onDataChanged(T data);

}
