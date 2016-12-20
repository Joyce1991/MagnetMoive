package com.xxz.magnet.ui.fragment;

/**
 * Created with Android Studio.
 * <p>
 * Author:xiaxf
 * <p>
 * Date:2015/8/13.
 */
public class FragmentFactory {
    public static final int FRAGMENT_TYPE_GET_VERIFY = 0x1002; //获取验证码


    private static FragmentFactory instance = new FragmentFactory();

    private FragmentFactory() {
    }

    public static FragmentFactory getInstance() {
        return instance;
    }

    public BaseFragment getFragment(int type) {
        BaseFragment fragment = null;

        switch (type) {
            case FRAGMENT_TYPE_GET_VERIFY:

                break;


        }
        return fragment;
    }

}
