package net.silica;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.silica.dao.NetworkChangeReceiver;
import net.silica.homeView.HomeFragment;
import net.silica.imageSliderViewPager.IndicatorView;
import net.silica.userView.UserFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

// ctrl + shift + - : rut gon code
public class MainActivity extends AppCompatActivity {
    Context mContext;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager viewPager;
    private IndicatorView indicatorView;
    Timer timer;
    int page = 0;
    ArrayList<String> listImageSlider;
    UserFragment userFragment;
    HomeFragment homeFragment;
    NetworkChangeReceiver receiver;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction = fragmentManager.beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.replace(R.id.fragment_content, new HomeFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_library:
                    return true;
                case R.id.navigation_subject:
//                    transaction = fragmentManager.beginTransaction();
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    transaction.replace(R.id.fragment_content, new AllFragment());
//                    transaction.commit();
                    return true;
//                case R.id.navigation_cart:
//                    return true;
                case R.id.navigation_user:
                    transaction = fragmentManager.beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.replace(R.id.fragment_content, new UserFragment());
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

//        hideNavigationBar();
        setContentView(R.layout.activity_main);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle("Collapsing");
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mContext = this.getApplicationContext();
        homeFragment = new HomeFragment();
        receiver = new NetworkChangeReceiver();
        final IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, filter);

//        viewPager = findViewById(R.id.viewPager);
//        indicatorView = findViewById(R.id.indicator);
//        initViewPager();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.fragment_content, homeFragment);
        transaction.commit();
//        final MyProgressDialog dialog = new MyProgressDialog(this);
//        dialog.setCancelable(false);
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//                dialog.show();
//            }
//        }, 10);
//        dialog.dismiss();
//        FragmentManager fm = getFragmentManager();
//
//// add
//        FragmentTransaction ft_add = fm.beginTransaction();
//        ft_add.add(R.id.your_placehodler,new YourFragment());
//        ft_add.commit();
//
//// replace
//        FragmentTransaction ft_rep = fm.beginTransaction();
//        ft_rep.replace(R.id.your_placehodler, new YourFragment());
//        ft_rep.commit();
//
//// remove
//        Fragment fragment = fm.findFragmentById(R.id.your_placehodler);
//        FragmentTransaction ft_remo = fm.beginTransaction();
//        ft_remo.remove(fragment);
//        ft_remo.commit();

//
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (receiver != null) {
            // Sometimes the Fragment onDestroy() unregisters the observer before calling below code
            // See <a>http://stackoverflow.com/questions/6165070/receiver-not-registered-exception-error</a>
            try {
                this.unregisterReceiver(receiver);
                receiver = null;
            } catch (IllegalArgumentException e) {
                // Check wether we are in debug mode
                e.printStackTrace();
            }
        }
//        unregisterReceiver(receiver);
    }

    public void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }


}
