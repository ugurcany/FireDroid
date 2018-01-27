package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 27.01.2018.
 */
public interface ChildDataChangeListener {

    <T> void onChildDataAdded(T data);

    <T> void onChildDataChanged(T data);

    <T> void onChildDataMoved(T data);

    <T> void onChildDataRemoved(T data);

    void onSubscriptionFailed(Exception exception);

}
