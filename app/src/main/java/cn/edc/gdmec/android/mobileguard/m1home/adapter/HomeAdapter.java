package cn.edc.gdmec.android.mobileguard.m1home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cn.edc.gdmec.android.mobileguard.R;

/**
 * Created by student on 17/9/19.
 */

public class HomeAdapter extends BaseAdapter {
    private Context context;
    int[] imageId={R.drawable.safe};

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
