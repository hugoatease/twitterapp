package worldline.ssm.rd.ux.wltwitter.utils;

import android.content.Context;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.http.ImageLoadTask;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

public class TweetAdapter extends ArrayAdapter<Tweet> {

    public TweetAdapter(Context context, int resource, List<Tweet> tweets) {
        super(context, resource, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.items_tweet, null);
        }

        Tweet tweet = getItem(position);

        if(tweet != null){
            TextView userTextView = (TextView) v.findViewById(R.id.user);
            TextView contentTextView = (TextView) v.findViewById(R.id.content_tweet);
            TextView screenNameTextView = (TextView) v.findViewById(R.id.screenName);
            ImageView userImageView = (ImageView) v.findViewById(R.id.user_picture);

            if (userTextView != null) {
                userTextView.setText(tweet.user.name);
            }

            if(screenNameTextView != null){
                screenNameTextView.setText("(@" + tweet.user.screenName + ")");
            }

            if (contentTextView != null) {
                contentTextView.setText(tweet.text);
            }

            if(userImageView != null){
                new ImageLoadTask(userImageView).execute(tweet.user.profileImageUrl);
            }
        }

        return v;
    }


    }
