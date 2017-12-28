package blog.ugurcan.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

import blog.ugurcan.firedroid.FireDroidActivity;

/**
 * Created by ugurcan on 28.12.2017.
 */
public class HomeActivity extends FireDroidActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

}
