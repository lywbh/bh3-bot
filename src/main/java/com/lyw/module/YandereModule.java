package com.lyw.module;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lyw.util.HttpClientUtils;
import com.lyw.util.RandomUtils;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

@Log4j
public class YandereModule {

    private static final String url = "https://yande.re/post.json";

    public static String randomPic(String rating) {
        if (StringUtils.isEmpty(rating)) {
            rating = "safe";
        }
        int page = RandomUtils.nextIntByRange(1000) + 1;
        String finalUrl = url + "?limit=1&page=" + page + "&tags=rating:" + rating;
        log.info("hentai url:" + finalUrl);
        String response = HttpClientUtils.doGet(finalUrl);
        log.info("hentai response:" + response);
        JSONArray respJson = JSON.parseArray(response);
        return respJson.getJSONObject(0).getString("preview_url");
    }

}
