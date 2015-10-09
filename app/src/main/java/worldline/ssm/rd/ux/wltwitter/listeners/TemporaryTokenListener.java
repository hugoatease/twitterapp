package worldline.ssm.rd.ux.wltwitter.listeners;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;

public interface TemporaryTokenListener {
    void onTokenFetch(OAuthCredentialsResponse credentials);
}
