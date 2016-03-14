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
import model.UserSessionManager;

public class EditEmail extends CustomActivityWithMenu {

    private Button editEmail;
    private EditText password;
    private EditText email;
    static private UserManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);

        manager = UserManager.getInstance(this);
        session = new UserSessionManager(this);
        editEmail = (Button)findViewById(R.id.edit_email_save_button);
        password = (EditText) findViewById(R.id.edit_email_password);
        email = (EditText) findViewById(R.id.edit_email_email);



        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String passText = password.getText().toString();
                final String emailText = email.getText().toString();
                if(passText.isEmpty()) {
                    password.setError("This field is required.");
                    return;
                }
                if(emailText.isEmpty()) {
                    email.setError("This field is required.");
                    return;
                }
                long id = Long.parseLong(session.getUserDetails().get(session.KEY_ID));
                    if (manager.checkPassword(id, passText)) {
                        if(!manager.existEmail(emailText)) {
                            long result = manager.updateEmail(id, emailText);
                            if(result ==1) {
                                Toast.makeText(EditEmail.this, "Edit successful", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditEmail.this, Settings.class);
                                startActivity(i);
                            }
                            else
                                Toast.makeText(EditEmail.this, "Edit fail", Toast.LENGTH_LONG).show();
                        }
                        else{
                            email.setError("Email already exists");
                        }
                    }
                    else{
                        password.setError("Wrong password");
                    }
            }
        });
    }

}
