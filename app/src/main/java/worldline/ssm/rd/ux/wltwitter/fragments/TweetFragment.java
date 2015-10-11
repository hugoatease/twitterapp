package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;


public class TweetFragment extends Fragment {
    private View rootView;


    public static TweetFragment newInstance(Tweet tweet){
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

    public void load(){
        final Tweet tweet = getArguments().getParcelable(getString(R.string.parcelable));

        TextView username =  (TextView) this.rootView.findViewById(R.id.tweet_user);
        TextView alias =  (TextView) this.rootView.findViewById(R.id.tweet_alias);
        TextView content =  (TextView) this.rootView.findViewById(R.id.tweet_content);
        ImageView picture = (ImageView) this.rootView.findViewById(R.id.tweet_user_picture);

        username.setText(tweet.user.name);
        alias.setText("(@" + tweet.user.screenName + ")");
        content.setText(tweet.text);
        Picasso.with(WLTwitterApplication.getContext()).load(tweet.user.profileImageUrl).into(picture);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
