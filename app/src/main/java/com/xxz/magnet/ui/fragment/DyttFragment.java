package com.xxz.magnet.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xxz.magnet.R;
import com.xxz.magnet.ui.adapter.ViewPagerAdapter;

/**
 * 电影天堂类型电影详情页面
 * 1.
 */

public class DyttFragment extends BaseFragment {

    public static DyttFragment newInstance() {
        return new DyttFragment();
    }
    @Override
    public void bindViews(View view) {
        setupToolbar(view);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.detail_movie_tabs);
        ViewPager pager = (ViewPager) view.findViewById(R.id.detail_movie_viewpager);
        tabLayout.setupWithViewPager(pager);
        setupViewPager(pager);
    }

    private void setupViewPager(ViewPager pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(FragmentFactory.getInstance().getFragment(FragmentFactory.FRAGMENT_TYPE_MOVE_SCREEN_SHOT), "Tab 1");
        adapter.addFrag(FragmentFactory.getInstance().getFragment(FragmentFactory.FRAGMENT_TYPE_MOVE_SCREEN_SHOT), "Tab 2");

        pager.setAdapter(adapter);
    }

    Toolbar toolbar;
    private void setupToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragmant_detail_dytt;
    }


}
