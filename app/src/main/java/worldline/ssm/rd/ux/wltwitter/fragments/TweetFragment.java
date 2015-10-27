package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.listeners.ButtonListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;


public class TweetFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private ButtonListener listener;
    private Tweet tweet;

    public static TweetFragment newInstance(Tweet tweet) {
        final TweetFragment tweetFragment = new TweetFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelable(WLTwitterApplication.getContext().getString(R.string.parcelable), tweet);
        tweetFragment.setArguments(arguments);
        return tweetFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.tweet_fragment, container, false);
        load();
        return this.rootView;
    }

    public void load() {
        tweet = getArguments().getParcelable(getString(R.string.parcelable));

        TextView username = (TextView) this.rootView.findViewById(R.id.tweet_user);
        TextView alias = (TextView) this.rootView.findViewById(R.id.tweet_alias);
        TextView content = (TextView) this.rootView.findViewById(R.id.tweet_content);
        ImageView picture = (ImageView) this.rootView.findViewById(R.id.tweet_user_picture);

        ImageButton rtButton = (ImageButton) this.rootView.findViewById(R.id.tweet_retweet);
        ImageButton starButton = (ImageButton) this.rootView.findViewById(R.id.tweet_star);
        ImageButton replyButton = (ImageButton) this.rootView.findViewById(R.id.tweet_reply);

        rtButton.setOnClickListener(this);
        starButton.setOnClickListener(this);
        replyButton.setOnClickListener(this);

        username.setText(tweet.user.name);
        alias.setText("(@" + tweet.user.screenName + ")");
        content.setText(tweet.text);
        Picasso.with(WLTwitterApplication.getContext()).load(tweet.user.profileImageUrl).into(picture);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (ButtonListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onRTButtonClicked, onStarButtonClicked and onReplyButtonClicked.");
        }
    }

    @Override
    public void onClick(View v) {
        ImageButton button = (ImageButton) v;
        switch (button.getId()) {
            case R.id.tweet_reply:
                listener.onReplyButtonClicked(tweet);
                break;
            case R.id.tweet_retweet:
                listener.onRTButtonClicked(tweet);
                break;
            case R.id.tweet_star:
                listener.onStarButtonClicked(tweet);
                break;
            default:
                break;
        }
    }
}
