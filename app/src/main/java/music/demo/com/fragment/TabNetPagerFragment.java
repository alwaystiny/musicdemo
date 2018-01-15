package music.demo.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

import magicasakura.utils.ThemeUtils;
import magicasakura.utils.TintManager;
import music.demo.com.R;

/**
 *
 * Created by liunian on 2018/1/14.
 */

public class TabNetPagerFragment extends Fragment {

    private ViewPager viewPager;
    private int page = 0;
    private RecommendFragment recommendFragment;
    private boolean isFirstLoad = true;

    public static final TabNetPagerFragment newInstance(int page, String[]title){
        TabNetPagerFragment tabNetPagerFragment = new TabNetPagerFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("page_number",page);
        tabNetPagerFragment.setArguments(bundle);
        return  tabNetPagerFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_tab,container,false);
        viewPager = ((ViewPager) view.findViewById(R.id.viewpager));
        if (viewPager!=null){
            setUpViewPager(viewPager);
            viewPager.setOffscreenPageLimit(2);
        }
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setTabTextColors(R.color.text_color, ThemeUtils.getThemeColorStateList(getContext(),R.color.theme_color_primary).getDefaultColor());
        tabLayout.setSelectedTabIndicatorColor(ThemeUtils.getThemeColorStateList(getActivity(),R.color.theme_color_primary).getDefaultColor());
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (recommendFragment==null)return;
        if (isVisibleToUser&& isFirstLoad){
            recommendFragment.requestData();
            isFirstLoad = false;
        }
    }

    private void setUpViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        recommendFragment = new RecommendFragment();
        adapter.addFragment(recommendFragment,"新曲");
        adapter.addFragment(new AllPlaylistFragment(), "歌单");
        adapter.addFragment(new RankingFragment(), "排行榜");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setCurrentItem(page);
    }

    class Adapter extends FragmentStatePagerAdapter{

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragment(Fragment fragment,String title){
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
