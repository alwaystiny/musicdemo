package music.demo.com.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import music.demo.com.activity.BaseActivity;
import music.demo.com.listener.MusicStateListener;

/**
 *
 * Created by liunian on 2018/1/14.
 */

public class BaseFragment extends Fragment implements MusicStateListener {

    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((BaseActivity)getActivity()).setMusicStateListenerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((BaseActivity)getActivity()).removeMusicStateListenerListener(this);
    }

    @Override
    public void updateTrackInfo() {

    }

    @Override
    public void updateTime() {

    }

    @Override
    public void changeTheme() {

    }

    @Override
    public void reloadAdapter() {

    }
}
