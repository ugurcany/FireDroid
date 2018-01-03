package blog.ugurcan.firedroid.auth;

/**
 * Created by ugurcan on 30.12.2017.
 */
public interface LogoutListener extends AuthStateListener {

    void onLogoutStarted();

    void onLogoutCompleted();

}
