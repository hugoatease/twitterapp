package worldline.ssm.rd.ux.wltwitter.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

public class RefreshLayoutStopReceiver extends BroadcastReceiver {

    SwipeRefreshLayout swipeRefreshLayout;

    public RefreshLayoutStopReceiver(SwipeRefreshLayout layout){
        swipeRefreshLayout = layout;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        swipeRefreshLayout.setRefreshing(false);
    }
}
