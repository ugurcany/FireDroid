package blog.ugurcan.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import blog.ugurcan.app.R;
import blog.ugurcan.firedroid.FireDroid;
import blog.ugurcan.firedroid.FireDroidActivity;
import blog.ugurcan.firedroid.auth.LoginListener;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ugurcan on 30.12.2017.
 */
public class LoginActivity extends FireDroidActivity implements LoginListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_google_login)
    void onGoogleLoginClicked() {
        FireDroid.auth().logInWithGoogle();
    }

    @OnClick(R.id.button_facebook_login)
    void onFacebookLoginClicked() {
        FireDroid.auth().logInWithFacebook();
    }

    @OnClick(R.id.button_twitter_login)
    void onTwitterLoginClicked() {
        FireDroid.auth().logInWithTwitter();
    }

    @Override
    public void onLoginStarted() {
        Log.d(getName(), "onLoginStarted()");
        showProgress("Logging in... Please wait");
    }

    @Override
    public void onLoginCompleted(boolean isSuccessful) {
        Log.d(getName(), "onLoginCompleted(): " + isSuccessful);
        hideDialog();

        if (isSuccessful) {
            Toast.makeText(this, "Successfully logged in!",
                    Toast.LENGTH_SHORT).show();

            finish();
        } else {
            Toast.makeText(this, "Login failed!",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
