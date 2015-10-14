package worldline.ssm.rd.ux.wltwitter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.listeners.ButtonListener;
import worldline.ssm.rd.ux.wltwitter.listeners.ClickListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;


public class TweetHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView picture;
    public TextView username;
    public TextView alias;
    public TextView content;
    public Button retweet;

    private View view;
    private ClickListener clickListener;
    private Tweet tweet;

    public TweetHolder(View itemView) {
        super(itemView);
        view = itemView;
        username = (TextView) itemView.findViewById(R.id.user);
        content = (TextView) itemView.findViewById(R.id.content_tweet);
        alias = (TextView) itemView.findViewById(R.id.screenName);
        picture = (ImageView) itemView.findViewById(R.id.user_picture);
        retweet = (Button) itemView.findViewById(R.id.retweet);

        retweet.setOnClickListener(this);
    }


    public void setView(Tweet tweet, ClickListener listener){
        this.clickListener = listener;
        this.tweet = tweet;
        view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v instanceof Button){
            ButtonListener btnlistener = (ButtonListener) clickListener;
            btnlistener.onRTButtonClicked(tweet);
        } else {
            clickListener.onTweetClicked(tweet);
        }
    }
}
