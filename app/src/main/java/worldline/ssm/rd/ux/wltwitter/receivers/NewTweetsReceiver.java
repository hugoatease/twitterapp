package worldline.ssm.rd.ux.wltwitter.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import worldline.ssm.rd.ux.wltwitter.helpers.NotificationHelper;
import worldline.ssm.rd.ux.wltwitter.utils.Constants;


public class NewTweetsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final int nbNewTweets = intent.getExtras().getInt(Constants.General.ACTION_NEW_TWEETS_EXTRA_NB_TWEETS);

        Log.d("onReceive, nbTweets", Integer.toString(nbNewTweets));
        if(nbNewTweets > 0){
            NotificationHelper.displayNotification(nbNewTweets);
        }
    }
}
