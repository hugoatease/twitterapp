package worldline.ssm.rd.ux.wltwitter.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import worldline.ssm.rd.ux.wltwitter.helpers.TwitterHelper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public ImageLoadTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            if (params[0] != null) {
                final Bitmap image = TwitterHelper.getTwitterUserImage(params[0]);
                if (image != null) {
                    return image;
                }
                return null;
            }
        }
        catch (Exception ex) {
            ex.getStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }

}