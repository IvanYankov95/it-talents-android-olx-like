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
import android.widget.TextView;
import android.widget.Toast;

import model.Message;
import model.UserAcc;
import model.UserManager;
import model.UserSessionManager;

public class SendMessage extends CustomActivityWithMenu {

    private static Button sendMessage;
    private Bundle bundle;
    private long userId;
    private long receiverId;
    private String receiverUsername;
    private String offerTitle;
    private String descriptionTxt;

    private TextView receiver;
    private EditText title;
    private EditText description;

    UserManager manager = UserManager.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        bundle = getIntent().getExtras();
        receiverId = bundle.getLong("id");
        receiverUsername = bundle.getString("Username");
        userId = Long.parseLong(session.getUserDetails().get(session.KEY_ID));
        offerTitle = bundle.getString("Title");

        receiver = (TextView) findViewById(R.id.send_message_receiver);
        receiver.setText(receiverUsername);
        title = (EditText) findViewById(R.id.send_message_title);
        title.setText(offerTitle);
        description = (EditText) findViewById(R.id.send_message_description);

        sendMessage = (Button)findViewById(R.id.send_message_button_send);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionTxt = description.getText().toString();
                String titleTxt = title.getText().toString();
                Message msg = new Message(userId, receiverId, titleTxt, descriptionTxt);
                long result = manager.sendMessage(msg);
                Toast.makeText(SendMessage.this, (result != -1 ? "Message sent" : "Send fails"), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SendMessage.this, MyMessages.class);
                startActivity(intent);

            }
        });
    }

}
