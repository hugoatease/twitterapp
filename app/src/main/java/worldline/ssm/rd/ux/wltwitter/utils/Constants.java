package worldline.ssm.rd.ux.wltwitter.utils;

public class Constants {

	public class General {
		public static final String LOG_TAG = "WLTwitter";
	}
	
	public class Login {
		public static final String EXTRA_LOGIN = "extraLogin";
	}
	
	public class Preferences {
		public static final String SHARED_PREFERENCES_FILE_NAME = "wlTwitterSharedPrefs";
		public static final String PREF_LOGIN = "prefLogin";
		public static final String PREF_PASSWORD = "prefPassword";
	}
	
	public class Twitter {
		public static final String URL_TOKEN = "https://api.twitter.com/oauth2/token";
		public static final String URL_STREAM = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";

		public static final String API_KEY = "zVbBgGOX1XWqmUFXAPN5A3xrb";
		public static final String API_SECRET = "YNLBrvGSNXZs4H8Thpjz3isCS6UqAH3u2gyG7zHpBzYhiJLTiW";
		public static final String DEFAULT_USERNAME = "rd_aw";
	}
	
}
