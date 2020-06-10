package com.example.douyin_suo.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.douyin_suo.entity.UserMessageBean;
import com.example.douyin_suo.fragment.itemFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class homeAdapter extends FragmentStateAdapter {

    private Log log;
    private List<UserMessageBean> list = new ArrayList<UserMessageBean>();

    public homeAdapter(@NotNull Fragment fragment, List<UserMessageBean> list) {
        super(fragment);
        this.list = list;
        log.d("Suo", "homeAdapter");
    }

    @Override
    public Fragment createFragment(int position) {
        return itemFragment.getNewInstance(list.get(position));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
