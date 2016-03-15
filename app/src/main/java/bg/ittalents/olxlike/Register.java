package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.UserAcc;
import model.UserManager;
import model.UserSessionManager;

public class Register extends CustomActivityWithMenu {

    private UserManager userManager;

    private static Button register;

    private static EditText username;
    private static EditText password;
    private static EditText confirmPassword;
    private static EditText email;
    private static EditText firstName;
    private static EditText lastName;
    private static EditText city;
    private static EditText address;
    private static EditText phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userManager = UserManager.getInstance(this);

        register = (Button) findViewById(R.id.registerButton);

        username        = (EditText) findViewById(R.id.usernameField);
        password        = (EditText) findViewById(R.id.passwordField);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordField);
        email           = (EditText) findViewById(R.id.emailField);
        firstName       = (EditText) findViewById(R.id.firstNameField);
        lastName        = (EditText) findViewById(R.id.lastNameField);
        city         = (EditText) findViewById(R.id.cityField);
        address         = (EditText) findViewById(R.id.addressField);
        phone           = (EditText) findViewById(R.id.phoneField);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameTxt = username.getText().toString();
                String passwordTxt = password.getText().toString();
                String emailTxt = email.getText().toString();
                String firstNameTxt = firstName.getText().toString();
                String lastNameTxt = lastName.getText().toString();
                String cityTxt = city.getText().toString();
                String addressTxt = address.getText().toString();
                String phoneTxt = phone.getText().toString();

                boolean usernameCheck = false;
                boolean emailCheck = false;
                boolean passwordCheck = false;
                boolean passwordMatch = false;

                if(userManager.existUsername(usernameTxt)){
                    username.setError("Username already exists");
                }
                else if(usernameTxt.isEmpty()){
                        username.setError("This field is required");
                } else
                    usernameCheck = true;

                if (userManager.existEmail(emailTxt)){
                    email.setError("Email already exists");
                }
                else if(emailTxt.isEmpty()){
                    email.setError("This field is required");
                }
                else if(!isEmailValid(emailTxt)){
                    email.setError("Please enter a valid email");
                }
                else
                    emailCheck = true;

                if(password.getText().toString().isEmpty()) {
                   password.setError("This field is required.");
                } else {
                    if (!userManager.checkPasswordStrength(password.getText().toString())) {
                        password.setError("Password is too weak\n At least 8 symbols \n At least 1 lowercase and uppercase \n At least 1 number");
                    } else
                        passwordCheck = true;

                    if (userManager.checkPasswordStrength(password.getText().toString()) && !password.getText().toString().equals(confirmPassword.getText().toString())) {
                        confirmPassword.setError("Passwords don't match");
                    } else
                        passwordMatch = true;
                }

                if(usernameCheck && emailCheck && passwordCheck && passwordMatch){
                    UserAcc user = new UserAcc(emailTxt, passwordTxt, usernameTxt, firstNameTxt, lastNameTxt, phoneTxt, cityTxt, addressTxt);
                    long id = userManager.register(user);
                    if(id != -1)
                        Toast.makeText(getApplicationContext(), "register successful" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, LogIn.class));
                }
            }
        });
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
