package blog.ugurcan.firedroid.db;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by ugurcan on 26.01.2018.
 */
class Subscription<T> {

    private DatabaseReference dbReference;
    private Class<T> dataClass;

    public Subscription(DatabaseReference dbReference) {
        this.dbReference = dbReference;
        this.dataClass = null;
    }

    public Subscription(DatabaseReference dbReference, Class<T> dataClass) {
        this.dbReference = dbReference;
        this.dataClass = dataClass;
    }

    public DatabaseReference getDbReference() {
        return dbReference;
    }

    public void setDbReference(DatabaseReference dbReference) {
        this.dbReference = dbReference;
    }

    public Class<T> getDataClass() {
        return dataClass;
    }

    public void setDataClass(Class<T> dataClass) {
        this.dataClass = dataClass;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Subscription
                && dbReference != null
                && ((Subscription) obj).dbReference != null
                && dbReference.equals(((Subscription) obj).dbReference);
    }
}
