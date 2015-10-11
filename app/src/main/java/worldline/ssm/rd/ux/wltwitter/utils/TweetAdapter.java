package worldline.ssm.rd.ux.wltwitter.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.http.ImageLoadTask;
import worldline.ssm.rd.ux.wltwitter.listeners.ButtonListener;
import worldline.ssm.rd.ux.wltwitter.listeners.ClickListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public class TweetAdapter extends RecyclerView.Adapter<TweetHolder> {

    List<Tweet> listTweets;
    ClickListener clickListener;

    public TweetAdapter(List<Tweet> tweets, ClickListener listener){
        listTweets = tweets;
        clickListener = listener;
    }

    @Override
    public TweetHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_tweet, parent, false);
        return new TweetHolder(v);
    }

    @Override
    public void onBindViewHolder(TweetHolder tweetHolder, int position) {
        if (position < getItemCount()) {
            final Tweet tweet = listTweets.get(position);
            tweetHolder.setView(tweet, clickListener);
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
                    Picasso.with(WLTwitterApplication.getContext()).load(tweet.user.profileImageUrl).into(tweetHolder.picture);
                }

                if (tweetHolder.retweet != null) {
                    tweetHolder.retweet.setText(R.string.short_retweet);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if(listTweets == null){
          return 0;
        }

        return listTweets.size();
    }
}
