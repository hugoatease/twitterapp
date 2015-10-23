package worldline.ssm.rd.ux.wltwitter.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseContract;
import worldline.ssm.rd.ux.wltwitter.helpers.WLTwitterDatabaseHelper;

import java.util.concurrent.locks.ReentrantLock;
import android.database.sqlite.SQLiteDatabase;

public class WLTwitterDatabaseProvider extends ContentProvider {

    // Our database helper
    private WLTwitterDatabaseHelper mDBHelper;

    // The URI matcher to check if the URI is correct
    private UriMatcher mUriMatcher;

    // The URI matcher code for correct result
    private static final int TWEET_CORRECT_URI_CODE = 42;

    // Use a Lock to be thread-safe
    private final ReentrantLock mLock = new ReentrantLock();

    @Override
    public boolean onCreate() {
        mDBHelper = new WLTwitterDatabaseHelper(getContext());
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(WLTwitterDatabaseContract.CONTENT_PROVIDER_TWEETS_AUTHORITY,
                WLTwitterDatabaseContract.TABLE_TWEETS, TWEET_CORRECT_URI_CODE);
        return true;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        if (mUriMatcher.match(uri) == TWEET_CORRECT_URI_CODE){
            mLock.lock();
            try {
                final SQLiteDatabase db = mDBHelper.getWritableDatabase();
                final int count = db.delete(WLTwitterDatabaseContract.TABLE_TWEETS, where, whereArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            } catch (Exception e){
                return 0;
            } finally {
                mLock.unlock();
            }
        }
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        if (mUriMatcher.match(uri) == TWEET_CORRECT_URI_CODE){
            return WLTwitterDatabaseContract.TWEETS_CONTENT_TYPE;
        }
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if ((mUriMatcher.match(uri) == TWEET_CORRECT_URI_CODE) && (null != values)){
            mLock.lock();
            try {
                final SQLiteDatabase db = mDBHelper.getWritableDatabase();
                final long rowId = db.insert(WLTwitterDatabaseContract.TABLE_TWEETS, "", values);
                if (rowId > 0) {
                    final Uri applicationUri = ContentUris.withAppendedId(WLTwitterDatabaseContract.TWEETS_URI, rowId);
                    getContext().getContentResolver().notifyChange(applicationUri, null);
                    return applicationUri;
                }
            } catch (Exception e){
                return null;
            } finally {
                mLock.unlock();
            }
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (mUriMatcher.match(uri) == TWEET_CORRECT_URI_CODE){
            mLock.lock();
            try {
                SQLiteDatabase db = mDBHelper.getReadableDatabase();
                Cursor c = db.query(WLTwitterDatabaseContract.TABLE_TWEETS, projection, selection, selectionArgs, null, null, sortOrder);
                c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;
            } catch (Exception e){
                return null;
            } finally {
                mLock.unlock();
            }
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if ((mUriMatcher.match(uri) == TWEET_CORRECT_URI_CODE) && (null != values)){
            mLock.lock();
            try {
                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                int count = db.update(WLTwitterDatabaseContract.TABLE_TWEETS, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            } catch (Exception e){
                return 0;
            } finally {
                mLock.unlock();
            }
        }
        return 0;
    }

}