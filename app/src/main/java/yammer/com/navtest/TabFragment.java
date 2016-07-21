package yammer.com.navtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongjiedong on 7/15/16.
 */
public class TabFragment extends Fragment {
    private static final String DESC = "DESC";
    private String text;
    private String TAG = this.getClass().getSimpleName();
    private static int count = 0;
    private Stack<String> bmpStack = new Stack<>();

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
        setHasOptionsMenu(true);
        text = getArguments().getString(DESC);
        count++;
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
        Log.e(TAG, "count " + count);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e(TAG, "onOptionsItemSelected" + TAG);
        switch (item.getItemId()) {
            case R.id.addlayer:
                addFragment();
                break;
            case R.id.clear_layer:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void addFragment() {

        // Take screen shot of current page
        int count = getChildFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            //try to make things look smoother using screenshot
            Drawable drawable = saveScreenShot();
            getView().setBackgroundDrawable(drawable);
        }
        String title = "tab-" + text + " child" + "-" + count;
        Fragment fragment = PageFragment.newInstance(title);
        FragmentManager cfm = getChildFragmentManager();
        FragmentTransaction ft = cfm.beginTransaction();
        ft.setCustomAnimations(R.anim.frag_slide_in_from_bottom, R.anim.frag_slide_in_from_bottom, R.anim.frag_slide_out_from_top, R.anim.frag_slide_out_from_top);
        // here it makes a difference use add or replace
        // replace will trigger view destroy while add is not
        ft.replace(R.id.fragment_inner_content, fragment, title).addToBackStack(title);
        ft.commit();
        count++;
    }


    public boolean onBackPressHandled() {
        FragmentManager fm = getChildFragmentManager();
        int count = fm.getBackStackEntryCount();
        Log.e(TAG, "stack size" + String.valueOf(count));
        if (count != 0) { // no more view on it now, lets give it to the base nav stack
            // lets roll back to previous fragment
            // use the prev image
            Drawable drawable = loadScreenShot();
            if (drawable != null) {
                getView().setBackgroundDrawable(drawable);
            }
            fm.popBackStack();

            return true;
        } else {
            return false;
        }
    }


    private Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    private Drawable saveScreenShot() {
        int count = getChildFragmentManager().getBackStackEntryCount();
        String sdcardBmpPath = text + count + "img.jpeg";
        String path = Environment.getExternalStorageDirectory().toString();
        sdcardBmpPath = path + "/Download/" + sdcardBmpPath;
        Bitmap bitmap = screenShot(getView());
        AndroidBmpUtil bmpUtil = new AndroidBmpUtil();
        //imageView.setImageBitmap(bitmap);

        if (bmpUtil.save(bitmap, sdcardBmpPath, getActivity())) {
            bmpStack.add(sdcardBmpPath);
            Log.e(TAG, "current snapshot: " + sdcardBmpPath);
            return new BitmapDrawable(getResources(), bitmap);
        } else {
            return null;
        }
    }

    private Drawable loadScreenShot() {
        if(bmpStack.empty()) {
            return null;
        }
        String snapshot = bmpStack.pop();
        Bitmap bitmap = BitmapFactory.decodeFile(snapshot);
        if (bitmap != null) {
            Log.e(TAG, "current snapshot: " + snapshot);

        }
        return new BitmapDrawable(getResources(), bitmap);
    }


}
