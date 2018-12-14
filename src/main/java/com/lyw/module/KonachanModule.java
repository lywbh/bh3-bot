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
public class KonachanModule {

    private static final String url = "https://konachan.com/post.json";
    private static final String rating = "q";

    public static String randomPic() {
        return randomPic("");
    }

    public static List<String> randomPic(int amount) {
        return randomPic("", amount);
    }

    public static String randomPic(String keyword) {
        List<String> resultList = randomPic(keyword, 1);
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public static List<String> randomPic(String keyword, int amount) {
        String finalUrl = url + "?tags=" + keyword + "%20rating:" + rating;
        log.info("hentai url:" + finalUrl);
        String response = HttpClientUtils.doGet(finalUrl);
        log.info("hentai response:" + response);
        JSONArray respJson = JSON.parseArray(response);
        if (respJson.isEmpty()) {
            return new ArrayList<>();
        }
        List<JSONObject> respList = respJson.toJavaList(JSONObject.class);
        List<JSONObject> fetchResults = RandomUtils.randomFromList(respList, amount);
        return fetchResults.stream().map(r -> r.getString("sample_url")).collect(Collectors.toList());
    }

}
