package com.xxz.magnet.ui.fragment;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaolu123.library.widgets.MultiStateView;
import com.xxz.bussiness.network.HttpRequester;
import com.xxz.bussiness.otto.BusProvider;
import com.xxz.magnet.R;
import com.xxz.magnet.utils.ViewUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with Android studio.
 * Author:xiaxf
 * Date:215/7/16.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected View mRootView;
    protected MultiStateView mStateView;
    protected boolean isError;

    private Map<String, String> mTagMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        BusProvider.register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int layoutRes = getLayoutRes();
        if (layoutRes == 0) {
            throw new IllegalArgumentException(
                    "getLayoutRes() returned 0, which is not allowed. "
                            + "If you don't want to use getLayoutRes() but implement your own view for this "
                            + "fragment manually, then you have to override onCreateView();");
        } else {
            mRootView = inflater.inflate(layoutRes, container, false);
            return mRootView;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStateView = findView(R.id.stateView);
        if (mStateView != null) {
            setViewForState();//如果没滑动到当前页则首先显示LoadingView
        }
        initViewAndData(mRootView);
    }

    private void initViewAndData(View view) {
        bindViews(view);
        setupViews(view);
        if (loadDataAfterBindView()) {
            showLoadingView();
            loadData();
        }
    }

    /**
     * 是否在bindview之后loadData，在子类自己配置
     *
     * @return
     */
    protected boolean loadDataAfterBindView() {
        return true;
    }

    /**
     * 设置view相关属性
     */
    protected void setupViews(View view) {

    }

    /**
     * 初始化View
     */
    public abstract void bindViews(View view);

    protected void loadData() {
    }

    /**
     * 自定义某个状态的View
     */
    protected void setViewForState() {

    }

    /**
     * findViewById 省略强转过程
     *
     * @param resId
     * @param <V>具体的View类型
     * @return
     */
    protected <V> V findView(@IdRes int resId) {
        //noinspection unchecked
        return ViewUtils.findView(mRootView, resId);
    }

    /**
     * findViewById 并添加OnClick事件
     *
     * @param resId
     * @param <V>   具体的View类型
     * @return
     */
    protected <V> V findViewAttachOnclick(@IdRes int resId) {
        return ViewUtils.findViewAttachOnclick(mRootView, resId, this);
    }


    protected abstract
    @LayoutRes
    int getLayoutRes();

    /**
     * 处理返回事件
     *
     * @return true表示在fragment内部已经完成了对返回事件的处理，外部不需要再处理;
     * false表示外部需要对返回事件继续处理
     */
    public boolean goBack() {
        return false;
    }

    ///**
    // * 显示空View并初始化控件
    // */
    //protected void showEmptyView() {
    //    if (mStateView == null)
    //        return;
    //
    //    isError = false;
    //    mStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    //    View emptyView = mStateView.getView(MultiStateView.VIEW_STATE_EMPTY);
    //    if (emptyView != null && emptyView.findViewById(R.id.llEmpty) != null) {
    //        emptyView.findViewById(R.id.llEmpty).setOnClickListener(this);
    //    }
    //}

    protected void showContentView() {
        if (mStateView == null)
            return;
        mStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        isError = false;
    }

    public void showLoadingView() {
        if (mStateView == null)
            return;

        mStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
    }

    /**
     * 显示错误View并初始化控件
     */
    protected void showErrorView() {
        if (mStateView == null) {
            return;
        }
        isError = true;
        mStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    protected void showEmptyView() {
        if (mStateView == null) {
            return;
        }
        mStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onDestroy() {
        BusProvider.unregister(this);
        cancelAllHttpRequest();
        super.onDestroy();
    }

    protected void clickEmptyView() {
        //showLoadingView();
        //loadData();
    }

    protected void clickErrorView() {
        isError = false;
        showLoadingView();
        loadData();
    }

    protected String createOKHttpTag(String... params) {
        StringBuilder builder = new StringBuilder(this.toString());
        if (params != null) {
            for (String param : params) {
                builder.append(",").append(param);
            }
        }

        String tag = builder.toString();
        mTagMap.put(tag, null);
        return tag;
    }

    private void cancelAllHttpRequest() {
        if (mTagMap != null) {
            if (cancelRequest()) {
                for (String key : mTagMap.keySet()) {
                    HttpRequester.cancelRequestByTag(key);
                }
            }
            mTagMap.clear();
        }
    }

    protected boolean cancelRequest() {
        return true;
    }

    @Override
    public void onClick(final View v) {
        if (getActivity() == null) {
            return;
        }
        //switch (v.getId()) {
        //    case R.id.ivBack:
        //        getActivity().onBackPressed();
        //        break;
        //
        //    case R.id.llEmpty:
        //        clickEmptyView();
        //        break;
        //
        //    case R.id.tvRetry:
        //        clickErrorView();
        //        break;
        //
        //    case R.id.tvRetryNet:
        //        PageSwitcher.switchToSettingPage(getActivity());
        //        break;
        //}
    }

    public void onPageSelected() {

    }

}
