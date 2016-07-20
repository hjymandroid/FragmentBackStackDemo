package yammer.com.navtest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by hongjiedong on 7/19/16.
 */
public class CustomRecyclerView extends RecyclerView {
    //int[]memo = new int[6000*1024];
    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.e("CustomRecyclerView", "finalized");
    }
}
