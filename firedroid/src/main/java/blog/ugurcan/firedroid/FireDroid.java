package blog.ugurcan.firedroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

import blog.ugurcan.firedroid.auth.FireAuth;

/**
 * Created by ugurcan on 28.12.2017.
 */
public class FireDroid {

    private static WeakReference<Context> mContext;
    private static WeakReference<Activity> mCurrentActivity;
    private static Class mLoginActivityClass;

    private static FireAuth auth;

    public static void init(Context appContext, Class loginActivityClass) {
        mContext = new WeakReference<>(appContext);
        mLoginActivityClass = loginActivityClass;
    }

    public static FireAuth auth() {
        if (auth == null) {
            auth = new FireAuth();
        }
        return auth;
    }

    public static Context context() {
        return mContext.get();
    }

    public static void goToLogin() {
        Intent intent = new Intent(FireDroid.currentActivity(), mLoginActivityClass);
        context().startActivity(intent);
    }

    public static boolean isOnLoginActivity() {
        return currentActivity().getClass().equals(mLoginActivityClass);
    }

    public static Activity currentActivity() {
        return mCurrentActivity.get();
    }

    static void setCurrentActivity(Activity activity) {
        nullifyCurrentActivity();
        mCurrentActivity = new WeakReference<>(activity);
    }

    private static void nullifyCurrentActivity() {
        if (mCurrentActivity != null) {
            mCurrentActivity.clear();
            mCurrentActivity = null;
        }
    }

}
