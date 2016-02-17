package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                //TODO check mail and password from database

                startActivity(new Intent(LogIn.this, Home.class));
            }
        });
    }

    public void goToRegister(View view) {
        startActivity(new Intent(LogIn.this, Register.class));
    }
}
