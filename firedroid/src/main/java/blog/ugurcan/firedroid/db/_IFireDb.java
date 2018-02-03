package blog.ugurcan.firedroid.db;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ugurcan on 20.01.2018.
 */
public interface _IFireDb extends IFireDb, ValueEventListener, ChildEventListener {

    void setDbOperationListener(DbOperationListener dbOperationListener);

    void setDataChangeListener(DataChangeListener dataChangeListener);

    void setChildDataChangeListener(ChildDataChangeListener childDataChangeListener);

    void startSubscriptions();

    void endSubscriptions();

}
