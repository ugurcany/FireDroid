package blog.ugurcan.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import blog.ugurcan.firedroid.FireDroid;
import blog.ugurcan.firedroid.FireDroidActivity;
import blog.ugurcan.firedroid.auth.LogoutListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ugurcan on 28.12.2017.
 */
public class HomeActivity extends FireDroidActivity implements LogoutListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        buttonLogin = menu.findItem(R.id.item_login);
        updateLoginButton();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.equals(buttonLogin)) {
            if (FireDroid.auth().isLoggedIn()) {
                FireDroid.auth().logOut();
            } else {
                startActivity(
                        new Intent(this, LoginActivity.class));
            }
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateContent();
        updateLoginButton();
    }

    @Override
    public void onLogoutStarted() {
        Log.d(getName(), "onLogoutStarted()");
        showDialog("Logging out... Please wait");
    }

    @Override
    public void onLogoutCompleted() {
        Log.d(getName(), "onLogoutCompleted()");
        hideDialog();

        Toast.makeText(this, "Successfully logged out!",
                Toast.LENGTH_SHORT).show();

        updateContent();
        updateLoginButton();
    }

    private void updateContent() {
        Picasso.with(this)
                .load(FireDroid.auth().getUserImageUrl())
                .into(imageviewUser);

        textviewUserDisplayName.setText(FireDroid.auth().getUserDisplayName());
        textviewUserEmail.setText(FireDroid.auth().getUserEmail());
        textviewUserPhone.setText(FireDroid.auth().getUserPhone());
        textviewAuthType.setText(FireDroid.auth().getAuthType().toString());
    }

    private void updateLoginButton() {
        if (buttonLogin != null) {
            if (FireDroid.auth().isLoggedIn()) {
                buttonLogin.setTitle(getString(R.string.menu_item_logout));
            } else {
                buttonLogin.setTitle(getString(R.string.menu_item_login));
            }
        }
    }

    @BindView(R.id.textview_user_display_name)
    TextView textviewUserDisplayName;
    @BindView(R.id.textview_user_email)
    TextView textviewUserEmail;
    @BindView(R.id.textview_user_phone)
    TextView textviewUserPhone;
    @BindView(R.id.textview_auth_type)
    TextView textviewAuthType;
    @BindView(R.id.imageview_user)
    ImageView imageviewUser;

    private MenuItem buttonLogin;

}
