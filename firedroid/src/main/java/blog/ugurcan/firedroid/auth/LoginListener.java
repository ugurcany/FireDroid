package blog.ugurcan.firedroid.auth;

/**
 * Created by ugurcan on 30.12.2017.
 */
public interface LoginListener {

    void onLoginStarted();

    void onLoginCompleted(boolean isSuccessful);

}
