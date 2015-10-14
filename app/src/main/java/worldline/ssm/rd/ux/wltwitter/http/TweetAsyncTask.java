package worldline.ssm.rd.ux.wltwitter.http;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.helpers.TwitterHelper;
import worldline.ssm.rd.ux.wltwitter.listeners.TweetListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public class TweetAsyncTask extends AsyncTask<String, Integer, List<Tweet>> {
    private TweetListener listener;

    public TweetAsyncTask(TweetListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Tweet> doInBackground(String... params) {
        if (TextUtils.isEmpty(params[0])) {
            return null;
        } else {
            return TwitterHelper.getTweetsOfUser(params[0]);
        }
    }

    @Override
    protected void onPostExecute(List<Tweet> tweets) {
        super.onPostExecute(tweets);
        listener.onTweetsRetrieved(tweets);
    }
}
