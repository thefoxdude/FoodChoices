package danielfox.foodchoices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;

public class SignUp extends Activity {
    Button cancel, signUp;
    TextView firstName, lastName, username, password, confirmPassword;
    DatabaseHelper database;
    User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initControls();
    }

    private void initControls() {
        firstName = (TextView) findViewById(R.id.firstNameField);
        lastName = (TextView) findViewById(R.id.lastNameField);
        username = (TextView) findViewById(R.id.usernameField);
        password = (TextView) findViewById(R.id.passwordField);
        confirmPassword = (TextView) findViewById(R.id.passwordConfirmField);
        cancel = (Button) findViewById(R.id.signUpCancel);
        signUp = (Button) findViewById(R.id.signUpConfirm);
        database = DatabaseHelper.getInstance(getApplicationContext());
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startNewActivity);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.createUser(firstName.getText().toString(),
                                    lastName.getText().toString(),
                                    username.getText().toString(),
                                    password.getText().toString());
                Intent startNewActivity = new Intent(getApplicationContext(), HomePage.class);
                newUser = database.findUser(username.getText().toString(), password.getText().toString());
                startNewActivity.putExtra("userID", newUser.getUserID());
                startActivity(startNewActivity);
                finish();
            }
        });
    }


}
