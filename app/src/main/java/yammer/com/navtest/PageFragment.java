package yammer.com.navtest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongjiedong on 7/15/16.
 */
public class PageFragment extends Fragment {

    private static List<AppInfo> applicationList = new ArrayList<AppInfo>();
    private ApplicationAdapter mAdapter;
    private RecyclerView mRecyclerView;
    @BindView(R.id.desc)
    TextView desc;
    private static final String DESC = "DESC";
    private String text;
    private String TAG = this.getClass().getSimpleName();
    private static int count = 0;

    public static PageFragment newInstance(String desc) {
        PageFragment fragment = new PageFragment();
        Bundle b = new Bundle();
        b.putString(DESC, desc);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        text = getArguments().getString(DESC);
        count++;
        Log.e(TAG, "count " + count);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subfragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        desc.setText(text);
        Log.e(TAG, text);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        mAdapter = new ApplicationAdapter(new ArrayList<AppInfo>(), R.layout.row_application, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        new InitializeApplicationsTask(mAdapter, mRecyclerView, getActivity()).execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, this.toString() + " onDestroyView " + text);
        mRecyclerView = null;
        desc = null;
        mAdapter = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, this.toString() + " onDestroy " + text);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.e(TAG, this.toString() + " finalize " + text);
        count--;
        Log.e(TAG, "count " + count);
    }

    /**
     * A simple AsyncTask to load the list of applications and display them
     */
    private static class InitializeApplicationsTask extends AsyncTask<Void, Void, Void> {
        WeakReference<RecyclerView> mRecyclerView;
        WeakReference<ApplicationAdapter> mAdapter;
        WeakReference<Activity> activity;

        public InitializeApplicationsTask(ApplicationAdapter mAdapter, RecyclerView mRecyclerView, Activity activity) {
            this.mAdapter = new WeakReference(mAdapter);
            this.mRecyclerView = new WeakReference<RecyclerView>(mRecyclerView);
            this.activity = new WeakReference<Activity>(activity);
        }

        @Override
        protected void onPreExecute() {
            mAdapter.get().clearApplications();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            applicationList.clear();

            //Query the applications
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> ril = activity.get().getPackageManager().queryIntentActivities(mainIntent, 0);
            for (ResolveInfo ri : ril) {
                applicationList.add(new AppInfo(activity.get(), ri));
            }
            Collections.sort(applicationList);

            for (AppInfo appInfo : applicationList) {
                //load icons before shown. so the list is smoother
                appInfo.getIcon();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //handle visibility
            mRecyclerView.get().setVisibility(View.VISIBLE);
            // mProgressBar.setVisibility(View.GONE);

            //set data for list
            mAdapter.get().addApplications(applicationList);
            // mSwipeRefreshLayout.setRefreshing(false);

            super.onPostExecute(result);
        }
    }

}
