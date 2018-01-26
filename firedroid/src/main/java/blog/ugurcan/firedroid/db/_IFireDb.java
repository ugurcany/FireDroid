package blog.ugurcan.firedroid.db;

import com.google.firebase.database.ValueEventListener;

/**
 * Created by ugurcan on 20.01.2018.
 */
public interface _IFireDb extends IFireDb, ValueEventListener {

    void setDbOperationListener(DbOperationListener dbOperationListener);

    void setSubscriptionListener(SubscriptionListener subscriptionListener);

    void startSubscriptions();

    void endSubscriptions();

}
