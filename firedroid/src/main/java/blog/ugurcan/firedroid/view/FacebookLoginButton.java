package blog.ugurcan.firedroid.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.facebook.login.widget.LoginButton;

import blog.ugurcan.firedroid.FireDroid;

/**
 * Created by ugurcan on 30.12.2017.
 */

public class FacebookLoginButton extends LoginButton {

    public FacebookLoginButton(@NonNull Context context) {
        this(context, null, 0);
    }

    public FacebookLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FacebookLoginButton(@NonNull Context context, @Nullable AttributeSet attrs,
                               @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setReadPermissions("email", "public_profile");

        FireDroid.auth().logInFacebook(this);
    }

}
