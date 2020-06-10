package com.example.douyin_suo.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.douyin_suo.R;
import com.example.douyin_suo.adapter.itemAdapter;
import com.example.douyin_suo.entity.PageChangeEventBean;
import com.example.douyin_suo.entity.UserMessageBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

public class itemFragment extends Fragment {

    private final static String TAG = "Suo";
    private Log log;

    private UserMessageBean userMsg = new UserMessageBean();

    @Override
    public void onCreate(@NotNull Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        assert getArguments() != null;
        userMsg.setId(getArguments().getString("id"));
        userMsg.setFeedurl(getArguments().getString("url"));
        userMsg.setNickname(getArguments().getString("name"));
        userMsg.setDescription(getArguments().getString("desc"));
        userMsg.setLikecount(getArguments().getString("like"));
        userMsg.setAvatar(getArguments().getString("avatar"));
        log.d(TAG,"itemFragment onCreate");
    }
    @Override
    public @NotNull View onCreateView(LayoutInflater inflater, @NotNull ViewGroup container,@NotNull Bundle savedInstanceState){
        log.d(TAG,"itemFragment onCreateView");
        return inflater.inflate(R.layout.fragment_itempage, container, false);
    }
    @Override
    public void onViewCreated(View view, @NotNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        View context = this.getView();
        ViewPager2 viewPager2 = context.findViewById(R.id.itemViewPager);
        viewPager2.setAdapter(new itemAdapter(this, this.userMsg));
        log.d(TAG,"itemFragment onViewCreate");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        log.d(TAG,"itemFragment onDestory");
    }

    @Subscribe(
            threadMode = ThreadMode.MAIN
    )
    public void savedInstanceState(PageChangeEventBean event){
        ViewPager2 viewPager2 = (ViewPager2)getActivity().findViewById(R.id.itemViewPager);
        viewPager2.setCurrentItem(event.getPosition());
    }

    public static final itemFragment getNewInstance(UserMessageBean msg){
        itemFragment fragment = new itemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",msg.getId());
        bundle.putString("url", msg.getFeedurl());
        bundle.putString("name",msg.getNickname());
        bundle.putString("desc",msg.getDescription());
        bundle.putString("like", msg.getLikecount());
        bundle.putString("avatar", msg.getAvatar());
        fragment.setArguments(bundle);
        return fragment;
    }
}
