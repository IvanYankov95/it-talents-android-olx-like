package bg.ittalents.olxlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import model.Message;
import model.UserManager;
import model.UserSessionManager;

public class ViewMessage extends CustomActivityWithMenu {

    TextView from;
    TextView to;
    TextView title;
    TextView content;
    UserManager manager;
    Bundle bundle;
    Message m;

    private static Button replyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);
        bundle = getIntent().getExtras();

        manager = UserManager.getInstance(this);
        m = manager.getMessage(bundle.getLong("msgId"));

        from = (TextView) findViewById(R.id.view_message_from);
        to = (TextView) findViewById(R.id.view_message_to);
        title = (TextView) findViewById(R.id.view_message_title);
        content = (TextView) findViewById(R.id.view_message_content);

        from.setText(from.getText().toString() + manager.getUser(m.getSenderId()).getUserName());
        to.setText(to.getText().toString() + manager.getUser(m.getReceiverId()).getUserName());
        title.setText(m.getHeading());
        content.setText(m.getText());


        replyMessage = (Button)findViewById(R.id.view_message_reply_button);
        replyMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
