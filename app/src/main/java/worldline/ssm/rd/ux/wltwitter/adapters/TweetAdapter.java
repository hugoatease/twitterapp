package worldline.ssm.rd.ux.wltwitter.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseManager;
import worldline.ssm.rd.ux.wltwitter.listeners.ClickListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.view.TweetHolder;

public class TweetAdapter extends CursorRecyclerViewAdapter<TweetHolder> implements ClickListener {

    ClickListener clickListener;

    public TweetAdapter(Context context, Cursor c, ClickListener listener){
        super(context, c);
        clickListener = listener;
    }

    @Override
    public TweetHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_tweet, parent, false);
        return new TweetHolder(v);
    }

    @Override
    public void onBindViewHolder(TweetHolder viewHolder, Cursor cursor) {
        final Tweet tweet = WLTwitterDatabaseManager.tweetFromCursor(cursor);

        viewHolder.username.setText(tweet.user.name);
        viewHolder.alias.setText("(@" + tweet.user.screenName + ")");
        viewHolder.content.setText(tweet.text);
        Picasso.with(WLTwitterApplication.getContext()).load(tweet.user.profileImageUrl).into(viewHolder.picture);

        viewHolder.setView(tweet, clickListener);
    }

    @Override
    public void onTweetClicked(Tweet tweet) {

    }
}
