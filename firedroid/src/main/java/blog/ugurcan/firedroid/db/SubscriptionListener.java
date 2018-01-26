package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 26.01.2018.
 */
public interface SubscriptionListener {

    <T> void onDataChanged(T data);

    void onSubscriptionStarted();

    void onSubscriptionFailed(Exception exception);

    void onSubscriptionEnded();

}
