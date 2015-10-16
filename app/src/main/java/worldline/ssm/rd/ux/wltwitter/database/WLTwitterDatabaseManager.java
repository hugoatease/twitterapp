package worldline.ssm.rd.ux.wltwitter.database;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
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

   /* public static void testDatabase(List<Tweet> tweets){
        final SQLiteOpenHelper sqLiteOpenHelper = new WLTwitterDatabaseHelper(WLTwitterApplication.getContext());
        final SQLiteDatabase tweetsDatabase = sqLiteOpenHelper.getWritableDatabase();

        for(Tweet tweet: tweets){
            final ContentValues contentValues = new ContentValues();
            contentValues.put(WLTwitterDatabaseContract.USER_NAME, tweet.user.name);
            contentValues.put(WLTwitterDatabaseContract.USER_ALIAS, tweet.user.screenName);
            contentValues.put(WLTwitterDatabaseContract.USER_IMAGE_URL, tweet.user.profileImageUrl);
            contentValues.put(WLTwitterDatabaseContract.TEXT, tweet.text);
            contentValues.put(WLTwitterDatabaseContract.DATE_CREATED, tweet.dateCreated);
            tweetsDatabase.insert(WLTwitterDatabaseContract.TABLE_TWEETS, "", contentValues);
        }

        final Cursor cursor = tweetsDatabase.query(WLTwitterDatabaseContract.TABLE_TWEETS, WLTwitterDatabaseContract.PROJECTION_FULL, null, null, null, null, null);
        while(cursor.moveToNext()){
            final String tweetUserName = cursor.getString(cursor.getColumnIndex(WLTwitterDatabaseContract.USER_NAME));
            Log.d("Username: ", tweetUserName);
        }

        if(!cursor.isClosed()){
            cursor.close();
        }
    }*/

    public static void testContentProvider(List<Tweet> tweets){
        WLTwitterApplication.getContext().getContentResolver().query(
                WLTwitterDatabaseContract.TWEETS_URI, WLTwitterDatabaseContract.PROJECTION_FULL, null, null, null);


        for(Tweet t: tweets){
            WLTwitterApplication.getContext().getContentResolver().insert(
                    WLTwitterDatabaseContract.TWEETS_URI, getContentValues(t));
        }

        WLTwitterApplication.getContext().getContentResolver().update(
                WLTwitterDatabaseContract.TWEETS_URI, null, null, null);

        WLTwitterApplication.getContext().getContentResolver().delete(
                WLTwitterDatabaseContract.TWEETS_URI, null, null);

    }

    public static ContentValues getContentValues(Tweet tweet){
        ContentValues contentValues = new ContentValues();
        contentValues.put(WLTwitterDatabaseContract.USER_NAME, tweet.user.name);
        contentValues.put(WLTwitterDatabaseContract.USER_ALIAS, tweet.user.screenName);
        contentValues.put(WLTwitterDatabaseContract.USER_IMAGE_URL, tweet.user.profileImageUrl);
        contentValues.put(WLTwitterDatabaseContract.TEXT, tweet.text);
        contentValues.put(WLTwitterDatabaseContract.DATE_CREATED, tweet.dateCreated);
        return contentValues;
    }

}
