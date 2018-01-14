package music.demo.com.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import music.demo.com.R;
import music.demo.com.fragment.MainFragment;
import music.demo.com.fragment.TabNetPagerFragment;
import music.demo.com.util.HandlerUtil;
import music.demo.com.widget.CustomViewPager;
import music.demo.com.widget.SplashScreen;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SplashScreen splashScreen;
    private ImageView barnet, barmusic, barfriends, search;
    private DrawerLayout drawerLayout;
    private ActionBar ab;
    private NavigationView nav_view;
    private ArrayList<ImageView> tabs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        splashScreen = new SplashScreen(this);
        splashScreen.show(R.drawable.art_login_bg,SplashScreen.SLIDE_LEFT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);

        barnet = (ImageView) findViewById(R.id.bar_net);
        barmusic = (ImageView) findViewById(R.id.bar_music);
        barfriends = (ImageView) findViewById(R.id.bar_friends);
        search = (ImageView) findViewById(R.id.bar_search);
        drawerLayout = (DrawerLayout) findViewById(R.id.fd);
//        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);

        setToolBar();
        setViewPager();
        setUpDrawer();

        HandlerUtil.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashScreen.removeSplashScreen();
            }
        }, 3000);
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setTitle("");

        nav_view = (NavigationView) findViewById(R.id.nav_view);
        // 解决菜单图标不显示原始颜色的问题
        nav_view.setItemIconTintList(null);
        nav_view.setNavigationItemSelectedListener(this);
    }

    private void setViewPager() {

        tabs.add(barnet);
        tabs.add(barmusic);
        final CustomViewPager customViewPager = (CustomViewPager)findViewById(R.id.main_viewpager);
        MainFragment mainFragment = new MainFragment();
        TabNetPagerFragment tabNetPagerFragment = new TabNetPagerFragment();
        final CustomeViewPagerAdapter customeViewPagerAdapter = new CustomeViewPagerAdapter(getSupportFragmentManager());
        customeViewPagerAdapter.addFragment(tabNetPagerFragment);
        customeViewPagerAdapter.addFragment(mainFragment);
        customViewPager.setAdapter(customeViewPagerAdapter);
        customViewPager.setCurrentItem(1);
        barmusic.setSelected(true);
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        barnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewPager.setCurrentItem(0);
            }
        });
        barmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewPager.setCurrentItem(1);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NetSearchWordsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    private void setUpDrawer() {

    }


    /**
     * 侧滑draw的菜单点击时间
     * @param item 菜单
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }


    class CustomeViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragments = new ArrayList<>();

        public CustomeViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment){
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
