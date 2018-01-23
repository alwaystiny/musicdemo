package music.demo.com.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import music.demo.com.R;
import music.demo.com.fragment.BaseFragment;
import music.demo.com.fragment.QuickControlsFragment;
import music.demo.com.listener.MusicStateListener;

/**
 * 基类
 * Created by liunian on 2018/1/10.
 */

public class BaseActivity extends AppCompatActivity {

    private Fragment fragment;
    private ArrayList<MusicStateListener> mMusicListener = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        howQuickControl(true);
    }

    private void howQuickControl(boolean show) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (show) {
            if (fragment ==null) {
                QuickControlsFragment.newInstance();
                fragmentTransaction.add(R.id.bottom_container,fragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.show(fragment).commitAllowingStateLoss();
            }
        } else {
            if (fragment !=null)
                fragmentTransaction.hide(fragment).commitAllowingStateLoss();
        }
    }

    public void setMusicStateListenerListener(MusicStateListener status) {
        if (status != null) {
            mMusicListener.add(status);
        }
    }

    public void removeMusicStateListenerListener(final MusicStateListener status) {
        if (status != null) {
            mMusicListener.remove(status);
        }
    }
}
