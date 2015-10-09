package worldline.ssm.rd.ux.wltwitter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.http.javanet.NetHttpTransport;
import worldline.ssm.rd.ux.wltwitter.http.OauthAsyncTask;
import worldline.ssm.rd.ux.wltwitter.listeners.ClickListener;
import worldline.ssm.rd.ux.wltwitter.fragments.TweetsFragment;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.utils.Constants;

import java.io.IOException;


public class WLTwitterActivity extends Activity implements ClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String login = getIntent().getExtras().getString("login");
        getActionBar().setSubtitle(login);

        TweetsFragment tweetsFragment = new TweetsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.main, tweetsFragment);
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences prefs = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
            prefs.edit().remove("login").commit();
            prefs.edit().remove("pwd").commit();
            prefs.edit().remove("remember").commit();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTweetClicked(Tweet tweet) {
        Toast.makeText(this, tweet.text, Toast.LENGTH_LONG).show();
    }
}
