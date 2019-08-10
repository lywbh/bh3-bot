package com.lyw.module;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyw.util.HttpClientUtils;
import com.lyw.util.RandomUtils;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
public class YandereModule {

    private static final String url = "https://yande.re/post.json";
    private static final String rating = "explicit";

    public static String randomPic() {
        int page = RandomUtils.nextIntByRange(1000) + 1;
        String finalUrl = url + "?limit=1&page=" + page + "&tags=rating:" + rating;
        log.info("hentai url:" + finalUrl);
        String response = HttpClientUtils.doGet(finalUrl);
        log.info("hentai response:" + response);
        JSONArray respJson = JSON.parseArray(response);
        return respJson.getJSONObject(0).getString("preview_url");
    }

}
