package worldline.ssm.rd.ux.wltwitter.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseContract;


public class WLTwitterDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tweets.db";
    private static final int DATABASE_VERSION = 17;

    public WLTwitterDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WLTwitterDatabaseContract.TABLE_TWEETS_CREATE_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WLTwitterDatabaseContract.TABLE_TWEETS);
        onCreate(db);
    }
}
