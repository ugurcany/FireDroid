package blog.ugurcan.firedroid.db;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import blog.ugurcan.firedroid.FireDroid;

/**
 * Created by ugurcan on 20.01.2018.
 */
public class FireDb implements _IFireDb {

    private DbOperationListener dbOperationListener;

    FireDb(FireDb.Initializer initializer) {

    }

    @Override
    public void setDbOperationListener(DbOperationListener dbOperationListener) {
        this.dbOperationListener = dbOperationListener;
    }

    @Override
    public void write(final int opId, String path, final Object data) {
        if (dbOperationListener == null)
            throw new IllegalStateException("Class does not implement DbOperationListener!");

        FirebaseDatabase.getInstance().getReference(path).setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
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

                        if (data != null) {
                            dbOperationListener.onDbOperationSuccessful(opId, data);
                        } else {
                            dbOperationListener.onDbOperationFailed(opId, null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        dbOperationListener.onDbOperationFailed(opId, databaseError.toException());
                    }
                });
    }

    public static class Initializer {

        public Initializer() {
        }

        public Initializer persistData(boolean persist) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(persist);
            return this;
        }

        public void init() {
            FireDb db = new FireDb(this);
            FireDroid.setDb(db);
        }

    }

}
