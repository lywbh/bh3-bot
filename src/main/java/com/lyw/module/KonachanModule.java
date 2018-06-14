package com.lyw.module;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyw.util.HttpClientUtils;
import com.lyw.util.RandomUtils;
import lombok.extern.log4j.Log4j;

@Log4j
public class KonachanModule {

    private static final String url = "https://konachan.com/post.json";
    private static final String rating = "s";

    public static String randomPic(String keyword) {
        String finalUrl = url + "?tags=" + keyword + "%20rating:" + rating;
        log.info("hentai url:" + finalUrl);
        String response = HttpClientUtils.doGet(finalUrl);
        log.info("hentai response:" + response);
        JSONArray respJson = JSON.parseArray(response);
        if (respJson.isEmpty()) {
            return null;
        }
        int randomPos = RandomUtils.nextIntByRange(respJson.size());
        JSONObject randomObj = respJson.getJSONObject(randomPos);
        String picUrl = randomObj.getString("sample_url");
        if (picUrl == null) {
            return null;
        }
        return picUrl;
    }

}
