package blog.ugurcan.firedroid.db;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

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
    public void write(final int opId, String path, Object value) {
        if (dbOperationListener == null)
            throw new IllegalStateException("Class does not implement DbOperationListener!");

        FirebaseDatabase.getInstance().getReference(path).setValue(value)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dbOperationListener.onDbOperationCompleted(
                                opId, task.isSuccessful(), task.getException());
                    }
                });
    }

    public static class Initializer {

        public Initializer() {
        }

        public Initializer persistData(boolean persist){
            FirebaseDatabase.getInstance().setPersistenceEnabled(persist);
            return this;
        }

        public void init() {
            FireDb db = new FireDb(this);
            FireDroid.setDb(db);
        }

    }

}
