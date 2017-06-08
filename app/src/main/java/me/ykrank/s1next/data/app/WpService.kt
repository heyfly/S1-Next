package me.ykrank.s1next.data.app

import io.reactivex.Observable
import me.ykrank.s1next.data.app.model.News
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ykrank on 2017/6/8.
 */

interface WpService {

    @GET(WpApi.API_NEWS_LIST)
    fun getNewsList(@Query("page") page: Int): Observable<List<News>>
}