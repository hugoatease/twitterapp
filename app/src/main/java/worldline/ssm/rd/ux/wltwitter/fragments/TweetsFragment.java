package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.http.TweetAsyncTask;
import worldline.ssm.rd.ux.wltwitter.listeners.TweetListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.utils.TweetAdapter;
import java.util.List;

public class TweetsFragment extends Fragment implements TweetListener{
    private View rootView;
    private RecyclerView tweetsView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_tweets, container, false);
        this.tweetsView = (RecyclerView) this.rootView.findViewById(R.id.tweetsListView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(WLTwitterApplication.getContext());
        this.tweetsView.setLayoutManager(layoutManager);
        return this.rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
        String login = prefs.getString("login", "");

      /*  final ProgressBar progressBar = new ProgressBar(getActivity());
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        progressBar.setLayoutParams(layout);
        progressBar.setIndeterminate(true);
        this.tweetsView.setEmptyView(progressBar);
        ((ViewGroup) this.rootView).addView(progressBar);*/

        if (!TextUtils.isEmpty(login)) {
            new TweetAsyncTask(this).execute(login);
        }
    }

    @Override
    public void onTweetsRetrieved(List<Tweet> tweets) {
        TweetAdapter adapter = new TweetAdapter(tweets);
        this.tweetsView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
