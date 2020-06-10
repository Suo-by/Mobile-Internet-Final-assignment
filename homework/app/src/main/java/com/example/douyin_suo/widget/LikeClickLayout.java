package com.example.douyin_suo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.douyin_suo.R;


import androidx.annotation.NonNull;


import java.util.Random;

public class LikeClickLayout extends FrameLayout {

    private Drawable icon = getResources().getDrawable(R.mipmap.ic_heart_2);
    private int mClickCount = 0;
    private Handler mHandler = new LikeClickLayoutHandler(this);

    private  LikeListener likeListener;

    public LikeClickLayout(@NonNull Context context) {
        super(context);
        initView();
    }
    public LikeClickLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }
    public LikeClickLayout(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        setClipChildren(false);
    }

    public void setLikeListener(LikeListener likeListener) {
        this.likeListener = likeListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();
            mClickCount++;
            this.mHandler.removeCallbacksAndMessages((Object)null);
            if(mClickCount >= 2){
                addHeartView(x, y);
                likeListener.onLikeListener();
                mHandler.sendEmptyMessageDelayed(1, 500);
            }else {
                mHandler.sendEmptyMessageDelayed(0, 500);
            }
        }
        return true;
    }

    public void onPause(){
        mClickCount = 0;
        mHandler.removeCallbacksAndMessages(null);
    }

    private void pauseClick(){
        if(mClickCount == 1)
            likeListener.onPauseListener();
        mClickCount = 0;
    }

    private void addHeartView(float x, float y){
        LayoutParams layoutParams = new LayoutParams(icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        layoutParams.leftMargin = (int) (x - icon.getIntrinsicWidth()/2);
        layoutParams.topMargin = (int) (y - icon.getIntrinsicHeight());
        final ImageView img = new ImageView(this.getContext());
        img.setScaleType(ImageView.ScaleType.MATRIX);
        Matrix matrix = new Matrix();
        matrix.postRotate(getRandomRotate());
        img.setImageMatrix(matrix);
        img.setImageDrawable(icon);
        img.setLayoutParams(layoutParams);
        addView(img);

        AnimatorSet showSet = getShowAnimSet(img);
        final AnimatorSet hideSet = getHideAnimSet(img);
        showSet.start();
        showSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                hideSet.start();
            }
        });
        hideSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(img);
            }
        });
    }

    private AnimatorSet getShowAnimSet(ImageView view){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.2f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(scaleX, scaleY);
        animSet.setDuration(100);
        return animSet;
    }

    private AnimatorSet getHideAnimSet(ImageView view){
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "ScaleY", 1f, 2f);
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, "translationY", 0f, -150f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(alpha, scaleX, scaleY, translation);
        animSet.setDuration(500);
        return animSet;
    }

    private float getRandomRotate(){
        return (float)((new Random()).nextInt(20)-10);
    }

    private static final class LikeClickLayoutHandler extends Handler {
        private LikeClickLayout mView;

        public LikeClickLayoutHandler(LikeClickLayout view){
            this.mView = view;
        }

        @Override
        public void handleMessage(Message msg){
            if(msg.what == 0)
                mView.pauseClick();
            else if(msg.what == 1)
                mView.onPause();
        }
    }

    public static interface LikeListener{
        public void onLikeListener();
        public void onPauseListener();
    }
}
