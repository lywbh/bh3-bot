package com.lyw.module;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyw.util.HttpClientUtils;
import com.lyw.vo.MusicItem;
import lombok.extern.log4j.Log4j;

@Log4j
public class MusicModule {

    private static String searchUrl = "http://localhost:3000/search";
    private static String fetchUrl = "http://localhost:3000/song/url";

    public static MusicItem search(String key) {
        String finalUrl = searchUrl + "?keywords=" + key;
        log.info("music search url:" + finalUrl);
        String response = HttpClientUtils.doGet(finalUrl);
        log.info("music search response:" + response);
        JSONObject respJson = JSON.parseObject(response);
        JSONArray musicJsonList = respJson.getJSONObject("result").getJSONArray("songs");
        if (musicJsonList.isEmpty()) {
            return null;
        }
        JSONObject mostRelative = musicJsonList.getJSONObject(0);
        MusicItem r = new MusicItem();
        r.setId(mostRelative.getInteger("id"));
        r.setName(mostRelative.getString("name"));
        r.setImageUrl(mostRelative.getJSONObject("album").getJSONObject("artist").getString("img1v1Url"));
        r.setMusicUrl(getRealUrl(r.getId()));
        return r;
    }

    public static String getRealUrl(Integer musicId) {
        String finalUrl = fetchUrl + "?id=" + musicId;
        log.info("play fetch url:" + finalUrl);
        String response = HttpClientUtils.doGet(finalUrl);
        log.info("play fetch response:" + response);
        JSONObject respJson = JSON.parseObject(response);
        JSONArray musicJsonList = respJson.getJSONArray("data");
        if (musicJsonList.isEmpty()) {
            return null;
        }
        return musicJsonList.getJSONObject(0).getString("url");
    }

}
