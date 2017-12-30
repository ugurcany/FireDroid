package blog.ugurcan.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import blog.ugurcan.firedroid.FireDroid;
import blog.ugurcan.firedroid.FireDroidActivity;
import blog.ugurcan.firedroid.auth.LogoutListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ugurcan on 28.12.2017.
 */
public class HomeActivity extends FireDroidActivity implements LogoutListener {

    @BindView(R.id.textview_user)
    TextView textviewUser;

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
        textviewUser.setText(FireDroid.auth().getUserDisplayName() + " / "
                + FireDroid.auth().getAuthType());
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

}
