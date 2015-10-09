package worldline.ssm.rd.ux.wltwitter.http;

import android.os.AsyncTask;
import android.util.Log;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.http.javanet.NetHttpTransport;
import worldline.ssm.rd.ux.wltwitter.utils.Constants;

import java.io.IOException;

public class OauthAsyncTask extends AsyncTask<Void, Void, OAuthCredentialsResponse> {
    @Override
    protected OAuthCredentialsResponse doInBackground(Void... params) {
        OAuthHmacSigner signer = new OAuthHmacSigner();
        signer.clientSharedSecret = Constants.Twitter.API_SECRET;

        OAuthGetTemporaryToken tokenFetcher = new OAuthGetTemporaryToken("https://api.twitter.com/oauth/request_token");
        tokenFetcher.consumerKey = Constants.Twitter.API_KEY;
        tokenFetcher.transport = new NetHttpTransport();
        tokenFetcher.signer = signer;

        try {
            return tokenFetcher.execute();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(OAuthCredentialsResponse oAuthCredentialsResponse) {
        super.onPostExecute(oAuthCredentialsResponse);


    }
}
