package blog.ugurcan.app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_write_allowed_for_auth)
    void onWriteToAllowedForAuthClicked() {
        if (buttonWriteAllowedForAuth.getProgress() != 0)
            return;

        updateDbOpButton(buttonWriteAllowedForAuth, 1);
        FireDroid.db().write(1, "allowedForAuth/mydata",
                new DbObject(123, "Test"));
    }

    @OnClick(R.id.button_write_allowed)
    void onWriteToAllowedClicked() {
        if (buttonWriteAllowed.getProgress() != 0)
            return;

        updateDbOpButton(buttonWriteAllowed, 1);
        FireDroid.db().write(2, "allowed/mydata",
                new DbObject(123, "Test"));
    }

    @OnClick(R.id.button_write_not_allowed)
    void onWriteToNotAllowedClicked() {
        if (buttonWriteNotAllowed.getProgress() != 0)
            return;

        updateDbOpButton(buttonWriteNotAllowed, 1);
        FireDroid.db().write(3, "notAllowed/mydata",
                new DbObject(123, "Test"));
    }

    @OnClick(R.id.button_read_allowed)
    void onReadFromAllowedClicked() {
        if (buttonReadAllowed.getProgress() != 0)
            return;

        updateDbOpButton(buttonReadAllowed, 1);
        FireDroid.db().read(4, "allowed/mydata", DbObject.class);
    }

    @Override
    public <T> void onDbOperationCompleted(
            int opId, boolean isSuccessful, T data, Exception exception) {
        Log.d(getName(), "onDbOperationCompleted(): " + isSuccessful
                + "\n" + "--Data: " + data
                + "\n" + "--Exception: " + exception);

        if (opId == 1) {
            updateDbOpButton(buttonWriteAllowedForAuth, isSuccessful ? 100 : -1);
        } else if (opId == 2) {
            updateDbOpButton(buttonWriteAllowed, isSuccessful ? 100 : -1);
        } else if (opId == 3) {
            updateDbOpButton(buttonWriteNotAllowed, isSuccessful ? 100 : -1);
        } else if (opId == 4) {
            updateDbOpButton(buttonReadAllowed, isSuccessful ? 100 : -1);
            Toast.makeText(this, "" + data, Toast.LENGTH_SHORT).show();
        }
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
    @BindView(R.id.button_read_allowed)
    ActionProcessButton buttonReadAllowed;

}
