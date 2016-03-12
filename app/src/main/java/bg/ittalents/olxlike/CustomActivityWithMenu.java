package bg.ittalents.olxlike;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import model.UserSessionManager;

/**
 * Created by Ivan on 12-Mar-16.
 */
public class CustomActivityWithMenu extends AppCompatActivity {

    protected UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new UserSessionManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);

        MenuManager manager = new MenuManager();
        manager.setMenuButtons(menu, this.getClass(), session);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuManager manager = new MenuManager();

        Class goingToClass = manager.onItemClick(item, session);

        if(goingToClass == null)
            session.logoutUser();
        else
            startActivity(new Intent(this, goingToClass));

        return true;
    }
}
