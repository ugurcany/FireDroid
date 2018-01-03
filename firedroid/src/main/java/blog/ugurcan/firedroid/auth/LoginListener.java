package blog.ugurcan.firedroid.auth;

/**
 * Created by ugurcan on 30.12.2017.
 */
public interface LoginListener extends AuthStateListener {

    void onLoginStarted();

    void onLoginCompleted(boolean isSuccessful);

}
