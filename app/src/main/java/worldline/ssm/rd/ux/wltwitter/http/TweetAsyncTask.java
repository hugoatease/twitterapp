package worldline.ssm.rd.ux.wltwitter.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.fragments.TweetListener;
import worldline.ssm.rd.ux.wltwitter.helpers.TwitterHelper;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

import java.util.List;

public class TweetAsyncTask extends AsyncTask<String, Integer, List<Tweet>> {
    private TweetListener listener;

    public TweetAsyncTask(TweetListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Tweet> doInBackground(String... params) {
        if (params[0] == null) {
            return null;
        }
        else {
            return TwitterHelper.getTweetsOfUser(params[0]);
        }
    }

    @Override
    protected void onPostExecute(List<Tweet> tweets) {
        super.onPostExecute(tweets);
        for (Tweet tweet: tweets) {
            Log.d("TweetAsyncTask", tweet.text);
        }
        listener.onTweetsRetrieved(tweets);
    }
}
