package blog.ugurcan.app.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dd.processbutton.iml.ActionProcessButton;

import blog.ugurcan.app.data.DbObject;
import blog.ugurcan.app.R;
import blog.ugurcan.firedroid.FireDroid;
import blog.ugurcan.firedroid.FireDroidActivity;
import blog.ugurcan.firedroid.db.ChildDataChangeListener;
import blog.ugurcan.firedroid.db.DataChangeListener;
import blog.ugurcan.firedroid.db.DbOperationListener;
import blog.ugurcan.firedroid.db.SubscriptionConfig;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ugurcan on 20.01.2018.
 */
public class DbActivity extends FireDroidActivity
        implements DbOperationListener, DataChangeListener, ChildDataChangeListener {

    private static final String PATH_TO_DATALIST = "auth-user/data-list";
    private static final String PATH_TO_DATA = "auth-user/data";

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

    /*
     ***************************************
     */
    @OnClick(R.id.button_write)
    void onWriteClicked(ActionProcessButton button) {
        if (button.getProgress() != BUTTON_ACTIVE)
            return;

        updateDbOpButton(button, BUTTON_IN_PROGRESS);
        FireDroid.db().writeTo(button.getId(), PATH_TO_DATA, dbObject);
    }

    @OnClick(R.id.button_push_under)
    void onPushUnderClicked(ActionProcessButton button) {
        if (button.getProgress() != BUTTON_ACTIVE)
            return;

        updateDbOpButton(button, BUTTON_IN_PROGRESS);
        FireDroid.db().pushUnder(button.getId(), PATH_TO_DATALIST, dbObject);
    }

    @OnClick(R.id.button_read)
    void onReadClicked(ActionProcessButton button) {
        if (button.getProgress() != BUTTON_ACTIVE)
            return;

        updateDbOpButton(button, BUTTON_IN_PROGRESS);
        FireDroid.db().readFrom(button.getId(), PATH_TO_DATA, DbObject.class);
    }

    @OnClick(R.id.button_subscribe_to_data)
    void onSubscribeToDataClicked(ActionProcessButton button) {
        FireDroid.db().subscribeToDataChange(PATH_TO_DATA, DbObject.class);
    }

    @OnClick(R.id.button_unsubscribe_from_data)
    void onUnsubscribeFromDataClicked(ActionProcessButton button) {
        FireDroid.db().unsubscribeFromDataChange(PATH_TO_DATA);
    }

    @OnClick(R.id.button_subscribe_to_child_data)
    void onSubscribeToChildDataClicked(ActionProcessButton button) {
        SubscriptionConfig subscriptionConfig = new SubscriptionConfig.Builder()
                .limitTo(SubscriptionConfig.LimitType.LAST, 3)
                .orderBy(SubscriptionConfig.OrderType.CHILD, "timestamp")
                .build();

        FireDroid.db().subscribeToChildDataChange(PATH_TO_DATALIST, DbObject.class,
                subscriptionConfig);
    }

    @OnClick(R.id.button_unsubscribe_from_child_data)
    void onUnsubscribeFromChildDataClicked(ActionProcessButton button) {
        FireDroid.db().unsubscribeFromChildDataChange(PATH_TO_DATALIST);
    }

    /*
     ***************************************
     */
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

        //OP ID == BUTTON VIEW ID
        ActionProcessButton button = findViewById(opId);
        updateDbOpButton(button, BUTTON_ERROR);

        //SHOW DIALOG
        String title = "ERROR";
        String message = "Operation: " + button.getText().toString()
                + "\n" + "Error: " + exception;
        showMessage(title, message);
    }

    /*
     ***************************************
     */
    @Override
    public <T> void onDataChanged(T data) {
        Log.d(getName(), "onDataChanged()"
                + "\n" + "--Data: " + data);
    }

    @Override
    public <T> void onChildDataAdded(String key, T data) {
        Log.d(getName(), "onChildDataAdded()"
                + "\n" + "--Key: " + key
                + "\n" + "--Data: " + data);
    }

    @Override
    public <T> void onChildDataChanged(String key, T data) {
        Log.d(getName(), "onChildDataChanged()"
                + "\n" + "--Key: " + key
                + "\n" + "--Data: " + data);
    }

    @Override
    public <T> void onChildDataMoved(String key, T data) {
        Log.d(getName(), "onChildDataMoved()"
                + "\n" + "--Key: " + key
                + "\n" + "--Data: " + data);
    }

    @Override
    public <T> void onChildDataRemoved(String key, T data) {
        Log.d(getName(), "onChildDataRemoved()"
                + "\n" + "--Key: " + key
                + "\n" + "--Data: " + data);
    }

    @Override
    public void onSubscriptionFailed(Exception exception) {
        Log.d(getName(), "onSubscriptionFailed()"
                + "\n" + "--Exception: " + exception);
    }

    /*
     ***************************************
     */
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
