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

import model.UserManager;
import model.dao.DBUserDAO;

public class LogIn extends AppCompatActivity {

    private static Button signIn;

    private static EditText email;
    private static EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

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
                UserManager manager = UserManager.getInstance(LogIn.this);
                if(manager.login(emailText, passwordText) == null){
                    Toast.makeText(LogIn.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LogIn.this, "Login sucssessfull!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogIn.this, Home.class));
                }
            }
        });
    }

    public void goToRegister(View view) {
        startActivity(new Intent(LogIn.this, Register.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_offer:
                startActivity(new Intent(LogIn.this, AddOffer.class));
                break;
            case R.id.action_home:
                startActivity(new Intent(LogIn.this, Home.class));
                break;
            case R.id.action_messages:
                startActivity(new Intent(LogIn.this, MyMessages.class));
                break;
            case R.id.action_profile:
                //TODO да се направи да праща към твоя профил ...
                startActivity(new Intent(LogIn.this, ViewUser.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(LogIn.this, Settings.class));
                break;
            case R.id.action_logout:
                //TODO да те логаутва...
                break;
            default:
                break;
        }

        return true;
    }

}
