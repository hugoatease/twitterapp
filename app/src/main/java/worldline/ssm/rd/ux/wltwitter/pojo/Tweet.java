package worldline.ssm.rd.ux.wltwitter.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.annotations.SerializedName;

public class Tweet {

	@SerializedName("created_at")
	public String dateCreated;

	@SerializedName("id")
	public String id;

	@SerializedName("text")
	public String text;

	@SerializedName("in_reply_to_status_id")
	public String inReplyToStatusId;

	@SerializedName("in_reply_to_user_id")
	public String inReplyToUserId;

	@SerializedName("in_reply_to_screen_name")
	public String inReplyToScreenName;

	@SerializedName("user")
	public TwitterUser user;

	@Override
	public String toString() {
		return text;
	}
	
	public long getDateCreatedTimestamp(){
		final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
		dateFormat.setLenient(false);
		try {
			final Date created = dateFormat.parse(dateCreated);
			return created.getTime();
		} catch (Exception e) {
			return 0;
		}
	}
	
}