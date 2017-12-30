package blog.ugurcan.firedroid.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;

import blog.ugurcan.firedroid.FireDroid;

/**
 * Created by ugurcan on 30.12.2017.
 */
public class FireAuth implements GoogleApiClient.OnConnectionFailedListener {

    private final static int REQUEST_GOOGLE_LOGIN = 9001;

    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleSignInClient googleSignInClient;

    private LoginListener loginListener;
    private LogoutListener logoutListener;

    public FireAuth() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (isLoggedIn() && FireDroid.isOnLoginActivity()) {
                    FireDroid.currentActivity().finish();
                } else if (!isLoggedIn() && !FireDroid.isOnLoginActivity()) {
                    FireDroid.goToLogin();
                }
            }
        };
    }

    public void init(String googleWebClientId) {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(googleWebClientId)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(FireDroid.context(), gso);
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setLogoutListener(LogoutListener logoutListener) {
        this.logoutListener = logoutListener;
    }

    public void addAuthStateListener() {
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    public void removeAuthStateListener() {
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
    }

    private boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public String getUserDisplayName() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getDisplayName();
        } else {
            return "-";
        }
    }

    public void logIn(AuthType authType) {
        switch (authType) {
            case Google:
                Intent signInIntent = googleSignInClient.getSignInIntent();
                FireDroid.currentActivity()
                        .startActivityForResult(signInIntent, FireAuth.REQUEST_GOOGLE_LOGIN);
                break;
            /*case Facebook:
                facebookAuthInteractor.signOut(this);
                break;*/
        }
    }

    public void handleLoginResult(int requestCode, Intent data) {
        if (requestCode == FireAuth.REQUEST_GOOGLE_LOGIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                authWith(AuthType.Google, account.getIdToken());
            } catch (ApiException e) {
                loginListener.onLoginCompleted(false);
            }
        }
    }

    private void authWith(AuthType authType, String token) {
        loginListener.onLoginStarted();

        AuthCredential credential;
        switch (authType) {
            case Google:
                credential = GoogleAuthProvider.getCredential(token, null);
                break;
            case Facebook:
                credential = FacebookAuthProvider.getCredential(token);
                break;
            default:
                return;
        }

        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(FireDroid.currentActivity(),
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loginListener.onLoginCompleted(task.isSuccessful());
                            }
                        });
    }

    public void logOut() {
        switch (getAuthType()) {
            case Google:
                logoutListener.onLogoutStarted();
                googleSignInClient.signOut().addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                logoutListener.onLogoutCompleted();
                                FirebaseAuth.getInstance().signOut();
                            }
                        });
                break;
            /*case Facebook:
                facebookAuthInteractor.signOut(this);
                break;*/
        }
    }

    private AuthType getAuthType() {
        if (!isLoggedIn()) {
            return AuthType.NONE;
        }

        List<String> providers = getAuthProviders();
        if (providers != null && providers.size() > 0) {
            if (providers.get(0).equals(GoogleAuthProvider.PROVIDER_ID)) {
                return AuthType.Google;
            } else if (providers.get(0).equals(FacebookAuthProvider.PROVIDER_ID)) {
                return AuthType.Facebook;
            }
        }

        return AuthType.UNIDENTIFIED;
    }

    private List<String> getAuthProviders() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getProviders();
        } else {
            return null;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(FireDroid.context(), "Google Play Services error!",
                Toast.LENGTH_SHORT).show();
    }

    public enum AuthType {
        Google, Facebook, UNIDENTIFIED, NONE;
    }

}
