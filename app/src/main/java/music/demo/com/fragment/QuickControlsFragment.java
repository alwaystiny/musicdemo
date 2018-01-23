package music.demo.com.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import magicasakura.utils.ThemeUtils;
import magicasakura.widgets.TintImageView;
import magicasakura.widgets.TintProgressBar;
import music.demo.com.R;
import music.demo.com.service.MusicPlayer;

/**
 * 底部控制播放栏
 * Created by liunian on 2018/1/23.
 */

public class QuickControlsFragment  extends BaseFragment {

    private TintImageView mPlayPause;
    private TintProgressBar mProgress;
    private TextView mTitle;
    private TextView mArtist;
    private SimpleDraweeView mAlbumArt;
    private ImageView next;
    private ImageView playQueue;

    public Runnable mUpdateProgress = new Runnable() {

        @Override
        public void run() {

//            long position = MusicPlayer.position();
//            long duration = MusicPlayer.duration();
//            if (duration > 0 && duration < 627080716){
//                mProgress.setProgress((int) (1000 * position / duration));
//            }
//
//            if (MusicPlayer.isPlaying()) {
//                mProgress.postDelayed(mUpdateProgress, 50);
//            }else {
//                mProgress.removeCallbacks(mUpdateProgress);
//            }

        }
    };

    public static QuickControlsFragment newInstance() {

        return new QuickControlsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bottom_nav, container, false);
        mPlayPause = (TintImageView) rootView.findViewById(R.id.control);
        mProgress = (TintProgressBar) rootView.findViewById(R.id.song_progress_normal);
        mTitle = (TextView) rootView.findViewById(R.id.playbar_info);
        mArtist = (TextView) rootView.findViewById(R.id.playbar_singer);
        mAlbumArt = (SimpleDraweeView) rootView.findViewById(R.id.playbar_img);
        next = (ImageView) rootView.findViewById(R.id.play_next);
        playQueue = (ImageView) rootView.findViewById(R.id.play_list);
        mProgress.setProgressTintList(ThemeUtils.getThemeColorStateList(mContext, R.color.theme_color_primary));
        mProgress.postDelayed(mUpdateProgress,0);

        mPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }
}
