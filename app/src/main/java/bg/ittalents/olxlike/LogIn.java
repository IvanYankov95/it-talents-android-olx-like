package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.UserAcc;
import model.UserManager;
import model.UserSessionManager;
import model.dao.DBUserDAO;

public class LogIn extends CustomActivityWithMenu {

    private static Button signIn;

    private static EditText email;
    private static EditText password;

    UserManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        session = new UserSessionManager(getApplicationContext());
        manager = UserManager.getInstance(LogIn.this);

        signIn = (Button) findViewById(R.id.signInButton);
        email = (EditText) findViewById(R.id.emailField);
        password = (EditText) findViewById(R.id.passwordField);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                if(emailText.isEmpty()) {
                    email.setError("This field is required.");
                    return;
                }
                if(passwordText.isEmpty()){
                    password.setError("This field is required.");
                    return;
                }

                UserAcc user = manager.login(emailText, passwordText);
                if(user == null){
                    Toast.makeText(LogIn.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LogIn.this, "Login sucssessfull!", Toast.LENGTH_SHORT).show();
                    session.createUserLoginSession(user.getUserId(), user.getUserName());
                    startActivity(new Intent(LogIn.this, Home.class));
                }
            }
        });
    }

    public void goToRegister(View view) {
        startActivity(new Intent(LogIn.this, Register.class));
    }

}
