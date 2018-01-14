package music.demo.com.fragment;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import magicasakura.utils.ThemeUtils;
import music.demo.com.R;
import music.demo.com.adapter.MainFragmentAdapter;
import music.demo.com.widget.DividerItemDecoration;

/**
 * 主界面
 * Created by liunian on 2018/1/14.
 */

public class MainFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiprefresh);

        linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefresh.setColorSchemeColors(ThemeUtils.getColorById(mContext,R.color.theme_color_primary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadAdapter();
            }
        });

        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(mContext);
        recyclerView.setAdapter(mainFragmentAdapter);
        recyclerView.setHasFixedSize(true);

        // 设置recyclerview  item之间的 分割符
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,LinearLayoutManager.VERTICAL));

        // 设置没有动画
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);


        // 加载数据
        reloadAdapter();

        ((Activity) mContext).getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);
        return view;
    }

    private void reloadAdapter() {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                ArrayList arrayList = new ArrayList();
                setMusicInfo();
                return null;
            }
        };
    }

    private void setMusicInfo() {

    }

    // 懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            reloadAdapter();
        }
    }
}
