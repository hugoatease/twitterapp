package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.WLTwitterActivity;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.adapters.TweetAdapter;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseContract;
import worldline.ssm.rd.ux.wltwitter.receivers.NewTweetsReceiver;
import worldline.ssm.rd.ux.wltwitter.receivers.RefreshLayoutStartReceiver;
import worldline.ssm.rd.ux.wltwitter.receivers.RefreshLayoutStopReceiver;
import worldline.ssm.rd.ux.wltwitter.services.TweetService;
import worldline.ssm.rd.ux.wltwitter.utils.Constants;

public class TweetsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {
    private SwipeRefreshLayout rootView;
    private RecyclerView tweetsView;
    private String login;

    private TweetAdapter adapter;
    private RefreshLayoutStartReceiver refreshLayoutStartReceiver;
    private RefreshLayoutStopReceiver refreshLayoutStopReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_tweets, container, false);

        this.tweetsView = (RecyclerView) this.rootView.findViewById(R.id.tweetsListView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(WLTwitterApplication.getContext());
        this.tweetsView.setLayoutManager(layoutManager);
        this.rootView.setOnRefreshListener(this);
        return this.rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
        login = prefs.getString("login", "");

        setReceivers();
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(refreshLayoutStartReceiver);
        refreshLayoutStartReceiver = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onRefresh() {
        if (!TextUtils.isEmpty(login)) {
            final Intent serviceIntent = new Intent(getActivity(), TweetService.class);

            NewTweetsReceiver mReceiver = new NewTweetsReceiver();
            getActivity().registerReceiver(mReceiver, new IntentFilter(Constants.General.ACTION_NEW_TWEETS));

            Bundle extras = new Bundle();
            extras.putString("login", login);
            serviceIntent.putExtras(extras);
            getActivity().startService(serviceIntent);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final CursorLoader cursorLoader = new CursorLoader(WLTwitterApplication.getContext());
        cursorLoader.setUri(WLTwitterDatabaseContract.TWEETS_URI);
        cursorLoader.setProjection(WLTwitterDatabaseContract.PROJECTION_FULL);
        cursorLoader.setSelection(null);
        cursorLoader.setSelectionArgs(null);
        cursorLoader.setSortOrder(WLTwitterDatabaseContract.ORDER_BY_DATE_CREATED_TIMESTAMP_DESCENDING);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter = new TweetAdapter(getActivity(), cursor, (WLTwitterActivity) getActivity());
        this.tweetsView.setAdapter(adapter);
        adapter.changeCursor(cursor);
    }


    private void setReceivers(){
        refreshLayoutStartReceiver = new RefreshLayoutStartReceiver(this.rootView);
        refreshLayoutStopReceiver = new RefreshLayoutStopReceiver(this.rootView);
        getActivity().registerReceiver(refreshLayoutStartReceiver, new IntentFilter(Constants.General.ACTION_SERVICE_STARTED));
        getActivity().registerReceiver(refreshLayoutStopReceiver, new IntentFilter(Constants.General.ACTION_SERVICE_STOPPED));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
