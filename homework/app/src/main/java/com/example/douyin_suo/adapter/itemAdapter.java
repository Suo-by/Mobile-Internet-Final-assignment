package com.example.douyin_suo.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.douyin_suo.entity.UserMessageBean;
import com.example.douyin_suo.fragment.userFragment;
import com.example.douyin_suo.fragment.videoFragment;

public class itemAdapter extends FragmentStateAdapter {

    private Log log;
    private UserMessageBean userMsg;

    public itemAdapter(Fragment fragment, UserMessageBean userMsg) {
        super(fragment);
        this.userMsg = userMsg;
        log.d("Suo", "itemAdapter");
    }

    @Override
    public Fragment createFragment(int position) {
        if(position == 0)
            return videoFragment.getNewInstance(userMsg);
        else
            return userFragment.getNewInstance(userMsg);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
