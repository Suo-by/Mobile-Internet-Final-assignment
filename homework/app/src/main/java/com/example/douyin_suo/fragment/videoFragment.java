package com.example.douyin_suo.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.douyin_suo.entity.UserMessageBean;
import com.example.douyin_suo.widget.LikeClickLayout;
import com.github.like.LikeButton;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import com.example.douyin_suo.R;
import com.example.douyin_suo.entity.PosClearEventBean;
import com.example.douyin_suo.entity.BarStateEventBean;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

public class videoFragment extends Fragment {

    private final static String TAG = "Suo";
    private Log log;

    private UserMessageBean userMsg = new UserMessageBean();
    private long mCurrentPosition = 0L;

    private boolean isLike = true;
    private int likeCount = 0;

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

        log.d(TAG,"videoFragment onCreate");
    }
    @Override
    public @NotNull View onCreateView(LayoutInflater inflater,@NotNull ViewGroup container,@NotNull Bundle savedInstanceState){
        mCurrentPosition = 0;
        log.d(TAG,"videoFragment onCreateView");
        return inflater.inflate(R.layout.fragment_videopage, container, false);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public  void onViewCreated(View view, @NotNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        final View context = this.getView();

        TextView des = context.findViewById(R.id.descripe);
        final TextView[] likecount = {context.findViewById(R.id.likecount)};
        des.setText(userMsg.getDescription());
        likecount[0].setText(userMsg.getLikecount());
        final StandardGSYVideoPlayer gsyVideoPlayer = context.findViewById(R.id.videoPlayer);


        if(gsyVideoPlayer != null){
            gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
            gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
            gsyVideoPlayer.getFullscreenButton().setVisibility(View.GONE);
            gsyVideoPlayer.setNeedShowWifiTip(false);
            gsyVideoPlayer.setLooping(true);
            gsyVideoPlayer.setDismissControlTime(0);
        }
        gsyVideoPlayer.setUpLazy(userMsg.getFeedurl(), false, null, null, "");

        final LikeClickLayout likeLayout = context.findViewById(R.id.likeClick);

        likeLayout.setLikeListener(new LikeClickLayout.LikeListener() {
            @Override
            public void onLikeListener() {
                likeCount++;
                LikeButton likeButton = context.findViewById(R.id.likeBtn);
                if(!likeButton.isLiked())
                    likeButton.performClick();
                if(isLike) {
                    TextView text = context.findViewById(R.id.likecount);
                    String num = (String) text.getText();
                    int count = Integer.parseInt(num);
                    count++;
                    text.setText(Integer.toString(count));
                    isLike = false;
                }
            }

            @Override
            public void onPauseListener() {
                if(gsyVideoPlayer.getGSYVideoManager().isPlaying())
                    gsyVideoPlayer.onVideoPause();
                else
                    gsyVideoPlayer.onVideoResume();
            }
        });

        log.d(TAG,"videoFragment onCreateView");
    }
    @Override
    public void onResume(){
        super.onResume();
        final StandardGSYVideoPlayer gsyVideoPlayer = (StandardGSYVideoPlayer)this.getActivity().findViewById(R.id.videoPlayer);
        EventBus.getDefault().post(new BarStateEventBean(true));
        if(mCurrentPosition > 0){
            gsyVideoPlayer.onVideoResume(false);
        }else {
            gsyVideoPlayer.postDelayed((Runnable)(new Runnable() {
                public final void run() {
                    gsyVideoPlayer.startPlayLogic();
                }
            }), 200L);
        }
        log.d(TAG,"videoFragment onResume");
    }
    @Override
    public void onPause(){
        super.onPause();
        LikeClickLayout likeClickLayout = (LikeClickLayout)this.getActivity().findViewById(R.id.likeClick);
        if(likeClickLayout != null)
            likeClickLayout.onPause();

        StandardGSYVideoPlayer gsyVideoPlayer = (StandardGSYVideoPlayer)this.getActivity().findViewById(R.id.videoPlayer);
        if(gsyVideoPlayer != null) {
            gsyVideoPlayer.onVideoPause();
            GSYVideoManager gsyVideoManager = (GSYVideoManager) gsyVideoPlayer.getGSYVideoManager();
            if(gsyVideoManager != null)
                mCurrentPosition = gsyVideoManager.getCurrentPosition();
            else
                mCurrentPosition = 0;
        }
        log.d(TAG,"videoFragment onPause");
    }
    @Override
    public void onStart(){
        super.onStart();
        log.d(TAG,"videoFragment onStart");
    }
    @Override
    public void onStop(){
        super.onStop();
        log.d(TAG,"videoFragment onStop");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        log.d(TAG,"videoFragment onDestory");
    }

    @Subscribe(
            threadMode = ThreadMode.MAIN
    )
    public void onEventClearPosition(PosClearEventBean event){
        if(event.getIsClear())
            this.mCurrentPosition = 0;
    }

    public static final videoFragment getNewInstance(UserMessageBean userMsg){
        videoFragment fragment = new videoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",userMsg.getId());
        bundle.putString("url", userMsg.getFeedurl());
        bundle.putString("name",userMsg.getNickname());
        bundle.putString("desc",userMsg.getDescription());
        bundle.putString("like", userMsg.getLikecount());
        bundle.putString("avatar", userMsg.getAvatar());
        fragment.setArguments(bundle);
        return fragment;
    }
}
