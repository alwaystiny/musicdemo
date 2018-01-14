package music.demo.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 主界面的recyclerview的adapter
 * Created by liunian on 2018/1/14.
 */

public class MainFragmentAdapter  extends RecyclerView.Adapter<MainFragmentAdapter.ItemHolder>{


    public MainFragmentAdapter(Context mContext) {
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemHolder  extends RecyclerView.ViewHolder{
        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
