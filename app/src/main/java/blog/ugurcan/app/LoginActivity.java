package blog.ugurcan.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import blog.ugurcan.firedroid.FireDroid;
import blog.ugurcan.firedroid.FireDroidActivity;
import blog.ugurcan.firedroid.auth.LoginListener;
import blog.ugurcan.firedroid.view.GoogleLoginButton;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ugurcan on 30.12.2017.
 */
public class LoginActivity extends FireDroidActivity implements LoginListener {

    @BindView(R.id.button_google_login)
    GoogleLoginButton googleLoginButton;

    @BindString(R.string.google_web_client_id)
    String googleWebClientId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        FireDroid.auth().setLoginListener(this);
    }

    @Override
    public String getName() {
        return LoginActivity.class.getSimpleName();
    }

    @Override
    public void onLoginStarted() {
        Log.d(getName(), "onLoginStarted()");
        showDialog("Logging in... Please wait");
    }

    @Override
    public void onLoginCompleted(boolean isSuccess) {
        Log.d(getName(), "onLoginCompleted(): " + isSuccess);
        hideDialog();

        if (isSuccess) {
            Toast.makeText(this, "Successfully logged in!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Login failed!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
