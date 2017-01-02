package com.xxz.magnet.ui.fragment;

/**
 * Created with Android Studio.
 * <p>
 * Author:xiaxf
 * <p>
 * Date:2015/8/13.
 */
public class FragmentFactory {
    /**
     * dytt类影视详情
     */
    public static final int FRAGMENT_TYPE_MOVE_DETAIL_DYTT = 0x1003;
    public static final int FRAGMENT_TYPE_MOVE_SCREEN_SHOT = 0x2001;


    private static FragmentFactory instance = new FragmentFactory();

    private FragmentFactory() {
    }

    public static FragmentFactory getInstance() {
        return instance;
    }

    public BaseFragment getFragment(int type) {
        BaseFragment fragment = null;

        switch (type) {
            case FRAGMENT_TYPE_MOVE_DETAIL_DYTT:
                fragment =  DyttFragment.newInstance();
                break;

            case FRAGMENT_TYPE_MOVE_SCREEN_SHOT:
                fragment = SSFragment.newInstance();
                break;
        }
        return fragment;
    }

}
