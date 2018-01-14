package music.demo.com.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 *
 * Created by liunian on 2018/1/14.
 */

public class BaseFragment extends Fragment {

    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
