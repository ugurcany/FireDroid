package blog.ugurcan.app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dd.processbutton.iml.ActionProcessButton;

import blog.ugurcan.firedroid.FireDroid;
import blog.ugurcan.firedroid.FireDroidActivity;
import blog.ugurcan.firedroid.db.DbOperationListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ugurcan on 20.01.2018.
 */
public class DbActivity extends FireDroidActivity implements DbOperationListener {

    private DbObject dbObject;

    private static final String ALLOWED_LIST_PATH = "allowed/datalist";
    private static final String ALLOWED_PATH = "allowed/mydata";
    private static final String ALLOWED_FOR_AUTH_PATH = "allowedForAuth/mydata";
    private static final String NOT_ALLOWED_PATH = "notAllowed/mydata";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        ButterKnife.bind(this);

        dbObject = new DbObject(123, "Test");
    }

    @OnClick(R.id.button_write_allowed)
    void onWriteToAllowedClicked() {
        if (buttonWriteAllowed.getProgress() != 0)
            return;

        updateDbOpButton(buttonWriteAllowed, 1);
        FireDroid.db().write(R.id.button_write_allowed, ALLOWED_PATH, dbObject);
    }

    @OnClick(R.id.button_write_allowed_for_auth)
    void onWriteToAllowedForAuthClicked() {
        if (buttonWriteAllowedForAuth.getProgress() != 0)
            return;

        updateDbOpButton(buttonWriteAllowedForAuth, 1);
        FireDroid.db().write(R.id.button_write_allowed_for_auth, ALLOWED_FOR_AUTH_PATH, dbObject);
    }

    @OnClick(R.id.button_write_not_allowed)
    void onWriteToNotAllowedClicked() {
        if (buttonWriteNotAllowed.getProgress() != 0)
            return;

        updateDbOpButton(buttonWriteNotAllowed, 1);
        FireDroid.db().write(R.id.button_write_not_allowed, NOT_ALLOWED_PATH, dbObject);
    }

    @OnClick(R.id.button_push_under_allowed)
    void onPushUnderAllowedClicked() {
        if (buttonPushUnderAllowed.getProgress() != 0)
            return;

        updateDbOpButton(buttonPushUnderAllowed, 1);
        FireDroid.db().pushUnder(R.id.button_push_under_allowed, ALLOWED_LIST_PATH, dbObject);
    }

    @OnClick(R.id.button_read_allowed)
    void onReadFromAllowedClicked() {
        if (buttonReadAllowed.getProgress() != 0)
            return;

        updateDbOpButton(buttonReadAllowed, 1);
        FireDroid.db().read(R.id.button_read_allowed, ALLOWED_PATH, DbObject.class);
    }

    @Override
    public <T> void onDbOperationSuccessful(int opId, T data) {
        Log.d(getName(), "onDbOperationSuccessful()"
                + "\n" + "--Data: " + data);

        ActionProcessButton button = findViewById(opId);
        updateDbOpButton(button, 100);
    }

    @Override
    public void onDbOperationFailed(int opId, Exception exception) {
        Log.d(getName(), "onDbOperationFailed()"
                + "\n" + "--Exception: " + exception);

        ActionProcessButton button = findViewById(opId);
        updateDbOpButton(button, -1);
    }

    private void updateDbOpButton(final ActionProcessButton button, int progress) {
        button.setMode(ActionProcessButton.Mode.ENDLESS);
        button.setProgress(progress);

        if (progress == -1 || progress == 100) {
            new CountDownTimer(3000, 3000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    button.setProgress(0);
                }
            }.start();
        }
    }

    @BindView(R.id.button_write_allowed_for_auth)
    ActionProcessButton buttonWriteAllowedForAuth;
    @BindView(R.id.button_write_allowed)
    ActionProcessButton buttonWriteAllowed;
    @BindView(R.id.button_write_not_allowed)
    ActionProcessButton buttonWriteNotAllowed;
    @BindView(R.id.button_push_under_allowed)
    ActionProcessButton buttonPushUnderAllowed;
    @BindView(R.id.button_read_allowed)
    ActionProcessButton buttonReadAllowed;

}
