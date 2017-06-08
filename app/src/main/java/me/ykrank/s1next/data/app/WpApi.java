package me.ykrank.s1next.data.app;

/**
 * Created by ykrank on 2017/6/8.
 */

public class WpApi {
    static final String BASE_HOST = "news.stage1.cc";
    static final String BASE_URL = "https://"+BASE_HOST+"/wp-json/wp/v2/";

    static final String API_NEWS_LIST = "posts?per_page=50&order=desc&context=embed";
}
