package worldline.ssm.rd.ux.wltwitter.services;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseManager;
import worldline.ssm.rd.ux.wltwitter.http.TweetAsyncTask;
import worldline.ssm.rd.ux.wltwitter.listeners.TweetListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.utils.Constants;

public class TweetService extends Service implements TweetListener {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TweetService", "Start");
        String login = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE).getString("login", "");

        if(login != ""){
            new TweetAsyncTask(this).execute(login);
        }

        refreshLayoutStartBroadcast();

        return Service.START_NOT_STICKY;
    }

    private void refreshLayoutStartBroadcast(){
        final Intent startServiceIntent = new Intent(Constants.General.ACTION_SERVICE_STARTED);
        sendBroadcast(startServiceIntent);
    }

    private void refreshLayoutStopBroadcast(){
        final Intent stopServiceIntent = new Intent(Constants.General.ACTION_SERVICE_STOPPED);
        sendBroadcast(stopServiceIntent);
    }

    @Override
    public void onTweetsRetrieved(List<Tweet> tweets) {
        int nbTweetsInserted = WLTwitterDatabaseManager.testContentProvider(tweets);
        stopSelf();
        Log.d("TweetService", "Stop");

        final Intent newTweetsIntent = new Intent(Constants.General.ACTION_NEW_TWEETS);
        final Bundle extras = new Bundle();
        extras.putInt(Constants.General.ACTION_NEW_TWEETS_EXTRA_NB_TWEETS, nbTweetsInserted);
        newTweetsIntent.putExtras(extras);
        sendBroadcast(newTweetsIntent);
        refreshLayoutStopBroadcast();
    }
}
