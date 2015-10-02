package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.http.TweetAsyncTask;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

import java.util.List;

public class TweetsFragment extends Fragment implements TweetListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweets, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
        String login = prefs.getString("login", "");
        new TweetAsyncTask(this).execute(login);
    }

    @Override
    public void onTweetsRetrieved(List<Tweet> tweets) {

    }
}
