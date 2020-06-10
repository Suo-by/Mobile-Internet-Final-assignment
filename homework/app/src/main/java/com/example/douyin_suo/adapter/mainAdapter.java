package com.example.douyin_suo.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.douyin_suo.entity.UserMessageBean;
import com.example.douyin_suo.fragment.homeFragment;

import java.util.ArrayList;
import java.util.List;

public class mainAdapter extends FragmentStateAdapter {

    private Log log;

    public mainAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        log.d("Suo", "mainAdapter");
    }

    @Override
    public Fragment createFragment(int position) {
        return homeFragment.getNewInstance();
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
