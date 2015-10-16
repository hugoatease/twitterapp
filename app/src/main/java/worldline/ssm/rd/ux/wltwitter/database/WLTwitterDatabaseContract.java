package worldline.ssm.rd.ux.wltwitter.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class WLTwitterDatabaseContract implements BaseColumns {

    // Field names
    public static final String DATE_CREATED = "dateCreated";
    public static final String TEXT = "tweetText";
    public static final String USER_NAME = "userName";
    public static final String USER_ALIAS = "userAlias";
    public static final String USER_IMAGE_URL = "userImageUrl";
    public static final String DATE_CREATED_TIMESTAMP = "dateCreatedTimestamp";

    // Table name
    public static final String TABLE_TWEETS = "tweets";

    // Table scripts creation
    private static final String TABLE_GENERIC_CREATE_SCRIPT_PREFIX = "CREATE TABLE IF NOT EXISTS ";
    private static final String TABLE_IMAGES_CREATE_SCRIPT_SUFFIX = "(" + _ID + " INTEGER PRIMARY KEY, " +
            DATE_CREATED + " TEXT NOT NULL, " +
            DATE_CREATED_TIMESTAMP + " INTEGER, " +
            TEXT + " TEXT NOT NULL, "+
            USER_NAME + " TEXT NOT NULL, "+
            USER_ALIAS + " TEXT NOT NULL, "+
            USER_IMAGE_URL + " TEXT NOT NULL)";

    public static final String TABLE_TWEETS_CREATE_SCRIPT = TABLE_GENERIC_CREATE_SCRIPT_PREFIX +
            TABLE_TWEETS + TABLE_IMAGES_CREATE_SCRIPT_SUFFIX;

    // The projections
    public static final String[] PROJECTION_FULL = new String[]{
            _ID,
            DATE_CREATED,
            DATE_CREATED_TIMESTAMP,
            TEXT,
            USER_NAME,
            USER_ALIAS,
            USER_IMAGE_URL
    };

    // Selections
    public static final String SELECTION_BY_ID = _ID + "=?";
    public static final String SELECTION_BY_CREATION_DATE = DATE_CREATED + "=?";
    public static final String SELECTION_BY_CREATION_DATE_TIMESTAMP = DATE_CREATED_TIMESTAMP + "=?";
    public static final String SELECTION_BY_USER_NAME = USER_NAME + "=?";

    // Sort order
    public static final String ORDER_BY_DATE_CREATED_TIMESTAMP_DESCENDING = DATE_CREATED_TIMESTAMP + " DESC";

    // Content Provider stuff
    public static final String CONTENT_PROVIDER_TWEETS_AUTHORITY = "worldline.ssm.rd.ux.TweetAuthority";
    public static final Uri TWEETS_URI = Uri.parse("content://" + CONTENT_PROVIDER_TWEETS_AUTHORITY + "/" + TABLE_TWEETS);
    public static final String TWEETS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.wltwitter.tweets";


}
