package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 27.01.2018.
 */
public interface ChildDataChangeListener extends _SubscriptionListener {

    <T> void onChildDataAdded(String key, T data);

    <T> void onChildDataChanged(String key, T data);

    <T> void onChildDataMoved(String key, T data);

    <T> void onChildDataRemoved(String key, T data);

}
