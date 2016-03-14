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

public class EditPersonalInfo extends CustomActivityWithMenu {

    private static Button editPersonalInfo;
    private static EditText fname;
    private static EditText lname;
    private static EditText phone;
    private static EditText city;
    private static EditText address;
    private long id;
    private UserAcc user;

    UserManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);
        manager = UserManager.getInstance(this);
        id = Long.parseLong(session.getUserDetails().get(session.KEY_ID));
        user = manager.getUser(id);

        fname = (EditText) findViewById(R.id.edit_personal_fname);
        lname = (EditText) findViewById(R.id.edit_personal_lname);
        phone = (EditText) findViewById(R.id.edit_personal_phone);
        city = (EditText) findViewById(R.id.edit_personal_city);
        address = (EditText) findViewById(R.id.edit_personal_address);
        editPersonalInfo = (Button)findViewById(R.id.edit_personal_info_save_button);
        fillData(user);
    }

   public void updateInfo(View v){
       String firstNameTxt = fname.getText().toString();
       String lastNameTxt = lname.getText().toString();
       String cityTxt = city.getText().toString();
       String addressTxt = address.getText().toString();
       String phoneTxt = phone.getText().toString();

       long result = manager.updatePersonalInfo(id, firstNameTxt, lastNameTxt, phoneTxt, cityTxt, addressTxt);
       Toast.makeText(EditPersonalInfo.this, (result == 1) ? "Edit successful" : "Edit fail", Toast.LENGTH_SHORT).show();
   }

    private void fillData(UserAcc user){
        fname.setText(user.getFirstName());
        lname.setText(user.getLastName());
        phone.setText(user.getPhoneNumber());
        city.setText(user.getCity());
        address.setText(user.getAddress());
    }

}
