package blog.ugurcan.app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dd.processbutton.iml.ActionProcessButton;

import blog.ugurcan.firedroid.FireDroid;
import blog.ugurcan.firedroid.FireDroidActivity;
import blog.ugurcan.firedroid.db.DbOperationListener;
import blog.ugurcan.firedroid.db.SubscriptionListener;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ugurcan on 20.01.2018.
 */
public class DbActivity extends FireDroidActivity
        implements DbOperationListener, SubscriptionListener {

    private static final String PATH_TO_DATALIST = "allowed/auth-user/data-list";
    private static final String PATH_TO_DATA = "allowed/auth-user/data";

    private static final int BUTTON_ACTIVE = 0;
    private static final int BUTTON_IN_PROGRESS = 1;
    private static final int BUTTON_SUCCESS = 100;
    private static final int BUTTON_ERROR = -1;

    private DbObject dbObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        ButterKnife.bind(this);

        dbObject = new DbObject(123, "Test");
    }

    @OnClick(R.id.button_write)
    void onWriteClicked(ActionProcessButton button) {
        if (button.getProgress() != BUTTON_ACTIVE)
            return;

        updateDbOpButton(button, BUTTON_IN_PROGRESS);
        FireDroid.db().write(R.id.button_write, PATH_TO_DATA, dbObject);
    }

    @OnClick(R.id.button_push_under)
    void onPushUnderClicked(ActionProcessButton button) {
        if (button.getProgress() != BUTTON_ACTIVE)
            return;

        updateDbOpButton(button, BUTTON_IN_PROGRESS);
        FireDroid.db().pushUnder(R.id.button_push_under, PATH_TO_DATALIST, dbObject);
    }

    @OnClick(R.id.button_read)
    void onReadClicked(ActionProcessButton button) {
        if (button.getProgress() != BUTTON_ACTIVE)
            return;

        updateDbOpButton(button, BUTTON_IN_PROGRESS);
        FireDroid.db().read(R.id.button_read, PATH_TO_DATA, DbObject.class);
    }

    @OnClick(R.id.button_subscribe)
    void onSubscribeClicked(ActionProcessButton button) {
        FireDroid.db().subscribe(PATH_TO_DATA, DbObject.class);
    }

    @OnClick(R.id.button_unsubscribe)
    void onUnsubscribeClicked(ActionProcessButton button) {
        FireDroid.db().unsubscribe(PATH_TO_DATA);
    }

    @Override
    public <T> void onDbOperationSuccessful(int opId, T data) {
        Log.d(getName(), "onDbOperationSuccessful()"
                + "\n" + "--Data: " + data);

        ActionProcessButton button = findViewById(opId);
        updateDbOpButton(button, BUTTON_SUCCESS);

        //SHOW DIALOG
        String title = "SUCCESSFUL";
        String message = "Operation: " + button.getText().toString()
                + "\n" + "Data: " + data;
        showMessage(title, message);
    }

    @Override
    public void onDbOperationFailed(int opId, Exception exception) {
        Log.d(getName(), "onDbOperationFailed()"
                + "\n" + "--Exception: " + exception);

        ActionProcessButton button = findViewById(opId);
        updateDbOpButton(button, BUTTON_ERROR);

        //SHOW DIALOG
        String title = "ERROR";
        String message = "Operation: " + button.getText().toString()
                + "\n" + "Error: " + exception;
        showMessage(title, message);
    }

    @Override
    public <T> void onDataChanged(T data) {
        Log.d(getName(), "onDataChanged()"
                + "\n" + "--Data: " + data);

        //SHOW DIALOG
        String title = "DATA CHANGED";
        String message = "Data: " + data;
        showMessage(title, message);
    }

    @Override
    public void onSubscriptionStarted() {
        Log.d(getName(), "onSubscriptionStarted()");
    }

    @Override
    public void onSubscriptionFailed(Exception exception) {
        Log.d(getName(), "onSubscriptionFailed()"
                + "\n" + "--Exception: " + exception);
    }

    @Override
    public void onSubscriptionEnded() {
        Log.d(getName(), "onSubscriptionEnded()");
    }

    private void updateDbOpButton(final ActionProcessButton button, int progress) {
        button.setMode(ActionProcessButton.Mode.ENDLESS);
        button.setProgress(progress);

        if (progress == BUTTON_ERROR || progress == BUTTON_SUCCESS) {
            new CountDownTimer(3000, 3000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    button.setProgress(BUTTON_ACTIVE);
                }
            }.start();
        }
    }

}
