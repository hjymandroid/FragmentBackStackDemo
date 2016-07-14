package yammer.com.navtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_account_box_black_24dp, "Account"))
                .addItem(new BottomNavigationItem(R.drawable.ic_assignment_black_24dp, "Assignment"))
                .addItem(new BottomNavigationItem(R.drawable.ic_bug_report_black_24dp, "Bug"))
                .addItem(new BottomNavigationItem(R.drawable.ic_border_all_black_24dp, "Border"))
                .addItem(new BottomNavigationItem(R.drawable.ic_add_shopping_cart_black_24dp, "Shopping"))
                .initialise();
    }
}
