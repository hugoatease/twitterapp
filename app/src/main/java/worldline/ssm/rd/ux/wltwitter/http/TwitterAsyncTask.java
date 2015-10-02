package worldline.ssm.rd.ux.wltwitter.http;

import android.content.Context;
import android.os.AsyncTask;
import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.helpers.TwitterHelper;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

import java.util.List;

public class TwitterAsyncTask extends AsyncTask<String, Integer, List<Tweet>> {
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
            String appName = WLTwitterApplication.getContext().getString(R.string.app_name);
            System.out.println("[" + appName + "] " + tweet.text);
        }
    }
}
