package worldline.ssm.rd.ux.wltwitter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import worldline.ssm.rd.ux.wltwitter.fragments.TweetFragment;
import worldline.ssm.rd.ux.wltwitter.fragments.TweetsFragment;
import worldline.ssm.rd.ux.wltwitter.listeners.ButtonListener;
import worldline.ssm.rd.ux.wltwitter.listeners.ClickListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;


public class WLTwitterActivity extends Activity implements ClickListener, ButtonListener {

    TweetsFragment tweetsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String login = getIntent().getExtras().getString("login");
        getActionBar().setSubtitle(login);

        tweetsFragment = new TweetsFragment();
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

        switch (id) {
            case R.id.action_logout:
                SharedPreferences prefs = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
                prefs.edit().remove("login").commit();
                prefs.edit().remove("pwd").commit();
                prefs.edit().remove("remember").commit();
                finish();
                break;
            case R.id.action_refresh:
                tweetsFragment.onRefresh();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTweetClicked(Tweet tweet) {
        TweetFragment fragment = TweetFragment.newInstance(tweet);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(tweetsFragment);
        transaction.add(R.id.main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onRTButtonClicked(Tweet tweet) {
        Toast.makeText(this, "RT " + tweet.text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStarButtonClicked(Tweet tweet) {
        Toast.makeText(this, tweet.text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReplyButtonClicked(Tweet tweet) {
        Toast.makeText(this, tweet.text, Toast.LENGTH_SHORT).show();
    }


}