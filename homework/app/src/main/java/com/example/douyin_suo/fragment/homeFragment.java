package com.example.douyin_suo.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.douyin_suo.JsonGet;
import com.example.douyin_suo.R;
import com.example.douyin_suo.entity.UserMessageBean;
import com.gyf.immersionbar.ImmersionBar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.example.douyin_suo.adapter.homeAdapter;
import com.example.douyin_suo.entity.PosClearEventBean;
import com.example.douyin_suo.entity.BarStateEventBean;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class homeFragment extends Fragment {

    private Log log;
    private final static String TAG = "Suo";

    public static final List<UserMessageBean> userMessages = JsonGet.userMessageBeans;
    private  homeAdapter homeadapter;

    @Override
    public void onCreate(@NotNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        log.d(TAG,"homeFragment onCreate");
    }
    @Override
    public void onResume(){
        super.onResume();
        log.d(TAG,"homeFragment onResume");
    }
    @Override
    public void onPause(){
        super.onPause();
        GSYVideoManager.onPause();
        log.d(TAG,"homeFragment onPause");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        GSYVideoManager.releaseAllVideos();
        log.d(TAG,"homeFragment onDestory");
    }


    @Override
    public @NotNull View onCreateView(@NotNull LayoutInflater inflater, @NotNull ViewGroup container, @NotNull Bundle savedInstanceState){
        log.d(TAG,"homeFragment onCreateView");
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }
    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.with(this).titleBar(R.id.toolBar).init();
        View context = this.getView();
        ViewPager2 viewPager2 = context.findViewById(R.id.viewPager_);
        if (userMessages != null) {
            homeadapter = new homeAdapter(this, userMessages);
            viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            viewPager2.setAdapter(homeadapter);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    EventBus.getDefault().post(new PosClearEventBean(true));
                    if (position == userMessages.size() - 1) {
                        userMessages.add(userMessages.get(0));
                        homeadapter.notifyDataSetChanged();
                    }
                }
            });
        }
        log.d(TAG,"homeFragment onViewCreate");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log.d(TAG, "homeFragment onDestoryView");
    }


    @Subscribe(
            threadMode = ThreadMode.MAIN
    )

    public final void onEventBarState(@NotNull BarStateEventBean event){
        if(event.getIsShow())
            getActivity().findViewById(R.id.toolBar).setVisibility(View.VISIBLE);
        else
            getActivity().findViewById(R.id.toolBar).setVisibility(View.GONE);
    }

    public static final homeFragment getNewInstance(){
        return new homeFragment();
    }

}
