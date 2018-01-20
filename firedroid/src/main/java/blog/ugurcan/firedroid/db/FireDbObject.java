package blog.ugurcan.firedroid.db;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

/**
 * Created by ugurcan on 21.01.2018.
 */
public class FireDbObject {

    private final Object timestamp = ServerValue.TIMESTAMP;

    Object getTimestamp() {
        return timestamp;
    }

    @Exclude
    public long timestamp() {
        return (long) timestamp;
    }

}
