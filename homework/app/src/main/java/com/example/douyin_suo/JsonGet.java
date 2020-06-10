package com.example.douyin_suo;

import com.example.douyin_suo.entity.UserMessageBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonGet {

    public static List<UserMessageBean> userMessageBeans = new ArrayList<>();

    public static List<UserMessageBean> readJsonFile(InputStream filestream) throws JSONException, IOException {

        List<UserMessageBean> userMessages = new ArrayList<>();
        String jsonString = "";
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = filestream.read()) != -1)
            sb.append((char) ch);

        jsonString = new String(sb.toString().getBytes("ISO-8859-1"), "utf-8");

        JSONArray jsonArray = new JSONArray(jsonString);
        for(int i = 0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("_id");
            String feedurl = jsonObject.getString("feedurl");
            String nickname = jsonObject.getString("nickname");
            String description = jsonObject.getString("description");
            String likecount = jsonObject.getString("likecount");
            String avatar = jsonObject.getString("avatar");
            userMessages.add(new UserMessageBean(id,feedurl,nickname,description,likecount,avatar));
        }
        return userMessages;
    }
}
