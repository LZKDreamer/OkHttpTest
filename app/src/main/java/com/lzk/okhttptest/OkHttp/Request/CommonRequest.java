package com.lzk.okhttptest.OkHttp.Request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by LiaoZhongKai on 2019/8/2.
 * 生成Okhttp的Request对象
 */

public class CommonRequest {

    /**
     *POST请求
     * @param url
     * @param params
     * @return 返回一个Request对象
     */
    public static Request createPostRequest(String url,RequestParams params) {
        FormBody.Builder mFormBodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                //遍历参数并添加到FormBody中
                mFormBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        //获取FormBody对象
        FormBody formBody = mFormBodyBuilder.build();

        return new Request.Builder().url(url).post(formBody).build();
    }

    /**
     * GET请求
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url,RequestParams params){
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null){
            for (Map.Entry<String,String> entry : params.urlParams.entrySet()){
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue())
                        .append("&");
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0,urlBuilder.length()-1))
                .get().build();
    }
}
