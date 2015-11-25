package com.example.danielfox.foodchoices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.danielfox.foodchoices.Functions.checkLogin;

public class LoginActivity extends Activity {

    Button login;
    EditText usernameInput, passwordInput;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initControls();
    }

    public void initControls() {
        login = (Button) findViewById(R.id.loginButton);
        usernameInput = (EditText) findViewById(R.id.username);
        username = usernameInput.getText().toString();
        passwordInput = (EditText) findViewById(R.id.password);
        password = passwordInput.getText().toString();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLogin(username, password)) {
                    Intent startNewActivity = new Intent(getApplicationContext(), HomePage.class);
                    username = usernameInput.getText().toString();
                    startNewActivity.putExtra("name", username);
                    startActivity(startNewActivity);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Incorrect Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
