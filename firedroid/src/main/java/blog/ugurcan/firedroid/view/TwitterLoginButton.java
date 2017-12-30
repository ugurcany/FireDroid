package blog.ugurcan.firedroid.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import blog.ugurcan.firedroid.FireDroid;
import blog.ugurcan.firedroid.R;

/**
 * Created by ugurcan on 30.12.2017.
 */
public class TwitterLoginButton extends com.twitter.sdk.android.core.identity.TwitterLoginButton {

    public TwitterLoginButton(@NonNull Context context) {
        this(context, null, 0);
    }

    public TwitterLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwitterLoginButton(@NonNull Context context, @Nullable AttributeSet attrs,
                              @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setTextSize(TypedValue.COMPLEX_UNIT_SP,
                getContext().getResources().getInteger(R.integer.login_button_textsize));
        setGravity(Gravity.CENTER);

        FireDroid.auth().logInTwitter(this);
    }

}
