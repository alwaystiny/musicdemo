package music.demo.com.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import music.demo.com.R;
import music.demo.com.activity.NetItemChangeActivity;
import music.demo.com.bean.RecommendListNewAlbumInfo;
import music.demo.com.bean.RecommendListRadioInfo;
import music.demo.com.bean.RecommendListRecommendInfo;
import music.demo.com.util.HttpUtils;
import music.demo.com.util.PreferencesUtility;
import music.demo.com.widget.LoodView;
import okhttp3.ResponseBody;

/**
 *
 * Created by liunian on 2018/1/14.
 */

public class RecommendFragment extends Fragment {

    private Context mContext;
    private boolean isDayFirst;
    private ViewGroup mContent;
    private LayoutInflater layoutInflater;
    private View mRecommendView ,mLoadView ,v1,v2,v3;
    private LoodView mLoodView;
    private LinearLayout mItemLayout,mViewContent;
    private List<RecommendListRecommendInfo> mRecomendList = new ArrayList<>();
    private List<RecommendListNewAlbumInfo> mNewAlbumsList = new ArrayList<>();
    private List<RecommendListRadioInfo> mRadioList = new ArrayList<>();
    private RecommendAdapter mRecommendAdapter;
    private NewAlbumsAdapter mNewAlbumsAdapter;
    private RadioAdapter mRadioAdapter;
    private RecyclerView mRecyclerView1,mRecyclerView2,mRecyclerView3;
    private GridLayoutManager gridLayoutManager1,gridLayoutManager2,gridLayoutManager3;
    private HashMap<String, View> mViewHashMap;
    private String mPosition;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContent = (ViewGroup)inflater.inflate(R.layout.fragment_recommend_container,container,false);



        layoutInflater = LayoutInflater.from(mContext);
        mRecommendView = layoutInflater.inflate(R.layout.recommend, container, false);
        int i = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        TextView day = (TextView) mRecommendView.findViewById(R.id.daily_text);
        day.setText(i+"");

        if(!PreferencesUtility.getInstance(mContext).isCurrentDayFirst(i+"")){
            PreferencesUtility.getInstance(mContext).setCurrentDate(i+"");
            View dayRec = layoutInflater.inflate(R.layout.loading_daymusic,container,false);
            ImageView view1 = (ImageView) dayRec.findViewById(R.id.loading_dayimage) ;
            RotateAnimation rotateAnimation = new RotateAnimation(0,360, 1, 0.5F, 1, 0.5F );
            rotateAnimation.setDuration(20000L);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setRepeatCount(Animation.INFINITE);
            rotateAnimation.setRepeatMode(Animation.INFINITE);
            view1.startAnimation(rotateAnimation);
            isDayFirst = true;
            mContent.addView(dayRec);
        }
        mItemLayout = ((LinearLayout) mRecommendView.findViewById(R.id.item_change));
        mItemLayout.setVisibility(View.INVISIBLE);

        mViewContent = ((LinearLayout) mRecommendView.findViewById(R.id.recommend_layout));
        mLoadView = layoutInflater.inflate(R.layout.loading, null, false);
        mViewContent.addView(mLoadView);


        TextView change = (TextView) mRecommendView.findViewById(R.id.change_item_position);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NetItemChangeActivity.class);
                mContext.startActivity(intent);
            }
        });
        mLoodView = (LoodView) mRecommendView.findViewById(R.id.loop_view);
        if(!isDayFirst){
            mContent.addView(mRecommendView);
        }


        mRecommendAdapter = new RecommendAdapter(null);
        mNewAlbumsAdapter = new NewAlbumsAdapter(null);
        mRadioAdapter = new RadioAdapter(null);

        return mContent;
    }

    public void requestData(){
        reloadAdapter();
    }

    private void reloadAdapter() {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                HashMap<String, String> params = new HashMap<>();
                params.put("channel","ppzs");
                params.put("operator","3");
                params.put("method","baidu.ting.plaza.index");
                params.put("cuid","89CF1E1A06826F9AB95A34DC0F6AAA14");
                params.put("from","android");
                params.put("version","5.8.1.0");
                HttpUtils.getInstance(mContext)
                        .getRetrofit()
                        .getRecommendView(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                String string = responseBody.string();
                                JSONObject object = new JSONObject(string);
                                JSONObject result = object.getJSONObject("result");
                                JSONArray radioArray = result.getJSONObject("radio").getJSONArray("result");
                                JSONArray recommendArray = result.getJSONObject("diy").getJSONArray("result");
                                JSONArray newAlbumArray = result.getJSONObject("mix_1").getJSONArray("result");
                                Gson gson = new Gson();
                                for (int i = 0; i < 6; i++) {
                                    mRecomendList.add(gson.fromJson(recommendArray.get(i).toString(), RecommendListRecommendInfo.class));
                                    mNewAlbumsList.add(gson.fromJson(newAlbumArray.get(i).toString(), RecommendListNewAlbumInfo.class));
                                    mRadioList.add(gson.fromJson(radioArray.get(i).toString(), RecommendListRadioInfo.class));
                                }
                            }
                        });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                v1 = layoutInflater.inflate(R.layout.recommend_playlist, mViewContent, false);
                mRecyclerView1 = ((RecyclerView) v1.findViewById(R.id.recommend_playlist_recyclerview));
                gridLayoutManager1 = new GridLayoutManager(mContext, 3);
                mRecyclerView1.setLayoutManager(gridLayoutManager1);
                mRecyclerView1.setAdapter(mRecommendAdapter);
                mRecyclerView1.setHasFixedSize(true);
                View more = v1.findViewById(R.id.more);
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mChangeView.changeTo(1);
                    }
                });


                v2 = layoutInflater.inflate(R.layout.recommend_newalbums, mViewContent, false);
                mRecyclerView2 = ((RecyclerView) v2.findViewById(R.id.recommend_newalbums_recyclerview));
                gridLayoutManager2 = new GridLayoutManager(mContext, 3);
                mRecyclerView2.setLayoutManager(gridLayoutManager2);
                mRecyclerView2.setAdapter(mNewAlbumsAdapter);
                mRecyclerView2.setHasFixedSize(true);



                v3 = layoutInflater.inflate(R.layout.recommend_radio, mViewContent, false);
                mRecyclerView3 = ((RecyclerView) v3.findViewById(R.id.recommend_radio_recyclerview));
                gridLayoutManager3 = new GridLayoutManager(mContext, 3);
                mRecyclerView3.setLayoutManager(gridLayoutManager3);
                mRecyclerView3.setAdapter(mRadioAdapter);
                mRecyclerView3.setHasFixedSize(true);

                mRecommendAdapter.update(mRecomendList);
                mNewAlbumsAdapter.update(mNewAlbumsList);
                mRadioAdapter.update(mRadioList);

                mViewHashMap = new HashMap<>();
                mViewHashMap.put("推荐歌单",v1);
                mViewHashMap.put("最新专辑",v2);
                mViewHashMap.put("主播电台",v3);
                mPosition = PreferencesUtility.getInstance(mContext).getItemPosition();

                mViewContent.removeView(mLoadView);
                if (isDayFirst){
                    mContent.removeAllViews();
                    mContent.addView(mRecommendView);
                }
                addViews();
                mItemLayout.setVisibility(View.VISIBLE);

            }
        }.execute();
    }

    private void addViews() {
        String[] split = mPosition.split(" ");
        for(int i =0;i<split.length;i++){
            mViewContent.addView(mViewHashMap.get(split[i]));
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(mLoodView != null){
                // mLoodView 布局文件同时需要加上focusableInTouchMode 防止scrollview往下滑
                mLoodView.requestFocus();
            }
        }
    }

    class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ItemView>{

        private List<RecommendListRecommendInfo> mRecomendList;

        private RecommendAdapter(List<RecommendListRecommendInfo> mRecomendList){

            this.mRecomendList = mRecomendList;
        }

        @Override
        public ItemView onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ItemView holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mRecomendList==null?0:mRecomendList.size();
        }

        public void update(List<RecommendListRecommendInfo> mRecomendList) {
            this.mRecomendList = mRecomendList;
            notifyDataSetChanged();
        }

        class ItemView extends RecyclerView.ViewHolder{

            public ItemView(View itemView) {
                super(itemView);
            }
        }
    }

    class NewAlbumsAdapter extends RecyclerView.Adapter<NewAlbumsAdapter.ItemView>{

        private List<RecommendListNewAlbumInfo> mNewAlbumsList;

        private NewAlbumsAdapter(List<RecommendListNewAlbumInfo> mNewAlbumsList){
            this.mNewAlbumsList = mNewAlbumsList;
        }

        @Override
        public ItemView onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ItemView holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mNewAlbumsList==null?0:mNewAlbumsList.size();
        }

        public void update(List<RecommendListNewAlbumInfo> mNewAlbumsList) {
            this.mNewAlbumsList = mNewAlbumsList;
            notifyDataSetChanged();
        }

        class ItemView extends RecyclerView.ViewHolder{

            public ItemView(View itemView) {
                super(itemView);
            }
        }
    }

    class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.ItemView>{

        private List<RecommendListRadioInfo> mRadioList;

        private RadioAdapter(List<RecommendListRadioInfo> mRadioList){
            this.mRadioList = mRadioList;
        }

        @Override
        public ItemView onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ItemView holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mRadioList==null?0:mRadioList.size();
        }

        public void update(List<RecommendListRadioInfo> mRadioList) {
            this.mRadioList = mRadioList;
            notifyDataSetChanged();
        }

        class ItemView extends RecyclerView.ViewHolder{

            public ItemView(View itemView) {
                super(itemView);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPosition == null)return;
        String str = PreferencesUtility.getInstance(mContext).getItemPosition();
        if (!str.equals(mPosition)){
            mPosition = str;
            mViewContent.removeAllViews();
            addViews();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoodView.onDestory();
    }
}
