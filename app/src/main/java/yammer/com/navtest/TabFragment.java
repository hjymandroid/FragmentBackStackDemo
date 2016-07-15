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
        Log.e(TAG, this.toString() + " destroied " + text);
    }
}
