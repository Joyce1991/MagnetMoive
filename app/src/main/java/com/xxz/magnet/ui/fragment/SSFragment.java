package com.xxz.magnet.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xxz.magnet.R;
import com.xxz.magnet.ui.adapter.SSAdapter;

import java.util.Arrays;

/**
 * 视频截图展示
 */

public class SSFragment extends BaseFragment {
    String[] items = new String[]{"http://i3.tietuku.com/f90a8271581525e4.png", "http://i3.tietuku.com/a8898eb8ad76f51d.png", "http://i3.tietuku.com/72143e618b43ed72.png", "http://i3.tietuku.com/2eb2c403bd0c50f3.png", "http://i3.tietuku.com/f288611fc5e419f3.png"};

    public static SSFragment newInstance() {
        return new SSFragment();
    }

    @Override
    public void bindViews(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.ss_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new SSAdapter(Arrays.asList(items)));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_dytt_ss;
    }
}
