package blog.ugurcan.app;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import blog.ugurcan.firedroid.FireDroidActivity;

/**
 * Created by ugurcan on 28.12.2017.
 */
public class HomeActivity extends FireDroidActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
                         @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_home);

    }

}
