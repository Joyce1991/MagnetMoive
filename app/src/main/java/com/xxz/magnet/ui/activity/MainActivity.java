package com.xxz.magnet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xxz.magnet.R;
import com.xxz.magnet.ui.fragment.FragmentFactory;
import com.xxz.magnet.ui.helper.PageSwitcher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchSubActivity(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("video_id", "xxxxxxxxx");
        PageSwitcher.switchToPage(this, FragmentFactory.FRAGMENT_TYPE_MOVE_DETAIL_DYTT, bundle);
    }
}
