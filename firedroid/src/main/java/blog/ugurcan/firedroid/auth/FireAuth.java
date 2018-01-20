package blog.ugurcan.firedroid.auth;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;
import java.util.List;

import blog.ugurcan.firedroid.FireDroid;

/**
 * Created by ugurcan on 30.12.2017.
 */
public class FireAuth implements _IFireAuth {

    private final static int REQUEST_GOOGLE_LOGIN = 9001;

    private GoogleSignInClient googleSignInClient;
    private CallbackManager fbCallbackManager;
    private TwitterAuthClient twitterAuthClient;

    private LoginListener loginListener;
    private LogoutListener logoutListener;

    FireAuth(Initializer initializer) {
        this.googleSignInClient = initializer.googleSignInClient;
        this.fbCallbackManager = initializer.fbCallbackManager;
        this.twitterAuthClient = initializer.twitterAuthClient;
    }

    @Override
    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public void setLogoutListener(LogoutListener logoutListener) {
        this.logoutListener = logoutListener;
    }

    @Override
    public boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    @Override
    public Uri getUserImageUrl() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getPhotoUrl();
        } else {
            return null;
        }
    }

    @Override
    public String getUserDisplayName() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getDisplayName();
        } else {
            return "-";
        }
    }

    @Override
    public String getUserEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getEmail();
        } else {
            return "-";
        }
    }

    @Override
    public String getUserPhone() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getPhoneNumber();
        } else {
            return "-";
        }
    }

    @Override
    public AuthType getAuthType() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {

            List<String> providers = currentUser.getProviders();
            if (providers != null && providers.size() > 0) {
                switch (providers.get(0)) {
                    case GoogleAuthProvider.PROVIDER_ID:
                        return AuthType.Google;
                    case FacebookAuthProvider.PROVIDER_ID:
                        return AuthType.Facebook;
                    case TwitterAuthProvider.PROVIDER_ID:
                        return AuthType.Twitter;
                }
            }

            return AuthType.UNIDENTIFIED;

        } else {
            return AuthType.NONE;
        }
    }

    @Override
    public void logInWithGoogle() {
        if (googleSignInClient == null)
            throw new IllegalStateException("Google auth not initialized!");
        if (loginListener == null)
            throw new IllegalStateException("Class does not implement LoginListener!");

        Intent signInIntent = googleSignInClient.getSignInIntent();
        FireDroid.currentActivity()
                .startActivityForResult(signInIntent, FireAuth.REQUEST_GOOGLE_LOGIN);
    }

    @Override
    public void logInWithFacebook() {
        if (fbCallbackManager == null)
            throw new IllegalStateException("Facebook auth not initialized!");
        if (loginListener == null)
            throw new IllegalStateException("Class does not implement LoginListener!");

        FacebookCallback<LoginResult> fbCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                doFirebaseLogin(AuthType.Facebook, loginResult.getAccessToken().getToken(), null);
            }

            @Override
            public void onCancel() {
                loginListener.onLoginCompleted(false);
            }

            @Override
            public void onError(FacebookException error) {
                loginListener.onLoginCompleted(false);
            }
        };
        LoginManager.getInstance().registerCallback(fbCallbackManager, fbCallback);

        List<String> perms = Arrays.asList("email", "public_profile");
        LoginManager.getInstance().logInWithReadPermissions(FireDroid.currentActivity(), perms);
    }

    @Override
    public void logInWithTwitter() {
        if (twitterAuthClient == null)
            throw new IllegalStateException("Twitter auth not initialized!");
        if (loginListener == null)
            throw new IllegalStateException("Class does not implement LoginListener!");

        Callback<TwitterSession> twitterCallback = new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterAuthToken twitterAuthToken = result.data.getAuthToken();
                doFirebaseLogin(AuthType.Twitter, twitterAuthToken.token, twitterAuthToken.secret);
            }

            @Override
            public void failure(TwitterException exception) {
                loginListener.onLoginCompleted(false);
            }
        };

        twitterAuthClient.authorize(FireDroid.currentActivity(), twitterCallback);
    }

    @Override
    public void logOut() {
        if (logoutListener == null)
            throw new IllegalStateException("Class does not implement LogoutListener!");

        switch (getAuthType()) {
            case Google:
                logoutListener.onLogoutStarted();
                googleSignInClient.signOut();
                doFirebaseLogout();
                break;
            case Facebook:
                logoutListener.onLogoutStarted();
                LoginManager.getInstance().logOut();
                doFirebaseLogout();
                break;
            case Twitter:
                logoutListener.onLogoutStarted();
                TwitterCore.getInstance().getSessionManager().clearActiveSession();
                doFirebaseLogout();
                break;
        }
    }


    @Override
    public void addAuthStateListener() {
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    public void removeAuthStateListener() {
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void handleLoginResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FireAuth.REQUEST_GOOGLE_LOGIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                doFirebaseLogin(AuthType.Google, account.getIdToken(), null);
            } catch (ApiException e) {
                loginListener.onLoginCompleted(false);
            }
        } else if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (Twitter.getInstance().getTwitterAuthConfig().getRequestCode() == requestCode) {
            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

    }


    private void doFirebaseLogin(AuthType authType, String token, String secret) {
        loginListener.onLoginStarted();

        AuthCredential credential;
        switch (authType) {
            case Google:
                credential = GoogleAuthProvider.getCredential(token, null);
                break;
            case Facebook:
                credential = FacebookAuthProvider.getCredential(token);
                break;
            case Twitter:
                credential = TwitterAuthProvider.getCredential(token, secret);
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

    private void doFirebaseLogout() {
        FirebaseAuth.getInstance().signOut();
        logoutListener.onLogoutCompleted();
    }


    public static class Initializer {

        private GoogleSignInClient googleSignInClient;
        private CallbackManager fbCallbackManager;
        private TwitterAuthClient twitterAuthClient;

        public Initializer() {
        }

        public Initializer google(String googleWebClientId) {
            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(googleWebClientId)
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(FireDroid.appContext(), gso);

            return this;
        }

        public Initializer facebook(String fbAppId) {
            FacebookSdk.setApplicationId(fbAppId);
            FacebookSdk.sdkInitialize(FireDroid.appContext());

            fbCallbackManager = CallbackManager.Factory.create();

            return this;
        }

        public Initializer twitter(String twitterKey, String twitterSecret) {
            TwitterAuthConfig twitterAuthConfig
                    = new TwitterAuthConfig(twitterKey, twitterSecret);

            TwitterConfig twitterConfig = new TwitterConfig
                    .Builder(FireDroid.appContext())
                    .twitterAuthConfig(twitterAuthConfig)
                    .build();

            Twitter.initialize(twitterConfig);

            twitterAuthClient = new TwitterAuthClient();

            return this;
        }

        public void init() {
            FireAuth auth = new FireAuth(this);
            FireDroid.setAuth(auth);
        }

    }

}
