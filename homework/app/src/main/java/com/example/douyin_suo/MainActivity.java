package com.example.douyin_suo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.douyin_suo.entity.UserMessageBean;
import com.gyf.immersionbar.ImmersionBar;
import com.example.douyin_suo.adapter.mainAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.douyin_suo.JsonGet.readJsonFile;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Suo";
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(@NotNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            AssetManager am = this.getResources().getAssets();
            JsonGet.userMessageBeans = JsonGet.readJsonFile(am.open("video.json"));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            JsonGet.userMessageBeans = null;
        }

        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_main);
        viewPager2 = (ViewPager2)findViewById(R.id.viewPager);
        viewPager2.setAdapter((RecyclerView.Adapter)(new mainAdapter((FragmentActivity)this)));
        viewPager2.setUserInputEnabled(false);
        int check = ContextCompat.checkSelfPermission(this, "android.permission-group.CALENDAR");
        if(check != 0)
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE\", \"android.permission.WRITE_EXTERNAL_STORAGE"}, 153);
    }

    @SuppressLint("WrongConstant")
    private final void toast(String msg) {
        Toast.makeText((Context)this, (CharSequence)msg, 0).show();
    }
}
