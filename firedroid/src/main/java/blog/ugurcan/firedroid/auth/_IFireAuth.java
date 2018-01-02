package blog.ugurcan.firedroid.auth;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ugurcan on 31.12.2017.
 */
public interface _IFireAuth extends IFireAuth, FirebaseAuth.AuthStateListener {

    void addAuthStateListener();

    void removeAuthStateListener();

    void handleLoginResult(int requestCode, int resultCode, Intent data);

}
