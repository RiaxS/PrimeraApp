package cr.ac.ucr.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);

        Intent intent;
        if(isLoggedIn){
            intent = new Intent(this, MainActivity.class);
        } else{
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        //se utiliza solo si no se quiere volver a activar esta activity
        finish();
    }
}