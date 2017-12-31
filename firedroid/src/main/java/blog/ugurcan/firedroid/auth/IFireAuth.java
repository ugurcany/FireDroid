package blog.ugurcan.firedroid.auth;

import android.net.Uri;

/**
 * Created by ugurcan on 31.12.2017.
 */
public interface IFireAuth {

    //LISTENER
    void setLoginListener(LoginListener loginListener);

    void setLogoutListener(LogoutListener logoutListener);

    //USER DATA
    Uri getUserImageUrl();

    String getUserDisplayName();

    String getUserEmail();

    String getUserPhone();

    AuthType getAuthType();

    //LOG OUT
    void logOut();

}
