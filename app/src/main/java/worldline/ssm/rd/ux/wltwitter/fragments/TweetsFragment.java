package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.http.TweetAsyncTask;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

import java.util.List;

public class TweetsFragment extends Fragment implements TweetListener {
    private ListView tweetsView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets, container, false);
        this.tweetsView = (ListView) view.findViewById(R.id.tweetsListView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
        String login = prefs.getString("login", "");
        if (!TextUtils.isEmpty(login)) {
            new TweetAsyncTask(this).execute(login);
        }
    }

    @Override
    public void onTweetsRetrieved(List<Tweet> tweets) {
        final ArrayAdapter<Tweet> adapter = new ArrayAdapter<Tweet>(getActivity(), android.R.layout.simple_list_item_1, tweets);
        this.tweetsView.setAdapter(adapter);
    }
}
