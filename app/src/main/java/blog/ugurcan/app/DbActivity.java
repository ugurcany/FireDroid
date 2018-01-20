package blog.ugurcan.app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

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

        updateWriteButton(buttonWriteAllowedForAuth, 1);
        FireDroid.db().write(1, "allowedForAuth/mydata", new Pair<>(123, "Test"));
    }

    @OnClick(R.id.button_write_allowed)
    void onWriteToAllowedClicked() {
        if (buttonWriteAllowed.getProgress() != 0)
            return;

        updateWriteButton(buttonWriteAllowed, 1);
        FireDroid.db().write(2, "allowed/mydata", new Pair<>(123, "Test"));
    }

    @OnClick(R.id.button_write_not_allowed)
    void onWriteToNotAllowedClicked() {
        if (buttonWriteNotAllowed.getProgress() != 0)
            return;

        updateWriteButton(buttonWriteNotAllowed, 1);
        FireDroid.db().write(3, "notAllowed/mydata", new Pair<>(123, "Test"));
    }

    @Override
    public void onDbOperationCompleted(int opId, boolean isSuccessful, Exception exception) {
        Log.d(getName(), "onDbOperationCompleted(): " + isSuccessful
                + "\n" + "--Exception: " + exception);

        if (opId == 1) {
            updateWriteButton(buttonWriteAllowedForAuth, isSuccessful ? 100 : -1);
        } else if (opId == 2) {
            updateWriteButton(buttonWriteAllowed, isSuccessful ? 100 : -1);
        } else if (opId == 3) {
            updateWriteButton(buttonWriteNotAllowed, isSuccessful ? 100 : -1);
        }
    }

    private void updateWriteButton(final ActionProcessButton button, int progress) {
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

}
