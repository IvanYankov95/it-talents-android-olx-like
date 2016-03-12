package bg.ittalents.olxlike;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import model.UserSessionManager;

/**
 * Created by Ivan on 11-Mar-16.
 */
public class MenuManager{

    public void setMenuButtons(Menu menu,Class c, UserSessionManager session){
        MenuItem addOffer = menu.findItem(R.id.action_add_offer);
        MenuItem home = menu.findItem(R.id.action_home);
        MenuItem messages = menu.findItem(R.id.action_messages);
        MenuItem profile = menu.findItem(R.id.action_profile);
        MenuItem settings = menu.findItem(R.id.action_settings);
        MenuItem logout = menu.findItem(R.id.action_logout);

        if(!session.isUserLoggedIn()) {
            messages.setVisible(false);
            profile.setVisible(false);
            settings.setVisible(false);
            logout.setVisible(false);
        }

        if(c.equals(AddOffer.class))
            addOffer.setVisible(false);

        if(c.equals(Home.class))
            home.setVisible(false);

        if(c.equals(MyMessages.class))
            messages.setVisible(false);

        //TODO когато оправим да можеш да си виждаш твоя профил, когато си в него да махнем бутона profile от менюто

        if(c.equals(Settings.class))
            settings.setVisible(false);
    }

    public Class onItemClick(MenuItem item, UserSessionManager session){

        switch (item.getItemId()) {
            case R.id.action_add_offer:
                if(session.isUserLoggedIn())
                    return AddOffer.class;
                else
                    return LogIn.class;
            case R.id.action_home:
                return Home.class;
            case R.id.action_messages:
                return MyMessages.class;
            case R.id.action_profile:
                //TODO да се направи да праща към твоя профил ...
                return ViewUser.class;
            case R.id.action_settings:
                return Settings.class;
            case R.id.action_logout:
                return null;
            default:
                return null;
        }
    }
}
