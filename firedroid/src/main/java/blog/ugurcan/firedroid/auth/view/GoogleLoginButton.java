package blog.ugurcan.firedroid.auth.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.common.SignInButton;

import blog.ugurcan.firedroid.FireDroid;

/**
 * Created by ugurcan on 30.12.2017.
 */
public class GoogleLoginButton extends FrameLayout implements View.OnClickListener {

    public GoogleLoginButton(@NonNull Context context) {
        this(context, null, 0);
    }

    public GoogleLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoogleLoginButton(@NonNull Context context, @Nullable AttributeSet attrs,
                             @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        SignInButton signInButton = new SignInButton(getContext());
        signInButton.setSize(SignInButton.SIZE_WIDE);
        addView(signInButton);

        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FireDroid.login().withGoogle();
    }

}
