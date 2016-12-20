package com.xxz.magnet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.xxz.magnet.R;
import com.xxz.magnet.ui.fragment.BaseFragment;
import com.xxz.magnet.ui.fragment.FragmentFactory;
import com.xxz.magnet.ui.helper.PageSwitcher;
import com.xxz.magnet.utils.LogUtils;

/**
 * Created with Android Studio.
 * <p/>
 * Author:xiaxf
 * <p/>
 * Date:2015/8/13.
 */
public class SubActivity extends AppCompatActivity {
    protected final static int LAYOUT_CONTAINER = R.id.subContent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        handleIntent(getIntent());
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(LAYOUT_CONTAINER);
    }

    @Override
    public void onBackPressed() {
        Fragment baseFragment = getCurrentFragment();
        if (baseFragment instanceof BaseFragment) {
            if (((BaseFragment) baseFragment).goBack()) {
                return;
            }
        }
        /* 解决fragment addToBackStack后，按返回键出现空白的Activity问题 */
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            try {
                super.onBackPressed();
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
    }

    private void handleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        setIntent(intent);

        int type = intent.getIntExtra(PageSwitcher.INTENT_EXTRA_FRAGMENT_TYPE, 0);
        Bundle args = intent.getBundleExtra(PageSwitcher.INTENT_EXTRA_FRAGMENT_ARGS);
        boolean useAnim = false;

        if (args != null) {
            useAnim = args.getBoolean(PageSwitcher.BUNDLE_FRAGMENT_ANIM);
        } else {
            args = new Bundle();
            final Bundle extras = intent.getExtras();
            if (extras != null) {
                args.putAll(intent.getExtras());
            }
        }
        pushFragment(type, args, LAYOUT_CONTAINER, useAnim);
    }

    /**
     * 将fragment加到backStack
     *
     * @param type      Fragment对应的clazz
     * @param args      Fragment参数
     * @param container 放置Fragment的View节点
     * @param useAnim   是否播放切换动画
     */
    protected void pushFragment(int type, Bundle args, int container, boolean useAnim) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fT = fragmentManager.beginTransaction();

        if (useAnim) {
            fT.setCustomAnimations(R.anim.open_slide_in, R.anim.open_slide_out,
                    R.anim.close_slide_in, R.anim.close_slide_out);
        }

        Fragment fragment;
        fragment = FragmentFactory.getInstance().getFragment(type);

        if (fragment == fragmentManager.findFragmentById(container)) {
            return;
        }

        if (fragment != null) {
            if (args != null) {
                fragment.setArguments(args);
            }

            fT.replace(container, fragment, String.valueOf(type));
        }

        fT.commitAllowingStateLoss();
    }

    /**
     * 向当前Fragment分发onActivityResult事件
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment f = getCurrentFragment();
        if (f != null) {
            f.onActivityResult(requestCode, resultCode, data);
        }
    }
}

