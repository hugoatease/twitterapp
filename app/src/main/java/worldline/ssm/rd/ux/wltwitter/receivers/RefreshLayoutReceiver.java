package worldline.ssm.rd.ux.wltwitter.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import worldline.ssm.rd.ux.wltwitter.utils.Constants;

public class RefreshLayoutReceiver extends BroadcastReceiver {

    SwipeRefreshLayout swipeRefreshLayout;

    public RefreshLayoutReceiver(){}

    public RefreshLayoutReceiver(SwipeRefreshLayout layout) {
        swipeRefreshLayout = layout;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch(intent.getAction()){
            case Constants.General.ACTION_SERVICE_STARTED:
                this.swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
                break;

            case Constants.General.ACTION_SERVICE_STOPPED:
                swipeRefreshLayout.setRefreshing(false);
                break;
            default:
                break;
        }
    }
}
