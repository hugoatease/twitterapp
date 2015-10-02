package worldline.ssm.rd.ux.wltwitter.fragments;

import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

import java.util.List;

public interface TweetListener {
    public void onTweetsRetrieved(List<Tweet> tweets);
}
