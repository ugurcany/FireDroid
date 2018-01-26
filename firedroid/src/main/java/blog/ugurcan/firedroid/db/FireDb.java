package blog.ugurcan.firedroid.db;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import blog.ugurcan.firedroid.FireDroid;

/**
 * Created by ugurcan on 20.01.2018.
 */
public class FireDb implements _IFireDb {

    private DbOperationListener dbOperationListener;
    private SubscriptionListener subscriptionListener;

    private List<Subscription> subscriptions;

    FireDb(FireDb.Initializer initializer) {
        subscriptions = new ArrayList<>();
    }

    @Override
    public void setDbOperationListener(DbOperationListener dbOperationListener) {
        this.dbOperationListener = dbOperationListener;
    }

    @Override
    public void setSubscriptionListener(SubscriptionListener subscriptionListener) {
        this.subscriptionListener = subscriptionListener;
    }

    @Override
    public void write(final int opId, String path, final Object data) {
        if (dbOperationListener == null)
            throw new IllegalStateException("Class does not implement DbOperationListener!");

        _write(opId, path, data, false);
    }

    @Override
    public void pushUnder(final int opId, String path, final Object data) {
        if (dbOperationListener == null)
            throw new IllegalStateException("Class does not implement DbOperationListener!");

        _write(opId, path, data, true);
    }

    private void _write(final int opId, String path, final Object data, boolean pushUnder) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(path);

        if (pushUnder)
            dbRef = dbRef.push();

        dbRef.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dbOperationListener.onDbOperationSuccessful(opId, data);
                } else {
                    dbOperationListener.onDbOperationFailed(opId, task.getException());
                }
            }
        });
    }

    @Override
    public <T> void read(final int opId, String path, final Class<T> dataClass) {
        FirebaseDatabase.getInstance().getReference(path)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        T data = dataSnapshot.getValue(dataClass);
                        dbOperationListener.onDbOperationSuccessful(opId, data);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        dbOperationListener.onDbOperationFailed(opId, databaseError.toException());
                    }
                });
    }

    @Override
    public <T> void subscribe(String path, final Class<T> dataClass) {
        if (subscriptionListener == null)
            throw new IllegalStateException("Class does not implement SubscriptionListener!");

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(path);
        Subscription<T> subsc = new Subscription<T>(dbRef, dataClass);

        if (!subscriptions.contains(subsc)) {
            subscriptions.add(subsc);
            subscriptionListener.onSubscriptionStarted();

            dbRef.removeEventListener(this);
            dbRef.addValueEventListener(this);
        } else {
            subscriptionListener.onSubscriptionFailed(new Exception("Already subscribed!"));
        }
    }

    @Override
    public void unsubscribe(String path) {
        if (subscriptionListener == null)
            throw new IllegalStateException("Class does not implement SubscriptionListener!");

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(path);
        Subscription subsc = getSubscription(dbRef);

        if (subscriptions.contains(subsc)) {
            subscriptions.remove(subsc);
            subscriptionListener.onSubscriptionEnded();

            dbRef.removeEventListener(this);
        } else {
            subscriptionListener.onSubscriptionFailed(new Exception("Already unsubscribed!"));
        }
    }

    @Override
    public void startSubscriptions() {
        if (subscriptionListener == null)
            return;

        for (Subscription subsc : subscriptions) {
            subsc.getDbReference().addValueEventListener(this);
        }
    }

    @Override
    public void endSubscriptions() {
        if (subscriptionListener == null)
            return;

        for (Subscription subsc : subscriptions) {
            subsc.getDbReference().removeEventListener(this);
        }
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Subscription subsc = getSubscription(dataSnapshot.getRef());
        if (subsc != null) {
            Object data = dataSnapshot.getValue(subsc.getDataClass());
            subscriptionListener.onDataChanged(subsc.getDataClass().cast(data));
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        subscriptionListener.onSubscriptionFailed(databaseError.toException());
    }

    private <T> Subscription getSubscription(DatabaseReference dbRef) {
        int index = subscriptions.indexOf(new Subscription<T>(dbRef));
        if (index >= 0 && index < subscriptions.size()) {
            return subscriptions.get(index);
        }
        return null;
    }

    /*
     * DB INITIALIZER
     */
    public static class Initializer {

        public Initializer() {
        }

        public Initializer diskPersistence(boolean isEnabled) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(isEnabled);
            return this;
        }

        public void init() {
            FireDb db = new FireDb(this);
            FireDroid.setDb(db);
        }

    }

}
