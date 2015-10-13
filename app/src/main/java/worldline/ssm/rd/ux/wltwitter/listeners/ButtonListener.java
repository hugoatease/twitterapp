package worldline.ssm.rd.ux.wltwitter.listeners;


import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public interface ButtonListener {
    void onRTButtonClicked(Tweet tweet);
    void onStarButtonClicked(Tweet tweet);
    void onReplyButtonClicked(Tweet tweet);
}
