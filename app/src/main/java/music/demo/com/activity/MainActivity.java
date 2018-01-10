package music.demo.com.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;

import music.demo.com.R;
import music.demo.com.util.HandlerUtil;
import music.demo.com.widget.SplashScreen;

public class MainActivity extends BaseActivity {

    private SplashScreen splashScreen;
    private ImageView barnet, barmusic, barfriends, search;
    private DrawerLayout drawerLayout;
    private ListView mLvLeftMenu;
    private ActionBar ab;

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
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);

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
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setTitle("");
    }

    private void setViewPager() {

    }

    private void setUpDrawer() {

    }
}
