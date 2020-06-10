package com.example.douyin_suo.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.example.douyin_suo.R;
import com.example.douyin_suo.entity.PageChangeEventBean;
import com.example.douyin_suo.entity.BarStateEventBean;
import com.example.douyin_suo.entity.UserMessageBean;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

public class userFragment extends Fragment {

    private final static String TAG="Suo";
    private Log log;
    private UserMessageBean userMsg = new UserMessageBean();

    @Override
    public @NotNull
    View onCreateView(LayoutInflater inflater, @NotNull ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_userpage, container, false);
    }

    @Override
    public void onViewCreated(View view, @NotNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        userMsg.setId(getArguments().getString("id"));
        userMsg.setFeedurl(getArguments().getString("url"));
        userMsg.setNickname(getArguments().getString("name"));
        userMsg.setDescription(getArguments().getString("desc"));
        userMsg.setLikecount(getArguments().getString("like"));
        userMsg.setAvatar(getArguments().getString("avatar"));

        View context = this.getView();
        Context context1 = this.getContext();

        final ImageView imageView = context.findViewById(R.id.backimg);
//        HttpURLConnection connection = null;

//        try {
//            URL bitmapUrl = new URL(userMsg.getAvatar());
//            connection = (HttpURLConnection) bitmapUrl.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
//            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                // 得到服务器返回过来的流对象
//                InputStream inputStream = connection.getInputStream();
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                imageView.setImageBitmap(bitmap);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Glide.with(context1).load(userMsg.getAvatar())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.d(TAG, "onException: " + e.toString()+"  model:"+model+" isFirstResource: "+isFirstResource);
                        imageView.setImageResource(R.mipmap.ic_launcher);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.e(TAG,  "model:"+model+" isFirstResource: "+isFirstResource);
                        return false;
                    }
                })
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_nopic)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(imageView);



        TextView textView = context.findViewById(R.id.nickname);
        textView.setText(userMsg.getNickname());

        FrameLayout backBtn = context.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new PageChangeEventBean(0));
            }
        });
        log.d(TAG,"userFragment onViewCreate");
    }
    @Override
    public void onResume(){
        super.onResume();
        EventBus.getDefault().post(new BarStateEventBean(false));
        log.d(TAG,"userFragment onResume");
    }


    public static final userFragment getNewInstance(UserMessageBean msg){
        userFragment fragment = new userFragment();
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
