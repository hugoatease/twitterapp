package worldline.ssm.rd.ux.wltwitter.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TwitterUser implements Parcelable {

	@SerializedName("screen_name")
	public String screenName;

	@SerializedName("name")
	public String name;

	@SerializedName("profile_image_url")
	public String profileImageUrl;


	public TwitterUser(){

	}

	protected TwitterUser(Parcel in) {
		screenName = in.readString();
		name = in.readString();
		profileImageUrl = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(screenName);
		dest.writeString(name);
		dest.writeString(profileImageUrl);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<TwitterUser> CREATOR = new Parcelable.Creator<TwitterUser>() {
		@Override
		public TwitterUser createFromParcel(Parcel in) {
			return new TwitterUser(in);
		}

		@Override
		public TwitterUser[] newArray(int size) {
			return new TwitterUser[size];
		}
	};
}