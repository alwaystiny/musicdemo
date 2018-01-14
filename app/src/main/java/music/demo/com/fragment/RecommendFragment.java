package music.demo.com.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import music.demo.com.R;
import music.demo.com.widget.LoodView;

/**
 *
 * Created by liunian on 2018/1/14.
 */

public class RecommendFragment extends Fragment {

    private Context mContext;
    private ViewGroup mContent;
    private LayoutInflater layoutInflater;
    private View mRecommendView;
    private LoodView mLoodView;
    private boolean isDayFirst;

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

        mLoodView = (LoodView) mRecommendView.findViewById(R.id.loop_view);
        if(!isDayFirst){
            mContent.addView(mRecommendView);
        }
        return mContent;
    }
}
