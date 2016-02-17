package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    private static Button register;

    private static EditText username;
    private static EditText password;
    private static EditText confirmPassword;
    private static EditText email;
    private static EditText firstName;
    private static EditText lastName;
    private static EditText address;
    private static EditText phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button) findViewById(R.id.registerButton);

        username        = (EditText) findViewById(R.id.usernameField);
        password        = (EditText) findViewById(R.id.passwordField);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordField);
        email           = (EditText) findViewById(R.id.emailField);
        firstName       = (EditText) findViewById(R.id.firstNameField);
        lastName        = (EditText) findViewById(R.id.lastNameField);
        address         = (EditText) findViewById(R.id.addressField);
        phone           = (EditText) findViewById(R.id.phoneField);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean usernameCheck = true;
                boolean emailCheck = true;
                boolean passwordCheck = false;
                boolean passwordMatch = false;

                //TODO check if username already exists in database!
                //TODO check if email already exists in database!

                if(password.getText().toString().isEmpty()) {
                   password.setError("This field is required.");
                } else {
                    if (!checkPasswordStrength(password.getText().toString())) {
                        password.setError("Password is too weak\n At least 8 symbols \n At least 1 lowercase and uppercase \n At least 1 number");
                    } else
                        passwordCheck = true;

                    if (checkPasswordStrength(password.getText().toString()) && !password.getText().toString().equals(confirmPassword.getText().toString())) {
                        confirmPassword.setError("Passwords don't match");
                    } else
                        passwordMatch = true;
                }

                if(usernameCheck && emailCheck && passwordCheck && passwordMatch){
                    //TODO register in database and log in
                    startActivity(new Intent(Register.this, LogIn.class));
                }
            }
        });
    }

    public boolean checkPasswordStrength(String password) {
        char[] test = password.toCharArray();

        boolean lowercase = false;
        boolean uppercase = false;
        boolean number = false;

        if(test.length < 8)
            return false;

        for (char aTest : test) {
            if (aTest >= 65 && aTest <= 90)
                lowercase = true;
            if (aTest >= 97 && aTest <= 122)
                uppercase = true;
            if (aTest >= 48 && aTest <= 57)
                number = true;
        }

        return !(!lowercase || !uppercase || !number);

    }
}
