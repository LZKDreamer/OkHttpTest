package com.lzk.okhttptest.OkHttp;

import com.lzk.okhttptest.OkHttp.Listener.DisposeDataHandle;
import com.lzk.okhttptest.OkHttp.Response.CommonJsonCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by administrator on 2019/8/2.
 * 请求的发送,请求参数的配置
 */

public class CommonOkhttpClient {
    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        mOkHttpClient = okHttpClientBuilder.build();
    }

    /**
     * 发送请求
     * @param request
     * @return
     */
    public static Call sendRequest(Request request, DisposeDataHandle handle){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }
}
