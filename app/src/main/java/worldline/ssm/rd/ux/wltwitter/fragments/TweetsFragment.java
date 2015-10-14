package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterActivity;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.http.TweetAsyncTask;
import worldline.ssm.rd.ux.wltwitter.listeners.TweetListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.view.TweetAdapter;

public class TweetsFragment extends Fragment implements TweetListener, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout rootView;
    private RecyclerView tweetsView;
    private String login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_tweets, container, false);
        this.tweetsView = (RecyclerView) this.rootView.findViewById(R.id.tweetsListView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(WLTwitterApplication.getContext());
        this.tweetsView.setLayoutManager(layoutManager);
        this.rootView.setOnRefreshListener(this);
        return this.rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
        login = prefs.getString("login", "");

        if (!TextUtils.isEmpty(login)) {
            this.rootView.post(new Runnable() {
                @Override
                public void run() {
                    rootView.setRefreshing(true);
                }
            });
            new TweetAsyncTask(this).execute(login);
        }
    }


    @Override
    public void onTweetsRetrieved(List<Tweet> tweets) {
        TweetAdapter adapter = new TweetAdapter(tweets, (WLTwitterActivity) getActivity());
        this.tweetsView.setAdapter(adapter);
        this.rootView.setRefreshing(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onRefresh() {
        if (!TextUtils.isEmpty(login)) {
            new TweetAsyncTask(this).execute(login);
        }
    }
}
