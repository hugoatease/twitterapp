package worldline.ssm.rd.ux.wltwitter.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

public class RefreshLayoutStartReceiver extends BroadcastReceiver {

    SwipeRefreshLayout swipeRefreshLayout;

    public RefreshLayoutStartReceiver(SwipeRefreshLayout layout) {
        swipeRefreshLayout = layout;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }
}
