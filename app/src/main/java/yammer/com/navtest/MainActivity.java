package yammer.com.navtest;

import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.this.getClass().getSimpleName();
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar navigationBar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private TabsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new TabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, new NoPageTransformer());

        viewPager.setOffscreenPageLimit(3);
        navigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_account_box_black_24dp, "Account"))
                .addItem(new BottomNavigationItem(R.drawable.ic_assignment_black_24dp, "Assignment"))
                .addItem(new BottomNavigationItem(R.drawable.ic_bug_report_black_24dp, "Bug"))
                .addItem(new BottomNavigationItem(R.drawable.ic_border_all_black_24dp, "Border"))
                .addItem(new BottomNavigationItem(R.drawable.ic_add_shopping_cart_black_24dp, "Shopping"))
                .initialise();

        navigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private class TabsAdapter extends FragmentStatePagerAdapter {

        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e(TAG, String.valueOf(position) + " tab creation");
            return TabFragment.newInstance(String.valueOf(position));
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    @Override
    public void onBackPressed() {
        // Check the
        TabFragment currentFragment = getCurrentFragment();
        if (currentFragment.onBackPressHandled()) {
            return;
        } else {
            Toast.makeText(this, "Let's handle back click", Toast.LENGTH_SHORT).show();
            return;
        }

        //super.onBackPressed();
    }

    private TabFragment getCurrentFragment() {
        TabFragment fragment = (TabFragment) adapter.instantiateItem(null, viewPager.getCurrentItem());
        return fragment;
    }

    private static class NoPageTransformer implements ViewPager.PageTransformer {
        public void transformPage(View view, float position) {
            if (position < 0) {
                view.setScrollX((int)((float)(view.getWidth()) * position));
            } else if (position > 0) {
                view.setScrollX(-(int) ((float) (view.getWidth()) * -position));
            } else {
                view.setScrollX(0);
            }
        }
    }
}
