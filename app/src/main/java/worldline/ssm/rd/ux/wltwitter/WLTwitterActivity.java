package worldline.ssm.rd.ux.wltwitter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import worldline.ssm.rd.ux.wltwitter.fragments.TweetFragment;
import worldline.ssm.rd.ux.wltwitter.fragments.TweetsFragment;
import worldline.ssm.rd.ux.wltwitter.listeners.ButtonListener;
import worldline.ssm.rd.ux.wltwitter.listeners.ClickListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.receivers.NewTweetsReceiver;
import worldline.ssm.rd.ux.wltwitter.services.TweetService;
import worldline.ssm.rd.ux.wltwitter.utils.Constants;


public class WLTwitterActivity extends Activity implements ClickListener, ButtonListener {

    TweetsFragment tweetsFragment;
    PendingIntent mServicePendingIntent;
    NewTweetsReceiver mReceiver;

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
    public void onResume() {
        super.onResume();

        final Calendar cal = Calendar.getInstance();
        final Intent serviceIntent = new Intent(this, TweetService.class);
        mServicePendingIntent = PendingIntent.getService(this, 0, serviceIntent, 0);
        final AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), Constants.Twitter.POLLING_DELAY, mServicePendingIntent);

        mReceiver = new NewTweetsReceiver();
        registerReceiver(mReceiver, new IntentFilter(Constants.General.ACTION_NEW_TWEETS));

        Bundle extras = new Bundle();
        extras.putString("login", getIntent().getExtras().getString("login"));
        serviceIntent.putExtras(extras);
        startService(serviceIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        final AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(mServicePendingIntent);
        unregisterReceiver(mReceiver);
        mReceiver = null;
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
                Toast.makeText(getApplicationContext(), getString(R.string.refresh_action), Toast.LENGTH_LONG).show();
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


    public void onClickPrevious(View v){
        onBackPressed();
    }

}