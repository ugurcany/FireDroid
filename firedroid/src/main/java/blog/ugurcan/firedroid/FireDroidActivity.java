package blog.ugurcan.firedroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import blog.ugurcan.firedroid.auth.AuthStateListener;
import blog.ugurcan.firedroid.auth.LoginListener;
import blog.ugurcan.firedroid.auth.LogoutListener;

/**
 * Created by ugurcan on 28.12.2017.
 */
public abstract class FireDroidActivity extends AppCompatActivity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getName(), "onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(getName(), "onStart()");

        if(this instanceof LoginListener){
            FireDroid._auth().setLoginListener((LoginListener) this);
        }
        if(this instanceof LogoutListener){
            FireDroid._auth().setLogoutListener((LogoutListener) this);
        }
        if(this instanceof AuthStateListener){
            FireDroid._auth().setAuthStateListener((AuthStateListener) this);
        }

        FireDroid._auth().addAuthStateListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getName(), "onResume()");

        FireDroid.setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getName(), "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(getName(), "onStop()");

        FireDroid._auth().removeAuthStateListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getName(), "onDestroy()");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FireDroid._auth().handleLoginResult(requestCode, resultCode, data);
    }

    public String getName(){
        return this.getClass().getSimpleName();
    }

    public void showDialog(String message) {
        hideDialog();

        dialog = new ProgressDialog(this);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void hideDialog() {
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }

}
