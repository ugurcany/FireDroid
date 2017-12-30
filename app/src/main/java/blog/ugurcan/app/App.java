package blog.ugurcan.app;

import android.app.Application;

import blog.ugurcan.firedroid.FireDroid;

/**
 * Created by ugurcan on 28.12.2017.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FireDroid.init(getApplicationContext());
        FireDroid.auth()
                .google(getString(R.string.google_web_client_id))
                .facebook(getString(R.string.facebook_app_id))
                .init(LoginActivity.class);
    }

}
