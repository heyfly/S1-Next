package me.ykrank.s1next;
import android.content.Context;

import java.security.SecureRandom;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import me.ykrank.s1next.util.RxJavaUtil;
import me.ykrank.s1next.widget.NullTrustManager;
import me.ykrank.s1next.widget.net.AppData;
import me.ykrank.s1next.widget.net.Data;
import me.ykrank.s1next.widget.net.Image;
import okhttp3.OkHttpClient;

/**
 * Provides instances of the objects according to build type when we need to inject.
 */
@Module
public final class BuildTypeModule {
    public BuildTypeModule(Context context) {

    }
    @Data
    @Provides
    @Singleton
    OkHttpClient providerDataOkHttpClient(@Data OkHttpClient.Builder builder) {
        //trust https
        try {
            X509TrustManager trustManager = new NullTrustManager();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory(), trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    @Image
    @Provides
    @Singleton
    OkHttpClient providerImageOkHttpClient(@Image OkHttpClient.Builder builder) {
        //trust https
        try {
            X509TrustManager trustManager = new NullTrustManager();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory(), trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    @AppData
    @Provides
    @Singleton
    OkHttpClient providerAppdataOkHttpClient(@AppData OkHttpClient.Builder builder) {
        //trust https
        try {
            X509TrustManager trustManager = new NullTrustManager();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory(), trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }
}