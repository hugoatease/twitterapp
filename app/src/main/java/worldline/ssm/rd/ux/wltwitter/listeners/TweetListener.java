package worldline.ssm.rd.ux.wltwitter.listeners;

import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

import java.util.List;

public interface TweetListener {
    void onTweetsRetrieved(List<Tweet> tweets);
}
