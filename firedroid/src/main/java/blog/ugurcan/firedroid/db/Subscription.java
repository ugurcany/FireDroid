package blog.ugurcan.firedroid.db;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by ugurcan on 26.01.2018.
 */
class Subscription<T> {

    private DatabaseReference dbReference;
    private Class<T> dataClass;
    private boolean isChildSubsc;

    public Subscription(DatabaseReference dbReference) {
        this.dbReference = dbReference;
        this.dataClass = null;
        this.isChildSubsc = false;
    }

    public Subscription(DatabaseReference dbReference, Class<T> dataClass, boolean isChildSubsc) {
        this.dbReference = dbReference;
        this.dataClass = dataClass;
        this.isChildSubsc = isChildSubsc;
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

    public boolean isChildSubsc() {
        return isChildSubsc;
    }

    public void setChildSubsc(boolean childSubsc) {
        isChildSubsc = childSubsc;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Subscription
                && dbReference != null
                && ((Subscription) obj).dbReference != null
                && dbReference.equals(((Subscription) obj).dbReference);
    }
}
