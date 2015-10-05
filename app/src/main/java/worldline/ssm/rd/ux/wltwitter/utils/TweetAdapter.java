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
            TextView tt1 = (TextView) v.findViewById(R.id.user);
            TextView tt2 = (TextView) v.findViewById(R.id.content_tweet);
            ImageView iv1 = (ImageView) v.findViewById(R.id.user_picture);

            if (tt1 != null) {
                tt1.setText(tweet.user.name + " (@" + tweet.user.screenName + ")");
            }

            if (tt2 != null) {
                tt2.setText(tweet.text);
            }

            if(iv1 != null){
                new ImageLoadTask(iv1).execute(tweet.user.profileImageUrl);
            }
        }

        return v;
    }


    }
