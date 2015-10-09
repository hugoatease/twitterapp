package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Fragment;
import android.os.Bundle;

import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;


public class TweetFragment extends Fragment {

    public static TweetFragment newInstance(Tweet tweet){
        final TweetFragment tweetFragment = new TweetFragment();
        final Bundle arguments = new Bundle();
        arguments.putString("username", tweet.user.name);
        arguments.putString("alias", tweet.user.screenName);
        arguments.putString("content", tweet.text);
        arguments.putString("imageURL", tweet.user.profileImageUrl);
        tweetFragment.setArguments(arguments);
        return tweetFragment;
    }
}
