package worldline.ssm.rd.ux.wltwitter.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.http.ImageLoadTask;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public class TweetAdapter extends RecyclerView.Adapter<TweetHolder> {

    List<Tweet> listTweets;

    public TweetAdapter(List<Tweet> tweets){
        listTweets = tweets;
    }

    @Override
    public TweetHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(WLTwitterApplication.getContext()).inflate(R.layout.items_tweet, parent, false);
        TweetHolder holder = new TweetHolder(v, listTweets);
        return holder;
    }

    @Override
    public void onBindViewHolder(TweetHolder tweetHolder, int position) {
        if (position < getItemCount()) {
            final Tweet tweet = listTweets.get(position);
            if (tweet != null) {
                if (tweetHolder.username != null) {
                    tweetHolder.username.setText(tweet.user.name);
                }

                if (tweetHolder.alias != null) {
                    tweetHolder.alias.setText("(@" + tweet.user.screenName + ")");
                }

                if (tweetHolder.content != null) {
                    tweetHolder.content.setText(tweet.text);
                }

                if (tweetHolder.picture != null) {
                    new ImageLoadTask(tweetHolder.picture).execute(tweet.user.profileImageUrl);
                }

                if (tweetHolder.retweet != null) {
                    tweetHolder.retweet.setText("RT");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return listTweets.size();
    }
}
