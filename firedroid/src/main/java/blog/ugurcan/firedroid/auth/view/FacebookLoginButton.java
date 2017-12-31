package blog.ugurcan.firedroid.auth.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import com.facebook.login.widget.LoginButton;

import blog.ugurcan.firedroid.FireDroid;
import blog.ugurcan.firedroid.R;

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
        int padding = padding();
        setPadding(padding, padding, padding, padding);
        setTextSize(TypedValue.COMPLEX_UNIT_SP,
                getContext().getResources().getInteger(R.integer.login_button_textsize));
        setGravity(Gravity.CENTER);

        setReadPermissions("email", "public_profile");
        FireDroid.login().withFacebook(this);
    }

    private int padding(){
        return (int) (getResources().getInteger(R.integer.login_button_padding)
                * getResources().getDisplayMetrics().density);
    }

}
