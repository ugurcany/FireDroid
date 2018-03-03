package blog.ugurcan.firedroid.db;

import com.google.firebase.database.Query;

/**
 * Created by ugurcan on 26.01.2018.
 */
class Subscription<T> {

    private Query query;
    private Class<T> dataClass;
    private boolean isChildSubsc;

    Subscription(Query query) {
        this.query = query;
        this.dataClass = null;
        this.isChildSubsc = false;
    }

    Subscription(Query query, Class<T> dataClass, boolean isChildSubsc) {
        this.query = query;
        this.dataClass = dataClass;
        this.isChildSubsc = isChildSubsc;
    }

    public Query getQuery() {
        return query;
    }

    public Class<T> getDataClass() {
        return dataClass;
    }

    public boolean isChildSubsc() {
        return isChildSubsc;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Subscription
                && query != null && ((Subscription) obj).query != null
                && query.getRef().equals(((Subscription) obj).query.getRef());
    }
}
