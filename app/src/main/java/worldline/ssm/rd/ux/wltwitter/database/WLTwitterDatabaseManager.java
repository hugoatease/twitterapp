package worldline.ssm.rd.ux.wltwitter.database;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.helpers.TwitterHelper;
import worldline.ssm.rd.ux.wltwitter.helpers.WLTwitterDatabaseHelper;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.pojo.TwitterUser;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

public class WLTwitterDatabaseManager {

    public static Tweet tweetFromCursor(Cursor c){
        if (null != c){
            final Tweet tweet = new Tweet();
            tweet.user = new TwitterUser();

            // Retrieve the date created
            if (c.getColumnIndex(WLTwitterDatabaseContract.DATE_CREATED) >= 0){
                tweet.dateCreated = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.DATE_CREATED));
            }

            // Retrieve the user name
            if (c.getColumnIndex(WLTwitterDatabaseContract.USER_NAME) >= 0){
                tweet.user.name = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.USER_NAME));
            }

            // Retrieve the user alias
            if (c.getColumnIndex(WLTwitterDatabaseContract.USER_ALIAS) >= 0){
                tweet.user.screenName = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.USER_ALIAS));
            }

            // Retrieve the user image url
            if (c.getColumnIndex(WLTwitterDatabaseContract.USER_IMAGE_URL) >= 0){
                tweet.user.profileImageUrl = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.USER_IMAGE_URL));
            }

            // Retrieve the text of the tweet
            if (c.getColumnIndex(WLTwitterDatabaseContract.TEXT) >= 0){
                tweet.text = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.TEXT));
            }

            return tweet;
        }
        return null;
    }

    public static ContentValues tweetToContentValues(Tweet tweet){
        final ContentValues values = new ContentValues();

        // Set the date created
        if (!TextUtils.isEmpty(tweet.dateCreated)){
            values.put(WLTwitterDatabaseContract.DATE_CREATED, tweet.dateCreated);
        }

        // Set the date created as timestamp
        values.put(WLTwitterDatabaseContract.DATE_CREATED_TIMESTAMP, tweet.getDateCreatedTimestamp());

        // Set the user name
        if (!TextUtils.isEmpty(tweet.user.name)){
            values.put(WLTwitterDatabaseContract.USER_NAME, tweet.user.name);
        }

        // Set the user alias
        if (!TextUtils.isEmpty(tweet.user.screenName)){
            values.put(WLTwitterDatabaseContract.USER_ALIAS, tweet.user.screenName);
        }

        // Set the user image url
        if (!TextUtils.isEmpty(tweet.user.profileImageUrl)){
            values.put(WLTwitterDatabaseContract.USER_IMAGE_URL, tweet.user.profileImageUrl);
        }

        // Set the text of the tweet
        if (!TextUtils.isEmpty(tweet.text)){
            values.put(WLTwitterDatabaseContract.TEXT, tweet.text);
        }

        return values;
    }

    public static void testContentProvider(List<Tweet> tweets, Boolean debug){
        for(Tweet t: tweets){
            insertTweetToDatabase(t);
        }

        insertTweetToDatabase(TwitterHelper.getOneFakeTweet("King Toto", "Toto", "Et plouf ! "));
        insertTweetToDatabase(TwitterHelper.getOneFakeTweet("Queen Tata", "Tata", "Et paf ! "));

        checkModification(debug);

        ContentValues newValues = new ContentValues();
        newValues.put(WLTwitterDatabaseContract.USER_NAME, "Prince Toto");

        // Update sur le Tweet King Toto
        int countUpdate = WLTwitterApplication.getContext().getContentResolver().update(
                WLTwitterDatabaseContract.TWEETS_URI, newValues, WLTwitterDatabaseContract.SELECTION_BY_USER_NAME, new String[]{"King Toto"});

        Log.d("Update, row count", Integer.toString(countUpdate));

        checkModification(debug);

        // Delete le Tweet de Queen Tata
        int isdelete = WLTwitterApplication.getContext().getContentResolver().delete(
                WLTwitterDatabaseContract.TWEETS_URI, WLTwitterDatabaseContract.SELECTION_BY_USER_NAME, new String[]{"Queen Tata"});

        Log.d("Delete, row count", Integer.toString(isdelete));

        checkModification(debug);
    }

    private static void insertTweetToDatabase(Tweet tweet){
        WLTwitterApplication.getContext().getContentResolver().insert(
                WLTwitterDatabaseContract.TWEETS_URI, tweetToContentValues(tweet));
    }

    private static void checkModification(boolean debug){
        if(!debug){
            return;
        }

        final SQLiteOpenHelper sqLiteOpenHelper = new WLTwitterDatabaseHelper(WLTwitterApplication.getContext());
        final SQLiteDatabase tweetsDatabase = sqLiteOpenHelper.getWritableDatabase();

        final Cursor cursor = tweetsDatabase.query(WLTwitterDatabaseContract.TABLE_TWEETS, WLTwitterDatabaseContract.PROJECTION_FULL, null, null, null, null, null);
        while(cursor.moveToNext()){
            final String tweetUserName = cursor.getString(cursor.getColumnIndex(WLTwitterDatabaseContract.USER_NAME));
            Log.d("Username: ", tweetUserName);
        }

        if(!cursor.isClosed()){
            cursor.close();
        }
    }
}
