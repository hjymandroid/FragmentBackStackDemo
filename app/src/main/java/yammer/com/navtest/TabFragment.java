package yammer.com.navtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongjiedong on 7/15/16.
 */
public class TabFragment extends Fragment {
    @BindView(R.id.desc)
    TextView desc;
    private static final String DESC = "DESC";
    private String text;
    private String TAG = this.getClass().getSimpleName();
    private static int count = 0;
    private int[][]memo = new int[1024][1024];

    public static TabFragment newInstance(String desc) {
        TabFragment fragment = new TabFragment();
        Bundle b = new Bundle();
        b.putString(DESC, desc);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = getArguments().getString(DESC);
        count++;
        Log.e(TAG, "count "+count);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabfragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        desc.setText(text);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, this.toString() + " onDestroyView " + text);
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
        Log.e(TAG, "count "+count);
    }
}
