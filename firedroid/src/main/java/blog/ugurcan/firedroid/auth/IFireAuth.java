package blog.ugurcan.firedroid.auth;

import android.net.Uri;

/**
 * Created by ugurcan on 31.12.2017.
 */
public interface IFireAuth {

    //USER DATA
    boolean isLoggedIn();

    Uri getUserImageUrl();

    String getUserDisplayName();

    String getUserEmail();

    String getUserPhone();

    AuthType getAuthType();

    //LOGIN
    void logInWithGoogle();

    void logInWithFacebook();

    void logInWithTwitter();

    //LOG OUT
    void logOut();

}
