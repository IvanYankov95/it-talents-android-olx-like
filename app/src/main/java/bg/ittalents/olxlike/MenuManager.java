package bg.ittalents.olxlike;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import model.UserSessionManager;

/**
 * Created by Ivan on 11-Mar-16.
 */
public class MenuManager extends Activity{

    public Class onItemClick(MenuItem item, UserSessionManager session){

        switch (item.getItemId()) {
            case R.id.action_add_offer:
                return AddOffer.class;
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
