package blog.ugurcan.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

        FireDroid.auth().setLogoutListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                FireDroid.auth().logOut();
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    @Override
    public String getName() {
        return HomeActivity.class.getSimpleName();
    }

    @Override
    public void onLogoutStarted() {
        showDialog("Logging out... Please wait");
    }

    @Override
    public void onLogoutCompleted() {
        hideDialog();
        Toast.makeText(this, "Successfully logged out!",
                Toast.LENGTH_SHORT).show();
    }

    private void updateUI(){
        Picasso.with(this)
                .load(FireDroid.auth().getUserImageUrl())
                .into(imageviewUser);

        textviewUserDisplayName.setText(FireDroid.auth().getUserDisplayName());
        textviewUserEmail.setText(FireDroid.auth().getUserEmail());
        textviewUserPhone.setText(FireDroid.auth().getUserPhone());
        textviewAuthType.setText(FireDroid.auth().getAuthType().toString());
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

}
