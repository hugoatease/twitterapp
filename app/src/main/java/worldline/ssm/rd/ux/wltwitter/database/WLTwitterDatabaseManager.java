package worldline.ssm.rd.ux.wltwitter.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.helpers.TwitterHelper;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.pojo.TwitterUser;

public class WLTwitterDatabaseManager {

    public static Tweet tweetFromCursor(Cursor c) {
        if (null != c) {
            final Tweet tweet = new Tweet();
            tweet.user = new TwitterUser();

            // Retrieve the date created
            if (c.getColumnIndex(WLTwitterDatabaseContract.DATE_CREATED) >= 0) {
                tweet.dateCreated = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.DATE_CREATED));
            }

            // Retrieve the user name
            if (c.getColumnIndex(WLTwitterDatabaseContract.USER_NAME) >= 0) {
                tweet.user.name = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.USER_NAME));
            }

            // Retrieve the user alias
            if (c.getColumnIndex(WLTwitterDatabaseContract.USER_ALIAS) >= 0) {
                tweet.user.screenName = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.USER_ALIAS));
            }

            // Retrieve the user image url
            if (c.getColumnIndex(WLTwitterDatabaseContract.USER_IMAGE_URL) >= 0) {
                tweet.user.profileImageUrl = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.USER_IMAGE_URL));
            }

            // Retrieve the text of the tweet
            if (c.getColumnIndex(WLTwitterDatabaseContract.TEXT) >= 0) {
                tweet.text = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.TEXT));
            }

            return tweet;
        }
        return null;
    }

    public static ContentValues tweetToContentValues(Tweet tweet) {
        final ContentValues values = new ContentValues();

        // Set the date created
        if (!TextUtils.isEmpty(tweet.dateCreated)) {
            values.put(WLTwitterDatabaseContract.DATE_CREATED, tweet.dateCreated);
        }

        // Set the date created as timestamp
        values.put(WLTwitterDatabaseContract.DATE_CREATED_TIMESTAMP, tweet.getDateCreatedTimestamp());

        // Set the user name
        if (!TextUtils.isEmpty(tweet.user.name)) {
            values.put(WLTwitterDatabaseContract.USER_NAME, tweet.user.name);
        }

        // Set the user alias
        if (!TextUtils.isEmpty(tweet.user.screenName)) {
            values.put(WLTwitterDatabaseContract.USER_ALIAS, tweet.user.screenName);
        }

        // Set the user image url
        if (!TextUtils.isEmpty(tweet.user.profileImageUrl)) {
            values.put(WLTwitterDatabaseContract.USER_IMAGE_URL, tweet.user.profileImageUrl);
        }

        // Set the text of the tweet
        if (!TextUtils.isEmpty(tweet.text)) {
            values.put(WLTwitterDatabaseContract.TEXT, tweet.text);
        }

        return values;
    }

    private static synchronized boolean doesContainTweet(Tweet tweet) {
        boolean result = false;
        if ((null != tweet) && (!TextUtils.isEmpty(tweet.dateCreated))) {
            final Cursor cursor = WLTwitterApplication.getContext().getContentResolver().query(
                    WLTwitterDatabaseContract.TWEETS_URI, WLTwitterDatabaseContract.PROJECTION_FULL,
                    WLTwitterDatabaseContract.SELECTION_BY_CREATION_DATE, new String[]{tweet.dateCreated}, null);
            if ((null != cursor) && (cursor.moveToFirst())) {
                result = true;
            }
            if ((null != cursor) && (!cursor.isClosed())) {
                cursor.close();
            }
        }
        return result;
    }


    public static int insertTweets(List<Tweet> tweets) {
        int nbTweetsInserted = 0;

        for (Tweet t : tweets) {
            if (!doesContainTweet(t)) {
                insertTweetToDatabase(t, WLTwitterDatabaseContract.TWEETS_URI);
                nbTweetsInserted++;
            }
        }

        return nbTweetsInserted;
    }

    public static void testContentProvider(List<Tweet> tweets) {
        for (Tweet t : tweets) {
            if (!doesContainTweet(t)) {
                insertTweetToDatabase(t, WLTwitterDatabaseContract.TWEETS_URI);
            }
        }
        String userName1 = "King Toto";
        String userName2 = "Queen Tata";

        // Pour tester les fonctions update et delete, j'ai réalisé une fonction qui permet de créer un seul faux Tweet
        insertTweetToDatabase(TwitterHelper.getOneFakeTweet(userName1, "Toto", "Et plouf ! "), WLTwitterDatabaseContract.TWEETS_URI);
        insertTweetToDatabase(TwitterHelper.getOneFakeTweet(userName2, "Tata", "Et paf ! "), WLTwitterDatabaseContract.TWEETS_URI);

        // Update sur le Tweet King Toto
        ContentValues newValues = new ContentValues();
        newValues.put(WLTwitterDatabaseContract.USER_NAME, "Prince Toto");
        WLTwitterApplication.getContext().getContentResolver().update(
                WLTwitterDatabaseContract.TWEETS_URI,
                newValues,
                WLTwitterDatabaseContract.SELECTION_BY_USER_NAME,
                new String[]{userName1});

        // Delete le Tweet de Queen Tata
        WLTwitterApplication.getContext().getContentResolver().delete(
                WLTwitterDatabaseContract.TWEETS_URI,
                WLTwitterDatabaseContract.SELECTION_BY_USER_NAME,
                new String[]{userName2});
    }

    public static void insertTweetToDatabase(Tweet tweet, Uri uri) {
        WLTwitterApplication.getContext().getContentResolver().insert(
                uri, tweetToContentValues(tweet));
    }
}
