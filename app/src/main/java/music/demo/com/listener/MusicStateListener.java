package music.demo.com.listener;

/**
 * 歌曲的状态信息
 * Created by liunian on 2018/1/23.
 */

public interface MusicStateListener {

    void updateTrackInfo();

    void updateTime();

    void changeTheme();

    void reloadAdapter();
}
