package cr.ac.ucr.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import cr.ac.ucr.app.utilis.AppPreferences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etEmail;
    private EditText etPassword;

    public static String PREFERENCES ="myapp_preferences";
    private SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();

         etEmail = findViewById(R.id.et_email);
         etPassword = findViewById(R.id.et_password);
    }



    private void login(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(email.isEmpty()){
            etEmail.setError(getString(R.string.email_error));
            return;
        }

        if(password.isEmpty()){
            etPassword.setError(getString(R.string.psw_error));
            return;
        }

        if(email.equalsIgnoreCase("admin@email.com")&& "123".equalsIgnoreCase(password)){

            editor.putBoolean("is_logged_in", true);
            editor.commit();

            AppPreferences.getInstance(this).put(AppPreferences.Keys.IS_LOGGED_IN,true);

            Toast.makeText(this, R.string.logged_in, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(this, R.string.no_match, Toast.LENGTH_SHORT).show();
        }

    }

    private void gotoRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_goto_register:
                gotoRegister();
                break;
            default:
                break;
        }
    }
}