package worldline.ssm.rd.ux.wltwitter.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.listeners.ClickListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public class TweetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView picture;
    public TextView username;
    public TextView alias;
    public TextView content;
    public Button retweet;

    public ClickListener clickListener;
    private List<Tweet> listTweets;

    public TweetHolder(View itemView, List<Tweet> tweets)/* ClickListener listener*/ {
        super(itemView);
        username = (TextView) itemView.findViewById(R.id.user);
        content = (TextView) itemView.findViewById(R.id.content_tweet);
        alias = (TextView) itemView.findViewById(R.id.screenName);
        picture = (ImageView) itemView.findViewById(R.id.user_picture);
        retweet = (Button) itemView.findViewById(R.id.retweet);

      //  clickListener = listener;
        listTweets = tweets;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        clickListener.onTweetClicked(listTweets.get(getPosition()));
    }
}
