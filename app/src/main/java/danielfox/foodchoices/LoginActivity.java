package danielfox.foodchoices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class LoginActivity extends Activity {

    Button login, signUp;
    EditText usernameInput, passwordInput;
    String username, password;
    User currentUser;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initControls();
    }

    public void initControls() {
        login = (Button) findViewById(R.id.loginButton);
        signUp = (Button) findViewById(R.id.signUpButton);
        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);
        database = DatabaseHelper.getInstance(getApplicationContext());
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getApplicationContext(), HomePage.class);
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                currentUser = database.findUser(username, password);
                if (currentUser.getFirstName() != null) {
                    startNewActivity.putExtra("userID", currentUser.getUserID());
                    startActivity(startNewActivity);
                    finish();
                } else {
                Toast.makeText(getApplicationContext(), "Incorrect Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getApplicationContext(), SignUp.class);
                startActivity(startNewActivity);
            }
        });
    }


}
